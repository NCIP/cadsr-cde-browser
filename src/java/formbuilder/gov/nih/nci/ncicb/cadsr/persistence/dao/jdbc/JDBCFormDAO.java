package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCFormTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
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

  public Collection getAllForms(
    String formLongName,
    String protocolIdSeq,
    String contextIdSeq,
    String workflow,
    String categoryName,
    String type) {
    Collection col = new ArrayList();
    FromQuery query = new FromQuery();
    query.setDataSource(getDataSource());
    query.setSql(
      formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName, type);

    return query.execute();
  }

  public static void main(String[] args) {
    //JDBCFormDAO 
  }

  public Collection getModulesInAForm(String formId) {
    return null;
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

  // inner class
  class FromQuery extends MappingSqlQuery {
    FromQuery() {
      super();
    }

    public void setSql(
      String formLongName,
      String protocolIdSeq,
      String contextIdSeq,
      String workflow,
      String categoryName,
      String type) {
      String whereClause =
        makeWhereClause(
          formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName,
          type);
      super.setSql("SELECT * FROM FORM_TEMPLATE_VIEW " + whereClause);
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return new JDBCFormTransferObject(rs);
    }

    public String makeWhereClause(
      String formName,
      String protocol,
      String context,
      String workflow,
      String category,
      String type) {
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

      where = whereBuffer.toString();

      return where;
    }
  }

  // inner class end    
}
