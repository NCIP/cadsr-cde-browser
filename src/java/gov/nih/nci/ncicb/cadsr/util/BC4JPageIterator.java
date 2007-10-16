package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.common.ampool.ApplicationPool;
import oracle.jbo.common.ampool.SessionCookie;

public class BC4JPageIterator implements PageIterator  {
  private static Log log = LogFactory.getLog(BC4JPageIterator.class.getName());
  protected int   rangeSize = 10;
  protected long  rangeStart = 0;
  protected long  leftOver;
  protected long  totalRecordCount;
  protected int   pageCount;
  protected int   currentPage = 0;
  protected int   iterMode;
  protected List pagesList = new ArrayList (11);
  private ViewObject myViewObject = null;
  private boolean performQuery = true;
  private boolean isInitialized = false;
  private String scrollerName;
  private boolean firstPage = false;
  private boolean lastPage = false;
  private int nextPageRecordCount;
  private boolean rowCountProvided = false;
  public BC4JPageIterator(){
 
  }

  public BC4JPageIterator(int pageSize){
    this.rangeSize = pageSize;
  }

  public BC4JPageIterator(ViewObject vo, int pageSize){
    this.myViewObject = vo;
    this.rangeSize = pageSize;
  }
  
  public void initialize() {
    if (!myViewObject.isExecuted()) myViewObject.executeQuery();
    
    if (isInitialized) {
      for (Iterator it = pagesList.iterator(); it.hasNext();) {
        it.next();
        it.remove();
      }
    }
    if (!rowCountProvided) {
      totalRecordCount = myViewObject.getEstimatedRowCount();
    }
    else {
      //log.debug("Row count provided not computed by Page Iterator");
    }
    computePageCount();
    populatePagesList();
    isInitialized = true;
    
  }

  public Object[] getRowsInRange() {
    Row[] activeRows = null;
    if (rangeStart < 0) rangeStart = 0;
    if (myViewObject != null) {
      myViewObject.setIterMode(RowIterator.ITER_MODE_LAST_PAGE_PARTIAL);
      myViewObject.setRangeSize(rangeSize);
      myViewObject.setRangeStart((int)rangeStart);
      //log.trace("Range start is " + rangeStart);
      
      activeRows = myViewObject.getAllRowsInRange();
      //log.trace("Range record count is "+myViewObject.getRowCountInRange());
      lastPage = myViewObject.isRangeAtBottom();
      firstPage = myViewObject.isRangeAtTop();
      computeNextPageRecordCount();
    }
    return activeRows;
  }
  public void getAllRows() {
    
  }
  public int getRangeSize() {
    return rangeSize;
  }
  public void setRangeSize(int rangeSize){
    this.rangeSize = rangeSize;
    computePageCount();
    populatePagesList();
  }

  private void computePageCount() {
    pageCount = (int) (totalRecordCount/rangeSize);
    if (pageCount * rangeSize < totalRecordCount){
      pageCount++;
    }
  }
  public long getRangeStart() {
     return rangeStart;
  }
  public void setRangeStart(long rangeStart) {
    this.rangeStart = rangeStart;
    
  }

  public long getTotalRecordCount() {
    return totalRecordCount;
  }
  public Object getScrollableObject() {
    return myViewObject;
  }
  public void setScrollableObject(Object vo) {
    myViewObject = (ViewObject)vo;
    initialize();
  }

  public void setScrollableObject(Object vo, long recordCount) {
    myViewObject = (ViewObject)vo;
    totalRecordCount = recordCount;
    rowCountProvided = true;
    initialize();
  }
  
  public int getPageCount() {
    return pageCount;
  }
  public int getCurrentPage() {
    return currentPage;
  }
  public void setCurrentPage(int pageNumber) {
    currentPage = pageNumber;
    rangeStart = currentPage*rangeSize;
  }
  public Object[] scrollToNextPage() {
    rangeStart = rangeStart + rangeSize;
    return this.getRowsInRange();
  }
  public Object[] scrollToPreviousPage(){
    rangeStart = rangeStart - rangeSize;
    return this.getRowsInRange();
  }
  public List getPageList() {
    return pagesList;
  }
  protected void populatePagesList() {
    if (pageCount != 0 && totalRecordCount != 0 ){
      
      long beginRecordNumber;
      long endRecordNumber;
      for (int i=0; i <pageCount; i++) {
        String pageEntry = new String();
        beginRecordNumber = i*rangeSize+1;
        if (i+1 >= pageCount) endRecordNumber = totalRecordCount;
        else endRecordNumber = (i+1)*rangeSize;
        pageEntry = beginRecordNumber + " - " + endRecordNumber + " of "+totalRecordCount;
        pagesList.add(pageEntry);
      }
    }
  }
  public void setPerformQuery(boolean query) {
    performQuery = query;
  }
  public boolean isFirstPage(){
    //return myViewObject.isRangeAtTop();
    return firstPage;
  }
  public boolean isLastPage() {
    //return myViewObject.isRangeAtBottom();
    return lastPage;
  }

  public void setPageScrollerName(String listName) {
    scrollerName = listName;
  }
  public String getPageScrollerName() {
    return scrollerName;
  }

  public int getNextPageNumber() {
    int nextPageNumber = currentPage;
    if (!this.isLastPage()) {
      nextPageNumber = currentPage + 1;
      log.trace("Next page number is "+nextPageNumber);
    }
    return nextPageNumber;
  }
  public int getPreviousPageNumber() {
    int prevPageNumber = currentPage;
    if (!this.isFirstPage()) {
      prevPageNumber = currentPage - 1;
      log.trace("Previous page number is "+prevPageNumber);
    }
    return prevPageNumber;
  }

  private void computeNextPageRecordCount() {
    int recordCount = (int)totalRecordCount - ((currentPage + 1)*rangeSize);
    if (recordCount >= rangeSize ) nextPageRecordCount =  rangeSize;
    else nextPageRecordCount = recordCount;
    
  }

  public int getNextPageRecordCount() {
    return nextPageRecordCount;
  }

  
  public static void main(String[] args) {
    ApplicationPool amPool;
    ApplicationModule am1;
    ApplicationModule am2;
    
    try {
      BC4JPageIterator bpi = new BC4JPageIterator(10);
      amPool = BC4JHelper
          .getApplicationPool("myPool","gov.nih.nci.ncicb.cadsr.persistence.bc4j","CDEBrowserBc4jModuleLocal",null);
      log.trace("Application pool name "+amPool.getPoolName());
      SessionCookie cookie1 = BC4JHelper
                    .createSessionCookie(amPool,"100","Test App",null);
      SessionCookie cookie2 = BC4JHelper
                    .createSessionCookie(amPool,"101","Test App",null);
      log.debug("Application pool size before checkout "+amPool.getAvailableInstanceCount());
      am1 = BC4JHelper
          .getApplicationModuleFromPool(cookie1);
      String sqlStmt = "SELECT preferred_name AS PreferredName " +
                       "FROM   data_elements "+
                       "WHERE  upper(preferred_name) like upper('%agent%') ";
      //bpi.createViewObject(sqlStmt,am1 );
      //ViewObject vo = am1.findViewObject("DataElementsView");
      ViewObject vo = BC4JHelper.createRuntimeViewObject(sqlStmt,am1);
      //vo.setWhereClause(" upper(preferred_name) like upper('%agent%') ");
      vo.setIterMode(RowIterator.ITER_MODE_LAST_PAGE_PARTIAL);
      vo.setRangeSize(10);
      vo.setRangeStart(110);
      log.debug("Total Record count "+vo.getEstimatedRowCount());
      
      log.debug("Record count in range "+vo.getRowCountInRange());
      
      BC4JHelper.returnApplicationModuleToPool(cookie1, false);
     
      log.debug("Application pool size after checkin "+amPool.getAvailableInstanceCount());

      /*am1 = BC4JHelper
          .getApplicationModuleFromPool(cookie1);
      vo = am1.findViewObject("DataElementsView");
      log.trace("Record count after second check out " + vo.getRowCount());
      log.trace("Where clause after second check out "+vo.getWhereClause());
      BC4JHelper.returnApplicationModuleToPool(cookie1,false);*/
      

      
    } 
    catch (Exception ex) {
      log.error("Exception occurred", ex);
    } 
    finally {
    }
  }
}