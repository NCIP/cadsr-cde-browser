package gov.nih.nci.ncicb.cadsr.formbuilder.service;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormInstructionChanges;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.resource.InstructionChanges;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.ModuleChanges;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.QuestionRepitition;
import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;
import gov.nih.nci.ncicb.cadsr.resource.TriggerActionChanges;
import gov.nih.nci.ncicb.cadsr.resource.Version;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface FormBuilderServiceDelegate {
    public Collection getAllForms(String formName, String protocol,
        String context, String workflow, String category, String type,
        String classificationIdSeq,
        String publicId, String version, String moduleLongName, String cdePublicId,
        NCIUser user);

    //Publish Change Order
    public Collection getAllFormsForClassification(String classificationIdSeq);

    public Collection getAllPublishedFormsForProtocol(String protocolIdSeq);

    public Form getFormDetails(String formPK) throws FormBuilderException;

    public Form updateForm(String formIdSeq, Form formHeader, Collection updatedModules,
        Collection deletedModules,Collection addedModules,
        Collection addedProtocols, Collection removedProtocols,
        Collection protocolTriggerActionChanges,
        FormInstructionChanges instructionChanges) throws FormBuilderException;

    public Module updateModule(String moduleIdSeq, ModuleChanges moduleChanges) throws FormBuilderException;

    public Form getFormRow(String formPK) throws FormBuilderException;

    public Form copyForm(String sourceFormPK, Form newForm)
        throws FormBuilderException;

    public Form editFormRow(String formPK) throws FormBuilderException;

    public int deleteForm(String formPK) throws FormBuilderException;

    public String createModule(Module module,
        Instruction moduleInstruction) throws FormBuilderException;

    public int removeModule(String formPK, String modulePK)
        throws FormBuilderException;

    public Form copyModules(String formPK, Collection modules)
        throws FormBuilderException;

    public Form createQuestions(String modulePK, Collection questions)
        throws FormBuilderException;

    public Form removeQuestions(String modulePK, Collection questions)
        throws FormBuilderException;

    public Form copyQuestions(String modulePK, Collection questions)
        throws FormBuilderException;

    public Form createValidValues(String modulePK, Collection validValues)
        throws FormBuilderException;

    public Form removeValidValues(String modulePK, Collection validValues)
        throws FormBuilderException;

    public Form copyValidValues(String modulePK, Collection validValues)
        throws FormBuilderException;

    public Collection getAllContexts();

    public Collection getAllFormCategories();

    public Collection getStatusesForACType(String acType);

    public boolean validateUser(String username, String password)
        throws FormBuilderException;

    public CDECart retrieveCDECart(String userName) throws FormBuilderException;

    public int addToCDECart(Collection items,String userName) throws FormBuilderException;

    public int removeFromCDECart(Collection items,String userName) throws FormBuilderException;

    public int updateDEAssociation(String questionId, String deId,
        String newLongName, String username) throws FormBuilderException;

    public Map getValidValues(Collection vdIdSeqs) throws FormBuilderException;

    public int assignFormClassification(List acIdList, List csCsiIdList)
        throws FormBuilderException;

    public int removeFormClassification(String acCsiId)
        throws FormBuilderException;
    public int removeFormClassification(String cscsiIdseq, String acId)
        throws FormBuilderException;
        
    public Collection retrieveFormClassifications(String acId)
        throws FormBuilderException;

    public Form createForm(Form form, Instruction formHeaderInstruction,
        Instruction formFooterInstruction)
        throws FormBuilderException;

    //Publish Change Order
    public void publishForm(String formIdSeq,String formType, String contextIdSeq) throws FormBuilderException;

    //Publish Change Order
    public void unpublishForm(String formIdSeq, String formType, String contextIdSeq) throws FormBuilderException;

    public ReferenceDocument createReferenceDocument (ReferenceDocument refDoc, String acIdseq) throws FormBuilderException;

    public void deleteReferenceDocument (String rdIdseq) throws FormBuilderException;

    public void updateReferenceDocument (ReferenceDocument refDoc) throws FormBuilderException;

    public void deleteAttachment (String name) throws FormBuilderException;

    public Collection getAllDocumentTypes() ;

    public int saveDesignation(String contextIdSeq, List acIdList) throws FormBuilderException;
    
    public Boolean isAllACDesignatedToContext(List cdeIdList , String contextIdSeq) throws FormBuilderException;

    public String createNewFormVersion(String formIdSeq, Float newVersionNumber, String changeNote)
        throws FormBuilderException;
    public List getFormVersions(int publicId)
        throws FormBuilderException;

    public void setLatestVersion(Version oldVersion, Version newVersion, List changedNoteList)
        throws FormBuilderException;
    public Float getMaxFormVersion(int publicId)
        throws FormBuilderException;
        
    public void removeFormProtocol(String formIdseq, String protocoldIdseq)
        throws FormBuilderException;
    public void addFormProtocol(String formIdseq, String protocoldIdseq)
        throws FormBuilderException;
    public void addFormProtocols(String formIdseq, Collection protocoldIds)
        throws FormBuilderException;
    public void removeFormProtocols(String formIdseq, Collection protocoldIds)
        throws FormBuilderException;

    public Protocol getProtocolByPK(String protocoldIdseq)
        throws FormBuilderException;

    public List getAllTriggerActionsForSource(String sourceId)
        throws FormBuilderException;

    public List<TriggerAction> getAllTriggerActionsForTarget(String targetId)
        throws FormBuilderException;
        
    public List<TriggerAction> getAllTriggerActionsForTargets(List<String> targetIds)
    throws FormBuilderException;
    
    public boolean isTargetForTriggerAction(List<String> targetIds)
    throws FormBuilderException;    

    
    public TriggerAction createTriggerAction(TriggerAction action)
           throws FormBuilderException;

    public TriggerAction updateTriggerAction(TriggerActionChanges changes)
             throws FormBuilderException;
    public void updateTriggerActions(List<TriggerActionChanges> changesList)
             throws FormBuilderException;
             
    public void removeFormClassificationUpdateTriggerActions(String cscsiIdseq, 
            String acId, List<TriggerActionChanges> triggerChangesList)             
            throws FormBuilderException;
    
    public void deleteTriggerAction(String triggerActionId)
                throws FormBuilderException;
    public List getRreferenceDocuments(String acId)                
                throws FormBuilderException;
                
    public Module saveQuestionRepititons(String moduleId,int repeatCount
            , Map<String,List<QuestionRepitition>> repititionMap,
            List<String> questionWithoutRepitions)
                throws FormBuilderException;              

}
