package gov.nih.nci.ncicb.cadsr.cdebrowser;

import gov.nih.nci.ncicb.cadsr.util.*;
import gov.nih.nci.ncicb.cadsr.database.*;
import java.sql.*;
import java.util.*;
import javax.servlet.http.*;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;
import gov.nih.nci.ncicb.cadsr.util.logging.Log;

/**
 * A Bean class.
 * <P>
 * @author Ram Chilukuri 
 * @version: $Id: ProtocolsForCdeBean.java,v 1.2 2004-08-17 13:12:13 jiangja Exp $
 * 
 */
public class ProtocolsForCdeBean extends Object {

  private static Log log = LogFactory.getLog(ProtocolsForCdeBean.class.getName());
  private String targetJsp = "dataElementsSearch.jsp";
  private CommonListBean clb;
  private String searchStr = "";
  private String whereClause = "";
  private String [] strArray = null;
  private StringBuffer contextsList = null;
  
  /**
   * Constructor
   *
   */
  public ProtocolsForCdeBean(
    String deIdseq,
    HttpServletRequest request,
    DBUtil dbUtil
    ) throws SQLException {

        
    try {
        // pass the following parameters to CommonListCntrlBean
        String[] searchParm ={"proto.long_name","Protocol Number"};
        String[] jspLinkParm={"NVL(proto.proto_idseq,'N/A')","p_proto_idseq"};
        String[] displayParm={"NVL(proto.long_name,'N/A')","Protocol Number",
                              "NVL(proto.lead_org,'N/A')","Lead Org",
                              "crf.long_name","Used In",
                              //"que.long_name","Submitted Question",
                              "crf.qtl_name" ,"Usage Type"};

        String[] sqlStmtParm = new String[2];
        sqlStmtParm[0] = "  from sbrext.protocols_ext proto "+
                         ", sbrext.quest_contents_ext crf " +
                         ", sbrext.quest_contents_ext que " +
                         "  where proto.proto_idseq (+) = crf.proto_idseq and " + 
                         "        crf.qc_idseq = que.dn_crf_idseq and " +
                         "        crf.qtl_name IN ('CRF','TEMPLATE') " +
                         "  and que.qtl_name = 'QUESTION' " +
                         "  and que.de_idseq = '" + deIdseq + "'";
                         
                           
        sqlStmtParm[1] = " order by proto.long_name,crf.long_name,que.long_name ";

        clb = new CommonListBean(
          request,
          dbUtil,
          searchParm,
          jspLinkParm,
          displayParm,
          sqlStmtParm,
          false,
          null
          );

        //Set the following parmameters
        clb.setCompressFlag(false); // set compress flag
        clb.setLinkCol(-1);          // set detail page link column, 0-> first; 1->second
        clb.setDetailReq_Type("dataElementDetails"); //set req_type for detail page
        clb.setShowRowNum(40);
        clb.setExtraURLInfo("&performQuery=false");
        if (request.getParameter("NOT_FIRST_DISPLAY") == null) {
          clb.forcePopulate();
        }

    }
    catch( SQLException e){
       log.error("Exception occurred", e);
    }
  
  }

  public CommonListBean getCommonListBean() {
    return this.clb;
  }

  public String getJsp() {
    return targetJsp;
  }

  /*public void releaseDb() {
    DBBrokerFactory.checkIn(dBBroker);
  }*/

  public String getSearchStr(int arrayIndex){
   if (strArray != null) {
     return strArray[arrayIndex];
   }
   else {
     return "";
   }
  }

  
}
