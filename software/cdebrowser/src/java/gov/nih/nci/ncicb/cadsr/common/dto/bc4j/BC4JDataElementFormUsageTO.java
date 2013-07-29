/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.dto.bc4j;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElementFormUsage;
import gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.FormUsagesForACdeViewRowImpl;

public class BC4JDataElementFormUsageTO implements DataElementFormUsage  {
  private String protocolLongName;
  private String formLongName;
  private String usageType;
  private String protocolLeadOrg;
  private String questionLongName;
  private String publicId;
  private String version;
  private String formURL;
  private String formDetailURL;
  private String crfIdSeq;
  
  public BC4JDataElementFormUsageTO() {
  }

  public BC4JDataElementFormUsageTO(FormUsagesForACdeViewRowImpl usage) {
    protocolLongName = usage.getProtocolNumber();
    formLongName = usage.getCrfLongName();
    usageType = usage.getUsageType();
    protocolLeadOrg = usage.getLeadOrg();
    questionLongName = usage.getQueLongName();
    publicId = usage.getPublicId();
    version = usage.getVersion();
    formURL = usage.getFormURL();
    formDetailURL = usage.getFormDetailURL();
    crfIdSeq = usage.getCrfIdseq();
  }

  public String getProtocolLongName() {
    return protocolLongName;
  }

  public String getFormLongName() {
    return formLongName;
  }

  public String getUsageType() {
    return usageType;
  }

  public String getProtocolLeadOrg() {
    return protocolLeadOrg;
  }


  public String getQuestionLongName()
  {
    return questionLongName;
  }
  
  public String getPublicId() {
    return publicId;
  }
  
  public String getVersion() {
    return version;
  }
  
  public String getFormURL() {
    return formURL;
  }
  
  public String getFormDetailURL() {
    return formDetailURL;
  }
  
  public String getCrfIdSeq() {
    return crfIdSeq;
  }
  
}