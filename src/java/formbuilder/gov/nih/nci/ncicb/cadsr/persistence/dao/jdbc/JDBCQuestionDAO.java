package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionDAO;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import java.util.Collection;


public class JDBCQuestionDAO extends JDBCBaseDAO implements QuestionDAO {
  public JDBCQuestionDAO(ServiceLocator locator) {
    super(locator);
  }

  public Collection getValidValues(String questionId) {
    return null;
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
}
