/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.common.util;

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