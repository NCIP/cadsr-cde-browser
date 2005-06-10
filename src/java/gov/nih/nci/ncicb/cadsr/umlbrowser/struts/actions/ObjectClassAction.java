package gov.nih.nci.ncicb.cadsr.umlbrowser.struts.actions;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.umlbrowser.service.UmlBrowserService;
import gov.nih.nci.ncicb.cadsr.umlbrowser.struts.common.UmlBrowserFormConstants;
import gov.nih.nci.ncicb.cadsr.umlbrowser.struts.common.UmlBrowserNavigationConstants;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ObjectClassAction extends UmlBrowserBaseDispatchAction
  implements UmlBrowserFormConstants,UmlBrowserNavigationConstants
{
  protected static Log log = LogFactory.getLog(ObjectClassAction.class.getName());
  public ObjectClassAction()
  {
  }

  /**
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
  public ActionForward getObjectClass(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    
    DynaActionForm dynaForm = (DynaActionForm) form;
    String obIdSeq = (String) dynaForm.get(OC_IDSEQ);
    String resetCrumbs = (String) dynaForm.get(this.RESET_CRUMBS);
    
    if (log.isDebugEnabled()) {
    log.info("ocr for With object class " + obIdSeq);
    }    


    try {

      UmlBrowserService service = this.getApplicationServiceLocator().findUmlBrowserService();
      ObjectClass objClass = service.getObjectClass(obIdSeq);      
      setSessionObject(request,OBJECT_CLASS,objClass,true);
      
      //List superClasses = service.getInheritenceRelationships(objClass);
      //setSessionObject(request,SUPER_OBJECT_CLASSES,superClasses,true);
      
      //Reset OCR Navigation bread crumbs if resetCrumbs is not null
      if(resetCrumbs!=null&&!resetCrumbs.equals(""))
        setSessionObject(request,OCR_NAVIGATION_BEAN,null);
    }
    catch (ServiceLocatorException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on getObjectClass obid= "+obIdSeq );
      }

      return mapping.findForward(FAILURE);
    }

    return mapping.findForward(SUCCESS);
  }
}