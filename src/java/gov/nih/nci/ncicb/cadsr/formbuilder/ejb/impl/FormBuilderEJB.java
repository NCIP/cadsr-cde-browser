package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.impl;

import com.evermind.sql.OrionCMTDataSource;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.ejb.common.SessionBeanAdapter;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service.FormBuilderServiceRemote;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.CDECartDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormInstructionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueInstructionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.InstructionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleInstructionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionInstructionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ReferenceDocumentDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ValueDomainDAO;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormInstructionChanges;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValueChange;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValueChanges;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.resource.InstructionChanges;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.ModuleChanges;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.Classification;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.QuestionChange;
import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.persistence.ErrorCodeConstants;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;

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
        String classificationIdSeq,NCIUser user) {
        FormDAO dao = daoFactory.getFormDAO();
        ContextDAO contextDao = daoFactory.getContextDAO();

        Collection forms = null;

        try {
            Context ctep = contextDao.getContextByName(Context.CTEP);
            String contextRestriction =null;
            if((user!=null&&!user.hasRoleAccess(CaDSRConstants.CDE_MANAGER,ctep))&&
               (user!=null&&!user.hasRoleAccess(CaDSRConstants.CONTEXT_ADMIN,ctep)))
            {
               contextRestriction= ctep.getConteIdseq();
            }
            forms = dao.getAllForms(formLongName, protocolIdSeq, contextIdSeq,
                    workflow, categoryName, type, classificationIdSeq,
                    contextRestriction);
        } catch (Exception ex) {
            throw new DMLException("Cannot get Forms", ex);
        }

        return forms;
    }


    /**
     * Get all forms that have been classified by this classification
     */
    public Collection getAllFormsForClassification(String classificationIdSeq)
    {
        FormDAO dao = daoFactory.getFormDAO();
        ContextDAO contextDao = daoFactory.getContextDAO();

        Collection forms = null;

        try {
          forms = dao.getAllFormsForClassification(classificationIdSeq);
        } catch (Exception ex) {
            throw new DMLException("Cannot get Forms", ex);
        }

        return forms;
    }

    /**
     * Get all published forms for a protocol
     */
    public Collection getAllPublishedFormsForProtocol(String protocolIdSeq)
    {
        FormDAO dao = daoFactory.getFormDAO();
        ContextDAO contextDao = daoFactory.getContextDAO();

        Collection forms = null;

        try {
          forms = dao.getAllPublishedFormsForProtocol(protocolIdSeq);
        } catch (Exception ex) {
            throw new DMLException("Cannot get Forms", ex);
        }

        return forms;
    }

    /**
     * Uses get complete form
     * Change Order : isPublished check
     * @param formPK
     *
     * @return form that match the formPK.
     */
    public Form getFormDetails(String formPK) {
        Form myForm = null;
        FormDAO fdao = daoFactory.getFormDAO();
        FormInstructionDAO fInstrdao = daoFactory.getFormInstructionDAO();

        ModuleDAO mdao = daoFactory.getModuleDAO();
        ModuleInstructionDAO mInstrdao = daoFactory.getModuleInstructionDAO();

        QuestionDAO qdao = daoFactory.getQuestionDAO();
        QuestionInstructionDAO qInstrdao = daoFactory.getQuestionInstructionDAO();

        FormValidValueDAO vdao = daoFactory.getFormValidValueDAO();
        FormValidValueInstructionDAO vvInstrdao = daoFactory.getFormValidValueInstructionDAO();
        ContextDAO cdao = daoFactory.getContextDAO();

        myForm = getFormRow(formPK);
        List refDocs = fdao.getAllReferenceDocuments(formPK,myForm.REF_DOC_TYPE_IMAGE);
        myForm.setReferenceDocs(refDocs);

        List instructions = fInstrdao.getInstructions(formPK);
        myForm.setInstructions(instructions);

        List footerInstructions = fInstrdao.getFooterInstructions(formPK);
        myForm.setFooterInstructions(footerInstructions);

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

            List mInstructions = mInstrdao.getInstructions(moduleId);
            block.setInstructions(mInstructions);

            questions = (List) mdao.getQuestionsInAModule(moduleId);
            qIter = questions.iterator();

            while (qIter.hasNext()) {
                term = (Question) qIter.next();

                String termId = term.getQuesIdseq();

                List qInstructions = qInstrdao.getInstructions(termId);
                term.setInstructions(qInstructions);

                values = (List) qdao.getValidValues(termId);
                term.setValidValues(values);

                vIter = values.iterator();
                while(vIter.hasNext())
                {
                  FormValidValue vv = (FormValidValue)vIter.next();
                  String vvId = vv.getValueIdseq();
                  List vvInstructions = vvInstrdao.getInstructions(vvId);
                  vv.setInstructions(vvInstructions);
                }
            }

            block.setQuestions(questions);
        }

        myForm.setModules(modules);
        Context caBIG = cdao.getContextByName(CaDSRConstants.CONTEXT_CABIG);
        myForm.setPublished(fdao.isFormPublished(myForm.getIdseq(),caBIG.getConteIdseq()));

        return myForm;
    }

  public Module getModule(String modulePK) {

    ModuleDAO mdao = daoFactory.getModuleDAO();
    QuestionDAO qdao = daoFactory.getQuestionDAO();
    FormValidValueDAO vdao = daoFactory.getFormValidValueDAO();
    ModuleInstructionDAO moduleInstrDao = daoFactory.getModuleInstructionDAO();
    QuestionInstructionDAO questionInstrDao = daoFactory.getQuestionInstructionDAO();
    FormValidValueInstructionDAO valueValueInstrDao = daoFactory.getFormValidValueInstructionDAO();

    Module module = mdao.findModuleByPrimaryKey(modulePK);
    module.setInstructions(moduleInstrDao.getInstructions(modulePK));
    List questions = (List) mdao.getQuestionsInAModule(modulePK);
    Iterator qIter = questions.iterator();
    Question term =null;

    while (qIter.hasNext()) {
        term = (Question) qIter.next();
        String termId = term.getQuesIdseq();
        term.setInstructions(questionInstrDao.getInstructions(termId));
        List values = (List) qdao.getValidValues(termId);
        term.setValidValues(values);
        Iterator vvIter = values.iterator();
        while (vvIter.hasNext()) {
          FormValidValue vv = (FormValidValue) vvIter.next();
          vv.setInstructions(valueValueInstrDao.getInstructions(vv.getValueIdseq()));
        }
      }

    module.setQuestions(questions);

    return module;
  }

  public Module updateModule(
       String moduleIdSeq,ModuleChanges moduleChanges)
       {
         ModuleDAO moduleDao = daoFactory.getModuleDAO();
         QuestionDAO questionDao = daoFactory.getQuestionDAO();
         FormValidValueDAO formValidValueDao = daoFactory.getFormValidValueDAO();
         ModuleInstructionDAO moduleInstrDao= daoFactory.getModuleInstructionDAO();
         QuestionInstructionDAO questionInstrDao= daoFactory.getQuestionInstructionDAO();
         FormValidValueInstructionDAO validValueInstrDao= daoFactory.getFormValidValueInstructionDAO();

         Module moduleHeader = moduleChanges.getUpdatedModule();
         if(moduleHeader!=null)
         {
           moduleHeader.setModifiedBy(getUserName());
           moduleDao.updateModuleComponent(moduleHeader);
         }
         //make module instruction changes
         InstructionChanges modInstructionChanges= moduleChanges.getInstructionChanges();
         if(modInstructionChanges!=null&& !modInstructionChanges.isEmpty())
             {
                 makeInstructionChanges(moduleInstrDao,modInstructionChanges);
             }
         //make Question instruction changes

         if(moduleChanges!=null&&!moduleChanges.isEmpty())
          {
             deleteQuestions(questionDao,questionInstrDao,formValidValueDao,validValueInstrDao,moduleChanges.getDeletedQuestions());
             updateQuestions(questionDao,questionInstrDao,formValidValueDao,validValueInstrDao,moduleChanges.getUpdatedQuestions());
             createNewQuestions(questionDao,questionInstrDao,formValidValueDao,validValueInstrDao,moduleChanges.getNewQuestions());
          }

         return getModule(moduleIdSeq);
       }

  public Form updateForm(
    String formIdSeq,
    Form formHeader,
    Collection updatedModules,
    Collection deletedModules,
    Collection addedModules,FormInstructionChanges instructionChanges) {
    ModuleDAO dao = daoFactory.getModuleDAO();
    FormDAO formdao = daoFactory.getFormDAO();
    FormInstructionDAO formInstrdao = daoFactory.getFormInstructionDAO();
    ModuleInstructionDAO moduleInstrdao = daoFactory.getModuleInstructionDAO();

   if(formHeader!=null)
   {
     formdao.updateFormComponent(formHeader);
   }
    if ((addedModules != null) && !addedModules.isEmpty()) {
      Iterator addedIt = addedModules.iterator();

      while (addedIt.hasNext()) {
        Module addedModule = (Module) addedIt.next();
        String newModIdseq = dao.createModuleComponent(addedModule);
        Instruction instr = addedModule.getInstruction();
        if(instr!=null)
          moduleInstrdao.createInstruction(instr,newModIdseq);
      }
    }
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
    //Update Instructions

    makeInstructionChanges(formInstrdao,instructionChanges.getFormHeaderInstructionChanges());
    makeFooterInstructionChanges(formInstrdao,instructionChanges.getFormFooterInstructionChanges());

    return getFormDetails(formIdSeq);
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
    public String createModule(Module module, Instruction modInstruction) {
        ModuleDAO mdao = daoFactory.getModuleDAO();
        module.setContext(module.getForm().getContext());

        //       module.setProtocol(module.getForm().getProtocol());
        module.setPreferredDefinition(module.getLongName());

        String modulePK = mdao.createModuleComponent(module);
        module.setModuleIdseq(modulePK);

        modInstruction.setContext(module.getForm().getContext());
        modInstruction.setPreferredDefinition(modInstruction.getLongName());

        ModuleInstructionDAO midao = daoFactory.getModuleInstructionDAO();
        midao.createInstruction(modInstruction,modulePK);

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
        //return "JASUR";//jboss
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

    public CDECart retrieveCDECart(String user) {
        //String user = getUserName();
        CDECartDAO myDAO = daoFactory.getCDECartDAO();
        CDECart cart = myDAO.findCDECart(user);

        return cart;
    }

    public int addToCDECart(Collection items,String user) {
        //String user = context.getCallerPrincipal().getName();
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

    public int removeFromCDECart(Collection items, String user) {
        Iterator it = items.iterator();
        String itemId = null;
        CDECartDAO myDAO = daoFactory.getCDECartDAO();
        int count = 0;

        while (it.hasNext()) {
            itemId = (String) it.next();

            myDAO.deleteCartItem(itemId,user);
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
    public int removeFormClassification(String cscsiIdseq, String acId){
        FormDAO myDAO = daoFactory.getFormDAO();

        return myDAO.removeClassification(cscsiIdseq,acId);
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

    public Form createForm(Form form, Instruction formHeaderInstruction,
        Instruction formFooterInstruction) {
        FormDAO fdao = daoFactory.getFormDAO();
        String newFormIdseq = fdao.createFormComponent(form);


        if (formHeaderInstruction != null)
        {
          FormInstructionDAO fidao1 = daoFactory.getFormInstructionDAO();
          fidao1.createInstruction(formHeaderInstruction,newFormIdseq);
        }

        if (formFooterInstruction != null)
        {
          FormInstructionDAO fidao2 = daoFactory.getFormInstructionDAO();
          fidao2.createFooterInstruction(formFooterInstruction,newFormIdseq);
        }
       Form insertedForm = this.getFormDetails(newFormIdseq);
        return insertedForm;

    }

    //Publish Change Order
    /**
     * Publishes the form by assigning publishing classifications to the form
     *
     * @inheritDoc
     */
    public void publishForm(String formIdSeq, String formType,String contextIdSeq) {
        FormDAO myDAO = daoFactory.getFormDAO();
        Collection schemeItems = null;
        if(formType.equals(PersistenceConstants.FORM_TYPE_CRF))
          schemeItems = myDAO.getPublishingCSCSIsForForm(contextIdSeq);

        if(formType.equals(PersistenceConstants.FORM_TYPE_TEMPLATE))
          schemeItems = myDAO.getPublishingCSCSIsForTemplate(contextIdSeq);

        if(schemeItems==null) return;
        Iterator it = schemeItems.iterator();

        while(it.hasNext())
        {
          CSITransferObject  cscsi = (CSITransferObject)it.next();
          try{
            myDAO.assignClassification(formIdSeq,cscsi.getCsCsiIdseq());

          }
        catch(DMLException exp)
          {
             //Ignored If already classified
             if(!exp.getErrorCode().equals(ErrorCodeConstants.ERROR_DUPLICATE_CLASSIFICATION))
             {
               throw exp;
             }
          }
        }
    }
   //Publish Change Order
    /**
     * Removes the publishing classifications assigned to this form
     * @inheritDoc
     */
    public void unpublishForm(String formIdSeq, String formType,String contextIdSeq) {
        FormDAO myDAO = daoFactory.getFormDAO();
        Collection schemeItems = null;
        if(formType.equals(PersistenceConstants.FORM_TYPE_CRF))
          schemeItems = myDAO.getPublishingCSCSIsForForm(contextIdSeq);

        if(formType.equals(PersistenceConstants.FORM_TYPE_TEMPLATE))
          schemeItems = myDAO.getPublishingCSCSIsForTemplate(contextIdSeq);

        if(schemeItems==null) return;
        Iterator it = schemeItems.iterator();
        while(it.hasNext())
        {
          String  cscsi = (String)it.next();
          myDAO.removeClassification(cscsi,formIdSeq);
        }
    }
  private void updateQuestions(QuestionDAO questionDao,
                                  QuestionInstructionDAO questionInstrDao,
                                  FormValidValueDAO fvvDao,
                                  FormValidValueInstructionDAO fvvInstrDao,
                                  List updatedQuestions)
  {
         if(updatedQuestions!=null&&!updatedQuestions.isEmpty())
         {
           Iterator updatedQChangesIt = updatedQuestions.iterator();
           while(updatedQChangesIt.hasNext())
           {
             QuestionChange currQuestionChange = (QuestionChange)updatedQChangesIt.next();
             if(currQuestionChange!=null&&!currQuestionChange.isEmpty())
             {
               Question currQ = currQuestionChange.getUpdatedQuestion();
               if(currQ!=null)
               {
                  currQ.setModifiedBy(getUserName());
                  questionDao.updateQuestionLongNameDispOrderDeIdseq(currQ);
               }
               InstructionChanges qInstrChanges = currQuestionChange.getInstrctionChanges();
               if(qInstrChanges!=null&&!qInstrChanges.isEmpty())
                  makeInstructionChanges(questionInstrDao,qInstrChanges);

               FormValidValueChanges formVVChanges = currQuestionChange.getFormValidValueChanges();
               if(formVVChanges!=null&&!formVVChanges.isEmpty())
               {
                 createNewValidValues(fvvDao,fvvInstrDao,formVVChanges.getNewValidValues(),formVVChanges.getQuestionId());
                 updateValidValues(fvvDao,fvvInstrDao,formVVChanges.getUpdatedValidValues());
                 deleteValidValues(fvvDao,fvvInstrDao,formVVChanges.getDeletedValidValues());
               }

             }
           }
         }
  }

  private void updateValidValues( FormValidValueDAO fvvDao,
                                  FormValidValueInstructionDAO fvvInstrDao,
                                  List updatedValidValues)
  {
         if(updatedValidValues!=null&&!updatedValidValues.isEmpty())
         {
           Iterator updatedVVChangesIt = updatedValidValues.iterator();
           while(updatedVVChangesIt.hasNext())
           {
             FormValidValueChange currVVChange = (FormValidValueChange)updatedVVChangesIt.next();
             if(currVVChange!=null&&!currVVChange.isEmpty())
             {
               FormValidValue currVV = currVVChange.getUpdatedValidValue();
               if(currVV!=null)
               {
                  currVV.setModifiedBy(getUserName());
                  fvvDao.updateDisplayOrder(currVV.getValueIdseq(),currVV.getDisplayOrder());
               }
               InstructionChanges vvInstrChanges = currVVChange.getInstrctionChanges();
               if(vvInstrChanges!=null&&!vvInstrChanges.isEmpty())
                  makeInstructionChanges(fvvInstrDao,vvInstrChanges);

             }
           }
         }
  }

  private void deleteValidValues( FormValidValueDAO fvvDao,
                                    FormValidValueInstructionDAO fvvInstrDao,
                                    List deletedValidValues)
  {
           if(deletedValidValues!=null&&!deletedValidValues.isEmpty())
           {
             Iterator deletedIt = deletedValidValues.iterator();
             while(deletedIt.hasNext())
             {
               FormValidValue currfvv = (FormValidValue)deletedIt.next();
               fvvDao.deleteFormValidValue(currfvv.getValueIdseq());
                //instructions
                Instruction vvInstr = currfvv.getInstruction();
                 if(vvInstr!=null)
                 {
                    fvvInstrDao.deleteInstruction(vvInstr.getIdseq());
                 }
             }
           }
  }
  private void createNewValidValues( FormValidValueDAO fvvDao,
                                    FormValidValueInstructionDAO fvvInstrDao,
                                    List newValidValues,String parentId)
  {
      
           if(newValidValues!=null&&!newValidValues.isEmpty())
           {
            /**
             Iterator newIt = newValidValues.iterator();
             while(newIt.hasNext())
             {
               FormValidValue currfvv = (FormValidValue)newIt.next();
               String newfvvIdseq = fvvDao.createFormValidValueComponent(currfvv);
                //instructions
                Instruction vvInstr = currfvv.getInstruction();
                 if(vvInstr!=null)
                 {
                    fvvInstrDao.createInstruction(vvInstr,newfvvIdseq);
                 }
             }
             **/
             fvvDao.createFormValidValueComponents(newValidValues,parentId);
             
           }
  }
  private void deleteQuestions(QuestionDAO questionDao,
                                  QuestionInstructionDAO questionInstrDao,
                                  FormValidValueDAO fvvDao,
                                  FormValidValueInstructionDAO fvvInstrDao,
                                  List deletedQuestions)
   {
         if(deletedQuestions!=null&&!deletedQuestions.isEmpty())
         {
           Iterator deletedIt = deletedQuestions.iterator();
           while(deletedIt.hasNext())
           {
             Question currQuestion = (Question)deletedIt.next();
             questionDao.deleteQuestion(currQuestion.getQuesIdseq());
               //instructions
               Instruction qInstr = currQuestion.getInstruction();
               if(qInstr!=null)
               {
                  questionInstrDao.deleteInstruction(qInstr.getIdseq());
               }

             List currQuestionValidValues = currQuestion.getValidValues();
             if (currQuestionValidValues!=null)
             {
               ListIterator currQuestionValidValuesIt = currQuestionValidValues.listIterator();
               while(currQuestionValidValuesIt!=null&&currQuestionValidValuesIt.hasNext())
               {
                 FormValidValue fvv = (FormValidValue)currQuestionValidValuesIt.next();
                 fvvDao.deleteFormValidValue(fvv.getValueIdseq());
                 //instructions
                 Instruction vvInstr = fvv.getInstruction();
                 if(vvInstr!=null)
                 {
                    fvvInstrDao.deleteInstruction(vvInstr.getIdseq());
                 }
               }
             }
           }
         }
   }

  private void createNewQuestions(QuestionDAO questionDao,
                                  QuestionInstructionDAO questionInstrDao,
                                  FormValidValueDAO fvvDao,
                                  FormValidValueInstructionDAO fvvInstrDao,
                                  List newQuestions)
  {
         if(newQuestions!=null&&!newQuestions.isEmpty())
         {
           Iterator newIt = newQuestions.iterator();
           while(newIt.hasNext())
           {
             Question currQuestion = (Question)newIt.next();
             currQuestion.setCreatedBy(getUserName());
             Question newQusetion = questionDao.createQuestionComponent(currQuestion);
               //instructions
               Instruction qInstr = currQuestion.getInstruction();
               if(qInstr!=null)
               {
                  qInstr.setCreatedBy(getUserName());
                  questionInstrDao.createInstruction(qInstr,newQusetion.getQuesIdseq());
               }
             List currQuestionValidValues = currQuestion.getValidValues();
             
             if (currQuestionValidValues!=null)
             {
              
               ListIterator currQuestionValidValuesIt = currQuestionValidValues.listIterator();
               while(currQuestionValidValuesIt!=null&&currQuestionValidValuesIt.hasNext())
               {
                 FormValidValue fvv = (FormValidValue)currQuestionValidValuesIt.next();
                 fvv.setCreatedBy(getUserName());
                 fvv.setQuestion(newQusetion);
                 String newFVVIdseq = fvvDao.createFormValidValueComponent(fvv);
                 //instructions
                 Instruction vvInstr = fvv.getInstruction();
                 if(vvInstr!=null)
                 {
                    vvInstr.setCreatedBy(getUserName());
                    fvvInstrDao.createInstruction(vvInstr,newFVVIdseq);
                 }
               }
               
               //fvvDao.createFormValidValueComponents(currQuestionValidValues,newQusetion.getQuesIdseq());
             }
           }
         }
  }
  private void makeInstructionChanges(InstructionDAO dao, InstructionChanges changes)
  {
    //Create new ones
    Instruction newInstr = changes.getNewInstruction();
    if(newInstr!=null)
    {
       newInstr.setCreatedBy(getUserName());
       dao.createInstruction(newInstr,changes.getParentId());
    }
    //update
    Instruction updatedInstr = changes.getUpdatedInstruction();
    if(updatedInstr!=null)
    {
       updatedInstr.setModifiedBy(getUserName());
       dao.updateInstruction(updatedInstr);

    }
    //delete
    Instruction deleteInstr = changes.getDeletedInstruction();
    if(deleteInstr!=null)
    {
       dao.deleteInstruction(deleteInstr.getIdseq());
    }

  }

  private void makeInstructionChanges(InstructionDAO dao, Map changesMap)
  {
    //Create new ones
    Map newInstrs = (Map)changesMap.get(FormInstructionChanges.NEW_INSTRUCTION_MAP);
    if(newInstrs!=null&&!newInstrs.isEmpty())
    {
           Set keySet = newInstrs.keySet();
           Iterator keyIt = keySet.iterator();
           while(keyIt.hasNext())
           {
             String parentIdSeq = (String)keyIt.next();
             Instruction instr = (Instruction)newInstrs.get(parentIdSeq);
             instr.setCreatedBy(getUserName());
             dao.createInstruction(instr,parentIdSeq);
           }
    }
    //update
    List updatedInstrs = (List)changesMap.get(FormInstructionChanges.UPDATED_INSTRUCTIONS);
    if(updatedInstrs!=null&&!updatedInstrs.isEmpty())
    {
           ListIterator updatedInstrIt = updatedInstrs.listIterator();
           while(updatedInstrIt.hasNext())
           {
             Instruction instr = (Instruction)updatedInstrIt.next();
             instr.setModifiedBy(getUserName());
             dao.updateInstruction(instr);
           }
    }
    //delete
    List deleteInstrs = (List)changesMap.get(FormInstructionChanges.DELETED_INSTRUCTIONS);
    if(deleteInstrs!=null&&!deleteInstrs.isEmpty())
    {
           ListIterator deleteInstrIt = deleteInstrs.listIterator();
           while(deleteInstrIt.hasNext())
           {
             Instruction instr = (Instruction)deleteInstrIt.next();
             dao.deleteInstruction(instr.getIdseq());
           }
    }
  }

  private void makeFooterInstructionChanges(FormInstructionDAO dao, Map changesMap)
  {
    //Create new ones
    Map newInstrs = (Map)changesMap.get(FormInstructionChanges.NEW_INSTRUCTION_MAP);
    if(newInstrs!=null&&!newInstrs.isEmpty())
    {
           Set keySet = newInstrs.keySet();
           Iterator keyIt = keySet.iterator();
           while(keyIt.hasNext())
           {
             String parentIdSeq = (String)keyIt.next();
             Instruction instr = (Instruction)newInstrs.get(parentIdSeq);
             instr.setCreatedBy(getUserName());
             dao.createFooterInstruction(instr,parentIdSeq);
           }
    }
    //update
    List updatedInstrs = (List)changesMap.get(FormInstructionChanges.UPDATED_INSTRUCTIONS);
    if(updatedInstrs!=null&&!updatedInstrs.isEmpty())
    {
           ListIterator updatedInstrIt = updatedInstrs.listIterator();
           while(updatedInstrIt.hasNext())
           {
             Instruction instr = (Instruction)updatedInstrIt.next();
             instr.setModifiedBy(getUserName());
             dao.updateInstruction(instr);
           }
    }
    //delete
    List deleteInstrs = (List)changesMap.get(FormInstructionChanges.DELETED_INSTRUCTIONS);
    if(deleteInstrs!=null&&!deleteInstrs.isEmpty())
    {
           ListIterator deleteInstrIt = deleteInstrs.listIterator();
           while(deleteInstrIt.hasNext())
           {
             Instruction instr = (Instruction)deleteInstrIt.next();
             dao.deleteInstruction(instr.getIdseq());
           }
    }
  }

  public ReferenceDocument createReferenceDocument (ReferenceDocument refDoc, String acIdseq)
  {
        ReferenceDocumentDAO myDAO = daoFactory.getReferenceDocumentDAO();
        return myDAO.createReferenceDoc(refDoc, acIdseq);

  }
  public void deleteReferenceDocument (String rdIdseq)
  {
        ReferenceDocumentDAO myDAO = daoFactory.getReferenceDocumentDAO();
        myDAO.deleteReferenceDocument(rdIdseq);

  }

  public void updateReferenceDocument (ReferenceDocument refDoc)
  {
        ReferenceDocumentDAO myDAO = daoFactory.getReferenceDocumentDAO();
        myDAO.updateReferenceDocument(refDoc);

  }

  public void deleteAttachment (String name)
  {
        ReferenceDocumentDAO myDAO = daoFactory.getReferenceDocumentDAO();
        myDAO.deleteAttachment(name);

  }

  public Collection getAllDocumentTypes() {
    return daoFactory.getReferenceDocumentTypeDAO().getAllDocumentTypes();
  }



}
