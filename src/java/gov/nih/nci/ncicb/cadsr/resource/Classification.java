 /**
   * Getter and setter methods for classifications
   *
   * @author Amit kochar
   * @version 1.0 (8/6/2002)
   */
package gov.nih.nci.ncicb.cadsr.resource;

public interface Classification 
{
   public String getDeIdseq();
   public void setDeIdseq(String aDeIdseq);
   
   public String getClassSchemeName();
   public void setClassSchemeName(String aClassSchemeName);
   
   public String getClassSchemeDefinition();
   public void setClassSchemeDefinition(String aClassSchemeDefinition);
   
   public String getClassSchemeItemName();
   public void setClassSchemeItemName(String aClassSchemeItemName);

   public String getClassSchemeItemType();
   public void setClassSchemeItemType(String aClassSchemeItemType);

   public String getClassSchemePublicId();
   public void setClassSchemePublicId(String publicId);


}
