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

package gov.nih.nci.ncicb.cadsr.common.cdebrowser.service;

import java.util.List;

import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;

import java.util.Locale;
import java.util.Properties;

public interface CDEBrowserService 
{
	public Properties getApplicationProperties(Locale locale);
	public Properties reloadApplicationProperties(Locale locale, String username);
	public void populateDataElementAltNameDef (DataElement de);
	public List getReferenceDocuments(String acIdseq);
	//public List getReferenceDocumentsForCSI(String cscsiIdseq);
	public Properties getApplicationProperties(Locale locale, String toolName);
}