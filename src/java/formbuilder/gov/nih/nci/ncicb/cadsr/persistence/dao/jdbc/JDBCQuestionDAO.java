package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

public class JDBCQuestionDAO extends JDBCBaseDAO implements QuestionDAO  {
  public JDBCQuestionDAO(ServiceLocator locator) {
    super(locator);  
  }
}