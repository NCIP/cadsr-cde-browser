package gov.nih.nci.ncicb.cadsr.lov;

/**
 * A Bean class.
 * <P>
 * @author Oracle Corporation
 */
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import gov.nih.nci.ncicb.cadsr.database.*;
import gov.nih.nci.ncicb.cadsr.util.*;
import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

public class DataElementConceptsLOVBean extends Object {
  private static Log log = LogFactory.getLog(DataElementConceptsLOVBean.class.getName());

  private String[] searchName;
  private String[] displayName;
  private String[] jspParm;
  private String[] sqlStmtParm;
  private CommonLOVBean clb;
  private String targetJsp = "valueDomainsLOV.jsp";
  private String whereClause = "";
  private String searchStr = "";
  private boolean isContextSpecific = false;
  private boolean isEscape = false;

  public DataElementConceptsLOVBean(HttpServletRequest request
                                   ,DBUtil dbUtil
                                   ,String additionalWhere
                                   ){

    try {
      searchStr = request.getParameter("SEARCH");
      if (searchStr ==null) searchStr ="";

      String searchWhere = "";
      String newSearchStr = "";
      if (!searchStr.equals("")) {
        /*if ((searchStr.indexOf("%") != -1)){
          isEscape = true;
          newSearchStr = StringReplace.strReplace(searchStr,"%","\\%");
          //newSearchStr = StringReplace.strReplace(newSearchStr,"_","\\_");
          newSearchStr = StringReplace.strReplace(newSearchStr,"*","%");
        }
        else {*/
          newSearchStr = StringReplace.strReplace(searchStr,"*","%");
          //Release 3.0, TT#1178
          newSearchStr = StringReplace.strReplace(newSearchStr,"'","''");
        //}
        searchWhere = " and   (upper (dec.long_name) like upper ( '"+newSearchStr+"') " +
                      " OR upper (dec.preferred_name) like upper ( '"+newSearchStr+"')) "
                      ;
      }
      if (request.getParameter("chkContext") == null){
        /*whereClause = " and   (upper (nvl(dec.long_name,'%')) like upper ( '%"+searchStr+"%') " +
                      " OR upper (nvl(dec.preferred_name,'%')) like upper ( '%"+searchStr+"%')) "
                      ;*/
          whereClause = searchWhere;
      }
      else {
        /*whereClause = " and   (upper (nvl(dec.long_name,'%')) like upper ( '%"+searchStr+"%') " +
                      " OR upper (nvl(dec.preferred_name,'%')) like upper ( '%"+searchStr+"%')) "+
                      additionalWhere;*/
        whereClause = searchWhere+additionalWhere;
        isContextSpecific = true;
      }
      if (isEscape) {
        whereClause = whereClause + "ESCAPE '\\'";
      }
      // pass the following parameters to CommonListCntrlBean
      String[] searchParm ={"dec.long_name","Keyword"};
      String[] jspLinkParm={ "dec.dec_idseq","P_ID"};
      String[] displayParm={"dec.preferred_name","Short Name" ,
                            "dec.long_name","Long Name",
                            "dec_conte.name","Context",
                            "dec.asl_name","Workflow Status",
                            "dec.preferred_definition","Definition",
                            "dec.version", "Version"};
      String[] sqlStmtParm = new String[2];
      sqlStmtParm[0] = " from sbr.data_element_concepts dec,sbr.contexts dec_conte " +
                           " where dec.conte_idseq = dec_conte.conte_idseq " +
                           " and dec.deleted_ind = 'No' " +
                           " and dec.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') " + whereClause;
      sqlStmtParm[1] = " order by dec.preferred_name ";
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
      clb.setDetailReq_Type("dec"); //set req_type for detail page
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
