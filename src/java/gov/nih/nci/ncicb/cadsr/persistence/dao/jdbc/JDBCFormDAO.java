package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCFormTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;
import gov.nih.nci.ncicb.cadsr.resource.Version;
import gov.nih.nci.ncicb.cadsr.resource.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;


import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;

public class JDBCFormDAO extends JDBCAdminComponentDAO implements FormDAO {
  public JDBCFormDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all the forms that match the given criteria
   *
   * @param formName
   * @param protocolIdseq
   * @param contextIdseq
   * @param workflow
   * @param category
   * @param type
   *
   * @return forms that match the criteria.
   */
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
    String cdePublicId) {
    FormQuery query = new FormQuery();
    query.setDataSource(getDataSource());
    query.setSql(
      formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName, type,
      classificationIdseq,contextRestriction, publicId, version, moduleLongName, cdePublicId);

    Collection forms =  query.execute();
    //add protocols
    fetchProtocols(forms);
    return forms;
  }


 //Publis Change Order
  /**
   * Gets all the forms that has been classified by this Classification
   *
   * @param <b>formId</b> Idseq of the Classification
   *
   * @return <b>Collection</b> List of Forms
   */
  public Collection getAllFormsForClassification(String classificationIdSeq)
  {
    FormByClassificationQuery query = new FormByClassificationQuery();
    query.setDataSource(getDataSource());
    query.setQuerySql(classificationIdSeq);
    List forms = query.execute();
    fetchProtocols(forms);
    return forms;
  }
//Publis Change Order
  /**
   * Gets all the forms that has been classified by this Classification
   *
   * @param <b>formId</b> Idseq of the Classification
   *
   * @return <b>Collection</b> List of Forms
   */
  public List getAllPublishedFormsForProtocol(String protocolIdSeq)
  {
    PublishedFormsByProtocol query = new PublishedFormsByProtocol();
    query.setDataSource(getDataSource());
    query.setQuerySql(protocolIdSeq);
    List forms = query.execute();
    //protocols
    fetchProtocols(forms);
    return forms;
  }

  /**
   * Gets all the protocols
   *
   * @param <b>contextId</b> Idseq of the Context
   *
   * @return <b>Collection</b> List of Protocols
   */
  public List getAllProtocolsForPublishedForms(String contextIdSeq)
  {
    PublishedProtocolsQuery query = new PublishedProtocolsQuery(getDataSource());
    return query.getProtocols(contextIdSeq);
  }


  /**
   * Gets all the forms that has been publish by Form Type
   *
   * @param <b>formType</b> Idseq of the Classification
   *
   * @return <b>List</b> List of Forms
   */
  private List getAllPublishedFormsByType(String formType, String contextId)
  {
    PublishedFormsByTypeQuery query = new PublishedFormsByTypeQuery(getDataSource());
    return query.getPublishedForms(formType, contextId);
  }
  /**
   * Gets all the forms that has been published by given context
   *
   * @param <b>contextId</b> Idseq of the Context
   *
   * @return <b>List</b> List of Forms
   */
  public List getAllPublishedForms(String contextId)
  {
    PublishedFormsByTypeQuery query = new PublishedFormsByTypeQuery(getDataSource());
    return query.getPublishedForms(PersistenceConstants.FORM_TYPE_CRF, contextId);
  }
  /**
   * Gets all the templatess that has been published by the given context
   *
   * @param <b>contextId</b> Idseq of the Context
   *
   * @return <b>List</b> List of Templates
   */
  public List getAllPublishedTemplates (String contextId)
  {
    PublishedFormsByTypeQuery query = new PublishedFormsByTypeQuery(getDataSource());
    return query.getPublishedForms(PersistenceConstants.FORM_TYPE_TEMPLATE, contextId);
  }

  /**
   * Gets all the modules that belong to the specified form
   *
   * @param formId corresponds to the form idseq
   *
   * @return modules that belong to the specified form
   */
  public Collection getModulesInAForm(String formId) {
    ModulesInAFormQuery_STMT query = new ModulesInAFormQuery_STMT();
    query.setDataSource(getDataSource());
    query._setSql(formId);

    return query.execute();
  }

  public String copyForm(
    String sourceFormId,
    Form newForm) throws DMLException {
    CopyForm nForm = new CopyForm(this.getDataSource());

    newForm.setPreferredName(generatePreferredName(newForm.getLongName()));

    Map out = nForm.execute(sourceFormId, newForm);

     if ( (out.get("p_return_desc")) == null) {    
      /*newForm.setFormIdseq((String) out.get("p_new_idseq"));
      return newForm;*/
      return (String)out.get("p_new_idseq");
    }
    else {
        DMLException dmlExp = new DMLException((String) out.get("p_return_desc"));
        dmlExp.setErrorCode(ERROR_COPYING_FORM);
         throw dmlExp;
    }
  }

    /**
     * Creates a new form version
     *
     * @param <b>formIdSeq</b> existing form IdSeq.
     * @param <b>newVersionNumber</b> new version number
     *
     * @return <b>String</b> the new Form Idseq.
     * @throws <b>DMLException</b>
     */
  public String createNewFormVersion(String formIdSeq, Float newVersionNumber, String changeNote, String createdBy) throws DMLException {
      VersionForm vForm = new VersionForm(this.getDataSource());

      Map out = vForm.execute(formIdSeq, newVersionNumber, changeNote, createdBy);

      if ((out.get("p_return_code")) == null) {
        /*newForm.setFormIdseq((String) out.get("p_new_idseq"));
        return newForm;*/
        return (String) out.get("p_new_idseq");
      }
      else {
          DMLException dmlExp = new DMLException((String) out.get("p_return_desc"));
          dmlExp.setErrorCode(ERROR_CREATE_NEW_VERSION_FORM);
           throw dmlExp;
      }
  }


  public List getFormVersions(int publicId){
    FormVersionsQuery query = new FormVersionsQuery();
    query.setDataSource(getDataSource());
    List result = query.getFormVersions(publicId);
    return result;
  }

  public Float getMaxFormVersion(int publicId){
      MaxFormVersionsQuery query = new MaxFormVersionsQuery();
      query.setDataSource(getDataSource());
      query.setSql(publicId);
      List result = (List) query.execute();

      if (result == null || result.isEmpty()){
          DMLException dmlExp = new DMLException("No matching record found.");
                dmlExp.setErrorCode(NO_MATCH_FOUND);
            throw dmlExp;
      }

      Float maxVersion = (Float) (result.get(0));
      return maxVersion;
  }
    public void setLatestVersion(Version oldVersion, Version newVersion, List changedNoteList, String modifiedBy){

        if (oldVersion!=null){
            SetLatestVersion setLatestSqlUpdate = new SetLatestVersion(getDataSource());
            setLatestSqlUpdate.updateVersionInfo(oldVersion.getId(), oldVersion.getChangeNote(), false, modifiedBy);
            //remove(oldVersion.getId(), oldVersion.getChangeNote());
        }

        if (newVersion!=null){
            //setLatestSqlUpdate.setLatestVersion(newVersion.getId(), newVersion.getChangeNote(), modifiedBy);
             SetLatestVersion setLatestSqlUpdate = new SetLatestVersion(getDataSource());
            setLatestSqlUpdate.updateVersionInfo(newVersion.getId(), newVersion.getChangeNote(), true, modifiedBy);
        }


        //SetChangeNote
        Iterator it = changedNoteList.iterator();
        while (it.hasNext()){
            SetLatestVersion setLatestSqlUpdate = new SetLatestVersion(getDataSource());
            Version version = (Version)it.next();
            setLatestSqlUpdate.updateVersionInfo(version.getId(), version.getChangeNote(), false, modifiedBy);
        }
    }

  /**
   * Creates a new form component (just the header info).
   *
   * @param <b>sourceForm</b> Form object
   *
   * @return <b>String</b> Form Idseq.
   *
   * @throws <b>DMLException</b>
   */
  public String createFormComponent(Form sourceForm) throws DMLException {
    // check if the user has the privilege to create module
    boolean create =
      this.hasCreate(
        sourceForm.getCreatedBy(), "QUEST_CONTENT", sourceForm.getContext().getConteIdseq());

    if (!create) {
         DMLException dmlExp = new DMLException("The user does not have the create form privilege.");
	       dmlExp.setErrorCode(INSUFFICIENT_PRIVILEGES);
           throw dmlExp;
    }

    InsertQuestContent insertQuestContent =
      new InsertQuestContent(this.getDataSource());
    String qcIdseq = generateGUID();
    int res = insertQuestContent.createContent(sourceForm, qcIdseq);

    if (res != 1) {
         DMLException dmlExp = new DMLException("Did not succeed creating form record in the " +
        " quest_contents_ext table.");
	       dmlExp.setErrorCode(ERROR_CREATEING_FORM);
           throw dmlExp;
    }
    sourceForm.setFormIdseq(qcIdseq);
    //insert protocols
    List protocols = sourceForm.getProtocols();
    if ( protocols!=null && (!protocols.isEmpty())){
        Iterator it = protocols.iterator();
        List ids = new ArrayList();
        while (it.hasNext()){
            Protocol p = (Protocol)it.next();
            ids.add(p.getProtoIdseq());
        }
        addFormProtocols(qcIdseq, ids, sourceForm.getCreatedBy());
    }
    return qcIdseq;
  }

  /**
   * Deletes the entire form including all the components associated with it.
   *
   * @param <b>formId</b> Idseq of the form component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteForm(String formId) throws DMLException {
    DeleteForm deleteForm = new DeleteForm(this.getDataSource());
    Map out = deleteForm.executeDeleteCommand(formId);

    String returnCode = (String) out.get("p_return_code");
    String returnDesc = (String) out.get("p_return_desc");

    if (!StringUtils.doesValueExist(returnCode)) {
      return 1;
    }
    else {
           DMLException dmlExp = new DMLException(returnDesc);
	       dmlExp.setErrorCode(ERROR_DELETE_FORM_FAILED);
           throw dmlExp;
    }
  }

  public int updateFormComponent(Form newForm) throws DMLException {

    UpdateFormComponent  updateFormComponent  =
      new UpdateFormComponent (this.getDataSource());
    int res = updateFormComponent.updateFormFields(newForm);
    if (res != 1) {
         DMLException dmlExp = new DMLException("Did not succeed updating form record in the " +
        " quest_contents_ext table.");
	       dmlExp.setErrorCode(ERROR_UPDATING_FORM);
           throw dmlExp;
    }
    return res;
  }

  public Form addModule(
    String formId,
    Module module) throws DMLException {
    return null;
  }

  public Form addModules(
    String formId,
    Collection modules) throws DMLException {
    return null;
  }

  /**
   * Gets a form by the form idseq
   *
   * @param formId corresponds to the form idseq
   *
   * @return form that has the formId as the primary key
   */
  public Form findFormByPrimaryKey(String formId) throws DMLException {
    Form myForm = null;
    FormByPrimaryKey query = new FormByPrimaryKey();
    query.setDataSource(getDataSource());
    query.setSql();

    List result = (List) query.execute(formId);

    if (result == null || result.isEmpty()){
        DMLException dmlExp = new DMLException("No matching record found.");
              dmlExp.setErrorCode(NO_MATCH_FOUND);
          throw dmlExp;
    }

    myForm = (Form) (query.execute(formId).get(0));

    //get protocols
    myForm.setProtocols(getProtocols(myForm));
    return myForm;
  }

  public Form findFormByTimestamp(
    String formId,
    Timestamp ts) throws DMLException {
    Form myForm = null;
    FormByTimestamp query = new FormByTimestamp();
    query.setDataSource(getDataSource());
    query.setSql();

    List result = (List) query.execute(new Object[] { ts, formId });

    if (result.size() != 0) {
      myForm = (Form) (query.execute(new Object[] { ts, formId }).get(0));
      //get protocols
      myForm.setProtocols(getProtocols(myForm));
    }
    else
    {
         DMLException dmlExp = new DMLException("No matching record found.");
	       dmlExp.setErrorCode(NO_MATCH_FOUND);
           throw dmlExp;
    }

    return myForm;
  }
 // Publish Change Order

  /**
   * Checks if the Form Component is published
   *
   * @param <b>acId</b> Idseq of an admin component
   */
  public boolean isFormPublished(String formIdSeq,String conteIdSeq) {
    return this.isClassifiedForPublish(formIdSeq,conteIdSeq);

  }

  // Publish Change Order

  /**
   * Gets all the publishing Classifications for a form for given context id
   */
  public List getPublishingCSCSIsForForm(String contextIdSeq) {
    return getCSIByType(PersistenceConstants.CS_TYPE_PUBLISH
                    , PersistenceConstants.CSI_TYPE_PUBLISH_FORM
                    ,  contextIdSeq);
  }

  /**
   * Gets all the publishing Classifications for forms
   */
  public List getAllPublishingCSCSIsForForm() {
    return getAllCSIByType(PersistenceConstants.CS_TYPE_PUBLISH
                    , PersistenceConstants.CSI_TYPE_PUBLISH_FORM );
  }

  /**
   * Gets all the publishing Classifications for templates
   */
  public List getAllPublishingCSCSIsForTemplate() {
    return getAllCSIByType(PersistenceConstants.CS_TYPE_PUBLISH
                    , PersistenceConstants.CSI_TYPE_PUBLISH_TEMPLATE );
  }


 /**
  * Gets all the forms sort by context id, protocol name, form name
  *
  * @param
  *
  * @return forms that match the criteria.
  */
 public List getAllFormsOrderByContextProtocol() {
  FormContextProtoQuery query = new FormContextProtoQuery();

  query.setDataSource(getDataSource());
  query.setSql();
  query.execute();

  return query.getFormCollection();
 }
 
   /**
    * Gets all the forms sort by context id, protocol name, form name
    *
    * @param
    *
    * @return forms that match the criteria.
    */
   public List getFormsOrderByProtocol(String contextIdSeq) {
    FormProtoQuery query = new FormProtoQuery(getDataSource());

    return query.getFormCollection(contextIdSeq);
   }
 /**
  * Gets all the forms sort by context id, protocol name, form name
  *
  * @param
  *
  * @return forms that match the criteria.
  */
 public List getAllTemplatesOrderByContext() {
  TemplateByContextQuery query = new TemplateByContextQuery();

  query.setDataSource(getDataSource());
  query.setSql();
  return query.getAllTemplates();
 }

 /**
  * Gets all the templates for given context id
  *
  * @param
  *
  * @return forms that match the criteria.
  */
 public List getAllTemplatesForContextId(String contextIdseq) {
  TemplateForContextIdQuery query = new TemplateForContextIdQuery(getDataSource());
  return query.execute(contextIdseq);
 }

 /**
  * Gets all the template types for given context id
  *
  * @param
  *
  * @return forms that match the criteria.
  */
 public List getAllTemplateTypes(String contextIdseq) {
  TemplateTypeQuery query = new TemplateTypeQuery(getDataSource());
  return query.execute(contextIdseq);
 }

  // Publish Change Order

  /**
   * Gets all the publishing Classifications for a form
   */
  public List getPublishingCSCSIsForTemplate(String contextIdSeq) {
    return getCSIByType(PersistenceConstants.CS_TYPE_PUBLISH
                    , PersistenceConstants.CSI_TYPE_PUBLISH_TEMPLATE
                    ,  contextIdSeq);
  }

    public void removeFormProtocol(String formIdseq, String protocolIdseq){
        RemoveFormProtocolQuery query = new RemoveFormProtocolQuery(getDataSource());
        query.removeFormProtocol(formIdseq, protocolIdseq);
        return;
    }

    public void removeFormProtocols(String formIdseq, Collection protocolIds){
        RemoveFormProtocolQuery query = new RemoveFormProtocolQuery(getDataSource());
        query.removeFormProtocols(formIdseq, protocolIds);
        return;
    }

    public void addFormProtocol(String formIdseq, String protocoldIdseq, String createdBy){
        AddFormProtocolQuery query = new AddFormProtocolQuery(getDataSource());
        query.addFormProtocol(formIdseq, protocoldIdseq, createdBy);
        return;
    }

    public void addFormProtocols(String formIdseq, Collection ids, String createdBy){
        if (ids==null || ids.isEmpty()){
            return;
        }
        AddFormProtocolQuery query = new AddFormProtocolQuery(getDataSource());
        Iterator it = ids.iterator();
        while (it.hasNext()){
            String pid = (String)it.next();
            query.addFormProtocol(formIdseq, pid, createdBy);
        }
        return;
    }

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    JDBCFormDAO formTest = new JDBCFormDAO(locator);
      //public String createNewFormVersion(String formIdSeq, Float newVersionNumber, String changeNote, String createdBy) throws DMLException {
    formTest.createNewFormVersion("0870F3C9-8AC2-25FC-E044-0003BA0B1A09", new Float(18), "version 14","SBREXT");
    System.out.println("jereera ");



    /*
    FormTransferObject newForm = new FormTransferObject();

    newForm.setLongName("CHRIS TEST 1");
    newForm.setPreferredName(formTest.generatePreferredName(newForm.getLongName()));
//     newForm.setFormIdseq();

    newForm.setPreferredDefinition("This is a test Definition");

    ContextTransferObject newContext = new ContextTransferObject(null);
    newContext.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
    newForm.setContext(newContext);

    ProtocolTransferObject newProtocol = new ProtocolTransferObject(null);
    newProtocol.setProtoIdseq("9DC06830-EB45-2E3C-E034-080020C9C0E0");
    newForm.setProtocol(newProtocol);

    newForm.setFormCategory("Comments");
    newForm.setFormType("CRF");

    newForm.setAslName("DRAFT NEW");

    newForm.setVersion(new Float(1.0));

//     newForm.setPreferredName(newForm.getLongName());
    newForm.setCreatedBy("Anonymous");

    try {
	    formTest.copyForm("9DC06CFF-6DF7-2B36-E034-080020C9C0E0",  newForm);
    } catch (DMLException e){
	    System.out.println("Failed: " + e);
    }
    */

//     //String formId1 = "9E343E83-5FEB-119F-E034-080020C9C0E0";  //CRF
//     String formId1 = "99CD59C5-A98A-3FA4-E034-080020C9C0E0"; // TEMPLATE

//     try {
//       System.out.println(formTest.findFormByPrimaryKey(formId1));
//     }
//     catch (DMLException e) {
//       System.out.println("Failed to get a form for " + formId1);
//     }

//     try {
//       Form form1 =
//         formTest.findFormByPrimaryKey("D3830147-13E8-11BF-E034-0003BA0B1A09");
//       Form form2 =
//         formTest.findFormByPrimaryKey("D3830147-13E8-11BF-E034-0003BA0B1A09");
//       form2.setPreferredName("testcopyprna1456");
//       form2.setLongName("my form test 456123");
//       form2.setAslName("DRAFT MOD");
//       form2.setConteIdseq("29A8FB18-0AB1-11D6-A42F-0010A4C1E842");
//       System.out.println(form2.getProtocol().getProtoIdseq() + "Conte_idseq");

//       //System.out.println(formTest.copyForm(form1, form2).getFormIdseq());
//     }

//     catch (DMLException e) {
//       System.out.println("Failed to find Form");
//     }

    //formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName,
    // type, classificationIdseq

    /*
       System.out.println(formTest.getAllForms(
         "", "", "99BA9DC8-2095-4E69-E034-080020C9C0E0", "", "", "",
         "99BA9DC8-A622-4E69-E034-080020C9C0E0"));
       System.out.println(formTest.getAllForms(
         "", "", "99BA9DC8-2095-4E69-E034-080020C9C0E0", "", "", "", null));
       System.out.println(
         formTest.getModulesInAForm("99CD59C5-A9A0-3FA4-E034-080020C9C0E0"));
       String formId = "9E343E83-5FEB-119F-E034-080020C9C0E0";
       try {
         Form test = formTest.findFormByPrimaryKey(formId);
         //System.out.println(formTest.findFormByPrimaryKey(formId));
         System.out.println(formTest.findFormByTimestamp(formId, test.getDateModified()));
       }
       catch (DMLException e) {
         System.out.println("Failed to get a form for " + formId);
       }
     */
    /*
       // test createFormComponent method.
       // for each test, change long name(preferred name generated from long name)
       try {
         Form newForm = new FormTransferObject();
         newForm.setVersion(new Float(2.31));
         newForm.setLongName("Test Form Long Name 030104 1");
         newForm.setPreferredDefinition("Test Form pref def");
         newForm.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
         Protocol protocol = new ProtocolTransferObject("TEST");
         protocol.setProtoIdseq("A1204FD0-22B8-3B68-E034-080020C9C0E0");
         newForm.setProtocol(protocol);
         newForm.setAslName("DRAFT NEW");
         newForm.setCreatedBy("Hyun Kim");
         newForm.setFormCategory("Registration");
         newForm.setFormType("CRF");
         System.out.println(formTest.createFormComponent(newForm));
       }
       catch (DMLException de) {
         System.out.println("******Printing DMLException*******");
         de.printStackTrace();
         System.out.println("******Finishing printing DMLException*******");
       }
     */
    /*
       try {
         int res = formTest.deleteForm("D4700045-2FD0-0DAA-E034-0003BA0B1A09");
         System.out.println("\n*****Delete Form Result 1: " + res);
       }
       catch (DMLException de) {
         System.out.println("******Printing DMLException*******");
         de.printStackTrace();
         System.out.println("******Finishing printing DMLException*******");
       }
     */
    Form formX = null;
    try {
    /*
    //Test getAllTemplatesForContextId
    Collection temps = formTest.getAllTemplatesForContextId("99BA9DC8-2095-4E69-E034-080020C9C0E0");
    System.out.println(temps.size() + " templates retrieved");

    // Test getAllPublishedFormsByType
    Collection temps = formTest.getAllPublishedFormsByType("CRF", "D9344734-8CAF-4378-E034-0003BA12F5E7");
    System.out.println(temps.size() + " templates retrieved");
  */
    // Test getAllPublishedFormsByType
    //Collection temps = formTest.getAllProtocolsForPublishedForms("D9344734-8CAF-4378-E034-0003BA12F5E7");
    //Collection temps = formTest.getAllFormsOrderByContextProtocol();
   //  Collection temps = formTest.getAllPublishingCSCSIsForForm();

   //   formX = formTest.findFormByPrimaryKey("D4D75662-033F-6DD1-E034-0003BA0B1A09");
 //  Collection temps = formTest.getAllTemplatesOrderByContext();
 //  System.out.println(temps.size() + " templates retrieved");
    }

    catch (DMLException e) {
      System.out.println("Failed to get a form for " + formX);
    }
    if (formX != null) {
      formX.setFormType("CRF");
      formX.getContext().setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
      formX.setAslName("CRF TEMPLATE");
      formX.setPreferredName("Form update test");
      formX.setPreferredDefinition("Form update test definition");
      //for multiple protocols...
      //formX.getProtocol().setProtoIdseq("9B0EAC7E-6A62-0DEB-E034-080020C9C0E0");
      formX.setLongName("Form update test long name");
      formX.setFormCategory("Lab");
      formX.setModifiedBy("sbrext");
      formTest.updateFormComponent(formX);
    }
  }


  /**
   * Inner class that accesses database to get all the modules that belong to
   * the specified form
   */
  class ModulesInAFormQuery_STMT extends MappingSqlQuery {
    ModulesInAFormQuery_STMT() {
      super();
    }

    public void _setSql(String id) {
      super.setSql("SELECT * FROM FB_MODULES_VIEW where CRF_IDSEQ = '" + id + "'");
//       declareParameter(new SqlParameter("CRF_IDSEQ", Types.VARCHAR));
    }

   /**
    * 3.0 Refactoring- Removed JDBCTransferObject
    */
    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
     Module module = new ModuleTransferObject();
     module.setModuleIdseq(rs.getString(1));  // MOD_IDSEQ
     module.setVersion(rs.getFloat(3)); //version
     module.setConteIdseq(rs.getString(4)); //context idseq
     module.setAslName(rs.getString(5));  //workflow status
     module.setPreferredName(rs.getString(6));
     module.setPreferredDefinition(rs.getString(7));
     module.setLongName(rs.getString(8));     // LONG_NAME
     module.setDisplayOrder(rs.getInt(13));   // DISPLAY_ORDER
     module.setNumberOfRepeats(rs.getInt(14));//repeat_no
     return module;
    }
  }



    /**
     * Inner class that accesses database to get a form's protocols using the form idseq
     */
    class FormProtocolByFormPrimaryKey extends MappingSqlQuery {
      FormProtocolByFormPrimaryKey() {
        super();
      }

      public void setSql() {
        String sql = " SELECT p.proto_idseq, p.version, p.conte_idseq, p.preferred_name, p.preferred_definition, p.asl_name, p.long_name, p.LATEST_VERSION_IND, p.begin_date, p.END_DATE, p.PROTOCOL_ID, p.TYPE, p.PHASE, p.LEAD_ORG, p.origin, p.PROTO_ID, c.name contextname " +
                     " FROM protocol_qc_ext fp, protocols_ext p , sbr.contexts c" +
                     " where QC_IDSEQ = ? and fp.PROTO_IDSEQ = p.PROTO_IDSEQ and p.deleted_ind='No' " +
                     " AND p.conte_idseq = c.conte_idseq";
        super.setSql(sql);
        declareParameter(new SqlParameter("QC_IDSEQ", Types.VARCHAR));
      }

      protected Object mapRow( ResultSet rs, int rownum) throws SQLException {
        Protocol protocol = new ProtocolTransferObject();
        protocol.setProtoIdseq(rs.getString(1));
        protocol.setVersion(rs.getFloat(2));
        protocol.setConteIdseq(rs.getString(3));
        protocol.setPreferredName(rs.getString(4));
        protocol.setPreferredDefinition(rs.getString(5));
        protocol.setAslName(rs.getString(6));
        protocol.setLongName(rs.getString(7));
        protocol.setLatestVersionInd(rs.getString(8));
        protocol.setBeginDate(rs.getDate(9));
        protocol.setEndDate(rs.getDate(10));
        protocol.setProtocolId(rs.getString(11));
        protocol.setType(rs.getString(12));
        protocol.setPhase(rs.getString(13));
        protocol.setLeadOrg(rs.getString(14));
        protocol.setOrigin(rs.getString(15));
        Float publicId = rs.getFloat(16);
        protocol.setPublicId(publicId.intValue());
        String contextName = rs.getString(17);
        Context context = new ContextTransferObject();
        context.setConteIdseq(rs.getString(3));
        context.setName(contextName);
        protocol.setContext(context);

        return protocol;
      }
    }//end of class FormProtocolByFormPrimaryKey


  /**
   * Inner class that accesses database to get a form using the form idseq
   */
  class FormByPrimaryKey extends MappingSqlQuery {
    FormByPrimaryKey() {
      super();
    }

    public void setSql() {
      super.setSql("SELECT * FROM FB_FORMS_VIEW where QC_IDSEQ = ? ");
      declareParameter(new SqlParameter("QC_IDSEQ", Types.VARCHAR));
    }
   /**
    * 3.0 Refactoring- Removed JDBCTransferObject
    */
    protected Object mapRow( ResultSet rs, int rownum) throws SQLException {
      Form form = new FormTransferObject();
      form.setFormIdseq(rs.getString(1)); // QC_IDSEQ
      form.setIdseq(rs.getString(1));
      form.setLongName(rs.getString(9)); //LONG_NAME
      form.setPreferredName(rs.getString(7)); // PREFERRED_NAME

      //setContext(new ContextTransferObject(rs.getString("context_name")));
      ContextTransferObject contextTransferObject = new ContextTransferObject();
      contextTransferObject.setConteIdseq(rs.getString(4)); //CONTE_IDSEQ
      contextTransferObject.setName(rs.getString(12)); // CONTEXT_NAME
      form.setContext(contextTransferObject);
      form.setDateModified(rs.getTimestamp(15));

      //multiple protocols will be set later
      /*ProtocolTransferObject protocolTransferObject =
        new ProtocolTransferObject(rs.getString(11)); //PROTOCOL_LONG_NAME
      protocolTransferObject.setProtoIdseq(rs.getString(10));  // PROTO_IDSEQ
      //form.setProtocol(protocolTransferObject);

      List protocols = new ArrayList();
      protocols.add(protocolTransferObject);
      form.setProtocols(protocols);
      */
      form.setFormType(rs.getString(3)); // TYPE
      form.setAslName(rs.getString(6)); // WORKFLOW
      form.setVersion(new Float(rs.getString(2))); // VERSION
      form.setPreferredDefinition(rs.getString(8)); // PREFERRED_DEFINITION
      form.setCreatedBy(rs.getString(13)); // CREATED_BY
      form.setFormCategory(rs.getString(5));
      form.setPublicId(rs.getInt(17));
      return form;
    }
  }

  class FormByTimestamp extends MappingSqlQuery {
    FormByTimestamp() {
      super();
    }

    public void setSql() {
      super.setSql(
        "SELECT * FROM FB_FORMS_VIEW where DATE_MODIFIED = ? AND QC_IDSEQ = ?");
      declareParameter(new SqlParameter("date_modified", Types.TIMESTAMP));
      declareParameter(new SqlParameter("qc_idseq", Types.VARCHAR));
    }

   /**
    * 3.0 Refactoring- Removed JDBCTransferObject
    */
    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
    Form form = new FormTransferObject();
    form.setFormIdseq(rs.getString(1)); // QC_IDSEQ
    form.setIdseq(rs.getString(1));
    form.setLongName(rs.getString(9)); //LONG_NAME
    form.setPreferredName(rs.getString(7)); // PREFERRED_NAME

    //setContext(new ContextTransferObject(rs.getString("context_name")));
    ContextTransferObject contextTransferObject = new ContextTransferObject();
    contextTransferObject.setConteIdseq(rs.getString(4)); //CONTE_IDSEQ
    contextTransferObject.setName(rs.getString(12)); // CONTEXT_NAME
    form.setContext(contextTransferObject);
    form.setDateModified(rs.getTimestamp(15));

    //multiple protocols will be set later
    /*ProtocolTransferObject protocolTransferObject =
      new ProtocolTransferObject(rs.getString(11)); //PROTOCOL_LONG_NAME
    protocolTransferObject.setProtoIdseq(rs.getString(10));  // PROTO_IDSEQ
    form.setProtocol(protocolTransferObject);
    */

    form.setFormType(rs.getString(3)); // TYPE
    form.setAslName(rs.getString(6)); // WORKFLOW
    form.setVersion(new Float(rs.getString(2))); // VERSION
    form.setPreferredDefinition(rs.getString(8)); // PREFERRED_DEFINITION
    form.setCreatedBy(rs.getString(13)); // CREATED_BY
    form.setFormCategory(rs.getString(5));

    return form;
    }
  }

  /**
   * Inner class that accesses database to get all the forms and modules that
   * match the given criteria
   */
  class FormQuery extends MappingSqlQuery {
    FormQuery() {
      super();
    }

    public void setSql(
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
      String moduleName,
      String cdePublicId) {

      String selectWhat = "SELECT distinct f.qc_idseq, f.version, f.type, f.conte_idseq, f.CATEGORY_NAME, f.workflow, f.preferred_name, f.definition, " +
                          " f.long_name, f.context_name, f.public_id, latest_version_ind,  f.DATE_MODIFIED, f.DATE_CREATED ";
      StringBuffer fromWhat = new StringBuffer(" FROM FB_FORMS_VIEW f ");
      StringBuffer initialWhere = new StringBuffer();
      boolean hasWhere = false;
      if (StringUtils.doesValueExist(moduleName) || StringUtils.doesValueExist(cdePublicId)){
        fromWhat.append(", FB_QUEST_MODULE_VIEW q ");
        initialWhere.append(" where ( f.QC_IDSEQ = q.FORM_IDSEQ )");
        hasWhere = true;
      }
      if (StringUtils.doesValueExist(protocolIdSeq) || StringUtils.doesValueExist(protocolIdSeq)){
        fromWhat.append(", protocol_qc_ext p ");
        if (hasWhere){
            initialWhere.append(" AND ( f.QC_IDSEQ = p.qc_idseq) and (p.proto_idseq ='" + protocolIdSeq + "') ");
        }else{
            initialWhere.append(" where ( f.QC_IDSEQ = p.qc_idseq) and (p.proto_idseq ='" + protocolIdSeq + "') ");
            hasWhere = true;
        }
      }
      String whereClause = makeWhereClause(
                          formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName,
                          type, classificationIdseq,contextRestriction,
                          publicId, version, moduleName, cdePublicId, hasWhere);
      String sql = selectWhat.toString() + " " + fromWhat.toString() + " "
                    + initialWhere.toString() + whereClause;
      System.out.println(sql);
      super.setSql(sql);
/*
      if (StringUtils.doesValueExist(moduleName) || StringUtils.doesValueExist(cdePublicId)){
        whereClause = makeWhereClause(
          formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName,
          type, classificationIdseq,contextRestriction,
          publicId, version, moduleName, cdePublicId, true);
          String sql = "SELECT " + selectWhat +
                        " FROM FB_FORMS_VIEW f, FB_QUEST_MODULE_VIEW q where ( f.QC_IDSEQ = q.FORM_IDSEQ ) " +
                        whereClause + "ORDER BY upper(f.LONG_NAME)";
           super.setSql(sql);
           System.out.println(sql);
     }else{
      whereClause = makeWhereClause(
           formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName,
           type, classificationIdseq,contextRestriction,
           publicId, version, moduleName, cdePublicId, false);
           String sql = "SELECT " + selectWhat + " FROM FB_FORMS_VIEW  f " + whereClause + " ORDER BY upper(f.LONG_NAME)";
           super.setSql(sql);
           System.out.println("sql=" + sql);
     }
    */
}

   /**
    * 3.0 Refactoring- Removed JDBCTransferObject
    */
    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      String formName = rs.getString("LONG_NAME");

      Form form = new FormTransferObject();
/*      String selectWhat = "distinct f.qc_idseq, f.version, f.type, f.conte_idseq, f.CATEGORY_NAME, f.workflow, f.preferred_name, f.definition, " +
                          " f.long_name, f.context_name, f.public_id, latest_version_ind, f. ";
*/
     form.setFormIdseq(rs.getString(1)); // QC_IDSEQ
     form.setIdseq(rs.getString(1));
     form.setLongName(rs.getString(9)); //LONG_NAME
     form.setPreferredName(rs.getString(7)); // PREFERRED_NAME

     //setContext(new ContextTransferObject(rs.getString("context_name")));
     ContextTransferObject contextTransferObject = new ContextTransferObject();
     contextTransferObject.setConteIdseq(rs.getString(4)); //CONTE_IDSEQ
     contextTransferObject.setName(rs.getString(10)); // CONTEXT_NAME
     form.setContext(contextTransferObject);
     form.setDateModified(rs.getTimestamp(13));

     //multiple protcols will be set later
     form.setFormType(rs.getString(3)); // TYPE
     form.setAslName(rs.getString(6)); // WORKFLOW
     form.setVersion(new Float(rs.getString(2))); // VERSION
     form.setPublicId(rs.getInt(11)); //Public ID
     form.setPreferredDefinition(rs.getString(8)); // PREFERRED_DEFINITION
     form.setCreatedBy(rs.getString(14)); // CREATED_BY
     form.setFormCategory(rs.getString(5));

/*    form.setFormIdseq(rs.getString(1)); // QC_IDSEQ
    form.setIdseq(rs.getString(1));
    form.setLongName(rs.getString(9)); //LONG_NAME
    form.setPreferredName(rs.getString(7)); // PREFERRED_NAME

    //setContext(new ContextTransferObject(rs.getString("context_name")));
    ContextTransferObject contextTransferObject = new ContextTransferObject();
    contextTransferObject.setConteIdseq(rs.getString(4)); //CONTE_IDSEQ
    contextTransferObject.setName(rs.getString(12)); // CONTEXT_NAME
    form.setContext(contextTransferObject);
    form.setDateModified(rs.getTimestamp(15));

    //multiple protcols will be set later
    form.setFormType(rs.getString(3)); // TYPE
    form.setAslName(rs.getString(6)); // WORKFLOW
    form.setVersion(new Float(rs.getString(2))); // VERSION
    form.setPublicId(rs.getInt(17)); //Public ID
    form.setPreferredDefinition(rs.getString(8)); // PREFERRED_DEFINITION
    form.setCreatedBy(rs.getString(13)); // CREATED_BY
    form.setFormCategory(rs.getString(5));
*/
    return form;
    }

    public String makeWhereClause(
      String formName,
      String protocol,
      String context,
      String workflow,
      String category,
      String type,
      String classificationIdseq,
      String contextRestriction,
      String publicId,
      String version,
      String moduleName,
      String cdePublicId,
      boolean hasWhere)
    {
      String where = "";
      StringBuffer whereBuffer = new StringBuffer("");

      if (StringUtils.doesValueExist(formName)) {
        String temp = StringUtils.strReplace(formName, "*", "%");
        temp = StringUtils.strReplace(temp, "'", "''");

        if (hasWhere) {
          whereBuffer.append(
            " AND UPPER(f.LONG_NAME) LIKE " + "UPPER('" + temp + "')");
        }
        else {
          whereBuffer.append(
            " WHERE UPPER(f.LONG_NAME) LIKE " + "UPPER('" + temp + "')");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(protocol)) {
        if (hasWhere) {
          whereBuffer.append(" AND p.PROTO_IDSEQ ='" + protocol + "'");
        }
        else {
          whereBuffer.append(" WHERE p.PROTO_IDSEQ ='" + protocol + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(context)) {
        if (hasWhere) {
          whereBuffer.append(" AND f.CONTE_IDSEQ ='" + context + "'");
        }
        else {
          whereBuffer.append(" WHERE f.CONTE_IDSEQ ='" + context + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(workflow)) {
        if (hasWhere) {
          whereBuffer.append(" AND f.WORKFLOW ='" + workflow + "'");
        }
        else {
          whereBuffer.append(" WHERE f.WORKFLOW ='" + workflow + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(category)) {
        if (hasWhere) {
          whereBuffer.append(" AND f.CATEGORY_NAME ='" + category + "'");
        }
        else {
          whereBuffer.append(" WHERE f.CATEGORY_NAME ='" + category + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(type)) {
        if (hasWhere) {
          whereBuffer.append(" AND f.TYPE ='" + type + "'");
        }
        else {
          whereBuffer.append(" WHERE f.TYPE ='" + type + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(classificationIdseq)) {
        if (hasWhere) {
          whereBuffer.append(
            " AND f.QC_IDSEQ in (select ac_idseq from ac_csi where CS_CSI_IDSEQ ='" +
            classificationIdseq + "')");
        }
        else {
          whereBuffer.append(
            " WHERE f.QC_IDSEQ in (select ac_idseq from ac_csi where CS_CSI_IDSEQ ='" +
            classificationIdseq + "')");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(contextRestriction)) {
        if (hasWhere) {
          whereBuffer.append(" AND (f.CONTE_IDSEQ !='" + contextRestriction + "' or f.type in ('TEMPLATE'))");
        }
        else {
          whereBuffer.append(" WHERE (f.CONTE_IDSEQ !='" + contextRestriction + "' or f.type in ('TEMPLATE'))");
          hasWhere = true;
        }
      }

/*        if (StringUtils.doesValueExist(publicId)) {
          if (hasWhere) {
            whereBuffer.append(" AND (f.PUBLIC_ID =" + publicId + ")");
          }
          else {
            whereBuffer.append(" WHERE (f.PUBLIC_ID =" + publicId + ")");
            hasWhere = true;
          }
        }
      */
        if (StringUtils.doesValueExist(publicId)) {
          if (hasWhere) {
            whereBuffer.append(" AND (f.PUBLIC_ID =" + publicId + ")");
          }
          else {
            whereBuffer.append(" WHERE (f.PUBLIC_ID =" + publicId + ")");
            hasWhere = true;
          }
        }

        if (StringUtils.doesValueExist(version) && "latestVersion".equals(version)) {
            if (hasWhere) {
              whereBuffer.append(" AND (f.LATEST_VERSION_IND='Yes')");
            }
            else {
              whereBuffer.append(" WHERE (f.LATEST_VERSION_IND='Yes')");
              hasWhere = true;
            }
        }//eles indicate all versions.

         if (StringUtils.doesValueExist(moduleName)) {
            String temp = StringUtils.strReplace(moduleName, "*", "%");
            temp = StringUtils.strReplace(temp, "'", "''");
             if (hasWhere) {
               whereBuffer.append(" AND (UPPER(q.MODULE_NAME) like UPPER('" + temp + "'))");
             }
             else {
               whereBuffer.append(" WHERE (UPPER(q.MODULE_NAME) like UPPER('" + temp + "'))");
               hasWhere = true;
             }
         }

        if (StringUtils.doesValueExist(cdePublicId)) {
            if (hasWhere) {
              whereBuffer.append(" AND (q.CDE_ID='" + cdePublicId + "')");
            }
            else {
              whereBuffer.append(" WHERE (q.CDE_ID='" + cdePublicId + "')");
              hasWhere = true;
            }
        }

      where = whereBuffer.toString();

      return where;
    }
  }

//Publish Change Order

  /**
   * Inner class that accesses database to get all the forms
   * match the given criteria
   */
  class FormByClassificationQuery extends MappingSqlQuery {
    FormByClassificationQuery() {
      super();
    }

    public void setQuerySql(String csidSeq) {
     String querySql = " SELECT * FROM FB_FORMS_VIEW formview, sbr.cs_csi csc,sbr.ac_csi acs "
        + " where  csc.cs_idseq = '"+ csidSeq +"'"
        + " and csc.cs_csi_idseq = acs.cs_csi_idseq "
				+ " and acs.AC_IDSEQ=formview.QC_IDSEQ "
        + " ORDER BY upper(protocol_long_name), upper(context_name)";
      super.setSql(querySql);
    }


    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      String formName = rs.getString("LONG_NAME");

      return new JDBCFormTransferObject(rs);
    }

  }



  //Publish Change Order

  /**
   * Inner class that accesses database to get all the forms  that
   * match the given criteria
   */
  class PublishedFormsByProtocol extends MappingSqlQuery {
    PublishedFormsByProtocol() {
      super();
    }

    public void setQuerySql(String protocolIdSeq) {
     String querySql = " SELECT *  " +
     "from FB_FORMS_VIEW formview, published_forms_view published, "
     + " (select ac_idseq, cs_csi_idseq, cs_conte_idseq  from sbrext.ac_class_view_ext where upper(CSTL_NAME) = upper('"
                + CaDSRConstants.FORM_CS_TYPE + "') and upper(CSITL_NAME) = upper('"
                + CaDSRConstants.FORM_CSI_TYPE + "')) accs"
        + " where PROTO_IDSEQ = '"+ protocolIdSeq +"'"
        + " and published.QC_IDSEQ = formview.QC_IDSEQ "
        + "and formview.QC_IDSEQ = accs.AC_IDSEQ(+) "
        + "and formview.proto_idseq = published.protocol_idseq "
        + " ORDER BY upper(protocol_long_name), upper(context_name), upper (formview.long_name), published.qc_idseq";
      super.setSql(querySql);
    }


    protected Object mapRow( ResultSet rs, int rownum) throws SQLException {
      String formName = rs.getString("LONG_NAME");
      Form form = new JDBCFormTransferObject(rs);
      if ((rs.getString("CS_CSI_IDSEQ")!= null &&
        (rs.getString("CS_CSI_IDSEQ")).length() >0)) {
       ClassSchemeItem csi = new CSITransferObject();
      csi.setCsCsiIdseq(rs.getString("CS_CSI_IDSEQ"));
      csi.setCsConteIdseq(rs.getString("cs_conte_idseq"));
     if (form.getClassifications() == null)
      form.setClassifications(new ArrayList());

     form.getClassifications().add(csi);

    }
      return form;

  }
  }
  /**
   * Inner class that accesses database to create a form in the
   * quest_contents_ext table.
   */
  private class InsertQuestContent extends SqlUpdate {
    public InsertQuestContent(DataSource ds) {
      String contentInsertSql =
        " INSERT INTO quest_contents_ext " +
        " (qc_idseq, version, preferred_name, long_name, preferred_definition, " +
        "  conte_idseq, proto_idseq, asl_name, created_by, qtl_name, qcdl_name ) " +
        " VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

      this.setDataSource(ds);
      this.setSql(contentInsertSql);
      declareParameter(new SqlParameter("p_qc_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_version", Types.VARCHAR));
      declareParameter(new SqlParameter("p_preferred_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_long_name", Types.VARCHAR));
      declareParameter(
        new SqlParameter("p_preferred_definition", Types.VARCHAR));
      declareParameter(new SqlParameter("p_conte_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_proto_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_asl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      declareParameter(new SqlParameter("p_qtl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_qcdl_name", Types.VARCHAR));
      compile();
    }

    protected int createContent(
      Form sm,
      String qcIdseq) {

      String protocolIdSeq = null;
      //Form is now associated with multiple protocols. Protocols must be created after form iself
      //has been created. Protocols will be created in another method call.
      /*if(sm.getProtocol()!=null) {
         protocolIdSeq = sm.getProtocol().getProtoIdseq();
      }
      */
      Object[] obj =
        new Object[] {
          qcIdseq, sm.getVersion().toString(),
          generatePreferredName(sm.getLongName()), sm.getLongName(),
          sm.getPreferredDefinition(), sm.getContext().getConteIdseq(),
          protocolIdSeq, sm.getAslName(), sm.getCreatedBy(),
          sm.getFormType(), sm.getFormCategory()
        };

      int res = update(obj);

      return res;
    }
  }

  /**
   * Inner class that accesses database to delete a form.
   */
  private class DeleteForm extends StoredProcedure {
    public DeleteForm(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.remove_crf");
      declareParameter(new SqlParameter("p_crf_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map executeDeleteCommand(String crfIdseq) {
      Map in = new HashMap();
      in.put("p_crf_idseq", crfIdseq);

      Map out = execute(in);

      return out;
    }
  }


  /**
   * Inner class that copies the source form to a new form
   */
  private class CopyForm extends StoredProcedure {
    public CopyForm(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.copy_crf");
      declareParameter(new SqlParameter("p_src_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_qtl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_version", Types.VARCHAR));
      declareParameter(new SqlParameter("p_preferred_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_long_name", Types.VARCHAR));
      declareParameter(
        new SqlParameter("p_preferred_definition", Types.VARCHAR));
      declareParameter(new SqlParameter("p_conte_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_proto_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_asl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_new_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map execute(
      String sourceFormId,
      Form newForm) {
      Map in = new HashMap();

      in.put("p_src_idseq", sourceFormId);
      in.put("p_qtl_name", newForm.getFormType());
      in.put("p_version", newForm.getVersion().toString());
      in.put("p_preferred_name", newForm.getPreferredName());
      in.put("p_long_name", newForm.getLongName());
      in.put("p_preferred_definition", newForm.getPreferredDefinition());
      in.put("p_conte_idseq", newForm.getContext().getConteIdseq());
      //TODO - CopyForm - should copy the protocols in another table?
      //in.put("p_proto_idseq", newForm.getProtocol().getProtoIdseq());
      in.put("p_proto_idseq", null);
      in.put("p_asl_name", newForm.getAslName());
      in.put("p_created_by", newForm.getCreatedBy());

      Map out = execute(in);

      return out;
    }
  }


    /**
     * Inner class that make a new version of the source form
     */
    private class VersionForm extends StoredProcedure {
      public VersionForm(DataSource ds) {
        super(ds, "Sbrext_Form_Builder_Pkg.CRF_VERSION");
        declareParameter(new SqlParameter("P_Idseq", Types.VARCHAR));
        declareParameter(new SqlParameter("p_version", Types.FLOAT));
        declareParameter(new SqlParameter("p_change_note", Types.VARCHAR));
        declareParameter(new SqlParameter("p_Created_by", Types.VARCHAR));

        declareParameter(new SqlOutParameter("p_new_idseq", Types.VARCHAR));
        declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
        declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
        compile();
      }

      public Map execute(
        String sourceFormId,
        Float newVersionNumber,
        String changeNote,
        String createdBy) {
        Map in = new HashMap();

        in.put("P_Idseq", sourceFormId);
        in.put("p_version", newVersionNumber);
        in.put("p_change_note", changeNote);
        in.put("p_Created_by", createdBy);
        System.out.println(this.getSql());
          System.out.println(this.getCallString());
          System.out.println(this.getGeneratedKeysColumnNames());
        Map out = execute(in);
        return out;
      }
    }//end of private class




  /**
   * Inner class to update the Form component.
   *
   */
   //Form is now associated with multiple protocols. So form header does not include protocols any more
  private class UpdateFormComponent extends SqlUpdate {
    public UpdateFormComponent(DataSource ds) {
      /*String updateFormSql =
        " UPDATE quest_contents_ext SET " +
        " qtl_name = ?, conte_idseq = ?, asl_name = ?, preferred_name = ?, " +
        " preferred_definition = ?, proto_idseq = ?, long_name = ?, qcdl_name = ?, " +
        " modified_by = ? " +
        " WHERE qc_idseq = ? ";
       */
       
       //update form should keep the existing proto_idseq 
       //even though the protocols are stored in a new table in 3.1
       String updateFormSql =
         " UPDATE quest_contents_ext SET " +
         " qtl_name = ?, conte_idseq = ?, asl_name = ?, preferred_name = ?, " +
         " preferred_definition = ?, long_name = ?, qcdl_name = ?, " +
         " modified_by = ? " +
         " WHERE qc_idseq = ? ";        

      this.setDataSource(ds);
      this.setSql(updateFormSql);
      declareParameter(new SqlParameter("qtl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("conte_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("asl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("preferred_name", Types.VARCHAR));
      declareParameter(new SqlParameter("preferred_definition", Types.VARCHAR));
      //declareParameter(new SqlParameter("proto_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("long_name", Types.VARCHAR));
      declareParameter(new SqlParameter("qcdl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("modified_by", Types.VARCHAR));
     declareParameter(new SqlParameter("qc_idseq", Types.VARCHAR));
      compile();
    }

    protected int updateFormFields(Form form) {

      //String protocolIdSeq = null;
      //form is now associated with mulitple forms. Form fields do not include protocols.
      /*if(form.getProtocol()!=null) {
         protocolIdSeq = form.getProtocol().getProtoIdseq();
      }
      */

      Object[] obj =
        new Object[] {
          form.getFormType(), form.getContext().getConteIdseq(), form.getAslName(),
          form.getPreferredName(), form.getPreferredDefinition(),
          //protocolIdSeq, 
          form.getLongName(),
          form.getFormCategory(), form.getModifiedBy(), form.getFormIdseq()
        };
      int res = update(obj);

      return res;
    }
  }
 /**
  * Inner class that accesses database to get all the forms
  * sort by context and protocol
  */
 class FormContextProtoQuery  extends MappingSqlQuery {
 String lastFormId = null;
 Form currentForm = null;
 List formsList = new ArrayList();
  FormContextProtoQuery() {
   super();
  }

  public void setSql() {
   String allFormsbyProtocolQueryStmt =
             " SELECT quest.qc_idseq " + " ,quest.long_name long_name " + " ,quest.preferred_name preffered_name "
                + " ,quest.preferred_definition preferred_definition " + " ,quest.CONTE_IDSEQ "
                + " ,proto_qc.PROTO_IDSEQ PROTO_IDSEQ " + " ,proto.LONG_NAME   proto_name "
                + " ,proto.preferred_name proto_preferred_name "
                + "  ,proto.preferred_definition proto_preferred_definition, proto.CONTE_IDSEQ proto_context "
                + ", accs.cs_csi_idseq, accs.cs_conte_idseq "
                + " FROM  sbrext.quest_contents_ext quest,protocols_ext proto, protocol_qc_ext proto_qc,  "
                + " (select ac_idseq, cs_csi_idseq, cs_conte_idseq  from sbrext.ac_class_view_ext where upper(CSTL_NAME) = upper('"
                + CaDSRConstants.FORM_CS_TYPE + "') and upper(CSITL_NAME) = upper('"
                + CaDSRConstants.FORM_CSI_TYPE + "')) accs "
                + " WHERE quest.QC_IDSEQ = proto_qc.QC_IDSEQ(+) and proto_qc.PROTO_IDSEQ = proto.PROTO_IDSEQ "
                + " and quest.QC_IDSEQ = accs.AC_IDSEQ(+) and quest.qtl_name = 'CRF' "
                + " AND   quest.deleted_ind = 'No' "
                + " AND   quest.latest_version_ind = 'Yes' "
                + " ORDER BY proto.conte_idseq,upper(proto.LONG_NAME),upper(quest.long_name), quest.QC_IDSEQ";

   super.setSql(allFormsbyProtocolQueryStmt);
  }

  protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
   String currContextId = rs.getString("CONTE_IDSEQ");
   String formId = rs.getString("qc_idseq");

   Form form = null;

   if (!formId.equals(lastFormId)) {
     form = new FormTransferObject();
     form.setClassifications(new ArrayList());
     form.setFormIdseq(rs.getString("qc_idseq"));                       // QC_IDSEQ
     form.setIdseq(rs.getString("qc_idseq"));
     form.setLongName(rs.getString("long_name"));                       //LONG_NAME
     form.setPreferredName(rs.getString("preffered_name"));             // PREFERRED_NAME
     form.setPreferredDefinition(rs.getString("preferred_definition")); // preferred_definition

     //setContext(new ContextTransferObject(rs.getString("context_name")));
     ContextTransferObject contextTransferObject = new ContextTransferObject();
     contextTransferObject.setConteIdseq(rs.getString("CONTE_IDSEQ")); //CONTE_IDSEQ
     form.setContext(contextTransferObject);
      if (rs.getString("PROTO_IDSEQ") != null &&
      (rs.getString("PROTO_IDSEQ")).length() >0) {
       Protocol protocol = new ProtocolTransferObject();
       protocol.setLongName(rs.getString("proto_name"));
       protocol.setPreferredName(rs.getString("proto_preferred_name"));
       protocol.setLongName(rs.getString("proto_name"));
       protocol.setPreferredDefinition(rs.getString("proto_preferred_definition"));
       protocol.setIdseq(rs.getString("PROTO_IDSEQ"));
       protocol.setProtoIdseq(rs.getString("PROTO_IDSEQ"));
       protocol.setConteIdseq(rs.getString("proto_context"));
       if (form.getProtocols()== null)
         form.setProtocols(new ArrayList());
       form.getProtocols().add(protocol);


     }

     lastFormId = formId;
     currentForm = form;
     formsList.add(form);
   } else
   {
     form = currentForm;
   }
   if ((rs.getString("CS_CSI_IDSEQ")!= null &&
      (rs.getString("CS_CSI_IDSEQ")).length() >0)) {
     ClassSchemeItem csi = new CSITransferObject();
     csi.setCsCsiIdseq(rs.getString("CS_CSI_IDSEQ"));
     csi.setCsConteIdseq(rs.getString("cs_conte_idseq"));
  //   csi.setClassSchemeItemType(rs.getString("CSITL_NAME"));
  //   csi.setCsType(rs.getString("CSTL_NAME"));
     if (form.getClassifications() == null)
      form.setClassifications(new ArrayList());

     form.getClassifications().add(csi);
   }
   return form;
  }

  protected List getFormCollection() {

    return formsList;
  }

 }

  /**
  * Inner class that accesses database to get all the templates
  * sort by context and protocol
  */
 class TemplateByContextQuery  extends MappingSqlQuery {
 String lastFormId = null;
 Form currentForm = null;
 List formsList = new ArrayList();

  TemplateByContextQuery() {
   super();
  }

  public void setSql() {
   String allTemplatesQueryStmt = "SELECT  qc_idseq ,long_name "
                    +" ,preferred_name  "
                    +" ,preferred_definition "
                    +" ,context.CONTE_IDSEQ "
                    +" ,context.NAME  "
                    + ", accs.cs_csi_idseq "
                    +" FROM  sbrext.quest_contents_ext quest, contexts context, "
                    + " (select ac_idseq, cs_csi_idseq from sbrext.ac_class_view_ext where upper(CSTL_NAME) = upper('"
                    + CaDSRConstants.TEMPLATE_CS_TYPE + "') and upper(CSITL_NAME) = upper('"
                    + CaDSRConstants.TEMPLATE_CSI_TYPE + "')) accs "
                    +" where  context.CONTE_IDSEQ=quest.CONTE_IDSEQ "
                    + "AND  quest.QC_IDSEQ = accs.AC_IDSEQ(+)"
                    +" AND   deleted_ind = 'No' "
                    +" AND   latest_version_ind = 'Yes' "
                    +" AND   qtl_name = 'TEMPLATE' "
                    +" ORDER BY conte_idseq, upper(long_name) " ;
   log.debug(allTemplatesQueryStmt);
   super.setSql(allTemplatesQueryStmt);
  }

  protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
   String currContextId = rs.getString("CONTE_IDSEQ");
   String formId = rs.getString("qc_idseq");

   Form form = null;

   if (!formId.equals(lastFormId)) {
   form = new FormTransferObject();
   form.setFormIdseq(rs.getString("qc_idseq"));                       // QC_IDSEQ
   form.setIdseq(rs.getString("qc_idseq"));
   form.setLongName(rs.getString("long_name"));                       //LONG_NAME
   form.setPreferredName(rs.getString("preferred_name"));             // PREFERRED_NAME
   form.setPreferredDefinition(rs.getString("preferred_definition")); // preferred_definition

   //setContext(new ContextTransferObject(rs.getString("context_name")));
   ContextTransferObject contextTransferObject = new ContextTransferObject();
   contextTransferObject.setConteIdseq(rs.getString("CONTE_IDSEQ")); //CONTE_IDSEQ
   contextTransferObject.setName(rs.getString("NAME")); //context name
   form.setContext(contextTransferObject);
   formsList.add(form);
   } else
   {
     form = currentForm;
   }
   if ((rs.getString("CS_CSI_IDSEQ")!= null &&
      (rs.getString("CS_CSI_IDSEQ")).length() >0)) {
     ClassSchemeItem csi = new CSITransferObject();
     csi.setCsCsiIdseq(rs.getString("CS_CSI_IDSEQ"));
  //   csi.setClassSchemeItemType(rs.getString("CSITL_NAME"));
  //   csi.setCsType(rs.getString("CSTL_NAME"));
     if (form.getClassifications() == null)
      form.setClassifications(new ArrayList());

     form.getClassifications().add(csi);
   }
   return form;
  }

  protected List getAllTemplates() {
      execute();
      return formsList;

    }

 }

   /**
  * Inner class that accesses database to get all the templates
  * sort by context and protocol
  */
 class TemplateForContextIdQuery  extends MappingSqlQuery {
  TemplateForContextIdQuery() {
   super();
  }
  TemplateForContextIdQuery (DataSource ds)
    {
      super(ds, " SELECT distinct qc.qc_idseq "
                                       +" ,qc.long_name "
                                       +" ,qc.preferred_name "
                                       +" ,qc.preferred_definition "
                                       +" ,qc.qcdl_name "
                                       +" ,acs.cs_csi_idseq "
                                       +" FROM  sbrext.quest_contents_ext qc, contexts context"
                                       +" ,sbr.ac_csi acs "
                                       +" WHERE "
                                       +" context.CONTE_IDSEQ=qc.CONTE_IDSEQ "
                                       +" AND    qcdl_name is not null "
                                       +" AND    qc.conte_idseq =  ? "
                                       +" AND   qc.deleted_ind = 'No' "
                                       +" AND   qc.latest_version_ind = 'Yes'  "
                                       +" AND   qc.qtl_name = 'TEMPLATE' "
                                       +" AND   qc.qc_idseq = acs.ac_idseq "
                                       +" ORDER BY cs_csi_idseq,qc.qcdl_name,long_name ");

      declareParameter(new SqlParameter("ContextIDSeq", Types.VARCHAR));
      compile();
    }

  protected Object mapRow(ResultSet rs, int rownum) throws SQLException {

   Form form = new FormTransferObject();
   form.setFormIdseq(rs.getString("qc_idseq"));                       // QC_IDSEQ
   form.setIdseq(rs.getString("qc_idseq"));
   form.setLongName(rs.getString("long_name"));                       //LONG_NAME
   form.setPreferredName(rs.getString("preferred_name"));             // PREFERRED_NAME
   form.setPreferredDefinition(rs.getString("preferred_definition")); // preferred_definition
   form.setFormCategory(rs.getString("qcdl_name"));   //form category

   String cscsiId = rs.getString("cs_csi_idseq");
   if (cscsiId != null && !cscsiId.equals(""))
   {
     CSITransferObject csi = new CSITransferObject();
     csi.setCsCsiIdseq(cscsiId);
     Collection formCs = new ArrayList();
     formCs.add(csi);
     form.setClassifications(formCs);
   }

   return form;
  }
 }

  class PublishedFormsByTypeQuery extends MappingSqlQuery {
  String lastFormId = null;
  Form currentForm = null;
  List formsList = new ArrayList();

    PublishedFormsByTypeQuery(DataSource ds)  {
      super(ds, " select  published.QC_IDSEQ "
                                    +" ,published.QC_LONG_NAME "
                                    +",published.QC_PREFERRED_NAME"
                                    +" ,published.QC_PREFERRED_DEFINITION "
                                    +" ,published.FORM_CONTE_IDSEQ "
                                    +" ,published.FORM_CONTEXT "
                                    + ", accs.cs_csi_idseq "
                                    + ", accs.cs_conte_idseq"
                                    +" from published_forms_view published, "
                    + " (select ac_idseq, cs_csi_idseq, cs_conte_idseq from sbrext.ac_class_view_ext where upper(CSTL_NAME) = upper('"
                    + CaDSRConstants.TEMPLATE_CS_TYPE + "') and upper(CSITL_NAME) = upper('"
                    + CaDSRConstants.TEMPLATE_CSI_TYPE + "')) accs "
                                    +" where  +  published.QC_IDSEQ = accs.AC_IDSEQ(+) and "
                                    +" published.QTL_NAME = ? "
                                    +" and published.PUBLISH_CONTE_IDSEQ = ? "
                                    +" order by upper(QC_LONG_NAME), published.QC_IDSEQ " );
      declareParameter(new SqlParameter("form_type", Types.VARCHAR));
      declareParameter(new SqlParameter("context_id", Types.VARCHAR));
      compile();
    }

  protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
   String formId = rs.getString("qc_idseq");

   Form form = null;

   if (!formId.equals(lastFormId)) {

     form = new FormTransferObject();
     form.setFormIdseq(rs.getString("qc_idseq"));                       // QC_IDSEQ
     form.setIdseq(rs.getString("qc_idseq"));
     form.setLongName(rs.getString("qc_long_name"));                       //LONG_NAME
     form.setPreferredName(rs.getString("qc_preferred_name"));             // PREFERRED_NAME
     form.setPreferredDefinition(rs.getString("qc_preferred_definition")); // preferred_definition
     ContextTransferObject contextTransferObject = new ContextTransferObject();
     contextTransferObject.setConteIdseq(rs.getString("FORM_CONTE_IDSEQ")); //CONTE_IDSEQ
     contextTransferObject.setName(rs.getString("FORM_CONTEXT")); //context name
     form.setContext(contextTransferObject);

     lastFormId = formId;
     currentForm = form;
     formsList.add(form);
   } else   {
     form = currentForm;
   }
   if ((rs.getString("CS_CSI_IDSEQ")!= null &&
      (rs.getString("CS_CSI_IDSEQ")).length() >0)) {
     ClassSchemeItem csi = new CSITransferObject();
     csi.setCsCsiIdseq(rs.getString("CS_CSI_IDSEQ"));
     csi.setCsConteIdseq(rs.getString("CS_CONTE_IDSEQ"));
  //   csi.setClassSchemeItemType(rs.getString("CSITL_NAME"));
  //   csi.setCsType(rs.getString("CSTL_NAME"));
     if (form.getClassifications() == null)
      form.setClassifications(new ArrayList());

     form.getClassifications().add(csi);
   }
   return form;
  }

    protected List getPublishedForms( String formType, String contextId) {
      Object[] obj =
        new Object[] {formType,
        contextId
        };
      execute(obj);
      return formsList;

    }
 }

  class PublishedProtocolsQuery extends MappingSqlQuery {

    PublishedProtocolsQuery(DataSource ds)  {
      super(ds, " select distinct proto_idseq, proto.preferred_name "
                                            +" ,proto.long_name ,proto.preferred_definition "
                                            +" ,proto.conte_idseq "
                                            +" from protocols_ext proto "
                                            +" , published_forms_view "
                                            +" where "
                                            +" proto.PROTO_IDSEQ=published_forms_view.PROTOCOL_IDSEQ "
                                            +" and proto.PROTO_IDSEQ=published_forms_view.PROTOCOL_IDSEQ "
                                            +" and   proto.deleted_ind = 'No' "
                                            +" and	 proto.latest_version_ind = 'Yes' "
                                            +" and   PUBLISH_CONTE_IDSEQ=? "
                                            +" order by upper(proto.long_name) " );
      declareParameter(new SqlParameter("context_id", Types.VARCHAR));
      compile();
    }

  protected Object mapRow(ResultSet rs, int rownum) throws SQLException {

   Protocol protocol = new ProtocolTransferObject();
   protocol.setIdseq(rs.getString("proto_idseq"));
   protocol.setPreferredName(rs.getString("preferred_name"));
   protocol.setLongName(rs.getString("long_name"));
   protocol.setPreferredDefinition(rs.getString("preferred_definition"));
   protocol.setConteIdseq(rs.getString("conte_idseq"));

   return protocol;
  }

    protected List getProtocols( String contextId) {
      Object[] obj =
        new Object[] {
        contextId
        };

      return execute(obj);

    }
 }

  class TemplateTypeQuery extends MappingSqlQuery {

    TemplateTypeQuery(DataSource ds)  {

      super(ds, "SELECT distinct qcdl_name "
                                       +"FROM   quest_contents_ext "
                                       +"WHERE  qtl_name = 'TEMPLATE' "
                                       +"AND    conte_idseq = ? "
                                       +"AND    deleted_ind = 'No' "
                                       +"AND    latest_version_ind = 'Yes' "
                                       +"AND    qcdl_name is not null "
                                       +"ORDER BY upper(qcdl_name) " );

      declareParameter(new SqlParameter("context_id", Types.VARCHAR));
      compile();
    }

  protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
   return rs.getString(1);
  }

    protected List getTemplateTypes( String contextId) {
      Object[] obj =
        new Object[] {
        contextId
        };

      return execute(obj);

    }
 }

 /**
  * Inner class that accesses database to get all the forms
  * versions by the public id.
  */
 class  FormVersionsQuery  extends MappingSqlQuery {
    List versionList = null;

    public FormVersionsQuery() {
    super();
   }

   public void setSql(int publicId) {
   String allFormVersionsByPublicId =
      " SELECT qc_idseq, version, change_note, latest_version_ind from QUEST_CONTENTS_EXT crf where QC_ID=" + publicId + " order by version";
   super.setSql(allFormVersionsByPublicId);
   compile();
  }

  protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
    Float versionNumber = rs.getFloat("version");
    String formId = rs.getString("qc_idseq");
    String changeNote = rs.getString("change_note");
    String latestVersionInd = rs.getString("latest_version_ind");

    Version version = new Version();
    version.setId(formId);
    version.setVersionNumber(versionNumber);
    version.setChangeNote(changeNote);
    version.setLatestVersionIndicator("YES".equalsIgnoreCase(latestVersionInd));

    if (versionList == null){
        versionList = new ArrayList();
    }
    versionList.add(version);

    return version;
  }

  protected List getFormVersions(int publicId) {
    setSql(publicId);
    execute();
    return versionList;
  }

 }

    /**
     * Inner class that accesses database to get the maximum form version
     * by the form public id.
     */
    class  MaxFormVersionsQuery  extends MappingSqlQuery {
       public MaxFormVersionsQuery() {
       super();
      }

      public void setSql(int publicId) {
      String maxFormVersionsByPublicId =
               " SELECT MAX(version) from QUEST_CONTENTS_EXT crf where (crf.QTL_NAME = 'CRF' or crf.QTL_NAME = 'TEMPLATE') AND QC_ID = " + publicId;
      super.setSql(maxFormVersionsByPublicId);
      compile();
     }

     protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
       Float maxVersion = rs.getFloat(1);
       return maxVersion;
     }

    }


    private class SetLatestVersion extends SqlUpdate {
        public SetLatestVersion(DataSource ds){
            this.setDataSource(ds);
        }

        protected int updateVersionInfo(String formIdSeq, String changeNote, boolean lastestVersionInd, String modifiedBy){
          String latestVersionIndStr = "No";
          if (lastestVersionInd){
              latestVersionIndStr = "Yes";
          }
          String setLatestVersionSql =
            "update quest_contents_ext set latest_version_ind = '" + latestVersionIndStr + "', change_note='"
            + changeNote + "',  modified_by = '" + modifiedBy + "' where qc_idseq='" + formIdSeq + "'";
          setSql(setLatestVersionSql);
          compile();
          int res = update();
          return res;
        }
    }


    private void fetchProtocols( Collection forms){
        if (forms == null || forms.isEmpty()){
            return;
        }

        Iterator it = forms.iterator();
        while (it.hasNext()){
            Form form = (Form)it.next();
            form.setProtocols(getProtocols(form));
        }
        return;
    }

    private List getProtocols(Form form){
        FormProtocolByFormPrimaryKey query = new FormProtocolByFormPrimaryKey();
        query.setDataSource(getDataSource());
        query.setSql();
        List protocols = query.execute(form.getFormIdseq());
        return protocols;
    }

    private class RemoveFormProtocolQuery extends SqlUpdate {
      public RemoveFormProtocolQuery(DataSource ds) {
        this.setDataSource(ds);
      }

    protected int removeFormProtocol( String formIdseq, String protocolIdseq) {
        String sql =
              "delete from  protocol_qc_ext where qc_idseq= '" + formIdseq + "' AND proto_idseq = '" + protocolIdseq+ "'";
          this.setSql(sql);
          compile();
          int res = update();
          return res;
      }
    protected int removeFormProtocols( String formIdseq, Collection protocolIds) {
        if (protocolIds==null || protocolIds.size()==0){
            return 0;
        }
        String delimtedBy = ",";
        StringBuffer sbuf = new StringBuffer();
        Iterator it = protocolIds.iterator();
        while (it.hasNext()){
            String pid = (String)it.next();
            sbuf.append(delimtedBy).append("'").append(pid).append("'");
        }
        String ids = sbuf.toString().substring(delimtedBy.length());
        String sql =
              "delete from  protocol_qc_ext where qc_idseq= '" + formIdseq + "' AND proto_idseq in( " + ids+ ")";
          setSql(sql);
          compile();
          int res = update();
          return res;
      }
    }

    private class AddFormProtocolQuery extends SqlUpdate {
      public AddFormProtocolQuery(DataSource ds) {
        this.setDataSource(ds);
      }

    protected int addFormProtocol( String formIdseq, String protocolIdseq, String createdBy) {
        String sql =
              "insert into protocol_qc_ext  (qc_idseq, proto_idseq, created_by ) values ('" + formIdseq + "', '" + protocolIdseq + "', '" + createdBy + "' )";
          this.setSql(sql);
          compile();
          int res = update();
          return res;
      }
    }
   /**
    * Inner class that accesses database to get all the forms
    * sort by context and protocol
    */
   class FormProtoQuery  extends MappingSqlQuery {
   String lastFormId = null;
   Form currentForm = null;
   List formsList = new ArrayList();
    FormProtoQuery(DataSource ds) {
       super(
         ds,   " SELECT quest.qc_idseq " + " ,quest.long_name long_name " + " ,quest.preferred_name preffered_name "
                  + " ,quest.preferred_definition preferred_definition " + " ,quest.CONTE_IDSEQ "
                  + " ,proto_qc.PROTO_IDSEQ PROTO_IDSEQ " + " ,proto.LONG_NAME   proto_name "
                  + " ,proto.preferred_name proto_preferred_name "
                  + "  ,proto.preferred_definition proto_preferred_definition, proto.CONTE_IDSEQ proto_context "
                  + ", accs.cs_csi_idseq, accs.cs_conte_idseq "
                  + " FROM  sbrext.quest_contents_ext quest,protocols_ext proto, protocol_qc_ext proto_qc,  "
                  + " (select ac_idseq, cs_csi_idseq, cs_conte_idseq  from sbrext.ac_class_view_ext where upper(CSTL_NAME) = upper('"
                  + CaDSRConstants.FORM_CS_TYPE + "') and upper(CSITL_NAME) = upper('"
                  + CaDSRConstants.FORM_CSI_TYPE + "')) accs "
                  + " WHERE quest.QC_IDSEQ = proto_qc.QC_IDSEQ(+) and proto_qc.PROTO_IDSEQ = proto.PROTO_IDSEQ "
                  + " and quest.QC_IDSEQ = accs.AC_IDSEQ(+) and quest.qtl_name = 'CRF' "
                  + " AND   quest.deleted_ind = 'No' "
                  + " AND   quest.latest_version_ind = 'Yes' "
                  + " AND  proto.conte_idseq = ? "
                  + " ORDER BY upper(proto.LONG_NAME),upper(quest.long_name), quest.QC_IDSEQ");

       declareParameter(new SqlParameter("CONTE_IDSEQ", Types.VARCHAR));
       compile();
    }

    protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
     String formId = rs.getString("qc_idseq");

     Form form = null;

     if (!formId.equals(lastFormId)) {
       form = new FormTransferObject();
       form.setClassifications(new ArrayList());
       form.setFormIdseq(rs.getString("qc_idseq"));                       // QC_IDSEQ
       form.setIdseq(rs.getString("qc_idseq"));
       form.setLongName(rs.getString("long_name"));                       //LONG_NAME
       form.setPreferredName(rs.getString("preffered_name"));             // PREFERRED_NAME
       form.setPreferredDefinition(rs.getString("preferred_definition")); // preferred_definition

       //setContext(new ContextTransferObject(rs.getString("context_name")));
       ContextTransferObject contextTransferObject = new ContextTransferObject();
       contextTransferObject.setConteIdseq(rs.getString("CONTE_IDSEQ")); //CONTE_IDSEQ
       form.setContext(contextTransferObject);
        if (rs.getString("PROTO_IDSEQ") != null &&
        (rs.getString("PROTO_IDSEQ")).length() >0) {
         Protocol protocol = new ProtocolTransferObject();
         protocol.setLongName(rs.getString("proto_name"));
         protocol.setPreferredName(rs.getString("proto_preferred_name"));
         protocol.setLongName(rs.getString("proto_name"));
         protocol.setPreferredDefinition(rs.getString("proto_preferred_definition"));
         protocol.setIdseq(rs.getString("PROTO_IDSEQ"));
         protocol.setProtoIdseq(rs.getString("PROTO_IDSEQ"));
         protocol.setConteIdseq(rs.getString("proto_context"));
         if (form.getProtocols()== null)
           form.setProtocols(new ArrayList());
         form.getProtocols().add(protocol);


       }

       lastFormId = formId;
       currentForm = form;
       formsList.add(form);
     } else
     {
       form = currentForm;
     }
     if ((rs.getString("CS_CSI_IDSEQ")!= null &&
        (rs.getString("CS_CSI_IDSEQ")).length() >0)) {
       ClassSchemeItem csi = new CSITransferObject();
       csi.setCsCsiIdseq(rs.getString("CS_CSI_IDSEQ"));
       csi.setCsConteIdseq(rs.getString("cs_conte_idseq"));
    //   csi.setClassSchemeItemType(rs.getString("CSITL_NAME"));
    //   csi.setCsType(rs.getString("CSTL_NAME"));
       if (form.getClassifications() == null)
        form.setClassifications(new ArrayList());

       form.getClassifications().add(csi);
     }
     return form;
    }

    protected List getFormCollection(String contextIdseq) {
       Object[] obj =
         new Object[] {
            contextIdseq
         };

      execute(obj);
       
      return formsList;
    }

   }

}
