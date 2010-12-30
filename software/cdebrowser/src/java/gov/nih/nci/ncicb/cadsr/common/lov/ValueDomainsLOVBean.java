package gov.nih.nci.ncicb.cadsr.common.lov;

/**
 * A Bean class.
 * <P>
 * @author Oracle Corporation
 * @version: $Id: ValueDomainsLOVBean.java,v 1.3 2009-04-02 15:43:38 davet Exp $
 */
import gov.nih.nci.ncicb.cadsr.common.util.*;
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;
import org.apache.commons.lang.StringEscapeUtils;
import java.sql.*;

import javax.servlet.http.*;

public class ValueDomainsLOVBean extends Object {
  private static Log log = LogFactory.getLog(ValueDomainsLOVBean.class.getName());

  private CommonLOVBean clb;
  private String targetJsp = "valueDomainsLOV.jsp";
  private String whereClause = "";
  private String searchStr = "";
  private boolean isContextSpecific = false;
  private boolean searchEnumerated = true;
  private boolean searchNonEnumerated = true;

  public ValueDomainsLOVBean(HttpServletRequest request
                            ,DBUtil dbUtil
                            ,String additionalWhere
                             ){

    try {
      searchStr = request.getParameter("SEARCH");
      if (searchStr ==null) searchStr ="";
      String searchWhere = "";
      String newSearchStr = "";
      if (!searchStr.equals("")) {
        newSearchStr = StringReplace.strReplace(searchStr,"*","%");
        newSearchStr = StringReplace.strReplace(newSearchStr,"'","''");
        searchWhere = " and   (upper(vd.long_name) like upper ( '"+newSearchStr+"') " +
                      " OR upper(vd.preferred_name) like upper ( '"+newSearchStr+"')) "
                      ;
      }
      
      String enumStr = request.getParameter("enum");
      if (enumStr != null && !enumStr.equalsIgnoreCase("both")) {
    	  if (enumStr.equalsIgnoreCase("enum")) {
    		  searchWhere += " and vd.vd_type_flag = 'E'";
    		  searchEnumerated = true;
    		  searchNonEnumerated = false;
    	  }
    	  else if (enumStr.equalsIgnoreCase("nonenum")) {
    		  searchWhere += " and vd.vd_type_flag = 'N'";
    		  searchEnumerated = false;
    		  searchNonEnumerated = true;
    	  }
      }
      else {
    	  searchEnumerated = true;
		  searchNonEnumerated = true;
      }
            
      if (request.getParameter("chkContext") == null){
          whereClause = searchWhere;
      }
      else {
        whereClause = searchWhere+additionalWhere;
        isContextSpecific = true;
      }

      // pass the following parameters to CommonListCntrlBean
      String[] searchParm ={"vd.long_name","Keyword"};
      String[] jspLinkParm={ "vd.vd_idseq","P_ID"};
      String[] displayParm={"vd.preferred_name","Short Name" ,
                            "vd.long_name","Long Name",
                            "vd_conte.name","Context",
                            "vd.asl_name","Workflow Status",
                            "vd.preferred_definition","Definition",
                            "vd.version", "Version"};
      String[] sqlStmtParm = new String[2];
      sqlStmtParm[0] = " from sbr.value_domains_view vd, sbr.contexts_view vd_conte " +
                           " where vd.conte_idseq = vd_conte.conte_idseq " +
                           " and vd.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') " + whereClause;
      sqlStmtParm[1] = " order by vd.preferred_name ";
      int[] lovPassbackCols = {0};

      clb = new CommonLOVBean(
        request,
        dbUtil,
        searchParm,
        jspLinkParm,
        displayParm,
        sqlStmtParm,
        false,
        lovPassbackCols );

      clb.setCompressFlag(false); // set compress flag
      clb.setLinkCol(0);          // set detail page link column, 0-> first; 1->second
      clb.setDetailReq_Type("value_domains"); //set req_type for detail page
      clb.setShowRowNum(40);
      //clb.setPerformQueryToFalse();
      //clb.setJsId(StringEscapeUtils.escapeHtml(request.getParameter("idVar")));
      clb.setJsId("jspValueDomain");      
      //clb.setJsName(StringEscapeUtils.escapeHtml(request.getParameter("nameVar")));
      clb.setJsName("txtValueDomain");      
      if (isContextSpecific)
        clb.setExtraURLInfo("&performQuery=false&ckhContext=yes");
      else
        clb.setExtraURLInfo("&performQuery=false");
    }
    catch( SQLException e){
      //this.dBBroker = dBBroker;
      log.error("exception: ", e);
    }
  }

  public CommonLOVBean getCommonLOVBean() {
    return this.clb;
  }

  public String getJsp() {
    return targetJsp;
  }
  public boolean getIsContextSpecific(){
    return isContextSpecific;
  }
  
  public boolean getSearchEnumerated(){
    return searchEnumerated;
  }
  
  public boolean getSearchNonEnumerated(){
    return searchNonEnumerated;
  }

}
