package gov.nih.nci.ncicb.cadsr.persistence.dao;
import java.util.Collection;
import java.util.List;

public interface FormDAO  {
  
   public Collection getFormsByContext(String context);
}