package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.impl;

import gov.nih.nci.ncicb.cadsr.ejb.common.SessionBeanAdapter;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service.FormBuilderServiceRemote;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;

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

  public void ejbCreate() {
    locator = null;
    ServiceLocator locator = ServiceLocatorFactory.getEJBLocator();
    daoFactory = AbstractDAOFactory.getDAOFactory(locator);
  }

 public Collection getAllForms(String formName, String protocol, String context, 
    String workflow, String category, String type) throws DMLException
 {
    //        JDBCDAOFactory factory = (JDBCDAOFactory)new JDBCDAOFactory().getDAOFactory((ServiceLocator)new TestServiceLocatorImpl());
    FormDAO dao = daoFactory.getFormDAO();
    Collection test = null;
    try{
     test = dao.getAllForms(formName,protocol, context,workflow,category,type);
    }
    catch(Exception ex)
    {
      throw new DMLException("Cannot get Forms",ex);
    }
    return test;
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
}
