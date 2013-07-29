/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.resource.handler;

import gov.nih.nci.ncicb.cadsr.common.resource.Contact;

import java.util.List;

public interface AdminComponentHandler {

	public List<Contact> getACContacts(String acIdSeq);
}
