package gov.nih.nci.ncicb.cadsr.jsp.tag.handler;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import gov.nih.nci.ncicb.cadsr.util.SortableColumnHeader;

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
    int newOrderValue=1;
    String imageUrl = "";
    SortableColumnHeader sortableTableHeader = (SortableColumnHeader)session.getAttribute(sortableColumnHeaderBeanId);
    String targetStr = "";
    if(target!=null)
        targetStr="target=\""+target+"\" ";  
        
    if(sortableTableHeader.getOrder()==-1&&sortFieldValue.equalsIgnoreCase(sortableTableHeader.getPrimary()))
    {
      newOrderValue=1;
      imageUrl="<img src=\""+descendingImageUrl+"\" border=0 >";
    }
    else if(sortableTableHeader.getOrder()==1&&sortFieldValue.equalsIgnoreCase(sortableTableHeader.getPrimary()))
    {
      newOrderValue=-1;
      imageUrl="<img src=\""+ascendingImageUrl+"\" border=0 >";
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