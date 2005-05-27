package gov.nih.nci.ncicb.cadsr.umlbrowser.util;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.dto.OCRPackageTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProjectTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.OCRPackage;

import gov.nih.nci.ncicb.cadsr.resource.Project;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ObjectExtractor
{
  private static String PROJECT_CS_TYPE = "Project";
  private static String SUB_PROJECT_CSI_TYPE = "UML_PACKAGE_ALIAS";
  private static String PACKAGE_CSI_TYPE = "UML_PACKAGE_NAME";
  
  public ObjectExtractor()
  {
  }

  public static List getSubProjects(ObjectClassRelationship ocr)
  {
     List acCSCSIS = ocr.getAcCsCsis();
     if(acCSCSIS==null) return null;
     ListIterator acCSCSISIt = acCSCSIS.listIterator();
     List subprojects = new ArrayList();
     Map tempSubProjectMap =  new HashMap();
     while(acCSCSISIt.hasNext())
     {
       AdminComponentClassSchemeClassSchemeItem acCsCsi = (AdminComponentClassSchemeClassSchemeItem)acCSCSISIt.next();
       ClassSchemeClassSchemeItem cscsi = acCsCsi.getCsCsi();
       Project currSubProject = null;
       if(cscsi.getCs().getType().equals(PROJECT_CS_TYPE) &&
             cscsi.getCsi().getType().equals(SUB_PROJECT_CSI_TYPE))
       {
         currSubProject =  (Project)tempSubProjectMap.get(cscsi.getId());
         if(currSubProject==null)
         {
           Project currProject = new ProjectTransferObject();
           currProject.setLongName(cscsi.getCs().getLongName()); 
           
           currSubProject =  new ProjectTransferObject();
           currSubProject.setParent(currProject);
           currSubProject.setLongName(cscsi.getCsi().getName());
           
           subprojects.add(currSubProject);
           tempSubProjectMap.put(cscsi.getId(),currSubProject);           
         }
       }
     }
     return subprojects;
  }


  public static List getPackages(ObjectClassRelationship ocr)
  {
  
     List acCSCSIS = ocr.getAcCsCsis();
     if(acCSCSIS==null) return null;
     ListIterator acCSCSISIt = acCSCSIS.listIterator();
     List packages = new ArrayList();
     Map tempSubProjectMap =  new HashMap();
     while(acCSCSISIt.hasNext())
     {
       AdminComponentClassSchemeClassSchemeItem acCsCsi = (AdminComponentClassSchemeClassSchemeItem)acCSCSISIt.next();
       ClassSchemeClassSchemeItem cscsi = acCsCsi.getCsCsi();
       Project currSubProject = null;
       if(cscsi.getCs().getType().equals(PROJECT_CS_TYPE) &&
             cscsi.getCsi().getType().equals(PACKAGE_CSI_TYPE))
       {
         currSubProject =  (Project)tempSubProjectMap.get(cscsi.getId());
         if(currSubProject==null)
         {
           Project currProject = new ProjectTransferObject();
           currProject.setLongName(cscsi.getCs().getLongName());        ;
           
           OCRPackage currPackage = new OCRPackageTransferObject();
           currPackage.setParent(currProject);
           currPackage.setLongName(cscsi.getCsi().getName());
           
           packages.add(currPackage);
           tempSubProjectMap.put(cscsi.getId(),currPackage);           
         }
       }
     }
     return packages;
     
  }
}