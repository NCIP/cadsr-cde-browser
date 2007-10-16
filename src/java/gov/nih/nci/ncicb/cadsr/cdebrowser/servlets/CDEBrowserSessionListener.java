package gov.nih.nci.ncicb.cadsr.cdebrowser.servlets;

import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CDEBrowserSessionListener implements HttpSessionListener{

    protected static Log log = LogFactory.getLog(CDEBrowserSessionListener.class.getName());

    public void sessionCreated(HttpSessionEvent se) {
        if (log.isDebugEnabled()){
            log.debug("New CDE Browser session " + se.getSession().getId() + " is created");
        }
        CDEBrowserParams.reloadInstance();
        return;
    }

    public void sessionDestroyed(HttpSessionEvent se) {
         if (log.isDebugEnabled()){
             log.debug("Session " + se.getSession().getId() + " is about to be destroyed.");
         }
    }




}
