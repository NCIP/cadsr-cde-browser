package gov.nih.nci.ncicb.cadsr.persistence.bc4j;

import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;

import java.sql.Date;
import java.sql.SQLException;


public class ValidValuesValueObject implements ValidValue {
  protected ValueDomain valueDomain;
  protected String vdIdseq;
  protected String shortMeaning;
  protected String shortMeaningDescription;
  protected String shortMeaningValue;
  protected String vmDescription;
  protected String vpIdseq;

  public ValidValuesValueObject() {
    super();
  }

  public ValidValuesValueObject(ValidValuesViewRowImpl validValuesViewRowImpl) {
    vdIdseq = validValuesViewRowImpl.getVdIdseq();
    shortMeaning = validValuesViewRowImpl.getShortMeaning();
    shortMeaningDescription = validValuesViewRowImpl.getMeaningDescription();
    shortMeaningValue = validValuesViewRowImpl.getValue();
    vmDescription = validValuesViewRowImpl.getDescription();
  }

  public String getVdIdseq() {
    return vdIdseq;
  }

  public void setVdIdseq(String aVdIdseq) {
    vdIdseq = aVdIdseq;
  }

  public String getShortMeaning() {
    return shortMeaning;
  }

  public void setShortMeaning(String aShortMeaning) {
    shortMeaning = aShortMeaning;
  }

  public String getShortMeaningDescription() {
    return shortMeaningDescription;
  }

  public void setShortMeaningDescription(String aShortMeaningDescription) {
    shortMeaningDescription = aShortMeaningDescription;
  }

  public String getShortMeaningValue() {
    return shortMeaningValue;
  }

  public void setShortMeaningValue(String aShortMeaningValue) {
    shortMeaningValue = aShortMeaningValue;
  }

  public String getDescription() {
    return vmDescription;
  }

  public void setDescription(String vmDescription) {
    this.vmDescription = vmDescription;
  }
    /**
   * Clones the Object
   * @return 
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException {
    return super.clone();

  }

  public String getVpIdseq() {
    return vpIdseq;
  }

  public void setVpIdseq(String aVpIdseq) {
    this.vpIdseq = aVpIdseq;
  }
}