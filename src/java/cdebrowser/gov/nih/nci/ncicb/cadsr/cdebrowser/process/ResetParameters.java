/**
*
*Copyright (c) 2002 Oracle Corporation
*/

package gov.nih.nci.ncicb.cadsr.cdebrowser.process;


// java imports
import java.util.*;
import java.io.*;

// Framework imports
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.GenericProcess;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.Service;
import oracle.cle.process.ProcessConstants;
//Constants Imports



/**
 *
 * @author Oracle Corporation 
 */
public class ResetParameters extends GenericProcess
{
	


	public ResetParameters()
	{
 		super();
 		
		//add conditions here
		
		DEBUG = false;
	} // end default constructor


	/**
	* Registers all the parameters and results 
	* (<code>ProcessInfo</code>) for this process
	* during construction.
	* 
	* @author Oracle Corporation	
	*/
	protected void registerInfo()
	{
		try
		{
			// register info here
			registerParameter(
				new ProcessParameter(ProcessConstants.USER,
				ProcessConstants.USER,
				"The logged in User",
				null));

			// result info
			


			// parameter info
			
		} // end try
		catch(ProcessInfoException pie)
		{
			reportException(pie, DEBUG);
		} // end cath
	} // end registerInfo



	/**
	* Start:  Everything that this process does at runtime is
	* done within the scope of this method and anything that
	* it invokes.
	*
	* @author Oracle Corporation	
	*/
	public void start()
	{
		try
		{
			// local variables for results
			

			// local variables for parameters
			


			setCondition(SUCCESS);
			} // end try
		catch(Exception ex)
		{
			try
			{
				setCondition(FAILURE);
			} // end try
			catch(TransitionConditionException tce)
			{
				reportException(tce,DEBUG);
			} // end catch
			reportException(ex,DEBUG);
		} // end catch
	} // end start
}
