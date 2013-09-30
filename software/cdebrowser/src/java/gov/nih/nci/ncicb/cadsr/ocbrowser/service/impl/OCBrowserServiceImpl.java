/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.ocbrowser.service.impl;

import gov.nih.nci.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.cadsr.domain.ObjectClass;
import gov.nih.nci.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.service.OCBrowserService;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

public class OCBrowserServiceImpl implements OCBrowserService
{
	private ApplicationService appService = null;

	public OCBrowserServiceImpl()
	{
	}

	private void getCadsrService(){
		try {
			if (appService == null)
			{
				CDEBrowserParams params = CDEBrowserParams.getInstance();
				String url = params.getCadsrAPIUrl();				
				if (!url.equals(""))
					appService = ApplicationServiceProvider.getApplicationServiceFromUrl(url, "CaDsrServiceInfo");
				else
					appService = ApplicationServiceProvider.getApplicationService("CaDsrServiceInfo");
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}

	/**
	 * Returns a list of ocrs
	 */
	public  List getAssociationsForOC(String ocIdseq)
	{		
		DetachedCriteria criteria = DetachedCriteria.forClass(ObjectClassRelationship.class);
		criteria.add(Expression.or(Expression.eq("sourceObjectClass.id", ocIdseq),Expression.eq("targetObjectClass.id", ocIdseq)));
		List result;		
		try {
			this.getCadsrService();
			result = appService.query(criteria);						
		} catch (ApplicationException e1) {
			throw new RuntimeException(e1);
		}		
		return new ArrayList(new HashSet(result));
	}

	/**
	 * Returns object class
	 */
	public  ObjectClass getObjectClass(String ocIdseq)
	{		
		ObjectClass searchOc = new ObjectClass();
		searchOc.setId(ocIdseq);

		List result;
		try {
			this.getCadsrService();
			result = appService.search(searchOc.getClass(), searchOc);
		} catch (ApplicationException e) {
			throw new RuntimeException(e);
		}
		ObjectClass oc = (ObjectClass)result.get(0);		
		return oc;
	}

	/**
	 * Return all the super classes, The list sorted from super classes to subclasses
	 */
	public List getInheritenceRelationships(ObjectClass oc)
	{		
		List superClasses =  new ArrayList();
		ObjectClassRelationship ocr = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(ObjectClassRelationship.class);
		criteria.createCriteria("sourceObjectClass").add(Expression.eq("id", oc.getId()));
		criteria.add(Expression.eq("name", "IS_A"));
		List result;
		try {
			this.getCadsrService();
			result = appService.query(criteria);
		} catch (ApplicationException e1) {
			throw new RuntimeException(e1);
		}

		if(!result.isEmpty())
			ocr = (ObjectClassRelationship)result.get(0);

		if(ocr!=null&&ocr.getSourceObjectClass()!=null)
			superClasses.add(ocr.getSourceObjectClass());
		while(ocr!=null&&ocr.getTargetObjectClass()!=null)
		{
			superClasses.add(0,ocr.getTargetObjectClass());

			criteria = DetachedCriteria.forClass(ObjectClassRelationship.class);
			criteria.createCriteria("sourceObjectClass").add(Expression.eq("id", ocr.getTargetObjectClass().getId()));
			criteria.add(Expression.eq("name", "IS_A"));
			try {
				this.getCadsrService();
				result = appService.query(criteria);
			} catch (ApplicationException e) {
				throw new RuntimeException(e);
			}

			if(!result.isEmpty())
				ocr = (ObjectClassRelationship)result.get(0);
			else ocr = null;
		}		
		return superClasses;
	}

	public ClassSchemeClassSchemeItem getParentCsCsi(ClassSchemeClassSchemeItem csCsi) {		
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassSchemeClassSchemeItem.class);
		criteria.createCriteria("childClassSchemeClassSchemeItemCollection")
		.add(Restrictions.idEq(csCsi.getId()));

		List result = null;
		try {
			this.getCadsrService();
			result = appService.query(criteria);
		} catch (ApplicationException e) {
			throw new RuntimeException(e);
		}

		if(result.size() > 0){			
			return (ClassSchemeClassSchemeItem)result.get(0);
		}else {			
			return null;
		}		
	}
}