package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCFormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;


public class JDBCFormDAO extends JDBCBaseDAO implements FormDAO {
  public JDBCFormDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all the forms that match the given criteria
   *
   * @param formName
   * @param protocolIdseq
   * @param contextIdseq
   * @param workflow
   * @param category
   * @param type
   *
   * @return forms that match the criteria.
   */
  public Collection getAllForms(
    String formLongName,
    String protocolIdSeq,
    String contextIdSeq,
    String workflow,
    String categoryName,
    String type,
    String classificationIdseq) {
    FormQuery query = new FormQuery();
    query.setDataSource(getDataSource());
    query.setSql(
      formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName, type,
      classificationIdseq);

    return query.execute();
  }

  /**
   * Gets all the modules that belong to the specified form
   *
   * @param formId corresponds to the form idseq
   *
   * @return modules that belong to the specified form
   */
  public Collection getModulesInAForm(String formId) {
    ModulesInAFormQuery query = new ModulesInAFormQuery();
    query.setDataSource(getDataSource());
    query.setSql();

    return query.execute(formId);
  }

  public Form copyForm(
    Form sourceForm,
    Form newForm) throws DMLException {
    return null;
  }

  public Form createFormComponent(Form sourceForm) throws DMLException {
    return null;
  }

  public Form addModules(
    String formId,
    Collection modules) throws DMLException {
    return null;
  }

  /**
   * Gets a form by the form idseq
   *
   * @param formId corresponds to the form idseq
   *
   * @return form that has the formId as the primary key
   */
  public Form findFormByPrimaryKey(String formId) throws DMLException {
    FormByPrimaryKey query = new FormByPrimaryKey();
    query.setDataSource(getDataSource());
    query.setSql();

    return (Form) query.execute(formId).get(0);
  }

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    JDBCFormDAO formTest = new JDBCFormDAO(locator);

    //formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName, 
    // type, classificationIdseq

    /*
       System.out.println(formTest.getAllForms(
         "", "", "99BA9DC8-2095-4E69-E034-080020C9C0E0", "", "", "",
         "99BA9DC8-A622-4E69-E034-080020C9C0E0"));
       System.out.println(formTest.getAllForms(
         "", "", "99BA9DC8-2095-4E69-E034-080020C9C0E0", "", "", "", null));
     */
    System.out.println(
      formTest.getModulesInAForm("99CD59C5-A9A0-3FA4-E034-080020C9C0E0"));

    String formId = "99CD59C5-A9A0-3FA4-E034-080020C9C0E0";

    try {
      System.out.println(formTest.findFormByPrimaryKey(formId));
    }
    catch (DMLException e) {
      System.out.println("Failed to get a form for " + formId);
    }
  }

  public Form addModule(
    String formId,
    Module module) throws DMLException {
    return null;
  }

  public int updateFormComponent(Form newForm) throws DMLException {
    return 0;
  }

  public int deleteForm(String formId) throws DMLException {
    return 0;
  }

  /**
   * Inner class that accesses database to get all the modules that belong to
   * the specified form
   */
  class ModulesInAFormQuery extends MappingSqlQuery {
    ModulesInAFormQuery() {
      super();
    }

    public void setSql() {
      super.setSql("SELECT * FROM FB_MODULES_VIEW where CRF_IDSEQ = ? ");
      declareParameter(new SqlParameter("CRF_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return new JDBCModuleTransferObject(rs);
    }
  }

  /**
   * Inner class that accesses database to get a form using the form idseq
   */
  class FormByPrimaryKey extends MappingSqlQuery {
    FormByPrimaryKey() {
      super();
    }

    public void setSql() {
      super.setSql("SELECT * FROM FB_FORMS_VIEW where QC_IDSEQ = ? ");
      declareParameter(new SqlParameter("QC_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return new JDBCFormTransferObject(rs);
    }
  }

  /**
   * Inner class that accesses database to get all the forms and modules that
   * match the given criteria
   */
  class FormQuery extends MappingSqlQuery {
    FormQuery() {
      super();
    }

    public void setSql(
      String formLongName,
      String protocolIdSeq,
      String contextIdSeq,
      String workflow,
      String categoryName,
      String type,
      String classificationIdseq) {
      String whereClause =
        makeWhereClause(
          formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName,
          type, classificationIdseq);
      super.setSql("SELECT * FROM FB_FORMS_VIEW " + whereClause);
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      String formName = rs.getString("LONG_NAME");

      return new JDBCFormTransferObject(rs);
    }

    public String makeWhereClause(
      String formName,
      String protocol,
      String context,
      String workflow,
      String category,
      String type,
      String classificationIdseq) {
      String where = "";
      StringBuffer whereBuffer = new StringBuffer("");
      boolean hasWhere = false;

      if (StringUtils.doesValueExist(formName)) {
        String temp = StringUtils.strReplace(formName, "*", "%");

        if (hasWhere) {
          whereBuffer.append(
            " AND UPPER(LONG_NAME) LIKE " + "UPPER('" + temp + "')");
        }
        else {
          whereBuffer.append(
            " WHERE UPPER(LONG_NAME) LIKE " + "UPPER('" + temp + "')");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(protocol)) {
        if (hasWhere) {
          whereBuffer.append(" AND PROTO_IDSEQ ='" + protocol + "'");
        }
        else {
          whereBuffer.append(" WHERE PROTO_IDSEQ ='" + protocol + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(context)) {
        if (hasWhere) {
          whereBuffer.append(" AND CONTE_IDSEQ ='" + context + "'");
        }
        else {
          whereBuffer.append(" WHERE CONTE_IDSEQ ='" + context + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(workflow)) {
        if (hasWhere) {
          whereBuffer.append(" AND WORKFLOW ='" + workflow + "'");
        }
        else {
          whereBuffer.append(" WHERE WORKFLOW ='" + workflow + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(category)) {
        if (hasWhere) {
          whereBuffer.append(" AND CATEGORY_NAME ='" + category + "'");
        }
        else {
          whereBuffer.append(" WHERE CATEGORY_NAME ='" + category + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(type)) {
        if (hasWhere) {
          whereBuffer.append(" AND TYPE ='" + type + "'");
        }
        else {
          whereBuffer.append(" WHERE TYPE ='" + type + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(classificationIdseq)) {
        if (hasWhere) {
          whereBuffer.append(
            " AND QC_IDSEQ in (select ac_idseq from ac_csi where CS_CSI_IDSEQ ='" +
            classificationIdseq + "')");
        }
        else {
          whereBuffer.append(
            " WHERE QC_IDSEQ in (select ac_idseq from ac_csi where CS_CSI_IDSEQ ='" +
            classificationIdseq + "')");
          hasWhere = true;
        }
      }

      where = whereBuffer.toString();

      return where;
    }
  }
}
