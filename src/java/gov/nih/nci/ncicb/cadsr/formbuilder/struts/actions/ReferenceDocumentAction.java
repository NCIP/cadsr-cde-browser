package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.dto.AttachmentTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.ReferenceDocFormBean;
import gov.nih.nci.ncicb.cadsr.resource.AdminComponent;
import gov.nih.nci.ncicb.cadsr.resource.Attachment;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.BLOB;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class ReferenceDocumentAction
 extends FormBuilderSecureBaseDispatchAction {
 private static String FORM_EDIT_UPDATED_REFDOCS = "formEditUpdatedRefDocs";
 private static String FORM_EDIT_DELETED_REFDOCS = "formEditDeletedRefDocs";
 private static String FORM_EDIT_ADDED_REFDOCS = "formEditAddedRefDocs";

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
 public ActionForward manageReferenceDocs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws IOException, ServletException {
  ReferenceDocFormBean refForm = (ReferenceDocFormBean)form;

  //refForm.reset(mapping,request);

  Form crf = (Form)getSessionObject(request, CRF);
  //populate the refForm
  Form orgCRF = (Form)getSessionObject(request, CLONED_CRF);
  List deletedRefDocs = (List)getSessionObject(request, DELETED_REFDOCS);

  if (deletedRefDocs == null) {
   deletedRefDocs = new ArrayList();
  }

  setSessionObject(request, DELETED_REFDOCS, deletedRefDocs);

  List deletedAtts = (List)getSessionObject(request, DELETED_ATTACHMENTS);

  if (deletedAtts == null) {
   deletedAtts = new ArrayList();
  }

  setSessionObject(request, DELETED_ATTACHMENTS, deletedAtts);

  List clonedRefDocs = orgCRF.getRefereceDocs();
  setSessionObject(request, REFDOCS_CLONED, clonedRefDocs);

  setSessionObject(request, REFDOCS_TEMPLATE_ATT_NAME, linkedAttachmentName(crf.getRefereceDocs()));

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
 public ActionForward viewReferenceDocs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws IOException, ServletException {
  Form crf = (Form)getSessionObject(request, CRF);

  setSessionObject(request, REFDOCS_TEMPLATE_ATT_NAME, linkedAttachmentName(crf.getRefereceDocs()));
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
  out = response.getOutputStream();

  InputStream is = null;
  String attachmentName = request.getParameter(FormConstants.REFERENCE_DOC_ATTACHMENT_NAME);
  response.addHeader("Content-Disposition", "inline;filename=\"" + attachmentName + "\"");
//  response.addHeader("Pragma", "No-cache");
//  response.addHeader("Cache-Control", "public");
//  response.addHeader("Expires", "0");

  // first find out if the attachment is new and saved in the session

  Map attMap = (Map)getSessionObject(request, REFDOC_ATTACHMENT_MAP);
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
      {
       out.flush();
       out.close();
      }
    
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
 public ActionForward gotoUploadDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response) throws IOException, ServletException {
  ReferenceDocFormBean refForm = (ReferenceDocFormBean)form;

  String refDocId = refForm.getSelectedRefDocId();
  return mapping.findForward("gotoUpload");
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
 public ActionForward deleteAttachment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException, ServletException {
  ReferenceDocFormBean refForm = (ReferenceDocFormBean)form;

  String [] selectedDeleteItems = refForm.getSelectedItems();
  List deletedAtts = (List)getSessionObject(request, DELETED_ATTACHMENTS);

  if (deletedAtts == null)
   deletedAtts = new ArrayList();

  Form crf = (Form)getSessionObject(request, CRF);
  List refDocs = crf.getRefereceDocs();

  String refDocId = refForm.getSelectedRefDocId();
  List attachments = ((ReferenceDocument)refDocs.get(Integer.parseInt(refDocId))).getAttachments();
  String [] selectedDeleteAtts = refForm.getSelectedItems();

  for (int i = 0; i < selectedDeleteItems.length; i++) {
   int deleteIndex = Integer.parseInt(selectedDeleteItems[i]);

   deletedAtts.add(attachments.get(deleteIndex));
  }

  attachments.removeAll(deletedAtts);
  this.setSessionObject(request, DELETED_ATTACHMENTS, deletedAtts);
  saveMessage("cadsr.formbuilder.refdoc.attachment.delete.success", request);

  return mapping.findForward("success");
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
 public ActionForward uploadDocuments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) throws IOException, ServletException {
  Form crf = (Form)getSessionObject(request, CRF);

  List refDocs = crf.getRefereceDocs();
  ReferenceDocFormBean refForm = (ReferenceDocFormBean)form;

  String refDocId = refForm.getSelectedRefDocId();
  ReferenceDocument refDoc = (ReferenceDocument)refDocs.get(Integer.parseInt(refDocId));
  List attachments = refDoc.getAttachments();

  FormFile file = refForm.getUploadedFile();
  //retrieve the file representation
  // append a random number at the end to be unique
  int dotIndex = file.getFileName().lastIndexOf(".");
  String name = file.getFileName().substring(0, dotIndex -1);
  String extension = file.getFileName().substring(dotIndex);
  name = name + "__" + (new Random()).nextInt();
  Attachment attachment = new AttachmentTransferObject();
  attachment.setName(name + extension);

  attachment.setMimeType(file.getContentType());
  attachment.setDocSize(file.getFileSize());

  if (attachments == null) {
   attachments = new ArrayList();

   refDoc.setAttachments(attachments);
  }

  attachments.add(attachment);

  Map attMap = (Map)getSessionObject(request, REFDOC_ATTACHMENT_MAP);

  if (attMap == null)
   attMap = new HashMap();

  attMap.put(attachment, file);
  setSessionObject(request, REFDOC_ATTACHMENT_MAP, attMap);
  saveMessage("cadsr.formbuilder.refdoc.attach.success", request);
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
 public ActionForward saveReferenceDocs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws IOException, ServletException {
  FormBuilderServiceDelegate service = getFormBuilderService();

  Form crf = (Form)getSessionObject(request, CRF);

  List refDocs = crf.getRefereceDocs();
  List originalDocs = (List)getSessionObject(request, REFDOCS_CLONED);
  Map attachments = (Map)getSessionObject(request, REFDOC_ATTACHMENT_MAP);

  Iterator iter = refDocs.iterator();
  boolean anythingChanged = false;
  int index = 0;

  try {
   for (int i = 0; i < refDocs.size(); i++) {
    ReferenceDocument refDoc = (ReferenceDocument)refDocs.get(i);

    refDoc.setDisplayOrder(i);

    if (refDoc.getDocIDSeq() == null) {
     //create new reference document in the database
     ReferenceDocument newRefDoc = service.createReferenceDocument(refDoc, crf.getIdseq());

     for (int j = 0; j < refDoc.getAttachments().size(); j++) {
      Attachment attachment = (Attachment)refDoc.getAttachments().get(j);

      FormFile attFile = (FormFile)attachments.get(attachment);

      if (attFile != null)
       if (saveRefDocAttachment(attachment, newRefDoc.getDocIDSeq(), attFile))
        attachments.remove(attachment);
     }

     anythingChanged = true;
    } else {
     ReferenceDocument origRefDoc = findOriginalRefDoc(originalDocs, refDoc);

     if (origRefDoc != null) //this should alreay be true {
      if (hasRefDocChanged(origRefDoc, refDoc)) {
       service.updateReferenceDocument(refDoc);

       anythingChanged = true;
      }

     List refAtts = refDoc.getAttachments();
     List origAtts = origRefDoc.getAttachments();
     Iterator attIter = refAtts.iterator();

     while (attIter.hasNext()) {
      Attachment newAtt = (Attachment)attIter.next();

      if (isAttachmentNew(origAtts, newAtt.getName())) {
       FormFile attFile = (FormFile)attachments.get(newAtt);

       if (attFile != null)
        if (saveRefDocAttachment(newAtt, origRefDoc.getDocIDSeq(), attFile)) {
         attachments.remove(newAtt);

         anythingChanged = true;
        }
      }
     }
    }
   }

   List deletedRefDocs = (List)getSessionObject(request, DELETED_REFDOCS);

   for (int i = 0; i < deletedRefDocs.size(); i++) {
    ReferenceDocument refDoc = (ReferenceDocument)deletedRefDocs.get(i);

    if (refDoc.getDocIDSeq() != null) {
     service.deleteReferenceDocument(refDoc.getDocIDSeq());

     anythingChanged = true;
    }
   }

   List deletedAtts = (List)getSessionObject(request, DELETED_ATTACHMENTS);

   for (int i = 0; i < deletedAtts.size(); i++) {
    Attachment deleteAttachment = (Attachment)deletedAtts.get(i);

    service.deleteAttachment(deleteAttachment.getName());
    anythingChanged = true;
   }
  } catch (DMLException dmle) {
   if (dmle.getMessage().indexOf("Document name already exist") >= 0)
    saveError("cadsr.formbuilder.refdoc.save.duplicate.error", request);
   else
    saveError("cadsr.formbuilder.refdoc.save.error", request);

   return mapping.findForward("success"); //simply reload the form
  } catch (FormBuilderException fe) {
   log.error("Error occurred trying to save reference documents", fe);

   saveError(ERROR_REFERENCE_DOC_SAVE_FAILED, request);
   saveError(fe.getErrorCode(), request);

   //         return mapping.findForward(FAILURE);

   saveError("cadsr.formbuilder.refdoc.save.error", request);
   return mapping.findForward("success"); //simply reload the form
  }

  // now save attachments
  if (!anythingChanged) {
   saveMessage("cadsr.formbuilder.refdoc.edit.nochange", request);

   return mapping.findForward("success"); //simply reload the form
  }

  removeSessionObject(request, DELETED_REFDOCS);
  removeSessionObject(request, REFDOC_ATTACHMENT_MAP);
  removeSessionObject(request, DELETED_ATTACHMENTS);
  removeSessionObject(request, REFDOCS_TEMPLATE_ATT_NAME);

  //reset CRF and CLONED_CRF
  try {
    crf = service.getFormDetails(crf.getIdseq());
    setSessionObject(request, CRF, crf);
    setSessionObject(request, CLONED_CRF, (Form) crf.clone(),true);
  } catch (Exception formE)
  {
  //refresh cache
    return mapping.findForward("gotoEdit");
  }
  saveMessage("cadsr.formbuilder.refdoc.save.success", request);
  return mapping.findForward("backtoRefDocEdit");
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
 public ActionForward confirmCancelReferenceDocs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws IOException, ServletException {

  if (this.anythingChanged(form, request))
  {
    return mapping.findForward("gotoConfirmCancel");
  } else {
    removeSessionObject(request, REFDOC_ATTACHMENT_MAP);
    removeSessionObject(request, DELETED_REFDOCS);
  }
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
 public ActionForward cancelReferenceDocs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws IOException, ServletException {

  removeSessionObject(request, REFDOC_ATTACHMENT_MAP);

  removeSessionObject(request, DELETED_REFDOCS);
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
 public ActionForward gotoCreateReferenceDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws IOException, ServletException {
  /*
  int displayOrder = Integer.parseInt(request.getParameter("selectedRefDocId"));
  FormBuilderBaseDynaFormBean dynaForm = (FormBuilderBaseDynaFormBean)form;
  dynaForm.set("selectedRefDocId"
  */
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
 public ActionForward saveNewReferenceDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws IOException, ServletException {
  FormBuilderBaseDynaFormBean createForm = (FormBuilderBaseDynaFormBean)form;

  String docName = (String)createForm.get("docName");
  String contextIdSeq = (String)createForm.get("contextIdSeq");
  String docText = (String)createForm.get("docText");
  String url = (String)createForm.get("url");
  String docType = (String)createForm.get("docType");

  ReferenceDocument ref = new ReferenceDocumentTransferObject();

  ref.setDocName(docName);
  Context context = new ContextTransferObject();
  context.setConteIdseq(contextIdSeq);
  ref.setContext(context);
  ref.setUrl(url);
  ref.setDocText(docText);
  ref.setDocType(docType);
  List attachments = new ArrayList();
  ref.setAttachments(attachments);
  Form crf = (Form)getSessionObject(request, CRF);
  List refDocs = crf.getRefereceDocs();

  if (refDocs == null) {
   refDocs = new ArrayList();

   refDocs.add(ref);
  } else {
   int displayOrder = Integer.parseInt((String)createForm.get("selectedRefDocId"));

   refDocs.add(displayOrder, ref);
  }
  //createForm.clear();

  saveMessage("cadsr.formbuilder.refdoc.add.success", request);

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
 public ActionForward cancelCreateReferenceDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                               HttpServletResponse response) throws IOException, ServletException {
  FormBuilderBaseDynaFormBean createForm = (FormBuilderBaseDynaFormBean)form;

  createForm.clear();

  return mapping.findForward("manageReferenceDoc");
 }

 private List getDummyRefDocs() {
  List refDocs = new ArrayList();

  ReferenceDocument ref = new ReferenceDocumentTransferObject();
  ref.setDocName("Paperforms");
  Context aContext = new ContextTransferObject();
  aContext.setName("CTEP");
  ref.setContext(aContext);

  List attachments = new ArrayList();
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
  attachments = new ArrayList();
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

 /**
* Swap the display order of the Reference document with next Reference
* document.
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
 public ActionForward moveRefDocDown(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException, ServletException {
  ReferenceDocFormBean editForm = (ReferenceDocFormBean)form;

  int refDocIndex = Integer.parseInt(editForm.getSelectedRefDocId());
  Form crf = (Form)getSessionObject(request, CRF);

  List refDocs = crf.getRefereceDocs();

  if ((refDocs != null) && (refDocs.size() > 1)) {
   ReferenceDocument nextDoc = (ReferenceDocument)refDocs.get(refDocIndex + 1);

   //int currDisplayOrder = currModule.getDisplayOrder();
   refDocs.remove(refDocIndex + 1);
   refDocs.add(refDocIndex, nextDoc);
  }

  log.debug("Move down Reference Doc");

  return mapping.findForward("success");
 }

 /**
* Swap the display order of the Reference Documentation with the previous
* reference documentation
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
 public ActionForward moveRefDocUp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response) throws IOException, ServletException {
  ReferenceDocFormBean editForm = (ReferenceDocFormBean)form;

  int refDocIndex = Integer.parseInt(editForm.getSelectedRefDocId());
  Form crf = (Form)getSessionObject(request, CRF);

  List refDocs = crf.getRefereceDocs();

  if ((refDocs != null) && (refDocs.size() > 1)) {
   ReferenceDocument refDoc = (ReferenceDocument)refDocs.get(refDocIndex);

   ReferenceDocument prvRef = (ReferenceDocument)refDocs.get(refDocIndex - 1);
   //int currDisplayOrder = currModule.getDisplayOrder();
   refDocs.remove(refDocIndex);
   refDocs.add(refDocIndex - 1, refDoc);
  }

  log.debug("Move up Reference Doc");

  return mapping.findForward("success");
 }
 /**
* Delete the selected Reference Documentation
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
 public ActionForward deleteRefDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response) throws IOException, ServletException {
  ReferenceDocFormBean editForm = (ReferenceDocFormBean)form;

  int refDocIndex = Integer.parseInt(editForm.getSelectedRefDocId());
  Form crf = (Form)getSessionObject(request, CRF);
  List deletedRefDocs = (List)getSessionObject(request, DELETED_REFDOCS);

  if (deletedRefDocs == null) {
   deletedRefDocs = new ArrayList();
  }

  List refDocs = crf.getRefereceDocs();

  if ((refDocs != null) && (refDocs.size() >= 1)) {
   ReferenceDocument refDoc = (ReferenceDocument)refDocs.get(refDocIndex);

   //int currDisplayOrder = currModule.getDisplayOrder();
   refDocs.remove(refDocIndex);
   deletedRefDocs.add(refDoc);
  }

  setSessionObject(request, DELETED_REFDOCS, deletedRefDocs);
  saveMessage("cadsr.formbuilder.refdoc.delete.success", request);

  log.debug("Delete Reference Doc");

  return mapping.findForward("success");
 }

 /**
* Added back a deleted Reference Documentation
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
 public ActionForward unDeleteRefDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException, ServletException {
  ReferenceDocFormBean editForm = (ReferenceDocFormBean)form;

  String addDeletedRefdocIdSeq = editForm.getAddDeletedRefDocIdSeq();

  int refDocIndex = Integer.parseInt(editForm.getSelectedRefDocId());

  Form crf = (Form)getSessionObject(request, CRF);
  List refDocs = crf.getRefereceDocs();

  if (refDocs == null)
   refDocs = new ArrayList();

  List deletedRefDocs = (List)getSessionObject(request, DELETED_REFDOCS);

  Iterator iter = deletedRefDocs.iterator();

  while (iter.hasNext()) {
   Object test = iter.next();

   ReferenceDocument refDoc = (ReferenceDocument)test; //iter.next();

   if (refDoc.getDocIDSeq().equals(addDeletedRefdocIdSeq)) {
    deletedRefDocs.remove(refDoc);

    refDocs.add(refDocIndex, refDoc);
    break;
   }
  }

  crf.setReferenceDocs(refDocs);
  setSessionObject(request, DELETED_REFDOCS, deletedRefDocs, true);

  log.debug("Undelete Reference Doc");

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
 public ActionForward getFormToEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws IOException, ServletException {
  int displayOrder = Integer.parseInt(request.getParameter("selectedRefDocId"));

  FormBuilderBaseDynaFormBean dynaForm = (FormBuilderBaseDynaFormBean)form;
  Form crf = (Form)getSessionObject(request, CRF);
  List refDocs = crf.getRefereceDocs();
  ReferenceDocument refDoc = (ReferenceDocument)refDocs.get(displayOrder);
  dynaForm.set("docName", refDoc.getDocName());
  dynaForm.set("docText", refDoc.getDocText());
  dynaForm.set("docType", refDoc.getDocType());
  dynaForm.set("url", refDoc.getUrl());
  dynaForm.set("selectedRefDocId", String.valueOf(displayOrder));
  dynaForm.set("contextIdSeq", refDoc.getContext().getConteIdseq());

  return mapping.findForward("gotoEditReferenceDoc");
 }

 /**
  * Cancel edit reference documentation
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
 public ActionForward cancelEditReferenceDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws IOException, ServletException {
  FormBuilderBaseDynaFormBean editForm = (FormBuilderBaseDynaFormBean)form;

  editForm.clear();

  return mapping.findForward("manageReferenceDoc");
 }

 /**
  * Cancel edit reference documentation
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
 public ActionForward cancelUploadAttachement(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws IOException, ServletException {

  return mapping.findForward("success");
 }
 /**
  * Save changes to reference documentation
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
 public ActionForward saveEditReferenceDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response) throws IOException, ServletException {
  FormBuilderBaseDynaFormBean dynaForm = (FormBuilderBaseDynaFormBean)form;

  Form crf = (Form)getSessionObject(request, CRF);
  List refDocs = crf.getRefereceDocs();
  int displayOrder = Integer.parseInt((String)dynaForm.get("selectedRefDocId"));
  ReferenceDocument refDoc = (ReferenceDocument)refDocs.get(displayOrder);
  refDoc.setDocName((String)dynaForm.get("docName"));
  refDoc.setDocText((String)dynaForm.get("docText"));
  refDoc.setUrl((String)dynaForm.get("url"));
  refDoc.getContext().setConteIdseq((String)dynaForm.get("contextIdSeq"));
  refDoc.setDocType((String)dynaForm.get("docType"));

  dynaForm.clear();

  return mapping.findForward("manageReferenceDoc");
 }

 private ReferenceDocument findOriginalRefDoc(List origDocs, ReferenceDocument refDoc) {
  if (origDocs != null) {
   Iterator iter = origDocs.iterator();

   while (iter.hasNext()) {
    ReferenceDocument origRefDoc = (ReferenceDocument)iter.next();

    if (origRefDoc.getDocIDSeq().equals(refDoc.getDocIDSeq()))
     return origRefDoc;
   }
  }

  return null;
 }

 private boolean hasRefDocChanged(ReferenceDocument origRefDoc, ReferenceDocument refDoc) {
  if (origRefDoc.getDisplayOrder() != refDoc.getDisplayOrder())
   return true;

  if (origRefDoc.getDocName() != null && !origRefDoc.getDocName().equals(refDoc.getDocName()))
   return true;

  if (origRefDoc.getDocName() == null && (refDoc.getDocName() != null))
   return true;

  if (origRefDoc.getDocName() != null && !origRefDoc.getDocName().equals(refDoc.getDocName()))
   return true;

  if (origRefDoc.getDocName() == null && (refDoc.getDocName() != null))
   return true;

  if (origRefDoc.getDocType() != null && !origRefDoc.getDocType().equals(refDoc.getDocType()))
   return true;

  if (origRefDoc.getDocType() == null && (refDoc.getDocType() != null))
   return true;

  if (!origRefDoc.getContext().getConteIdseq().equals(refDoc.getContext().getConteIdseq()))
   return true;

  if (origRefDoc.getUrl() != null && !origRefDoc.getUrl().equals(refDoc.getUrl()))
   return true;

  if (origRefDoc.getUrl() == null && (refDoc.getUrl() != null))
   return true;

  if (origRefDoc.getDocText() != null && !origRefDoc.getDocText().equals(refDoc.getDocText()))
   return true;

  if (origRefDoc.getDocText() == null && (refDoc.getDocText() != null))
   return true;

  return false;
 }

 private boolean saveRefDocAttachment(Attachment attachment, String rd_idseq, FormFile attFile) throws DMLException {
  String
     sqlNewRow = "INSERT INTO reference_blobs (rd_idseq,name,mime_type,doc_size,content_type,blob_content) "
                    + "VALUES (?,?,?,?,?,EMPTY_BLOB())",
     sqlLockRow = "SELECT blob_content FROM reference_blobs " + "WHERE name = ? FOR UPDATE",
     sqlSetBlob = "UPDATE reference_blobs " + "SET blob_content = ? " + "WHERE name = ?";

  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  try {
   DBUtil dbUtil = new DBUtil();

   //String dsName = CDEBrowserParams.getInstance("cdebrowser").getSbrDSN();
   dbUtil.getOracleConnectionFromContainer();
   conn = dbUtil.getConnection();
   conn.setAutoCommit(false);
   //make new row
   ps = conn.prepareStatement(sqlNewRow);
   ps.setString(1, rd_idseq);
   ps.setString(2, attachment.getName());
   ps.setString(3, attachment.getMimeType());
   ps.setLong(4, attachment.getDocSize());
   ps.setString(5, "BLOB");

   ps.executeUpdate();
   //lock new row
   ps = conn.prepareStatement(sqlLockRow);
   ps.setString(1, attachment.getName());
   rs = ps.executeQuery();
   rs.next();

   BLOB dbBlob = (BLOB)rs.getBlob(1);
   //update blob
   ps = conn.prepareStatement(sqlSetBlob);
   ps.setString(2, attachment.getName());
   dbBlob.putBytes(1, attFile.getFileData());

   ps.setBlob(1, dbBlob);
   conn.commit();
  } catch (SQLException sqlE) {
   if (sqlE.getMessage().indexOf("unique constraint") > 0) {
    throw new DMLException("Document name already exists in the database.");
   }
  } catch (Exception ex) {
   log.error("Exception Caught:", ex);

   throw new DMLException("Exception occurred while saving attachment to the database", ex);
  } finally {
   try {
   
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

    return false;
   } finally { }
  }

  return true;
 }

 private boolean isAttachmentNew(List origAttachments, String attName) {
  Iterator iter = origAttachments.iterator();

  while (iter.hasNext()) {
   Attachment origAtt = (Attachment)iter.next();

   if (origAtt.getName().equals(attName))
    return false;
  }

  return true;
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

  private boolean anythingChanged( ActionForm form, HttpServletRequest request) {

  Form crf = (Form)getSessionObject(request, CRF);

  List refDocs = crf.getRefereceDocs();
  List originalDocs = (List)getSessionObject(request, REFDOCS_CLONED);
  Map attachments = (Map)getSessionObject(request, REFDOC_ATTACHMENT_MAP);

  Iterator iter = refDocs.iterator();
  int index = 0;

   for (int i = 0; i < refDocs.size(); i++) {
    ReferenceDocument refDoc = (ReferenceDocument)refDocs.get(i);

    refDoc.setDisplayOrder(i);

    if (refDoc.getDocIDSeq() == null) {
     // new reference document
     return true;
    } else {
     ReferenceDocument origRefDoc = findOriginalRefDoc(originalDocs, refDoc);

     if (origRefDoc != null) //this should alreay be true {
      if (hasRefDocChanged(origRefDoc, refDoc)) {
       return true; // some existing ref doc has changed
      }

     List refAtts = refDoc.getAttachments();
     List origAtts = origRefDoc.getAttachments();
     Iterator attIter = refAtts.iterator();

     while (attIter.hasNext()) {
      Attachment newAtt = (Attachment)attIter.next();

      if (isAttachmentNew(origAtts, newAtt.getName()))
       return true;


     }
    }
   }

   List deletedRefDocs = (List)getSessionObject(request, DELETED_REFDOCS);

   for (int i = 0; i < deletedRefDocs.size(); i++) {
    ReferenceDocument refDoc = (ReferenceDocument)deletedRefDocs.get(i);

    if (refDoc.getDocIDSeq() != null) {
     return true;
    }
   }

   List deletedAtts = (List)getSessionObject(request, DELETED_ATTACHMENTS);

   for (int i = 0; i < deletedAtts.size(); i++) {
    Attachment deleteAttachment = (Attachment)deletedAtts.get(i);

    return true;
   }

  return false;
 }

 private String linkedAttachmentName (List refDocs)
 {
     // find out which attachment is first uploaded
  Iterator refIter = refDocs.iterator();
  String attachmentName = "";
  
  if(refDocs==null) return "";
  if(refDocs.isEmpty()) return "";
  
  ReferenceDocument ref = (ReferenceDocument)refDocs.get(0);

  //first find the first reference document of type IMAGE_FILE
 /** while (refIter.hasNext())
  {
    ref = (ReferenceDocument) refIter.next();
    if (ref.getDocType().equalsIgnoreCase(ReferenceDocument.REF_DOC_TYPE_IMAGE))
      break;
  }
  **/
  //then find its first uploaded attachment
  if (ref !=null && ref.getAttachments()!=null && ref.getAttachments().size() >0){
         // fix to get last atttachemnt by created by
        Attachment attachment = (Attachment) ref.getAttachments().get(ref.getAttachments().size()-1);
        attachmentName = attachment.getName();
  }
  return attachmentName;
 }
}
