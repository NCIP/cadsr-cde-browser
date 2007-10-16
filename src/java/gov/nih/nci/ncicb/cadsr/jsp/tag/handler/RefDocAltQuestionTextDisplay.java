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
import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;

import org.apache.commons.beanutils.PropertyUtils;
import gov.nih.nci.ncicb.cadsr.util.*;

/**
 * This Handler is used to display Reference Document of type Alternate Question text and
 * Preferred question text. Depending on the  ref doc type;
 *  If only a single item item exist a hyper text is displayed
 *  Otherwise a Select box is displayed
 *
 * Example Usage
 * <cde:RefDocAltQuestionTextDisplay questionBeanId= "question" //Current Question bean Id
 *                    htmlObjectRef="targetObjectId"// Target location to copy value
 *                    selectBoxClassName="AltQuestionField" //select Style sheet class
 *                    selectBoxSize="4" //number of rows to display
 *                    refDocType="ReferenceDocumentType" // ref doc type
 *                    questionIndex="1"  // index of the question in the module
 *                    selectBoxJSFunctionName="refDocSelected" //JS function when selected
 *                    hyperLinkJSFunctionName="refDocHyperlink" //hper for single vaue
 *                    />
 *
 * Uses the the javascripts below to populate selection to question text
 * function refDocSelected(srcCompId,targetCompId)
 *   {
 *        var targetObj = document.getElementById(targetCompId);
 *
 *         var srcObj = document.getElementById(srcCompId);
 *         var i;
 *         var count = 0;
 *         for (i=0; i<srcObj.options.length; i++) {
 *           if (srcObj.options[i].selected) {
 *              targetObj.value = srcObj.options[i].value;
 *             }
 *           }
 *     }
 *
 *  function refDocHyperlink(targetCompId,newValue)
 *    {
 *        var targetObj = document.getElementById(targetCompId);
 *        targetObj.value=newValue;
 *
 *     }
 */
public class RefDocAltQuestionTextDisplay extends TagSupport implements CaDSRConstants,FormConstants
{
  private String questionBeanId;
  private String selectBoxClassName;
  private String selectBoxSize;
  private String htmlObjectRef;
  private String questionIndex;
  private String refDocType;
  private String hyperLinkJSFunctionName;
  private String selectBoxJSFunctionName;





  public RefDocAltQuestionTextDisplay()
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
            List refDocs = de.getRefereceDocs();
            if(refDocs!=null&&!refDocs.isEmpty())
            {
                List<ReferenceDocument> matchingDocs = ReferenceDocUtil.getReferenceDocsByType(refDocs,refDocType);
                if(!matchingDocs.isEmpty())
                {

                    if(matchingDocs.size()<2)
                    {
                        // Generate Hyper link
                         String itemIdentifierPrefix = "questionOptionHyperLink"+refDocType;
                        ReferenceDocument currDoc = (ReferenceDocument)matchingDocs.get(0);
                        String displayString = currDoc.getDocText();
                        if (displayString!=null && displayString.length()>0){
                            displayString = StringUtils.strReplace(displayString, "'", "\\'");
                            displayString = StringUtils.strReplace(displayString, "\"", "&quot;");
                        }
                        StringBuffer linkStr = new StringBuffer("<a href=\"javascript:"+hyperLinkJSFunctionName+"('"+htmlObjectRef+"','"+displayString+"')\">");
                        linkStr.append(currDoc.getDocText());
                        linkStr.append("</a>");
                        //out.print(script);
                        out.print(linkStr.toString());
                    }
                    else
                    {
                        //Generate Multi select box
                         String itemIdentifier = "questionOptions"+questionIndex+refDocType;
                        StringBuffer buffer = new StringBuffer("<select  name=\""+itemIdentifier+"\" size=\""+selectBoxSize+"\" class=\""+selectBoxClassName+"\"" +
                                              "onDblClick=\""+selectBoxJSFunctionName+"('"+itemIdentifier+"','"+htmlObjectRef+"')\">");
                        for(ReferenceDocument currDoc: matchingDocs)
                        {
                            String displayString = (currDoc).getDocText();
                            if (displayString!=null && displayString.length()>0){
                                displayString = StringUtils.strReplace(displayString, "\"", "&quot;");
                            }
                            buffer.append("<option value=\""+displayString+"\">"+(currDoc).getDocText()+"</option>");
                        }
                        buffer.append("</select>");
                        out.print(buffer.toString());
                    }
                }
            }
        }

      } catch(Exception ioe ) {
          throw new JspException( "I/O Error : " + ioe.getMessage() );
      }//end try/catch
      return Tag.SKIP_BODY;

    }//end doStartTag()


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



  public String getHtmlObjectRef()
  {
    return htmlObjectRef;
  }

  public void setHtmlObjectRef(String newHtmlObjectRef)
  {
    htmlObjectRef = newHtmlObjectRef;
  }



  public void setQuestionIndex(String questionIndex)
  {
    this.questionIndex = questionIndex;
  }


  public String getQuestionIndex()
  {
    return questionIndex;
  }


    public void setSelectBoxClassName(String selectBoxClassName)
    {
        this.selectBoxClassName = selectBoxClassName;
    }

    public String getSelectBoxClassName()
    {
        return selectBoxClassName;
    }

    public void setSelectBoxSize(String selectBoxSize)
    {
        this.selectBoxSize = selectBoxSize;
    }

    public String getSelectBoxSize()
    {
        return selectBoxSize;
    }

    public void setRefDocType(String refDocType)
    {
        this.refDocType = refDocType;
    }

    public String getRefDocType()
    {
        return refDocType;
    }

    public void setHyperLinkJSFunctionName(String hyperLinkJSFunctionName)
    {
        this.hyperLinkJSFunctionName = hyperLinkJSFunctionName;
    }

    public String getHyperLinkJSFunctionName()
    {
        return hyperLinkJSFunctionName;
    }

    public void setSelectBoxJSFunctionName(String selectBoxJSFunctionName)
    {
        this.selectBoxJSFunctionName = selectBoxJSFunctionName;
    }

    public String getSelectBoxJSFunctionName()
    {
        return selectBoxJSFunctionName;
    }
}
