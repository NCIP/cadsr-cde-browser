package gov.nih.nci.ncicb.cadsr.jsp.tag.handler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Display a dropdown with for display order(1--n) with selected index selected
 */
public class DisplayOrderSelection  extends TagSupport
{
  
  private String currentIndex;
  private String collectionSize;
  private String selectClassName;
  private String selectName;

  public DisplayOrderSelection()
  {
  }


  public int doStartTag() throws javax.servlet.jsp.JspException {
    HttpServletRequest  req;
    JspWriter out;
     try {
        req = ( HttpServletRequest )pageContext.getRequest();
        out = pageContext.getOut();
 
        String htmlString = generateSelectList(Integer.parseInt(collectionSize),
                                              Integer.parseInt(currentIndex));
        out.print(htmlString);   
        
      } catch(Exception ioe ) {
          throw new JspException( "I/O Error : " + ioe.getMessage() );
      }//end try/catch
      return Tag.SKIP_BODY;

    }//end doStartTag() 
    
    
 private String generateSelectList(int listSize, int selectedIndex)
 {
    StringBuffer htmlStr = new StringBuffer();
    htmlStr.append("<SELECT NAME="+selectName+" CLASS=\""+selectClassName+"\""+"/>");
    int counted = 0;
    int localIndex = 1;
    int recIndex = 0;
    String selectedStr = " ";
    boolean selected = false;
    for(int i=0;i<listSize;i++)
    { 
       if(i==selectedIndex)
       {
         selectedStr="SELECTED";
         selected=true;
       }      
      else
       {
        selectedStr=" ";
       }
      htmlStr.append("<OPTION "+selectedStr+"  VALUE=\""+i+"\">"+(i+1)+"</OPTION>");      
      localIndex++;      
    }
    
    htmlStr.append("</SELECT>&nbsp;");
    
    return htmlStr.toString();
 }    
  public String getCurrentIndex()
  {
    return currentIndex;
  }

  public void setCurrentIndex(String currentIndex)
  {
    this.currentIndex = currentIndex;
  }

  public String getCollectionSize()
  {
    return collectionSize;
  }

  public void setCollectionSize(String collectionSize)
  {
    this.collectionSize = collectionSize;
  }

  public String getSelectClassName()
  {
    return selectClassName;
  }

  public void setSelectClassName(String selectClassName)
  {
    this.selectClassName = selectClassName;
  }

  public String getSelectName()
  {
    return selectName;
  }

  public void setSelectName(String selectName)
  {
    this.selectName = selectName;
  }
}