package gov.nih.nci.ncicb.cadsr.jsp.bean;

public class PaginationBean 
{
  private int currentPageIndex;
  private int previousPageIndex;
  private int nextPageIndex;
  private int offset;
  private int pageSize;
  private int listSize;
  private boolean initialized=false;

  
  public PaginationBean()
  {
    previousPageIndex=-1;
    currentPageIndex=1;
    nextPageIndex=currentPageIndex+1;
    offset=0;
  }

  public void next()
  {
    int tempOffset = offset+pageSize;
    
    if(tempOffset>listSize-1)
      offset=listSize-1;
    else
      offset=tempOffset;
      
    if(tempOffset+pageSize>(listSize-1))
    {
      previousPageIndex=currentPageIndex;
      currentPageIndex=nextPageIndex;
      nextPageIndex=-1;
    }
    else
    {
      previousPageIndex=currentPageIndex;
      currentPageIndex=nextPageIndex;
      nextPageIndex=nextPageIndex+1;      
    }
  }

  public void previous()
  {
    int tempOffset = offset-pageSize;
    
    if(tempOffset<=0)
      offset=0;
    else
      offset=tempOffset;    
      
    if(tempOffset-pageSize<0)
    {
      currentPageIndex=1;
      previousPageIndex=-1;
      nextPageIndex=currentPageIndex+1;
    }
    else
    {
      previousPageIndex=currentPageIndex-1;
      currentPageIndex=previousPageIndex;
      nextPageIndex=currentPageIndex;      
    }
  }  
  public void setPageIndex(int index)
  {
    if(index==1)
    {
       previousPageIndex=-1;
       currentPageIndex=1;
       nextPageIndex=currentPageIndex+1;
       offset=0;
       return;
    }    
    offset=(index-1)*this.pageSize;
    previousPageIndex=index-1;
    currentPageIndex=index;
    if(offset+pageSize>=listSize)
      nextPageIndex=-1;
    else
       nextPageIndex=index+1;     
  }
  public int getCurrentPageIndex()
  {
    return currentPageIndex;
  }

  public void setCurrentPageIndex(int newCurrentPageIndex)
  {
    currentPageIndex = newCurrentPageIndex;
  }

  public int getPreviousPageIndex()
  {
    return previousPageIndex;
  }

  public void setPreviousPageIndex(int newPreviousPageIndex)
  {
    previousPageIndex = newPreviousPageIndex;
  }

  public int getNextPageIndex()
  {
    return nextPageIndex;
  }

  public void setNextPageIndex(int newNextPageIndex)
  {
    nextPageIndex = newNextPageIndex;
  }

  public int getOffset()
  {
    return offset;
  }

  public void setOffset(int newOffset)
  {
    offset = newOffset;
  }

  public int getPageSize()
  {
    return pageSize;
  }

  public void setPageSize(int newPageSize)
  {
    pageSize = newPageSize;
  }

  public int getListSize()
  {
    return listSize;
  }

  public void setListSize(int newListSize)
  {
    listSize = newListSize;
  }

  public boolean isInitialized()
  {
    return initialized;
  }

  public void setInitialized(boolean newInitialized)
  {
    initialized = newInitialized;
  }

  public void init(int newPageSize)
  {
    this.pageSize=newPageSize;
    if(offset+listSize<newPageSize)
      this.nextPageIndex=-1;
    setInitialized(true);
  }



}