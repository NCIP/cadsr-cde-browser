package gov.nih.nci.ncicb.cadsr.jsp.tag.handler;
import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * This Handler is used to display Alternate Question text from a Dataelement property
 * The text is displayed as hyper link if it not equal to Questions longName.
 * Example Usage
 *   <cde:questionAltText questionBeanId= "question" 
 *                 htmlObjectRef="questionInputText"
 *                 deProperty = "longName"
 *                 formIndex="0"
 *                 questionIndex="2" /> 
 *                             
 */
public class AltQuestionText extends TagSupport implements CaDSRConstants,FormConstants
{
  private String questionBeanId;
  private String deProperty;
  private String htmlObjectRef;
  private String formIndex;
  private String questionIndex;


  public AltQuestionText()
  {
  }
  
  public int doStartTag() throws javax.servlet.jsp.JspException {
    HttpServletRequest  req;
    JspWriter out;
     try {
        req = ( HttpServletRequest )pageContext.getRequest();
        out = pageContext.getOut();
        Question currQuestion = (Question)pageContext.getAttribute(questionBeanId);
        String longName = currQuestion.getLongName();
        DataElement de = currQuestion.getDataElement();
        if(de!=null)
          {
            String propValue = (String)PropertyUtils.getProperty(de,deProperty);
            if(propValue==null)
              return Tag.SKIP_BODY;
            if(propValue.equals(longName))
            {              
              out.print(propValue);
            }
            else
            {
              String script  = generateJavaScript(formIndex,questionIndex,deProperty,propValue);            
              StringBuffer linkStr = new StringBuffer("<a href=\"javascript:question"+questionIndex+deProperty+"populate()\">");
              linkStr.append(propValue);
              linkStr.append("</a>");
              out.print(script);
              out.print(linkStr.toString());
            }
          }          
       //out.print(getTableFooter());
      } catch(Exception ioe ) {
          throw new JspException( "I/O Error : " + ioe.getMessage() );
      }//end try/catch
      return Tag.SKIP_BODY;

    }//end doStartTag()  

  private String generateJavaScript(String formIndex,String questionIndex
                                    , String property, String propValue)
  {
    StringBuffer script = new StringBuffer("\n<SCRIPT LANGUAGE=\"JavaScript\"><!--");
    script.append(" \nfunction  "+"question"+questionIndex+property+"populate() \n") ;
    script.append("\n {");
    script.append("\n var objForm"+questionIndex+" = document.forms["+formIndex+"];");
    script.append("\n var objQuestion"+questionIndex+" = objForm"+questionIndex+"['"+htmlObjectRef+"'];");
    script.append("\n objQuestion"+questionIndex+".value = \""+propValue+"\";");
    script.append("\n}"); 
    script.append("\n--> </SCRIPT>\n");
    return script.toString();
  }
  
  public String getQuestionBeanId()
  {
    return questionBeanId;
  }

  public void setQuestionBeanId(String newQuestionBeanId)
  {
    questionBeanId = newQuestionBeanId;
  }


  public String getDeProperty()
  {
    return deProperty;
  }

  public void setDeProperty(String newDeProperty)
  {
    deProperty = newDeProperty;
  }

  public String getHtmlObjectRef()
  {
    return htmlObjectRef;
  }

  public void setHtmlObjectRef(String newHtmlObjectRef)
  {
    htmlObjectRef = newHtmlObjectRef;
  }


  public void setFormIndex(String formIndex)
  {
    this.formIndex = formIndex;
  }


  public String getFormIndex()
  {
    return formIndex;
  }


  public void setQuestionIndex(String questionIndex)
  {
    this.questionIndex = questionIndex;
  }


  public String getQuestionIndex()
  {
    return questionIndex;
  }
  
}