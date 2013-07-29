/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.jsp.tag.handler;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.util.ContextUtils;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * This tag put a attribute(key) in the page context with value YES or NO 
 * depending on the user have specified role in the context.
 */
public class CheckAccess extends TagSupport implements CaDSRConstants
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
            System.out.println("user : " + user.toString()); 
           Context currContext = ContextUtils.getContextByName(contexts,contextName);     
           if(currContext!=null&&user.hasRoleAccess(role,currContext))
           {
             pageContext.setAttribute(key,YES);
           }
           else
            pageContext.setAttribute(key,NO);
         }
         //Publish Change Order
         else
          {
             System.out.println("user : null"); 
            pageContext.setAttribute(key,NO);
          }         
        
      } catch(Exception ioe ) {
    	  System.out.println(" start tag error " + ioe.getMessage());
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