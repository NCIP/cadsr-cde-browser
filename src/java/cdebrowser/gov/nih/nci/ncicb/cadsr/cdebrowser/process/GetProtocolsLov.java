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


/**
 *
 * @author Ram Chilukuri 
 */
public class GetProtocolsLov extends BasePersistingProcess
{
	private HttpServletRequest myRequest = null;
  private StringBuffer contextsList = null;
    
	public GetProtocolsLov()
	{
		this(null);

		DEBUG = false;
	} 

	public GetProtocolsLov(Service aService)
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
		try
		{
      String crfIdseq = getStringInfo("templateIdseq");

			setCondition(SUCCESS);
			}
		catch(Exception ex){
      try{
				setCondition(FAILURE);
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