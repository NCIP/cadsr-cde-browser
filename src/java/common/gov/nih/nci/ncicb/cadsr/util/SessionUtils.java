package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
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


public class SessionUtils {

 private static Map  sessionObjectCache = Collections.synchronizedMap(new HashMap());
 private static Map  sessionObjectCacheTimeout = Collections.synchronizedMap(new HashMap());
 private static int CACHE_TIMEOUT_VALUE = 1800000;
  public SessionUtils() {
  }

 public static void setPreviousSessionValues(HttpServletRequest request)
 {
      String previousSessionId = request.getParameter(CaDSRConstants.PREVIOUS_SESSION_ID);
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
       }  
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
    clearStaleObject();
    Long currTime = new Long(new Date().getTime());
    sessionObjectCacheTimeout.put(key,currTime);
    sessionObjectCache.put(key,object);
    }
  }
  
  public static Object removeFromSessionCache(String key)
  {
   synchronized(sessionObjectCache)
    {
    Object cachedObject = sessionObjectCache.remove(key);
    sessionObjectCacheTimeout.remove(key);    
    return cachedObject;
    }
  }
  
  private static void clearStaleObject()
  {
    Set keys = sessionObjectCacheTimeout.keySet();
    if(keys!=null)
    {
      Iterator it = keys.iterator();
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
  }
}
