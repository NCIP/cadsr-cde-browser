package gov.nih.nci.ncicb.cadsr.util;

import java.util.List;

public interface PageIterator  {
  public Object getScrollableObject();
  public void setScrollableObject(Object vo);
  public void setScrollableObject(Object vo,long recordCount);
  
  public int getRangeSize();
  public void setRangeSize(int rangeSize);

  public long getRangeStart();
  public void setRangeStart(long rangeStart);

  public long getTotalRecordCount();
  public int getPageCount();
  public void setCurrentPage(int pageNumber);
  public int getCurrentPage();

  public Object[] scrollToNextPage();
  public Object[] scrollToPreviousPage();

  public Object[] getRowsInRange();
  public List getPageList();

  public void setPageScrollerName(String listName);
  public String getPageScrollerName();

  public int getNextPageNumber();
  public int getPreviousPageNumber();

  public boolean isFirstPage();
  public boolean isLastPage();

  public int getNextPageRecordCount();
}