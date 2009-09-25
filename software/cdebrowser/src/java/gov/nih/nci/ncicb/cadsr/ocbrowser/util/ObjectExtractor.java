package gov.nih.nci.ncicb.cadsr.ocbrowser.util;

import gov.nih.nci.cadsr.domain.AdministeredComponentClassSchemeItem;
import gov.nih.nci.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.common.dto.OCRPackageTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ProjectTransferObject;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.service.OCBrowserService;
import gov.nih.nci.ncicb.cadsr.common.resource.OCRPackage;
import gov.nih.nci.ncicb.cadsr.common.resource.Project;
import gov.nih.nci.ncicb.cadsr.ocbrowser.service.impl.OCBrowserServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ObjectExtractor
{
	private static String PROJECT_CS_TYPE = "Project";
	private static String SUB_PROJECT_CSI_TYPE = "UML_PACKAGE_ALIAS";
	private static String PACKAGE_CSI_TYPE = "UML_PACKAGE_NAME";

	private static OCBrowserService ocService = new OCBrowserServiceImpl(); 

	public ObjectExtractor()
	{
	}

	public static Collection getProjects(ObjectClassRelationship ocr)
	{
		Collection<AdministeredComponentClassSchemeItem> acCSCSIS = ocr.getAdministeredComponentClassSchemeItemCollection();
		if(acCSCSIS==null) return null;
		Iterator acCSCSISIt = acCSCSIS.iterator();
		List Projects = new ArrayList();

		Map projectMap = new HashMap();
		Map subProjectMap = new HashMap();

		while(acCSCSISIt.hasNext())
		{
			AdministeredComponentClassSchemeItem acCsCsi = (AdministeredComponentClassSchemeItem)acCSCSISIt.next();
			ClassSchemeClassSchemeItem cscsi = acCsCsi.getClassSchemeClassSchemeItem();
			Project newProject = (Project)projectMap.get(cscsi.getClassificationScheme().getId());
			if(newProject==null)
			{
				newProject = new ProjectTransferObject();
				newProject.setId(cscsi.getClassificationScheme().getId());
				newProject.setName(cscsi.getClassificationScheme().getLongName());
				projectMap.put(cscsi.getClassificationScheme().getId(),newProject);
			}
			//get Sub project

			if(cscsi.getClassificationScheme().getType().equals(PROJECT_CS_TYPE) &&
					cscsi.getClassificationSchemeItem().getType().equals(SUB_PROJECT_CSI_TYPE))
			{
				Project subProject = (Project)subProjectMap.get(cscsi.getId());
				if(subProject==null)
				{
					subProject = new ProjectTransferObject();
					subProject.setId(cscsi.getId());
					subProject.setName(cscsi.getClassificationSchemeItem().getLongName());
					subProjectMap.put(cscsi.getId(),subProject);
					newProject.addChild(subProject);
				}
			}
		}
		acCSCSISIt = acCSCSIS.iterator();
		while(acCSCSISIt.hasNext())
		{
			AdministeredComponentClassSchemeItem acCsCsi = (AdministeredComponentClassSchemeItem)acCSCSISIt.next();
			ClassSchemeClassSchemeItem cscsi = acCsCsi.getClassSchemeClassSchemeItem();
			if(cscsi.getClassificationScheme().getType().equals(PROJECT_CS_TYPE) &&
					cscsi.getClassificationSchemeItem().getType().equals(PACKAGE_CSI_TYPE))
			{

				OCRPackage ocrPackage = new OCRPackageTransferObject();
				ocrPackage.setId(cscsi.getId());
				ocrPackage.setName(cscsi.getClassificationSchemeItem().getLongName());

				ClassSchemeClassSchemeItem parentCsCsi = ocService.getParentCsCsi(cscsi);

				if(parentCsCsi != null)
				{
					if(parentCsCsi.getClassificationScheme().getType().equals(PROJECT_CS_TYPE) &&
							parentCsCsi.getClassificationSchemeItem().getType().equals(SUB_PROJECT_CSI_TYPE))
					{
						Project subProject = (Project)subProjectMap.get(parentCsCsi.getId());
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