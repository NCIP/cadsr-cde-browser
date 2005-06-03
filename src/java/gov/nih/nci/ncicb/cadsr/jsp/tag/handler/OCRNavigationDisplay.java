package gov.nih.nci.ncicb.cadsr.jsp.tag.handler;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.jsp.bean.OCRNavigationBean;
import gov.nih.nci.ncicb.cadsr.umlbrowser.struts.common.UmlBrowserFormConstants;
import gov.nih.nci.ncicb.cadsr.umlbrowser.struts.common.UmlBrowserNavigationConstants;
import gov.nih.nci.ncicb.cadsr.umlbrowser.util.OCUtils;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class OCRNavigationDisplay extends TagSupport
{
  private String navigationListId = null;
  private String outGoingImage = null;
  private String inCommingImage = null;
  private String biDirectionalImage = null;
  private String scope = null;
  private String activeNodes = "true";

  
  public OCRNavigationDisplay()
  {
  }

  public int doStartTag() throws javax.servlet.jsp.JspException {
    HttpServletRequest  req;
    JspWriter out;
    
    try
    {
      out = pageContext.getOut();
      req = ( HttpServletRequest )pageContext.getRequest();
      
      LinkedList navigationList = null;
      if(scope.equals("session"))
        navigationList = (LinkedList)req.getSession().getAttribute(navigationListId);
      else
        navigationList = (LinkedList)pageContext.getAttribute(navigationListId);

      if(navigationList==null)
        return Tag.SKIP_BODY;
      if(navigationList.isEmpty())
        return Tag.SKIP_BODY;   
      if(navigationList.size()<2)
        return Tag.SKIP_BODY;       
      out.print(generateDisplayString(navigationList,req));
    }
    catch (IOException e)
    {
      throw new JspException( "I/O Error : " + e.getMessage() );
    }
    return Tag.SKIP_BODY;
  }


  public void setNavigationListId(String navigationListId)
  {
    this.navigationListId = navigationListId;
  }


  public String getNavigationListId()
  {
    return navigationListId;
  }

  private String generateDisplayString(LinkedList list, HttpServletRequest req)
  {
    ObjectClass currObject= (ObjectClass)req.getSession().getAttribute(UmlBrowserFormConstants.OBJECT_CLASS);
    ListIterator it = list.listIterator();
    StringBuffer str = new StringBuffer("<table align=\"center\">");
    str.append("<tr align=\"center\" >");
    int index=0;
    while(it.hasNext())
    {
      OCRNavigationBean currBean = (OCRNavigationBean)it.next();
      String tdClass = "OraFieldText";
      if(currObject.getId().equals(currBean.getObjectClass().getId()))
        tdClass = "OraFieldTextVV";

      str.append("<td>");
        str.append("<table vAlign=top cellSpacing=1 cellPadding=1  width=\"100%\" align=center border=0 class=\"OraBGAccentVeryDark\">");
          str.append("<tr class=OraTabledata align=\"center\" >");
             str.append("<td class="+tdClass+">");
               str.append("<table><tr><td >");
                str.append(getObjectString(currBean,req,currObject,index));
               str.append("</td></tr></table>");
             str.append("</td>");
          str.append("</tr>");
         str.append("</table>");
      str.append("</td>");
      index++; //used to track crumbs index
      if(currBean.isShowDirection())
      {
          str.append("<td>");
            str.append("<table border=0>");
              str.append("<tr>");
                 str.append("<td align=left>");
                    str.append(StringUtils.ifNullReplaceWithnbsp(getFirstMultiplicity(currBean.getDirection(),currBean.getOcr())));
                 str.append("</td>");
                 str.append("<td>");
                    str.append("&nbsp;");
                 str.append("</td>");                 
                 str.append("<td align=right>");
                    str.append(StringUtils.ifNullReplaceWithnbsp(getSecondMultiplicity(currBean.getDirection(),currBean.getOcr())));
                 str.append("</td>");             
              str.append("</tr>");
              str.append("<tr>");
                 str.append("<td colspan=3 align=center>");
                    str.append("<img width=\"100%\" height=\"15\" src=\""+req.getContextPath()+getImage(currBean.getDirection()) +"\"border=0>");
                 str.append("</td>");             
              str.append("</tr>"); 
              str.append("<tr>");
                 str.append("<td align=left>");
                    str.append(StringUtils.ifNullReplaceWithnbsp(getFirstRoleName(currBean.getDirection(),currBean.getOcr())));
                 str.append("</td>");
                 str.append("<td>");
                    str.append("&nbsp;");
                 str.append("</td>");                 
                 str.append("<td align=right>");
                    str.append(StringUtils.ifNullReplaceWithnbsp(getSecondRoleName(currBean.getDirection(),currBean.getOcr())));
                 str.append("</td>");             
              str.append("</tr>");              
             str.append("</table>");
          str.append("</td>");
      }
    }
    str.append("</tr></table>");
    return str.toString();
  }

 private String getObjectString(OCRNavigationBean bean,HttpServletRequest  req,ObjectClass currObject,int crumbIndex)
 {
  
  if(activeNodes.equalsIgnoreCase("false"))
  {
    return bean.getObjectClass().getLongName();
  }
  String ocrurl = req.getContextPath()+"/umlbrowser/ocrDetailsAction.do?"
          +UmlBrowserNavigationConstants.METHOD_PARAM+"="+UmlBrowserNavigationConstants.OCR_DETAILS
          +"&"+UmlBrowserFormConstants.OC_IDSEQ+"="+bean.getObjectClass().getId()
          +"&"+UmlBrowserFormConstants.OCR_BR_CRUMBS_INDEX+"="+String.valueOf(crumbIndex);
  StringBuffer str = new StringBuffer();
  str.append("<a href=");
  str.append(ocrurl);
  str.append(">");
  str.append(bean.getObjectClass().getLongName());
  str.append("</a>");
  return str.toString();
 }
 private String getImage(String direction)
 {
   if(direction.equals(UmlBrowserFormConstants.OUT_GOING_OCRS))
     return outGoingImage;
   if(direction.equals(UmlBrowserFormConstants.IN_COMMING_OCRS))
     return this.inCommingImage;
   if(direction.equals(UmlBrowserFormConstants.BIDIRECTIONAL_OCRS))
     return  this.biDirectionalImage;    
   return null;
 }
 private String getFirstMultiplicity(String direction,ObjectClassRelationship ocr)
 {
   if(direction.equals(UmlBrowserFormConstants.OUT_GOING_OCRS))
     return OCUtils.getSourceMultiplicityDisplayString(ocr);
   if(direction.equals(UmlBrowserFormConstants.IN_COMMING_OCRS))
     return OCUtils.getTargetMultiplicityDisplayString(ocr);
     
     return OCUtils.getSourceMultiplicityDisplayString(ocr);
 }
 private String getSecondMultiplicity(String direction,ObjectClassRelationship ocr)
 {
   if(direction.equals(UmlBrowserFormConstants.OUT_GOING_OCRS))
     return OCUtils.getTargetMultiplicityDisplayString(ocr);
   if(direction.equals(UmlBrowserFormConstants.IN_COMMING_OCRS))
     return OCUtils.getSourceMultiplicityDisplayString(ocr);
   
   return OCUtils.getTargetMultiplicityDisplayString(ocr);  

 } 
 private String getFirstRoleName(String direction,ObjectClassRelationship ocr)
 {
   if(direction.equals(UmlBrowserFormConstants.OUT_GOING_OCRS))
     return StringUtils.replaceNull(ocr.getSourceRole());
   if(direction.equals(UmlBrowserFormConstants.IN_COMMING_OCRS))
     return StringUtils.replaceNull(ocr.getTargetRole());
   
   return StringUtils.replaceNull(ocr.getSourceRole());
 }
 private String getSecondRoleName(String direction,ObjectClassRelationship ocr)
 {
   if(direction.equals(UmlBrowserFormConstants.OUT_GOING_OCRS))
     return ocr.getTargetRole();
   if(direction.equals(UmlBrowserFormConstants.IN_COMMING_OCRS))
     return ocr.getSourceRole();
   
   return ocr.getTargetRole();

 }  
  public void setOutGoingImage(String outGoingImage)
  {
    this.outGoingImage = outGoingImage;
  }


  public String getOutGoingImage()
  {
    return outGoingImage;
  }


  public void setInCommingImage(String inCommingImage)
  {
    this.inCommingImage = inCommingImage;
  }


  public String getInCommingImage()
  {
    return inCommingImage;
  }


  public void setBiDirectionalImage(String biDirectionalImage)
  {
    this.biDirectionalImage = biDirectionalImage;
  }


  public String getBiDirectionalImage()
  {
    return biDirectionalImage;
  }


  public void setScope(String scope)
  {
    this.scope = scope;
  }


  public String getScope()
  {
    return scope;
  }


  public void setActiveNodes(String activeNodes)
  {
    this.activeNodes = activeNodes;
  }


  public String getActiveNodes()
  {
    return activeNodes;
  }
 
}