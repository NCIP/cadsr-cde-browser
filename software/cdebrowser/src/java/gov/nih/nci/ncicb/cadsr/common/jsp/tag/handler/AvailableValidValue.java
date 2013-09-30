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

package gov.nih.nci.ncicb.cadsr.common.jsp.tag.handler;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.common.resource.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class AvailableValidValue extends TagSupport implements CaDSRConstants
{

  private String questionBeanId;
  private String availableValidValusMapId;
  private String selectClassName;
  private String selectName;
  public static final String AVAILABLE_VALID_VALUE_PRESENT="availableValidValuePresent";

  public AvailableValidValue(){}

  public int doStartTag() throws javax.servlet.jsp.JspException {
    HttpServletRequest  req;
    JspWriter out;
     try {
        req = ( HttpServletRequest )pageContext.getRequest();
        out = pageContext.getOut();
        pageContext.setAttribute(AVAILABLE_VALID_VALUE_PRESENT,null);
        Question currQuestion = (Question)pageContext.getAttribute(questionBeanId);
        Map availableVVMap = (Map)pageContext.getSession().getAttribute(availableValidValusMapId);
        List questionVVList = currQuestion.getValidValues();
        String questionIdSeq = currQuestion.getQuesIdseq();
        List availableVVs = (List)availableVVMap.get(questionIdSeq);
        List nonListedVVs = (List)pageContext.getAttribute(questionIdSeq+"NotListedValidValues");
        
        if(nonListedVVs==null&&(availableVVs!=null&&!availableVVs.isEmpty()))
         {
          nonListedVVs = getNotListedValidValues(currQuestion.getValidValues(),availableVVs);
          pageContext.setAttribute(questionIdSeq+"NotListedValidValues",nonListedVVs);
         }
        
        if(availableVVs!=null&&!availableVVs.isEmpty())
        {

           //List nonListedVVs = getNotListedValidValues(currQuestion.getValidValues(),availableVVs);
             if(!nonListedVVs.isEmpty())
             {
               String html = generateHtml(nonListedVVs,availableVVs,questionIdSeq);
               out.print(html); 
               pageContext.setAttribute(AVAILABLE_VALID_VALUE_PRESENT,"Yes");
               
             }
             else
             {
               out.print("&nbsp;"); 
               pageContext.removeAttribute(AVAILABLE_VALID_VALUE_PRESENT);
             }
        }
        else
        {
            pageContext.removeAttribute(AVAILABLE_VALID_VALUE_PRESENT);
            out.print("&nbsp;"); 
        }        
        
      } catch(Exception ioe ) {
          throw new JspException( "I/O Error : " + ioe.getMessage() );
      }//end try/catch
      return Tag.SKIP_BODY;

    }//end doStartTag()  

  public List getNotListedValidValues(List questionVVList, List availableVVList)
  {
    List nonListedVVs = new ArrayList();
    ListIterator availableVVListIterate = availableVVList.listIterator();
    
    FormValidValue  currFormValidValue = null;
    while (availableVVListIterate.hasNext()) {
      currFormValidValue = (FormValidValue) availableVVListIterate.next();
      //Valid Values may not be mapped to VD
      if(!questionVVList.contains(currFormValidValue))
      { 
        nonListedVVs.add(currFormValidValue);
      }
    }
    return nonListedVVs;
  }
  
  public String generateHtml(List nonListedVVs,List availableValidVaues,String questionIdSeq)
  {
    StringBuffer selectHtml = new StringBuffer("<select class=\""+selectClassName+"\" name=\""+selectName+"\"> \n");
    
    StringBuffer optionHtml = (StringBuffer)pageContext.getAttribute(questionIdSeq+"validValueOptionBuffer");
    //
    //The options are cached since they dont change for the same question
    //
    if(optionHtml!=null)
    {              
       return selectHtml.toString()+optionHtml.toString();
    }
    optionHtml = new StringBuffer();
    
    ListIterator avalilableVVsListIterate = nonListedVVs.listIterator();
     while (avalilableVVsListIterate.hasNext()) {
      FormValidValue fvv = (FormValidValue) avalilableVVsListIterate.next();
      int index = availableValidVaues.indexOf(fvv);
      optionHtml.append("<option value=\""+index+"\">"+fvv.getLongName()+"</option> \n" );
     }
    optionHtml.append("</select>");

    pageContext.setAttribute(questionIdSeq+"validValueOptionBuffer",optionHtml);
    
    return selectHtml.toString()+optionHtml.toString();
  }

  public String getQuestionBeanId()
  {
    return questionBeanId;
  }

  public void setQuestionBeanId(String newQuestionBeanId)
  {
    questionBeanId = newQuestionBeanId;
  }

  public String getAvailableValidValusMapId()
  {
    return availableValidValusMapId;
  }

  public void setAvailableValidValusMapId(String newAvailableValidValusMapId)
  {
    availableValidValusMapId = newAvailableValidValusMapId;
  }

  public String getSelectClassName()
  {
    return selectClassName;
  }

  public void setSelectClassName(String newSelectClassName)
  {
    selectClassName = newSelectClassName;
  }

  public String getSelectName()
  {
    return selectName;
  }

  public void setSelectName(String newSelectName)
  {
    selectName = newSelectName;
  }


}