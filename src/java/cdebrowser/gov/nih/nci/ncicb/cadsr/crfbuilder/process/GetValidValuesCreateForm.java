package gov.nih.nci.ncicb.cadsr.crfbuilder.process;

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

//Application Imports
import gov.nih.nci.ncicb.cadsr.base.process.BaseGenericProcess;



/**
 *
 * @author Ram Chilukuri 
 */
public class GetValidValuesCreateForm extends GenericProcess{
	
	public GetValidValuesCreateForm(){
 		super();
 		
		//add conditions here
		
		DEBUG = false;
	}


	/**
	* Registers all the parameters and results 
	* (<code>ProcessInfo</code>) for this process
	* during construction.
	* 
	* @author Ram Chilukuri	
	*/
	protected void registerInfo(){
		try{
			// register info here
			registerParameter(
				new ProcessParameter(ProcessConstants.USER,
				ProcessConstants.USER,
				"The logged in User",
				null));

			// result info
			


			// parameter info
			
		}
		catch(ProcessInfoException pie){
			reportException(pie, DEBUG);
		}
	}



	/**
	* Start:  Everything that this process does at runtime is
	* done within the scope of this method and anything that
	* it invokes.
	*
	* @author Ram Chilukuri	
	*/
	public void start(){
		try{
			// local variables for results
			

			// local variables for parameters
			


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
}
