package gov.nih.nci.ncicb.cadsr.common.util;

import oracle.jbo.*;
import oracle.jbo.server.*;
import oracle.jbo.common.ampool.PoolMgr;
import oracle.jbo.common.ampool.ApplicationPool;
import java.util.Properties;
import oracle.jbo.common.ampool.SessionCookie;


public class BC4JHelper  {
  public BC4JHelper() {
  }

  public static ViewObject createRuntimeViewObject(String sqlStmt
                                                  ,ApplicationModule myAppModule)
                           throws Exception {
    ViewObject newViewObject = null;
    try {
      newViewObject = myAppModule.createViewObjectFromQueryStmt(null
                                                               ,sqlStmt);
    } 
    catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } 
        
    return newViewObject;                         
  }

  public static ApplicationPool getApplicationPool
                                  (String poolName
                                  ,String packageName
                                  ,String configName
                                  ,Properties props) 
                     throws Exception {
    ApplicationPool pool = null;
    try{
      PoolMgr mgr = oracle.jbo.common.ampool.PoolMgr.getInstance(); 
      pool = mgr.findPool(poolName,packageName,configName,props);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw new Exception
       ("Error occured in creating/retrieving application module pool "+e.getMessage());
    }
    return pool;
  }

  public static ApplicationModule getApplicationModuleFromPool
                         (SessionCookie cookie) throws Exception {

    ApplicationModule am = null;
    try {
      am = cookie.useApplicationModule();
    } 
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception("Error occured in checking out an application module "
                        +"from the application pool " + ex.getMessage());
    } 
    finally {
    }
    return am;                         
  }

  public static void returnApplicationModuleToPool 
                         (SessionCookie cookie
                         ,boolean preserveState) throws Exception {

    try {
      cookie.releaseApplicationModule(true,preserveState);
    } 
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception("Error occured in checking in an application module "
                        +"to the application pool " + ex.getMessage());
    } 
    finally {
    }
  }

  public static SessionCookie createSessionCookie 
                            (ApplicationPool amPool
                            ,String sessionId
                            ,String applicationId
                            ,Properties props) throws Exception {
    SessionCookie cookie = null;
    try {
      cookie = amPool.createSessionCookie(applicationId,sessionId,props);
    } 
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception("Error occured in creating a new session cookie "
                        + ex.getMessage());
    }
    return cookie;
  }

  public static void removeSessionCookie(ApplicationPool amPool
                                        ,SessionCookie cookie) throws Exception {
    amPool.removeSessionCookie(cookie);                                        
  }
  
  public static void main(String[] args) {
    BC4JHelper bC4JHelper = new BC4JHelper();
  }
}