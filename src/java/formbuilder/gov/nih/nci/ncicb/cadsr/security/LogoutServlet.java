package gov.nih.nci.ncicb.cadsr.security;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

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
    String logoutHome = this.getServletContext().getInitParameter("LogoutHome");
    if(logoutHome!=null)
    {
      request.getSession().invalidate();
      response.sendRedirect(logoutHome);
    }
    else
      {
        response.getWriter().println("<b><h3>User Logged out</h3></b>");
        response.getWriter().flush();
      }
  }
 
}