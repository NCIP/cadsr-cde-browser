package gov.nih.nci.ncicb.cadsr.resource;
import java.io.Serializable;

public interface ReferenceDocument extends Serializable {
  public String getDocName();
  public String getDocType();
  public String getDocText();
  public String getDocIDSeq();
  public String getUrl();
  public String getLanguage();
  
  public void setDocName(String docName);
  public void setDocText(String docText);
  public void setDocType(String docType);
  public void setLanguage(String language);
  public void setUrl(String url);
  public void setDocIDSeq(String docIdSeq);
}