package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCFormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionDAO;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class JDBCQuestionDAO extends JDBCBaseDAO implements QuestionDAO {
  public JDBCQuestionDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all the valid values that belong to the specified question
   *
   * @param questionId corresponds to the question idseq
   *
   * @return valid values that belong to the specified question
   */
  public Collection getValidValues(String questionId) {
    Collection col = new ArrayList();
    ValidValuesInAModuleQuery query = new ValidValuesInAModuleQuery();
    query.setDataSource(getDataSource());
    query.setSql();

    return query.execute(questionId);
  }

  public Question createQuestionComponent(Question newQuestion)
    throws DMLException {
    return null;
  }

  public Question addValidValues(
    String questionId,
    Collection validValues) throws DMLException {
    return null;
  }

  public int deleteQuestion(String questionId) throws DMLException {
    return 0;
  }

  public int updateDisplayOrder(
    String questionId,
    int newDisplayOrder) throws DMLException {
    return 0;
  }

  public int updateQuestionLongName(
    String questionId,
    String newLongName) throws DMLException {
    return 0;
  }

  /**
   * Test application
   * 
   */
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    JDBCQuestionDAO test = new JDBCQuestionDAO(locator);
    
    Collection result = test.getValidValues("D3830147-1454-11BF-E034-0003BA0B1A09");

    Iterator iterator = result.iterator(); 
		while(iterator.hasNext()) {
      System.out.println("Valid Value: " + 
        ((JDBCFormValidValueTransferObject)iterator.next()).toString());
    }

  }

  /**
   * Inner class that accesses database to get all the questions that belong to
   * the specified module
   */
  class ValidValuesInAModuleQuery extends MappingSqlQuery {
    ValidValuesInAModuleQuery() {
      super();
    }

    public void setSql() {
      super.setSql(
        "SELECT * FROM SBREXT.FB_VALID_VALUES_VIEW where QUES_IDSEQ = ? ");
      declareParameter(new SqlParameter("QUESTION_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return new JDBCFormValidValueTransferObject(rs);
    }
  }
}
