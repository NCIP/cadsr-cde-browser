package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCFormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.resource.Form;
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
    Collection col = new ArrayList();
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
    Collection col = new ArrayList();
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

  public Form findFormByPrimaryKey(String formId) throws DMLException {
    return null;
  }

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    JDBCFormDAO formTest = new JDBCFormDAO(locator);

    // formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName, 
    // type, classificationIdseq
    formTest.getAllForms(
      "", "", "99BA9DC8-2095-4E69-E034-080020C9C0E0", "", "", "",
      "99BA9DC8-A622-4E69-E034-080020C9C0E0");

    formTest.getModulesInAForm("99CD59C5-A9A0-3FA4-E034-080020C9C0E0");
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
      //System.out.println("module name = " + rs.getString("LONG_NAME") +
      //  " display order = " + rs.getString("DISPLAY_ORDER"));
      return new JDBCModuleTransferObject(rs);
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

      //System.out.println("form name ++++++ = " + formName);
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

      if (!formName.equals("")) {
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

      if (!protocol.equals("")) {
        if (hasWhere) {
          whereBuffer.append(" AND PTOTO_IDSEQ ='" + protocol + "'");
        }
        else {
          whereBuffer.append(" WHERE PTOTO_IDSEQ ='" + protocol + "'");
          hasWhere = true;
        }
      }

      if (!context.equals("")) {
        if (hasWhere) {
          whereBuffer.append(" AND CONTE_IDSEQ ='" + context + "'");
        }
        else {
          whereBuffer.append(" WHERE CONTE_IDSEQ ='" + context + "'");
          hasWhere = true;
        }
      }

      if (!workflow.equals("")) {
        if (hasWhere) {
          whereBuffer.append(" AND WORKFLOW ='" + workflow + "'");
        }
        else {
          whereBuffer.append(" WHERE WORKFLOW ='" + workflow + "'");
          hasWhere = true;
        }
      }

      if (!category.equals("")) {
        if (hasWhere) {
          whereBuffer.append(" AND CATEGORY_NAME ='" + category + "'");
        }
        else {
          whereBuffer.append(" WHERE CATEGORY_NAME ='" + category + "'");
          hasWhere = true;
        }
      }

      if (!type.equals("")) {
        if (hasWhere) {
          whereBuffer.append(" AND TYPE ='" + type + "'");
        }
        else {
          whereBuffer.append(" WHERE TYPE ='" + type + "'");
          hasWhere = true;
        }
      }

      if (!classificationIdseq.equals("")) {
        if (hasWhere) {
          whereBuffer.append(
            " AND CS_CSI_IDSEQ ='" + classificationIdseq + "'");
        }
        else {
          whereBuffer.append(
            " WHERE CS_CSI_IDSEQ ='" + classificationIdseq + "'");
          hasWhere = true;
        }
      }

      where = whereBuffer.toString();

      return where;
    }
  }
}
