package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormBuilderBaseAction;


public abstract class FormBuilderSecureAction extends FormBuilderBaseAction {
  /**
   * This is a super class for actions that require user to be logged in.
   *
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    if (!isLoggedIn(request)) {
      String path =
        mapping.findForward(FormBuilderConstants.SUCCESS_KEY).getPath();

      setLoginToken(request, path);
      return mapping.findForward(FormBuilderConstants.SIGNON_KEY);
    }
    else {
      return executeCommand(mapping, form, request, response);
    }
  }
}
