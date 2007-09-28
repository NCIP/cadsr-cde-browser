package gov.nih.nci.ncicb.cadsr.cdebrowser.servlets;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormElementLocker;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.LockingService;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;

import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
     * This Filter is used to make sure the user has priviledge to edit the form and its form elements.
     */
public class EditFormFilter implements javax.servlet.Filter{
        private FilterConfig filterConfig;
        protected static Log log = LogFactory.getLog(EditFormFilter.class.getName());

        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, javax.servlet.ServletException
        {
          String forwardToJSP = filterConfig.getInitParameter("formDetailPage");
          String expiredSessionJSP = filterConfig.getInitParameter("expiredSessionJSP");
          HttpServletRequest req = (HttpServletRequest)request;
          HttpSession userSession = req.getSession(false);

          //in case session already expired
          if (userSession == null){
              ((HttpServletResponse)response).sendRedirect(req.getContextPath()+ expiredSessionJSP);
              return;
          }

          String formIdSeq = null;
          Object obj = request.getParameter(FormConstants.FORM_ID_SEQ);
          if (obj!=null){
              formIdSeq = (String)obj;
          }else{
            if(userSession != null){
                obj = userSession.getAttribute(FormConstants.CRF);
                if(obj!=null){
                    Form crf = (Form)obj;
                    formIdSeq = crf.getIdseq();
                }
            }
          }
         ServletContext sc = userSession.getServletContext();
         ApplicationServiceLocator loc = getApplicationServiceLocator(sc);
         LockingService service = loc.findLockingService();
         if (!service.isFormLocked(formIdSeq, ((HttpServletRequest)request).getRemoteUser())){
            //good, just continue;
            chain.doFilter(request, response);
            //System.out.println("not locked. formIdseq=" + formIdSeq);
            return;
         }
         try{
             FormElementLocker locker = getApplicationServiceLocator(sc).findLockingService().getFormLocker(formIdSeq);
             //System.out.println("locked by user ID=" + ((HttpServletRequest)request).getRemoteUser() + "at " + locker.getTimeStamp());

             String email = locker.getNciUser().getEmailAddress();
             String phone = locker.getNciUser().getPhoneNumber();
             Date since = locker.getTimeStamp();
             String thatSessionId = locker.getSessionId();
             
             String contact;
             if (email!=null && email.length()>0){
                 contact = "Email: "  + email;
             }else{
                 if (phone!=null && phone.length()>0){
                    contact = "Phone: " + locker.getNciUser().getPhoneNumber();
                 }   
                 else{
                    contact = "Email/Phone not available";                    
                    }
             }
             
             saveMessage("cadsr.formbuilder.form.locked", (HttpServletRequest)request,
                   locker.getNciUser().getUsername(), contact);
             if (log.isDebugEnabled()){
                 StringBuffer sb = new StringBuffer();
                 sb.append("Form ").append(formIdSeq).append(" is locked by user ")
                    .append(locker.getNciUser().getUsername()).append(" since ")
                    .append(since.toString()).append(" Session id=")
                    .append(thatSessionId);
                 log.debug(sb.toString());
             }
             request.setAttribute(FormConstants.FORM_ID_SEQ, formIdSeq);
             RequestDispatcher dispatcher = filterConfig.getServletContext().getRequestDispatcher(forwardToJSP);
             dispatcher.forward(request,response);
             return;
        }catch (Exception e){
            log.error("Error in execute ", e);
            throw new javax.servlet.ServletException(e);
        }
    }


    public void init(final FilterConfig filterConfig){
        this.filterConfig = filterConfig;
    }

    public void destroy(){
        filterConfig = null;
    }

    protected void saveMessage(
      String key,
      HttpServletRequest request, String arg0, String arg1) {
      if (key != null) {
        ActionMessage message = new ActionMessage(key,arg0, arg1);
        ActionMessages messages = null;
        messages = (ActionMessages)request.getAttribute(Globals.MESSAGE_KEY);
        if(messages==null)
          messages = new ActionMessages();

        messages.add(messages.GLOBAL_MESSAGE, message);
        request.setAttribute(Globals.MESSAGE_KEY, messages);
      }
    }

    protected ApplicationServiceLocator getApplicationServiceLocator(ServletContext sc)
      throws ServiceLocatorException {
      ApplicationServiceLocator appServiceLocator =
        (ApplicationServiceLocator) sc.getAttribute(
          ApplicationServiceLocator.APPLICATION_SERVICE_LOCATOR_CLASS_KEY);
      if(appServiceLocator==null)
        throw new ServiceLocatorException("Could no find ApplicationServiceLocator with key ="+ ApplicationServiceLocator.APPLICATION_SERVICE_LOCATOR_CLASS_KEY);
      return appServiceLocator;
    }
}
