package gov.nih.nci.ncicb.cadsr.ocbrowser.util;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.dto.OCRPackageTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ProjectTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.OCRPackage;

import gov.nih.nci.ncicb.cadsr.common.resource.Project;
import java.util.ArrayList;
import java.util.Collection;
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

  public static Collection getProjects(ObjectClassRelationship ocr)
  {
     List acCSCSIS = ocr.getAcCsCsis();
     if(acCSCSIS==null) return null;
     ListIterator acCSCSISIt = acCSCSIS.listIterator();
     List Projects = new ArrayList();

     Map projectMap = new HashMap();
     Map subProjectMap = new HashMap();

     while(acCSCSISIt.hasNext())
     {
       AdminComponentClassSchemeClassSchemeItem acCsCsi = (AdminComponentClassSchemeClassSchemeItem)acCSCSISIt.next();
       ClassSchemeClassSchemeItem cscsi = acCsCsi.getCsCsi();
       Project newProject = (Project)projectMap.get(cscsi.getCs().getId());
       if(newProject==null)
       {
         newProject = new ProjectTransferObject();
         newProject.setId(cscsi.getCs().getId());
         newProject.setName(cscsi.getCs().getLongName());
         projectMap.put(cscsi.getCs().getId(),newProject);
       }
       //get Sub project

       if(cscsi.getCs().getType().equals(PROJECT_CS_TYPE) &&
             cscsi.getCsi().getType().equals(SUB_PROJECT_CSI_TYPE))
       {
           Project subProject = (Project)subProjectMap.get(cscsi.getId());
           if(subProject==null)
           {
              subProject = new ProjectTransferObject();
              subProject.setId(cscsi.getId());
              subProject.setName(cscsi.getCsi().getName());
              subProjectMap.put(cscsi.getId(),subProject);
              newProject.addChild(subProject);
           }
       }
     }
     acCSCSISIt = acCSCSIS.listIterator();
      while(acCSCSISIt.hasNext())
     {
       AdminComponentClassSchemeClassSchemeItem acCsCsi = (AdminComponentClassSchemeClassSchemeItem)acCSCSISIt.next();
       ClassSchemeClassSchemeItem cscsi = acCsCsi.getCsCsi();
        if(cscsi.getCs().getType().equals(PROJECT_CS_TYPE) &&
             cscsi.getCsi().getType().equals(PACKAGE_CSI_TYPE))
       {

          OCRPackage ocrPackage = new OCRPackageTransferObject();
          ocrPackage.setId(cscsi.getId());
          ocrPackage.setName(cscsi.getCsi().getName());

           if(cscsi.getParent()!=null)
           {
              ClassSchemeClassSchemeItem parentcscsi = cscsi.getParent();
              if(parentcscsi.getCs().getType().equals(PROJECT_CS_TYPE) &&
                   parentcscsi.getCsi().getType().equals(SUB_PROJECT_CSI_TYPE))
               {
                  Project subProject = (Project)subProjectMap.get(parentcscsi.getId());
                  if(subProject!=null)
                  {
                    subProject.addPackage(ocrPackage);
                  }
               }
           }
       }
     }

     return projectMap.values();
  }




}