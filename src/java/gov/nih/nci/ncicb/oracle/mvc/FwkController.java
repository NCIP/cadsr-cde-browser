package gov.nih.nci.ncicb.oracle.mvc;

// java imports
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
// cle imports
import oracle.cle.exception.CLEException;
import oracle.cle.exception.CLEExceptionConstants;
import oracle.cle.exception.CLERuntimeException;
import oracle.cle.util.statemachine.TransitionTable;
import oracle.cle.util.CLEUtil;
import oracle.cle.util.SimpleDate;
import oracle.cle.process.Child;
import oracle.cle.process.ServiceController;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.ResultTypes;
import oracle.cle.process.Service;
import oracle.cle.process.Parent;
import oracle.cle.process.ProcessConstants;
import oracle.cle.process.ProcessInfo;
import oracle.cle.resource.User;
import oracle.cle.persistence.UserImpl;
import oracle.cle.persistence.ConnectionManager;
// clex imports
import oracle.clex.util.ContentTypes;
import oracle.clex.util.MultipartRequest;
import oracle.clex.process.PageConstants;
import oracle.clex.util.PartnerSSOEnabler;
import oracle.clex.process.controller.HttpServletController;


public class FwkController extends HttpServletController{

  public FwkController()
  {
    super();
  } 
  
  /**
   * Overloads the init function of the super.
   *
   * Initializes the servlet, and retrieves parameters for the
   * startup.
   */
  public void init(ServletConfig sc) throws ServletException
  {
    super.init(sc);
 
  } // end init


 
  protected Service preServiceStart(HttpServletRequest request,
                                    HttpServletResponse response)
    throws PreServiceException, Exception
  {
    HttpSession session = null;
    User userObject = handleOSSOLogin(request, response);

    if(getSSOEnabler()!=null && userObject==null)
    {
      return null;
    } // end if

    try
    {
      session = request.getSession(true);
    } // end try
    catch(Exception e)
    {
      CLEException cle= new CLEException(CLEException.CLE224, e);
      System.err.println(e.getMessage());
      e.printStackTrace();
    } // end catch

    Service service = null;
    ProcessInfo user = null;
    Vector globalInfos = new Vector();
    //HttpServletRequest httpRequest;
    //HttpServletResponse httpResponse;
    MultipartRequest multipartRequest = null;
    String previousServiceString = null;

    // Set the IS_SESSION_RESET flag in the session so we can reuse the object
    Boolean isSessionReset = new Boolean(false);
    if (session.isNew())
    {
      setSessionAttribute(session, IS_SESSION_RESET, isSessionReset);
      debug("Session IS NEW");
    }
    else
    {
      isSessionReset =
        (Boolean)getSessionAttribute(session, IS_SESSION_RESET);
      debug("Session IS NOT NEW");
    } // end else
    if(isSessionReset==null)
    {
      isSessionReset = new Boolean(false);
    } // end if

    // if we are in a new Service but have the same session,
    // invalidate the session and get a new one.  The global infos
    // from the old service are returned as a Vector.
    previousServiceString =
      (String)getSessionAttribute(session, ProcessConstants.SERVICENAME);
    String currentServiceName = getName();
    
    //09/29/2001(MH)Added to null checks for previousServiceString
    // and currentServiceName because you don't want to do this
    // if the service is the first service run in the session.
    // If it is the first one run, then the previousServiceName will be
    // null and not checking that would cause a NullPointerException
    if(previousServiceString!=null
       &&currentServiceName!=null
       &&!(previousServiceString.equals(currentServiceName))
       || previousServiceString==null && currentServiceName==null||getPageId(request, multipartRequest)==null)
    {
      if((previousServiceString==null && currentServiceName==null)||getPageId(request, multipartRequest)==null )
      {
        setSessionAttribute(session, IS_SESSION_RESET, Boolean.TRUE);
      } // end if
      globalInfos = handleNewService(session);
      //----------------------------------------------------------------------------
      
    }

    // Handle any Multipart Requests
    multipartRequest = handleMultipartRequest(request);
    setSessionAttribute(session, MULTIPARTREQUEST, multipartRequest);

    // Handle LOGOFF request and redirects to a page
    String logoff =
      getValue(ProcessConstants.LOGOFF, request, multipartRequest);

    if(logoff != null)
    {
      String logoffLocation = getLoggedOffPageLocation();

      if (logoffLocation == null)
      {
        logoffLocation = logoff;
      } // end if

      // Make sure that the URL is valid
      try
      {
        new java.net.URL(logoffLocation);
      } // end try
      catch (java.net.MalformedURLException e)
      {
        System.err.println("Logging off '" + getName() + "' service -- redirecting to server root");
        System.err.println("Please specify a valid URL and protocol in query string");
        System.err.println(" ie: http://<server>:<port>?logoff=http://<valid url>");
        logoffLocation = "/";
      } // end try

      response.setStatus(response.SC_MOVED_TEMPORARILY);
      response.setHeader("Location", logoffLocation);

      // Remove the SSO Login application cookie
      PartnerSSOEnabler pSSOEnabler = getSSOEnabler();
      if(pSSOEnabler!=null)
      {
        pSSOEnabler.removeAppCookie(response);
      } // end if

      session.invalidate();
      return null;
    } // end LOGOFF check

    // If the PAGEID is null or if the RESTART option is in effect,
    //  then invalidate the session and start a new one.
    if(getPageId(request, multipartRequest)==null
       ||
       getValue(ProcessConstants.RESTART, request, multipartRequest)!=null)
    {
      Service theService = (Service)(getSessionAttribute(session, previousServiceString
                             + ".service"));
      Hashtable theInfoTable = null;

      if(theService!=null)
        theInfoTable = theService.getInfoTable();

      if(theInfoTable!=null)
      {
        if(user==null)
        {
          user =
            (ProcessInfo)(theInfoTable.get(ProcessConstants.USER));
        } // end if
      } // end if

      // Clean out the session objects
      initSessionObjects(session);
    } // end if

    isSessionReset =
      (Boolean)getSessionAttribute(session, IS_SESSION_RESET);
    if (isSessionReset.booleanValue())
    {
      debug("Session IS New");

      service = getConcreteService();

      // 10/16/2000
      // this try/catch is being added by Marc and Chad to catch
      // a null pointer exception caused by a null service.
      // If a null pointer exception occurs
      // then an error message will be passed back via the
      // response stream and will appear on the browser.
      try
      {
        // Marc Horowitz 01/25/2001
        // Added set the serviceName attribute when the service
        // is loaded for the first time
        setName(service.getName());
        service.setParent((Parent)this);
        // We need to do this or the processResults method will fail
        service.setService(service);
      } // end try
      catch(Exception ex)
      {
        if(DEBUG)
        {
          String logLocation = this.getWebServerLogLocation();
          StringBuffer buffer = new StringBuffer();

          buffer.append("<html><head>");
          buffer.append("<title> Service Error</title>");
          buffer.append("</head>");
          buffer.append("<body><B>Your service has an error.  ");
          buffer.append("Please see the appropriate log ");
          buffer.append("file for your web server.  </b><br>");
          if(logLocation!=null||logLocation.equals(""))
          {
            buffer.append("<a href=\"file:///"+logLocation);
            buffer.append("\">click here for log file</a>");
          } // end if
          buffer.append("</body>");
          buffer.append("</html>");
          response.getWriter().print(buffer.toString());
          response.getWriter().flush();
        } // end if(DEBUG)
        else
        {
          response.getWriter().print(
          "<html><head>"+
          "<title> Application Error</title>"+
          "</head>"+
          "<body><B>An error occured.  Contact System Adminstrator.</b><br>"+
          "</body>"+
          "</html>");
          response.getWriter().flush();
        } // end else

        CLEException cle =
          new CLEException(CLEException.CLE227,ex);
      } //end catch
    } // end if
    else
    {
      debug("Session NOT New");
      response.setContentType(ContentTypes.TEXTHTML);//JSS - Need to resolve
                                                     // what's going on here!!!

      // unpersist the infoTablePool
      Hashtable infoTablePool = new Hashtable();

      try
      {
        infoTablePool =
          (Hashtable)getSessionAttribute(session, INFOTABLEPOOL);

        // unpersist the service.  Check to see if the service is null,
        // if it is, then call getConcreteService which sets the service
        // variable.  If not use the service variable and the name that
        // is associated to the service variable.
        if(service==null)
        {
          service =
            (Service)getSessionAttribute(session, getName()
                                          +".service");
        } // end if
        else
        {
          service = (Service)getSessionAttribute(session, service.getName()+".service");
        } // end else
      } // end try
      catch (Exception e)
      {
        CLEException cle = new CLEException(CLEException.CLE227, e);
        System.err.println("Exception getting values from session.getValue()");
        e.printStackTrace();
      } // end catch

      // Get the associated DisplayGroup for the current page
      String pageId = getPageId(request, multipartRequest);
      String currentPageId = "";

      try
      {
        try
        {
          currentPageId =
            service.getStringInfo(CURRENTPAGEID);
        } // end try
        catch(Exception currPageIdException)
        {
          currentPageId = null;
        } // end catch

        if(currentPageId==null)
        {
          currentPageId = "";
        } // end if

        debug("We should be at: " + currentPageId);
        debug("We are at: " + pageId);
        // If we are not on the page we are supposed to be on, then look up
        //  the requisite info table for the specified id.
        if(pageId!=null && currentPageId!=null &&
           pageId.compareTo(currentPageId)!=0)
        {

          // bug 2395934 - removed the line infoTablePool = new Hashtable()
          // documented here because Luis said that it was here to
          // possible fix another problem, but do not know what.  
          debug("" + infoTablePool.size());

          Hashtable tempHT = (Hashtable)(infoTablePool.get(pageId));
          Hashtable replacementInfoTable = this.cloneInfoTable(tempHT);

          if(replacementInfoTable!=null)
          {
            service.setInfoTable(replacementInfoTable);
          } // end if
          else
          {
            System.err.println("Controller Error: InfoTable Not Found For " + PAGEID + " " + pageId);
            Enumeration keys = infoTablePool.keys();
            System.err.print("Available Groups are: [");

            while(keys.hasMoreElements())
            {
              System.err.print(keys.nextElement() + " ");
            } // end while
            System.err.println("]");
          } // end else
        } // end if
      } // end try
      catch(Exception e)
      {
        CLEException cle =
          new CLEException(CLEException.CLE227, e);
        System.err.println("Exception in: " + getClass().getName());
        e.printStackTrace();
      } // end catch
      ////////////// end retrieveState
    } // end else
    
    loadServiceInfo(service, request, response);

    if(user==null && userObject!=null)
    {
      // Create a new ProcessInfo for the new User object to put into
      // the service's infotable later on... now we'll put it into the
      // session
      user =
        new ProcessInfo(ProcessConstants.USER,
                        "User",
                        "The Application's User",
                        service,
                        userObject);
      // Set the current User ProcessInfo in the session
      setSessionAttribute(session, ProcessConstants.USER, user);
    } // end if
    if(user!=null)
    {
      service.putInfo(user);
    } // end if

    // Put all the global infos back into the service.  If the Vector
    // is empty then this shouldn't go through anyhow (no biggy).  That should
    // be the case when hitting the same service over and over.  This loop
    // SHOULD fire, however, when there are global infos being passed between
    // subsequent different services.
 
    for(int k=0; k<globalInfos.size(); k++)
    {
      ProcessInfo pi = (ProcessInfo)globalInfos.elementAt(k);
      
      if(globalInfos.elementAt(k)!=null)
      {
        service.putInfo(((ProcessInfo)globalInfos.elementAt(k)));
 
      } // end if
    } // end for
  
    return service;
  } // end preServiceStart



  /**
   * if we are in a new Service but have the same session,
   *  invalidate the session and get a new one.  We might eventually
   *  want to do something nicer than this like clean up the
   *  session and reuse it so we don't have to get another
   *  cookie for each servlet we hit.
   *  <br/><br/>
   * The Vector returned contains all the global infos including the
   * User
   *
   * @author Luis Amat (9/04/2001)
   * @version 1.0 (Refactored from preServiceStart)
   */
  protected Vector handleNewService(HttpSession session)
  {
    // This is the Collection of GlobalInfos and User to be returned.
    Vector globalInfos = new Vector();

    // Use length > 3 because after a session a reset, there is always
    // three values in there (IS_SESSION_RESET and the USER)
    Boolean isSessionReset = (Boolean)getSessionAttribute(session, IS_SESSION_RESET);
    if(isSessionReset == null)
    {
      isSessionReset = new Boolean(true);
    }
      String previousServiceString =
        (String)getSessionAttribute(session, ProcessConstants.SERVICENAME);
      String currentServiceName = getName();
      //09/29/2001(MH)Added to null checks for previousServiceString
      // and currentServiceName because you don't want to do this
      // if the service is the first service run in the session.
      // If it is the first one run, then the previousServiceName will be
      // null and not checking that would cause a NullPointerException
      if(previousServiceString!=null)
      {
        Service theService = (Service)(
          getSessionAttribute(session, previousServiceString + ".service"));
        Hashtable theInfoTable = null;

        if(theService!=null)
        {
          theInfoTable = theService.getInfoTable();
        }
        if(theInfoTable!=null)
        {
          // Get the current User ProcessInfo from the session
          ProcessInfo user =
            (ProcessInfo)getSessionAttribute(session, ProcessConstants.USER);

          if(user==null)
          {
            // Get the ProcessConstants.USER for transfer to the next service
            user =
              (ProcessInfo)(theInfoTable.get(ProcessConstants.USER));
            //Adding user object to the global infos MH/JSS 10/01/2001
            if(user!=null)
            {
              globalInfos.addElement(user);
            }
            // Get the previous service's global infos for transfer
            // to the next service
            Vector globalInfoNames = theService.getGlobalInfoNames();
            String globalInfoName = "";
            ProcessInfo globalInfo = null;
            for(int n=0; n<globalInfoNames.size(); n++)
            {
              try
              {
                if(globalInfoNames.elementAt(n)!=null)
                {
                  globalInfoName = (String)globalInfoNames.elementAt(n);
                  globalInfo =
                    (ProcessInfo)
                      (theInfoTable.get(globalInfoName));
                  globalInfos.addElement(globalInfo);
                } // end if
              } // end try
              catch(Exception globalInfoException)
              {
                 CLEException cle=
                   new CLEException
                     (CLEException.CLE225,
                      "Exception getting global info named: '"
                       + globalInfoName + "' for transfer to "
                       + "subsequent service",
                      globalInfoException);
                System.err.println("Exception getting global info named: '"
                                   + globalInfoName + "' for transfer to "
                                   + "subsequent service");
                globalInfoException.printStackTrace();
              } // end catch
            } // end for
          }
        }

        // Clean up the session objects
        initSessionObjects(session);
      }
    return globalInfos;
  } // end handleNewService

   /**
   * Returns the requested attribute from the session object.  Backward
   *  compatible to Servlet 2.0 (tries <code>getValue(attributeName)</code>
   *  if <code>getAttribute(attributeName)</code> method
   *  throws <code>NoSuchMethodError</code>.
   *
   * @author Rupa Keskar
   * @version 1.0 (11/26/2001)
   */
  private Object getSessionAttribute(HttpSession session, String attributeName)
  {
    Object attributeValue = null;
    try
    {
      attributeValue = session.getAttribute(attributeName);
    } // end try
    catch(NoSuchMethodError mnfe)
    {
      attributeValue = session.getValue(attributeName);
    } // end catch
    if (attributeValue instanceof String)
    {
      String value = (String) attributeValue;
      // If it is an empty value, we convert it back to null,
      // otherwise we will get classCastException
      if (value.equals(new String()))
      {
        attributeValue = null;
      } // end if
    } // end if

    return attributeValue;
  } // end getSessionAttribute

  /**
   * Sets the requested attribute in the session object
   *
   * @author Rupa Keskar
   * @version 1.1 (Luis Amat 2/21/2002) Doing null check on value.
   * @version 1.0 (11/26/2001)
   */
  private void setSessionAttribute(HttpSession session,
                                   String attributeName,
                                   Object attributeValue)
  {
    if(attributeValue == null)
    {
      attributeValue = "";
    } // end if
    
    try
    {
      session.setAttribute(attributeName, attributeValue);
    } // end try
    catch(NoSuchMethodError mnfe)
    {
      // If AttributeValue is null, put an empty String otherwise
      // we get NullPointerException
      if (attributeValue == null)
      {
        attributeValue = new String();
      } // end if
      session.putValue(attributeName, attributeValue);
    } // end catch
    catch(Exception e)
    {
      if(session!=null)
      {
        System.err.println("" + CLEUtil.getUnqualifiedClassName(this) 
          +": unknown occured found while performing session.setAttribute." 
          + " Trying session.putValue\n");
        // If AttributeValue is null, put an empty String otherwise
        // we get NullPointerException
        if (attributeValue == null)
        {
          attributeValue = new String();
        } // end if
        session.putValue(attributeName, attributeValue);
      } // end if session!=null
      else
      {
        CLEException ce = new CLEException(CLEExceptionConstants.CLE224, e);
        throw new CLERuntimeException(ce);
      } // end else
    } // end catch    
  } // end setSessionAttribute
 
 class PreServiceException extends CLEException {}
class PostServiceException extends CLEException {}
}


