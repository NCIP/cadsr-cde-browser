package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.impl;

import com.evermind.sql.OrionCMTDataSource;

import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.ejb.common.SessionBeanAdapter;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service.FormBuilderServiceRemote;
import gov.nih.nci.ncicb.cadsr.persistence.dao.*;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.resource.*;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;

import java.rmi.RemoteException;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.sql.DataSource;


public class FormBuilderEJB extends SessionBeanAdapter
  implements FormBuilderServiceRemote {
  ServiceLocator locator;
  AbstractDAOFactory daoFactory;

  /**
   * Uses the ServiceLocator returned by  ServiceLocatorFactory.getEJBLocator()
   * to instantiate the daoFactory. It could also be changed so that the
   * ServiceLocator be a input param to the ejbCreate.
   */
  public void ejbCreate() {
    locator = ServiceLocatorFactory.getEJBLocator();
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
    String type,
    String classificationIdSeq) throws DMLException {
    FormDAO dao = daoFactory.getFormDAO();
    Collection forms = null;

    try {
      forms =
        dao.getAllForms(
          formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName,
          type, classificationIdSeq);
    }
    catch (Exception ex) {
      throw new DMLException("Cannot get Forms", ex);
    }

    return forms;
  }

  public Form getFormDetails(String formPK) throws DMLException {
    Form myForm = null;
    FormDAO fdao = daoFactory.getFormDAO();
    ModuleDAO mdao = daoFactory.getModuleDAO();
    QuestionDAO qdao = daoFactory.getQuestionDAO();
    FormValidValueDAO vdao = daoFactory.getFormValidValueDAO();
    myForm = getFormRow(formPK);

    List modules = (List) fdao.getModulesInAForm(formPK);
    Iterator mIter = modules.iterator();
    List questions;
    Iterator qIter;
    List values;
    Iterator vIter;
    Module block;
    Question term;
    FormValidValue value;

    while (mIter.hasNext()) {
      block = (Module) mIter.next();

      String moduleId = block.getModuleIdseq();
      questions = (List) mdao.getQuestionsInAModule(moduleId);
      qIter = questions.iterator();

      while (qIter.hasNext()) {
        term = (Question) qIter.next();

        String termId = term.getQuesIdseq();
        values = (List) qdao.getValidValues(termId);
        term.setValidValues(values);
      }

      block.setQuestions(questions);
    }

    myForm.setModules(modules);

    return myForm;
  }

  public Form getFormRow(String formPK) throws DMLException {
    FormDAO dao = daoFactory.getFormDAO();

    return dao.findFormByPrimaryKey(formPK);
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
    return daoFactory.getWorkFlowStatusDAO().getWorkFlowStatusesForACType(
      acType);
  }

  public boolean validateUser(
    String username,
    String password) throws DMLException {
    return false;
  }

  public CDECart retrieveCDECart(String username)
    throws DMLException {
    return null;
  }

  public int addToCDECart(CDECartItem item)
    throws DMLException {
    return 0;
  }

  public int removeFromCDECart(String itemId)
    throws DMLException {
    return 0;
  }

  public Form copyForm(
    String sourceFormPK,
    Form newForm) throws DMLException {
    Form resultForm = null;
    
    try {
      FormDAO myDAO= daoFactory.getFormDAO();
      String resultFormPK = myDAO.copyForm(sourceFormPK,newForm);
      resultForm = this.getFormDetails(resultFormPK);
    } 
    catch (DMLException ex) {
      context.setRollbackOnly();
      throw ex;
    } 
    return resultForm;
  }

  /**
   * public Collection getContextsForUserAndRole( String username, String role)
   * throws DMLException { return null; }
   */
}
