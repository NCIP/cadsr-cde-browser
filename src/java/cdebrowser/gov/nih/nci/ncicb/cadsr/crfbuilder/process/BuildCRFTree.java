package gov.nih.nci.ncicb.cadsr.crfbuilder.process;

import gov.nih.nci.ncicb.cadsr.base.process.BasePersistingProcess;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.handler.FormHandler;
import gov.nih.nci.ncicb.cadsr.util.TabInfoBean;

import javax.servlet.http.HttpServletRequest;

import oracle.cle.persistence.HandlerFactory;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;


/**
 * @author Ram Chilukuri
 */
public class BuildCRFTree extends BasePersistingProcess {
  public BuildCRFTree() {
    this(null);
    DEBUG = false;
  }

  public BuildCRFTree(Service aService) {
    super(aService);
    DEBUG = false;
  }

  /**
   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
   * this process during construction.
   */
  public void registerInfo() {
    try {
      registerStringParameter(CRFBuilderConstants.TARGET_FORM_ID);
      registerStringResult(CRFBuilderConstants.TARGET_FORM_ID);
      registerResultObject(CRFBuilderConstants.TARGET_FORM_TIB);
      registerResultObject(CRFBuilderConstants.TARGET_FORM);
      
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
    TabInfoBean tib = null;
    HttpServletRequest myRequest = null;
    try {
      String formIdseq = getStringInfo(CRFBuilderConstants.TARGET_FORM_ID);
      FormHandler formhandler =
        (FormHandler) HandlerFactory.getHandler(Form.class);
      Form frm = formhandler.findFormByPrimaryKey(formIdseq, getSessionId());
      tib = new TabInfoBean("crfbuilder_tabs");
      myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);

      if (tib.getMainTabNum() != 0) {
        tib.setMainTabNum(0);
      }

      setResult(CRFBuilderConstants.TARGET_FORM, frm);
      setResult(CRFBuilderConstants.TARGET_FORM_ID, formIdseq);
      setResult(CRFBuilderConstants.TARGET_FORM_TIB, tib);
      setCondition(SUCCESS);
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
