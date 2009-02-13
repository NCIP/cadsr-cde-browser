package gov.nih.nci.ncicb.cadsr.common.jsp.tag.handler;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.util.SortableColumnHeader;
import gov.nih.nci.ncicb.cadsr.common.util.StringUtils;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class SorableColumnHeaderBreadcrumb extends TagSupport implements CaDSRConstants
{

  private String sortableColumnHeaderBeanId;
  private String prefix="";
  private String separator ="";
  private String showDefault="N";
  private String defaultText="Default";
  private String labelMapping="";
  private String[] labelMappingArr=null;
  private String ascendingText="";
  private String descendingText="";

  public SorableColumnHeaderBreadcrumb()
  {
  }
  
  public int doStartTag() throws javax.servlet.jsp.JspException {

    HttpServletRequest req = ( HttpServletRequest )pageContext.getRequest();
    JspWriter out = pageContext.getOut();
    HttpSession session = req.getSession();
    
    SortableColumnHeader sortableTableHeader = (SortableColumnHeader)session.getAttribute(sortableColumnHeaderBeanId);

    StringBuffer sb = new StringBuffer();
      if (sortableTableHeader!=null)
      {
        if(prefix!=null)
           sb.append(prefix);
        if(showDefault.equalsIgnoreCase("Y"))
        {
          if(sortableTableHeader.isDefaultOrder())
          {
            sb.append(defaultText);
            pageContext.setAttribute(SortableColumnHeader.DEFAULT_SORT_ORDER,YES);
          }
          else
          {
            pageContext.removeAttribute(SortableColumnHeader.DEFAULT_SORT_ORDER);
          }
        }   
        sb.append(getLabel(sortableTableHeader.getPrimary()));
        if(sortableTableHeader.getSecondary()!=null&&!sortableTableHeader.getSecondary().equalsIgnoreCase(""))
        {
          sb.append(separator);
          sb.append(getLabel(sortableTableHeader.getSecondary()));
        }
        if(sortableTableHeader.getTertiary()!=null&&!sortableTableHeader.getTertiary().equalsIgnoreCase(""))
        {
          sb.append(separator);
          sb.append(getLabel(sortableTableHeader.getTertiary()));      
        }
        if(sortableTableHeader.getOrder()==sortableTableHeader.ASCENDING)
        {
          sb.append(this.ascendingText);
        }
        else
        {
          sb.append(this.descendingText);
        }
        try{   
           out.print(sb.toString());
     
         } catch( IOException ioe ) {
              throw new JspException( "I/O Error : " + ioe.getMessage() );
        }//end try/catch    
      }
    
      return Tag.SKIP_BODY;

    }//end doStartTag()    

  public String getPrefix()
  {
    return prefix;
  }

  public void setPrefix(String prefix)
  {
    this.prefix = prefix;
  }

  public String getSeparator()
  {
    return separator;
  }

  public void setSeparator(String separator)
  {
    this.separator = separator;
  }

  public String getShowDefault()
  {
    return showDefault;
  }

  public void setShowDefault(String showDefault)
  {
    this.showDefault = showDefault;
  }

  public String getSortableColumnHeaderBeanId()
  {
    return sortableColumnHeaderBeanId;
  }

  public void setSortableColumnHeaderBeanId(String sortableColumnHeaderBeanId)
  {
    this.sortableColumnHeaderBeanId = sortableColumnHeaderBeanId;
  }

  public String getDefaultText()
  {
    return defaultText;
  }

  public void setDefaultText(String defaultText)
  {
    this.defaultText = defaultText;
  }

  public String getLabelMapping()
  {
    return labelMapping;
  }

  public void setLabelMapping(String labelMapping)
  {
    this.labelMapping = labelMapping;
    labelMappingArr = StringUtils.tokenizeCSVList(labelMapping);
    
  }
  private  String getLabel(String key) 
   {		
     if(labelMappingArr==null)
      return "";
     for(int i = 0; i < labelMappingArr.length; i+=2) 
      {			
       if(key.trim().equalsIgnoreCase(labelMappingArr[i])) 
        {				
         return labelMappingArr[i+1];			
        }		
      }		
      return "";	
  }        

  public String getAscendingText()
  {
    return ascendingText;
  }

  public void setAscendingText(String ascendingText)
  {
    this.ascendingText = ascendingText;
  }

  public String getDescendingText()
  {
    return descendingText;
  }

  public void setDescendingText(String descendingText)
  {
    this.descendingText = descendingText;
  }
}

