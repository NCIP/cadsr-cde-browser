package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.AttachmentTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;


import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.ReferenceDocFormBean;
import gov.nih.nci.ncicb.cadsr.resource.Attachment;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import oracle.sql.BLOB;
import oracle.jdbc.OracleResultSet;

public class ReferenceDocumentAction extends FormBuilderSecureBaseDispatchAction {

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
  public ActionForward manageReferenceDocs(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    ReferenceDocFormBean refForm = (ReferenceDocFormBean)form;
    
    //refForm.reset(mapping,request);

    Form crf = (Form) getSessionObject(request, CRF);
      
    if(crf.getRefereceDocs()==null)
        crf.setReferenceDocs(getDummyRefDocs());
    else if (crf.getRefereceDocs().isEmpty())
        crf.setReferenceDocs(getDummyRefDocs());

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
  public ActionForward viewReferenceDocs(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    
    Form crf = (Form) getSessionObject(request, CRF);
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
  public ActionForward viewReferenceDocAttchment(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    Blob theBlob = null;
    InputStream is = null;
    OutputStream out = null;
    try {
      DBUtil dbUtil = new DBUtil();
      String dsName = CDEBrowserParams.getInstance("cdebrowser").getSbrDSN();
      dbUtil.getConnectionFromContainer(dsName);
      out = response.getOutputStream();
      String attachmentName = request.getParameter(FormConstants.REFERENCE_DOC_ATTACHMENT_NAME);
      response.addHeader("Content-Disposition", "attachment; filename=" + attachmentName);
      response.addHeader("Pragma", "No-cache");
      response.addHeader("Cache-Control", "no-cache");
      response.addHeader("Expires", "0");    
  
      String sqlStmt = "SELECT blob_content, mime_type from reference_blobs where name = ?";
      log.info(sqlStmt);
      PreparedStatement ps = dbUtil.getConnection().prepareStatement(sqlStmt);
      ps.setString(1,attachmentName);
      ResultSet rs = ps.executeQuery();
      boolean exists = false;
      if (rs.next()) {
        exists = true;
        String mimeType = rs.getString(2);
        response.setContentType(mimeType);
        theBlob = ((OracleResultSet)rs).getBLOB(1);
        is = theBlob.getBinaryStream();

        //Writing to the OutputStream
        byte[] buf = new byte[4 * 1024];  // 4K buffer
        int bytesRead;
        while ((bytesRead = is.read(buf)) != -1) {
          out.write(buf, 0, bytesRead);
        }
      }
    } 
    catch (Exception ex) {
      log.error("Exception Caught:", ex);
    } 
    finally {
      try {
        if (is != null) is.close();
        if (out != null) out.close();
        //if (db != null) db.closeDB();  
      } 
      catch (Exception ex) {
         log.error("Exception Caught cleaning up:", ex);
      } 
      finally {
      }
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
  public ActionForward gotoUploadDocument(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    ReferenceDocFormBean refForm = (ReferenceDocFormBean)form;
    
    String refDocId = refForm.getSelectedRefDocId();
	  return mapping.findForward("gotoUpload");
	  
    }
    
  /**
   * Create a form
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
  public ActionForward uploadDocuments(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    Form crf = (Form) getSessionObject(request, CRF);
    List refDocs = crf.getRefereceDocs();
    ReferenceDocFormBean refForm = (ReferenceDocFormBean)form;
    
    String refDocId = refForm.getSelectedRefDocId();
    ReferenceDocument refDoc = (ReferenceDocument)refDocs.get(Integer.parseInt(refDocId));
    List attachments = refDoc.getAttachments();
    
    FormFile file = refForm.getUploadedFile();
    //retrieve the file representation
    String name = file.getFileName();
    Attachment aAttachment = new AttachmentTransferObject();
    aAttachment.setName(name);
    attachments.add(aAttachment) ;  
	  return mapping.findForward("success");
	  
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
  public ActionForward saveReferenceDocs(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    
	  return mapping.findForward("gotoEdit");
	  
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
  public ActionForward cancelReferenceDocs(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

	  return mapping.findForward("gotoEdit");
	  
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
  public ActionForward gotoCreateReferenceDoc(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    
	  return mapping.findForward("gotoCreateReferenceDoc");
	  
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
  public ActionForward saveNewReferenceDoc(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    FormBuilderBaseDynaFormBean createForm = (FormBuilderBaseDynaFormBean) form;
    String docName = (String) createForm.get("docName");
    String contextIdSeq = (String) createForm.get("contextIdSeq");
    String docText = (String) createForm.get("docText");
    String url = (String) createForm.get("url");
    
    ReferenceDocument ref = new ReferenceDocumentTransferObject();
    
    ref.setDocName(docName);
    Context context = new ContextTransferObject();
    //context.setName(refForm.getName());
    //ref.setContext(context);
    ref.setUrl(url);
    ref.setDocText(docText);
    List attachments =  new ArrayList(); 
    ref.setAttachments(attachments);
    Form crf = (Form) getSessionObject(request, CRF);
    List refDocs = crf.getRefereceDocs();
    refDocs.add(ref);
    //createForm.clear();
    
	  return mapping.findForward("manageReferenceDoc");
	  
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
  public ActionForward cancelCreateReferenceDoc(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    FormBuilderBaseDynaFormBean createForm = (FormBuilderBaseDynaFormBean) form;

    createForm.clear();
    
	  return mapping.findForward("manageReferenceDoc");
	  
    }        
  private List getDummyRefDocs()
  {
    
      List refDocs =  new ArrayList();    
      ReferenceDocument ref = new ReferenceDocumentTransferObject();
      ref.setDocName("Paperforms");
      Context aContext = new ContextTransferObject();
      aContext.setName("CTEP");
      ref.setContext(aContext);
      
      List attachments =  new ArrayList(); 
      Attachment aAttachment = new AttachmentTransferObject();
      aAttachment.setName("ADVERSE_EVENTS.PDF");
      attachments.add(aAttachment);
      aAttachment = new AttachmentTransferObject();
      aAttachment.setName("ADVERSE_EVENTS.doc");      
      attachments.add(aAttachment);
      ref.setAttachments(attachments);
      refDocs.add(ref);
      
      ref = new ReferenceDocumentTransferObject();
      ref.setDocName("References");
      aContext = new ContextTransferObject();
      aContext.setName("CTEP");
      ref.setContext(aContext); 
      attachments =  new ArrayList(); 
      aAttachment = new AttachmentTransferObject();
      aAttachment.setName("TrialInformation.doc");
      attachments.add(aAttachment);
      aAttachment = new AttachmentTransferObject();
      aAttachment.setName("HistoricalData.pdf");      
      attachments.add(aAttachment);      
      ref.setAttachments(attachments);
      refDocs.add(ref);
      
      return refDocs;
  }
}

