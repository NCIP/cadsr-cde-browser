package gov.nih.nci.ncicb.cadsr.resource;

public interface ValueDomain extends AdminComponent 
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
   public Float getCDVersion();

   public String getVDType();
   public void setVDType(String type);
   
}