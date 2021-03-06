/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.common.base.process.*;
import gov.nih.nci.ncicb.cadsr.common.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.common.database.*;
import gov.nih.nci.ncicb.cadsr.common.lov.ValueDomainsLOVBean;
import gov.nih.nci.ncicb.cadsr.common.util.*;

import oracle.cle.persistence.HandlerFactory;

import oracle.cle.process.PersistingProcess;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.Service;

import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

import java.io.*;

import java.util.*;

import javax.servlet.http.*;


/**
 * @author Ram Chilukuri
 */
public class GetValueDomainsLOV extends BasePersistingProcess {
  public GetValueDomainsLOV() {
    this(null);
    DEBUG = false;
  }

  public GetValueDomainsLOV(Service aService) {
    super(aService);
    DEBUG = false;
  }

  /**
   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
   * this process during construction.
   */
  public void registerInfo() {
    try {
      registerResultObject(ProcessConstants.VD_LOV);
      registerResultObject("tib");
      registerParameterObject("SEARCH");
      registerStringParameter("P_PARAM_TYPE");
      registerStringParameter("P_CONTEXT");
      registerStringParameter("P_CONTE_IDSEQ");
      registerParameterObject(ProcessConstants.VD_LOV);
      registerStringParameter("performQuery");
      registerStringResult("performQuery");
      registerParameterObject("dbUtil");
      registerStringParameter("SBR_DSN");
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
    String performQuery = null;
    ValueDomainsLOVBean vlb = null;
    String additionalWhere = "";
    DBUtil dbUtil = null;

    try {
      tib = new TabInfoBean("cdebrowser_lov_tabs");
      myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);

      if (tib.getMainTabNum() != 0) {
        tib.setMainTabNum(0);
      }

      performQuery = getStringInfo("performQuery");

      if (performQuery == null) {
        dbUtil = (DBUtil) getInfoObject("dbUtil");

        //String dsName = getStringInfo("SBR_DSN");
        dbUtil.getConnectionFromContainer();

        String conteIdseq = getStringInfo("P_CONTE_IDSEQ");

        if (conteIdseq == null) {
          conteIdseq = "";
        }
        else
		{

			if (!AppScanValidator.validateElementIdSequence(conteIdseq))
				throw new Exception ("Invalidate ID sequence");
		}
        additionalWhere =
          " and upper(nvl(vd_conte.conte_idseq,'%')) like upper ( '%" +
          conteIdseq + "%') ";
        vlb = new ValueDomainsLOVBean(myRequest, dbUtil, additionalWhere);
        dbUtil.returnConnection();
      }
      else {
        vlb = (ValueDomainsLOVBean) getInfoObject(ProcessConstants.VD_LOV);
        vlb.getCommonLOVBean().resetRequest(myRequest);
      }

      setResult(ProcessConstants.VD_LOV, vlb);
      setResult("performQuery", null);
      setResult("tib", tib);
      setCondition(SUCCESS);
    }
    catch (Exception ex) {
      try {
        setCondition(FAILURE);
        //dbUtil.returnConnection();
      }
      catch (TransitionConditionException tce) {
        reportException(tce, DEBUG);
      }
      catch (Exception e) {
        reportException(e, DEBUG);
      }

      reportException(ex, DEBUG);
    }finally{
    	if (dbUtil != null) {
			dbUtil.returnConnection();
		}
    }
  }

  protected TransitionCondition getPersistSuccessCondition() {
    return getCondition(SUCCESS);
  }

  protected TransitionCondition getPersistFailureCondition() {
    return getCondition(FAILURE);
  }
}
