package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ReferenceDocumentDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.util.StringUtils;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;


public class JDBCReferenceDocumentDAO extends JDBCAdminComponentDAO
  implements ReferenceDocumentDAO {
  public JDBCReferenceDocumentDAO(ServiceLocator locator) {
    super(locator);
  }
  
public ReferenceDocument createReferenceDoc(ReferenceDocument newRefDoc, String acIdSeq)
    throws DMLException 
    {
    InsertRefDoc insertQuestContent =
      new InsertRefDoc(this.getDataSource());
    String rdIdseq = generateGUID();
    int res = insertQuestContent.createContent(newRefDoc, rdIdseq, acIdSeq);

    if (res != 1) {
      throw new DMLException(
        "Did not succeed creating form instruction record in the " +
        " quest_contents_ext table.");
    }
    newRefDoc.setDocIDSeq(rdIdseq);
    return newRefDoc;
      
    }
    
  /**
   * Deletes the Reference including all the attachments associated with it.
   *
   * @param <b>refDocId</b> Idseq of the reference document.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteReferenceDocument(String refDocId) throws DMLException {
    DeleteRefDoc deleteRefDoc = new DeleteRefDoc(this.getDataSource());
    Map out = deleteRefDoc.executeDeleteCommand(refDocId);

    String returnCode = (String) out.get("p_return_code");
    String returnDesc = (String) out.get("p_return_desc");

    if (!StringUtils.doesValueExist(returnCode)) {
      return 1;
    }
    else {
           DMLException dmlExp = new DMLException(returnDesc);
	       dmlExp.setErrorCode(ERROR_DELETE_FORM_FAILED);
           throw dmlExp;
    }
  }   
    
  /**
   * Deletes an attachment associated with a reference document
   *
   * @param <b>attachmentName</b> name of attachment.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteAttachment(String attachmentName) throws DMLException {
    DeleteRefDocAttachment deleteAttachment = new DeleteRefDocAttachment(this.getDataSource());
    Map out = deleteAttachment.executeDeleteCommand(attachmentName);

    String returnCode = (String) out.get("p_return_code");
    String returnDesc = (String) out.get("p_return_desc");

    if (!StringUtils.doesValueExist(returnCode)) {
      return 1;
    }
    else {
           DMLException dmlExp = new DMLException(returnDesc);
	       dmlExp.setErrorCode(ERROR_DELETE_FORM_FAILED);
           throw dmlExp;
    }
  }   
    
  public int updateReferenceDocument(ReferenceDocument refDoc) throws DMLException {
  
    UpdateRefDoc  updateRefDoc  = 
      new UpdateRefDoc (this.getDataSource());
    int res = updateRefDoc.updateRefDoc(refDoc);
    if (res != 1) {
         DMLException dmlExp = new DMLException("Did not succeed updating form record in the " + 
        " quest_contents_ext table.");
	       dmlExp.setErrorCode(ERROR_UPDATING_FORM);
           throw dmlExp;             
    }
    return res;
  }  
    
    
    
  /**
   * Inner class to create a reference document record in the
   * reference_documents table.
   */
  private class InsertRefDoc extends SqlUpdate {
    public InsertRefDoc(DataSource ds) {
      String refDocInsertSql =
        " INSERT INTO sbr.reference_documents_view " +
        " (rd_idseq, name, dctl_name, ac_idseq, doc_text, url, " +
        " display_order, conte_idseq ) " +
        " VALUES " + " (?, ?, ?, ?, ?,?, ?, ?) ";
      this.setDataSource(ds);
      this.setSql(refDocInsertSql);
      declareParameter(new SqlParameter("rd_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("name", Types.VARCHAR));
      declareParameter(new SqlParameter("dctl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("ac_idseq", Types.VARCHAR));
      declareParameter(
        new SqlParameter("doc_text", Types.VARCHAR));
      declareParameter(new SqlParameter("url", Types.VARCHAR));
      declareParameter(new SqlParameter("display_order", Types.INTEGER));
      declareParameter(new SqlParameter("p_conte_idseq", Types.VARCHAR));
//      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      compile();
    }

    protected int createContent(
      ReferenceDocument refDoc, String rdIdseq, String acIdseq) {
      Object[] obj =
        new Object[] {
          rdIdseq, refDoc.getDocName(),
          refDoc.getDocType(), acIdseq,
          refDoc.getDocText(), refDoc.getUrl(),
          new Integer(refDoc.getDisplayOrder()), refDoc.getContext().getConteIdseq(),
          
        };

      int res = update(obj);

      return res;
    }
  }
  /**
   * Inner class that accesses database to delete a reference document.
   */
  private class DeleteRefDoc extends StoredProcedure {
    public DeleteRefDoc(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.remove_rd");
      declareParameter(new SqlParameter("p_rd_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map executeDeleteCommand(String refDocIdseq) {
      Map in = new HashMap();
      in.put("p_rd_idseq", refDocIdseq);

      Map out = execute(in);

      return out;
    }
  }
  
  /**
   * Inner class to update the Reference Document.
   *
   */
  private class UpdateRefDoc extends SqlUpdate {
    public UpdateRefDoc(DataSource ds) {
      String updateRefDocSql =
        " UPDATE sbr.reference_documents_view SET " +
        " name = ? ," + 
        " dctl_name = ? ," + 
        " doc_text = ? ," + 
        " url = ? ," + 
        " display_order = ? ," + 
        " conte_idseq = ? " + 
        " WHERE rd_idseq = ? ";

      this.setDataSource(ds);
      this.setSql(updateRefDocSql);
      declareParameter(new SqlParameter("p_name", Types.VARCHAR));
      declareParameter(new SqlParameter("dctl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_doc_text", Types.VARCHAR));
      declareParameter(new SqlParameter("p_url", Types.VARCHAR));
      declareParameter(new SqlParameter("p_display_order", Types.INTEGER));
      declareParameter(new SqlParameter("p_conte_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("rd_idseq", Types.VARCHAR));
      compile();
    }

    protected int updateRefDoc( ReferenceDocument refDoc) {

      Object[] obj =
        new Object[] {
           refDoc.getDocName(),
           refDoc.getDocType(),
           refDoc.getDocText(),
           refDoc.getUrl(),
           new Integer(refDoc.getDisplayOrder()),
           refDoc.getContext().getConteIdseq(),
           refDoc.getDocIDSeq()
        };
      int res = update(obj);

      return res;
    }
  }
  /**
   * Inner class that accesses database to delete a reference document attachment.
   */
  private class DeleteRefDocAttachment extends StoredProcedure {
    public DeleteRefDocAttachment(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.remove_rb");
      declareParameter(new SqlParameter("p_name", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map executeDeleteCommand(String name) {
      Map in = new HashMap();
      in.put("p_name", name);

      Map out = execute(in);

      return out;
    }
  }
  


}