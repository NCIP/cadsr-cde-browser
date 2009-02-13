package gov.nih.nci.ncicb.cadsr.common.resource;
import java.io.Serializable;
import java.util.List;

public interface ReferenceDocument extends Serializable, Orderable, Audit{
  public static String REF_DOC_TYPE_PREFERRED_QUESTION_TEXT = "Preferred Question Text";
  public static String REF_DOC_TYPE_ALT_QUESTION_TEXT = "Alternate Question Text";
  public static final String REF_DOC_TYPE_IMAGE = "IMAGE_FILE";

  public String getDocName();
  public String getDocType();
  public String getDocText();
  public String getDocIDSeq();
  public String getUrl();
  public String getLanguage();
  public Context getContext();
  public List getAttachments();

  public void setDocName(String docName);
  public void setDocText(String docText);
  public void setDocType(String docType);
  public void setLanguage(String language);
  public void setUrl(String url);
  public void setDocIDSeq(String docIdSeq);
  public void setContext(Context newContext);
  public void setAttachments(List attachments);

  public Object clone() throws CloneNotSupportedException ;

}