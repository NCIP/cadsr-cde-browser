package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.impl;

import gov.nih.nci.ncicb.cadsr.ejb.common.SessionBeanAdapter;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service.FormBuilderServiceRemote;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.sql.DataSource;


public class FormBuilderEJB extends SessionBeanAdapter
  implements FormBuilderServiceRemote {
  ServiceLocator locator;
  AbstractDAOFactory daoFactory;

  /**
   * Uses the ServiceLoactor returned by  ServiceLocatorFactory.getEJBLocator()
   * to instantiate the daoFactory. It could also be changed so that the
   * ServiceLocator be a input param to the ejbCreate.
   */
  public void ejbCreate() {
    locator = null;

    ServiceLocator locator = ServiceLocatorFactory.getEJBLocator();
    daoFactory = AbstractDAOFactory.getDAOFactory(locator);
  }

  /**
   * Uses the formDAO to get all the forms that match the given criteria
   *
   * @param formName
   * @param protocol
   * @param context
   * @param workflow
   * @param category
   * @param type
   *
   * @return forms that match the criteria.
   *
   * @throws DMLException
   */
  public Collection getAllForms(
    String formLongName,
    String protocolIdSeq,
    String contextIdSeq,
    String workflow,
    String categoryName,
    String type) throws DMLException {
    //        JDBCDAOFactory factory = (JDBCDAOFactory)new JDBCDAOFactory().getDAOFactory((ServiceLocator)new TestServiceLocatorImpl());
    FormDAO dao = daoFactory.getFormDAO();
    Collection forms = null;

    try {
      forms =
        dao.getAllForms(
          formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName,
          type);
    }
    catch (Exception ex) {
      throw new DMLException("Cannot get Forms", ex);
    }

    return forms;
  }

  public Form getFormDetails(String formPK) throws DMLException {
    return null;
  }

  public Form getFormRow(String formPK) throws DMLException {
    return null;
  }

  public Form copyForm(Form form) throws DMLException {
    return null;
  }

  public Form editFormRow(String formPK) throws DMLException {
    return null;
  }

  public int deleteForm(String formPK) throws DMLException {
    return 0;
  }

  public Form createModule(
    String formPK,
    Module module) throws DMLException {
    return null;
  }

  public int removeModule(
    String formPK,
    String modulePK) throws DMLException {
    return 0;
  }

  public Form copyModules(
    String formPK,
    Collection modules) throws DMLException {
    return null;
  }

  public Form createQuestions(
    String modulePK,
    Collection questions) throws DMLException {
    return null;
  }

  public Form removeQuestions(
    String modulePK,
    Collection questions) throws DMLException {
    return null;
  }

  public Form copyQuestions(
    String modulePK,
    Collection questions) throws DMLException {
    return null;
  }

  public Form createValidValues(
    String modulePK,
    Collection validValues) throws DMLException {
    return null;
  }

  public Form removeValidValues(
    String modulePK,
    Collection validValues) throws DMLException {
    return null;
  }

  public Form copyValidValues(
    String modulePK,
    Collection validValues) throws DMLException {
    return null;
  }

  public String getUserName() {
    return context.getCallerPrincipal().getName();
  }

  public Collection getAllContexts() throws DMLException {
    return daoFactory.getContextDAO().getAllContexts();  
  }

  public Collection getAllFormCategories() throws DMLException {
    return daoFactory.getFormCategoryDAO().getAllCategories();
  }

  public Collection getStatusesForACType(String acType)
    throws DMLException {
    return daoFactory.getWorkFlowStatusDAO().getWorkFlowStatusesForACType(acType);
  }

  public boolean validateUser(
    String username,
    String password) throws DMLException {
    return false;
    }

 /** public Collection getContextsForUserAndRole(
    String username,
    String role) throws DMLException {
    return null;
    }
    **/
}
