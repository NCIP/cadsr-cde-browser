package gov.nih.nci.ncicb.cadsr.crfbuilder.process;

import gov.nih.nci.ncicb.cadsr.base.process.BaseGenericProcess;
import gov.nih.nci.ncicb.cadsr.html.DropDownListHelper;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.handler.FormHandler;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.util.TabInfoBean;

import oracle.cle.persistence.HandlerFactory;

import oracle.cle.process.GenericProcess;
import oracle.cle.process.ProcessConstants;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.Service;

import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Ram Chilukuri
 */
public class GetCopyTemplateForm extends BaseGenericProcess {
  public GetCopyTemplateForm() {
    super();
    DEBUG = false;
  }

  /**
   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
   * this process during construction.
   */
  protected void registerInfo() {
    try {
      registerStringParameter(CRFBuilderConstants.SOURCE_FORM_ID);
      registerResultObject(CRFBuilderConstants.SRC_FORM_TIB);
      registerResultObject(CRFBuilderConstants.SOURCE_FORM);
      registerStringResult(CRFBuilderConstants.COPY_FORM_CONTEXTS_LIST);
      registerStringResult(CRFBuilderConstants.COPY_FORM_STATUSES_LIST);
      registerStringResult(CRFBuilderConstants.COPY_FORM_FORM_TYPES_LIST);
      registerParameterObject("dbUtil");
      registerStringParameter("SBR_DSN");
    } catch (ProcessInfoException pie) {
      reportException(pie, DEBUG);
    }
  }

  /**
   * Start:  Everything that this process does at runtime is done within the
   * scope of this method and anything that it invokes.
   */
  public void start() {
    TabInfoBean tib = null;
    HttpServletRequest myRequest = null;
    DBUtil dbUtil = null;

    try {
      String formIdseq = getStringInfo(CRFBuilderConstants.SOURCE_FORM_ID);
      FormHandler formhandler =
        (FormHandler) HandlerFactory.getHandler(Form.class);
      Form frm = formhandler.findFormByPrimaryKey(formIdseq, getSessionId());
      tib = new TabInfoBean("crfbuilder_tabs");
      myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);

      if (tib.getMainTabNum() != 0) {
        tib.setMainTabNum(0);
      }

      dbUtil = (DBUtil) getInfoObject("dbUtil");

      String dsName = getStringInfo("SBR_DSN");
      dbUtil.getConnectionFromContainer(dsName);

      //Getting context pop list
      String conteIdseq = frm.getConteIdseq();
      String contextsList =
        DropDownListHelper.getReadContextsList(
          conteIdseq, "jspCopyFrmContext", dbUtil);

      //Getting workflow status pop list
      String formStatus = frm.getAslName();
      String statusesList =
        DropDownListHelper.getACStatusesList(
          formStatus, "QUEST_CONTENT", "jspCopyFrmStatus", dbUtil);

      //Getting form type list
      String formType = frm.getFormType();
      String typesList =
        DropDownListHelper.getFormTypesList(formType, "jspCopyFrmType", dbUtil);

      setResult(CRFBuilderConstants.SOURCE_FORM, frm);
      setResult(CRFBuilderConstants.SRC_FORM_TIB, tib);
      setResult(CRFBuilderConstants.COPY_FORM_CONTEXTS_LIST, contextsList);
      setResult(CRFBuilderConstants.COPY_FORM_FORM_TYPES_LIST, typesList);
      setResult(CRFBuilderConstants.COPY_FORM_STATUSES_LIST, statusesList);
      setCondition(SUCCESS);
    } catch (Exception ex) {
      try {
        setCondition(FAILURE);
      } catch (TransitionConditionException tce) {
        reportException(tce, DEBUG);
      }

      reportException(ex, DEBUG);
    } finally {
      if (dbUtil != null) {
        try {
          dbUtil.returnConnection();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }
}
