package gov.nih.nci.ncicb.cadsr.common.util;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SessionUtils {

 public static Map  sessionObjectCache = Collections.synchronizedMap(new HashMap());
 private static Map  sessionObjectCacheTimeout = Collections.synchronizedMap(new HashMap());
 private static int CACHE_TIMEOUT_VALUE = 1800000;
 protected static Log log = LogFactory.getLog(SessionUtils.class.getName());
  public SessionUtils() {
  }

 public static void setPreviousSessionValues(HttpServletRequest request)
 {
      String previousSessionId = request.getParameter(CaDSRConstants.PREVIOUS_SESSION_ID);
      log.error("SessionUtil.setPreviousSessionValues at :"+TimeUtils.getEasternTime());
        
    synchronized(sessionObjectCache)
    {
       log.error("SessionUtil.setPreviousSessionValues(synchronized Start) at :"+TimeUtils.getEasternTime());
       if(previousSessionId!=null)
       {
          Map map = (Map)SessionUtils.sessionObjectCache.get(previousSessionId);
          Set keys = (Set)map.get(CaDSRConstants.GLOBAL_SESSION_KEYS);
          Map objectMap = (Map)map.get(CaDSRConstants.GLOBAL_SESSION_MAP);
          if(keys!=null&&objectMap!=null)
        {
          Iterator keyIt  = keys.iterator();
          while(keyIt.hasNext())
          {
            String key = (String)keyIt.next();
            Object obj = objectMap.get(key);
            request.getSession().setAttribute(key,obj);
          }
        }
        log.error("SessionUtil.setPreviousSessionValues(synchronized End) at :"+TimeUtils.getEasternTime());
       } 
    }
    log.error("SessionUtil.setPreviousSessionValues end at :"+TimeUtils.getEasternTime());
 }
 /**
  public static void addGlobalSessionKey(
    HttpSession session,
    String key) {
    if (session != null) {
      Collection keys =
        (Collection) session.getAttribute(CaDSRConstants.GLOBAL_SESSION_KEYS);

      if (keys == null) {
        keys = new HashSet();
      }

      keys.add(key);
      session.setAttribute(CaDSRConstants.GLOBAL_SESSION_KEYS, keys);
    }
  }
  */
  public  static void addToSessionCache(String key,Object object)
  {
    synchronized(sessionObjectCache)
    {
    log.error("SessionUtil.addToSessionCache(synchronized) start :"+TimeUtils.getEasternTime());
     clearStaleObject();
     Long currTime = new Long(new Date().getTime());
     sessionObjectCacheTimeout.put(key,currTime);
     sessionObjectCache.put(key,object);
    log.error("SessionUtil.addToSessionCache(synchronized) End :"+TimeUtils.getEasternTime());
    }
  }
  
  public static Object removeFromSessionCache(String key)
  {
   synchronized(sessionObjectCache)
    {
    log.error("SessionUtil.removeFromSessionCache(synchronized) start :"+TimeUtils.getEasternTime());
    Object cachedObject = sessionObjectCache.remove(key);
    sessionObjectCacheTimeout.remove(key);    
    log.error("SessionUtil.removeFromSessionCache(synchronized) end :"+TimeUtils.getEasternTime());
    return cachedObject;
    }
  }
  
  private static void clearStaleObject()
  {
   synchronized(sessionObjectCacheTimeout){
   log.error("SessionUtil.clearStaleObject start :"+TimeUtils.getEasternTime());
    Set keys = sessionObjectCacheTimeout.keySet();
    Collection copyKeys =  new ArrayList();
    keys.addAll(copyKeys); // done to avoid java.util.ConcurrentModificationException
    if(keys!=null)
    {
      Iterator it = copyKeys.iterator();
      while(it.hasNext())
      {
        String key = (String)it.next();
        Long storedTime = (Long)sessionObjectCacheTimeout.get(key);
        if(new Long(new Date().getTime()).longValue()> storedTime.longValue()+CACHE_TIMEOUT_VALUE)
        {
          sessionObjectCache.remove(key);
          sessionObjectCacheTimeout.remove(key);
        }
      }
    }
   log.error("SessionUtil.clearStaleObject( start :"+TimeUtils.getEasternTime());
   }
  }
}
