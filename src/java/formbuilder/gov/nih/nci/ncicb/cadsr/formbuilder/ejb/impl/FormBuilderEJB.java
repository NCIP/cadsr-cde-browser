package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.impl;

import com.evermind.sql.OrionCMTDataSource;

import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
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
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

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
    public Collection getAllForms(String formLongName, String protocolIdSeq,
        String contextIdSeq, String workflow, String categoryName, String type,
        String classificationIdSeq) {
        FormDAO dao = daoFactory.getFormDAO();
        Collection forms = null;

        try {
            forms = dao.getAllForms(formLongName, protocolIdSeq, contextIdSeq,
                    workflow, categoryName, type, classificationIdSeq);
        } catch (Exception ex) {
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

    public Module getModule(String modulePK) {
        ModuleDAO mdao = daoFactory.getModuleDAO();
        QuestionDAO qdao = daoFactory.getQuestionDAO();
        FormValidValueDAO vdao = daoFactory.getFormValidValueDAO();

        List questions = (List) mdao.getQuestionsInAModule(modulePK);
        Iterator qIter = questions.iterator();
        Question term = null;
        Module module = null;

        while (qIter.hasNext()) {
            term = (Question) qIter.next();

            String termId = term.getQuesIdseq();
            List values = (List) qdao.getValidValues(termId);
            term.setValidValues(values);
        }

        module.setQuestions(questions);

        return module;
    }

    public Module updateModule(String moduleIdSeq, Module moduleHeader,
        Collection updatedQuestions, Collection deletedQuestions,
        Collection newQuestions, Map updatedValidValues, Map addedValidValues,
        Map deletedValidValues) {
        ModuleDAO moduleDao = daoFactory.getModuleDAO();
        QuestionDAO questionDao = daoFactory.getQuestionDAO();
        FormValidValueDAO formValidValueDao = daoFactory.getFormValidValueDAO();

        if (moduleHeader != null) {
            moduleHeader.setModifiedBy(getUserName());
            moduleDao.updateModuleLongName(moduleHeader.getModuleIdseq(),
                moduleHeader.getLongName());
        }

        if ((updatedQuestions != null) && !updatedQuestions.isEmpty()) {
            Iterator updatedIt = updatedQuestions.iterator();

            while (updatedIt.hasNext()) {
                Question currQuestion = (Question) updatedIt.next();
                currQuestion.setModifiedBy(getUserName());
                questionDao.updateQuestionLongNameDispOrderDeIdseq(currQuestion);
            }
        }

        if ((deletedQuestions != null) && !deletedQuestions.isEmpty()) {
            Iterator deletedIt = deletedQuestions.iterator();

            while (deletedIt.hasNext()) {
                Question currQuestion = (Question) deletedIt.next();
                questionDao.deleteQuestion(currQuestion.getQuesIdseq());
            }
        }

        if ((newQuestions != null) && !newQuestions.isEmpty()) {
            Iterator newIt = newQuestions.iterator();

            while (newIt.hasNext()) {
                Question currQuestion = (Question) newIt.next();
                currQuestion.setCreatedBy(getUserName());
                questionDao.createQuestionComponent(currQuestion);

                List currQuestionValidValues = currQuestion.getValidValues();
                ListIterator currQuestionValidValuesIt = currQuestionValidValues.listIterator();

                while ((currQuestionValidValuesIt != null) &&
                        currQuestionValidValuesIt.hasNext()) {
                    FormValidValue fvv = (FormValidValue) currQuestionValidValuesIt.next();
                    formValidValueDao.createFormValidValueComponent(fvv);
                }
            }
        }

        if ((updatedValidValues != null) && !updatedValidValues.isEmpty()) {
            Set keySet = updatedValidValues.keySet();
            Iterator keyIt = keySet.iterator();

            while (keyIt.hasNext()) {
                String questionIdSeq = (String) keyIt.next();
                List validValueList = (List) updatedValidValues.get(questionIdSeq);
                ListIterator vvListIt = validValueList.listIterator();

                while (vvListIt.hasNext()) {
                    FormValidValue fvv = (FormValidValue) vvListIt.next();
                    fvv.setModifiedBy(getUserName());
                    formValidValueDao.updateDisplayOrder(fvv.getValueIdseq(),
                        fvv.getDisplayOrder());
                }
            }
        }

        if ((addedValidValues != null) && !addedValidValues.isEmpty()) {
            Set keySet = addedValidValues.keySet();
            Iterator keyIt = keySet.iterator();

            while (keyIt.hasNext()) {
                String questionIdSeq = (String) keyIt.next();
                List validValueList = (List) addedValidValues.get(questionIdSeq);
                ListIterator vvListIt = validValueList.listIterator();

                while (vvListIt.hasNext()) {
                    FormValidValue fvv = (FormValidValue) vvListIt.next();
                    fvv.setCreatedBy(getUserName());
                    formValidValueDao.createFormValidValueComponent(fvv);
                }
            }
        }

        if ((deletedValidValues != null) && !deletedValidValues.isEmpty()) {
            Set keySet = deletedValidValues.keySet();
            Iterator keyIt = keySet.iterator();

            while (keyIt.hasNext()) {
                String questionIdSeq = (String) keyIt.next();
                List validValueList = (List) deletedValidValues.get(questionIdSeq);
                ListIterator vvListIt = validValueList.listIterator();

                while (vvListIt.hasNext()) {
                    FormValidValue fvv = (FormValidValue) vvListIt.next();
                    formValidValueDao.deleteFormValidValue(fvv.getValueIdseq());
                }
            }
        }

        return getModule(moduleHeader.getModuleIdseq());
    }

    public Form updateForm(Form formHeader, Collection updatedModules,
        Collection deletedModules) {
        ModuleDAO dao = daoFactory.getModuleDAO();
        FormDAO formdao = daoFactory.getFormDAO();

        if (formHeader != null) {
            formdao.updateFormComponent(formHeader);
        }

        if ((updatedModules != null) && !updatedModules.isEmpty()) {
            Iterator updatedIt = updatedModules.iterator();

            while (updatedIt.hasNext()) {
                Module updatedModule = (Module) updatedIt.next();
                dao.updateDisplayOrder(updatedModule.getModuleIdseq(),
                    updatedModule.getDisplayOrder());
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
    public String createModule(Module module, ModuleInstruction modInstruction) {
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

    public int removeModule(String formPK, String modulePK) {
        return 0;
    }

    public Form copyModules(String formPK, Collection modules) {
        return null;
    }

    public Form createQuestions(String modulePK, Collection questions) {
        return null;
    }

    public Form removeQuestions(String modulePK, Collection questions) {
        return null;
    }

    public Form copyQuestions(String modulePK, Collection questions) {
        return null;
    }

    public Form createValidValues(String modulePK, Collection validValues) {
        return null;
    }

    public Form removeValidValues(String modulePK, Collection validValues) {
        return null;
    }

    public Form copyValidValues(String modulePK, Collection validValues) {
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
        return daoFactory.getWorkFlowStatusDAO().getWorkFlowStatusesForACType(acType);
    }

    public boolean validateUser(String username, String password) {
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

            //       myDAO.deleteCartItem(itemId,getUserName());
            count++;
        }

        return count;
    }

    public Form copyForm(String sourceFormPK, Form newForm) {
        Form resultForm = null;

        FormDAO myDAO = daoFactory.getFormDAO();
        String resultFormPK = myDAO.copyForm(sourceFormPK, newForm);
        resultForm = this.getFormDetails(resultFormPK);

        return resultForm;
    }

    public int updateDEAssociation(String questionId, String deId,
        String newLongName, String username) {
        QuestionDAO myDAO = daoFactory.getQuestionDAO();
        int ret = myDAO.updateQuestionDEAssociation(questionId, deId,
                newLongName, this.getUserName());

        return ret;
    }

    public Map getValidValues(Collection vdIdSeqs) {
        ValueDomainDAO myDAO = daoFactory.getValueDomainDAO();
        Map valueMap = myDAO.getPermissibleValues(vdIdSeqs);

        return valueMap;
    }

    /**
     *
     * @inheritDoc
     */
    public int assignFormClassification(String acId, String csCsiId) {
        FormDAO myDAO = daoFactory.getFormDAO();

        return myDAO.assignClassification(acId, csCsiId);
    }

    /**
     *
     * @inheritDoc
     */
    public int removeFormClassification(String acCsiId) {
        FormDAO myDAO = daoFactory.getFormDAO();

        return myDAO.removeClassification(acCsiId);
    }

    /**
     *
     * @inheritDoc
     */
    public Collection retrieveFormClassifications(String acId) {
        FormDAO myDAO = daoFactory.getFormDAO();

        return myDAO.retrieveClassifications(acId);
    }

    public Form createForm(Form form, FormInstruction formHeaderInstruction,
        FormInstruction formFooterInstruction) {
        FormDAO fdao = daoFactory.getFormDAO();
        Form newForm = fdao.createFormComponent(form);

        formHeaderInstruction.setForm(newForm);

        FormInstructionDAO fidao1 = daoFactory.getFormInstructionDAO();
        fidao1.createFormInstructionComponent(formHeaderInstruction);

        formFooterInstruction.setForm(newForm);

        FormInstructionDAO fidao2 = daoFactory.getFormInstructionDAO();
        fidao2.createFormInstructionComponent(formFooterInstruction);

        return form;
    }
}
