package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.base.process.*;
import gov.nih.nci.ncicb.cadsr.cdebrowser.ProtocolsForCdeBean;
import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.database.*;
import gov.nih.nci.ncicb.cadsr.html.HTMLPageScroller;
import gov.nih.nci.ncicb.cadsr.resource.*;
import gov.nih.nci.ncicb.cadsr.resource.handler.DataElementHandler;
import gov.nih.nci.ncicb.cadsr.util.*;
import gov.nih.nci.ncicb.cadsr.util.BC4JPageIterator;
import gov.nih.nci.ncicb.cadsr.util.PageIterator;

import oracle.cle.persistence.HandlerFactory;

import oracle.cle.process.PersistingProcess;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.Service;

import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

import java.io.*;

import java.util.*;

import javax.servlet.http.*;


public class GetProtocols extends BasePersistingProcess {
  public GetProtocols() {
    this(null);

    DEBUG = false;
  }

  public GetProtocols(Service aService) {
    super(aService);

    DEBUG = false;
  }

  /**
   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
   * this process during construction.
   */
  public void registerInfo() {
    try {
      registerParameterObject("de");
      registerResultObject("tib");
      registerResultObject(ProcessConstants.DE_USAGES_LIST);
      registerStringParameter("performQuery");
      registerStringResult("performQuery");
      registerStringParameter(ProcessConstants.DE_FORM_RESULTS_PAGE_NUMBER);
      registerStringResult(ProcessConstants.DE_FORM_RESULTS_PAGE_NUMBER);
      registerParameterObject("usagesPageIterator");
      registerResultObject("usagesPageIterator");
      registerResultObject(ProcessConstants.DE_FORM_PAGE_SCROLLER);
      registerParameterObject(ProcessConstants.DE_FORM_PAGE_SCROLLER);
    }
    catch (ProcessInfoException pie) {
      reportException(pie, true);
    }
    catch (Exception e) {
    }
  }

  /**
   * persist: called by start to do all persisting work for this process.  If
   * this method throws an exception, then the condition returned by the
   * <code>getPersistFailureCondition()</code> is set.  Otherwise, the
   * condition returned by <code>getPersistSuccessCondition()</code> is set.
   */
  public void persist() throws Exception {
    List usages = new ArrayList();
    TabInfoBean tib = null;
    String performQuery = null;
    String pageNumber = null;
    HttpServletRequest myRequest = null;
    PageIterator usagesIterator = null;
    List pageList = null;
    StringBuffer pagesDropDown = null;
    HTMLPageScroller scroller = null;
    String scrollerHTML = "";
    int blockSize = 20;

    try {
      tib = new TabInfoBean("cdebrowser_details_tabs");
      myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);

      if (tib.getMainTabNum() != 4) {
        tib.setMainTabNum(4);
      }

      DataElement de = (DataElement) getInfoObject("de");
      String deIdseq = de.getDeIdseq();
      Object sessionId = getSessionId();
      performQuery = getStringInfo("performQuery");

      if (performQuery == null) {
        pageNumber = "0";
        usagesIterator = new BC4JPageIterator(blockSize);
        usagesIterator.setCurrentPage(0);

        //Creating page scroller drop down
        scroller = new HTMLPageScroller(usagesIterator);
        scroller.setPageListName("usages_pages");
      }
      else {
        pageNumber =
          getStringInfo(ProcessConstants.DE_FORM_RESULTS_PAGE_NUMBER);
        usagesIterator = (BC4JPageIterator) getInfoObject("usagesPageIterator");
        usagesIterator.setCurrentPage(Integer.parseInt(pageNumber));
        scroller =
          (HTMLPageScroller) getInfoObject(
            ProcessConstants.DE_FORM_PAGE_SCROLLER);
      }

      DataElementHandler dh =
        (DataElementHandler) HandlerFactory.getHandler(DataElement.class);
      usages = dh.getAllFormUsages(deIdseq, sessionId, usagesIterator);

      scroller.setExtraURLInfo("&performQuery=no");
      scroller.generateHTML();
      scrollerHTML = scroller.getScrollerHTML();

      setResult(ProcessConstants.DE_USAGES_LIST, usages);
      setResult(ProcessConstants.DE_FORM_PAGE_SCROLLER, scroller);
      setResult("usagesPageIterator", usagesIterator);
      setResult("tib", tib);
      setResult("performQuery", null);
      setResult(ProcessConstants.DE_FORM_RESULTS_PAGE_NUMBER, null);
      setCondition(SUCCESS);
    }
    catch (Exception ex) {
      try {
        setCondition(FAILURE);

        //dbUtil.returnConnection();
      }
      catch (TransitionConditionException tce) {
        reportException(tce, DEBUG);
      }
      catch (Exception e) {
        reportException(e, DEBUG);
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
