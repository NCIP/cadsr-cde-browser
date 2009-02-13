package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.handler;

import gov.nih.nci.ncicb.cadsr.common.cdebrowser.userexception.DocumentNotFoundException;
import gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.CDEBrowserBc4jModuleImpl;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceBlob;
import gov.nih.nci.ncicb.cadsr.common.resource.handler.ReferenceBlobHandler;

import oracle.cle.persistence.Handler;
import oracle.cle.persistence.HandlerFactory;

import oracle.cle.util.CLEUtil;


public class ReferenceBlobHandlerImpl extends Handler
  implements ReferenceBlobHandler {
  public ReferenceBlobHandlerImpl() {
  }

  public Class getReferenceClass() {
    return ReferenceBlob.class;
  }

  public Object findObjectForAdminComponent(
    Object adminComponentIdseq,
    String docType,
    Object sessionId) throws DocumentNotFoundException, Exception {
    ReferenceBlob rb = null;

    try {
      CDEBrowserBc4jModuleImpl module =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      rb = module.getRefBlobsForAdminComponent(adminComponentIdseq, docType);
      releaseConnection(sessionId);
    }
    catch (DocumentNotFoundException e) {
      throw e;
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      releaseConnection(sessionId);
    }

    return rb;
  }
  /**
   * Get the forst reference document and the forst attachment of that 
   * reference Document
   */  
  public Object findFirstObjectForAdminComponent(
    Object adminComponentIdseq,
    Object sessionId) throws DocumentNotFoundException, Exception {
    ReferenceBlob rb = null;

    try {
      CDEBrowserBc4jModuleImpl module =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      rb = module.getFirstRefBlobsForAdminComponent(adminComponentIdseq);
      releaseConnection(sessionId);
    }
    catch (DocumentNotFoundException e) {
      throw e;
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      releaseConnection(sessionId);
    }

    return rb;
  }  
  public Object refDocForAdminComponent(
    Object refDocIdseq,
    Object sessionId) throws DocumentNotFoundException, Exception {
    ReferenceBlob rb = null;

    try {
      CDEBrowserBc4jModuleImpl module =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      rb = module.getRefBlobsForAdminComponent(refDocIdseq);
      releaseConnection(sessionId);
    }
    catch (DocumentNotFoundException e) {
      throw e;
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      releaseConnection(sessionId);
    }

    return rb;
  }
  

  /*public static void main(String[] args) {
    Integer sessionId = new Integer(1);

    try {
      ReferenceBlobHandler rh =
        (ReferenceBlobHandler) HandlerFactory.getHandler(ReferenceBlob.class);
      ReferenceBlob rb =
        (ReferenceBlob) rh.findObjectForAdminComponent(
          "99CD59C5-A8B7-3FA4-E034-080020C9C0E0", "IMAGE_FILE", sessionId);
      System.out.println("Doc Name is: " + rb.getName());
    }
    catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }*/
}
