package gov.nih.nci.ncicb.cadsr.formbuilder.service.ejb;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.servicelocator.Locate;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.rmi.RemoteException;

import java.util.HashMap;
import java.util.Map;


public abstract class FormBuilderDynamicServiceDelegateImpl
  implements InvocationHandler, Locate {
  protected Map formBuilderMethodMap;
  protected Object formbuilderEJBObj;
  private ServiceLocator serviceLocator = null;

  public FormBuilderDynamicServiceDelegateImpl(ServiceLocator locator)
    throws FormBuilderException {
    serviceLocator = locator;
    init();
  }

  protected abstract void init() throws FormBuilderException;

  /**
   * Caches all the methods in FormBuilderServiceDelegate object
   *
   * @param obj
   */
  protected void loadMethods(Object obj) {
    formBuilderMethodMap = new HashMap();

    Method[] formBuilderMethods = obj.getClass().getMethods();

    for (int i = 0; i < formBuilderMethods.length; i++) {
      formBuilderMethodMap.put(
        formBuilderMethods[i].getName(), formBuilderMethods[i]);
    }
  }

  public Object invoke(
    Object proxy,
    Method method,
    Object[] args) throws Throwable {
    try {
      Method serviceMethod =
        (Method) formBuilderMethodMap.get(method.getName());

      if (serviceMethod != null) {
        return serviceMethod.invoke(formbuilderEJBObj, args);
      }
      else {
        throw new NoSuchMethodException(
          " FormBuilder service does not implement " + method.getName());
      }
    }
    catch (InvocationTargetException ex) {
      if (ex.getTargetException() instanceof RemoteException) {
        RemoteException remoteExp = (RemoteException) ex.getTargetException();

        if (remoteExp.detail instanceof DMLException) {
          DMLException dmlExp = (DMLException) remoteExp.detail;
          FormBuilderException formExp = new FormBuilderException(dmlExp);
          formExp.setErrorCode(dmlExp.getErrorCode());
          throw formExp;
        }
        else {
          throw new FatalException(remoteExp);
        }
      }
      else {
        throw new FatalException(ex.getTargetException());
      }
    }
  }

  public ServiceLocator getServiceLocator() {
    return serviceLocator;
  }

  public void setServiceLocator(ServiceLocator newServiceLocator) {
    serviceLocator = newServiceLocator;
  }
}
