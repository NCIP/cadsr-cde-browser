package gov.nih.nci.ncicb.cadsr.jsp.tag.handler;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.util.ContextUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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

/**
 * This tag put a attribute(key) in the page context with value YES or NO 
 * depending on the user have specified role in the context.
 */
public class CheckAccess extends TagSupport implements CaDSRConstants,FormConstants
{

  private String role;
  private String contextName;
  private String key;

  public CheckAccess(){}

  public int doStartTag() throws javax.servlet.jsp.JspException {
    HttpServletRequest  req;
    JspWriter out;
     try {
        req = ( HttpServletRequest )pageContext.getRequest();
        out = pageContext.getOut();
        NCIUser user = (NCIUser)pageContext.getSession().getAttribute(this.USER_KEY);
        Collection contexts = (Collection)pageContext.getSession().getAttribute(this.ALL_CONTEXTS);
        
        if(user!=null)
         {
           Context currContext = ContextUtils.getContextByName(contexts,contextName);     
           if(currContext!=null&&user.hasRoleAccess(role,currContext))
           {
             pageContext.setAttribute(key,YES);
           }
           else
            pageContext.setAttribute(key,NO);
         }
        
      } catch(Exception ioe ) {
          throw new JspException( "I/O Error : " + ioe.getMessage() );
      }//end try/catch
      return Tag.SKIP_BODY;

    }//end doStartTag()  

  public String getContextName()
  {
    return contextName;
  }

  public void setContextName(String newContextName)
  {
    contextName = newContextName;
  }

  public String getKey()
  {
    return key;
  }

  public void setKey(String newKey)
  {
    key = newKey;
  }

  public String getRole()
  {
    return role;
  }

  public void setRole(String newRole)
  {
    role = newRole;
  }

}