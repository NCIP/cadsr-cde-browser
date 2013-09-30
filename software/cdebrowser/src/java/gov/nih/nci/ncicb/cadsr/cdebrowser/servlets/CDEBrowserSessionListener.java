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

package gov.nih.nci.ncicb.cadsr.cdebrowser.servlets;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CDEBrowserSessionListener implements HttpSessionListener{

	protected static Log log = LogFactory.getLog(CDEBrowserSessionListener.class.getName());

	public void sessionCreated(HttpSessionEvent se) {
		if (log.isDebugEnabled()){			
			log.debug("New CDE Browser session " + se.getSession().getId() + " is created");
		}
		CDEBrowserParams.reloadInstance();
		return;
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		if (log.isDebugEnabled()){			
			CDECart cart = (CDECart)se.getSession().getAttribute(CaDSRConstants.CDE_CART);
			if(cart != null){
				cart.expireCart();
			}		
			log.info("Object Cart with userId: PublicUser"+se.getSession().getId()+ " set for expiration");
			log.debug("Session " + se.getSession().getId() + " is about to be destroyed.");
		}
	}
}
