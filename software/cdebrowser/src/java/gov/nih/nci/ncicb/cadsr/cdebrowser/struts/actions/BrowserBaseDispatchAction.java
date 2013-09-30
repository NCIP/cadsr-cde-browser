/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.cdebrowser.struts.actions;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.struts.common.BrowserFormConstants;
import gov.nih.nci.ncicb.cadsr.common.struts.common.BrowserNavigationConstants;
import gov.nih.nci.ncicb.cadsr.common.struts.common.BaseDispatchAction;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oracle.cle.process.ProcessConstants;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.Service;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BrowserBaseDispatchAction extends BaseDispatchAction implements BrowserFormConstants 
              ,BrowserNavigationConstants,CaDSRConstants
{

  /**
   * This Action forwards to the default formbuilder home.
   *
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   *
   * @return
   *
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward sendHome(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    return mapping.findForward("cdebrowserHome");
  }
  
   /**
    * Retrive Object from MVC Frame works info table 
    */
    protected Object getInfoObject(HttpServletRequest request,String aKey)
    {
      Object infoValue = null;
      ProcessInfo info = null;
      Service service = getService(request);
      if(service!=null)
        info = (ProcessInfo)service.getInfo(aKey, false);

      if (info!=null && info.isReady())
      {
         infoValue = info.getValue();
      }

      return infoValue;
    }
    
    protected Service getService( HttpServletRequest request)
    {
      String serviceName = null;
      HttpSession session = request.getSession();
      try
      {
        serviceName = (String) session.getAttribute(ProcessConstants.SERVICENAME);
      }
      catch(NoSuchMethodError mnfe)
      {
        serviceName = (String) session.getValue(ProcessConstants.SERVICENAME);
      }

      Object serviceObject = session.getAttribute(serviceName + ".service");

      return (Service)serviceObject;
    }  
}