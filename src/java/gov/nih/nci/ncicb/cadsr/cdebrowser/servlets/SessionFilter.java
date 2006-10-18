package gov.nih.nci.ncicb.cadsr.cdebrowser.servlets;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.cle.process.ProcessConstants;

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
      String expiredSessionJSP = filterConfig.getInitParameter("expiredSessionJSP");      
      String controller = filterConfig.getInitParameter("controllerName");   
      HttpServletRequest req = (HttpServletRequest)request;
      Object param = request.getParameter("FirstTimer"); 
      HttpSession userSession = req.getSession(false);
      
      if(param==null)
      {
        if(userSession==null)
          {
             ((HttpServletResponse)response).sendRedirect(req.getContextPath()+ expiredSessionJSP);
            return;
          }
      }      
      
      if ("formBuilder".equals(controller)){
          if (userSession.getAttribute("Initialized") == null){
              ((HttpServletResponse)response).sendRedirect(req.getContextPath()+ expiredSessionJSP);
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