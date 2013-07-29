/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.base.process.BasePersistingProcess;
import gov.nih.nci.ncicb.cadsr.common.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.common.dto.ConceptDerivationRuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.html.HTMLPageScroller;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AdminComponentDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ConceptDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ValueDomainDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomain;
import gov.nih.nci.ncicb.cadsr.common.resource.handler.ValidValueHandler;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.common.util.BC4JPageIterator;
import gov.nih.nci.ncicb.cadsr.common.util.GenericPopListBean;
import gov.nih.nci.ncicb.cadsr.common.util.PageIterator;
import gov.nih.nci.ncicb.cadsr.common.util.TabInfoBean;

import oracle.cle.persistence.HandlerFactory;

import oracle.cle.process.PersistingProcess;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.Service;

import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Ram Chilukuri
 */
public class GetValidValues extends BasePersistingProcess {
  public GetValidValues() {
    this(null);
    DEBUG = false;
  }

  public GetValidValues(Service aService) {
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
      registerResultObject(ProcessConstants.VALID_VALUES_LIST);
      registerStringParameter("performQuery");
      registerStringResult("performQuery");
      registerStringParameter(ProcessConstants.VALID_VALUES_PAGE_NUMBER);
      registerStringResult(ProcessConstants.VALID_VALUES_PAGE_NUMBER);
      registerParameterObject("validValuesPageIterator");
      registerResultObject("validValuesPageIterator");
      registerResultObject(ProcessConstants.VALID_VALUES_PAGE_LIST);
      registerResultObject(ProcessConstants.VALID_VALUES_PAGE_SCROLLER);
      registerParameterObject(ProcessConstants.VALID_VALUES_PAGE_SCROLLER);
    }
    catch (ProcessInfoException pie) {
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
    //Vector validValues = new Vector();
    TabInfoBean tib = null;
    ValueDomain vd = null;
    String performQuery = null;
    String pageNumber = null;
    HttpServletRequest myRequest = null;
    PageIterator vvIterator = null;
    List vvList = null;
    List pageList = null;
    StringBuffer pagesDropDown = null;
    HTMLPageScroller scroller = null;
    String scrollerHTML = "";
    int blockSize = 20;

    try {
      DataElement de = (DataElement) getInfoObject("de");
      String vdIdseq = de.getVdIdseq();
      Object sessionId = getSessionId();
      performQuery = getStringInfo("performQuery");

      tib = new TabInfoBean("cdebrowser_details_tabs");
      myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);

      int tabNum = tib.getMainTabNum();

      if (tabNum != 2) {
        tib.setMainTabNum(2);
      }

      //Create a BC4JPageIterator object when the user enters valid values tab 
      //first time. This BC4JPageIterator object will be placed in the info 
      //table which, will be used for scrolling thru different pages
      if (performQuery == null) {
        pageNumber = "0";
        vvIterator = new BC4JPageIterator(blockSize);
        vvIterator.setCurrentPage(0);

        //Creating page scroller drop down
        scroller = new HTMLPageScroller(vvIterator, myRequest.getContextPath());
        scroller.setPageListName("vv_pages");
      }
      else {
        pageNumber = getStringInfo(ProcessConstants.VALID_VALUES_PAGE_NUMBER);
        vvIterator =
          (BC4JPageIterator) getInfoObject("validValuesPageIterator");
        vvIterator.setCurrentPage(Integer.parseInt(pageNumber));
        scroller =
          (HTMLPageScroller) getInfoObject(
            ProcessConstants.VALID_VALUES_PAGE_SCROLLER);
      }

      ServiceLocator locator =
        ServiceLocatorFactory.getLocator(
          CaDSRConstants.CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);

      AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
      ConceptDAO conDAO = daoFactory.getConceptDAO();

      if (de.getValueDomain().getConceptDerivationRule() != null) {
        ConceptDerivationRule rule =
          conDAO.findConceptDerivationRule(
            de.getValueDomain().getConceptDerivationRule().getIdseq());
        de.getValueDomain().setConceptDerivationRule(rule);
      }

      if (de.getValueDomain().getRefereceDocs() == null) {
        ValueDomainDAO vdDAO = daoFactory.getValueDomainDAO();
        de.getValueDomain().setReferenceDocs(vdDAO.getAllReferenceDocuments(de.getValueDomain().getVdIdseq(), null));
      }
      
      if (de.getValueDomain().getContext() == null) {
          ValueDomainDAO vdDAO = daoFactory.getValueDomainDAO();
          de.getValueDomain().setContext(vdDAO.getContext(de.getVdIdseq()));
      }
      
      ValidValueHandler validValueHandler =
        (ValidValueHandler) HandlerFactory.getHandler(ValidValue.class);
      vvList =
        validValueHandler.getMyValidValues(vdIdseq, sessionId, vvIterator);

      if ((vvList != null) && !vvList.isEmpty()) {
        vvList = conDAO.populateConceptsForValidValues(vvList);
      }

      scroller.setExtraURLInfo("&performQuery=no");
      scroller.generateHTML();
      scrollerHTML = scroller.getScrollerHTML();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    setResult(ProcessConstants.VALID_VALUES_LIST, vvList);
    setResult(ProcessConstants.VALID_VALUES_PAGE_LIST, pagesDropDown);
    setResult(ProcessConstants.VALID_VALUES_PAGE_SCROLLER, scroller);
    setResult("validValuesPageIterator", vvIterator);
    setResult("tib", tib);
    setResult("performQuery", null);
    setResult(ProcessConstants.VALID_VALUES_PAGE_NUMBER, null);
    setCondition(SUCCESS);
  }

  protected TransitionCondition getPersistSuccessCondition() {
    return getCondition(SUCCESS);
  }

  protected TransitionCondition getPersistFailureCondition() {
    return getCondition(FAILURE);
  }
}
