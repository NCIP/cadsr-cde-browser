package gov.nih.nci.ncicb.cadsr.jsp.tag.handler;
import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspWriter;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.resource.Form;
/**
 * This TagHandler is used to display icons by role
 * The icon active image is displayed if the user-in-session
 * has the role for the CRF defined by "formId"
 * Ex.
 * <cde:secureIcon  formId="form" activeImageSource="i/edit.gif" activeUrl="test" 
 *		            role="aRole" altMessage="Edit"/>
 *             
 */
public class SecureIconDisplay extends TagSupport implements CaDSRConstants
{
  
  
  private String activeImageSource;
  private String inactiveImageSource;
  private String activeUrl;
  private String role;
  private String altMessage;
  private String formId;
  private String urlPrefix="";

  public SecureIconDisplay()
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
        Form form = (Form)pageContext.getAttribute(formId);
        String contextId = form.getContext().getConteIdseq();
        NCIUser  nciUser =  (NCIUser)pageContext.getSession().getAttribute(USER_KEY);
        req = ( HttpServletRequest )pageContext.getRequest();
        out = pageContext.getOut();     
       if( nciUser.hasRoleAccess(role,contextId))
        {
          out.print("<a href='"+activeUrl+"'/><img src=\""+urlPrefix+activeImageSource+"\" border=0 alt='"+altMessage+"'></a>");
        }
        else
        {
          if(inactiveImageSource!=null)
              out.print("<img src=\""+urlPrefix+inactiveImageSource +"\"border=0>");         
        }
      } catch( IOException ioe ) {
          throw new JspException( "I/O Error : " + ioe.getMessage() );
      }//end try/catch
      return Tag.SKIP_BODY;

    }//end doStartTag()

  public String getRole()
  {
    return role;
  }

  public void setRole(String newRole)
  {
    role = newRole;
  }

  public String getAltMessage()
  {
    return altMessage;
  }

  public void setAltMessage(String newAltMessage)
  {
    altMessage = newAltMessage;
  }

  public String getFormId()
  {
    return formId;
  }

  public void setFormId(String newFormId)
  {
    formId = newFormId;
  }

  public String getUrlPrefix()
  {
    return urlPrefix;
  }

  public void setUrlPrefix(String newUrlPrefix)
  {
    urlPrefix = newUrlPrefix;
  }

}