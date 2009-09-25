package gov.nih.nci.ncicb.cadsr.common.dto.bc4j;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElementFormUsage;
import gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.FormUsagesForACdeViewRowImpl;

public class BC4JDataElementFormUsageTO implements DataElementFormUsage  {
  private String protocolLongName;
  private String formLongName;
  private String usageType;
  private String protocolLeadOrg;
  private String questionLongName;
  
  public BC4JDataElementFormUsageTO() {
  }

  public BC4JDataElementFormUsageTO(FormUsagesForACdeViewRowImpl usage) {
    protocolLongName = usage.getProtocolNumber();
    formLongName = usage.getCrfLongName();
    usageType = usage.getUsageType();
    protocolLeadOrg = usage.getLeadOrg();
    questionLongName = usage.getQueLongName();
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
}