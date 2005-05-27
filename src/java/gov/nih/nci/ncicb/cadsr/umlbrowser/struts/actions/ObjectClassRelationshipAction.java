package gov.nih.nci.ncicb.cadsr.umlbrowser.struts.actions;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormAction;
import gov.nih.nci.ncicb.cadsr.jsp.bean.OCRNavigationBean;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.umlbrowser.service.UmlBrowserService;
import gov.nih.nci.ncicb.cadsr.umlbrowser.service.UmlBrowserServiceException;
import gov.nih.nci.ncicb.cadsr.umlbrowser.struts.common.UmlBrowserFormConstants;
import gov.nih.nci.ncicb.cadsr.umlbrowser.struts.common.UmlBrowserNavigationConstants;

import gov.nih.nci.ncicb.cadsr.umlbrowser.util.OCUtils;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ObjectClassRelationshipAction extends UmlBrowserBaseDispatchAction
  implements UmlBrowserFormConstants,UmlBrowserNavigationConstants
{

   protected static Log log = LogFactory.getLog(ObjectClassRelationshipAction.class.getName());
   
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
  public ActionForward getObjectClassRelationships(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    
    DynaActionForm dynaForm = (DynaActionForm) form;
    String obcIdSeq = (String) dynaForm.get(OC_IDSEQ);
    
    if (log.isDebugEnabled()) {
    log.info("ocr for With object class " + obcIdSeq);
    }    

    try {

      UmlBrowserService service = this.getApplicationServiceLocator().findUmlBrowserService();

      List ocrs = service.getAssociationsForOC(obcIdSeq);      
      Map ocrMap = OCUtils.sortByOCRTypes(ocrs,obcIdSeq);
      
      ObjectClass objClass = service.getObjectClass(obcIdSeq);      
      setSessionObject(request,OBJECT_CLASS,objClass,true);
      
      setSessionObject(request,OUT_GOING_OCRS,ocrMap.get(OUT_GOING_OCRS),true);
      setSessionObject(request,IN_COMMING_OCRS,ocrMap.get(IN_COMMING_OCRS),true);
      setSessionObject(request,BIDIRECTIONAL_OCRS,ocrMap.get(BIDIRECTIONAL_OCRS),true);
      

    }
    catch (ServiceLocatorException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on getObjectClassRelationships obid= "+obcIdSeq );
      }

      return mapping.findForward(FAILURE);
    }

    return mapping.findForward(SUCCESS);
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
  public ActionForward clearNavigationPath(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    setSessionObject(request,OCR_NAVIGATION_BEAN,null);


    return mapping.findForward(SUCCESS);
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
  public ActionForward navigateOCR(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    
    DynaActionForm dynaForm = (DynaActionForm) form;
    String obcIdSeq = (String) dynaForm.get(OC_IDSEQ);
    String ocrIndex = (String) dynaForm.get(OCR_INDEX);
    String direction = (String) dynaForm.get(OCR_DIRECTION);
    
    if (log.isDebugEnabled()) {
      log.info("ocr for With object class " + obcIdSeq);
      log.info("ocr index " + ocrIndex);
      log.info("direction " + direction);
    }    

    try {

      //Get ocr navigation crumbs
      LinkedList crumbs = (LinkedList)getSessionObject(request,OCR_NAVIGATION_BEAN);
      if(crumbs==null)
      {
        crumbs = new LinkedList();
        setSessionObject(request,OCR_NAVIGATION_BEAN,crumbs,true);
      }
      if(crumbs.isEmpty())
        {
          OCRNavigationBean bean = new OCRNavigationBean();
          ObjectClass currObjectClass = (ObjectClass)getSessionObject(request,OBJECT_CLASS);
          bean.setObjectClass(currObjectClass);
          crumbs.add(bean);
        }
      

      OCRNavigationBean lastNavBean = (OCRNavigationBean)crumbs.getLast();
      //Make sure same object is not navigated // need review
      if(lastNavBean.getObjectClass().getId()!=obcIdSeq)
      {
              //get the list of ocrs depending on the direction clicked
              List oldList= (List)getSessionObject(request,direction);
              ObjectClassRelationship navigatedOCR = (ObjectClassRelationship)oldList.get(Integer.parseInt(ocrIndex));
        
              UmlBrowserService service = this.getApplicationServiceLocator().findUmlBrowserService();
        
              ObjectClass objClass = service.getObjectClass(obcIdSeq);
              List ocrs = service.getAssociationsForOC(obcIdSeq);
              
              
              Map ocrMap = OCUtils.sortByOCRTypes(ocrs,obcIdSeq);
              
              setSessionObject(request,OBJECT_CLASS,objClass,true);
              setSessionObject(request,OUT_GOING_OCRS,ocrMap.get(OUT_GOING_OCRS),true);
              setSessionObject(request,IN_COMMING_OCRS,ocrMap.get(IN_COMMING_OCRS),true);
              setSessionObject(request,BIDIRECTIONAL_OCRS,ocrMap.get(BIDIRECTIONAL_OCRS),true);
              
              //Update old bean
              lastNavBean.setOcr(navigatedOCR);
              lastNavBean.setDirection(direction);
              lastNavBean.setShowDirection(true);
              
              //Add new link
              OCRNavigationBean bean = new OCRNavigationBean();
              bean.setObjectClass(objClass);
              crumbs.add(bean);              
      }
    }
    catch (ServiceLocatorException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on getObjectClassRelationships obid= "+obcIdSeq );
      }

      return mapping.findForward(FAILURE);
    }

    return mapping.findForward(SUCCESS);
  }  


}