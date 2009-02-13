package gov.nih.nci.ncicb.cadsr.common.resource;
//import oracle.sql.*;
import oracle.jbo.domain.BlobDomain;

public interface ReferenceBlob  {
  public String getMimeType();
  public void setMimeType(String mimetype);

  public BlobDomain getBlobContent();
  public void setBlobContent(BlobDomain theBlob);

  public String getName();
  public void setName(String docName);

  public int getDocSize();
  
}