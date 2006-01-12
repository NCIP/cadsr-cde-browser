package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.base.process.BasePersistingProcess;
import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.cdebrowser.service.CDEBrowserService;
import gov.nih.nci.ncicb.cadsr.html.HTMLPageScroller;
import gov.nih.nci.ncicb.cadsr.resource.Classification;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.handler.ClassificationHandler;
import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.BC4JPageIterator;
import gov.nih.nci.ncicb.cadsr.util.PageIterator;
import gov.nih.nci.ncicb.cadsr.util.TabInfoBean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import oracle.cle.persistence.HandlerFactory;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;


/**
 *
 * @author Ram Chilukuri 
 */
public class GetClassificationSchemes extends BasePersistingProcess
{
  
  public GetClassificationSchemes()
  {
   this(null);
   DEBUG = false;
  }

  public GetClassificationSchemes(Service aService)
  {
   super(aService);
   DEBUG = false;
  }

  /**
	* Registers all the parameters and results 
	* (<code>ProcessInfo</code>) for this process
	* during construction.
	*/
	public void registerInfo(){
		try{
      registerParameterObject("de");
      registerResultObject("tib");
      registerResultObject("csRefDocs");
      registerResultObject("csiRefDocs");
      registerResultObject(ProcessConstants.CLASSIFICATION_VECTOR);

      registerStringParameter("newSearch");
      registerStringResult("newSearch");
      registerStringParameter(ProcessConstants.DE_CS_RESULTS_PAGE_NUMBER);
      registerStringResult(ProcessConstants.DE_CS_RESULTS_PAGE_NUMBER);
      registerParameterObject(ProcessConstants.DE_CS_PAGE_ITERATOR);
      registerResultObject(ProcessConstants.DE_CS_PAGE_ITERATOR);
      registerResultObject(ProcessConstants.DE_CS_PAGE_SCROLLER);
      registerParameterObject(ProcessConstants.DE_CS_PAGE_SCROLLER);
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
	* @author Oracle Corporation
	*/
	public void persist() throws Exception{

    TabInfoBean tib = null;
    Vector classificationSchemes = new Vector();
    HttpServletRequest myRequest = null;
    String newSearch = null;
    PageIterator csIterator = null;
    String pageNumber = null;
    int blockSize = 20;
    HTMLPageScroller scroller = null;
    String scrollerHTML = "";
	 Map csRefDocs = new HashMap();
    Map csiRefDocs = new HashMap();
		try{
      DataElement de = (DataElement)getInfoObject("de");
      myRequest = (HttpServletRequest)getInfoObject("HTTPRequest");
    
      String deIdseq = de.getDeIdseq();
      Object sessionId = getSessionId();
      newSearch = getStringInfo("newSearch");

      ClassificationHandler classificationHandler =
          (ClassificationHandler)HandlerFactory.getHandler(Classification.class);

      if (newSearch == null) {
        pageNumber = "0";
        csIterator = new BC4JPageIterator(blockSize);
        csIterator.setCurrentPage(0);

        //Creating page scroller drop down
        scroller = new HTMLPageScroller(csIterator, myRequest.getContextPath());
        scroller.setPageListName("cs_pages");
        scrollerHTML = "";
      }
      else {
        pageNumber = getStringInfo(ProcessConstants.DE_CS_RESULTS_PAGE_NUMBER);
        csIterator = (BC4JPageIterator)getInfoObject
                     (ProcessConstants.DE_CS_PAGE_ITERATOR);
        csIterator.setCurrentPage(Integer.parseInt(pageNumber));
        scroller = (HTMLPageScroller)getInfoObject
                     (ProcessConstants.DE_CS_PAGE_SCROLLER);
      }

      /*classificationSchemes = classificationHandler.getClassificationSchemes(
                              deIdseq,sessionId);*/

      classificationSchemes = classificationHandler.getClassificationSchemes(
                              deIdseq,csIterator,sessionId);

      scroller.setExtraURLInfo("&newSearch=no");
      scroller.generateHTML();
      scrollerHTML = scroller.getScrollerHTML();

      tib = new TabInfoBean("cdebrowser_details_tabs");
      tib.processRequest(myRequest);
      int tabNum = tib.getMainTabNum();
      if (tabNum != 3) {
          tib.setMainTabNum(3);
      }
      
      ApplicationServiceLocator  appServiceLocator =(ApplicationServiceLocator)
      myRequest.getSession().getServletContext().getAttribute(ApplicationServiceLocator.APPLICATION_SERVICE_LOCATOR_CLASS_KEY);
      
      CDEBrowserService cdeBrowserService = appServiceLocator.findCDEBrowserService();
      Iterator iter = classificationSchemes.iterator();
      Set csNames = new HashSet();  // to avoid duplicate cs 
      Set csiNames = new HashSet();
      
      while (iter.hasNext()) {
         
         Classification cs = (Classification) iter.next();
         if (!csNames.contains(cs.getClassSchemeName())) {
            List refDocs = cdeBrowserService.getReferenceDocuments(cs.getCsIdseq());
            if (refDocs != null && refDocs.size()>0)
               csRefDocs.put(cs.getClassSchemeName(), refDocs);
            csNames.add(cs.getClassSchemeName());
         }
         if (!csiNames.contains(cs.getClassSchemeItemName())) {
            List refDocs = cdeBrowserService.getReferenceDocumentsForCSI(cs.getCsiIdseq());
            if (refDocs != null && refDocs.size()>0)
               csiRefDocs.put(cs.getClassSchemeItemName(), refDocs);
            csiNames.add(cs.getClassSchemeItemName());
         }
      }
		} 
		catch(Exception e){
      e.printStackTrace();      
      
    } 
    setResult("csRefDocs", csRefDocs);
    setResult("csiRefDocs", csiRefDocs);
    setResult(ProcessConstants.CLASSIFICATION_VECTOR, classificationSchemes);
    setResult(ProcessConstants.DE_CS_PAGE_ITERATOR,csIterator);
    setResult(ProcessConstants.DE_CS_PAGE_SCROLLER,scroller);
    setResult(ProcessConstants.DE_CS_RESULTS_PAGE_NUMBER,pageNumber);
    setResult("tib",tib);
		setCondition(SUCCESS);
	} 



	protected TransitionCondition getPersistSuccessCondition()
	{
		return getCondition(SUCCESS);
	} // end getPersistSuccessCondition



	protected TransitionCondition getPersistFailureCondition()
	{
		return getCondition(FAILURE);
	} // end getPersistFailureCondition
  
}