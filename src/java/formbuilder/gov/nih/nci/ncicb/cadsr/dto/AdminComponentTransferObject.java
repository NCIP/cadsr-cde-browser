package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.AdminComponent;
import gov.nih.nci.ncicb.cadsr.resource.Context;

import java.sql.Date;

import java.util.List;


public class AdminComponentTransferObject extends BaseTransferObject
  implements AdminComponent {
  protected String preferredName;
  protected String preferredDefinition;
  protected String longName;
  protected String aslName;
  protected Float version;
  protected String deletedInd;
  protected String latestVerInd;
  protected String conteIdseq;
  protected Context context;
  protected List refDocs;
  protected List designations;
  protected int publicId;
  protected String origin;

  public AdminComponentTransferObject() {
  }

  public String getPreferredName() {
    return preferredName;
  }

  public String getLongName() {
    return longName;
  }

  public Float getVersion() {
    return version;
  }

  public String getPreferredDefinition() {
    return preferredDefinition;
  }

  public String getAslName() {
    return aslName;
  }

  public String getLatestVersionInd() {
    return latestVerInd;
  }

  public String getDeletedInd() {
    return deletedInd;
  }

  public String getConteIdseq() {
    return conteIdseq;
  }

  public Context getContext() {
    return context;
  }

  public int getPublicId() {
    return publicId;
  }

  public String getOrigin() {
    return origin;
  }

  //setter methods
  public void setPreferredName(String pPreferredName) {
    preferredName = pPreferredName;
  }

  public void setLongName(String pLongName) {
    longName = pLongName;
  }

  public void setVersion(Float pVersion) {
    version = pVersion;
  }

  public void setPreferredDefinition(String pPreferredDefinition) {
    preferredDefinition = pPreferredDefinition;
  }

  public void setAslName(String pAslName) {
    aslName = pAslName;
  }

  public void setLatestVersionInd(String pLatestVersionInd) {
    latestVerInd = pLatestVersionInd;
  }

  public void setDeletedInd(String pDeletedInd) {
    deletedInd = pDeletedInd;
  }

  public void setConteIdseq(String pContIdseq) {
    conteIdseq = pContIdseq;
  }

  public void setContext(Context pContext) {
    context = pContext;
  }

  public List getRefereceDocs() {
    return refDocs;
  }

  public List getDesignations() {
    return designations;
  }

  public void setReferenceDocs(List lRefDocs) {
    refDocs = lRefDocs;
  }

  public void setDesignations(List lDes) {
    designations = lDes;
  }

  public void setPublicId(int id) {
    publicId = id;
  }

  public void setOrigin(String source) {
    origin = source;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(ATTR_SEPARATOR + "preferredName=" + getPreferredName());
    sb.append(ATTR_SEPARATOR + "longName=" + getLongName());
    sb.append(ATTR_SEPARATOR + "version=" + getVersion());
    sb.append(
      ATTR_SEPARATOR + "preferredDefinition=" + getPreferredDefinition());
    sb.append(ATTR_SEPARATOR + "aslName=" + getAslName());
    sb.append(ATTR_SEPARATOR + "latestVersionInd=" + getLatestVersionInd());
    sb.append(ATTR_SEPARATOR + "deletedInd=" + getDeletedInd());
    sb.append(ATTR_SEPARATOR + "publicId=" + getPublicId());
    sb.append(ATTR_SEPARATOR + "origin=" + getOrigin());

    Context context = getContext();

    if (context != null) {
      sb.append(ATTR_SEPARATOR + "Context=" + context.toString());
    }
    else {
      sb.append(ATTR_SEPARATOR + "Context=null");
    }
    sb.append(super.toString());

    return sb.toString();
  }
}
