package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.common.base.process.BasePersistingProcess;
import gov.nih.nci.ncicb.cadsr.common.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.common.lov.ClassificationsLOVBean;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.common.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.common.util.TabInfoBean;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

import org.apache.commons.lang.StringEscapeUtils;

//CDE Browser Application Imports
//import oracle.cle.process.ProcessConstants;
// Framework imports
// java imports


/**
 * @author Ram Chilukuri
 * @version: $Id: GetClassificationsLOV.java,v 1.8 2008-10-24 21:11:04 davet Exp $
 */
public class GetClassificationsLOV extends BasePersistingProcess {
  public GetClassificationsLOV() {
    this(null);
    DEBUG = false;
  }

  public GetClassificationsLOV(Service aService) {
    super(aService);
    DEBUG = false;
  }

  /**
   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
   * this process during construction.
   */
  public void registerInfo() {
    try {
      registerResultObject(ProcessConstants.CS_LOV);
      registerResultObject("tib");
      registerParameterObject("SEARCH");
      registerStringParameter("P_PARAM_TYPE");
      registerStringParameter("P_CONTEXT");
      registerStringParameter("P_CONTE_IDSEQ");
      registerParameterObject(ProcessConstants.DEC_LOV);
      registerStringParameter("performQuery");
      registerStringResult("performQuery");
      registerParameterObject("dbUtil");
      registerStringParameter("SBR_DSN");
      registerStringParameter("INITIALIZED");
    }
    catch (ProcessInfoException pie) {
      reportException(pie, true);
    }

    catch (Exception e) {
    }
  }

  /**
   * persist: called by start to do all persisting work for this process.  If
   * this method throws an exception, then the condition returned by the
   * <code>getPersistFailureCondition()</code> is set.  Otherwise, the
   * condition returned by <code>getPersistSuccessCondition()</code> is set.
   */
  public void persist() throws Exception {
    HttpServletRequest myRequest = null;
    TabInfoBean tib = null;
    String[] searchParam = null;
    ClassificationsLOVBean cslb = null;
    String performQuery = null;
    DBUtil dbUtil = null;
    String dsName = null;

    try {
      if (getStringInfo("INITIALIZED") == null) {
        CDEBrowserParams params = CDEBrowserParams.getInstance();
        //dsName = params.getSbrDSN();
        dbUtil = new DBUtil();
      }
      else {
        dbUtil = (DBUtil) getInfoObject("dbUtil");
        //dsName = getStringInfo("SBR_DSN");
      }

      tib = new TabInfoBean("cdebrowser_lov_tabs");
      myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);

      if (tib.getMainTabNum() != 0) {
        tib.setMainTabNum(0);
      }

      searchParam = getInfoStringArray("SEARCH");

      if (searchParam != null) {
        int numberOfSearchFields = searchParam.length;
      }

      performQuery = StringEscapeUtils.escapeHtml(getStringInfo("performQuery"));

      if (performQuery == null) {
        //dbUtil = (DBUtil) getInfoObject("dbUtil");
        //dsName = getStringInfo("SBR_DSN");
        dbUtil.getConnectionFromContainer();

        String contextIdSeq = StringEscapeUtils.escapeHtml(getStringInfo("P_CONTE_IDSEQ"));

//         if (conteIdseq == null) {
//           conteIdseq = "";
//         }

	String chk = StringEscapeUtils.escapeHtml(getStringInfo("chkContext"));

	String[] contexts = null;

	if((chk != null) && chk.equals("always")) {
	  Collection coll = (Collection)myRequest.getSession().getAttribute("userContexts");
	  contexts = new String[coll.size()];
	  int i=0;
	  for(Iterator it = coll.iterator(); it.hasNext(); i++)
	    contexts[i] = ((Context)it.next()).getConteIdseq();
	  myRequest.setAttribute("chkContext", chk);
	} else {
	  if ((contextIdSeq != null) && (contextIdSeq.length() > 0)) {
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


//         String additionalWhere =
//           " and upper(nvl(cs_conte.conte_idseq,'%')) like upper ( '%" +
//           conteIdseq + "%') ";

        cslb = new ClassificationsLOVBean(myRequest, dbUtil, additionalWhere);
        dbUtil.returnConnection();
      }
      else {
        cslb = (ClassificationsLOVBean) getInfoObject(ProcessConstants.CS_LOV);
        cslb.getCommonLOVBean().resetRequest(myRequest);
      }

      setResult(ProcessConstants.CS_LOV, cslb);
      setResult("tib", tib);
      setResult("performQuery", null);
      setCondition(SUCCESS);
    }
    catch (Exception ex) {
      try {
        setCondition(FAILURE);
        dbUtil.returnConnection();
      }
      catch (TransitionConditionException tce) {
        reportException(tce, DEBUG);
      }
      catch (Exception e) {
        reportException(e, DEBUG);
      }

      reportException(ex, DEBUG);
    }
  }

  protected TransitionCondition getPersistSuccessCondition() {
    return getCondition(SUCCESS);
  }

  protected TransitionCondition getPersistFailureCondition() {
    return getCondition(FAILURE);
  }

  private void initialize() {
    if (getStringInfo("INITIALIZED") == null) {
      //CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
      //setResult("SBREXT_DSN", params.getSbrextDSN());
      //setResult("SBR_DSN", params.getSbrDSN());
    }
  }
}
