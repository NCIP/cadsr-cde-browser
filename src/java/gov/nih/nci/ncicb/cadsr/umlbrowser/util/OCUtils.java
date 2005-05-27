package gov.nih.nci.ncicb.cadsr.umlbrowser.util;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.umlbrowser.struts.common.UmlBrowserFormConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class OCUtils implements UmlBrowserFormConstants
{
   
  public OCUtils()
  {
  }
  
  public static Map sortByOCRTypes(List ocrList,String ocId)
  {
    
    
    List outgoing = new ArrayList();
    List ingoing = new ArrayList();
    List bidirectional = new ArrayList();
    Map ocrMap = new HashMap();
    ocrMap.put(OUT_GOING_OCRS,outgoing);
    ocrMap.put(IN_COMMING_OCRS,ingoing);
    ocrMap.put(BIDIRECTIONAL_OCRS,bidirectional);
    if(ocrList==null)
      return ocrMap;
    //remove duplicates this need to fixed at the hibernate level
   ListIterator it = ocrList.listIterator();
   List uniqueList = new ArrayList();
   List idList = new ArrayList();
    while(it.hasNext())
    {
      ObjectClassRelationship obcr = (ObjectClassRelationship)it.next();
      if(!idList.contains(obcr.getId()))
      {
        idList.add(obcr.getId());
        uniqueList.add(obcr);
      }
    }
    
    ListIterator uit = uniqueList.listIterator();
    while(uit.hasNext())
    {
      ObjectClassRelationship obcr = (ObjectClassRelationship)uit.next();
      if(obcr.getDirection().equalsIgnoreCase(obcr.DIRECTION_BOTH))
        bidirectional.add(obcr);
      if((obcr.getDirection().equalsIgnoreCase(obcr.DIRECTION_SINGLE))&&
          ocId.equals(obcr.getSource().getId()))
        outgoing.add(obcr);
      if((obcr.getDirection().equalsIgnoreCase(obcr.DIRECTION_SINGLE))&&
          ocId.equals(obcr.getTarget().getId()))
        ingoing.add(obcr);        
    }
    
    return ocrMap;
  } 
  
  public static String getSourceMultiplicityDisplayString(ObjectClassRelationship ocr)
  {
    int low = ocr.getSourceLowCardinality();
    int high =  ocr.getSourceHighCardinality();
    String highStr = String.valueOf(high);
    if(high==-1)
      highStr = "*";
      
    return String.valueOf(low)+". ."+highStr;
  }
  
  public static String getTargetMultiplicityDisplayString(ObjectClassRelationship ocr)
  {
    int low = ocr.getTargetLowCardinality();
    int high =  ocr.getTargetHighCardinality();
    String highStr = String.valueOf(high);
    if(high==-1)
      highStr = "*";
      
    return String.valueOf(low)+". ."+highStr;    
  }  
  
}