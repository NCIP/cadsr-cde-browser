package gov.nih.nci.ncicb.cadsr.cdebrowser.servlets;
import com.evermind.security.RoleManager;
import com.evermind.security.User;

import com.evermind.security.UserManager;
import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.security.oc4j.DBUserManager;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;
import java.io.OutputStream;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import oracle.cle.process.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.util.SessionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This Filter is used to make sure there is valid service insession for cdebrowser
 */
public class SessionFilter implements javax.servlet.Filter
{
    private FilterConfig filterConfig;
    protected static Log log = LogFactory.getLog(SessionFilter.class.getName());
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, javax.servlet.ServletException
    {
      String controllerName = filterConfig.getInitParameter("controllerName");
      String expiredSessionJSP = filterConfig.getInitParameter("expiredSessionJSP");      
      HttpServletRequest req = (HttpServletRequest)request;
      Object param = request.getParameter("FirstTimer"); 
      if(param==null)
      {
        HttpSession userSession = req.getSession(false);
        Object obj = null;
        if(userSession!=null)    
          obj = userSession.getAttribute(ProcessConstants.SERVICENAME);  
        if(obj==null)
          {
            RequestDispatcher dispatcher = filterConfig.getServletContext().getRequestDispatcher(expiredSessionJSP);
            dispatcher.forward(request,response);
            return;
          }
      }
      chain.doFilter(request, response);
    }

 
    public void init(final FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    public void destroy()
    {
        filterConfig = null;
    }
}