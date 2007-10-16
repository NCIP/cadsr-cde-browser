package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormAction;

import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FormLockerSessionListener implements HttpSessionListener{

    protected static Log log = LogFactory.getLog(FormLockerSessionListener.class.getName());

    public void sessionCreated(HttpSessionEvent se) {
        if (log.isDebugEnabled()){
            log.debug("New session " + se.getSession().getId() + " is created");
        }
        return;
    }

    public void sessionDestroyed(HttpSessionEvent se) {
         if (log.isDebugEnabled()){
             log.debug("Session " + se.getSession().getId() + " is about to be destroyed.");
         }
        getApplicationServiceLocator(se.getSession().getServletContext()).findLockingService().unlockFormBySession(se.getSession().getId());
    }
    
    
    protected ApplicationServiceLocator getApplicationServiceLocator(ServletContext sc)
      throws ServiceLocatorException {
      ApplicationServiceLocator appServiceLocator =
        (ApplicationServiceLocator) sc.getAttribute(
          ApplicationServiceLocator.APPLICATION_SERVICE_LOCATOR_CLASS_KEY);
      if(appServiceLocator==null)
        throw new ServiceLocatorException("Could no find ApplicationServiceLocator with key ="+ ApplicationServiceLocator.APPLICATION_SERVICE_LOCATOR_CLASS_KEY);
      return appServiceLocator;
    } 
        

}
