package gov.nih.nci.ncicb.cadsr.ocbrowser.struts.actions;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.dto.AttachmentTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.ocbrowser.service.OCBrowserService;
import gov.nih.nci.ncicb.cadsr.ocbrowser.struts.common.OCBrowserFormConstants;
import gov.nih.nci.ncicb.cadsr.ocbrowser.struts.common.OCBrowserNavigationConstants;
import gov.nih.nci.ncicb.cadsr.resource.Attachment;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;


public class ObjectClassAction extends OCBrowserBaseDispatchAction
  implements OCBrowserFormConstants,OCBrowserNavigationConstants
{
  protected static Log log = LogFactory.getLog(ObjectClassAction.class.getName());
  public ObjectClassAction()
  {
  }

  /**
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
  public ActionForward getObjectClass(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {


    DynaActionForm dynaForm = (DynaActionForm) form;
    String obIdSeq = (String) dynaForm.get(OC_IDSEQ);
    String resetCrumbs = (String) dynaForm.get(this.RESET_CRUMBS);

    if (log.isDebugEnabled()) {
    log.info("ocr for With object class " + obIdSeq);
    }


    try {

      OCBrowserService service = this.getApplicationServiceLocator().findOCBrowserService();
      ObjectClass objClass = service.getObjectClass(obIdSeq);
      setSessionObject(request,OBJECT_CLASS,objClass,true);

      List superClasses = service.getInheritenceRelationships(objClass);
      setSessionObject(request,SUPER_OBJECT_CLASSES,superClasses,true);

      //Reset OCR Navigation bread crumbs if resetCrumbs is not null
      if(resetCrumbs!=null&&!resetCrumbs.equals(""))
        setSessionObject(request,OCR_NAVIGATION_BEAN,null);
    }
    catch (ServiceLocatorException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on getObjectClass obid= "+obIdSeq );
      }

      return mapping.findForward(FAILURE);
    }

    return mapping.findForward(SUCCESS);
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
   public ActionForward viewReferenceDocAttchment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                  HttpServletResponse response) throws IOException, ServletException {
    OutputStream out = null;

    InputStream is = null;
    out = response.getOutputStream();
    String attachmentName = request.getParameter(FormConstants.REFERENCE_DOC_ATTACHMENT_NAME);
    response.addHeader("Content-Disposition", "inline;filename=" + attachmentName);
    response.addHeader("Pragma", "cache");
    response.addHeader("Cache-Control", "private");
    response.addHeader("Expires", "0");

    // first find out if the attachment is new and saved in the session

    Map attMap = (Map)getSessionObject(request, FormConstants.REFDOC_ATTACHMENT_MAP);
    Attachment attachment = getAttachmentFromSession(attMap, attachmentName);

    if (attachment != null) {
     FormFile attFile = (FormFile)attMap.get(attachment);

     is = attFile.getInputStream();
     response.setContentType(attachment.getMimeType());
    } else {
     Blob theBlob = null;

     Connection conn = null;
     ResultSet rs = null;
     PreparedStatement ps = null;
     try {
      DBUtil dbUtil = new DBUtil();

      //String dsName = CDEBrowserParams.getInstance("cdebrowser").getSbrDSN();
      dbUtil.getOracleConnectionFromContainer();

      String sqlStmt = "SELECT blob_content, mime_type, doc_size from reference_blobs where name = ?";
      log.info(sqlStmt);
      conn = dbUtil.getConnection();
      ps = conn.prepareStatement(sqlStmt);
      ps.setString(1, attachmentName);
      rs = ps.executeQuery();
      boolean exists = false;

      if (rs.next()) {
       exists = true;

       String mimeType = rs.getString(2);
   //    (mimeType);
       
       response.setContentType(mimeType);
       //theBlob = ((OracleResultSet)rs).getBLOB(1);
       theBlob = rs.getBlob(1);
       is = theBlob.getBinaryStream();
       response.setContentLength(rs.getInt(3));
       response.setBufferSize(4*1024);

        //Writing to the OutputStream
        if (is != null) {
         byte [] buf = new byte[4 * 1024]; // 4K buffer
      
         int bytesRead;
      
         while ((bytesRead = is.read(buf)) != -1) {
          out.write(buf, 0, bytesRead);
         }
        }
       response.setStatus(HttpServletResponse.SC_OK);
      

      }
     } catch (Exception ex) {
      log.error("Exception Caught:", ex);
     } finally {
      try {
        if (is != null)
         is.close();
         
        if (out != null) 
         out.close();
      
       try
       {
         if(ps!=null)
          ps.close();
       }
       catch (Exception e)
       {       
       }
       try
       {
         if(rs!=null)
          rs.close();
       }
       catch (Exception e)
       {       
       }     
       if (conn != null)
        conn.close();

      //if (db != null) db.closeDB();
      } catch (Exception ex) {
       log.error("Exception Caught cleaning up:", ex);
      } finally { }
     }
    }

    return null;
   }  
   
   private Attachment getAttachmentFromSession(Map attMap, String fileName) {
    if (attMap == null)
     return null;

    Iterator iter = attMap.keySet().iterator();

    while (iter.hasNext()) {
     Attachment attachment = (AttachmentTransferObject)iter.next();

     if (attachment.getName().equals(fileName))
      return attachment;
    }

    return null;
   }
   
}