package gov.nih.nci.ncicb.cadsr.resource;

public interface DataElementConcept extends AdminComponent
{
   public String getDecIdseq();
   public void setDecIdseq(String aDecIdseq);

   public String getCdIdseq(); 
   public void setCdIdseq(String aCdIdseq);

   public String getProplName();
   public void setProplName(String aProplName);

   public String getOclName();
   public void setOclName(String aOclName);
   
   public String getObjClassPublicId();
   public void setObjClassPublicId(String aObjClassPublicId);
   
   public String getObjClassQualifier();
   public void setObjClassQualifier(String aObjClassQualifier);

   public String getPropertyPublicId();
   public void setPropertyPublicId(String aPropertyPublicId);

   public String getPropertyQualifier();
   public void setPropertyQualifier(String aPropertyQualifier);

   public String getChangeNote();
   public void setChangeNote(String aChangeNote);
   public String getObjClassPrefName();

   public String getObjClassContextName();
   public String getPropertyPrefName();

   public String getPropertyContextName();
   public Float getObjClassVersion();
   public Float getPropertyVersion();
   public String getContextName();
   public String getCDContextName();
   public String getCDPrefName();
   public Float getCDVersion();
   public String getCDPublicId();
   public void setCDPublicId(String publicId);
}