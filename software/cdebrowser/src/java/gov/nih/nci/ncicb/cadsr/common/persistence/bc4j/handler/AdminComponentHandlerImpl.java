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

package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.handler;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AdminComponentDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.AdminComponent;
import gov.nih.nci.ncicb.cadsr.common.resource.Contact;
import gov.nih.nci.ncicb.cadsr.common.resource.handler.AdminComponentHandler;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorFactory;

import java.util.List;

import oracle.cle.persistence.Handler;

public class AdminComponentHandlerImpl extends Handler 
										implements AdminComponentHandler, 
													CaDSRConstants {
	public Class getReferenceClass() {
		return AdminComponent.class;
	}

	public List<Contact> getACContacts(String acIdSeq) {
		ServiceLocator locator = ServiceLocatorFactory.getLocator(CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
		AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
		AdminComponentDAO adminDAO = daoFactory.getAdminComponentDAO();
		return adminDAO.getContacts(acIdSeq);
	}

}
