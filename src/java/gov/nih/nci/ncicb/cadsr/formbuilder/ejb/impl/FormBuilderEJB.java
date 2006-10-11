package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.impl;

import com.evermind.sql.OrionCMTDataSource;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.ejb.common.SessionBeanAdapter;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service.FormBuilderServiceRemote;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
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
import gov.nih.nci.ncicb.cadsr.resource.FormElement;
import java.util.HashMap;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AdminComponentDAO;

import gov.nih.nci.ncicb.cadsr.persistence.dao.ConceptDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ProtocolDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionRepititionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.TriggerActionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc.JDBCQuestionDAO;
import gov.nih.nci.ncicb.cadsr.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.QuestionRepitition;
import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;
import gov.nih.nci.ncicb.cadsr.resource.TriggerActionChanges;
import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;
import gov.nih.nci.ncicb.cadsr.resource.Version;

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

public class FormBuilderEJB extends SessionBeanAdapter implements FormBuilderServiceRemote
{
    ServiceLocator locator;

    AbstractDAOFactory daoFactory;

    /**
     * Uses the ServiceLocator returned by  ServiceLocatorFactory.getEJBLocator()
     * to instantiate the daoFactory. It could also be changed so that the
     * ServiceLocator be a input param to the ejbCreate.
     */
    public void ejbCreate()
    {
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
     * @param classificationIdSeq
     * @param version
     * @param moduleLongName
     * @param cdePublicId
     * @param user
     *
     * @return forms that match the criteria.
     *
     * @throws DMLException
     */
    public Collection getAllForms(String formLongName, String protocolIdSeq,
                                  String contextIdSeq, String workflow,
                                  String categoryName, String type,
                                  String classificationIdSeq, String publicId,
                                  String version, String moduleLongName,
                                  String cdePublicId, NCIUser user)
    {
        FormDAO dao = daoFactory.getFormDAO();
        ContextDAO contextDao = daoFactory.getContextDAO();

        Collection forms = null;

        try
        {
            String contextRestriction = null;
            /** TT 1892
            Context ctep = contextDao.getContextByName(Context.CTEP);

            if((user!=null&&!user.hasRoleAccess(CaDSRConstants.CDE_MANAGER,ctep))&&
               (user!=null&&!user.hasRoleAccess(CaDSRConstants.CONTEXT_ADMIN,ctep)))
            {
               contextRestriction= ctep.getConteIdseq();
            }
            **/
            forms =
                dao.getAllForms(formLongName, protocolIdSeq, contextIdSeq, workflow,
                                    categoryName, type, classificationIdSeq,
                                    contextRestriction, publicId, version,
                                    moduleLongName, cdePublicId);

        } catch (Exception ex)
        {
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

        try
        {
            forms = dao.getAllFormsForClassification(classificationIdSeq);
        } catch (Exception ex)
        {
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

        try
        {
            forms = dao.getAllPublishedFormsForProtocol(protocolIdSeq);
        } catch (Exception ex)
        {
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
    public Form getFormDetails(String formPK)
    {
        Form myForm = null;
        FormDAO fdao = daoFactory.getFormDAO();
        QuestionRepititionDAO qrdao = daoFactory.getQuestionRepititionDAO();
        FormInstructionDAO fInstrdao = daoFactory.getFormInstructionDAO();

        ModuleDAO mdao = daoFactory.getModuleDAO();
        ModuleInstructionDAO mInstrdao = daoFactory.getModuleInstructionDAO();

        QuestionDAO qdao = daoFactory.getQuestionDAO();
        QuestionInstructionDAO qInstrdao =
            daoFactory.getQuestionInstructionDAO();

        FormValidValueDAO vdao = daoFactory.getFormValidValueDAO();
        FormValidValueInstructionDAO vvInstrdao =
            daoFactory.getFormValidValueInstructionDAO();
        ContextDAO cdao = daoFactory.getContextDAO();

        myForm = getFormRow(formPK);
        List refDocs =
            fdao.getAllReferenceDocuments(formPK, ReferenceDocument.REF_DOC_TYPE_IMAGE);
        myForm.setReferenceDocs(refDocs);

        List instructions = fInstrdao.getInstructions(formPK);
        myForm.setInstructions(instructions);

        List footerInstructions = fInstrdao.getFooterInstructions(formPK);
        myForm.setFooterInstructions(footerInstructions);

        List modules = (List)fdao.getModulesInAForm(formPK);
        Iterator mIter = modules.iterator();
        List questions;
        Iterator qIter;
        List values;
        Iterator vIter;
        Module block;
        Question term;
        FormValidValue value;

        Map<String,FormElement> possibleTargets = new HashMap<String,FormElement>();
        List<TriggerAction>  allActions = new ArrayList<TriggerAction>();

        while (mIter.hasNext())
        {
            block = (Module)mIter.next();
            block.setForm(myForm);

            String moduleId = block.getModuleIdseq();

            List mInstructions = mInstrdao.getInstructions(moduleId);
            block.setInstructions(mInstructions);

            questions = (List)mdao.getQuestionsInAModule(moduleId);
            qIter = questions.iterator();

            while (qIter.hasNext())
            {
                term = (Question)qIter.next();
                term.setModule(block);
                String termId = term.getQuesIdseq();
                 possibleTargets.put(termId,term); //one of the possible targets

                if (term.getDataElement() != null){
                    List<ReferenceDocument> deRefDocs =
                    getReferenceDocuments(term.getDataElement().getDeIdseq());
                    term.getDataElement().setReferenceDocs(deRefDocs);
                    
                    
                    //set the value domain 
                    ValueDomain currentVd = term.getDataElement().getValueDomain();                    
                    if (currentVd!=null && currentVd.getVdIdseq()!=null){
                        ValueDomainDAO vdDAO = daoFactory.getValueDomainDAO();                        
                        ValueDomain vd = vdDAO.getValueDomainById(currentVd.getVdIdseq());
                        
                        //set concept to the value domain.
                        String cdrId = vd.getConceptDerivationRule().getIdseq();
                        if (cdrId!=null){
                            ConceptDAO conceptDAO = daoFactory.getConceptDAO();
                            ConceptDerivationRule cdr =  
                                conceptDAO.findConceptDerivationRule(vd.getConceptDerivationRule().getIdseq());
                            vd.setConceptDerivationRule(cdr);
                        }                        
                        term.getDataElement().setValueDomain(vd);
                    }//end of if   
                }
                List qInstructions = qInstrdao.getInstructions(termId);
                term.setInstructions(qInstructions);
                                
                
                values = (List)qdao.getValidValues(termId);
                term.setValidValues(values);

                vIter = values.iterator();
                while (vIter.hasNext())
                {
                    FormValidValue vv = (FormValidValue)vIter.next();
                    vv.setQuestion(term);
                    String vvId = vv.getValueIdseq();
                    List vvInstructions = vvInstrdao.getInstructions(vvId);
                    vv.setInstructions(vvInstructions);
                    //Set Skip Patterns
                    List<TriggerAction> actions= getAllTriggerActionsForSource(vvId);
                    allActions.addAll(actions);
                    setSourceForTriggerActions(vv,actions);
                    vv.setTriggerActions(actions);
                }
                
                //set question default 
                term = setQuestionDefaultValue(term);
                List<QuestionRepitition> qReps = qrdao.getQuestionRepititions(term.getQuesIdseq());
                setQuestionRepititionDefaults(qReps,values);
                term.setQuestionRepitition(qReps);                
            }

            block.setQuestions(questions);
            //Set Skip Patterns
             possibleTargets.put(moduleId,block); //one of the possible targets
            List<TriggerAction> actions= getAllTriggerActionsForSource(moduleId);
            allActions.addAll(actions);
            setSourceForTriggerActions(block,actions);
            block.setTriggerActions(actions);
        }

        myForm.setModules(modules);
        Context caBIG = cdao.getContextByName(CaDSRConstants.CONTEXT_CABIG);
        myForm
        .setPublished(fdao.isFormPublished(myForm.getIdseq(), caBIG.getConteIdseq()));

        //Collection formCSIs = fdao.retrieveClassifications(formPK);
        //myForm.setClassifications(formCSIs);
        setTargetsForTriggerActions(possibleTargets,allActions);

        return myForm;
    }

    public Module getModule(String modulePK)
    {

        ModuleDAO mdao = daoFactory.getModuleDAO();
        QuestionDAO qdao = daoFactory.getQuestionDAO();
        QuestionRepititionDAO qrdao = daoFactory.getQuestionRepititionDAO();
        FormValidValueDAO vdao = daoFactory.getFormValidValueDAO();
        ModuleInstructionDAO moduleInstrDao =
            daoFactory.getModuleInstructionDAO();
        QuestionInstructionDAO questionInstrDao =
            daoFactory.getQuestionInstructionDAO();
        FormValidValueInstructionDAO valueValueInstrDao =
            daoFactory.getFormValidValueInstructionDAO();

        Module module = mdao.findModuleByPrimaryKey(modulePK);
        module.setInstructions(moduleInstrDao.getInstructions(modulePK));
        List questions = (List)mdao.getQuestionsInAModule(modulePK);
        Iterator qIter = questions.iterator();
        Question term = null;

        Map<String,FormElement> possibleTargets = new HashMap<String,FormElement>();
        List<TriggerAction>  allActions = new ArrayList<TriggerAction>();


        while (qIter.hasNext())
        {
            term = (Question)qIter.next();
            String termId = term.getQuesIdseq();
            term.setModule(module);
            if (term.getDataElement() != null){
                List<ReferenceDocument> refDocs =
                    getReferenceDocuments(term.getDataElement().getDeIdseq());
                term.getDataElement().setReferenceDocs(refDocs);

                //set the value domain 
                ValueDomain currentVd = term.getDataElement().getValueDomain();                    
                if (currentVd!=null && currentVd.getVdIdseq()!=null){
                    ValueDomainDAO vdDAO = daoFactory.getValueDomainDAO();                        
                    ValueDomain vd = vdDAO.getValueDomainById(currentVd.getVdIdseq());
                    
                    //set concept to the value domain.
                    String cdrId = vd.getConceptDerivationRule().getIdseq();
                    if (cdrId!=null){
                        ConceptDAO conceptDAO = daoFactory.getConceptDAO();
                        ConceptDerivationRule cdr =  
                            conceptDAO.findConceptDerivationRule(vd.getConceptDerivationRule().getIdseq());
                        vd.setConceptDerivationRule(cdr);
                    }                        
                    term.getDataElement().setValueDomain(vd);
                }//end of if   
            }        

            term.setInstructions(questionInstrDao.getInstructions(termId));
            List values = (List)qdao.getValidValues(termId);
            term.setValidValues(values);
            Iterator vvIter = values.iterator();

            possibleTargets.put(termId,term); //one of the possible targets

            while (vvIter.hasNext())
            {
                FormValidValue vv = (FormValidValue)vvIter.next();
                vv.setQuestion(term);
                vv.setInstructions(valueValueInstrDao.getInstructions(vv.getValueIdseq()));
               //Set Skip Patterns
               List<TriggerAction> actions= getAllTriggerActionsForSource(vv.getValueIdseq());
               allActions.addAll(actions);
               setSourceForTriggerActions(vv,actions);
               vv.setTriggerActions(actions);
           }
           
            //set question default 
            term = setQuestionDefaultValue(term);
            List<QuestionRepitition> qReps = qrdao.getQuestionRepititions(term.getQuesIdseq());
            setQuestionRepititionDefaults(qReps,values);
            term.setQuestionRepitition(qReps);
            
        }

        module.setQuestions(questions);
        //Set Skip Patterns
         possibleTargets.put(modulePK,module); //one of the possible targets
        List<TriggerAction> actions= getAllTriggerActionsForSource(modulePK);
        allActions.addAll(actions);
        setSourceForTriggerActions(module,actions);
        module.setTriggerActions(actions);

        setTargetsForTriggerActions(possibleTargets,allActions);

        return module;
    }

    public Module updateModule(String moduleIdSeq, ModuleChanges moduleChanges)
    {
        ModuleDAO moduleDao = daoFactory.getModuleDAO();
        QuestionDAO questionDao = daoFactory.getQuestionDAO();
        FormValidValueDAO formValidValueDao =
            daoFactory.getFormValidValueDAO();
        ModuleInstructionDAO moduleInstrDao =
            daoFactory.getModuleInstructionDAO();
        QuestionInstructionDAO questionInstrDao =
            daoFactory.getQuestionInstructionDAO();
        FormValidValueInstructionDAO validValueInstrDao =
            daoFactory.getFormValidValueInstructionDAO();

        Module moduleHeader = moduleChanges.getUpdatedModule();
        if (moduleHeader != null)
        {
            moduleHeader.setModifiedBy(getUserName());
            moduleDao.updateModuleComponent(moduleHeader);
        }
        //make module instruction changes
        InstructionChanges modInstructionChanges =
            moduleChanges.getInstructionChanges();
        if (modInstructionChanges != null && !modInstructionChanges.isEmpty())
        {
            makeInstructionChanges(moduleInstrDao, modInstructionChanges);
        }
        //make Question instruction changes

        if (moduleChanges != null && !moduleChanges.isEmpty())
        {
            deleteQuestions(questionDao, questionInstrDao, formValidValueDao,
                            validValueInstrDao,
                            moduleChanges.getDeletedQuestions());
            updateQuestions(questionDao, questionInstrDao, formValidValueDao,
                            validValueInstrDao,
                            moduleChanges.getUpdatedQuestions());
            createNewQuestions(questionDao, questionInstrDao,
                               formValidValueDao, validValueInstrDao,
                               moduleChanges.getNewQuestions());
        }

        return getModule(moduleIdSeq);
    }

    public Form updateForm(String formIdSeq, Form formHeader,
                           Collection updatedModules,
                           Collection deletedModules, Collection addedModules,
                           Collection addedProtocolIds,
                           Collection removedProtocolIds,
                           Collection protocolTriggerActionChanges,
                           FormInstructionChanges instructionChanges)
    {
        ModuleDAO dao = daoFactory.getModuleDAO();
        QuestionDAO questionDAO = daoFactory.getQuestionDAO();
        QuestionInstructionDAO questInstrDAO = daoFactory.getQuestionInstructionDAO();
        FormValidValueDAO formVVDAO = daoFactory.getFormValidValueDAO();
        FormValidValueInstructionDAO formVVInstDAO = daoFactory.getFormValidValueInstructionDAO();
        FormDAO formdao = daoFactory.getFormDAO();
        FormInstructionDAO formInstrdao = daoFactory.getFormInstructionDAO();
        ModuleInstructionDAO moduleInstrdao =
            daoFactory.getModuleInstructionDAO();

        if (formHeader != null)
        {
            formHeader.setModifiedBy(getUserName());
            formdao.updateFormComponent(formHeader);
        }
        if ((addedModules != null) && !addedModules.isEmpty())
        {
            Iterator addedIt = addedModules.iterator();

            while (addedIt.hasNext())
            {
                Module addedModule = (Module)addedIt.next();
                String newModIdseq = dao.createModuleComponent(addedModule);
                addedModule.setIdseq(newModIdseq);
                Instruction instr = addedModule.getInstruction();
                if (instr != null)
                    moduleInstrdao.createInstruction(instr, newModIdseq);
               List<Question> questions = addedModule.getQuestions();
               if (questions != null) {
                  for (int i=0; i<questions.size(); i++) {
                     questions.get(i).setModule(addedModule);
                  }
                    this.createNewQuestions(questionDAO,questInstrDAO,formVVDAO,
                            formVVInstDAO, questions);
               }
            }
        }
        if ((updatedModules != null) && !updatedModules.isEmpty())
        {
            Iterator updatedIt = updatedModules.iterator();

            while (updatedIt.hasNext())
            {
                Module updatedModule = (Module)updatedIt.next();
                dao.updateDisplayOrder(updatedModule.getModuleIdseq(), updatedModule
                                       .getDisplayOrder(), getUserName());
            }
        }

        //Delete Modules
        if ((deletedModules != null) && !deletedModules.isEmpty())
        {
            Iterator deletedIt = deletedModules.iterator();

            while (deletedIt.hasNext())
            {
                Module deletedModule = (Module)deletedIt.next();
                dao.deleteModule(deletedModule.getModuleIdseq());
            }
        }
        //Update Instructions

        makeInstructionChanges(formInstrdao,
                               instructionChanges.getFormHeaderInstructionChanges());
        makeFooterInstructionChanges(formInstrdao,
                                     instructionChanges.getFormFooterInstructionChanges());

        //update form/protocol association
        addFormProtocols(formIdSeq, addedProtocolIds);
        removeFormProtocols(formIdSeq, removedProtocolIds);
        
        updateTriggerActions((List<TriggerActionChanges>)protocolTriggerActionChanges);
        return getFormDetails(formIdSeq);
    }


    public Form getFormRow(String formPK)
    {
        FormDAO dao = daoFactory.getFormDAO();

        return dao.findFormByPrimaryKey(formPK);
    }

    public Form editFormRow(String formPK) throws DMLException
    {
        return null;
    }

    public int deleteForm(String formPK)
    {
        FormDAO fdao = daoFactory.getFormDAO();

        return fdao.deleteForm(formPK);
    }

    /**
    * @inheritDoc
    */
    public String createModule(Module module, Instruction modInstruction)
    {
        ModuleDAO mdao = daoFactory.getModuleDAO();
        module.setContext(module.getForm().getContext());

        //       module.setProtocol(module.getForm().getProtocol());
        module.setPreferredDefinition(module.getLongName());

        String modulePK = mdao.createModuleComponent(module);
        module.setModuleIdseq(modulePK);

        modInstruction.setContext(module.getForm().getContext());
        modInstruction.setPreferredDefinition(modInstruction.getLongName());

        ModuleInstructionDAO midao = daoFactory.getModuleInstructionDAO();
        midao.createInstruction(modInstruction, modulePK);

        return modulePK;
    }

    public int removeModule(String formPK, String modulePK)
    {
        return 0;
    }

    public Form copyModules(String formPK, Collection modules)
    {
        return null;
    }

    public Form createQuestions(String modulePK, Collection questions)
    {
        return null;
    }

    public Form removeQuestions(String modulePK, Collection questions)
    {
        return null;
    }

    public Form copyQuestions(String modulePK, Collection questions)
    {
        return null;
    }

    public Form createValidValues(String modulePK, Collection validValues)
    {
        return null;
    }

    public Form removeValidValues(String modulePK, Collection validValues)
    {
        return null;
    }

    public Form copyValidValues(String modulePK, Collection validValues)
    {
        return null;
    }

    private String getUserName()
    {
        return context.getCallerPrincipal().getName().toUpperCase();
        //return "JASUR";//jboss
    }


    public Collection getAllContexts()
    {
        return daoFactory.getContextDAO().getAllContexts();
    }

    public Collection getAllFormCategories()
    {
        return daoFactory.getFormCategoryDAO().getAllCategories();
    }

    public Collection getStatusesForACType(String acType)
    {
        return daoFactory.getWorkFlowStatusDAO()
        .getWorkFlowStatusesForACType(acType);
    }

    public boolean validateUser(String username, String password)
    {
        return false;
    }

    public CDECart retrieveCDECart(String user)
    {
        //String user = getUserName();
        CDECartDAO myDAO = daoFactory.getCDECartDAO();
        CDECart cart = myDAO.findCDECart(user);

        return cart;
    }

    public int addToCDECart(Collection items, String user)
    {
        //String user = context.getCallerPrincipal().getName();
        Iterator it = items.iterator();
        CDECartItem item = null;
        CDECartDAO myDAO = daoFactory.getCDECartDAO();
        int count = 0;

        while (it.hasNext())
        {
            item = (CDECartItem)it.next();
            item.setCreatedBy(user.toUpperCase());
            myDAO.insertCartItem(item);
            count++;
        }

        return count;
    }

    public int removeFromCDECart(Collection items, String user)
    {
        Iterator it = items.iterator();
        String itemId = null;
        CDECartDAO myDAO = daoFactory.getCDECartDAO();
        int count = 0;

        while (it.hasNext())
        {
            itemId = (String)it.next();

            myDAO.deleteCartItem(itemId, user);
            count++;
        }

        return count;
    }

    public Form copyForm(String sourceFormPK, Form newForm)
    {
        Form resultForm = null;

        FormDAO myDAO = daoFactory.getFormDAO();
        String resultFormPK = myDAO.copyForm(sourceFormPK, newForm);
        resultForm = this.getFormDetails(resultFormPK);
        return resultForm;
    }

    public int updateDEAssociation(String questionId, String deId,
                                   String newLongName, String username)
    {
        QuestionDAO myDAO = daoFactory.getQuestionDAO();
        int ret =
            myDAO.updateQuestionDEAssociation(questionId, deId, newLongName,
                                                    this.getUserName());

        return ret;
    }

    public Map getValidValues(Collection vdIdSeqs)
    {
        ValueDomainDAO myDAO = daoFactory.getValueDomainDAO();
        Map valueMap = myDAO.getPermissibleValues(vdIdSeqs);

        return valueMap;
    }

    /**
     *
     * @inheritDoc
     */
    public int assignFormClassification(List acIdList, List csCsiIdList)
    {
        //sanity check
        if (acIdList == null || acIdList.size() == 0)
        {
            return 0;
        }

        if (csCsiIdList == null || csCsiIdList.size() == 0)
        {
            return 0;
        }

        FormDAO myDAO = daoFactory.getFormDAO();
        Iterator it = acIdList.iterator();
        int total = 0;
        int ret = 0;
        while (it.hasNext())
        {
            String acId = (String)it.next();
            Iterator it2 = csCsiIdList.iterator();
            while (it2.hasNext())
            {
                String csCsiId = (String)it2.next();
                try
                {
                    ret = myDAO.assignClassification(acId, csCsiId);
                } catch (DMLException dmle)
                {
                    ;
                    //log. ignore this duplicated classification error.
                }
                total += ret;
            }
            //end of while
        }
        //end of while
        return total;
    }

    /**
     *
     * @inheritDoc
     */
    public int removeFormClassification(String cscsiIdseq, String acId)
    {
        FormDAO myDAO = daoFactory.getFormDAO();

        return myDAO.removeClassification(cscsiIdseq, acId);
    }

    public void removeFormClassificationUpdateTriggerActions(String cscsiIdseq, String acId, 
            List<TriggerActionChanges> triggerChangesList){
        updateTriggerActions(triggerChangesList);
        removeFormClassification(cscsiIdseq, acId);
    
    }

    /**
     *
     * @inheritDoc
     */
    public int removeFormClassification(String acCsiId)
    {
        FormDAO myDAO = daoFactory.getFormDAO();

        return myDAO.removeClassification(acCsiId);
    }

    /**
     *
     * @inheritDoc
     */
    public Collection retrieveFormClassifications(String acId)
    {
        FormDAO myDAO = daoFactory.getFormDAO();

        return myDAO.retrieveClassifications(acId);
    }

    /**
     *
     * @inheritDoc
     */
    public Form createForm(Form form, Instruction formHeaderInstruction,
                           Instruction formFooterInstruction)
    {
        FormDAO fdao = daoFactory.getFormDAO();
        String newFormIdseq = fdao.createFormComponent(form);


        if (formHeaderInstruction != null)
        {
            FormInstructionDAO fidao1 = daoFactory.getFormInstructionDAO();
            fidao1.createInstruction(formHeaderInstruction, newFormIdseq);
        }

        if (formFooterInstruction != null)
        {
            FormInstructionDAO fidao2 = daoFactory.getFormInstructionDAO();
            fidao2
            .createFooterInstruction(formFooterInstruction, newFormIdseq);
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
    public void publishForm(String formIdSeq, String formType,
                            String contextIdSeq)
    {
        FormDAO myDAO = daoFactory.getFormDAO();
        Collection schemeItems = null;
        if (formType.equals(PersistenceConstants.FORM_TYPE_CRF))
            schemeItems = myDAO.getPublishingCSCSIsForForm(contextIdSeq);

        if (formType.equals(PersistenceConstants.FORM_TYPE_TEMPLATE))
            schemeItems = myDAO.getPublishingCSCSIsForTemplate(contextIdSeq);

        if (schemeItems == null)
            return;
        Iterator it = schemeItems.iterator();

        while (it.hasNext())
        {
            CSITransferObject cscsi = (CSITransferObject)it.next();
            try
            {
                myDAO.assignClassification(formIdSeq, cscsi.getCsCsiIdseq());

            } catch (DMLException exp)
            {
                //Ignored If already classified
                if (!exp.getErrorCode()
                    .equals(ErrorCodeConstants.ERROR_DUPLICATE_CLASSIFICATION))
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
    public void unpublishForm(String formIdSeq, String formType,
                              String contextIdSeq)
    {
        FormDAO myDAO = daoFactory.getFormDAO();
        Collection schemeItems = null;
        if (formType.equals(PersistenceConstants.FORM_TYPE_CRF))
            schemeItems = myDAO.getPublishingCSCSIsForForm(contextIdSeq);

        if (formType.equals(PersistenceConstants.FORM_TYPE_TEMPLATE))
            schemeItems = myDAO.getPublishingCSCSIsForTemplate(contextIdSeq);

        if (schemeItems == null)
            return;
        Iterator it = schemeItems.iterator();
        while (it.hasNext())
        {
            CSITransferObject cscsi = (CSITransferObject)it.next();
            myDAO.removeClassification(cscsi.getCsCsiIdseq(), formIdSeq);
        }
    }

    private void updateQuestions(QuestionDAO questionDao,
                                 QuestionInstructionDAO questionInstrDao,
                                 FormValidValueDAO fvvDao,
                                 FormValidValueInstructionDAO fvvInstrDao,
                                 List updatedQuestions)
    {
        if (updatedQuestions != null && !updatedQuestions.isEmpty())
        {
            Iterator updatedQChangesIt = updatedQuestions.iterator();
            while (updatedQChangesIt.hasNext())
            {
                QuestionChange currQuestionChange =
                    (QuestionChange)updatedQChangesIt.next();
                if (currQuestionChange != null &&
                    !currQuestionChange.isEmpty())
                {
                    Question currQ = currQuestionChange.getUpdatedQuestion();
                    if (currQ != null)
                    {
                        currQ.setModifiedBy(getUserName());
                        questionDao
                        .updateQuestionLongNameDispOrderDeIdseq(currQ);
                        
                    }
                    
                    if (currQuestionChange.isQuestAttrChange()){
                        questionDao.updateQuestAttr(currQuestionChange, getUserName().toUpperCase());
                    }
                    
                    
                    InstructionChanges qInstrChanges =
                        currQuestionChange.getInstrctionChanges();
                    if (qInstrChanges != null && !qInstrChanges.isEmpty())
                        makeInstructionChanges(questionInstrDao,
                                               qInstrChanges);

                    FormValidValueChanges formVVChanges =
                        currQuestionChange.getFormValidValueChanges();
                    if (formVVChanges != null && !formVVChanges.isEmpty())
                    {
                        createNewValidValues(fvvDao, fvvInstrDao,
                                             formVVChanges.getNewValidValues(),
                                             formVVChanges.getQuestionId());
                        updateValidValues(fvvDao, fvvInstrDao,
                                          formVVChanges.getUpdatedValidValues());
                        deleteValidValues(fvvDao, fvvInstrDao,
                                          formVVChanges.getDeletedValidValues());
                    }

                }
            }
        }
    }

    private void updateValidValues(FormValidValueDAO fvvDao,
                                   FormValidValueInstructionDAO fvvInstrDao,
                                   List updatedValidValues)
    {
        if (updatedValidValues != null && !updatedValidValues.isEmpty())
        {
            Iterator updatedVVChangesIt = updatedValidValues.iterator();
            while (updatedVVChangesIt.hasNext())
            {
                FormValidValueChange currVVChange =
                    (FormValidValueChange)updatedVVChangesIt.next();
                if (currVVChange != null && !currVVChange.isEmpty())
                {
                    FormValidValue currVV =
                        currVVChange.getUpdatedValidValue();
                    if (currVV != null)
                    {
                        currVV.setModifiedBy(getUserName());
                        fvvDao
                        .updateDisplayOrder(currVV.getValueIdseq(), currVV
                                                  .getDisplayOrder(),
                                                  getUserName());                                                
                    }
                    if (currVVChange.getUpdatedFormValueMeaningText()!=null || 
                        currVVChange.getUpdatedFormValueMeaningDesc()!=null){
                        fvvDao.updateValueMeaning(currVVChange.getValidValueId(), 
                            currVVChange.getUpdatedFormValueMeaningText(), 
                            currVVChange.getUpdatedFormValueMeaningDesc(), 
                            getUserName());
                    }
                    InstructionChanges vvInstrChanges =
                        currVVChange.getInstrctionChanges();
                    if (vvInstrChanges != null && !vvInstrChanges.isEmpty())
                        makeInstructionChanges(fvvInstrDao, vvInstrChanges);

                }
            }
        }
    }

    private void deleteValidValues(FormValidValueDAO fvvDao,
                                   FormValidValueInstructionDAO fvvInstrDao,
                                   List deletedValidValues)
    {
        if (deletedValidValues != null && !deletedValidValues.isEmpty())
        {
            Iterator deletedIt = deletedValidValues.iterator();
            while (deletedIt.hasNext())
            {
                FormValidValue currfvv = (FormValidValue)deletedIt.next();
                fvvDao.deleteFormValidValue(currfvv.getValueIdseq());
                //instructions
                Instruction vvInstr = currfvv.getInstruction();
                if (vvInstr != null)
                {
                    fvvInstrDao.deleteInstruction(vvInstr.getIdseq());
                }
            }
        }
    }

    private void createNewValidValues(FormValidValueDAO fvvDao,
                                      FormValidValueInstructionDAO fvvInstrDao,
                                      List newValidValues, String parentId)
    {

        if (newValidValues != null && !newValidValues.isEmpty())
        {

            Iterator newIt = newValidValues.iterator();
            while (newIt.hasNext())
            {
                FormValidValue currfvv = (FormValidValue)newIt.next();
                String newfvvIdseq =
                    fvvDao.createFormValidValueComponent(currfvv, parentId, getUserName());                                
                
                //instructions
                Instruction vvInstr = currfvv.getInstruction();
                if (vvInstr != null)
                {
                    fvvInstrDao.createInstruction(vvInstr, newfvvIdseq);
                }
            }

            // Use the one below onces migrated to 9i driver (Instructions created by procedure)
            //fvvDao.createFormValidValueComponents(newValidValues,parentId);

        }
    }

    private void deleteQuestions(QuestionDAO questionDao,
                                 QuestionInstructionDAO questionInstrDao,
                                 FormValidValueDAO fvvDao,
                                 FormValidValueInstructionDAO fvvInstrDao,
                                 List deletedQuestions)
    {
        if (deletedQuestions != null && !deletedQuestions.isEmpty())
        {
            Iterator deletedIt = deletedQuestions.iterator();
            while (deletedIt.hasNext())
            {
                Question currQuestion = (Question)deletedIt.next();
                questionDao.deleteQuestion(currQuestion.getQuesIdseq());                
                //instructions
                Instruction qInstr = currQuestion.getInstruction();
                if (qInstr != null)
                {
                    questionInstrDao.deleteInstruction(qInstr.getIdseq());
                }

                List currQuestionValidValues = currQuestion.getValidValues();
                if (currQuestionValidValues != null)
                {
                    ListIterator currQuestionValidValuesIt =
                        currQuestionValidValues.listIterator();
                    while (currQuestionValidValuesIt != null &&
                           currQuestionValidValuesIt.hasNext())
                    {
                        FormValidValue fvv =
                            (FormValidValue)currQuestionValidValuesIt.next();
                        fvvDao.deleteFormValidValue(fvv.getValueIdseq());
                        //instructions
                        Instruction vvInstr = fvv.getInstruction();
                        if (vvInstr != null)
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
    Context questionContext = null;
        if (newQuestions != null && !newQuestions.isEmpty())
        {
            Iterator newIt = newQuestions.iterator();
            while (newIt.hasNext())
            {
                Question currQuestion = (Question)newIt.next();
                questionContext = currQuestion.getContext();
                currQuestion.setCreatedBy(getUserName());
                if (currQuestion.getVersion() == null)
                  currQuestion.setVersion(new Float(1.0));
                Question newQusetion =
                    questionDao.createQuestionComponent(currQuestion);
                //instructions
                Instruction qInstr = currQuestion.getInstruction();
                if (qInstr != null)
                {
                    qInstr.setCreatedBy(getUserName());
                    questionInstrDao
                    .createInstruction(qInstr, newQusetion.getQuesIdseq());
                }
                List currQuestionValidValues = currQuestion.getValidValues();

                if (currQuestionValidValues != null)
                {

                    ListIterator currQuestionValidValuesIt =
                        currQuestionValidValues.listIterator();
                    while (currQuestionValidValuesIt != null &&
                           currQuestionValidValuesIt.hasNext())
                    {
                        FormValidValue fvv =
                            (FormValidValue)currQuestionValidValuesIt.next();
                        fvv.setCreatedBy(getUserName());
                        fvv.setQuestion(newQusetion);
                        String newFVVIdseq =
                            fvvDao.createFormValidValueComponent(
                                    fvv,newQusetion.getQuesIdseq(),getUserName());
                        //createFormValidValueComponent() will 
                        //create valid value and record in the valid_value_att_ext 
                        
                        //instructions
                        Instruction vvInstr = fvv.getInstruction();
                        if (vvInstr != null)
                        {
                            vvInstr.setCreatedBy(getUserName());
                            vvInstr.setContext(questionContext);
                            fvvInstrDao
                            .createInstruction(vvInstr, newFVVIdseq);
                        }
                    }

                    //fvvDao.createFormValidValueComponents(currQuestionValidValues,newQusetion.getQuesIdseq());
                }
            }
        }
    }

    private void makeInstructionChanges(InstructionDAO dao,
                                        InstructionChanges changes)
    {
        //Create new ones
        Instruction newInstr = changes.getNewInstruction();
        if (newInstr != null)
        {
            newInstr.setCreatedBy(getUserName());
            dao.createInstruction(newInstr, changes.getParentId());
        }
        //update
        Instruction updatedInstr = changes.getUpdatedInstruction();
        if (updatedInstr != null)
        {
            updatedInstr.setModifiedBy(getUserName());
            dao.updateInstruction(updatedInstr);

        }
        //delete
        Instruction deleteInstr = changes.getDeletedInstruction();
        if (deleteInstr != null)
        {
            dao.deleteInstruction(deleteInstr.getIdseq());
        }

    }

    private void makeInstructionChanges(InstructionDAO dao, Map changesMap)
    {
        //Create new ones
        Map newInstrs =
            (Map)changesMap.get(FormInstructionChanges.NEW_INSTRUCTION_MAP);
        if (newInstrs != null && !newInstrs.isEmpty())
        {
            Set keySet = newInstrs.keySet();
            Iterator keyIt = keySet.iterator();
            while (keyIt.hasNext())
            {
                String parentIdSeq = (String)keyIt.next();
                Instruction instr = (Instruction)newInstrs.get(parentIdSeq);
                instr.setCreatedBy(getUserName());
                dao.createInstruction(instr, parentIdSeq);
            }
        }
        //update
        List updatedInstrs =
            (List)changesMap.get(FormInstructionChanges.UPDATED_INSTRUCTIONS);
        if (updatedInstrs != null && !updatedInstrs.isEmpty())
        {
            ListIterator updatedInstrIt = updatedInstrs.listIterator();
            while (updatedInstrIt.hasNext())
            {
                Instruction instr = (Instruction)updatedInstrIt.next();
                instr.setModifiedBy(getUserName());
                dao.updateInstruction(instr);
            }
        }
        //delete
        List deleteInstrs =
            (List)changesMap.get(FormInstructionChanges.DELETED_INSTRUCTIONS);
        if (deleteInstrs != null && !deleteInstrs.isEmpty())
        {
            ListIterator deleteInstrIt = deleteInstrs.listIterator();
            while (deleteInstrIt.hasNext())
            {
                Instruction instr = (Instruction)deleteInstrIt.next();
                dao.deleteInstruction(instr.getIdseq());
            }
        }
    }

    private void makeFooterInstructionChanges(FormInstructionDAO dao,
                                              Map changesMap)
    {
        //Create new ones
        Map newInstrs =
            (Map)changesMap.get(FormInstructionChanges.NEW_INSTRUCTION_MAP);
        if (newInstrs != null && !newInstrs.isEmpty())
        {
            Set keySet = newInstrs.keySet();
            Iterator keyIt = keySet.iterator();
            while (keyIt.hasNext())
            {
                String parentIdSeq = (String)keyIt.next();
                Instruction instr = (Instruction)newInstrs.get(parentIdSeq);
                instr.setCreatedBy(getUserName());
                dao.createFooterInstruction(instr, parentIdSeq);
            }
        }
        //update
        List updatedInstrs =
            (List)changesMap.get(FormInstructionChanges.UPDATED_INSTRUCTIONS);
        if (updatedInstrs != null && !updatedInstrs.isEmpty())
        {
            ListIterator updatedInstrIt = updatedInstrs.listIterator();
            while (updatedInstrIt.hasNext())
            {
                Instruction instr = (Instruction)updatedInstrIt.next();
                instr.setModifiedBy(getUserName());
                dao.updateInstruction(instr);
            }
        }
        //delete
        List deleteInstrs =
            (List)changesMap.get(FormInstructionChanges.DELETED_INSTRUCTIONS);
        if (deleteInstrs != null && !deleteInstrs.isEmpty())
        {
            ListIterator deleteInstrIt = deleteInstrs.listIterator();
            while (deleteInstrIt.hasNext())
            {
                Instruction instr = (Instruction)deleteInstrIt.next();
                dao.deleteInstruction(instr.getIdseq());
            }
        }
    }

    public ReferenceDocument createReferenceDocument(ReferenceDocument refDoc,
                                                     String acIdseq)
    {
        ReferenceDocumentDAO myDAO = daoFactory.getReferenceDocumentDAO();
        return myDAO.createReferenceDoc(refDoc, acIdseq);

    }

    private List<ReferenceDocument> getReferenceDocuments(String acIdseq)
    {
        ReferenceDocumentDAO myDAO = (ReferenceDocumentDAO)daoFactory.getReferenceDocumentDAO();
        List refDocs = myDAO.getAllReferenceDocuments(acIdseq, 
                        null);
        return refDocs;        
    }

    public void deleteReferenceDocument(String rdIdseq)
    {
        ReferenceDocumentDAO myDAO = daoFactory.getReferenceDocumentDAO();
        myDAO.deleteReferenceDocument(rdIdseq);

    }

    public void updateReferenceDocument(ReferenceDocument refDoc)
    {
        ReferenceDocumentDAO myDAO = daoFactory.getReferenceDocumentDAO();
        myDAO.updateReferenceDocument(refDoc);

    }

    public void deleteAttachment(String name)
    {
        ReferenceDocumentDAO myDAO = daoFactory.getReferenceDocumentDAO();
        myDAO.deleteAttachment(name);

    }

    public Collection getAllDocumentTypes()
    {
        return daoFactory.getReferenceDocumentTypeDAO().getAllDocumentTypes();
    }

    public int saveDesignation(String contextIdSeq, List acIdList)
    {
        return daoFactory.getFormDAO().designate(contextIdSeq, acIdList, getUserName());
    }
    
    public Boolean isAllACDesignatedToContext(List cdeIdList , String contextIdSeq){
        FormDAO dao = daoFactory.getFormDAO();
        boolean ret = dao.isAllACDesignatedToContext(cdeIdList, contextIdSeq);        
        return new Boolean(ret);
    }


    public String createNewFormVersion(String formIdSeq,
                                       Float newVersionNumber,
                                       String changeNote)
    {
        FormDAO myDAO = daoFactory.getFormDAO();
        String resultFormPK = myDAO.createNewFormVersion(formIdSeq, newVersionNumber, changeNote, getUserName().toUpperCase());

        resultFormPK = resultFormPK.substring(0,36);
        //Form resultForm = this.getFormDetails(resultFormPK);
        
        return resultFormPK;
    }

    public List getFormVersions(int publicId)
    {
        FormDAO myDAO = daoFactory.getFormDAO();
        List versions = myDAO.getFormVersions(publicId);
        return versions;
    }

    public void setLatestVersion(Version oldVersion, Version newVersion, List changedNoteList)
    {
        FormDAO myDAO = daoFactory.getFormDAO();
        myDAO.setLatestVersion(oldVersion, newVersion, changedNoteList, getUserName());
        return;
    }

    public Float getMaxFormVersion(int publicId){
        FormDAO myDAO = daoFactory.getFormDAO();
        Float maxVersion = myDAO.getMaxFormVersion(publicId);
        return maxVersion;
    }
    public void removeFormProtocol(String formIdseq, String protocoldIdseq)
    {
        FormDAO myDAO = daoFactory.getFormDAO();
        myDAO.removeFormProtocol(formIdseq, protocoldIdseq);
    }
    
    public void  removeTriggerActionsChanges(Collection<TriggerActionChanges> triggerActionChanges){
        if (triggerActionChanges==null || triggerActionChanges.isEmpty()){
            return;
        }
        TriggerActionDAO dao = daoFactory.getTriggerActionDAO();
        Iterator it = triggerActionChanges.iterator();
        while (it.hasNext()){
            TriggerActionChanges taChanges = (TriggerActionChanges)it.next();
            List protocolIds = taChanges.getDeleteProtocols();
            List classificationIds = taChanges.getDeleteCsis();
            if (protocolIds != null &&  !protocolIds.isEmpty()){
                for (Object pid : protocolIds){
                    dao.deleteTriggerActionProtocol(taChanges.getTriggerActionId(),(String)pid);    
                }
            }
            
            if (classificationIds != null && !classificationIds.isEmpty()){
                for (Object cid:classificationIds){
                    dao.deleteTriggerActionCSI(taChanges.getTriggerActionId(), (String)cid);    
                }
            }
            
        }
    }

    public void removeFormProtocols(String formIdseq, Collection protocolIds)
    {
        if (protocolIds == null || protocolIds.isEmpty())
        {
            return;
        }
        FormDAO myDAO = daoFactory.getFormDAO();
        myDAO.removeFormProtocols(formIdseq, protocolIds);
        return;
    }

    public void addFormProtocol(String formIdseq, String protocoldIdseq)
    {
        //sanity check
        if (protocoldIdseq==null || protocoldIdseq.length()==0){            
            return;
        }
        
        if (formIdseq==null || formIdseq.length()==0){
            return;
        }
        
        FormDAO myDAO = daoFactory.getFormDAO();
        myDAO.addFormProtocol(formIdseq, protocoldIdseq, getUserName());
        return;
    }

    public void addFormProtocols(String formIdseq, Collection protocols)
    {
        if (protocols == null || protocols.isEmpty())
        {
            return;
        }
        
        Iterator it = protocols.iterator();
        while (it.hasNext())
        {
            String pid = (String)it.next();
            if (pid.length()>0){
                addFormProtocol(formIdseq, pid);
            }    
        }
        return;
    }

    public Protocol getProtocolByPK(String protocoldIdseq)
    {
        ProtocolDAO dao = daoFactory.getProtocolDAO();
        return dao.getProtocolByPK(protocoldIdseq);

    }

    
    public List<TriggerAction> getAllTriggerActionsForSource(String sourceId)
    {
        TriggerActionDAO dao = daoFactory.getTriggerActionDAO();
        List<TriggerAction> triggerActions = dao.getTriggerActionsForSource(sourceId);
        for(TriggerAction action:triggerActions)
        {
            action.setProtocols(dao.getAllProtocolsForTriggerAction(action.getIdSeq()));
            action.setClassSchemeItems(dao.getAllClassificationsForTriggerAction(action.getIdSeq()));
        }
        return triggerActions;
    }

    public List<TriggerAction> getAllTriggerActionsForTarget(String targetId)
    {
        TriggerActionDAO dao = daoFactory.getTriggerActionDAO();
        List<TriggerAction> triggerActions = dao.getTriggerActionsForTarget(targetId);

        return triggerActions;
    }
    
    public List<TriggerAction> getAllTriggerActionsForTargets(List<String> targetIds)
    {
        TriggerActionDAO dao = daoFactory.getTriggerActionDAO();
        List<TriggerAction> triggerActions = new ArrayList<TriggerAction>();
        for(String id:targetIds)
        {
            List<TriggerAction> currTriggerActions = dao.getTriggerActionsForTarget(id);
            triggerActions.addAll(currTriggerActions);
        }

        return triggerActions;
    }
    
    public boolean isTargetForTriggerAction(List<String> targetIds)
    {
        TriggerActionDAO dao = daoFactory.getTriggerActionDAO();
        for(String id:targetIds)
        {
            if(dao.isTargetForTriggerAction(id))
                return true;
        }

        return false;
    }
    
    public TriggerAction createTriggerAction(TriggerAction action)
    {
        TriggerActionDAO dao = daoFactory.getTriggerActionDAO();
        String newId = dao.createTriggerAction(action,getUserName().toUpperCase());
        
        //create protocols and classifications
        List protocols = action.getProtocols();
        if (protocols!=null && !protocols.isEmpty()){
            for (Object obj : protocols){
                dao.addTriggerActionProtocol(newId, ((Protocol)obj).getProtoIdseq(), getUserName().toUpperCase());
            }    
        }
        List csiList = action.getClassSchemeItems();
        if (csiList!=null && !csiList.isEmpty()){        
            for (Object obj : csiList){
                dao.addTriggerActionCSI(newId, ((ClassSchemeItem)obj).getAcCsiIdseq(), getUserName().toUpperCase());
            }    
        }
        return getTriggerActionForPK(newId);

    }

    public TriggerAction updateTriggerAction(TriggerActionChanges changes)
    {
        TriggerActionDAO dao = daoFactory.getTriggerActionDAO();
        String userId = getUserName().toUpperCase();
        String triggetId = changes.getTriggerActionId();
       if(changes.getNewInstruction()!=null)
       {

           dao.updateTriggerActionInstruction(triggetId,
                        changes.getNewInstruction(),userId);          
       }
        if(changes.getNewTargetId()!=null)
        {

            dao.updateTriggerActionTarget(triggetId,
                         changes.getNewTargetId(),userId);          
        }      
        
        if(changes.getAddProtocols()!=null&&!changes.getAddProtocols().isEmpty())
        {
            for(String protocolId: changes.getAddProtocols())
            {
                dao.addTriggerActionProtocol(triggetId,protocolId,userId);
            }                  
        }     
        if(changes.getAddCsis()!=null&&!changes.getAddCsis().isEmpty())
        {
            for(String accsiId: changes.getAddCsis())
            {
                dao.addTriggerActionCSI(triggetId,accsiId,userId);
            }                  
        }    
        
        if(changes.getDeleteCsis()!=null&&!changes.getDeleteCsis().isEmpty())
        {
            for(String accsiId: changes.getDeleteCsis())
            {
                dao.deleteTriggerActionCSI(triggetId,accsiId);
            }                  
        }    
        if(changes.getDeleteProtocols()!=null&&!changes.getDeleteProtocols().isEmpty())
        {
            for(String protoId: changes.getDeleteProtocols())
            {
                dao.deleteTriggerActionProtocol(triggetId,protoId);
            }                  
        }            
        return getTriggerActionForPK(triggetId);
        
    }

    public void updateTriggerActions(
       List<TriggerActionChanges> changesList){
           if (changesList==null || changesList.isEmpty()){
               return;
           }
           
           for (TriggerActionChanges changes:changesList){
               updateTriggerAction(changes);               
           }
       }

    public void deleteTriggerAction(String triggerActionId)
    {
        TriggerActionDAO dao = daoFactory.getTriggerActionDAO();
        dao.deleteTriggerActionCSIProtocols(triggerActionId);
        dao.deleteTriggerAction(triggerActionId);
    }
    
    public Module saveQuestionRepititons(String moduleId,int repeatCount
    , Map<String,List<QuestionRepitition>> repititionMap,
    List<String> questionWithoutRepitions)
    {
        QuestionRepititionDAO dao = daoFactory.getQuestionRepititionDAO();
        String userId = getUserName().toUpperCase();
        
        if(questionWithoutRepitions!=null&&!questionWithoutRepitions.isEmpty())
        {
            Iterator<String> lit = questionWithoutRepitions.iterator();
            while(lit.hasNext())
            {
                String key = lit.next();
                dao.deleteRepititionsForQuestion(key);
            }   
        }
        if(repititionMap!=null &&!repititionMap.isEmpty())
        {
            Set<String> keys = repititionMap.keySet();
            Iterator<String>  it = keys.iterator();
            while(it.hasNext())
            {
                String key = it.next();
                dao.deleteRepititionsForQuestion(key);
            }
        }
        dao.updateModuleRepeatCount(moduleId,repeatCount,userId);
        if(repititionMap!=null &&!repititionMap.isEmpty())
        {
            Set keys = repititionMap.keySet();
            Iterator<String> it = keys.iterator();
            while(it.hasNext())
            {
                String key = it.next();
                List<QuestionRepitition> qrList = repititionMap.get(key);
                for(QuestionRepitition questionRep:qrList)
                {
                    dao.createRepitition(key,questionRep,userId);
                }
            }
        } 
         return getModule(moduleId);
    }
    
    /**
     * Get FormValidValue from the question and assign it to Question repitition.
     * This is done since the Question repitition quesry only retrives ids
     * @param qreps
     * @param values
     */
    private void setQuestionRepititionDefaults(List<QuestionRepitition> qreps,List values)
    {
        for(QuestionRepitition repitition:qreps)
        {
            if(repitition.getDefaultValidValue()!=null)
            {
              if(values!=null)
              {
                  Iterator it = values.iterator();
                  FormValidValue value =null;
                  while(it.hasNext())
                  {
                       value = (FormValidValue)it.next();
                       if(value.getValueIdseq().equals(repitition.getDefaultValidValue().getValueIdseq()))
                       {
                           repitition.setDefaultValidValue(value);
                       }
                  }
                  
              }
              
            }
        }
    }
    private void setSourceForTriggerActions(FormElement source, List<TriggerAction> actions)
    {
        for(TriggerAction action : actions)
        {
            action.setActionSource(source);

        }
    }

    private void setTargetsForTriggerActions(Map<String,FormElement> possibleTargetMap, List<TriggerAction> actions)
    {
        for(TriggerAction action : actions)
        {
            FormElement element = possibleTargetMap.get(action.getActionTarget().getIdseq());
            // the target FormElement may not may not in the possibleTargetMap considering it may
            //skip to a FormElement in a different module for example.
            //In case the target FormElement is not in the possibleTargetMap, element will be null,
            //In this case just keep the idseq which is already in the target action.
            if (element!=null){
                action.setActionTarget(element);
            }    

        }
    }
    private TriggerAction getTriggerActionForPK(String triggerId)
    {
        TriggerActionDAO dao = daoFactory.getTriggerActionDAO();
        TriggerAction newAction = dao.getTriggerActionForId(triggerId);
        newAction.setClassSchemeItems(dao.getAllClassificationsForTriggerAction(triggerId));
        newAction.setProtocols(dao.getAllProtocolsForTriggerAction(triggerId));        
        return newAction;
    }
    
    private Question setQuestionDefaultValue(Question term ){
        QuestionDAO dao = daoFactory.getQuestionDAO();
        term = dao.getQuestionDefaultValue(term);
        return term;
    }

    public List getRreferenceDocuments(String acId) {
        FormDAO mDAO = daoFactory.getFormDAO();
        return mDAO.getAllReferenceDocuments(acId, null);//docType is not in use
    }
    


}
