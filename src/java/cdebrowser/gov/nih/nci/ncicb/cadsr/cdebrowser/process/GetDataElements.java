package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.base.process.*;
import gov.nih.nci.ncicb.cadsr.cdebrowser.DESearchQueryBuilder;
import gov.nih.nci.ncicb.cadsr.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.database.*;
import gov.nih.nci.ncicb.cadsr.dto.TreeParametersTransferObject;
import gov.nih.nci.ncicb.cadsr.html.HTMLPageScroller;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.PageContextValueObject;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.handler.*;
import gov.nih.nci.ncicb.cadsr.resource.CDEBrowserPageContext;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.TreeParameters;
import gov.nih.nci.ncicb.cadsr.resource.handler.CDEBrowserPageContextHandler;
import gov.nih.nci.ncicb.cadsr.resource.handler.DataElementHandler;
import gov.nih.nci.ncicb.cadsr.util.*;

import oracle.cle.persistence.HandlerFactory;

import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.Service;

import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

import java.text.DateFormat;

import java.util.*;

import javax.servlet.http.*;


/**
 * @author Ram Chilukuri
 */
public class GetDataElements extends BasePersistingProcess {
  String dsName = null;

  public GetDataElements() {
    this(null);

    DEBUG = false;
  }

  public GetDataElements(Service aService) {
    super(aService);

    DEBUG = false;
  }

  /**
   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
   * this process during construction.
   */
  public void registerInfo() {
    try {
      registerResultObject("desb");
      registerResultObject("tib");
      registerStringParameter("P_PARAM_TYPE");
      registerStringParameter("P_IDSEQ");
      registerStringParameter("P_CS_CSI_IDSEQ");
      registerStringResult("P_CONTEXT");
      registerStringResult("P_CONTE_IDSEQ");
      registerStringParameter("P_CONTE_IDSEQ");
      registerStringParameter("diseaseName");
      registerStringParameter("templateType");
      registerStringParameter("templateName");
      registerStringParameter("csName");
      registerStringResult("P_PROTOCOL");
      registerParameterObject("SEARCH");
      registerResultObject(ProcessConstants.PAGE_CONTEXT);
      registerResultObject(ProcessConstants.ALL_DATA_ELEMENTS);
      registerStringParameter("performQuery");
      registerStringResult("performQuery");
      registerParameterObject("desb");
      registerStringParameter("NOT_FIRST_DISPLAY");
      registerStringParameter("SBREXT_DSN");
      registerStringParameter("SBR_DSN");
      registerStringParameter("XML_DOWNLOAD_DIR");
      registerStringParameter("TREE_URL");
      registerStringResult("SBREXT_DSN");
      registerStringResult("SBR_DSN");
      registerStringResult("XML_DOWNLOAD_DIR");
      registerStringResult("TREE_URL");
      registerStringParameter("INITIALIZED");
      registerStringResult("INITIALIZED");
      registerParameterObject("dbUtil");
      registerResultObject("dbUtil");
      registerStringParameter("XML_PAGINATION_FLAG");
      registerStringResult("XML_PAGINATION_FLAG");
      registerStringParameter("XML_FILE_MAX_RECORDS");
      registerStringResult("XML_FILE_MAX_RECORDS");
      registerParameterObject(ProcessConstants.DE_SEARCH_PAGE_ITERATOR);
      registerResultObject(ProcessConstants.DE_SEARCH_PAGE_ITERATOR);
      registerStringParameter(ProcessConstants.DE_SEARCH_RESULTS_PAGE_NUMBER);
      registerStringResult(ProcessConstants.DE_SEARCH_RESULTS_PAGE_NUMBER);
      registerParameterObject(ProcessConstants.DE_SEARCH_QUERY_BUILDER);
      registerResultObject(ProcessConstants.DE_SEARCH_QUERY_BUILDER);
      registerResultObject(ProcessConstants.DE_SEARCH_PAGE_SCROLLER);
      registerResultObject(ProcessConstants.DE_SEARCH_TOP_PAGE_SCROLLER);
      registerResultObject("uem");
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
    HttpServletRequest myRequest = null;
    TabInfoBean tib = null;
    String treeWhereClause = null;
    String[] searchParam = null;
    Object sessionId = getSessionId();
    DBUtil dbUtil = null;

    try {
      System.out.println(
        getCurrentTimestamp() +
        "- GetDataElements process started successfully ");
      initialize();

      System.out.println(
        getCurrentTimestamp() + " -Performed intialization successfully");

      tib = new TabInfoBean("cdebrowser_search_tabs");
      myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);

      if (tib.getMainTabNum() != 0) {
        tib.setMainTabNum(0);
      }

      System.out.println(
        getCurrentTimestamp() + "- Intialized TabInfoBean successfully");

      String paramType = getStringInfo("P_PARAM_TYPE");
      String paramIdSeq = getStringInfo("P_IDSEQ");
      String paramCsCsiIdSeq = getStringInfo("P_CS_CSI_IDSEQ");
      String treeConteIdseq = getStringInfo("P_CONTE_IDSEQ");
      searchParam = getInfoStringArray("SEARCH");

      String performQuery = getStringInfo("performQuery");
      String diseaseName = getStringInfo("diseaseName");
      String templateType = getStringInfo("templateType");
      String csName = getStringInfo("csName");
      String templateName = getStringInfo("templateName");

      System.out.println(
        getCurrentTimestamp() + "- Retrieved request parameters successfully");

      TreeParameters treeParam = new TreeParametersTransferObject();

      if ((paramType != null) && (paramIdSeq != null)) {
        CDEBrowserPageContextHandler ph =
          (CDEBrowserPageContextHandler) HandlerFactory.getHandler(
            CDEBrowserPageContext.class);
        CDEBrowserPageContext pc = null;

        if (paramType.equals("CORE") || paramType.equals("NON-CORE")) {
          treeParam.setNodeType(paramType);
          treeParam.setNodeIdseq(paramIdSeq);
          treeParam.setClassSchemeItemName(diseaseName);
          treeParam.setClassSchemeName(csName);
          treeParam.setConteIdseq(treeConteIdseq);

          /*pc = (CDEBrowserPageContext)ph
             .findPageContext(paramType, paramCsCsiIdSeq, sessionId);*/
          pc = new PageContextValueObject(treeParam);
        }
        else if (paramType.equals("TEMPLATE")) {
          treeParam.setNodeType(paramType);
          treeParam.setNodeIdseq(paramIdSeq);
          treeParam.setClassSchemeName(csName);
          treeParam.setClassSchemeItemName(diseaseName);
          treeParam.setTemplateGrpName(templateType);
          treeParam.setCDETemplateName(templateName);
          treeParam.setConteIdseq(treeConteIdseq);

          /*pc = (CDEBrowserPageContext)ph
             .findPageContext(paramType, paramCsCsiIdSeq, sessionId);*/
          pc = new PageContextValueObject(treeParam);
        }
        else {
          pc =
            (CDEBrowserPageContext) ph.findPageContext(
              paramType, paramIdSeq, sessionId);
        }

        setResult(ProcessConstants.PAGE_CONTEXT, pc);
        setResult("P_CONTEXT", pc.getContextName());
        setResult("P_CONTE_IDSEQ", pc.getConteIdseq());

        System.out.println(
          getCurrentTimestamp() + "- Created PageContext object successfully");
      }

      DataElementSearchBean desb = null;
      List queryResults = null;
      String pageNum;
      PageIterator dePageIterator = null;
      DESearchQueryBuilder queryBuilder = null;
      String queryStmt;
      String orderBy;
      DataElementHandler dh = null;
      Object[] queryParams = null;

      dbUtil = getDBUtil();

      if (performQuery == null) {
        desb =
          new DataElementSearchBean(myRequest, paramType, paramIdSeq, dbUtil);
        dePageIterator = new BC4JPageIterator(40);
      }
      else if (performQuery.equals("yes")) {
        desb =
          new DataElementSearchBean(myRequest, paramType, paramIdSeq, dbUtil);

        System.out.println(
          getCurrentTimestamp() +
          "- Created DataElementSearchBean successfully");

        dePageIterator =
          (PageIterator) getInfoObject(
            ProcessConstants.DE_SEARCH_PAGE_ITERATOR);
        //dePageIterator.setCurrentPage(0);

        if (dePageIterator == null) {
          System.out.println(
          getCurrentTimestamp() +
          "- Unable to retrieve PageIterator from session. Creating a new PageIterator..");
          dePageIterator = new BC4JPageIterator(40);
        }
        else {
          System.out.println(
          getCurrentTimestamp() +
          "- Retrieved PageIterator from session successfully");
        }
        dePageIterator.setCurrentPage(0);
        queryBuilder =
          new DESearchQueryBuilder(
            myRequest, paramType, paramIdSeq, treeConteIdseq);

        System.out.println(
          getCurrentTimestamp() +
          "- Created DESearchQueryBuilder successfully");

        queryStmt = queryBuilder.getSQLWithoutOrderBy();
        orderBy = queryBuilder.getOrderBy();

        queryParams = queryBuilder.getQueryParams();

        System.out.println(
          getCurrentTimestamp() + "- Query stmt is " + queryStmt);

        dh = (DataElementHandler) HandlerFactory.getHandler(DataElement.class);

        System.out.println(
          getCurrentTimestamp() +
          "- Obtained DataElementHandler using HandlerFactory successfully");

        queryResults =
          dh.findDataElementsFromQueryClause(
            queryStmt, orderBy, getSessionId(), dePageIterator);

        System.out.println(
          getCurrentTimestamp() + "- Query executed successfully");

        createPageScroller(dePageIterator, ProcessConstants.TOP_PAGE_SCROLLER);
        createPageScroller(
          dePageIterator, ProcessConstants.BOTTOM_PAGE_SCROLLER);

        System.out.println(
          getCurrentTimestamp() + "- Created both page scrollers successfully");
      }
      else if (performQuery.equals("no")) {
        desb = (DataElementSearchBean) getInfoObject("desb");

        int pageNumber =
          Integer.parseInt(
            getStringInfo(ProcessConstants.DE_SEARCH_RESULTS_PAGE_NUMBER));
        dePageIterator =
          (PageIterator) getInfoObject(
            ProcessConstants.DE_SEARCH_PAGE_ITERATOR);
        dePageIterator.setCurrentPage(pageNumber);
        queryBuilder =
          (DESearchQueryBuilder) getInfoObject(
            ProcessConstants.DE_SEARCH_QUERY_BUILDER);

        queryStmt = queryBuilder.getSQLWithoutOrderBy();
        orderBy = queryBuilder.getOrderBy();

        queryParams = queryBuilder.getQueryParams();
        dh = (DataElementHandler) HandlerFactory.getHandler(DataElement.class);

        queryResults =
          dh.findDataElementsFromQueryClause(
            queryStmt, orderBy, getSessionId(), dePageIterator);

        createPageScroller(dePageIterator, ProcessConstants.TOP_PAGE_SCROLLER);
        createPageScroller(
          dePageIterator, ProcessConstants.BOTTOM_PAGE_SCROLLER);
      }

      setResult("desb", desb);
      setResult(ProcessConstants.DE_SEARCH_PAGE_ITERATOR, dePageIterator);
      setResult(ProcessConstants.DE_SEARCH_QUERY_BUILDER, queryBuilder);
      setResult(ProcessConstants.ALL_DATA_ELEMENTS, queryResults);
      setResult("tib", tib);
      setResult("performQuery", null);

      setCondition(SUCCESS);
    }
    catch (Exception ex) {
      UserErrorMessage uem;

      try {
        System.out.println(getCurrentTimestamp() + "- Exception caught");
        tib = new TabInfoBean("cdebrowser_error_tabs");
        myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
        tib.processRequest(myRequest);

        if (tib.getMainTabNum() != 0) {
          tib.setMainTabNum(0);
        }

        uem = new UserErrorMessage();
        uem.setMsgOverview("Unexpected Application Error");
        uem.setMsgText(
          "An unexpected application error has occurred. Please re-try your search");
        uem.setMsgTechnical(
          "<b>System administrator:</b> Here is the stack " +
          "trace from the Exception.<BR><BR>" + ex.toString() + "<BR><BR>");
        setResult("tib", tib);
        setResult("uem", uem);
        setCondition(FAILURE);
        dbUtil.returnConnection();
        ex.printStackTrace();
      }
      catch (TransitionConditionException tce) {
        reportException(tce, DEBUG);
      }
      catch (Exception e) {
        reportException(e, DEBUG);
      }

      reportException(ex, DEBUG);
      throw ex;
    }
    finally {
      if (dbUtil != null) {
        dbUtil.returnConnection();
      }
    }
  }

  protected TransitionCondition getPersistSuccessCondition() {
    return getCondition(SUCCESS);
  }

  protected TransitionCondition getPersistFailureCondition() {
    return getCondition(FAILURE);
  }

  private void initialize() {
    if (getStringInfo("INITIALIZED") == null) {
      CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
      setResult("SBREXT_DSN", params.getSbrextDSN());
      dsName = params.getSbrDSN();
      setResult("SBR_DSN", params.getSbrDSN());
      setResult("XML_DOWNLOAD_DIR", params.getXMLDownloadDir());
      setResult("XML_PAGINATION_FLAG", params.getXMLPaginationFlag());
      setResult("XML_FILE_MAX_RECORDS", params.getXMLFileMaxRecords());
      setResult("TREE_URL", params.getTreeURL());
      setResult("INITIALIZED", "yes");
    }
    else {
      setResult("SBREXT_DSN", getStringInfo("SBREXT_DSN"));
      setResult("SBR_DSN", getStringInfo("SBR_DSN"));
      dsName = getStringInfo("SBR_DSN");
      setResult("XML_DOWNLOAD_DIR", getStringInfo("XML_DOWNLOAD_DIR"));
      setResult("XML_PAGINATION_FLAG", getStringInfo("XML_PAGINATION_FLAG"));
      setResult("XML_FILE_MAX_RECORDS", getStringInfo("XML_FILE_MAX_RECORDS"));
      setResult("TREE_URL", getStringInfo("TREE_URL"));
      setResult("INITIALIZED", "yes");
    }
  }

  private DBUtil getDBUtil() throws Exception {
    DBUtil dbUtil = null;

    if ((DBUtil) getInfoObject("dbUtil") == null) {
      try {
        dbUtil = new DBUtil();
        dbUtil.getConnectionFromContainer(dsName);
      }
      catch (Exception ex) {
        ex.printStackTrace();
        throw ex;
      }
    }
    else {
      dbUtil = (DBUtil) getInfoObject("dbUtil");
      dbUtil.getConnectionFromContainer(dsName);
    }

    setResult("dbUtil", dbUtil);

    return dbUtil;
  }

  private void createPageScroller(
    PageIterator pageIterator,
    String location) throws Exception {
    if (ProcessConstants.BOTTOM_PAGE_SCROLLER.equals(location)) {
      HTMLPageScroller dePageScroller =
        dePageScroller = new HTMLPageScroller(pageIterator);
      dePageScroller.setPageListName("dePages");
      dePageScroller.setExtraURLInfo("&performQuery=no");
      dePageScroller.generateHTML();
      setResult(ProcessConstants.DE_SEARCH_PAGE_SCROLLER, dePageScroller);
    }
    else if (ProcessConstants.TOP_PAGE_SCROLLER.equals(location)) {
      HTMLPageScroller dePageScroller =
        dePageScroller = new HTMLPageScroller(pageIterator);
      dePageScroller.setPageListName("dePagesTop");
      dePageScroller.setExtraURLInfo("&performQuery=no");
      dePageScroller.setOnChangeJSFunctionName("topListChanged");
      dePageScroller.generateHTML();
      setResult(ProcessConstants.DE_SEARCH_TOP_PAGE_SCROLLER, dePageScroller);
    }
  }

  private String getCurrentTimestamp() {
    Date timestamp = new Date();
    String ts = DateFormat.getDateTimeInstance().format(timestamp);

    return ts;
  }
}
