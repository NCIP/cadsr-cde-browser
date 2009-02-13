package gov.nih.nci.ncicb.cadsr.common.lov;

/**
 * A Bean class.
 * <P>
 * @author Oracle Corporation
 */
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.util.*;
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;

import java.sql.*;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.*;
//import healthtoolkit.beans.dbservice.*;
//import healthtoolkit.utils.*;

public class ClassificationsLOVBean extends Object {
  private static Log log = LogFactory.getLog(ClassificationsLOVBean.class.getName());

//  private String[] searchName;
//  private String[] displayName;
//  private String[] jspParm;
//  private String[] sqlStmtParm;
  //private DBBroker dBBroker = null;
  private CommonLOVBean clb;
  private String targetJsp = "classificationsLOV.jsp";
  private String whereClause = "";
  private String[] searchStr = null;
  private boolean isContextSpecific = false;

  public ClassificationsLOVBean(HttpServletRequest request
                               ,DBUtil dbUtil
                               ,String additionalWhere
                               ){

    getClassificationBean(request, dbUtil, additionalWhere);
  }
 
  public ClassificationsLOVBean(
		  	HttpServletRequest req, 
		  	DBUtil dbUtil,
		    String chk,
		    String contextIdSeq) {
	
		// build additional query filters
		String additionalWhere = getAdditionalWhere(req, chk, contextIdSeq);
		//call the method to get the bean
		getClassificationBean(req, dbUtil, additionalWhere);
	}
  
  private void getClassificationBean(HttpServletRequest request
			, DBUtil dbUtil, String additionalWhere) {

		try {
			// searchStr = request.getParameter("SEARCH");
			searchStr = request.getParameterValues("SEARCH");
			String csWhere = "";
			String csvWhere = "";
			String csiWhere = "";
			if (searchStr != null) {
				for (int i = 0; i < searchStr.length; i++) {
					if (searchStr[i] == null)
						searchStr[i] = "";
				}
				if (!searchStr[0].equals("")) {
					String newSearchStr0 = StringReplace.strReplace(
							searchStr[0], "*", "%");
					// Release 3.0, TT#1178
					newSearchStr0 = StringReplace.strReplace(newSearchStr0,
							"'", "''");
					csWhere = " and   (upper (cs.long_name) like upper ( '"
							+ newSearchStr0 + "') "
							+ " OR upper (cs.preferred_name) like upper ( '"
							+ newSearchStr0 + "')) ";
				}
				// Release 3.2 GF#1247
				if (!searchStr[1].equals("")) {
					Float newSearchFlt1 = new Float(searchStr[1]);
					csvWhere = " and cs.version = " + newSearchFlt1.toString()
							+ "  ";
				}
				if (!searchStr[2].equals("")) {
					String newSearchStr1 = StringReplace.strReplace(
							searchStr[2], "*", "%");
					// Release 3.0, TT#1178
					newSearchStr1 = StringReplace.strReplace(newSearchStr1,
							"'", "''");
					csiWhere = " and upper (csi.long_name) like upper ( '"
							+ newSearchStr1 + "') ";
				}
				if (request.getParameter("chkContext") == null) {
					whereClause = csWhere + csvWhere + csiWhere;
				} else {
					whereClause = csWhere + csvWhere + csiWhere
							+ additionalWhere;
					isContextSpecific = true;
				}

			}
			// pass the following parameters to CommonListCntrlBean
			String[] searchParm = { "cs.long_name", "Classification Scheme",
					"cs.version", "CS Version", // Release 3.2 GF#1247
					"csi.long_name csi_name", "Class Scheme Item" };
			String[] jspLinkParm = { "csc.cs_csi_idseq", "P_ID" };
			String[] displayParm = {
					"csi.long_name csi_name",	"Class Scheme Item Name",
					"csi.csi_id||'v'||" + "case when csi.version = trunc(csi.version) "
							+ "then to_char(csi.version,'99.9') "
							+ "else to_char(csi.version,'99.99') "
							+ "end csi_version",
					"CSI Public ID Version",
					"cs.long_name", "CS Long Name",
					"cs.cs_id||'v'||" + "case when cs.version = trunc(cs.version) "
							+ "then  to_char(cs.version,'99.9') "
							+ "else   to_char(cs.version,'99.99') "
							+ "end csversion",
					"CS Public ID Version", // Release 3.2 GF#1247
					"cs_conte.name", "CS Context",
					"cs.asl_name", "CS Workflow Status",
					"cs.preferred_definition", "CS Definition" };
			String[] sqlStmtParm = new String[2];
			sqlStmtParm[0] = " from sbr.classification_schemes_view cs, sbr.contexts_view cs_conte, "
					+ "      sbr.cs_items_view csi, sbr.cs_csi_view csc "
					+ " where cs.conte_idseq = cs_conte.conte_idseq "
					//+
					// Release 3.2 GF#1247 " and cs.latest_version_ind = 'Yes' "
					// +
					//" and cs.deleted_ind = 'No' "  //when using view no need for this
					+ " and cs.cs_idseq = csc.cs_idseq "
					+ " and csi.csi_idseq = csc.csi_idseq "
					+ " and cs.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') "
					+ whereClause;
			sqlStmtParm[1] = " order by cs.long_name, csi.long_name ";
			int[] lovPassbackCols = { 0, 4 };

			clb = new CommonLOVBean(request,
					// dBBroker,
					dbUtil, searchParm, jspLinkParm, displayParm, sqlStmtParm,
					false, lovPassbackCols);

			clb.setCompressFlag(false); // set compress flag
			clb.setLinkCol(0); // set detail page link column, 0-> first;
								// 1->second
			clb.setDetailReq_Type("value_domains"); // set req_type for detail
													// page
			clb.setShowRowNum(40);
			// clb.setPerformQueryToFalse();

			clb.setJsId(request.getParameter("idVar"));
			clb.setJsName(request.getParameter("nameVar"));
			if (isContextSpecific)
				clb.setExtraURLInfo("&performQuery=false&ckhContext=yes");
			else
				clb.setExtraURLInfo("&performQuery=false");

		} catch (SQLException e) {
			//this.dBBroker = dBBroker;
			log.error("Exception: ", e);
		}
	}

  private String getAdditionalWhere(HttpServletRequest req, String chk, String contextIdSeq) {
	
	    String[] contexts = null;
	    if(chk != null && chk.equals("always")) 
	    {
	  	  Collection coll = (Collection)req.getSession().getAttribute("userContexts");
		  contexts = new String[coll.size()];
		  int i=0;
		  for(Iterator it = coll.iterator(); it.hasNext(); i++)
		    contexts[i] = ((Context)it.next()).getConteIdseq();
		  req.setAttribute("chkContext", chk);
	    } else {	    
		    if ((contextIdSeq != null) && (contextIdSeq.length() > 0)) 
		    {
		    	contexts = new String[1];
		    	contexts[0] = contextIdSeq;
		    } else
		    	contexts = new String[0];
	    }
		// build additional query filters
		String additionalWhere = "";
		if(contexts.length > 0)
		  additionalWhere +=
		    " and (upper(nvl(cs_conte.conte_idseq,'%')) like upper ( '%" +
		    contexts[0] + "%') ";

		for(int i=1; i<contexts.length; i++) {
		  additionalWhere +=
		    " or upper(nvl(cs_conte.conte_idseq,'%')) like upper ( '%" +
		    contexts[i] + "%') ";
		}
		if(contexts.length > 0)
		  additionalWhere += ")";
		//return teh string
		return additionalWhere;
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
