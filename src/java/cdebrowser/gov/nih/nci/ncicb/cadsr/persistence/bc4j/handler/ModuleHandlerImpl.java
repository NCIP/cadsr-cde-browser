package gov.nih.nci.ncicb.cadsr.persistence.bc4j.handler;

import gov.nih.nci.ncicb.cadsr.common.bc4j.BC4JConnectionManager;
import gov.nih.nci.ncicb.cadsr.crfbuilder.exception.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.FormBuilderServiceImpl;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.handler.ModuleHandler;

import oracle.cle.persistence.Handler;
import oracle.cle.persistence.HandlerFactory;

import oracle.jbo.ViewObject;
import oracle.jbo.common.ampool.SessionCookie;

public class ModuleHandlerImpl extends Handler implements ModuleHandler  {
  String providerName;
  BC4JConnectionManager manager;
  public ModuleHandlerImpl() {
    super();
    providerName = this.getDeploymentKey(Module.class);
    manager = BC4JConnectionManager.getInstance();
  }

  public Class getReferenceClass(){
    return Module.class;
  }

  public Module findModuleByPrimaryKey(String pk, Object sessionId) 
                                      throws FormBuilderException {
    FormBuilderServiceImpl myService = null;
    Module myModule = null;
    try {
      myService =
        (FormBuilderServiceImpl) manager.getConnection(
          (String) sessionId, providerName);
      System.out.println("App Module " + myService.toString());
      myModule = myService.findModuleByPK(pk);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new FormBuilderException(
        "Exception occured in finding module by Primary Key", ex);
    } finally {
      try {
        manager.releaseConnection(
          (String) sessionId, SessionCookie.SHARED_MANAGED_RELEASE_MODE,
          providerName);
        System.out.println("App Module released successfully");
      } catch (Exception ex) {
        System.out.println(
          "Exception occured in releasing Application Module: FormBuilderService");
        ex.printStackTrace();
      }
    }
    return myModule;
  }

  public int deleteModuleByPrimaryKey(String pk, Object sessionId) 
                                    throws FormBuilderException {
    return 0;
  }

  public int createModule(Module block, Object sessionId) 
                         throws FormBuilderException {
    return 0;
  }

  public int editModule(Module block, Object sessionId) 
                       throws FormBuilderException {
    int returnValue = 0;
    FormBuilderServiceImpl myService = null;
    try  {
      myService =
        (FormBuilderServiceImpl) manager.getConnection(
          (String) sessionId, providerName);
      myService.updateModule(block);
      returnValue = 1;
    } catch (Exception ex)  {
      ex.printStackTrace();
      throw new FormBuilderException(
        "Exception occured in updating module", ex);
    } finally  {
      try  {
        manager.releaseConnection(
          (String) sessionId, SessionCookie.SHARED_MANAGED_RELEASE_MODE,
          providerName);
      } catch (Exception ex)  {
        System.out.println(
          "Exception occured in releasing Application Module: FormBuilderService");
        ex.printStackTrace();
      }       
    }
    
    return returnValue;
  }

  public int addQuestions(Question[] terms) throws FormBuilderException {
    return 0;
  }
}