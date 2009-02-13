package gov.nih.nci.ncicb.cadsr.common.resource;

import java.io.Serializable;
import java.sql.Date;

public interface ValidValue extends Serializable {

	public String getVdIdseq();

	public void setVdIdseq(String aVdIdseq);

	public String getShortMeaning();

	public void setShortMeaning(String aShortMeaning);

	public String getShortMeaningDescription();

	public void setShortMeaningDescription(String aShortMeaningDescription);

	public String getShortMeaningValue();

	public void setShortMeaningValue(String aShortMeaningValue);

	public String getDescription();

	public void setDescription(String vmDescription);

	public String getVpIdseq();

	public void setVpIdseq(String aVpIdseq);

	public Object clone() throws CloneNotSupportedException ;

	public ConceptDerivationRule getConceptDerivationRule();

	public void setConceptDerivationRule(ConceptDerivationRule rule);  

	public void setValueMeaning(ValueMeaning vm);

	public ValueMeaning getValueMeaning();
	
	public void setVmId(Integer vmId);

	public Integer getVmId();

	public void setVmVersion(Float vmVersion);

	public Float getVmVersion();
	
	public void setBeginDate(String beginDate);

	public String getBeginDate();

	public void setEndDate(String endDate);

	public String getEndDate();
	
	public void setContext(String context);
	
	public String getContext();
	
	public void setWorkflowstatus(String workflowstatus);
	
	public String getWorkflowstatus();

}
