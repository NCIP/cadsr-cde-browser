package gov.nih.nci.ncicb.cadsr.formbuilder.service.ejb;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.ejb.FormBuilderHome;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import java.rmi.RemoteException;
import javax.ejb.CreateException;

public class FormBuilderDynamicRemoteServiceDelegateImpl extends FormBuilderDynamicServiceDelegateImpl
{
  public FormBuilderDynamicRemoteServiceDelegateImpl() throws FormBuilderException
  {
    super();
  }
   protected void init() throws FormBuilderException {
    try {

      ServiceLocator locator = ServiceLocatorFactory.getLocator();
      FormBuilderHome home =
        (FormBuilderHome) locator.getRemoteHome(
          "java:comp/env/ejb/FormBuilder",
          gov.nih.nci.ncicb.cadsr.formbuilder.ejb.FormBuilderHome.class);
 
   /**    FormBuilderHome home =
       (FormBuilderHome) locator.getRemoteHome(
          "FormBuilder",
          gov.nih.nci.ncicb.cadsr.formbuilder.ejb.FormBuilderHome.class);
          
          **/
      formbuilderEJBObj = home.create();
      // Store Service methods
      loadMethods(formbuilderEJBObj);

    }
    catch (ServiceLocatorException ex) {
      // Translate Service Locator exception into
      // application exception
      throw new FormBuilderException(
        "Unable to locate FormBuilder Session EJB", ex);
    }
    catch (CreateException ex) {
      // Translate the Session create exception into
      // application exception
      throw new FormBuilderException(
        "Unable to create FormBuilder Session EJB", ex);
    }
    catch(RemoteException ex)
    {
       throw new FormBuilderException(
        "Unable to create FormBuilder Session EJB", ex);
    }
  }
}