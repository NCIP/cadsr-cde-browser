package gov.nih.nci.ncicb.cadsr.common.ocbrowser.service;
import gov.nih.nci.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.cadsr.domain.ObjectClass;
import java.util.List;

public interface OCBrowserService
{
	public List getAssociationsForOC(String ocIdseq);

	public ObjectClass getObjectClass(String ocIdseq);

	public List getInheritenceRelationships(ObjectClass oc);

	public ClassSchemeClassSchemeItem getParentCsCsi(ClassSchemeClassSchemeItem csCsi);
}