package gov.nih.nci.ncicb.cadsr.crfbuilder.process;
import gov.nih.nci.ncicb.cadsr.base.process.BasePersistingProcess;
import gov.nih.nci.ncicb.cadsr.html.DropDownListHelper;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.handler.ModuleHandler;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.util.TabInfoBean;

import javax.servlet.http.HttpServletRequest;

import oracle.cle.persistence.HandlerFactory;
import oracle.cle.process.PersistingProcess;
import oracle.cle.process.ProcessConstants;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

import gov.nih.nci.ncicb.cadsr.dto.bc4j.*;

/**
 *
 * @author Ram Chilukuri 
 */
public class UpdateCRFElement extends BasePersistingProcess{

	public UpdateCRFElement(){
		this(null);
		DEBUG = false;
	}

	public UpdateCRFElement(Service aService){
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
			registerStringParameter(CRFBuilderConstants.UPDATE_QC_IDSEQ);
      registerStringParameter(CRFBuilderConstants.UPDATE_ASL_NAME);
      registerStringParameter(CRFBuilderConstants.UPDATE_CONTE_IDSEQ);
      registerStringParameter(CRFBuilderConstants.UPDATE_DEFINITION);
      registerStringParameter(CRFBuilderConstants.UPDATE_LONG_NAME);
      registerStringParameter(CRFBuilderConstants.UPDATE_PREF_NAME);
      registerStringParameter(CRFBuilderConstants.UPDATE_VERSION);
      registerStringParameter(CRFBuilderConstants.UPDATE_ELEMENT_TYPE);
      registerStringResult(CRFBuilderConstants.TARGET_FORM_ID);
      registerParameterObject(CRFBuilderConstants.TARGET_FORM);

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
			String elementType = getStringInfo(CRFBuilderConstants.UPDATE_ELEMENT_TYPE);
      String qcIdseq = getStringInfo(CRFBuilderConstants.UPDATE_QC_IDSEQ);
      String prefName = getStringInfo(CRFBuilderConstants.UPDATE_PREF_NAME);
      String longName = getStringInfo(CRFBuilderConstants.UPDATE_LONG_NAME);
      String version = getStringInfo(CRFBuilderConstants.UPDATE_VERSION);
      String def = getStringInfo(CRFBuilderConstants.UPDATE_DEFINITION);
      String status = getStringInfo(CRFBuilderConstants.UPDATE_ASL_NAME);
      String conte = getStringInfo(CRFBuilderConstants.UPDATE_CONTE_IDSEQ);
      if ("Module".equals(elementType)) {
        Module mod = new BC4JModuleTransferObject();
        mod.setAslName(status);
        mod.setConteIdseq(conte);
        mod.setLongName(longName);
        mod.setModuleIdseq(qcIdseq);
        mod.setPreferredDefinition(def);
        mod.setPreferredName(prefName);
        mod.setVersion(new Float(Float.parseFloat(version)));
        updateModule(mod);
      }
      else if ("Question".equals(elementType)) {
        updateQuestion();
      }
      else if ("Value".equals(elementType)) {
        updateFormValue();
      }
      
      Form frm = (Form)getInfoObject(CRFBuilderConstants.TARGET_FORM);
      String frmIdseq = frm.getFormIdseq();
      setResult(CRFBuilderConstants.TARGET_FORM_ID,frmIdseq);
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

  private void updateModule (Module mod) throws Exception {
    ModuleHandler modhandler = 
			(ModuleHandler)HandlerFactory.getHandler(Module.class);
    int val = modhandler.editModule(mod,getSessionId());
  }

  private void updateQuestion () throws Exception {
    
  }

  private void updateFormValue () throws Exception {
    
  }
}
