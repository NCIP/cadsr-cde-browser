/**
*
*Copyright (c) 2002 Oracle Corporation
*/
package gov.nih.nci.ncicb.cadsr.cdebrowser.process;
// java imports
import java.util.*;
import java.io.*;
import javax.servlet.http.*;

// Framework imports
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.PersistingProcess;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.Service;
//import oracle.cle.process.ProcessConstants;
import oracle.cle.persistence.HandlerFactory;

//CDE Browser Application Imports

import gov.nih.nci.ncicb.cadsr.util.*;
import gov.nih.nci.ncicb.cadsr.database.*;
//import healthtoolkit.beans.dbservice.*;
//import healthtoolkit.utils.*;
import gov.nih.nci.ncicb.cadsr.base.process.*;
import gov.nih.nci.ncicb.cadsr.lov.ClassificationsLOVBean;
import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;


/**
 *
 * @author Ram Chilukuri 
 */
public class GetClassificationsLOV extends BasePersistingProcess
{
	  
	public GetClassificationsLOV()
	{
		this(null);
		DEBUG = false;
	}


	public GetClassificationsLOV(Service aService)
	{
		super(aService);
		DEBUG = false;
	}

	/**
	* Registers all the parameters and results 
	* (<code>ProcessInfo</code>) for this process
	* during construction.
	*
	* @author Ram Chilukuri
	*/
	public void registerInfo()
	{
		try
		{
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
		} 
		catch(ProcessInfoException pie){
			reportException(pie,true);
		}

    catch(Exception e) {
    }
	} 

	/**
	* persist: called by start to do all persisting
	*   work for this process.  If this method throws
	*   an exception, then the condition returned by
	*   the <code>getPersistFailureCondition()</code>
	*   is set.  Otherwise, the condition returned by
	*   <code>getPersistSuccessCondition()</code> is
	*   set.
	*
	* @author Oracle
	*/
	public void persist() throws Exception
	{
    HttpServletRequest myRequest = null;
    //DBBroker ctlrDBBroker = null;
    //DBLAccess dblAccess = null;
    TabInfoBean tib = null;
    String [] searchParam = null;
    ClassificationsLOVBean cslb = null;
    String performQuery = null;
    DBUtil dbUtil = null;
		try
		{
			tib = new TabInfoBean("cdebrowser_lov_tabs");
      myRequest = (HttpServletRequest)getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);
      if (tib.getMainTabNum() != 0) {
        tib.setMainTabNum(0);
      }
      searchParam = getInfoStringArray("SEARCH");
      if (searchParam != null) {
        int numberOfSearchFields = searchParam.length;
        for (int i=0; i<numberOfSearchFields; i++) {
          //System.out.println("Search[" +i +"]" + searchParam[i]);
        }
      }
      
      performQuery = getStringInfo("performQuery");
      if (performQuery == null){
        //dblAccess = DBLAccess.getDBLAccess("sbr");
        //ctlrDBBroker = dblAccess.getDBBroker();
        dbUtil = (DBUtil)getInfoObject("dbUtil");
        String dsName = getStringInfo("SBR_DSN");
        dbUtil.getConnectionFromContainer(dsName);
        String conteIdseq = getStringInfo("P_CONTE_IDSEQ");
        if (conteIdseq ==null) conteIdseq = "";
        String additionalWhere = " and upper(nvl(cs_conte.conte_idseq,'%')) like upper ( '%"+conteIdseq+"%') ";
        //cslb = new ClassificationsLOVBean(myRequest,dblAccess,additionalWhere);
        cslb = new ClassificationsLOVBean(myRequest,dbUtil,additionalWhere);
        dbUtil.returnConnection();
        //dblAccess.returnDb();
      }
      else {
        cslb = (ClassificationsLOVBean)getInfoObject(ProcessConstants.CS_LOV);
        cslb.getCommonLOVBean().resetRequest(myRequest);
      }

      setResult(ProcessConstants.CS_LOV,cslb);
      setResult("tib",tib);
      setResult("performQuery",null);
 			setCondition(SUCCESS);
			}
		catch(Exception ex){
      /*if (ctlrDBBroker != null) {
        DBBrokerFactory.checkInRemove(ctlrDBBroker);
        //System.out.println("CheckIn with REMOVE performed!!");
      }*/
			try{
				setCondition(FAILURE);
        dbUtil.returnConnection();
			} 
			catch(TransitionConditionException tce){
				reportException(tce,DEBUG);
			}
      catch(Exception e){
        reportException(e,DEBUG);
      }

			reportException(ex,DEBUG);
			} 
	} 

	protected TransitionCondition getPersistSuccessCondition()
	{
		return getCondition(SUCCESS);
	} 

	protected TransitionCondition getPersistFailureCondition()
	{
		return getCondition(FAILURE);
	} 
    
}
