package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;

public class ClassificationsViewRowImpl extends ViewRowImpl
{
	public static final int ACIDSEQ = 0;
	public static final int ACTLNAME = 1;
	public static final int VERSION = 2;
	public static final int CONTEIDSEQ = 3;
	public static final int CSIIDSEQ = 4;	
	public static final int CSITLNAME = 5;
	public static final int LONGNAME = 6;	
	public static final int CSIID = 7;
	public static final int CSIVERSION = 8;
	public static final int CSIDSEQ = 9;
	public static final int PREFERREDNAME = 10;
	public static final int PREFERREDDEFINITION = 11;
	public static final int CSID = 12;
	public static final int CSVERSION = 13;
	public static final int CSLONGNAME = 14;	

	/**
	 * This is the default constructor (do not remove)
	 */
	public ClassificationsViewRowImpl()
	{
	}

	/**
	 * Gets AcCsi entity object.
	 */
	public AcCsiImpl getAcCsi()
	{
		return (AcCsiImpl)getEntity(0);
	}

	/**
	 * Gets AdministeredComponents entity object.
	 */
	public AdministeredComponentsImpl getAdministeredComponents()
	{
		return (AdministeredComponentsImpl)getEntity(1);
	}

	/**
	 * Gets ClassificationSchemes entity object.
	 */
	public ClassificationSchemesImpl getClassificationSchemes()
	{
		return (ClassificationSchemesImpl)getEntity(2);
	}

	/**
	 * Gets ClassSchemeItems entity object.
	 */
	public ClassSchemeItemsImpl getClassSchemeItems()
	{
		return (ClassSchemeItemsImpl)getEntity(3);
	}

	/**
	 * Gets CsCsi entity object.
	 */
	public CsCsiImpl getCsCsi()
	{
		return (CsCsiImpl)getEntity(4);
	}

	/**Gets the attribute value for AC_IDSEQ using the alias name AcIdseq.
	 */
	public String getAcIdseq() {
		return (String) getAttributeInternal(ACIDSEQ);
	}

	/**Gets the attribute value for ACTL_NAME using the alias name ActlName.
	 */
	public String getActlName() {
		return (String) getAttributeInternal(ACTLNAME);
	}

	/**Sets <code>value</code> as attribute value for ACTL_NAME using the alias name ActlName.
	 */
	public void setActlName(String value) {
		setAttributeInternal(ACTLNAME, value);
	}

	/**Gets the attribute value for VERSION using the alias name Version.
	 */
	public Number getVersion() {
		return (Number) getAttributeInternal(VERSION);
	}

	/**Sets <code>value</code> as attribute value for VERSION using the alias name Version.
	 */
	public void setVersion(Number value) {
		setAttributeInternal(VERSION, value);
	}

	/**Gets the attribute value for CONTE_IDSEQ using the alias name ConteIdseq.
	 */
	public String getConteIdseq() {
		return (String) getAttributeInternal(CONTEIDSEQ);
	}

	/**Sets <code>value</code> as attribute value for CONTE_IDSEQ using the alias name ConteIdseq.
	 */
	public void setConteIdseq(String value) {
		setAttributeInternal(CONTEIDSEQ, value);
	}

	/**Gets the attribute value for CSITL_NAME using the alias name CsitlName.
	 */
	public String getCsitlName() {
		return (String) getAttributeInternal(CSITLNAME);
	}

	/**Sets <code>value</code> as attribute value for CSITL_NAME using the alias name CsitlName.
	 */
	public void setCsitlName(String value) {
		setAttributeInternal(CSITLNAME, value);
	}

	/**Gets the attribute value for LONG_NAME using the alias name LongName.
	 */
	public String getLongName() {
		return (String) getAttributeInternal(LONGNAME);
	}

	/**Sets <code>value</code> as attribute value for LONG_NAME using the alias name LongName.
	 */
	public void setLongName(String value) {
		setAttributeInternal(LONGNAME, value);
	}

	/**Gets the attribute value for CSI_ID using the alias name CsiId.
	 */
	public Number getCsiId() {
		return (Number) getAttributeInternal(CSIID);
	}
	
	/**Sets <code>value</code> as attribute value for CSI_ID using the alias name CsiId.
	 */
	public void setCsiId(Number value) {
		setAttributeInternal(CSIID, value);
	}

	/**Gets the attribute value for VERSION using the alias name CsiVersion.
	 */
	public Number getCsiVersion() {
		return (Number) getAttributeInternal(CSIVERSION);
	}

	/**Sets <code>value</code> as attribute value for VERSION using the alias name CsiVersion.
	 */
	public void setCsiVersion(Number value) {
		setAttributeInternal(CSIVERSION, value);
	}

	/**Gets the attribute value for CS_IDSEQ using the alias name CsIdseq.
	 */
	public String getCsIdseq() {
		return (String) getAttributeInternal(CSIDSEQ);
	}

	/**Sets <code>value</code> as attribute value for CS_IDSEQ using the alias name CsIdseq.
	 */
	public void setCsIdseq(String value) {
		setAttributeInternal(CSIDSEQ, value);
	}

	/**Gets the attribute value for VERSION using the alias name CsVersion.
	 */
	public Number getCsVersion() {
		return (Number) getAttributeInternal(CSVERSION);
	}

	/**Sets <code>value</code> as attribute value for VERSION using the alias name CsVersion.
	 */
	public void setCsVersion(Number value) {
		setAttributeInternal(CSVERSION, value);
	}

	/**Gets the attribute value for PREFERRED_NAME using the alias name PreferredName.
	 */
	public String getPreferredName() {
		return (String) getAttributeInternal(PREFERREDNAME);
	}

	/**Sets <code>value</code> as attribute value for PREFERRED_NAME using the alias name PreferredName.
	 */
	public void setPreferredName(String value) {
		setAttributeInternal(PREFERREDNAME, value);
	}

	/**Gets the attribute value for PREFERRED_DEFINITION using the alias name PreferredDefinition.
	 */
	public String getPreferredDefinition() {
		return (String) getAttributeInternal(PREFERREDDEFINITION);
	}

	/**Sets <code>value</code> as attribute value for PREFERRED_DEFINITION using the alias name PreferredDefinition.
	 */
	public void setPreferredDefinition(String value) {
		setAttributeInternal(PREFERREDDEFINITION, value);
	}

	/**Gets the attribute value for LONG_NAME using the alias name CsLongName.
	 */
	public String getCsLongName() {
		return (String) getAttributeInternal(LONGNAME);
	}

	/**Sets <code>value</code> as attribute value for LONG_NAME using the alias name CsLongName.
	 */
	public void setCsLongName(String value) {
		setAttributeInternal(LONGNAME, value);
	}

	/**Gets the attribute value for CS_ID using the alias name CsId.
	 */
	public Number getCsId() {
		return (Number) getAttributeInternal(CSID);
	}

	/**Sets <code>value</code> as attribute value for CS_ID using the alias name CsId.
	 */
	public void setCsId(Number value) {
		setAttributeInternal(CSID, value);
	}

	/**Gets the attribute value for CSIIDSEQ using the alias name CsiIdseq.
	 */
	public String getCsiIdseq() {
		return (String) getAttributeInternal(CSIIDSEQ);
	}

	/**getAttrInvokeAccessor: generated method. Do not modify.
	 */
	protected Object getAttrInvokeAccessor(int index, 
			AttributeDefImpl attrDef) throws Exception {
		switch (index) {
		case ACIDSEQ:
			return getAcIdseq();
		case ACTLNAME:
			return getActlName();
		case VERSION:
			return getVersion();
		case CONTEIDSEQ:
			return getConteIdseq();
		case CSITLNAME:
			return getCsitlName();
		case LONGNAME:
			return getLongName();
		case CSIID:
			return getCsiId();
		case CSIVERSION:
			return getCsiVersion();
		case CSIDSEQ:
			return getCsIdseq();
		case CSVERSION:
			return getCsVersion();
		case PREFERREDNAME:
			return getPreferredName();
		case PREFERREDDEFINITION:
			return getPreferredDefinition();
		case CSLONGNAME:
			return getCsLongName();
		case CSID:
			return getCsId();
		case CSIIDSEQ:
			return getCsiIdseq();
		default:
			return super.getAttrInvokeAccessor(index, attrDef);
		}
	}

	/**setAttrInvokeAccessor: generated method. Do not modify.
	 */
	protected void setAttrInvokeAccessor(int index, Object value, 
			AttributeDefImpl attrDef) throws Exception {
		switch (index) {
		case ACTLNAME:
			setActlName((String)value);
			return;
		case VERSION:
			setVersion((Number)value);
			return;
		case CONTEIDSEQ:
			setConteIdseq((String)value);
			return;
		case CSITLNAME:
			setCsitlName((String)value);
			return;
		case LONGNAME:
			setLongName((String)value);
			return;
		case CSIVERSION:
			setCsiVersion((Number)value);
			return;
		case CSIDSEQ:
			setCsIdseq((String)value);
			return;
		case CSVERSION:
			setCsiVersion((Number)value);
			return;
		case PREFERREDNAME:
			setPreferredName((String)value);
			return;
		case PREFERREDDEFINITION:
			setPreferredDefinition((String)value);
			return;
		case CSLONGNAME:
			setCsLongName((String)value);
			return;
		case CSID:
			setCsId((Number)value);
			return;
		default:
			super.setAttrInvokeAccessor(index, value, attrDef);
		return;
		}
	}
}