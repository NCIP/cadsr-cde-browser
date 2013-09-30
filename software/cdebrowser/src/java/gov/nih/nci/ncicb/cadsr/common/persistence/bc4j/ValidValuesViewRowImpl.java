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

package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j;

import oracle.jbo.domain.Number;

import oracle.jbo.domain.Date;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ValidValuesViewRowImpl extends ViewRowImpl {
    public static final int NAME = 0;
    public static final int CONTEIDSEQ = 1;
	public static final int PVIDSEQ = 2;
    public static final int VALUE = 3;
    public static final int MEANINGDESCRIPTION = 4;
    public static final int VDIDSEQ = 5;
    public static final int ASLNAME = 6;
    public static final int CONDRIDSEQ = 7;
    public static final int PREFERREDDEFINITION = 8;
    public static final int LONGNAME = 9;
    public static final int VERSION = 10;
    public static final int VMID = 11;
    public static final int VPIDSEQ = 12;
    public static final int BEGINDATE = 13;
    public static final int ENDDATE = 14;
    public static final int VMIDSEQ = 15;

    /**This is the default constructor (do not remove).
     */
    public ValidValuesViewRowImpl() {
    }
    
    /**Gets Contexts entity object.
     */
    public ContextsImpl getContexts() {
        return (ContextsImpl)getEntity(0);
    }

    /**Gets PermissibleValues entity object.
     */
    public PermissibleValuesImpl getPermissibleValues() {
        return (PermissibleValuesImpl)getEntity(1);
    }

    /**Gets ValueDomains entity object.
     */
    public ValueDomainsImpl getValueDomains() {
        return (ValueDomainsImpl)getEntity(2);
    }

    /**Gets ValueMeaningsLov entity object.
     */
    public ValueMeaningsLovImpl getValueMeaningsLov() {
        return (ValueMeaningsLovImpl)getEntity(3);
    }

    /**Gets VdPvs entity object.
     */
    public VdPvsImpl getVdPvs() {
        return (VdPvsImpl)getEntity(4);
    }
    
    /**Gets the attribute value for NAME using the alias name Name.
     */
    public String getName() {
        return (String) getAttributeInternal(NAME);
    }

    /**Sets <code>value</code> as attribute value for NAME using the alias name Name.
     */
    public void setName(String value) {
        setAttributeInternal(NAME, value);
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

    /**Gets the attribute value for PV_IDSEQ using the alias name PvIdseq.
     */
    public String getPvIdseq() {
        return (String) getAttributeInternal(PVIDSEQ);
    }

    /**Sets <code>value</code> as attribute value for PV_IDSEQ using the alias name PvIdseq.
     */
    public void setPvIdseq(String value) {
        setAttributeInternal(PVIDSEQ, value);
    }

    /**Gets the attribute value for VALUE using the alias name Value.
     */
    public String getValue() {
        return (String) getAttributeInternal(VALUE);
    }

    /**Sets <code>value</code> as attribute value for VALUE using the alias name Value.
     */
    public void setValue(String value) {
        setAttributeInternal(VALUE, value);
    }

    /**Gets the attribute value for MEANING_DESCRIPTION using the alias name MeaningDescription.
     */
    public String getMeaningDescription() {
        return (String) getAttributeInternal(MEANINGDESCRIPTION);
    }

    /**Sets <code>value</code> as attribute value for MEANING_DESCRIPTION using the alias name MeaningDescription.
     */
    public void setMeaningDescription(String value) {
        setAttributeInternal(MEANINGDESCRIPTION, value);
    }

    /**Gets the attribute value for VD_IDSEQ using the alias name VdIdseq.
     */
    public String getVdIdseq() {
        return (String) getAttributeInternal(VDIDSEQ);
    }

    /**Sets <code>value</code> as attribute value for VD_IDSEQ using the alias name VdIdseq.
     */
    public void setVdIdseq(String value) {
        setAttributeInternal(VDIDSEQ, value);
    }
    
    /**Gets the attribute value for ASL_NAME using the alias name AslName.
     */
    public String getAslName() {
        return (String) getAttributeInternal(ASLNAME);
    }

    /**Sets <code>value</code> as attribute value for ASL_NAME using the alias name AslName.
     */
    public void setAslName(String value) {
        setAttributeInternal(ASLNAME, value);
    }

    /**Gets the attribute value for CONDR_IDSEQ using the alias name CondrIdseq.
     */
    public String getCondrIdseq() {
        return (String) getAttributeInternal(CONDRIDSEQ);
    }

    /**Sets <code>value</code> as attribute value for CONDR_IDSEQ using the alias name CondrIdseq.
     */
    public void setCondrIdseq(String value) {
        setAttributeInternal(CONDRIDSEQ, value);
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

    /**Gets the attribute value for VM_ID using the alias name VmId.
     */
    public Number getVmId() {
        return (Number) getAttributeInternal(VMID);
    }

    /**Sets <code>value</code> as attribute value for VM_ID using the alias name VmId.
     */
    public void setVmId(Number value) {
        setAttributeInternal(VMID, value);
    }

    /**Gets the attribute value for VP_IDSEQ using the alias name VpIdseq.
     */
    public String getVpIdseq() {
        return (String) getAttributeInternal(VPIDSEQ);
    }

    /**Sets <code>value</code> as attribute value for VP_IDSEQ using the alias name VpIdseq.
     */
    public void setVpIdseq(String value) {
        setAttributeInternal(VPIDSEQ, value);
    }

    /**Gets the attribute value for BEGIN_DATE using the alias name BeginDate.
     */
    public Date getBeginDate() {
        return (Date) getAttributeInternal(BEGINDATE);
    }

    /**Sets <code>value</code> as attribute value for BEGIN_DATE using the alias name BeginDate.
     */
    public void setBeginDate(Date value) {
        setAttributeInternal(BEGINDATE, value);
    }

    /**Gets the attribute value for END_DATE using the alias name EndDate.
     */
    public Date getEndDate() {
        return (Date) getAttributeInternal(ENDDATE);
    }

    /**Sets <code>value</code> as attribute value for END_DATE using the alias name EndDate.
     */
    public void setEndDate(Date value) {
        setAttributeInternal(ENDDATE, value);
    }

    /**Gets the attribute value for VM_IDSEQ using the alias name VmIdseq.
     */
    public String getVmIdseq() {
        return (String) getAttributeInternal(VMIDSEQ);
    }

    /**Sets <code>value</code> as attribute value for VM_IDSEQ using the alias name VmIdseq.
     */
    public void setVmIdseq(String value) {
        setAttributeInternal(VMIDSEQ, value);
    }

    /**getAttrInvokeAccessor: generated method. Do not modify.
     */
    protected Object getAttrInvokeAccessor(int index, 
                                           AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case NAME:
            return getName();
        case CONTEIDSEQ:
            return getConteIdseq();
        case PVIDSEQ:
            return getPvIdseq();
        case VALUE:
            return getValue();
        case MEANINGDESCRIPTION:
            return getMeaningDescription();
        case VDIDSEQ:
            return getVdIdseq();
        case ASLNAME:
            return getAslName();
        case CONDRIDSEQ:
            return getCondrIdseq();
        case PREFERREDDEFINITION:
            return getPreferredDefinition();
        case LONGNAME:
            return getLongName();
        case VERSION:
            return getVersion();
        case VMID:
            return getVmId();
        case VPIDSEQ:
            return getVpIdseq();
        case BEGINDATE:
            return getBeginDate();
        case ENDDATE:
            return getEndDate();
        case VMIDSEQ:
            return getVmIdseq();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

    /**setAttrInvokeAccessor: generated method. Do not modify.
     */
    protected void setAttrInvokeAccessor(int index, Object value, 
                                         AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case NAME:
            setName((String)value);
            return;
        case CONTEIDSEQ:
            setConteIdseq((String)value);
            return;        
        case PVIDSEQ:
            setPvIdseq((String)value);
            return;
        case VALUE:
            setValue((String)value);
            return;
        case MEANINGDESCRIPTION:
            setMeaningDescription((String)value);
            return;
        case VDIDSEQ:
            setVdIdseq((String)value);
            return;
        case ASLNAME:
            setAslName((String)value);
            return;
        case CONDRIDSEQ:
            setCondrIdseq((String)value);
            return;
        case PREFERREDDEFINITION:
            setPreferredDefinition((String)value);
            return;
        case LONGNAME:
            setLongName((String)value);
            return;
        case VERSION:
            setVersion((Number)value);
            return;
        case VMID:
            setVmId((Number)value);
            return;
        case VPIDSEQ:
            setVpIdseq((String)value);
            return;
        case BEGINDATE:
            setBeginDate((Date)value);
            return;
        case ENDDATE:
            setEndDate((Date)value);
            return;
        case VMIDSEQ:
            setVmIdseq((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }
}
