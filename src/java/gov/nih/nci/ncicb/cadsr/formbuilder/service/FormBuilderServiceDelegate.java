package gov.nih.nci.ncicb.cadsr.formbuilder.service;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.resource.InstructionChanges;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;
import java.util.Collection;
import java.util.Map;


public interface FormBuilderServiceDelegate {
    public Collection getAllForms(String formName, String protocol,
        String context, String workflow, String category, String type,
        String classificationIdSeq,NCIUser user);

    //Publish Change Order
    public Collection getAllFormsForClassification(String classificationIdSeq);

    public Collection getAllPublishedFormsForProtocol(String protocolIdSeq);

    public Form getFormDetails(String formPK) throws FormBuilderException;

    public Form updateForm(String formIdSeq, Form formHeader, Collection updatedModules,
        Collection deletedModules,Collection addedModules
         ,InstructionChanges instructionChanges) throws FormBuilderException;

    public Module updateModule(String moduleIdSeq, Module moduleHeader,
        Collection updatedQuestions, Collection deletedQuestions,
        Collection newQuestions, Map updatedValidValues, Map addedValidValues,
        Map deletedValidValues,InstructionChanges instructionChanges) throws FormBuilderException;

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

    public CDECart retrieveCDECart() throws FormBuilderException;

    public int addToCDECart(Collection items) throws FormBuilderException;

    public int removeFromCDECart(Collection items) throws FormBuilderException;

    public int updateDEAssociation(String questionId, String deId,
        String newLongName, String username) throws FormBuilderException;

    public Map getValidValues(Collection vdIdSeqs) throws FormBuilderException;

    public int assignFormClassification(String acId, String csCsiId)
        throws FormBuilderException;

    public int removeFormClassification(String acCsiId)
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

}
