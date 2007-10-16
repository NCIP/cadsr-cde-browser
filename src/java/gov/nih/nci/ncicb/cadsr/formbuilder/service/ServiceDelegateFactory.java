package gov.nih.nci.ncicb.cadsr.formbuilder.service;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import java.util.*;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

public class ServiceDelegateFactory implements PlugIn {
  private static Log log = LogFactory.getLog(ServiceDelegateFactory.class.getName());
  private static final String SERVICE_LOCATOR_TYPE="gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator";
  private String serviceClassName =
    "gov.nih.nci.ncicb.cadsr.formbuilder.service.ejb.FormBuilderDynamicRemoteServiceDelegateImpl";
  private ServiceLocator locator = null;    
  private ActionServlet servlet;
  private static Map cache = Collections.synchronizedMap(new HashMap());
  public ServiceDelegateFactory() {
  }

  public void destroy() {
    servlet.getServletContext().setAttribute(
      FormBuilderConstants.SERVICE_DELEGATE_FACTORY_KEY, null);
  }

  public void init(
    ActionServlet servlet,
    ModuleConfig config) throws ServletException {
    this.servlet = servlet;
    String locatorClassName = servlet.getInitParameter(ServiceLocator.SERVICE_LOCATOR_CLASS_KEY);
    
    locator = ServiceLocatorFactory.getLocator(locatorClassName);
    log.info("Service Locator" + locator);
    servlet.getServletContext().setAttribute(
      FormBuilderConstants.SERVICE_DELEGATE_FACTORY_KEY, this);
  }

  public FormBuilderServiceDelegate createService()
    throws ServiceStartupException {
    FormBuilderServiceDelegate proxy = null;

    try {
      /*String className = locator.getString(FormBuilderConstants.SERVICE_DELEGATE_CLASS_KEY);
      if (className != null) {
        serviceClassName = className;
      }*/

      Class serviceClass = Class.forName(getServiceClassName());
      Class[] paramTypes = new Class[1];
      paramTypes[0]= Class.forName(SERVICE_LOCATOR_TYPE);
      Constructor contruct= serviceClass.getConstructor(paramTypes);
      
      Object[] objTypes = new Object[1];
      objTypes[0]=locator;
      
      Object serviceInstance = contruct.newInstance(objTypes);

      Class[] serviceInterface =  new Class[]{FormBuilderServiceDelegate.class};
      proxy = (FormBuilderServiceDelegate)Proxy.newProxyInstance(
      	Thread.currentThread().getContextClassLoader(),serviceInterface,(InvocationHandler)serviceInstance);
      log.trace("proxycreate done");
    }
    catch (Exception ex) {
      log.error("Unable to create new service", ex);
      throw new ServiceStartupException("Unable to create new service", ex);
    }

    return proxy;
  }

  public FormBuilderServiceDelegate findService()
    throws ServiceStartupException {
    FormBuilderServiceDelegate proxy = null;
    String key = this.getServiceClassName();
    if (cache.containsKey(key)) {
      proxy = (FormBuilderServiceDelegate)cache.get(this.getServiceClassName());
    }
    else {
      proxy = this.createService();
      cache.put(key,proxy);
    }
    return proxy;
  }

  public String getServiceClassName() {
    String className = locator.getString(FormBuilderConstants.SERVICE_DELEGATE_CLASS_KEY);
    if (className != null) {
      serviceClassName = className;
    }
    return serviceClassName;
  }

  public void setServiceClassName(String className) {
    this.serviceClassName = className;
  }
  public static void main(String[] args) throws Exception
  {
      Class serviceClass = Class.forName("gov.nih.nci.ncicb.cadsr.formbuilder.service.ejb.FormBuilderDynamicRemoteServiceDelegateImpl");
      Class[] paramTypes = new Class[1];
      paramTypes[0]= Class.forName("gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator");
      
      Object[] objTypes = new Object[1];
      ServiceLocator locator= ServiceLocatorFactory.getEJBLocator();
      objTypes[0]=locator;
      Constructor contruct= serviceClass.getConstructor(paramTypes);
      Object test = contruct.newInstance(objTypes);
      //contruct.
  }
}
