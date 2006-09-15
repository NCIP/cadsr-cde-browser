package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.cle.process.ProcessResult;

import oracle.jbo.*;

//import oracle.jbo.client.*;
//import oracle.jbo.http.*;
//import oracle.jbo.common.ampool.*;

public class SessionHelper 
{
  private static Log log = LogFactory.getLog(SessionHelper.class.getName());
  public static void cleanSession(HttpServletRequest request)
  {
    HttpSession session = SessionHelper.getExistingSession(request);
    if (session != null)
    {
      for (Enumeration e = session.getAttributeNames() ; e.hasMoreElements() ;) {
         session.removeAttribute((String)e.nextElement());
         //log.trace("Removing " + (String)e.nextElement() + "from the session.");
     }
    }
  }
  
  /**
   * Starts a new session or returns the existing session.  Returns NULL if
   * no session exists and unable to start a new one.
   */
  public static HttpSession getSession(HttpServletRequest request)
  {
    return getSession(request,true);
  }
  
  /**
   * Returns an existing session or NULL if there is no existing session.
   */
  public static HttpSession getExistingSession(HttpServletRequest request)
  {
    return getSession(request,false);
  }
  
  private static HttpSession getSession(HttpServletRequest request, boolean createSession)
  {
    HttpSession session = null;
    try {
      session = request.getSession(false);
      if (session == null) {
        if (createSession)
        {
          session = request.getSession(true);
          //log.trace("Session being created.");
        }
        else
        {
          //log.trace("Session doesn't exist, not created.");
        }
      }
      else {
        //log.trace("Session already exists.");
      }
    }
    catch (Exception e) {
      log.error("Failure in controller --- request get session section.", e);
    }
    return session;
  }

  public static void putValue(HttpServletRequest request, String key, Object object)
  {
    HttpSession session = SessionHelper.getExistingSession(request);
    session.putValue(key, object);
  }

  public static void removeValue(HttpServletRequest request, String key)
  {
    HttpSession session = SessionHelper.getExistingSession(request);
    session.removeValue(key);
  }

  public static Object getValue(HttpServletRequest request, String key)
  {
    HttpSession session = SessionHelper.getExistingSession(request);
    return session.getAttribute(key);
  }

  public static void initApplicationModule(HttpServletRequest request) throws ConnectException
  {
    //HttpSession session = SessionHelper.getExistingSession(request);
    Hashtable env = new Hashtable(2);
    env.put(Context.INITIAL_CONTEXT_FACTORY, JboContext.JBO_CONTEXT_FACTORY);
    env.put(JboContext.DEPLOY_PLATFORM, JboContext.PLATFORM_LOCAL);

    ApplicationModule ccrrAM = null;
    ccrrAM = (ApplicationModule)SessionHelper.getValue(request,"ccrrAM");
    if (ccrrAM == null)
    {
      try
      {
            Context ic = new InitialContext(env);
            String theAMDefName = "ccrr.bc4j.CcrrModule";
            ApplicationModuleHome home = (ApplicationModuleHome)ic.lookup(theAMDefName);
            ccrrAM = home.create();
            String unameAndPwd = (String)SessionHelper.getValue(request,"username") +
              "/" + (String)SessionHelper.getValue(request,"password");
            log.info("user/pwd = " + unameAndPwd);
            ccrrAM.getTransaction().connect("jdbc:oracle:thin:" + unameAndPwd + "cbiodb2-d.nci.nih.gov:1521:CBDEV");
            SessionHelper.putValue(request,"ccrrAM",ccrrAM);
      }
      catch(Exception e)
      {
        log.error("Exception occurred in initApplicationModule", e);
        String errorMsg = e.getMessage();
        if (errorMsg.indexOf("JBO-26061") >= 0)
        {
          throw (new ConnectException("Invalid username or password, please try again."));
        }
        else
        {
          throw (new ConnectException("Unknown problem trying to authenticate, please try again."));
        }
      }
    }
  }
  /*public static void initDBBroker(HttpServletRequest request) throws Exception {
    try{
      DBLAccess dblAccess = DBLAccess.getDBLAccess("sbrext");
      DBBroker sbrextDBBroker = dblAccess.getDBBroker();
      SessionHelper.putValue(request,"sbrextDBBroker",sbrextDBBroker);
    }
    catch(Exception e) {
      log.trace("Error occured in initDBBroker(): " + e.getMessage());
      throw new Exception("Error initializing universal DBBroker");
    }
  }*/
  public static void invalidateSession(HttpServletRequest request) {
    HttpSession session = SessionHelper.getExistingSession(request);
    if (session != null) {
      session.invalidate();
    }
  }
   
   /**
    * This method retrieves the info bean objects defined by the Oracle
    * MVC framework from a Http Session. 
    */
   public static Object getInfoBean(HttpSession session, String key) {
   Object infoBean = null;
     if (session != null) {
        Hashtable infoBeans = (Hashtable) session.getAttribute("InfoTablePool");
        if (infoBeans != null) {
           Hashtable deGrp =(Hashtable) infoBeans.get("DataElementsGroup");
           if (deGrp != null) 
            infoBean =  ((ProcessResult) deGrp.get(key)).getValue();
         }
   }
      return infoBean;
   }
}