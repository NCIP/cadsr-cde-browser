package gov.nih.nci.ncicb.cadsr.cdebrowser.servlets;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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