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

/**
 * @author Ram Chilukuri
 */
public class GetCRFElementForEdit extends BasePersistingProcess {
  public GetCRFElementForEdit() {
    this(null);
    DEBUG = false;
  }

  public GetCRFElementForEdit(Service aService) {
    super(aService);
    DEBUG = false;
  }

  /**
   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
   * this process during construction.
   */
  public void registerInfo() {
    try {
      registerStringParameter(CRFBuilderConstants.EDIT_MODULE_ID);
      registerStringParameter(CRFBuilderConstants.EDIT_QUESTION_ID);
      registerStringParameter(CRFBuilderConstants.EDIT_VALUE_ID);
      registerResultObject(CRFBuilderConstants.EDIT_MODULE);
      registerResultObject(CRFBuilderConstants.EDIT_QUESTION);
      registerResultObject(CRFBuilderConstants.EDIT_VALUE);
      registerStringParameter(CRFBuilderConstants.EDIT_CRF_ELEMENT_TYPE);
      registerStringResult(CRFBuilderConstants.EDIT_CRF_ELEMENT_TYPE);
      registerResultObject(CRFBuilderConstants.EDIT_CRF_ELEMENT_TIB);
      registerStringResult(CRFBuilderConstants.EDIT_MODULE_STATUSES_LIST);
      registerParameterObject(CRFBuilderConstants.TARGET_FORM);
      registerResultObject(CRFBuilderConstants.TARGET_FORM);
      registerParameterObject("dbUtil");
      registerStringParameter("SBR_DSN");
      registerStringResult(CRFBuilderConstants.UPDATE_QC_IDSEQ);
    } catch (ProcessInfoException pie) {
      reportException(pie, true);
    }
  }

  /**
   * persist: called by start to do all persisting work for this process.  If
   * this method throws an exception, then the condition returned by the
   * <code>getPersistFailureCondition()</code> is set.  Otherwise, the
   * condition returned by <code>getPersistSuccessCondition()</code> is set.
   */
  public void persist() throws Exception {
    String elementType = null;
    TabInfoBean tib = null;
    HttpServletRequest myRequest = null;
    DBUtil dbUtil = null;
    try {
      elementType = getStringInfo(CRFBuilderConstants.EDIT_CRF_ELEMENT_TYPE);

      if ("Module".equals(elementType)) {
        String modIdseq = getStringInfo(CRFBuilderConstants.EDIT_MODULE_ID);
        ModuleHandler modHandler = 
          (ModuleHandler) HandlerFactory.getHandler(Module.class);
        Module editModule =
          modHandler.findModuleByPrimaryKey(
            modIdseq, getSessionId());
        setResult(CRFBuilderConstants.EDIT_MODULE, editModule);
        dbUtil = (DBUtil) getInfoObject("dbUtil");

        String dsName = getStringInfo("SBR_DSN");
        dbUtil.getConnectionFromContainer(dsName);

        //Getting workflow status pop list
        String modStatus = editModule.getAslName();
        String statusesList =
          DropDownListHelper.getACStatusesList(
            modStatus, "QUEST_CONTENT", "jspStatus", dbUtil);
        setResult(CRFBuilderConstants.EDIT_MODULE_STATUSES_LIST,statusesList);
        setResult(CRFBuilderConstants.UPDATE_QC_IDSEQ,modIdseq);

      }

      tib = new TabInfoBean("crfbuilder_tabs");
      myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);

      if (tib.getMainTabNum() != 0) {
        tib.setMainTabNum(0);
      }

      Form frm = (Form)getInfoObject(CRFBuilderConstants.TARGET_FORM);
      setCondition(SUCCESS);
      setResult(CRFBuilderConstants.EDIT_CRF_ELEMENT_TIB, tib);
      setResult(CRFBuilderConstants.TARGET_FORM,frm);
      setResult(CRFBuilderConstants.EDIT_CRF_ELEMENT_TYPE,elementType);
    } catch (Exception ex) {
      try {
        setCondition(FAILURE);
      } catch (TransitionConditionException tce) {
        reportException(tce, DEBUG);
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
}
