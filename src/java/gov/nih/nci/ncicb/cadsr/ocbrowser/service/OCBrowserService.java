package gov.nih.nci.ncicb.cadsr.ocbrowser.service;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import java.util.List;

public interface OCBrowserService
{


  public  List getAssociationsForOC(String ocIdseq);

   public  ObjectClass getObjectClass(String ocIdseq);

   public List getInheritenceRelationships(ObjectClass oc);
}