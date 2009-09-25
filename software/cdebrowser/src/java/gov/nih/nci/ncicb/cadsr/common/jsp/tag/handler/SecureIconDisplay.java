package gov.nih.nci.ncicb.cadsr.common.jsp.tag.handler;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.taglib.TagUtils;

/**
 * This TagHandler is used to display icons by role
 * The icon active image is displayed if the user-in-session
 * has the role for the CRF defined by "formId"
 * displays the active image only if the type of the form is equal to value of formType 
 * Ex.
 * <cde:secureIcon  formId="form" activeImageSource="i/edit.gif" activeUrl="test" 
 *		            role="aRole" altMessage="Edit"
 *                paramId="Id" paramProperty="formIdseq"
 *                formType="CRF"/>
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
  private String paramId;
  private String paramProperty;
  private String target;
  private String formScope;
  private String formType;
  private String confirmMessageKey;

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
        Form form = null;
        if(formScope.equals(CaDSRConstants.SESSION_SCOPE))
        {
          form = (Form)pageContext.getSession().getAttribute(formId);
        }
        else if(formScope.equals(CaDSRConstants.PAGE_SCOPE))
        {
          form = (Form)pageContext.getAttribute(formId);
        }
        if(form==null)
          throw new JspException( "Secure icon: no form object in any scope ");
        Context userContext = form.getContext();
        NCIUser  nciUser =  (NCIUser)pageContext.getSession().getAttribute(USER_KEY);
        req = ( HttpServletRequest )pageContext.getRequest();
        out = pageContext.getOut();    
      String targetStr = "";
      if(target!=null)
        targetStr="target=\""+target+"\" ";        
       if(hasPrivilege(role,formType,nciUser,form))
        {
          String hrefVal=null;
          if(confirmMessageKey!=null)
            hrefVal= getConfirmMethod(getHrefUrl(req,form));
          else
            hrefVal=getHrefUrl(req,form);
            
          out.print("<a href=\""+hrefVal+"\" "+targetStr+"/><img src=\""+urlPrefix+activeImageSource+"\" border=0 alt='"+altMessage+"'></a>");
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
 
 private String getConfirmMethod(String url) throws JspException
 {
   String message = TagUtils.getInstance().message(pageContext,null,null,confirmMessageKey);
   String method = "javascript:actionConfirm('"+message+"', '"+url+"')";
   return method;
 }
  protected boolean hasPrivilege(String userRole,String currFormType, NCIUser user,Form form) throws JspException
  {
    Context userContext = form.getContext();
    
    if(userRole!=null&&currFormType!=null)
    {
     if(form.getFormType().equalsIgnoreCase(currFormType))
     {
       return true;
     }
     else
     {
       return user.hasRoleAccess(userRole,userContext);
     }
    }
    else if(currFormType==null&&userRole!=null)
    {
      return user.hasRoleAccess(role,userContext);
    }
    else if(userRole==null&&currFormType!=null)
    {
      return form.getFormType().equalsIgnoreCase(currFormType);
    }
    return false;
  }
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

  public String getParamId()
  {
    return paramId;
  }

  public void setParamId(String newParamId)
  {
    paramId = newParamId;
  }

  public String getParamProperty()
  {
    return paramProperty;
  }

  public void setParamProperty(String newParamProperty)
  {
    paramProperty = newParamProperty;
  }
  private String getHrefUrl(HttpServletRequest req,Form form) throws JspException
  {
    req.getContextPath();
    StringBuffer sb = new StringBuffer(req.getContextPath()+activeUrl);
    if(activeUrl!=null&&paramId!=null&&paramProperty!=null)
    {
      if(activeUrl.indexOf("=")!=-1)
        if(!activeUrl.endsWith("&"))
          sb.append("&");  
      sb.append(paramId+"="+getParamValue(form,paramProperty));
    
    }
    return sb.toString();
  }
  private String getParamValue(Form form,String paramProperty) throws JspException
  {
    String paramValue = null;
    try
    {
      PropertyUtils  beanPropertyUtil = new PropertyUtils();
      paramValue = (String)beanPropertyUtil.getProperty(form,paramProperty);

    }
    catch(Exception ex)
    {
      throw new JspException(ex.toString());
    }  
    return paramValue;
  }

  public String getTarget()
  {
    return target;
  }

  public void setTarget(String newTarget)
  {
    target = newTarget;
  }

  public String getFormScope()
  {
    return formScope;
  }

  public void setFormScope(String newFormScope)
  {
    formScope = newFormScope;
  }

  public String getFormType()
  {
    return formType;
  }

  public void setFormType(String newFormType)
  {
    formType = newFormType;
  }

  public String getConfirmMessageKey()
  {
    return confirmMessageKey;
  }

  public void setConfirmMessageKey(String newConfirmMessageKey)
  {
    confirmMessageKey = newConfirmMessageKey;
  }
}