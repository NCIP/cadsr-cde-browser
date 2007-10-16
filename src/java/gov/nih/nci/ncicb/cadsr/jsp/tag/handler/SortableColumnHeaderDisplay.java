package gov.nih.nci.ncicb.cadsr.jsp.tag.handler;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import gov.nih.nci.ncicb.cadsr.util.SortableColumnHeader;
import gov.nih.nci.ncicb.cadsr.CaDSRConstants;

/**
 * This Tag is used to display Columnn header which is displayed
 * as a hyper link. Example usage:
 * 		 <cde:sortableColumnHeader
 *         sortableColumnHeaderBeanId="<%=FormConstants.FORM_SEARCH_RESULT_COMPARATOR%>" 
 *         ascendingImageUrl="i/up.gif"
 *         descendingImageUrl="i/down.gif" 
 *		     actionUrl='<%="/sortFormSearchAction.do?"+NavigationConstants.METHOD_PARAM+"=sortResult"%>' 
 *	   	   columnHeader="Workflow Status" 
 *         orderParamId="sortOrder" 
 *	   	   sortFieldId="sortField"
 *	   	   sortFieldValue = "aslName"
 *		   	 target="_parent"
 *      />  
 *   
 *  Property "sortableColumnHeaderBeanId" defines the Id of a bean in session context which should
 *  should be an implementation of SortableColumnHeader interface
 *  Property "orderParamId" defines the parameter used to set sorting order in the request
 *  Property "sortFieldId" defines the parameter used to set field that need to sorted order in the request
 *  Property "sortFieldValue" defines the property ofthe Object in the List that need to be sorted 
 */
public class SortableColumnHeaderDisplay extends TagSupport
{
  
  private String sortableColumnHeaderBeanId;
  private String ascendingImageUrl;
  private String descendingImageUrl;
  private String actionUrl;
  private String columnHeader;
  private String orderParamId;
  private String sortFieldId;
  private String sortFieldValue;
  private String target;
  public SortableColumnHeaderDisplay()
  {
  }
  
  public int doStartTag() throws javax.servlet.jsp.JspException {

    HttpServletRequest req = ( HttpServletRequest )pageContext.getRequest();
    JspWriter out = pageContext.getOut();
    HttpSession session = req.getSession();
   // if(anchor!=null)
     //   session.setAttribute(CaDSRConstants.ANCHOR,anchor);    
    int newOrderValue=1;
    String imageUrl = "";
    SortableColumnHeader sortableTableHeader = (SortableColumnHeader)session.getAttribute(sortableColumnHeaderBeanId);
    String targetStr = "";
    if(target!=null)
        targetStr="target=\""+target+"\" ";  
        
    if(sortableTableHeader.getOrder()==-1&&sortFieldValue.equalsIgnoreCase(sortableTableHeader.getPrimary()))
    {
      newOrderValue=1;
      if(descendingImageUrl!=null&&!descendingImageUrl.equalsIgnoreCase(""))
        imageUrl="&nbsp;<img src=\""+descendingImageUrl+"\" border=0 >";
    }
    else if(sortableTableHeader.getOrder()==1&&sortFieldValue.equalsIgnoreCase(sortableTableHeader.getPrimary()))
    {
      newOrderValue=-1;
      if(ascendingImageUrl!=null&&!ascendingImageUrl.equalsIgnoreCase(""))
        imageUrl="&nbsp;<img src=\""+ascendingImageUrl+"\" border=0 >";
    }
    StringBuffer sb = new StringBuffer(req.getContextPath()+actionUrl);
    if(actionUrl.indexOf("=")!=-1)
    {
        if(!actionUrl.endsWith("&"))
          sb.append("&");  
      
    }
    sb.append(orderParamId+"="+newOrderValue);
    sb.append("&"+sortFieldId+"="+sortFieldValue);
    try{   
       out.print("<a STYLE=\"text-decoration: none\" href=\""+sb.toString()+"\" "+targetStr+"/> "+columnHeader+imageUrl+"</a>");
     } catch( IOException ioe ) {
          throw new JspException( "I/O Error : " + ioe.getMessage() );
      }//end try/catch    
    
      return Tag.SKIP_BODY;

    }//end doStartTag()  


  public String getascendingImageUrl()
  {
    return ascendingImageUrl;
  }

  public void setascendingImageUrl(String ascendingImageUrl)
  {
    this.ascendingImageUrl = ascendingImageUrl;
  }

  public String getdescendingImageUrl()
  {
    return descendingImageUrl;
  }

  public void setDescendingImageUrl(String descendingImageUrl)
  {
    this.descendingImageUrl = descendingImageUrl;
  }

  public String getactionUrl()
  {
    return actionUrl;
  }

  public void setActionUrl(String actionUrl)
  {
    this.actionUrl = actionUrl;
  }

  public String getColumnHeader()
  {
    return columnHeader;
  }

  public void setColumnHeader(String columnHeader)
  {
    this.columnHeader = columnHeader;
  }


  public void setOrderParamId(String orderParamId)
  {
    this.orderParamId = orderParamId;
  }


  public String getOrderParamId()
  {
    return orderParamId;
  }


  public void setSortFieldId(String sortFieldId)
  {
    this.sortFieldId = sortFieldId;
  }


  public String getSortFieldId()
  {
    return sortFieldId;
  }


  public void setSortFieldValue(String sortFieldValue)
  {
    this.sortFieldValue = sortFieldValue;
  }


  public String getSortFieldValue()
  {
    return sortFieldValue;
  }


  public void setSortableColumnHeaderBeanId(String sortableColumnHeaderBeanId)
  {
    this.sortableColumnHeaderBeanId = sortableColumnHeaderBeanId;
  }


  public String getSortableColumnHeaderBeanId()
  {
    return sortableColumnHeaderBeanId;
  }


  public void setTarget(String target)
  {
    this.target = target;
  }


  public String getTarget()
  {
    return target;
  }

}