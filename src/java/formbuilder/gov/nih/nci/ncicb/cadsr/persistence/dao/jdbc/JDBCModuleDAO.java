package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCQuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleDAO;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collection;


public class JDBCModuleDAO extends JDBCBaseDAO implements ModuleDAO {
  public JDBCModuleDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all the questions that belong to the specified module
   *
   * @param moduleId corresponds to the module idseq
   *
   * @return questions that belong to the specified module
   */
  public Collection getQuestionsInAModule(String moduleId) {
    Collection col = new ArrayList();
    QuestionsInAModuleQuery query = new QuestionsInAModuleQuery();
    query.setDataSource(getDataSource());
    query.setSql();

    return query.execute(moduleId);
  }

  public Module createModuleComponent(Module sourceModule)
    throws DMLException {
    return null;
  }

  public Module addQuestions(
    String moduleId,
    Collection questions) throws DMLException {
    return null;
  }

  public int deleteModule(String moduleId) throws DMLException {
    return 0;
  }

  public int updateDisplayOrder(
    String moduleId,
    int newDisplayOrder) throws DMLException {
    return 0;
  }

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    JDBCModuleDAO test = new JDBCModuleDAO(locator);

    //System.out.println(
    //  test.getQuestionsInAModule("99CD59C5-B13D-3FA4-E034-080020C9C0E0"));
    System.out.println(
      test.getQuestionsInAModule("99CD59C5-A9C3-3FA4-E034-080020C9C0E0"));
  }

  /**
   * Inner class that accesses database to get all the questions that belong to
   * the specified module
   */
  class QuestionsInAModuleQuery extends MappingSqlQuery {
    QuestionsInAModuleQuery() {
      super();
    }

    public void setSql() {
      super.setSql(
        "SELECT * FROM SBREXT.FB_QUESTIONS_VIEW where MODULE_IDSEQ = ? ");
      declareParameter(new SqlParameter("MODULE_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return new JDBCQuestionTransferObject(rs);
    }
  }
}
