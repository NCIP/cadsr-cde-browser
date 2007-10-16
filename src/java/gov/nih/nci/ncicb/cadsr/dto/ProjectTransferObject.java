package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.OCRPackage;
import gov.nih.nci.ncicb.cadsr.resource.Project;
import java.util.ArrayList;
import java.util.List;

public class ProjectTransferObject implements Project
{

  private String name;
  private String id;
  //subprojects
  private List children;
  
  private List packages;


  public void setName(String name)
  {
    this.name = name;
  }


  public String getName()
  {
    return name;
  }


  public void setId(String id)
  {
    this.id = id;
  }


  public String getId()
  {
    return id;
  }


  public void setChildren(List children)
  {
    this.children = children;
  }

  public void addChild(Project subProject)
  {
    if(children==null)
      children = new ArrayList();
    children.add(subProject);
  }
  public List getChildren()
  {
    return children;
  }


  public void setPackages(List packages)
  {
    this.packages = packages;
  }


  public List getPackages()
  {
    return packages;
  }
  public void addPackage(OCRPackage ocrPackage)
  {
     if(packages==null)
      packages = new ArrayList();
    packages.add(ocrPackage);   
  }

  
}