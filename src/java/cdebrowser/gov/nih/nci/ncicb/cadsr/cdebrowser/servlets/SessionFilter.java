package gov.nih.nci.ncicb.cadsr.cdebrowser.servlets;


import java.io.OutputStream;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import oracle.cle.process.ProcessConstants;

public class SessionFilter implements javax.servlet.Filter
{
    private FilterConfig filterConfig;

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