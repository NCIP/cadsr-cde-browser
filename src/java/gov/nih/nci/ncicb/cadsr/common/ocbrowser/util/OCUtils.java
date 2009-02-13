package gov.nih.nci.ncicb.cadsr.common.ocbrowser.util;

import gov.nih.nci.cadsr.domain.Concept;
import gov.nih.nci.cadsr.domain.ObjectClass;
import gov.nih.nci.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.common.jsp.bean.OCRNavigationBean;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.struts.common.OCBrowserFormConstants;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class OCUtils implements OCBrowserFormConstants
{

	public OCUtils()
	{
	}

	/**
	 * Groups the ocrs into outgoing, incoming and bidirectional
	 */
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

		ListIterator it = ocrList.listIterator();

		ListIterator uit = ocrList.listIterator();
		while(uit.hasNext())
		{
			ObjectClassRelationship obcr = (ObjectClassRelationship)uit.next();
			if(obcr.getDirection()==null)
				continue;
			else if(obcr.getDirection().equalsIgnoreCase("Bi-Directional"))
				bidirectional.add(obcr);
			else if((obcr.getDirection().equalsIgnoreCase("Source->Destination"))&&
					ocId.equals(obcr.getSourceObjectClass().getId()))
				outgoing.add(obcr);
			else if((obcr.getDirection().equalsIgnoreCase("Source->Destination"))&&
					ocId.equals(obcr.getTargetObjectClass().getId()))
				ingoing.add(obcr);
		}

		return ocrMap;
	}

	/**
	 * Replace the -1 with * since caDSR store multiplicity many as -1
	 */
	public static String getSourceMultiplicityDisplayString(ObjectClassRelationship ocr)
	{
		int low = ocr.getSourceLowMultiplicity();
		int high =  ocr.getSourceHighMultiplicity();
		String highStr = String.valueOf(high);
		if(high==-1)
			highStr = "*";

		return String.valueOf(low)+". ."+highStr;
	}

	/**
	 * Returns false if the Current OCR has already been navigated by the user.
	 * Checks the Navigation bread crumbs for this information
	 */
	public static boolean isNavigationAllowed(HttpServletRequest req,String navigationListId,ObjectClassRelationship currentOcr)
	{
		LinkedList navigationList = (LinkedList)req.getSession().getAttribute(navigationListId);

		if(navigationList==null)
			return true;
		if(navigationList.isEmpty())
			return true;
		if(navigationList.size()<2)
			return true;
		ListIterator it = navigationList.listIterator();
		while(it.hasNext())
		{
			OCRNavigationBean currBean = (OCRNavigationBean)it.next();
			ObjectClassRelationship ocr = currBean.getOcr();
			if(ocr!=null&&currentOcr!=null)
			{
				if(ocr.getId().equals(currentOcr.getId()))
					return false;
			}
		}
		return true;
	}

	/**
	 * Since in bidirectional ocr target and source could be interchangable
	 */
	public static ObjectClass getBiderectionalTarget(ObjectClassRelationship ocr,ObjectClass currObject)
	{
		if(ocr.getTargetObjectClass().getId().equals(currObject.getId()))
		{
			return ocr.getSourceObjectClass();
		}
		else
		{
			return ocr.getTargetObjectClass();
		}
	}

	/**
	 * Check if an oc is source for this bidirectional ocr
	 */
	public static boolean isOCSourceForBidirectionalOCR(ObjectClassRelationship ocr,ObjectClass currObject)
	{
		if(ocr.getSourceObjectClass().getId().equals(currObject.getId()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Check if an oc is source for this bidirectional ocr
	 */
	public static String getCurrentRoleStringForBidirectionalOCRForDisplay(ObjectClassRelationship ocr,ObjectClass currObject)
	{
		if(isOCSourceForBidirectionalOCR(ocr,currObject))
		{
			return "Source";
		}
		else
		{
			return "Target";
		}
	}

	/**
	 * Since in bidirectional ocr target and source could be interchangable
	 */
	public static String getBiderectionalTargetAttributeName(ObjectClassRelationship ocr,ObjectClass currObject)
	{
		if(ocr.getTargetObjectClass().getId().equals(currObject.getId()))
		{
			return "source";
		}
		else
		{
			return "target";
		}
	}

	/**
	 * Replace the -1 with * since caDSR store multiplicity many as -1
	 */
	public static String getTargetMultiplicityDisplayString(ObjectClassRelationship ocr)
	{
		int low = ocr.getTargetLowMultiplicity();
		int high =  ocr.getTargetHighMultiplicity();
		String highStr = String.valueOf(high);
		if(high==-1)
			highStr = "*";

		return String.valueOf(low)+". ."+highStr;
	}

	public static String getConceptCodeUrl(Concept concept, CDEBrowserParams params, String anchorClass)
	{
		String codeUrl = "";
		String hrefBegin1 = "";
		String hrefBegin2 = "";
		String hrefClose = "";

		if(concept!=null)
		{
			String code = concept.getPreferredName();
			String evsStr = getEvsUrlForConcept(concept,params);
			if(evsStr!=null)
			{
				hrefBegin1  = "<a class=\""+anchorClass+"\" TARGET=\"_blank\"  href=\""+evsStr;
				hrefBegin2 = "\">";
				hrefClose = "</a>";
				codeUrl = hrefBegin1+code+hrefBegin2+code+hrefClose;
			}
			else
			{
				hrefBegin1 = "";
				hrefBegin2 = "";
				hrefClose = "";
				codeUrl = code;
			}

		}

		return codeUrl;
	}

	public static String getEvsUrlForConcept(Concept concept, CDEBrowserParams params)
	{
		String evsSource = concept.getEvsSource();
		Map urlMap = params.getEvsUrlMap();
		return (String)urlMap.get(evsSource);
	}
}