package gov.nih.nci.ncicb.cadsr.common.bc4j;

import gov.nih.nci.ncicb.cadsr.util.BC4JHelper;

import java.util.Hashtable;
import java.util.Properties;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import oracle.jbo.ApplicationModule;
import oracle.jbo.common.ampool.ApplicationPool;
import oracle.jbo.common.ampool.SessionCookie;

import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

public class BC4JConnectionProvider implements HttpSessionListener {
  private static Log log = LogFactory.getLog(BC4JConnectionProvider.class.getName());
  private static Hashtable existingSessionCookies = new Hashtable(29);
  private String providerName;
  private Hashtable providerInfo;
  private ApplicationPool amPool;

  public BC4JConnectionProvider(String deploymentKey, Hashtable providerInfo) {
    providerName = deploymentKey;
    this.providerInfo = providerInfo;
  }

  private SessionCookie findSessionCookie(String sessionId)
    throws Exception {
    SessionCookie cookie = null;

    if (existingSessionCookies.containsKey(sessionId + providerName)) {
      cookie =
        (SessionCookie) existingSessionCookies.get(sessionId + providerName);
    } else {
      cookie = this.createSessionCookie(sessionId);
      existingSessionCookies.put(sessionId + providerName, cookie);
    }

    return cookie;
  }

  private SessionCookie getSessionCookie(String sessionId) {
    return (SessionCookie) existingSessionCookies.get(sessionId + providerName);
  }

  private SessionCookie createSessionCookie(String sessionId)
    throws Exception {
    SessionCookie cookie = null;
    Properties connProps = (Properties) providerInfo.get(providerName);
    String configName = connProps.getProperty("configname");
    String packageName = connProps.getProperty("persistencebase");
    String poolName = providerName + "Pool";

    amPool =
      BC4JHelper.getApplicationPool(poolName, packageName, configName, null);

    cookie =
      BC4JHelper.createSessionCookie(amPool, sessionId, providerName, null);

    return cookie;
  }

  public ApplicationModule getConnection(String sessionId)
    throws Exception {
    ApplicationModule am = null;
    SessionCookie cookie = this.findSessionCookie(sessionId);
    am = BC4JHelper.getApplicationModuleFromPool(cookie);
    log.info("Available instance count in pool after check out is " +
      amPool.getAvailableInstanceCount());

    return am;
  }

  public void releaseConnection(int releaseMode, String sessionId)
    throws Exception {
    SessionCookie cookie = this.getSessionCookie(sessionId);
    amPool.releaseApplicationModule(cookie, releaseMode);
    log.info("Available instance count in pool after check in is " +
      amPool.getAvailableInstanceCount());
  }

  public void removeSessionCookie(String sessionId) throws Exception {
    SessionCookie cookie = this.getSessionCookie(sessionId);
    amPool.removeSessionCookie(cookie);
  }

  public void sessionCreated(HttpSessionEvent p0) {
  }

  public void sessionDestroyed(HttpSessionEvent p0) {
    String sessionId = p0.getSession().getId();

    try {
      removeSessionCookie(sessionId);
    } catch (Exception ex) {
      log.error("Exception occured in removing session cookie", ex);
    }
  }
}
