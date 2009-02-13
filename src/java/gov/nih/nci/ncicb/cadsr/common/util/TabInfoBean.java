package gov.nih.nci.ncicb.cadsr.common.util;

import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.*;

public class TabInfoBean
{
  private static Log log = LogFactory.getLog(TabInfoBean.class.getName());
  private static Hashtable retrievedPropertiesHT = new Hashtable();

  private static Hashtable shareLabelWithAllTabsHT = new Hashtable();
  private static Hashtable logoutURLHT = new Hashtable();
  private static Hashtable homeURLHT = new Hashtable();
  private static Hashtable appTitleHT = new Hashtable();
  private static Hashtable mainTabLabelsHT = new Hashtable();
  private static Hashtable mainTabURLParamsHT = new Hashtable();
  private static Hashtable subTabLabelsHT = new Hashtable();
  private static Hashtable subTabURLParamsHT = new Hashtable();
  private static Hashtable recordLabelsHT = new Hashtable();
  private static Hashtable defaultRecLabelHT = new Hashtable();
  private static Hashtable leftRecDelimHT = new Hashtable();
  private static Hashtable rightRecDelimHT = new Hashtable();

  private String defaultRecLabel;
  private String leftRecDelim = null;
  private String rightRecDelim = null;

  private int mainTabNum = 0;
  private int subTabNum = 0;
  private String curRecLabel = null;
  private int curRecId = 0;

  private boolean shareLabelWithAllTabs;
  private String logoutURL = null;
  private String homeURL = null;
  private String appTitle = null;
  private String[] mainTabLabels = null;
  private String[] mainTabURLParams = null;
  private Vector subTabLabels;
  private Vector subTabURLParams;
  private String[] recordLabels = null;

  /**
   * Constructor
   */
  public TabInfoBean(String propFilename) throws Exception {
    retrieveProperties(propFilename);
  }

  private synchronized void retrieveProperties(String propFilename) throws Exception {

    if (!retrievedPropertiesHT.containsKey(propFilename)) {
      log.info("Retrieving tab properties ...");
      TabProperties tp = new TabProperties();
      boolean result = tp.InitParameters(propFilename);
      if (!result) {
        throw new Exception("Unable to retrieve properties for file " + propFilename);
      }
      else {
        shareLabelWithAllTabsHT.put(propFilename,StringUtils.booleanToStrYN(tp.getShareLabelWithAllTabs()));
        defaultRecLabelHT.put(propFilename,tp.getCurRecLabel());
        leftRecDelimHT.put(propFilename,tp.getLeftRecDelim());
        rightRecDelimHT.put(propFilename,tp.getRightRecDelim());
        logoutURLHT.put(propFilename,tp.getLogoutURL());
        homeURLHT.put(propFilename,tp.getHomeURL());
        appTitleHT.put(propFilename,tp.getAppTitle());
        mainTabLabelsHT.put(propFilename,tp.getMainTabLabels());
        mainTabURLParamsHT.put(propFilename,tp.getMainTabURLParams());
        subTabLabelsHT.put(propFilename,tp.getSubTabLabels());
        subTabURLParamsHT.put(propFilename,tp.getSubTabURLParams());
        recordLabelsHT.put(propFilename,tp.getRecordLabels());

        retrievedPropertiesHT.put(propFilename,"dummy");
      }
    }
    String temp1 = (String)shareLabelWithAllTabsHT.get(propFilename);
    shareLabelWithAllTabs = StringUtils.toBoolean(temp1);
    defaultRecLabel = (String)defaultRecLabelHT.get(propFilename);
    leftRecDelim = (String)leftRecDelimHT.get(propFilename);
    rightRecDelim = (String)rightRecDelimHT.get(propFilename);
    logoutURL = (String)logoutURLHT.get(propFilename);
    homeURL = (String)homeURLHT.get(propFilename);
    appTitle = (String)appTitleHT.get(propFilename);
    mainTabLabels = (String[])mainTabLabelsHT.get(propFilename);
    mainTabURLParams = (String[])mainTabURLParamsHT.get(propFilename);
    subTabLabels = (Vector)subTabLabelsHT.get(propFilename);
    subTabURLParams = (Vector)subTabURLParamsHT.get(propFilename);
    recordLabels = (String[])recordLabelsHT.get(propFilename);

    curRecLabel = defaultRecLabel;
  }

  public void processRequest(HttpServletRequest request) {
    int cookieValue;

    String maintab = (String)SessionHelper.getValue(request,"maintab");
    if (maintab != null)
    {
      mainTabNum = Integer.parseInt(maintab);
    }
    else
    {
      mainTabNum = 0;
      SessionHelper.putValue(request,"maintab","0");
    }

    String tabClicked = request.getParameter("tabClicked");
    if (tabClicked != null)
    {
      mainTabNum = Integer.parseInt(tabClicked);
      SessionHelper.putValue(request,"maintab",tabClicked);
    }

    String subtab = (String)SessionHelper.getValue(request,"subtab"+mainTabNum);
    if (subtab != null)
    {
      subTabNum = Integer.parseInt(subtab);
    }
    else
    {
      subTabNum = 0;
      SessionHelper.putValue(request,"subtab"+mainTabNum,"0");
    }

    String subTabClicked = request.getParameter("subTabClicked");
    if (subTabClicked != null)
    {
      subTabNum = Integer.parseInt(subTabClicked);
      SessionHelper.putValue(request,"subtab"+mainTabNum,subTabClicked);
    }

  }

  public String getLogoutURL() {
    return this.logoutURL;
  }

  public String getHomeURL() {
    return this.homeURL;
  }

  public int getMainTabCount() {
    return mainTabLabels.length;
  }

  public int getSubTabCount() {
    return ((String[])subTabLabels.elementAt(mainTabNum)).length;
  }

  // tabNum = 0, 1, 2, ...
  public String getMainTabLabel(int tabNum) {
    return mainTabLabels[tabNum];
  }

  // tabNum = 0, 1, 2, ...
  public String getMainTabURLParams(int tabNum) {
    return mainTabURLParams[tabNum];
  }

  // mainTabNum = 0, 1, 2, ...
  // subTabNum = 0, 1, 2, ...
  public String getSubTabLabel(int mainTabNum, int subTabNum) {
    return ((String[])subTabLabels.elementAt(mainTabNum))[subTabNum];
  }

  // mainTabNum = 0, 1, 2, ...
  // subTabNum = 0, 1, 2, ...
  public String getSubTabURLParams(int mainTabNum, int subTabNum) {
    return ((String[])subTabURLParams.elementAt(mainTabNum))[subTabNum];
  }

  public String getTargetSubTab(String requestTab) {
    return getSubTabURLParams(getMainTabNum(),getSubTabNum());
  }

  public String getAppTitle() {
    return this.appTitle;
  }

  public int getMainTabNum() {
    return this.mainTabNum;
  }

  public int getSubTabNum() {
    return this.subTabNum;
  }
  public void setMainTabNum(int tabNumber) {
    mainTabNum = tabNumber;
  }
  public void setMainTabLabel(String tabLabel){
      mainTabLabels[mainTabNum] = tabLabel;     
  }

}