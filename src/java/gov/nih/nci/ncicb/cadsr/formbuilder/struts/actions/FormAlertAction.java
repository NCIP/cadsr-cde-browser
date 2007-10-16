package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.cadsr.sentinel.util.DSRAlert;
import gov.nih.nci.cadsr.sentinel.util.DSRAlertImpl;
import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;





import gov.nih.nci.ncicb.cadsr.resource.Form;

import org.apache.struts.action.DynaActionForm;

public class FormAlertAction extends FormBuilderSecureBaseDispatchAction {
  /**
   * Set alert using setinalAPI
   *
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   *
   * @return
   *
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward setAlertForForm(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
      DynaActionForm dynaForm = (DynaActionForm)form;
      CDEBrowserParams params = CDEBrowserParams.getInstance();
      String url = params.getSentinalAPIUrl();
      String userName = request.getRemoteUser().toUpperCase();
      Form crf = (Form)getSessionObject(request,CRF);
      String formId = crf.getFormIdseq();
      int res = 0;
      DSRAlert sentinalApi = null;
      String alertName = "";
    try {
         sentinalApi = DSRAlertImpl.factory(url);
        res = sentinalApi.createAlert(userName,formId);
        alertName = sentinalApi.getAlertName();
    }
    catch (Exception exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception while setting alert for the form "+form , exp);
      }
      saveError(ERROR_FORM_ALERT, request);

      return mapping.findForward(FAILURE);
    }
    if(res!=DSRAlert.RC_CREATED&&res!=DSRAlert.RC_EXISTS)
    {
        saveError(ERROR_FORM_ALERT, request);
        return mapping.findForward(FAILURE);  
    }
    if (res==DSRAlert.RC_CREATED)
        saveMessage("cadsr.formbuilder.form.sentinal.success", request,alertName);
    if (res==DSRAlert.RC_EXISTS)
        saveMessage("cadsr.formbuilder.form.sentinal.exists", request,alertName);
    
    return mapping.findForward(SUCCESS);
  }

}
