package gov.nih.nci.ncicb.cadsr.common.jsp.tag.handler;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;

import java.io.IOException;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * This TagHandler is used to display icons depending
 * on if the CDE's VD is Enumerated or NonEnumerated.
 *
 */
public class EnumeratedVDIconDisplay extends TagSupport implements CaDSRConstants
{


  private String activeImageSource;
  private String inactiveImageSource;
  private String activeUrl;
  private String altMessage;
  private String dataElementId;
  private String target;

  public EnumeratedVDIconDisplay()
  {
  }

  public String getActiveImageSource()
  {
    return activeImageSource;
  }

  public void setActiveImageSource(String newImageSource)
  {
    activeImageSource = newImageSource;
  }

  public String getInactiveImageSource()
  {
    return inactiveImageSource;
  }

  public void setInactiveImageSource(String newInactiveImageSource)
  {
    inactiveImageSource = newInactiveImageSource;
  }

  public String getActiveUrl()
  {
    return activeUrl;
  }

  public void setActiveUrl(String newActiveUrl)
  {
    activeUrl = newActiveUrl;
  }

  public int doStartTag() throws javax.servlet.jsp.JspException {
    HttpServletRequest  req;
    JspWriter out;
     try {
      req = ( HttpServletRequest )pageContext.getRequest();
      out = pageContext.getOut();
      String targetStr = "";
          
      CDECartItem cdeCartItem = (CDECartItem)pageContext.getAttribute("de");
      DataElement cde = (DataElement)cdeCartItem.getItem();
      if(target!=null)
        targetStr="target=\""+target+"\" ";
      if(isEnumerated(cde))
        {
          out.print("<a href=\""+getHrefUrl(req)+"\" "+targetStr+"/><img src=\""+activeImageSource+"\" border=0 alt='"+altMessage+"'></a>");
        }
        else
        {
          if(inactiveImageSource!=null)
              out.print("<img src=\""+inactiveImageSource +"\"border=0>");
        }
      } catch( IOException ioe ) {
          throw new JspException( "I/O Error : " + ioe.getMessage() );
      }//end try/catch
      return Tag.SKIP_BODY;

    }//end doStartTag()

  private String getHrefUrl(HttpServletRequest req) throws JspException
  {
    req.getContextPath();
    StringBuffer sb = new StringBuffer(req.getContextPath()+activeUrl);
    return sb.toString();
  }
  public String getAltMessage()
  {
    return altMessage;
  }

  public void setAltMessage(String newAltMessage)
  {
    altMessage = newAltMessage;
  }

  public String getTarget()
  {
    return target;
  }

  public void setTarget(String newTarget)
  {
    target = newTarget;
  }
  protected boolean isEnumerated(DataElement cde)
  {
    Collection vvs = cde.getValueDomain().getValidValues();
    if(vvs!=null&&vvs.size()>0)
      return true;
    else
      return false;
  }

  public String getDataElementId()
  {
    return dataElementId;
  }

  public void setDataElementId(String dataElementId)
  {
    this.dataElementId = dataElementId;
  }
}