package gov.nih.nci.ncicb.cadsr.umlbrowser.service;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import java.util.List;

public interface UmlBrowserService 
{


  public  List getAssociationsForOC(String ocIdseq);
  
   public  ObjectClass getObjectClass(String ocIdseq);
}