package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.AttachmentTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;


import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.ReferenceDocFormBean;
import gov.nih.nci.ncicb.cadsr.resource.Attachment;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

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
    
    //Create a dummy list of RefDocs and attach to Form
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

