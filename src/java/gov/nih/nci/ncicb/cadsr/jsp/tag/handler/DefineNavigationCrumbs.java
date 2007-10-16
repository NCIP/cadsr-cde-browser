package gov.nih.nci.ncicb.cadsr.jsp.tag.handler;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.jsp.bean.OCRNavigationBean;
import gov.nih.nci.ncicb.cadsr.ocbrowser.struts.common.OCBrowserFormConstants;
import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class DefineNavigationCrumbs extends TagSupport
{
  private String ocrId;
  private String direction = null;
  private String beanId = null;

  public DefineNavigationCrumbs()
  {
  }

  public int doStartTag() throws javax.servlet.jsp.JspException {
    HttpServletRequest  req;
    JspWriter out;

    try
    {
      out = pageContext.getOut();
      req = ( HttpServletRequest )pageContext.getRequest();

      LinkedList newCrumbs = new LinkedList();
      pageContext.getAttribute(ocrId);

      OCRNavigationBean firstNavBean = new OCRNavigationBean();
      firstNavBean.setDirection(direction);
      firstNavBean.setShowDirection(true);
      ObjectClassRelationship ocr = (ObjectClassRelationship)pageContext.getAttribute(ocrId);
      firstNavBean.setOcr(ocr);
      ObjectClass currObject= (ObjectClass)req.getSession().getAttribute(OCBrowserFormConstants.OBJECT_CLASS);
      firstNavBean.setObjectClass(currObject);
      newCrumbs.add(firstNavBean);

      OCRNavigationBean secondNavBean = new OCRNavigationBean();
      secondNavBean.setShowDirection(false);
      ObjectClass otherObject= null;
       if(direction.equals(OCBrowserFormConstants.OUT_GOING_OCRS))
         otherObject =  ocr.getTarget();
       if(direction.equals(OCBrowserFormConstants.IN_COMMING_OCRS))
         otherObject =  ocr.getSource();
       if(direction.equals(OCBrowserFormConstants.BIDIRECTIONAL_OCRS))
       {
         if(ocr.getTarget().getId().equals(currObject.getId()))
         {
           otherObject=ocr.getSource();
         }
         else
         {
           otherObject=ocr.getTarget();
         }
       }
       secondNavBean.setObjectClass(otherObject);
       newCrumbs.add(secondNavBean);

       pageContext.setAttribute(beanId,newCrumbs);

    }
    catch (Exception e)
    {
      throw new JspException( "I/O Error : " + e.getMessage() );
    }
    return Tag.SKIP_BODY;
  }


  public void setOcrId(String ocrId)
  {
    this.ocrId = ocrId;
  }


  public String getOcrId()
  {
    return ocrId;
  }


  public void setDirection(String direction)
  {
    this.direction = direction;
  }


  public String getDirection()
  {
    return direction;
  }


  public void setBeanId(String beanId)
  {
    this.beanId = beanId;
  }


  public String getBeanId()
  {
    return beanId;
  }


}