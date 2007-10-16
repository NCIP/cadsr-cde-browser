package gov.nih.nci.ncicb.cadsr.jsp.tag.handler;
import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import org.apache.commons.beanutils.PropertyUtils;
import gov.nih.nci.ncicb.cadsr.util.*;

/**
 * This Handler is used to display Alternate Question text from a Dataelement property
 * The text is displayed as hyper link if it not equal to Questions longName.
 * Example Usage
 *   <cde:questionAltText questionBeanId= "question"
 *                 htmlObjectRef="questionInputText"
 *                 deProperty = "longName"
 *                 questionProperty="longName"
 *                 formIndex="0"
 *                 questionIndex="2" />
 *  deProperty and questionPropery are options if both are set question property is used
 *  the property value for the question is from the orgQuestionBeanId bean in session. When
 *  questionProperty is specified orgModuleBeanId,questionBeanId also need to be defined
 *
 */
public class AltQuestionText extends TagSupport implements CaDSRConstants,FormConstants
{
  private String questionBeanId;
  private String deProperty;
  private String htmlObjectRef;
  private String formIndex;
  private String questionIndex;
  private String questionProperty;
  private String orgModuleBeanId;


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
        if(questionProperty!=null&&orgModuleBeanId!=null)
        {
          // Get the property from the Question in the orgModule
          // If its a new Question the get the property value from
          // the DataElement for deProperty if deProperty is defined
          Module orgModule = (Module)pageContext.getSession().getAttribute(orgModuleBeanId);
          if(orgModule==null)
            throw new JspException( "No Bean by name "+orgModuleBeanId+"in session");
          List orgQuestions = orgModule.getQuestions();
          String propValue =null;
          if(orgQuestions.contains(currQuestion))
          {
            Question orgQuestion = getQuestionFromList(currQuestion.getQuesIdseq(),orgQuestions);
            propValue = (String)PropertyUtils.getProperty(orgQuestion,questionProperty);
          }
          else// get the value from the de
          {
            if(deProperty!=null)
            {
            if(de!=null)
              propValue = (String)PropertyUtils.getProperty(de,deProperty);
            }
          }

          /*if(propValue==null)
              return Tag.SKIP_BODY;
            */
        /* for saved question, use the default question text when CDE does not have preferred question text
            This is a temporary fix for GF1437 - to keep saved question behavior consistent for CDE
            with/without Preferred Question Text
        */
          if (  (propValue==null || propValue.length()==0)
                && "longCDEName".equals(deProperty) && "longName".equals(questionProperty)) {
                propValue = "Data Element " + de.getLongName() + " does not have Preferred Question Text";
          }
          String script  = generateJavaScript("question",formIndex,questionIndex,questionProperty,propValue);
          StringBuffer linkStr = new StringBuffer("<a href=\"javascript:question"+questionIndex+questionProperty+"populate()\">");
          linkStr.append(propValue);
          linkStr.append("</a>");
          out.print(script);
          out.print(linkStr.toString());
        }
        else if(de!=null)
          {
            String propValue = (String)PropertyUtils.getProperty(de,deProperty);
            /*if(propValue==null)
              return Tag.SKIP_BODY;
             */
             /* for saved question, use the default question text when CDE does not have preferred question text
                  This is a temporary fix for GF1437 - to keep saved question behavior consistent for CDE
                  with/without Preferred Question Text
              */
            if (propValue==null && "longCDEName".equals(deProperty) && "longName".equals(questionProperty)) {
                propValue = "Data Element " + de.getLongName() + " does not have Preferred Question Text";
            }
            if(propValue.equals(longName))
            {
              out.print(propValue);
            }
            else
            {
              String script  = generateJavaScript("questionde",formIndex,questionIndex,deProperty,propValue);
              StringBuffer linkStr = new StringBuffer("<a href=\"javascript:questionde"+questionIndex+deProperty+"populate()\">");
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

  private String generateJavaScript(String methodPrefix, String formIndex,String questionIndex
                                    , String property, String propValue)
  {

    StringBuffer script = new StringBuffer("\n<SCRIPT LANGUAGE=\"JavaScript\"><!--");
    script.append(" \nfunction  "+methodPrefix+questionIndex+property+"populate() \n") ;
    script.append("\n {");
    script.append("\n var objForm"+questionIndex+" = document.forms["+formIndex+"];");
    script.append("\n var objQuestion"+questionIndex+" = objForm"+questionIndex+"['"+htmlObjectRef+"'];");
    String str = StringUtils.getValidJSString(propValue);
    //to make \n work in JavaScript
    str = StringUtils.strReplace(str, ""+ (char)13+ (char)10, "\\n"); 
    script.append("\n objQuestion"+questionIndex+".value = \""+str+"\";");
    script.append("\n}");
    script.append("\n--> </SCRIPT>\n");

    return script.toString();
  }

  private Question getQuestionFromList(
    String questionIdSeq,
    List questions) {
    ListIterator iterate = questions.listIterator();

    while (iterate.hasNext()) {
      Question question = (Question) iterate.next();

      if (question.getQuesIdseq().equals(questionIdSeq)) {
        return question;
      }
    }
    return null;
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

  public String getQuestionProperty()
  {
    return questionProperty;
  }

  public void setQuestionProperty(String newQuestionProperty)
  {
    questionProperty = newQuestionProperty;
  }

  public String getOrgModuleBeanId()
  {
    return orgModuleBeanId;
  }

  public void setOrgModuleBeanId(String newOrgModuleBeanId)
  {
    orgModuleBeanId = newOrgModuleBeanId;
  }

}