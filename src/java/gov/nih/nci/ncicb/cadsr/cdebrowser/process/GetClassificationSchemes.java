package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.base.process.BasePersistingProcess;
import gov.nih.nci.ncicb.cadsr.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.resource.Classification;
import gov.nih.nci.ncicb.cadsr.resource.handler.ClassificationHandler;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.util.BC4JPageIterator;
import gov.nih.nci.ncicb.cadsr.util.PageIterator;
import gov.nih.nci.ncicb.cadsr.util.TabInfoBean;
import gov.nih.nci.ncicb.cadsr.html.HTMLPageScroller;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import oracle.cle.persistence.HandlerFactory;
import oracle.cle.process.PersistingProcess;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;


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
    
		try{
      DataElement de = (DataElement)getInfoObject("de");
    
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
        scroller = new HTMLPageScroller(csIterator);
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
      myRequest = (HttpServletRequest)getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);
      int tabNum = tib.getMainTabNum();
      if (tabNum != 3) {
          tib.setMainTabNum(3);
      }
    
		} 
		catch(Exception e){
      e.printStackTrace();      
      
    } 
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