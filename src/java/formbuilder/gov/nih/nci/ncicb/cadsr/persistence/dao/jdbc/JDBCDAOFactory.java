package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.DAOCreateException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.*;
import java.util.Collection;
import javax.sql.DataSource;



public class JDBCDAOFactory extends AbstractDAOFactory {

  public JDBCDAOFactory() {
  }

  public FormDAO getFormDAO() {
    return new JDBCFormDAO(serviceLocator);
  }

  public FormValidValueDAO getFormValidValueDAO() {
    return new JDBCFormValidValueDAO(serviceLocator);
  }

  public ModuleDAO getModuleDAO() {
    return new JDBCModuleDAO(serviceLocator);
  }

  public QuestionDAO getQuestionDAO() {
    return new JDBCQuestionDAO(serviceLocator);
  }
  
  public UserManagerDAO getUserManagerDAO()
  {
    return new JDBCUserManagerDAO(serviceLocator);
  }
  
    public static void main(String[] args) {
    
   /**  JDBCDAOFactory factory = (JDBCDAOFactory)new JDBCDAOFactory().getDAOFactory((ServiceLocator)new TestServiceLocatorImpl());
     FormDAO dao = factory.getFormDAO();
     Collection test = dao.getFormsByContext("Context1");
     System.out.println(test);
     **/
     
  }
}
