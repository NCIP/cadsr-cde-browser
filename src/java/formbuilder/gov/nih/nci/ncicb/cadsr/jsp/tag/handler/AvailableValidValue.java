package gov.nih.nci.ncicb.cadsr.jsp.tag.handler;
import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import org.apache.commons.beanutils.PropertyUtils;

public class AvailableValidValue extends TagSupport implements CaDSRConstants,FormConstants
{

  private String questionBeanId;
  private String valueDomainMapId;
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
        Map vdMap = (Map)pageContext.getSession().getAttribute(valueDomainMapId);
        List questionVVList = currQuestion.getValidValues();
        String questionVdIdSeq = null;
        if((currQuestion.getDataElement()!=null)&&(currQuestion.getDataElement().getValueDomain()!=null))
        {
          questionVdIdSeq = currQuestion.getDataElement().getValueDomain().getVdIdseq();
        }
        List vdVVList = null;
        if(questionVdIdSeq!=null)
        {
           vdVVList = (List)vdMap.get(questionVdIdSeq);
           if((vdVVList!=null)&&!vdVVList.isEmpty())
           {
               List avalilableVVs = getAvailableValidValues(questionVVList,vdVVList);
                 if(!avalilableVVs.isEmpty())
                 {
                   String html = generateHtml(avalilableVVs);
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
             out.print("&nbsp;"); 
           }
        }
        
      } catch(Exception ioe ) {
          throw new JspException( "I/O Error : " + ioe.getMessage() );
      }//end try/catch
      return Tag.SKIP_BODY;

    }//end doStartTag()  

  public List getAvailableValidValues(List questionVVList, List vdVVList)
  {
    List availableVVs = new ArrayList();
    ListIterator questionVVListIterate = questionVVList.listIterator();
    ListIterator vdVVListIterate = vdVVList.listIterator();
    
    ValidValue currValidValue =null;
    FormValidValue  currFormValidValue = null;
    while (vdVVListIterate.hasNext()) {
      currValidValue = (ValidValue) vdVVListIterate.next();
      FormValidValue fvv = new FormValidValueTransferObject();
      fvv.setLongName(currValidValue.getShortMeaningValue());
      if(!questionVVList.contains(fvv))
      {
          availableVVs.add(currValidValue);
      }
    }
    return availableVVs;
  }
  
  public String generateHtml(List avalilableVVs)
  {
    StringBuffer sb = new StringBuffer("<select class=\""+selectClassName+"\" name=\""+selectName+"\"> \n");
    ListIterator avalilableVVsListIterate = avalilableVVs.listIterator();
     while (avalilableVVsListIterate.hasNext()) {
      ValidValue vv = (ValidValue) avalilableVVsListIterate.next();
      sb.append("<option value=\""+vv.getVpIdseq()+"\">"+vv.getShortMeaningValue()+"</option> \n" );
     }
    sb.append("</select>");
    return sb.toString();
  }
  public String getQuestionBeanId()
  {
    return questionBeanId;
  }

  public void setQuestionBeanId(String newQuestionBeanId)
  {
    questionBeanId = newQuestionBeanId;
  }

  public String getValueDomainMapId()
  {
    return valueDomainMapId;
  }

  public void setValueDomainMapId(String newValueDomainMapId)
  {
    valueDomainMapId = newValueDomainMapId;
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