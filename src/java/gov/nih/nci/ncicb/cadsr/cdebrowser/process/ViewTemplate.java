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
import gov.nih.nci.ncicb.cadsr.base.process.*;
import gov.nih.nci.ncicb.cadsr.resource.*;
import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.cdebrowser.userexception.*;
import gov.nih.nci.ncicb.cadsr.resource.handler.ReferenceBlobHandler;


/**
 *
 * @author Ram Chilukuri 
 */
public class ViewTemplate extends BasePersistingProcess
{
	private HttpServletRequest myRequest = null;
  private StringBuffer contextsList = null;
    
	public ViewTemplate()
	{
		this(null);

		DEBUG = false;
	} 

	public ViewTemplate(Service aService)
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
		try{
			registerStringParameter(ProcessConstants.TEMPLATE_IDSEQ);
      registerStringParameter(ProcessConstants.DOCUMNET_IDSEQ);
      registerResultObject(ProcessConstants.REFERENCE_BLOB_VO);
      registerResultObject("tib");
      registerResultObject("uem");
      
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
    Object sessionId = getSessionId();
		try
		{
      String crfIdseq = getStringInfo("templateIdseq");
      String docIdseq = getStringInfo("documentIdseq");
      ReferenceBlobHandler rh = null;
      ReferenceBlob rb = null;
      if(docIdseq!=null)
      {
        rh = (ReferenceBlobHandler)HandlerFactory.getHandler(ReferenceBlob.class);
        rb = (ReferenceBlob)rh.refDocForAdminComponent(docIdseq,sessionId);        
      }
     else
     {
        rh = (ReferenceBlobHandler)HandlerFactory.getHandler(ReferenceBlob.class);
        rb = (ReferenceBlob)rh.findObjectForAdminComponent(crfIdseq
                                                                      ,"IMAGE_FILE"
                                                                      ,sessionId);               
     }

      setResult(ProcessConstants.REFERENCE_BLOB_VO,rb);
      setResult("tib",null);
			setCondition(SUCCESS);
			}
    catch(DocumentNotFoundException ex){
      TabInfoBean tib;
      UserErrorMessage uem;
      try{
				tib = new TabInfoBean("cdebrowser_error_tabs");
        myRequest = (HttpServletRequest)getInfoObject("HTTPRequest");
        tib.processRequest(myRequest);
        if (tib.getMainTabNum() != 0) {
        tib.setMainTabNum(0);
        }
        uem = new UserErrorMessage();
        uem.setMsgOverview("Document not found");
        uem.setMsgText("Selected form/template does not have an associated document.");
        setResult("tib",tib);
        setResult("uem",uem);
        setCondition(FAILURE);
        throw ex;
        //setCondition("failure");
			}
			catch(TransitionConditionException tce){
				reportException(tce,DEBUG);
			}

			reportException(ex,DEBUG);
    } 
		catch(Exception ex){
      TabInfoBean tib;
      UserErrorMessage uem;
      try{
        tib = new TabInfoBean("cdebrowser_error_tabs");
        myRequest = (HttpServletRequest)getInfoObject("HTTPRequest");
        tib.processRequest(myRequest);
        if (tib.getMainTabNum() != 0) {
        tib.setMainTabNum(0);
        }
        uem = new UserErrorMessage();
        uem.setMsgOverview("Application Error");
        uem.setMsgText("An unknown application error has occured. Please re-start CDEBrowser");
        setResult("tib",tib);
        setResult("uem",uem);
				setCondition(FAILURE);
        throw ex;
			}
			catch(TransitionConditionException tce){
				reportException(tce,DEBUG);
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
