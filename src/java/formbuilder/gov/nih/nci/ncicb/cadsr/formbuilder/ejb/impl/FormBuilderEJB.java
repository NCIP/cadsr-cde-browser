package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.impl;

import com.evermind.sql.OrionCMTDataSource;

import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.ejb.common.SessionBeanAdapter;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service.FormBuilderServiceRemote;
import gov.nih.nci.ncicb.cadsr.persistence.dao.*;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.resource.*;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;

import java.rmi.RemoteException;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import javax.ejb.EJBException;
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
    String classificationIdSeq) {
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

  public Form getFormDetails(String formPK) {
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

  public Form updateForm(
    Form formHeader,
    Collection updatedModules,
    Collection deletedModules) {
    ModuleDAO dao = daoFactory.getModuleDAO();

    //TODO Update form header
    //Update Module Display Order If there are Modules to update
    if ((updatedModules != null) && !updatedModules.isEmpty()) {
      Iterator updatedIt = updatedModules.iterator();

      while (updatedIt.hasNext()) {
        Module updatedModule = (Module) updatedIt.next();
        dao.updateDisplayOrder(
          updatedModule.getModuleIdseq(), updatedModule.getDisplayOrder());
      }
    }

    //Delete Modules
    if ((deletedModules != null) && !deletedModules.isEmpty()) {
      Iterator deletedIt = deletedModules.iterator();

      while (deletedIt.hasNext()) {
        Module deletedModule = (Module) deletedIt.next();
        dao.deleteModule(deletedModule.getModuleIdseq());
      }
    }

    return getFormDetails(formHeader.getFormIdseq());
  }

  public Form getFormRow(String formPK) {
    FormDAO dao = daoFactory.getFormDAO();

    return dao.findFormByPrimaryKey(formPK);
  }

  public Form editFormRow(String formPK) throws DMLException {
    return null;
  }

  public int deleteForm(String formPK) {
    FormDAO fdao = daoFactory.getFormDAO();

    return fdao.deleteForm(formPK);
  }
  
  /**
  * @inheritDoc
  */
  public String createModule(
    Module module,
    ModuleInstruction modInstruction) {
    ModuleDAO mdao = daoFactory.getModuleDAO();
    module.setContext(module.getForm().getContext());

    //       module.setProtocol(module.getForm().getProtocol());
    module.setPreferredDefinition(module.getLongName());

    String modulePK = mdao.createModuleComponent(module);
    module.setModuleIdseq(modulePK);

    modInstruction.setModule(module);
    modInstruction.setContext(module.getForm().getContext());
    modInstruction.setPreferredDefinition(modInstruction.getLongName());

    ModuleInstructionDAO midao = daoFactory.getModuleInstructionDAO();
    midao.createModuleInstructionComponent(modInstruction);

    return modulePK;
  }

  public int removeModule(
    String formPK,
    String modulePK) {
    return 0;
  }

  public Form copyModules(
    String formPK,
    Collection modules) {
    return null;
  }

  public Form createQuestions(
    String modulePK,
    Collection questions) {
    return null;
  }

  public Form removeQuestions(
    String modulePK,
    Collection questions) {
    return null;
  }

  public Form copyQuestions(
    String modulePK,
    Collection questions) {
    return null;
  }

  public Form createValidValues(
    String modulePK,
    Collection validValues) {
    return null;
  }

  public Form removeValidValues(
    String modulePK,
    Collection validValues) {
    return null;
  }

  public Form copyValidValues(
    String modulePK,
    Collection validValues) {
    return null;
  }

  private String getUserName() {
    return context.getCallerPrincipal().getName();
  }

  public Collection getAllContexts() {
    return daoFactory.getContextDAO().getAllContexts();
  }

  public Collection getAllFormCategories() {
    return daoFactory.getFormCategoryDAO().getAllCategories();
  }

  public Collection getStatusesForACType(String acType) {
    return daoFactory.getWorkFlowStatusDAO().getWorkFlowStatusesForACType(
      acType);
  }

  public boolean validateUser(
    String username,
    String password) {
    return false;
  }

  public CDECart retrieveCDECart() {
    String user = getUserName();
    CDECartDAO myDAO = daoFactory.getCDECartDAO();
    CDECart cart = myDAO.findCDECart(user);

    return cart;
  }

  public int addToCDECart(Collection items) {
    String user = context.getCallerPrincipal().getName();
    Iterator it = items.iterator();
    CDECartItem item = null;
    CDECartDAO myDAO = daoFactory.getCDECartDAO();
    int count = 0;

    while (it.hasNext()) {
      item = (CDECartItem) it.next();
      item.setCreatedBy(user);
      myDAO.insertCartItem(item);
      count++;
    }

    return count;
  }

  public int removeFromCDECart(Collection items) {
    Iterator it = items.iterator();
    String itemId = null;
    CDECartDAO myDAO = daoFactory.getCDECartDAO();
    int count = 0;

    while (it.hasNext()) {
      itemId = (String) it.next();
      myDAO.deleteCartItem(itemId);
      count++;
    }

    return count;
  }

  public Form copyForm(
    String sourceFormPK,
    Form newForm) {
    Form resultForm = null;

    FormDAO myDAO = daoFactory.getFormDAO();
    String resultFormPK = myDAO.copyForm(sourceFormPK, newForm);
    resultForm = this.getFormDetails(resultFormPK);

    return resultForm;
  }

  public int updateDEAssociation(
    String questionId,
    String deId,
    String newLongName,
    String username) {
    QuestionDAO myDAO = daoFactory.getQuestionDAO();
    int ret =
      myDAO.updateQuestionDEAssociation(
        questionId, deId, newLongName, this.getUserName());

    return ret;
  }
  
  public Map getValidValule(Collection vdIdSeqs)
  {
    ValueDomainDAO myDAO = daoFactory.getValueDomainDAO();
    Map valueMap =
      myDAO.getPermissibleValues(vdIdSeqs);
    return valueMap;
  }
}
