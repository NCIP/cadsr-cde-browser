package gov.nih.nci.ncicb.cadsr.cdebrowser.process;
// java imports
import java.util.*;
import java.io.*;

// Framework imports
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.PersistingProcess;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.Service;
import oracle.cle.process.ProcessConstants;
import oracle.cle.persistence.HandlerFactory;
//Constants Imports




/**
 *
 * @author Oracle Corporation 
 */
public class GetValidValueDetails extends PersistingProcess
{

	public GetValidValueDetails()
	{
		this(null);

		DEBUG = false;
	} // end default constructor


	public GetValidValueDetails(Service aService)
	{
		super(aService);

		DEBUG = false;
	} // end Constructor 2



	/**
	* Registers all the parameters and results 
	* (<code>ProcessInfo</code>) for this process
	* during construction.
	*
	* @author Oracle Corporation
	*/
	public void registerInfo()
	{
		try
		{
			// result info
			

      
			// parameter info
			//Added manually by Amit
      registerParameter(
				new ProcessParameter(ProcessConstants.USER,
				ProcessConstants.USER,
				"The logged in User",
				null));
		} // end try
		catch(ProcessInfoException pie)
		{
			reportException(pie,true);
		} // end catch    
	} // end registerInfo



	/**
	* persist: called by start to do all persisting
	*   work for this process.  If this method throws
	*   an exception, then the condition returned by
	*   the <code>getPersistFailureCondition()</code>
	*   is set.  Otherwise, the condition returned by
	*   <code>getPersistSuccessCondition()</code> is
	*   set.
	*
	* @author Oracle Corporation
	*/
	public void persist() throws Exception
	
	{
		try
		{
			//local variables for results
			

 			//local variables for parameters
			


			//create the handler
			

			setCondition(SUCCESS);
			} // end try
		catch(Exception ex)
		{
			try
			{
				setCondition(FAILURE);
			} // end catch
			catch(TransitionConditionException tce)
			{
				reportException(tce,DEBUG);
			} // end catch
				reportException(ex,DEBUG);
			} // end catch
	} // end persist



	protected TransitionCondition getPersistSuccessCondition()
	{
		return getCondition(SUCCESS);
	} // end getPersistSuccessCondition



	protected TransitionCondition getPersistFailureCondition()
	{
		return getCondition(FAILURE);
	} // end getPersistFailureCondition
}
