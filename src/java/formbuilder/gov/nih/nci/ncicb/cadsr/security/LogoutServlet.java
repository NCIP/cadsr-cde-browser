package gov.nih.nci.ncicb.cadsr.security;
import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet 
{
  public LogoutServlet()  
  {
  }

  protected void doGet(HttpServletRequest p0, HttpServletResponse p1) throws ServletException, IOException
  {
    // TODO:  Override this javax.servlet.http.HttpServlet method
    doPost(p0, p1);    
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String logoutHome = request.getParameter(CaDSRConstants.LOGOUT_URL);
    if(logoutHome!=null)
    {
      HttpSession session = request.getSession();
      if(session!=null)
      {
        Set keys = (Set)session.getAttribute(CaDSRConstants.GLOBAL_SESSION_KEYS);
        Map objMap = new HashMap();
        if(keys!=null)
        {
          Iterator it  = keys.iterator();
          while(it.hasNext())
          {
            String key = (String)it.next();
            Object obj = session.getAttribute(key);
            objMap.put(obj,key);
          }
        }
        //cleanSession(session);
       session.invalidate();
       session = request.getSession(true);
        if(keys!=null)
        {
          Iterator keyIt  = keys.iterator();
          while(keyIt.hasNext())
          {
            String key = (String)keyIt.next();
            Object obj = objMap.get(key);
            session.setAttribute(key,obj);
          }
        }
      }
      else
      {
        session = request.getSession(true); 
      }
      RequestDispatcher dispacher = request.getRequestDispatcher("/"+logoutHome);
      dispacher.forward(request,response);
      
    }
    else
      {
        request.getSession().invalidate();
        response.getWriter().println("User Logged out");
        response.getWriter().flush();
      }
  }
 private void cleanSession(HttpSession session)
 {
    Enumeration  keys = session.getAttributeNames();
    for (; keys.hasMoreElements() ;) {
         String key = (String)keys.nextElement();
         session.removeAttribute(key);
     }
 }
}