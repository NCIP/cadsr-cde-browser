package gov.nih.nci.ncicb.cadsr.persistence.bc4j.handler;

import gov.nih.nci.ncicb.cadsr.common.bc4j.BC4JConnectionManager;
import gov.nih.nci.ncicb.cadsr.crfbuilder.exception.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.FormBuilderServiceImpl;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.handler.FormHandler;

import oracle.cle.persistence.Handler;
import oracle.cle.persistence.HandlerFactory;

import oracle.jbo.ViewObject;
import oracle.jbo.common.ampool.SessionCookie;

import gov.nih.nci.ncicb.cadsr.dto.bc4j.BC4JFormTransferObject;


public class FormHandlerImpl extends Handler implements FormHandler {
  String providerName;
  BC4JConnectionManager manager;

  public FormHandlerImpl() {
    super();
    providerName = this.getDeploymentKey(Form.class);
    manager = BC4JConnectionManager.getInstance();
  }

  public Class getReferenceClass() {
    return Form.class;
  }

  public Form findFormByPrimaryKey(String pk, Object sessionId)
    throws FormBuilderException {
    FormBuilderServiceImpl myService = null;
    Form myForm = null;

    try {
      myService =
        (FormBuilderServiceImpl) manager.getConnection(
          (String) sessionId, "formbuilder_bc4j");
      System.out.println("App Module " + myService.toString());
      myForm = myService.findFormByPK(pk);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new FormBuilderException(
        "Exception occured in finding Form by Primary Key", ex);
    } finally {
      try {
        manager.releaseConnection(
          (String) sessionId, SessionCookie.SHARED_MANAGED_RELEASE_MODE,
          "formbuilder_bc4j");
        System.out.println("App Module released successfully");
      } catch (Exception ex) {
        System.out.println(
          "Exception occured in releasing Application Module: FormBuilderService");
        ex.printStackTrace();
      }
    }

    return myForm;
  }

  public int deleteFormByPrimaryKey(String pk, Object sessionId)
    throws FormBuilderException {
    return 0;
  }

  public int createForm(Form crf, Object sessionId) throws FormBuilderException {
    return 0;
  }

  public int editForm(Form crf, Object sessionId) throws FormBuilderException {
    return 0;
  }

  public String copyForm(Form sourceForm, Form targetForm, Object sessionId)
    throws FormBuilderException {
    FormBuilderServiceImpl myService = null;
    String newFormIdseq = null;
    try  {
      myService =
        (FormBuilderServiceImpl) manager.getConnection(
          (String) sessionId, "formbuilder_bc4j");
      newFormIdseq = myService.copyForm(sourceForm,targetForm);
    } catch (Exception ex)  {
      ex.printStackTrace();
      throw new FormBuilderException(
        "Exception occured in copying Form ", ex);
    } finally  {
      try  {
        manager.releaseConnection(
          (String) sessionId, SessionCookie.SHARED_MANAGED_RELEASE_MODE,
          "formbuilder_bc4j");
        System.out.println("App Module released successfully");
      } catch (Exception ex)  {
        System.out.println(
          "Exception occured in releasing Application Module: FormBuilderService");
        ex.printStackTrace();
      }
      
    }
    
    return newFormIdseq;
  }

  public int addModules(Module[] blocks) throws FormBuilderException {
    return 0;
  }

  private void printWhereClause(Object sessionId) {
    FormBuilderServiceImpl myService = null;

    try {
      myService =
        (FormBuilderServiceImpl) manager.getConnection(
          (String) sessionId, "formbuilder_bc4j");
      System.out.println("App Module " + myService.toString());

      ViewObject view = myService.getFormsView();
      System.out.println(
        "Forms view object where clause is " + view.getWhereClause());

      ViewObject modRecs = myService.getFormModuleRelView1();
      System.out.println(
        "Module Rec count is " + modRecs.getEstimatedRowCount());
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        //releaseConnection(sessionId);
        manager.releaseConnection(
          (String) sessionId, SessionCookie.SHARED_MANAGED_RELEASE_MODE,
          "formbuilder_bc4j");
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    //Integer sessionId = new Integer(1);
    String sessionId1 = "1";
    String sessionId2 = "2";
    Form srcForm = null;
    Form tarForm = null;

    try {
      FormHandlerImpl fh =
        (FormHandlerImpl) HandlerFactory.getHandler(Form.class);

      System.out.println("******* Session 1 *********");
      //A7CACF3B-5354-0580-E034-0003BA0B1A09
      srcForm = new BC4JFormTransferObject();
      tarForm = new BC4JFormTransferObject();
      srcForm.setFormIdseq("A7CACF3B-5354-0580-E034-0003BA0B1A09");
      tarForm.setPreferredName("ADVERSE EVENTS_COPY_10");
      tarForm.setLongName("ADVERSE EVENTS_COPY_10");
      tarForm.setPreferredDefinition("Fifth copy of ADVERSE EVENTS");
      tarForm.setAslName("UNDER DEVELOPMENT");
      tarForm.setVersion(new Float(1.0f));
      tarForm.setConteIdseq("A5599257-A08F-41D1-E034-080020C9C0E0");
      tarForm.setProtoIdseq("A55B7C47-A942-50BA-E034-080020C9C0E0");

      String newIdseq = fh.copyForm(srcForm,tarForm,sessionId1);
      System.out.println("New form idseq is "+newIdseq);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
