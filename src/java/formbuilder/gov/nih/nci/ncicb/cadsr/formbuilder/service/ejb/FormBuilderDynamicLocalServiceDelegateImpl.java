package gov.nih.nci.ncicb.cadsr.formbuilder.service.ejb;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.ejb.FormBuilderLocalHome;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.ejb.FormBuilderDynamicServiceDelegateImpl;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;

import javax.ejb.CreateException;


public class FormBuilderDynamicLocalServiceDelegateImpl
  extends FormBuilderDynamicServiceDelegateImpl {
  public FormBuilderDynamicLocalServiceDelegateImpl(ServiceLocator locator)
    throws FormBuilderException {
    super(locator);
  }

  protected void init() throws FormBuilderException {
    try {
      FormBuilderLocalHome home =
        (FormBuilderLocalHome) getServiceLocator().getLocalHome(
          "ejb/FormBuilder");
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
  }
}
