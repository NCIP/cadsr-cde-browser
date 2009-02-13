package gov.nih.nci.ncicb.cadsr.common.util;

import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;
import java.util.ResourceBundle;
import java.util.Vector;

public class TabProperties
{
  private static Log log = LogFactory.getLog(TabProperties.class.getName());
  private String curRecLabel = null;
  private String leftRecDelim = null;
  private String rightRecDelim = null;
  private boolean shareLabelWithAllTabs;
  private String logoutURL = null;
  private String homeURL = null;

  private String appTitle = null;

  private int mainTabCount = 0;
  private String[] mainTabLabels = null;
  private String[] mainTabURLParams = null;
  private Vector subTabLabels = new Vector();
  private Vector subTabURLParams = new Vector();
  private String[] recordLabels = null;

  /**
   * Constructor
   */
  public TabProperties()
  {
  }
  /**
   *  Read the resource bundle file
   *  propFilename - the specified resource file (fn.properties) without the extension
   *  (e.g., medsurv)
   */
  public boolean InitParameters(String propFilename)
  {
    boolean result = false;
    int index = 0;
    try
    {
      log.info("*** Resource File Name ***: " + propFilename);
      ResourceBundle b = ResourceBundle.getBundle(propFilename, java.util.Locale.getDefault());

      index++;
      curRecLabel = b.getString("init.curRecLabel");

      index++;
      leftRecDelim = b.getString("init.leftRecDelim");

      index++;
      rightRecDelim = b.getString("init.rightRecDelim");

      index++;
      logoutURL = b.getString("init.logoutURL");

      index++;
      homeURL = b.getString("init.homeURL");

      index++;
      appTitle = b.getString("init.appTitle");

      index++;
      mainTabCount = Integer.parseInt(b.getString("init.mainTabCount"));

      index++;
      mainTabLabels = StringUtils.tokenizeCSVList(b.getString("init.mainTabLabels"));

      index++;
      mainTabURLParams = StringUtils.tokenizeCSVList(b.getString("init.mainTabURLParams"));

      index++;
      recordLabels = StringUtils.tokenizeCSVList(b.getString("init.recordLabels"));

      for (int i = 1; i <= mainTabCount; i++) {
        index++;
        subTabLabels.addElement(StringUtils.tokenizeCSVList(b.getString("init.subTabLabels" + i)));
      }

      for (int i = 1; i <= mainTabCount; i++) {
        index++;
        subTabURLParams.addElement(StringUtils.tokenizeCSVList(b.getString("init.subTabParams" + i)));
      }

      try {
        index++;
        shareLabelWithAllTabs = StringUtils.toBoolean(b.getString("init.shareLabelWithAllTabs"));
      }
      catch (Exception e) {
        throw new Exception("shareLabelWithAllTabs - could not retrieve property");
      }

      result = true;
    }
    catch (java.util.MissingResourceException mre)
    {
      log.error("Error getting init parameters, missing resource values");
      log.error("Property missing index: " + index, mre);
      result = false;
    }
    catch (Exception e)
    {
      log.error("Error getting init parameters", e);
      result = false;
    }
    return result;
  }

  public String getCurRecLabel() {
    return curRecLabel;
  }

  public String getLeftRecDelim() {
    return leftRecDelim;
  }

  public String getRightRecDelim() {
    return rightRecDelim;
  }

  public boolean getShareLabelWithAllTabs() {
    return shareLabelWithAllTabs;
  }

  public String getLogoutURL() {
    return logoutURL;
  }

  public String getHomeURL() {
    return homeURL;
  }

  public String getAppTitle() {
    return appTitle;
  }

  public int getMainTabCount() {
    return mainTabCount;
  }

  public String[] getMainTabLabels() {
    return mainTabLabels;
  }

  public String[] getMainTabURLParams() {
    return mainTabURLParams;
  }

  public Vector getSubTabLabels() {
    return subTabLabels;
  }

  public Vector getSubTabURLParams() {
    return subTabURLParams;
  }

  public String[] getRecordLabels() {
    return recordLabels;
  }

}