package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleDAO;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import java.util.Collection;


public class JDBCModuleDAO extends JDBCBaseDAO implements ModuleDAO {
  public JDBCModuleDAO(ServiceLocator locator) {
    super(locator);
  }

  public static void main(String[] args) {
    //JDBCModuleDAO jDBCModuleDAO = new JDBCModuleDAO();
  }

  public Collection getQuestionsInAModule(String moduleId) {
    return null;
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
}
