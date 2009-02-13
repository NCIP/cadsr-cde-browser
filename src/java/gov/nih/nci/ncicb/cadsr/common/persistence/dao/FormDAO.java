package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.Module;

import gov.nih.nci.ncicb.cadsr.common.resource.Version;

import java.util.Collection;
import java.util.List;


public interface FormDAO extends AdminComponentDAO {
  public Collection getAllForms(
    String formLongName,
    String protocolIdSeq,
    String contextIdSeq,
    String workflow,
    String categoryName,
    String type,
    String classificationIdseq,
    String contextRestriction,
    String publicId,
    String version,
    String moduleLongName,
    String cdePublicId);

//Publish Change Order
  /**
   * Gets all the forms that has been classified by this Classification
   *
   * @param <b>formId</b> Idseq of the Classification
   *
   * @return <b>Collection</b> List of Forms
   */
  public Collection getAllFormsForClassification(String classificationIdSeq);
 
 //Publis Change Order
  /**
   * Gets all the forms that has been classified by this Classification
   *
   * @param <b>formId</b> Idseq of the Classification
   *
   * @return <b>Collection</b> List of Forms
   */
  public List getAllPublishedFormsForProtocol(String protocolIdSeq); 
  
  /**
   * Gets all the modules in a form
   *
   * @param <b>formId</b> Idseq of the form component.
   *
   * @return <b>Collection</b> List of module objects
   */
  public Collection getModulesInAForm(String formId);

  /**
   * Creates a new form by copying an existing form.
   * Hyun: Use stored procedure: sbrext_form_builder_pkg.copy_crf 
   * @param <b>sourceFormId</b> Idseq of the form that is being copied.
   * @param <b>newForm</b> Specifies data for new form
   *
   * @return <b>String</b> Idseq of the new form.
   *
   * @throws <b>DMLException</b>
   */
  public String copyForm(
    String sourceFormId,
    Form newForm) throws DMLException;
    
    /**
     * Creates a new version form .
     * Note: Use stored procedure: sbrext_form_builder_pkg.copy_crf 
     * @param <b>sourceFormId</b> Idseq of the form.
     * @param <b>newVersionNumber</b> the new version number.
     *
     * @return <b>String</b> Idseq of the new form.
     *
     * @throws <b>DMLException</b>
     */
    public String createNewFormVersion(
      String sourceFormId,
      Float newVersionNumber,
      String changeNote,
      String createdBy) throws DMLException;

    public Float getMaxFormVersion(int pulidId) throws DMLException;
  /**
   * Creates a new form component (just the header info).
   *
   * @param <b>sourceForm</b> Form object
   *
   * @return <b>String</b> Form Idseq.
   *
   * @throws <b>DMLException</b>
   */
  public String createFormComponent(Form sourceForm) throws DMLException;

  /**
   * Adds new modules to a form. 
   * Hyun: Don't implement this method
   *
   * @param <b>formId</b> Idseq of the form component.
   * @param <b>modules</b> Module objects to be added to the form
   *
   * @return <b>Form</b> Form object representing the form after addition of
   *         new modules.
   *
   * @throws <b>DMLException</b>
   */
  public Form addModules(
    String formId,
    Collection modules) throws DMLException;

  /**
   * Adds new module to a form. 
   * Hyun: Don't implement this method
   *
   * @param <b>formId</b> Idseq of the form component.
   * @param <b>module</b> Module object to be added to the form
   *
   * @return <b>Form</b> Form object representing the form after addition of
   *         new module.
   *
   * @throws <b>DMLException</b>
   */
  public Form addModule(
    String formId,
    Module module) throws DMLException;

  /**
   * Finds a form based on the primary key.
   *
   * @param <b>formId</b> Idseq of the form component.
   *
   * @return <b>Form</b> Form object representing the form.
   *
   * @throws <b>DMLException</b>
   */
  public Form findFormByPrimaryKey(String formId) throws DMLException;

  /**
   * Changes (updates) a form component (just the header info).
   *
   * @param <b>newForm</b> Form object
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateFormComponent(Form newForm) throws DMLException;

  /**
   * Deletes the entire form including all the components associated with it.
   * @param <b>formId</b> Idseq of the form component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteForm(String formId) throws DMLException;
  
// Publish Change order

    
 public boolean isFormPublished(String formIdSeq,String conteIdSeq);
// Publish Change order
    /**
   * Checks if the Template  published to a specific context
   *
   * @param <b>acId</b> Idseq of an admin component
   *
   * 
   */  
    
  public List getPublishingCSCSIsForForm(String contextIdSeq);

    /**
   * Gets all the publishing Classifications for a Template
   *
   * @param <b>acId</b> Idseq of an admin component
   *
   * 
   */  
  public List getPublishingCSCSIsForTemplate(String contextIdSeq);   
 
  /**
   * Gets all the forms ordered by context and protocol
   *
   * @param 
   *
   * 
   */  
  public List getAllFormsOrderByContextProtocol() ;
    /**
   * Gets all templates ordered by context
   *
   * @param 
   *
   * 
   */  
  
  public List getAllTemplatesOrderByContext() ;

 /**
  * Gets all the templates for given context id
  *
  * @param 
  *
  * @return forms that match the criteria.
  */
 public List getAllTemplatesForContextId(String contextIdseq);
 
 
  /**
   * Gets all the templatess that has been published by the given context
   *
   * @param <b>contextId</b> Idseq of the Context
   *
   * @return <b>List</b> List of Templates
   */
 public List getAllPublishedTemplates (String contextId);
 
  /**
   * Gets all the forms that has been published by given context
   *
   * @param <b>contextId</b> Idseq of the Context
   *
   * @return <b>List</b> List of Forms
   */
  public List getAllPublishedForms(String contextId);


  /**
   * Gets all the protocols 
   *
   * @param <b>contextId</b> Idseq of the Context
   *
   * @return <b>Collection</b> List of Protocols
   */
  public List getAllProtocolsForPublishedForms(String contextIdSeq);
  
    /**
   * Gets all Template Types
   *
   * @param <b>contextId</b> Idseq of the Context
   *
   * @return <b>Collection</b> List of TemplateType names
   */
  public List getAllTemplateTypes(String contextId);

  /**
   * Gets all the publishing Classifications for forms
   */
  public List getAllPublishingCSCSIsForForm() ;
  /**
   * Gets all the publishing Classifications for templates
   */
  public List getAllPublishingCSCSIsForTemplate() ;
  
  public List getFormVersions(int publicId);
  
  public void setLatestVersion(Version oldVersion, Version newVersion, List changedNoteList, String modifiedBy);

  public void removeFormProtocol(String formIdseq, String protocoldIdseq);

  public void removeFormProtocols(String formIdseq, Collection protocoldIds);
  
  public void addFormProtocol(String formIdseq, String protocoldIdseq, String createdBy);
  
  public void addFormProtocols(String formIdseq, Collection ids, String createdBy);
  
  public List getFormsOrderByProtocol(String contextIdSeq);

  }
