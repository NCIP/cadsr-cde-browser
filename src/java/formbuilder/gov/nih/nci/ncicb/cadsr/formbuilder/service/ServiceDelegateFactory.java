package gov.nih.nci.ncicb.cadsr.formbuilder.service;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import javax.servlet.ServletException;


public class ServiceDelegateFactory implements PlugIn {
  private String serviceClassName =
    "gov.nih.nci.ncicb.cadsr.formbuilder.service.ejb.FormBuilderDynamicRemoteServiceDelegateImpl";
  private ActionServlet servlet;

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
    servlet.getServletContext().setAttribute(
      FormBuilderConstants.SERVICE_DELEGATE_FACTORY_KEY, this);
  }

  public FormBuilderServiceDelegate createService()
    throws ServiceStartupException {
    FormBuilderServiceDelegate proxy = null;

    try {
      String className =
        servlet.getInitParameter(
          FormBuilderConstants.SERVICE_DELEGATE_CLASS_KEY);
      if (className != null) {
        serviceClassName = className;
      }

      Class serviceClass = Class.forName(getServiceClassName());

      Class[] serviceInterface =  new Class[]{FormBuilderServiceDelegate.class};
      proxy = (FormBuilderServiceDelegate)Proxy.newProxyInstance(
      	Thread.currentThread().getContextClassLoader(),serviceInterface,(InvocationHandler)serviceClass.newInstance());
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServiceStartupException("Unable to create new service", ex);
    }

    return proxy;
  }

  public String getServiceClassName() {
    return serviceClassName;
  }

  public void setServiceClassName(String className) {
    this.serviceClassName = className;
  }
}
