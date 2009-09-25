package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j;

import gov.nih.nci.ncicb.cadsr.common.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.common.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomain;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueMeaning;

import java.sql.Date;

public class ValidValuesValueObject implements ValidValue {
  protected ValueDomain valueDomain;
  protected String vdIdseq;
  protected String shortMeaning;
  protected String shortMeaningDescription;
  protected String shortMeaningValue;
  protected String vmDescription;
  protected String vpIdseq;  
  protected Integer vmId;
  protected Float vmVersion;
  protected String beginDate;
  protected String endDate;
  protected String context;
  protected String workflowstatus;
  ConceptDerivationRule conceptDerivationRule =null;

  public ValidValuesValueObject() {
    super();
  }

  public ValidValuesValueObject(ValidValuesViewRowImpl validValuesViewRowImpl) {
	  vdIdseq = validValuesViewRowImpl.getVdIdseq();
	  shortMeaning = validValuesViewRowImpl.getLongName();
	  shortMeaningDescription = validValuesViewRowImpl.getMeaningDescription();
	  shortMeaningValue = validValuesViewRowImpl.getValue();
	  vmDescription = validValuesViewRowImpl.getPreferredDefinition();
	  if(validValuesViewRowImpl.getBeginDate()!= null){
		  beginDate = ((Date)validValuesViewRowImpl.getBeginDate().dateValue()).toString();
	  }else {
		  beginDate = "";
	  }
	  if (validValuesViewRowImpl.getEndDate()!= null){
		  endDate = ((Date)validValuesViewRowImpl.getEndDate().dateValue()).toString();		 
	  }else {
		  endDate = "";
	  }
	  vmId = new Integer(validValuesViewRowImpl.getVmId().intValue());
	  vmVersion = new Float(validValuesViewRowImpl.getVersion().floatValue());
	  context = validValuesViewRowImpl.getName();
	  workflowstatus = validValuesViewRowImpl.getAslName();
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
    public ConceptDerivationRule getConceptDerivationRule()
   {
     return conceptDerivationRule;
   }
   public void setConceptDerivationRule(ConceptDerivationRule rule)
   {
     conceptDerivationRule = rule;
   }
   
   public String getBeginDate(){
	   return beginDate;
   }
   
   public void setBeginDate(String beginDate){
	   this.beginDate = beginDate;	   
   }
   
   public String getEndDate(){
	   return endDate;
   }
   
   public void setEndDate(String endDate){
	   this.endDate = endDate;	   
   }
   
   public Integer getVmId(){
	   return  vmId; 
   }
   
   public void setVmId(Integer vmId){
	   this.vmId = vmId;
   }
   public Float getVmVersion(){
	   return vmVersion;
   }
   
   public void setVmVersion(Float vmVersion){
	   this.vmVersion = vmVersion;
   }   
   
	public String getContext() {
		return context;
	}
	
	public void setContext(String context) {
		this.context = context;
	}

	public String getWorkflowstatus() {
		return workflowstatus;
	}
	
	public void setWorkflowstatus(String workflowstatus) {
		this.workflowstatus = workflowstatus;
	}

//the following 2 methods are added to ValidValue interface which is implemented 
   //both in bc4j layer and form builder value object.
   //These two methods must be implemented even though they may not be used 
   //in BC4J persistent layer   
   public ValueMeaning getValueMeaning(){
       return null;
   }
   
   public void setValueMeaning(ValueMeaning vm){
       return;
   }  
   
}
