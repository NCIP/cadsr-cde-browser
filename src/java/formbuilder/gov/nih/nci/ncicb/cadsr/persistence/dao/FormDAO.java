package gov.nih.nci.ncicb.cadsr.persistence.dao;
import java.util.Collection;
import java.util.List;

public interface FormDAO  {
  
   public Collection getAllForms(String formName, String protocol, String context, 
    String workflow, String category, String type);
}