package gov.nih.nci.ncicb.cadsr.resource;
import java.io.Serializable;
import java.util.List;

public interface ValueDomain extends AdminComponent , Serializable
{
   public String getVdIdseq();
   public void setVdIdseq(String aVdIdseq);

   public String getDatatype();
   public void setDatatype(String datatype);

   public String getDisplayFormat();
   public void setDisplayFormat(String dispFormat);

   public String getUnitOfMeasure();
   public void setUnitOfMeasure(String uom);

   public String getMaxLength();
   public void setMaxLength(String maxLength);

   public String getMinLength();
   public void setMinLength(String minLength);

   public String getHighValue();
   public void setHighValue(String highVal);

   public String getLowValue();
   public void setLowValue(String lowVal);

   public String getCharSet();
   public void setCharSet(String charSet);

   public String getDecimalPlace();
   public void setDecimalPlace(String dp);

   public String getCDContextName();
   public String getCDPrefName();
   public int getCDPublicId();
   public Float getCDVersion();

   public String getVDType();
   public void setVDType(String type);
   
   public List getValidValues();
   public void setValidValues(List validValues);
   
   public Representation getRepresentation();
   public void setRepresentation(Representation rep);
   
   public ConceptDerivationRule getConceptDerivationRule();
   public void setConceptDerivationRule(ConceptDerivationRule rule);
   public String getRepresentationPrefName();
   public Float  getRepresentationVersion();
   public String getRepresentationContextName();

   
}