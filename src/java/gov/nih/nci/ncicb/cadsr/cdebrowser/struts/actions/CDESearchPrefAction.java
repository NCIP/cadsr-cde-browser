/**
 * CompareCDEAction
 *
 * This class is the Action class for comparing CDEs side by side
  *
 * @release 3.0
 * @author: <a href=”mailto:jane.jiang@oracle.com”>Shaji Kakkodi</a>
 * @date: 2/16/2005
 */

package gov.nih.nci.ncicb.cadsr.cdebrowser.struts.actions;

import gov.nih.nci.ncicb.cadsr.cdebrowser.CDECompareList;
import gov.nih.nci.ncicb.cadsr.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;

import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;




import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.Service;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


public class CDESearchPrefAction
 extends BrowserBaseDispatchAction {



 /**
 *
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
 public ActionForward gotoCDESearchPref(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws IOException, ServletException {
  DynaActionForm prefForm = (DynaActionForm)form;
  
  DataElementSearchBean searchBean  = (DataElementSearchBean)getInfoObject(request,"desb");
  
  if(searchBean.isExcludeTestContext())
    {
      prefForm.set("excludeTestContext","true");
    }
  else
   {
     prefForm.set("excludeTestContext","false");
   }
  
  
   return mapping.findForward("prefPage");
 }

 /**
  *
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
 public ActionForward saveCDESearchPref(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                               HttpServletResponse response) throws IOException, ServletException {
  DynaActionForm prefForm = (DynaActionForm)form;
  
  String[] aslNames = request.getParameterValues("jspStatus");
  String[] regStatuses = request.getParameterValues("regStatus");
  String excludeTestContext = (String)prefForm.get("excludeTestContext");

  
  String[] emptyArr = {""};
  DataElementSearchBean searchBean  = (DataElementSearchBean)getInfoObject(request,"desb");
  if(aslNames==null)aslNames = emptyArr;
  if(regStatuses==null)regStatuses = emptyArr;
  

  searchBean.setAslNameExcludeList(aslNames);    
  searchBean.setRegStatusExcludeList(regStatuses);   

 
 if(excludeTestContext.equals("true"))
 {
   searchBean.setExcludeTestContext(true);
   prefForm.set("excludeTestContext","true");
 }
 else
 {
   searchBean.setExcludeTestContext(false);
   prefForm.set("excludeTestContext","false");
 }
  setSessionObject(request,TREE_REFRESH_INDICATOR,YES,true);
  
  try{
    searchBean.resetLOVList();
  }
  catch(Exception exp)
  {
    //Add message later
    saveError("cadsr.cdebrowser.cde.search.pref.save.error", request);
    return mapping.findForward("prefPage");
    
  }
  saveMessage("cadsr.cdebrowser.cde.search.pref.save.success", request);
  return mapping.findForward("prefPage");
 }

 /**
*
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
 public ActionForward cancelCDESearchPref(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException, ServletException {
                                     
  setSessionObject(request,TREE_REFRESH_INDICATOR,YES,true);
  return mapping.findForward("searchPage");
 }


   /**
    * Retrive Object from MVC Frame works info table 
    */
    private Object getInfoObject(HttpServletRequest request,String aKey)
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
    
    private Service getService( HttpServletRequest request)
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
    private boolean isEmptyString(String[] arr)
    {
      
      if(arr.length==1)
      {
        if(arr[0].equals(""))
          return true;
        else
          return false;
      }
      return false;
    }
}
