/**
 * CompareCDEAction
 *
 * This class is the Action class for selecting search screen tyep
  *
 * @release 3.0
 * @author: <a href=”mailto:jane.jiang@oracle.com”>Shaji Kakkodi</a>
 * @date: 8/16/2005
 */
package gov.nih.nci.ncicb.cadsr.cdebrowser.struts.actions;

import gov.nih.nci.ncicb.cadsr.cdebrowser.struts.common.BrowserFormConstants;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ScreenTypeAction extends BrowserBaseDispatchAction { 
  
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
  public ActionForward changeScreenType(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    DynaActionForm compareForm = (DynaActionForm) form;

    String screenType = (String) compareForm.get(BrowserFormConstants.BROWSER_SEARCH_SCREEN_TYPE);    
    
    if(screenType.equals(BrowserFormConstants.BROWSER_SCREEN_TYPE_ADVANCED))
    {
      this.setSessionObject(request,BrowserFormConstants.BROWSER_SCREEN_TYPE_ADVANCED,BrowserFormConstants.BROWSER_SCREEN_TYPE_ADVANCED,true);
    }
    else
    {
      this.removeSessionObject(request,BrowserFormConstants.BROWSER_SCREEN_TYPE_ADVANCED);
    }
    return mapping.findForward(SUCCESS);
  }   
  
  public ActionForward changeSearchScopeToSearchResults(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      this.setSessionObject(request, BrowserFormConstants.BROWSER_SEARCH_SCOPE, BrowserFormConstants.BROWSER_SEARCH_SCOPE_SEARCHRESULTS,true);
      DynaActionForm searchForm = (DynaActionForm) form;
      String baseQuery = (String) searchForm.get("baseQuery");    
      String searchMode = request.getParameter("jspNameSearchMode");
      String searchType = request.getParameter("jspBasicSearchType");
      String searchStr = request.getParameter("jspSimpleKeyword");
      
      String searchCrumb = "Search Criteria>>"+ searchMode + " (" + searchType + "=" + searchStr + ")";
      this.setSessionObject(request, "searchCrumb", searchCrumb, true);

      this.setSessionObject(request,"baseQuery", baseQuery,true);
      return mapping.findForward(SUCCESS);
    
      }

}
