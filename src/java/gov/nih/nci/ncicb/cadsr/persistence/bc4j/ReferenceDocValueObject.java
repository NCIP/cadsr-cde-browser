package gov.nih.nci.ncicb.cadsr.persistence.bc4j;
import gov.nih.nci.ncicb.cadsr.persistence.base.BaseValueObject;
import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;

public class ReferenceDocValueObject extends BaseValueObject
                        implements ReferenceDocument, java.io.Serializable{
  String docName = null;
  String docType = null;
  String docIDSeq = null;
  String docText = null;
  String lang = null;
  String url = null;
  //Integer displayOrder 
  public ReferenceDocValueObject(ReferenceDocumentsViewRowImpl refDoc) {
    docName = checkForNull(refDoc.getName());
    docType = refDoc.getDctlName();
    docIDSeq = refDoc.getRdIdseq();
    docText = checkForNull(refDoc.getDocText());
    lang = refDoc.getLaeName();
    url = checkForNull(refDoc.getUrl());
  }
  public String getDocName(){
    return docName;
  }
  public String getDocType(){
    return docType;
  }
  public String getDocText(){
    return docText;
  }
  public String getDocIDSeq(){
    return docIDSeq;
  }
  public String getUrl(){
    return url;
  }
  public String getLanguage(){
    return lang;
  }
}