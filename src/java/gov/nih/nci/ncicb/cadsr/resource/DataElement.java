package gov.nih.nci.ncicb.cadsr.resource;
import java.util.List;


public interface DataElement extends AdminComponent{
   public String getDeIdseq();
   public void setDeIdseq(String pDeIdseq);

   public ValueDomain getValueDomain();
   public void setValueDomain(ValueDomain pValueDomain);

   public String getVdIdseq();
   public void setVdIdseq(String pVdIdseq);

   public DataElementConcept getDataElementConcept();
   public void setDataElementConcept(DataElementConcept pDataElementConcept);

   public String getDecIdseq();
   public void setDecIdseq(String pDecIdseq);

   public String getVdName();
   public String getContextName();
   public String getLongCDEName();
   public String getCDEId();
   public String getDecName();

   public void setLongCDEName (String pLongCDEName);
   public void setContextName(String pConteName);
   public void setCDEId(String pCDEId);

   public String getUsingContexts();
   public void setUsingContexts(String usingContexts);

   public DerivedDataElement getDerivedDataElement();
   public void setDerivedDataElement(DerivedDataElement dataElementDerivation);

   public List getClassifications();
   public void setClassifications(List classifications);
   
   public List getOtherVersions();
   public void setOtherVersions(List deList);   
   
   public Object clone() throws CloneNotSupportedException ;
}
