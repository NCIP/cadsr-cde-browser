package gov.nih.nci.ncicb.cadsr.common.lov;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import gov.nih.nci.ncicb.cadsr.common.database.*;
import gov.nih.nci.ncicb.cadsr.common.util.*;
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;

public class ProtocolsLOVBean extends Object {
  private static Log log = LogFactory.getLog(ProtocolsLOVBean.class.getName());

  private String[] searchName;
  private String[] displayName;
  private String[] jspParm;
  private String[] sqlStmtParm;
  private CommonLOVBean clb;
  private String targetJsp = "protocolsLov.jsp";
  private String whereClause = "";
  private String searchStr = "";
  private boolean isContextSpecific = false;
  private boolean isEscape = false;

  public ProtocolsLOVBean(HttpServletRequest request
                                   ,DBUtil dbUtil
                                   ,String additionalWhere
                                   ){

    try {
      searchStr = request.getParameter("SEARCH");
      if (searchStr == null) searchStr = "";
      String searchWhere = "";
      String newSearchStr = "";
      if (!searchStr.equals("")) {
        newSearchStr = StringReplace.strReplace(searchStr,"*","%");
        newSearchStr = StringReplace.strReplace(newSearchStr,"'","''");
        searchWhere = " and   (upper (proto.long_name) like upper ( '" + newSearchStr+"') " +
                      " OR upper (proto.preferred_name) like upper ( '" + newSearchStr+"')) "
                      ;
      }
      if (request.getParameter("chkContext") == null){
        whereClause = searchWhere;
      }
      else {
        whereClause = searchWhere + additionalWhere;
        isContextSpecific = true;
      }
      if (isEscape) {
        whereClause = whereClause + "ESCAPE '\\'";
      }
      // pass the following parameters to CommonListCntrlBean
      String[] searchParm ={"proto.long_name","Keyword"};
      String[] jspLinkParm={ "proto.proto_idseq","P_ID"};
      String[] displayParm={"proto.long_name","Long Name",
                            "proto.protocol_id","Protocol ID",
                            "proto.preferred_name","Short Name" ,
                            "proto_conte.name","Context",
                            "proto.asl_name","Workflow Status",
                            "proto.preferred_definition","Definition",
                            "proto.lead_org","Lead Organization"};
      String[] sqlStmtParm = new String[2];
      sqlStmtParm[0] = " from sbrext.protocols_view_ext proto, sbr.contexts_view proto_conte " +
                           " where proto.conte_idseq = proto_conte.conte_idseq " +
                           " and proto.latest_version_ind = 'Yes' " +
                           //" and proto.deleted_ind = 'No' "  this is done in the view
                           //" and proto.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') ";
                            whereClause;
      sqlStmtParm[1] = " order by proto.preferred_name ";
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
      clb.setDetailReq_Type("protocols_ext"); //set req_type for detail page
      clb.setShowRowNum(40);
      clb.setJsId(request.getParameter("idVar"));
      clb.setJsName(request.getParameter("nameVar"));
      if (isContextSpecific)
        clb.setExtraURLInfo("&performQuery=false&ckhContext=yes");
      else
        clb.setExtraURLInfo("&performQuery=false");

    }
    catch( SQLException e){
      log.error("Exception: ", e);
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
}
