package gov.nih.nci.ncicb.cadsr.common.resource.handler;

import gov.nih.nci.ncicb.cadsr.common.resource.Contact;

import java.util.List;

public interface AdminComponentHandler {

	public List<Contact> getACContacts(String acIdSeq);
}
