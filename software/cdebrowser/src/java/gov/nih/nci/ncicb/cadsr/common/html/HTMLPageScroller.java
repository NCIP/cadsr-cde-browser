package gov.nih.nci.ncicb.cadsr.common.html;

import gov.nih.nci.ncicb.cadsr.common.util.PageIterator;
import gov.nih.nci.ncicb.cadsr.common.util.GenericPopListBean;

public class HTMLPageScroller  {

  private PageIterator pageIterator = null;
  private String pagesListHTML = "";
  private String prevPageIconHTML = "";
  private String nextPageIconHTML = "";
  private String scrollerHTML = "";
  private String extraURLInfo = "";
  private String listName = "";
  private String onChangeJSFunctionName = "listChanged";
  private String contextPath = "";

  public HTMLPageScroller(PageIterator pgIterator, String cPath)throws Exception {
    pageIterator = pgIterator;
    contextPath = cPath;
    
  }

  public void generateHTML() throws Exception {
    StringBuffer tableBuf = new StringBuffer("");
    if (pageIterator.getPageList().size() != 0) {
      int prevblockSize = pageIterator.getRangeSize();
      int nextblockSize = pageIterator.getNextPageRecordCount();
      StringBuffer pagesBuffer =  
      GenericPopListBean.buildList(pageIterator.getPageList()
                                  ,listName
                                  ,new Integer(pageIterator.getCurrentPage()).toString()
                                  ,false
                                  ,0
                                  ,false
                                  ,false
                                  ,false
                                  ,false
                                  ,"LOVField"
                                  ,true
                                  //,"listChanged"
                                  ,onChangeJSFunctionName
                                  ,null);
      pagesListHTML = pagesBuffer.toString();
      if (pageIterator.isFirstPage()){
        prevPageIconHTML = "<img src=\"" + contextPath + "/i/prev_off.gif\" border=\"0\" "+
                           "alt=\"Previous Page\">";
      }
      else {
        prevPageIconHTML = "<a href=\"javascript:goPage('"+
                           pageIterator.getPreviousPageNumber()
                           +"','"+extraURLInfo +
                           "')\"><img src=\""+ contextPath +"/i/prev_on.gif\" border=\"0\" "+
                           "alt=\"Previous Page\">&nbsp;Previous "+prevblockSize+"</a>";
      }

      if (pageIterator.isLastPage()){
        nextPageIconHTML = "<img src=\""+ contextPath +"/i/next_off.gif\" border=\"0\" "+
                           "alt=\"Next Page\">";
      }
      else {
        nextPageIconHTML = "<a href=\"javascript:goPage('"+
                           pageIterator.getNextPageNumber()
                           +"','"+extraURLInfo +
                           "')\">Next "+nextblockSize+"&nbsp;<img src=\""+ contextPath +"/i/next_on.gif\" border=\"0\" "+
                           "alt=\"Next Page\"></a>";
      }
      /*tableBuf.append("<table><tr>");
      tableBuf.append("<td valign=\"center\">"+prevPageIconHTML+"</td>");
      tableBuf.append("<td>"+pagesListHTML+"</td>");
      tableBuf.append("<td valign=\"center\">"+nextPageIconHTML+"</td>");
      tableBuf.append("</tr></table>");
      scrollerHTML = tableBuf.toString();*/

      scrollerHTML = prevPageIconHTML + "&nbsp;" + pagesListHTML + "&nbsp;" +
                     nextPageIconHTML;
      
    }
    else {
      scrollerHTML = "";
    }
  }

  public String getScrollerHTML() {
    return scrollerHTML;
  }

  public void setExtraURLInfo(String s) {
    extraURLInfo = s;
  }

  public void setPageListName(String dropDownName){
    listName = dropDownName;
  }

  public void setOnChangeJSFunctionName (String functionName) {
    onChangeJSFunctionName = functionName;
  }

  public String getOnChangeJSFunctionName() {
    return onChangeJSFunctionName;
  }


   public void setContextPath(String contextPath) {
      this.contextPath = contextPath;
   }
   public String getTotalRecordCount()
   {
     String sizeStr = "";
     if(pageIterator!=null)
     {
     long size = pageIterator.getTotalRecordCount();
      sizeStr = new Long(size).toString();
     }
     return sizeStr;
   }
}