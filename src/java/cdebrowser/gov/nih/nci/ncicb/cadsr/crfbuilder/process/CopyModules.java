package gov.nih.nci.ncicb.cadsr.crfbuilder.process;
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

//Application Imports
import gov.nih.nci.ncicb.cadsr.base.process.BasePersistingProcess;
//import gov.nih.nci.ncicb.cadsr.resource.Form;
//import gov.nih.nci.ncicb.cadsr.resource.FormHandler;




/**
 *
 * @author Ram Chilukuri 
 */
public class CopyModules extends PersistingProcess{

	public CopyModules(){
		this(null);
		DEBUG = false;
	}

	public CopyModules(Service aService){
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
	public void registerInfo(){
		try{
			registerParameter(
				new ProcessParameter(ProcessConstants.USER,
				ProcessConstants.USER,
				"The logged in User",
				null));

		}
		catch(ProcessInfoException pie){
			reportException(pie,true);
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
	* @author Ram Chilukuri
	*/
	public void persist() throws Exception{
		try{
			

			//create the handler
			//		FormHandler formhandler = 
			//(FormHandler)HandlerFactory.getHandler(Form.class);

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

	protected TransitionCondition getPersistSuccessCondition(){
		return getCondition(SUCCESS);
	}

	protected TransitionCondition getPersistFailureCondition(){
		return getCondition(FAILURE);
	}
}
