// Copyright (c) 2000 Oracle Corporation
package gov.nih.nci.ncicb.cadsr.common.util;


import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

/**
 * A Bean class.
 * <P>
 * @author Oracle Corporation
 */
public class CommonLOVBean extends CommonListBean {

  private String jsId = null;
  private String jsName = null;
  private String performQuery = null;
  private String moreURLInfo = "";

  /**
   * Constructor
   *
   *  @param request
   *  @param response
   *  @param dBBroker
   *  @param uib
   *  @param mySearch
   *  @param myJspLinkParm
   *  @param myDispParm
   *  @param mySqlParm
   */
  public CommonLOVBean(
    HttpServletRequest request,
    //DBBroker dBBroker,
    DBUtil dBBroker,
    String[] mySearch,
    String[] myJspLinkParm,
    String[] myDispParm,
    String[] mySqlParm,
    boolean buildSearchClause,
    int[] lovPassbackCols)
    throws SQLException {
      super(request,dBBroker,mySearch,myJspLinkParm,myDispParm,mySqlParm,buildSearchClause,lovPassbackCols);
      super.setDisplayMode(CommonListBean.LOV_DISPLAY);
  }

  public void setJsId(String id) {
    this.jsId = id;
  }

  public void setJsName(String name) {
    this.jsName = name;
  }

  public String getJsId() {
    return this.jsId;
  }

  public String getJsName() {
    return this.jsName;
  }

  /*public String getExtraURLInfo() {
    if (performQuery ==null)
      return "&" + "idVar=" + getJsId() + "&nameVar=" + getJsName() + moreURLInfo;
    else
      return "&" + "idVar=" + getJsId() + "&nameVar=" + getJsName() 
           + "&performQuery=false" + moreURLInfo;
  }*/

  public String getExtraURLInfo() {
    return "&" + "idVar=" + getJsId() + "&nameVar=" + getJsName()
           +super.getExtraURLInfo(); 
  }
  public void setPerformQueryToFalse() {
    performQuery = "false";
  }
  public void appendToURL(String s){
    moreURLInfo = s;
  }
  
}
