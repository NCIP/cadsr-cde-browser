package gov.nih.nci.ncicb.cadsr.util;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JDBCPageIterator implements PageIterator  {
  protected ResultSet myResultSet;
  protected int   rangeSize = 10;
  protected long  rangeStart = 0;
  protected long  leftOver;
  protected long  totalRecordCount;
  protected int   pageCount;
  protected int   currentPage = 0;
  protected int   iterMode;
  protected List pagesList = new ArrayList (11);

  public JDBCPageIterator() {
  }

  public JDBCPageIterator(ResultSet rs) {
    myResultSet = rs;
  }

  public Object getScrollableObject() {
    return myResultSet;
  }

  public void setScrollableObject(Object vo) {
  }

  public void setScrollableObject(Object vo,long recordCount) {
    
  }

  public int getRangeSize() {
    return 0;
  }

  public void setRangeSize(int rangeSize) {
  }

  public long getRangeStart() {
    return 0;
  }

  public void setRangeStart(long rangeStart) {
  }

  public long getTotalRecordCount() {
    return 0;
  }

  public int getPageCount() {
    return 0;
  }

  public void setCurrentPage(int pageNumber) {
  }

  public int getCurrentPage() {
    return 0;
  }

  public Object[] scrollToNextPage() {
    return null;
  }

  public Object[] scrollToPreviousPage() {
    return null;
  }

  public Object[] getRowsInRange() {
    return null;
  }

  public List getPageList() {
    return null;
  }

  public void setPageScrollerName(String listName) {
    
  }
  public String getPageScrollerName() {
    return null;
  }

  public int getNextPageNumber(){
    return 0;
  }
  public int getPreviousPageNumber(){
    return 0;
  }

  public boolean isFirstPage() {
    return false;
  }
  public boolean isLastPage() {
    return false;
  }

  public int getNextPageRecordCount() {
    return 0;
  }
}