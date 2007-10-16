package gov.nih.nci.ncicb.cadsr.lov;

/**
 * A Bean class.
 * <P>
 * @author Oracle Corporation
 */
import gov.nih.nci.ncicb.cadsr.util.*;
import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

import java.sql.*;

import javax.servlet.http.*;
//import healthtoolkit.beans.dbservice.*;
//import healthtoolkit.utils.*;

public class ClassificationsLOVBean extends Object {
  private static Log log = LogFactory.getLog(ClassificationsLOVBean.class.getName());

  private String[] searchName;
  private String[] displayName;
  private String[] jspParm;
  private String[] sqlStmtParm;
  //private DBBroker dBBroker = null;
  private CommonLOVBean clb;
  private String targetJsp = "classificationsLOV.jsp";
  private String whereClause = "";
  private String[] searchStr = null;
  private boolean isContextSpecific = false;

  public ClassificationsLOVBean(HttpServletRequest request
                               //,DBLAccess dblAccess
                               ,DBUtil dbUtil
                               ,String additionalWhere
                               ){

    try {
      //searchStr = request.getParameter("SEARCH");
      searchStr = request.getParameterValues("SEARCH");
      String csWhere = "";
      String csvWhere = "";
      String csiWhere = "";
      if (searchStr !=null) {
         for (int i = 0; i <searchStr.length ; i++)  {
            if (searchStr[i] == null)searchStr[i] = "";
         }
         if (!searchStr[0].equals("")){
            String newSearchStr0 = StringReplace.strReplace(searchStr[0],"*","%");
            //Release 3.0, TT#1178
            newSearchStr0 = StringReplace.strReplace(newSearchStr0,"'","''");
            csWhere = " and   (upper (cs.long_name) like upper ( '"+newSearchStr0+"') " +
                      " OR upper (cs.preferred_name) like upper ( '"+newSearchStr0+"')) "
                      ;
         }
         //Release 3.2 GF#1247
         if (!searchStr[1].equals("")) {
             Float newSearchFlt1 = new Float(searchStr[1]);
             csvWhere = " and cs.version = "+newSearchFlt1.toString()+"  ";
         }
         if (!searchStr[2].equals("")){
            String newSearchStr1 = StringReplace.strReplace(searchStr[2],"*","%");
            //Release 3.0, TT#1178
            newSearchStr1 = StringReplace.strReplace(newSearchStr1,"'","''");
            csiWhere = " and upper (csi.csi_name) like upper ( '"+newSearchStr1+"') ";
         }
         if (request.getParameter("chkContext") == null){
            /*whereClause = " and   (upper (nvl(cs.long_name,'%')) like upper ( '%"+searchStr[0]+"%') " +
                      " OR upper (nvl(cs.preferred_name,'%')) like upper ( '%"+searchStr[0]+"%')) "+
                      " and upper (nvl(csi.csi_name,'%')) like upper ( '%"+searchStr[2]+"%') "
                      ;*/
              whereClause = csWhere + csvWhere + csiWhere;
         }
         else {
            /*whereClause = " and   (upper (nvl(cs.long_name,'%')) like upper ( '%"+searchStr[0]+"%') " +
                      " OR upper (nvl(cs.preferred_name,'%')) like upper ( '%"+searchStr[0]+"%')) "+
                      " and upper (nvl(csi.csi_name,'%')) like upper ( '%"+searchStr[2]+"%') "+
                      additionalWhere;*/
              whereClause = csWhere + csvWhere + csiWhere + additionalWhere;
              isContextSpecific = true;
        }

      }
      // pass the following parameters to CommonListCntrlBean
      String[] searchParm ={"cs.long_name","Classification Scheme",
                            "cs.version","CS Version", //Release 3.2 GF#1247
                            "csi.csi_name","Class Scheme Item"};
      String[] jspLinkParm={ "csc.cs_csi_idseq","P_ID"};
      String[] displayParm={"csi.csi_name", "Class Scheme Item Name",
                            "cs.preferred_name","CS Short Name" ,
                            "cs.long_name","CS Long Name",
                            "cs_conte.name","CS Context",
                            "case when cs.version = trunc(cs.version) " + 
                            "            then  to_char(cs.version,'99.9')" + 
                            "			else   to_char(cs.version,'99.99')" + 
                            "			end csversion", "CS Version",  //Release 3.2 GF#1247
                            "cs.asl_name","CS Workflow Status",
                            "cs.preferred_definition","CS Definition"
                            };
      String[] sqlStmtParm = new String[2];
      sqlStmtParm[0] = " from sbr.classification_schemes cs,sbr.contexts cs_conte, " +
                       "      sbr.class_scheme_items csi, sbr.cs_csi csc " +
                       " where cs.conte_idseq = cs_conte.conte_idseq " +
        //Release 3.2 GF#1247  " and cs.latest_version_ind = 'Yes' " +
                       " and cs.deleted_ind = 'No' " +
                       " and cs.cs_idseq = csc.cs_idseq " +
                       " and csi.csi_idseq = csc.csi_idseq " +
                       " and cs.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') " +
                       whereClause;
      sqlStmtParm[1] = " order by cs.preferred_name ";
      int[] lovPassbackCols = {0,4};

      clb = new CommonLOVBean(
        request,
        //dBBroker,
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

      clb.setJsId(request.getParameter("idVar"));
      clb.setJsName(request.getParameter("nameVar"));
      if (isContextSpecific)
        clb.setExtraURLInfo("&performQuery=false&ckhContext=yes");
      else
        clb.setExtraURLInfo("&performQuery=false");

    }
    catch( SQLException e){
      //this.dBBroker = dBBroker;
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
