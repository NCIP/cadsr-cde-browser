package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants;
import gov.nih.nci.ncicb.cadsr.lov.ProtocolsLOVBean;
import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.util.TabInfoBean;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FormBuilderLOVAction extends FormBuilderBaseDispatchAction {
  /**
   * Returns all forms for the given criteria.
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
  public ActionForward getProtocolsLOV(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    FormBuilderServiceDelegate service = getFormBuilderService();
    DynaActionForm searchForm = (DynaActionForm) form;
    String protocolLongName =
      (String) searchForm.get(this.PROTOCOLS_LOV_PROTO_LONG_NAME);
    String contextIdSeq = (String) searchForm.get(this.CONTEXT_ID_SEQ);
    String performQuery = request.getParameter(this.PERFORM_QUERY_FIELD);

    if (contextIdSeq == null) {
      contextIdSeq = "";
    }

    String additionalWhere =
      " and upper(nvl(proto_conte.conte_idseq,'%')) like upper ( '%" +
      contextIdSeq + "%') ";

    DBUtil dbUtil = new DBUtil();
    ProtocolsLOVBean plb;

    try {
      TabInfoBean tib = new TabInfoBean("cdebrowser_lov_tabs");
      tib.processRequest(request);

      if (tib.getMainTabNum() != 0) {
        tib.setMainTabNum(0);
      }
      setSessionObject(request, this.PROTOCOLS_LOV_TAB_BEAN, tib);
      CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
      String dsName = params.getSbrDSN();
      dbUtil.getConnectionFromContainer(dsName);

      if (performQuery == null) {
        plb = new ProtocolsLOVBean(request, dbUtil, additionalWhere);
        setSessionObject(request, this.PROTOCOLS_LOV_BEAN, plb);
      }
      else {
        plb =
          (ProtocolsLOVBean) getSessionObject(request, this.PROTOCOLS_LOV_BEAN);
        plb.getCommonLOVBean().resetRequest(request);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    finally {
      try {
        dbUtil.returnConnection();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return mapping.findForward(SUCCESS);
  }

  /**
   * This Action forwards to the default formbuilder home.
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
  public ActionForward sendHome(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    return mapping.findForward(DEFAULT_HOME);
  }
}
