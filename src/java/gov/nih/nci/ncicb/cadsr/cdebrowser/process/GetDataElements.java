package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.base.process.BasePersistingProcess;
import gov.nih.nci.ncicb.cadsr.cdebrowser.DESearchQueryBuilder;
import gov.nih.nci.ncicb.cadsr.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.dto.CDECartItemTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.TreeParametersTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.bc4j.BC4JDataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.html.HTMLPageScroller;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.PageContextValueObject;
import gov.nih.nci.ncicb.cadsr.resource.CDEBrowserPageContext;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.TreeParameters;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;
import gov.nih.nci.ncicb.cadsr.resource.handler.CDEBrowserPageContextHandler;
import gov.nih.nci.ncicb.cadsr.resource.handler.DataElementHandler;
import gov.nih.nci.ncicb.cadsr.resource.handler.ValidValueHandler;
import gov.nih.nci.ncicb.cadsr.resource.impl.CDECartImpl;
import gov.nih.nci.ncicb.cadsr.util.BC4JPageIterator;
import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.util.DTOTransformer;
import gov.nih.nci.ncicb.cadsr.util.PageIterator;
import gov.nih.nci.ncicb.cadsr.util.SortableColumnHeader;
import gov.nih.nci.ncicb.cadsr.util.TabInfoBean;
import gov.nih.nci.ncicb.cadsr.util.UserErrorMessage;
import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

import java.text.DateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.cle.persistence.HandlerFactory;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;


/**
 * @author Ram Chilukuri
 * @version: $Id: GetDataElements.java,v 1.5 2004-10-15 21:00:43 kakkodis Exp $
 */
public class GetDataElements extends BasePersistingProcess {
private static Log log = LogFactory.getLog(GetDataElements.class.getName());

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
      registerResultObject("tibSearchDE");
      registerStringParameter("P_PARAM_TYPE");
      registerStringParameter("P_IDSEQ");

      //
      registerStringResult("P_PARAM_TYPE");
      registerStringResult("P_IDSEQ");

      //
      registerStringParameter("P_CS_CSI_IDSEQ");
      registerStringResult("P_CONTEXT");
      registerStringResult("P_CONTE_IDSEQ");
      registerStringParameter("P_CONTE_IDSEQ");
      registerStringParameter("diseaseName");
      registerStringParameter("templateType");
      registerStringParameter("templateName");
      registerStringParameter("contextName");
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
      registerParameterObject(ProcessConstants.SELECT_DE);

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
    HttpSession userSession = null;
    TabInfoBean tib = null;
    String treeWhereClause = null;
    String[] searchParam = null;
    Object sessionId = getSessionId();
    DBUtil dbUtil = null;

    try {
      myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
      userSession = myRequest.getSession(false);


      log.info("GetDataElements process started successfully ");
      initialize(userSession);
      log.info(" -Performed intialization successfully");

      tib = new TabInfoBean("cdebrowser_search_tabs");

      tib.processRequest(myRequest);

      if (tib.getMainTabNum() != 0) {
        tib.setMainTabNum(0);
      }

      log.info("- Intialized TabInfoBean successfully");

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
      String conteName = getStringInfo("contextName");
      
      log.info("- Retrieved request parameters successfully");

      TreeParameters treeParam = new TreeParametersTransferObject();

      if ((paramType != null) && (paramIdSeq != null) && !("newSearch".equals(performQuery))) {
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
          treeParam.setContextName(conteName);
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

        log.info("- Created PageContext object successfully");
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

        log.info("- Created DataElementSearchBean successfully");

        dePageIterator =
          (PageIterator) getInfoObject(
            ProcessConstants.DE_SEARCH_PAGE_ITERATOR);

        //dePageIterator.setCurrentPage(0);
        if (dePageIterator == null) {
          log.info("- Unable to retrieve PageIterator from session. Creating a new PageIterator..");
          dePageIterator = new BC4JPageIterator(40);
        }
        else {
          log.info("- Retrieved PageIterator from session successfully");
        }

        dePageIterator.setCurrentPage(0);
    
      
        queryBuilder =
          new DESearchQueryBuilder(
            myRequest, paramType, paramIdSeq, treeConteIdseq);

        log.trace("- Created DESearchQueryBuilder successfully");

        queryStmt = queryBuilder.getSQLWithoutOrderBy();
        orderBy = queryBuilder.getOrderBy();

        queryParams = queryBuilder.getQueryParams();

        log.trace("- Query stmt is " + queryStmt);

        dh = (DataElementHandler) HandlerFactory.getHandler(DataElement.class);

        log.trace("Obtained DataElementHandler using HandlerFactory successfully");

        queryResults =
          dh.findDataElementsFromQueryClause(
            queryStmt, orderBy, getSessionId(), dePageIterator);

        log.trace("Query executed successfully");

        createPageScroller(dePageIterator, ProcessConstants.TOP_PAGE_SCROLLER, myRequest.getContextPath());
        createPageScroller(
          dePageIterator, ProcessConstants.BOTTOM_PAGE_SCROLLER, myRequest.getContextPath());

        log.trace("Created both page scrollers successfully");
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

        createPageScroller(dePageIterator, ProcessConstants.TOP_PAGE_SCROLLER,
        myRequest.getContextPath());
        createPageScroller(dePageIterator, ProcessConstants.BOTTOM_PAGE_SCROLLER, 
        myRequest.getContextPath());
      }

      else if (performQuery.equals("addToCart")) {
        ValidValueHandler valueHandler =
          (ValidValueHandler) HandlerFactory.getHandler(ValidValue.class);
        desb = (DataElementSearchBean) getInfoObject("desb");
        dePageIterator =
          (PageIterator) getInfoObject(
            ProcessConstants.DE_SEARCH_PAGE_ITERATOR);
        queryBuilder =
          (DESearchQueryBuilder) getInfoObject(
            ProcessConstants.DE_SEARCH_QUERY_BUILDER);
        queryResults = (List) getInfoObject(ProcessConstants.ALL_DATA_ELEMENTS);

        CDECart cart = this.findCart(userSession);
        String[] itemsList = getInfoStringArray(ProcessConstants.SELECT_DE);
        CDECartItem cdeItem = null;
        DataElement de = null;
        ValueDomain vd = null;

        for (int i = 0; i < itemsList.length; i++) {
          cdeItem = new CDECartItemTransferObject();
          de = locateDataElement(queryResults, itemsList[i]);
          vd = de.getValueDomain();
          vd.setValidValues(
            valueHandler.getValidValues(vd.getVdIdseq(), getSessionId()));
          cdeItem.setItem(de);
          cart.setDataElement(cdeItem);
        }

        myRequest.setAttribute(
          ProcessConstants.CDE_CART_ADD_SUCCESS,
          "Data Element(s) added to your CDE Cart Successfully.");
      }
      else if (performQuery.equals("newSearch")) {
        paramType = null;
        paramIdSeq = null;
        queryResults = null;
        desb =
          new DataElementSearchBean(myRequest, paramType, paramIdSeq, dbUtil);

      }
      else if (performQuery.equals("sortResults")) {
        desb = (DataElementSearchBean) getInfoObject("desb");
        
        dePageIterator =
          (PageIterator) getInfoObject(
            ProcessConstants.DE_SEARCH_PAGE_ITERATOR);

        //dePageIterator.setCurrentPage(0);
        if (dePageIterator == null) {
          log.info("- Unable to retrieve PageIterator from session. Creating a new PageIterator..");
          dePageIterator = new BC4JPageIterator(40);
        }
        else {
          log.info("- Retrieved PageIterator from session successfully");
        }

        dePageIterator.setCurrentPage(0);
      
        queryBuilder =
          (DESearchQueryBuilder) getInfoObject(
            ProcessConstants.DE_SEARCH_QUERY_BUILDER);

        //update sort by column of queryBuilder
        String sortField = getStringInfo("sortField");
        String sortOrderString = getStringInfo("sortOrder");
        int sortOrder = SortableColumnHeader.ASCENDING;
        if (getStringInfo("sortOrder") != null)
           sortOrder = Integer.valueOf(getStringInfo("sortOrder")).intValue();
        
        SortableColumnHeader sortColumnHeader = queryBuilder.getSortColumnHeader();
        
        if ( !sortField.equalsIgnoreCase(sortColumnHeader.getPrimary())||sortColumnHeader.isDefaultOrder()) {
           
           if(!sortField.equalsIgnoreCase(sortColumnHeader.getPrimary()))
           {
             String secondary = sortColumnHeader.getSecondary();
             sortColumnHeader.setSecondary(sortColumnHeader.getPrimary());
             sortColumnHeader.setPrimary(sortField);
             sortColumnHeader.setTertiary(secondary);
           }
           if(sortColumnHeader.getPrimary().equalsIgnoreCase(sortColumnHeader.getSecondary()))
           {
             sortColumnHeader.setSecondary(sortColumnHeader.getTertiary());
           }
           if (sortColumnHeader.getPrimary().equalsIgnoreCase(sortColumnHeader.getTertiary())) {
             sortColumnHeader.setTertiary(null);
           }
           else if (sortColumnHeader.getSecondary().equalsIgnoreCase(sortColumnHeader.getTertiary()))
           {
             sortColumnHeader.setTertiary(null);
           }
           sortColumnHeader.setDefaultOrder(false);
        }
        sortColumnHeader.setOrder(sortOrder);
       
        log.trace("- Updated DESearchQueryBuilder successfully");

        queryStmt = queryBuilder.getSQLWithoutOrderBy();
        orderBy = queryBuilder.getOrderBy();

        queryParams = queryBuilder.getQueryParams();

        log.trace("- Query stmt is " + queryStmt);

        dh = (DataElementHandler) HandlerFactory.getHandler(DataElement.class);

        log.trace("Obtained DataElementHandler using HandlerFactory successfully");

        queryResults =
          dh.findDataElementsFromQueryClause(
            queryStmt, orderBy, getSessionId(), dePageIterator);

        log.trace("Query executed successfully");

        createPageScroller(dePageIterator, ProcessConstants.TOP_PAGE_SCROLLER, myRequest.getContextPath());
        createPageScroller(
          dePageIterator, ProcessConstants.BOTTOM_PAGE_SCROLLER, myRequest.getContextPath());

        log.trace("Created both page scrollers successfully");
      }
      setResult("desb", desb);
      setResult(ProcessConstants.DE_SEARCH_PAGE_ITERATOR, dePageIterator);
      setResult(ProcessConstants.DE_SEARCH_QUERY_BUILDER, queryBuilder);
      setResult(ProcessConstants.ALL_DATA_ELEMENTS, queryResults);
      setResult("tibSearchDE", tib);
      setResult("performQuery", null);
      setResult("P_PARAM_TYPE", paramType);
      setResult("P_IDSEQ", paramIdSeq);

      setCondition(SUCCESS);
    }
    catch (Exception ex) {
      UserErrorMessage uem;

      try {
        log.error("Exception caught", ex);
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
        setResult("tibSearchDE", tib);
        setResult("uem", uem);
        setCondition(FAILURE);
        dbUtil.returnConnection();
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

  private void initialize(HttpSession mySession) {
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

      CDECart cart = this.findCart(mySession);
      mySession.setAttribute(CaDSRConstants.CDE_CART, cart);
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
    String location, String cPath) throws Exception {
    if (ProcessConstants.BOTTOM_PAGE_SCROLLER.equals(location)) {
      HTMLPageScroller dePageScroller =
        dePageScroller = new HTMLPageScroller(pageIterator, cPath);
      dePageScroller.setPageListName("dePages");
      dePageScroller.setExtraURLInfo("&performQuery=no");
      dePageScroller.generateHTML();
      setResult(ProcessConstants.DE_SEARCH_PAGE_SCROLLER, dePageScroller);
    }
    else if (ProcessConstants.TOP_PAGE_SCROLLER.equals(location)) {
      HTMLPageScroller dePageScroller =
        dePageScroller = new HTMLPageScroller(pageIterator, cPath);
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

  private DataElement locateDataElement(
    List results,
    String deId) {
    Iterator it = results.iterator();
    DataElement de = null;

    while (it.hasNext()) {
      de = (DataElement) it.next();

      if (de.getDeIdseq().equals(deId)) {
        return DTOTransformer.toDataElement((BC4JDataElementTransferObject) de);
      }
    }

    return de;
  }

  private CDECart findCart(HttpSession mySession) {
    CDECart cart = (CDECart) mySession.getAttribute(CaDSRConstants.CDE_CART);

    if (cart == null) {
      cart = new CDECartImpl();
    }

    return cart;
  }
}
