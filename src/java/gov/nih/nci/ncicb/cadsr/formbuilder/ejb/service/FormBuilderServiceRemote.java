package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
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
import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;
import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.resource.TriggerActionChanges;
import gov.nih.nci.ncicb.cadsr.resource.Version;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface FormBuilderServiceRemote {
    public Collection getAllForms(String formLongName, String protocolIdSeq,
        String contextIdSeq, String workflow, String categoryName, String type,
        String classificationIdSeq,
        String publicId, String version, String moduleLongName, String cdePublicId,
        NCIUser user) throws RemoteException;

    public Form getFormDetails(String formPK) throws RemoteException;

    public Form updateForm(String formIdSeq, Form formHeader, Collection updatedModules,
        Collection deletedModules,Collection addedModules,
        Collection addedProtocols, Collection removedProtocols,
        Collection protocolTriggerActionChanges,
        FormInstructionChanges instructionChanges) throws RemoteException;

    public Module updateModule(String moduleIdSeq,ModuleChanges moduleChanges) throws RemoteException;

    public Form getFormRow(String formPK) throws RemoteException;

    public Form copyForm(String sourceFormPK, Form newForm)
        throws RemoteException;

    public Form editFormRow(String formPK) throws RemoteException;

    public int deleteForm(String formPK) throws RemoteException;

    /**
     * Creates a Module
     *
     * @param module a <code>Module</code> value
     * @param modInstrustion a <code>ModuleInstruction</code> value
     *
     * @return The PK of the newly created Module
     *
     * @exception RemoteException if an error occurs
     */
    public String createModule(Module module, Instruction modInstrustion)
        throws RemoteException;

    public int removeModule(String formPK, String modulePK)
        throws RemoteException;

    public Form copyModules(String formPK, Collection modules)
        throws RemoteException;

    public Form createQuestions(String modulePK, Collection questions)
        throws RemoteException;

    public Form removeQuestions(String modulePK, Collection questions)
        throws RemoteException;

    public Form copyQuestions(String modulePK, Collection questions)
        throws RemoteException;

    public Form createValidValues(String modulePK, Collection validValues)
        throws RemoteException;

    public Form removeValidValues(String modulePK, Collection validValues)
        throws RemoteException;

    public Form copyValidValues(String modulePK, Collection validValues)
        throws RemoteException;

    public Collection getAllContexts() throws RemoteException;

    public Collection getAllFormCategories() throws RemoteException;

    public Collection getStatusesForACType(String acType)
        throws RemoteException;

    public boolean validateUser(String username, String password)
        throws RemoteException;

    public CDECart retrieveCDECart(String userName) throws RemoteException;

    public int addToCDECart(Collection items,String userName) throws RemoteException;

    public int removeFromCDECart(Collection items,String userName) throws RemoteException;

    public int updateDEAssociation(String questionId, String deId,
        String newLongName, String username) throws RemoteException;

    public Map getValidValues(Collection vdIdSeqs) throws RemoteException;

    /**
     * Assigns the specified classification to an admin component
     *
     * @param <b>acId</b> Idseq of an admin component
     * @param <b>csCsiId</b> csCsiId
     * @param <b>csCDEIndicator</b>if the CDES on this form should be classified as well.
     *
     * @return <b>int</b> 1 - success; 0 - failure
     */
    public int assignFormClassification(List acIdList, List csCsiIdList)
        throws RemoteException;

    /**
     * Removes the specified classification assignment for an admin component
     *
     * @param <b>acCsiId</b> acCsiId
     *
     * @return <b>int</b> 1 - success; 0 - failure
     */
    public int removeFormClassification(String acCsiId)
        throws RemoteException;

    public int removeFormClassification(String cscsiIdseq, String acId)
        throws RemoteException;
    public void removeFormClassificationUpdateTriggerActions(
        String cscsiId,  String acIdSeq, List<TriggerActionChanges> triggerChangesList)
        throws RemoteException;

    /**
     * Retrieves all the assigned classifications for an admin component
     *
     * @param <b>acId</b> Idseq of an admin component
     *
     * @return <b>Collection</b> Collection of CSITransferObject
     */
    public Collection retrieveFormClassifications(String acId)
        throws RemoteException;

    public Form createForm(Form form, Instruction formHeaderInstruction,
        Instruction formFooterInstruction)
	    throws RemoteException;

    //Publish Change Order
    public Collection getAllPublishedFormsForProtocol(String protocolIdSeq) throws RemoteException;
    //Publish Change Order
    public Collection getAllFormsForClassification(String classificationIdSeq) throws RemoteException;
    /**
     * Publishes the form by assigning publishing classifications to the form
     *
     * @inheritDoc
     */
    public void publishForm(String formIdSeq,String formType, String contextIdSeq) throws RemoteException;

    //Publish Change Order
    /**
     * Removes the publishing classifications assigned to this form
     * @inheritDoc
     */
      public void unpublishForm(String formIdSeq, String formType, String contextIdSe) throws RemoteException;

      public ReferenceDocument createReferenceDocument (ReferenceDocument refDoc, String acIdseq)throws RemoteException;

      public void deleteReferenceDocument (String rdIdseq) throws RemoteException;

      public void updateReferenceDocument (ReferenceDocument refDoc) throws RemoteException;

      public void deleteAttachment (String name) throws RemoteException;

      public Collection getAllDocumentTypes() throws RemoteException;

      public int saveDesignation(String contextIdSeq, List acIdList) throws RemoteException;
      
      public Boolean isAllACDesignatedToContext(List cdeIdList , String contextIdSeq) throws RemoteException;
        
      public String createNewFormVersion(String formIdSeq, Float newVersionNumber, String changeNote)
        throws RemoteException;

      public List getFormVersions(int publicId)    throws RemoteException;

      public void setLatestVersion(Version oldVersion, Version newVersion, List changedNoteList)
        throws RemoteException;
      public Float getMaxFormVersion(int publicId)
        throws RemoteException;
      public void removeFormProtocol(String formIdseq, String protocoldIdseq)
            throws RemoteException;
      public void removeFormProtocols(String formIdseq, Collection protocolds)
            throws RemoteException;
      public void addFormProtocol(String formIdseq, String protocoldIdseq)
            throws RemoteException;
      public void addFormProtocols(String formIdseq, Collection protocolds)
              throws RemoteException;

      public Protocol getProtocolByPK(String protocoldIdseq)
            throws RemoteException;
      public List getAllTriggerActionsForSource(String sourceId)
        throws RemoteException;
    
     public List getAllTriggerActionsForTarget(String targetId)
       throws RemoteException;
       
     public List<TriggerAction> getAllTriggerActionsForTargets(List<String> targetIds)
        throws RemoteException;
     
     public boolean isTargetForTriggerAction(List<String> targetIds)
     throws RemoteException;
     
     public TriggerAction createTriggerAction(TriggerAction action)
            throws RemoteException;

     public TriggerAction updateTriggerAction(TriggerActionChanges changes)
              throws RemoteException;
     public void updateTriggerActions(
        List<TriggerActionChanges> changesList)
                throws RemoteException;

     public void deleteTriggerAction(String triggerActionId)
                throws RemoteException;

    public List getRreferenceDocuments(String acId) 
                throws RemoteException;
                
    public Module saveQuestionRepititons(String moduleId,int repeatCount
            , Map<String,List<QuestionRepitition>> repititionMap,List<String> questionWithoutRepitions)
            throws RemoteException;
                
    }
