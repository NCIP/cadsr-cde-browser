package gov.nih.nci.ncicb.cadsr.common.common.bc4j;

import gov.nih.nci.ncicb.cadsr.common.xml.util.ConnectionProviderLoader;

import oracle.cle.util.xml.XMLUtil;

import oracle.jbo.ApplicationModule;

import java.util.Hashtable;


public class BC4JConnectionManager {
  private static BC4JConnectionManager instance = null;
  private Hashtable bc4jConnProviders = null;
  private Hashtable providerInfo = null;

  private BC4JConnectionManager() {
    bc4jConnProviders = new Hashtable(11);
    providerInfo = this.loadFromXML();
  }

  public static synchronized BC4JConnectionManager getInstance() {
    if (instance == null) {
      instance = new BC4JConnectionManager();
    }

    return instance;
  }

  public BC4JConnectionProvider getProvider(String deploymentKey)
    throws Exception {
    BC4JConnectionProvider provider = null;

    //this.getInstance();
    synchronized (this) {
      if (!bc4jConnProviders.containsKey(deploymentKey)) {
        provider = new BC4JConnectionProvider(deploymentKey, providerInfo);
        bc4jConnProviders.put(deploymentKey, provider);
      } else {
        provider =
          (BC4JConnectionProvider) bc4jConnProviders.get(deploymentKey);
      }
    }

    return provider;
  }

  public ApplicationModule getConnection(String sessionId, String deploymentKey)
    throws Exception {
    ApplicationModule am = null;
    BC4JConnectionProvider provider = this.getProvider(deploymentKey);
    am = provider.getConnection(sessionId);

    return am;
  }

  public void releaseConnection(String sessionId, int releaseMode,
    String deploymentKey) throws Exception {
    BC4JConnectionProvider provider = this.getProvider(deploymentKey);
    provider.releaseConnection(releaseMode, sessionId);
  }

  private Hashtable loadFromXML() {
    Hashtable connPropertiesTable = null;

    try {
      String dtdValidationString =
        System.getProperty("cle.providers.dtdvalidation");
      ConnectionProviderLoader loader = new ConnectionProviderLoader();
      XMLUtil.saxParse("cle-providers.xml", loader, false, null);
      connPropertiesTable = loader.getPropertiesTable();
    } catch (Exception exception) {
      connPropertiesTable = new Hashtable(0);
    }

    return connPropertiesTable;
  }
}
