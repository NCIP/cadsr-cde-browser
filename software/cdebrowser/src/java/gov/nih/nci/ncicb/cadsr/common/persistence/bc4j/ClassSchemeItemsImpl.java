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

import java.sql.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.Key;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ClassSchemeItemsImpl extends EntityImpl {
    public static final int CSIIDSEQ = 0;
    public static final int CSITLNAME = 1;
    public static final int COMMENTS = 2;
    public static final int DATECREATED = 3;
    public static final int CREATEDBY = 4;
    public static final int DATEMODIFIED = 5;
    public static final int MODIFIEDBY = 6;
    public static final int CONDRIDSEQ = 7;
    public static final int PREFERREDNAME = 8;
    public static final int LONGNAME = 9;
    public static final int PREFERREDDEFINITION = 10;
    public static final int CONTEIDSEQ = 11;
    public static final int ASLNAME = 12;
    public static final int DELETEDIND = 13;
    public static final int LATESTVERSIONIND = 14;
    public static final int CSIID = 15;
    public static final int VERSION = 16;
    public static final int ORIGIN = 17;
    public static final int BEGINDATE = 18;
    public static final int ENDDATE = 19;
    public static final int CHANGENOTE = 20;
    private static EntityDefImpl mDefinitionObject;

    /**This is the default constructor (do not remove).
     */
    public ClassSchemeItemsImpl() {
    }

    /**Retrieves the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = (EntityDefImpl)EntityDefImpl.findDefObject("gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.ClassSchemeItems");
        }
        return mDefinitionObject;
    }

    /**Gets the attribute value for CsiIdseq, using the alias name CsiIdseq.
     */
    public String getCsiIdseq() {
        return (String)getAttributeInternal(CSIIDSEQ);
    }

    /**Sets <code>value</code> as the attribute value for CsiIdseq.
     */
    public void setCsiIdseq(String value) {
        setAttributeInternal(CSIIDSEQ, value);
    }

    /**Gets the attribute value for CsitlName, using the alias name CsitlName.
     */
    public String getCsitlName() {
        return (String)getAttributeInternal(CSITLNAME);
    }

    /**Sets <code>value</code> as the attribute value for CsitlName.
     */
    public void setCsitlName(String value) {
        setAttributeInternal(CSITLNAME, value);
    }

    /**Gets the attribute value for Comments, using the alias name Comments.
     */
    public String getComments() {
        return (String)getAttributeInternal(COMMENTS);
    }

    /**Sets <code>value</code> as the attribute value for Comments.
     */
    public void setComments(String value) {
        setAttributeInternal(COMMENTS, value);
    }

    /**Gets the attribute value for DateCreated, using the alias name DateCreated.
     */
    public Date getDateCreated() {
        return (Date)getAttributeInternal(DATECREATED);
    }

    /**Sets <code>value</code> as the attribute value for DateCreated.
     */
    public void setDateCreated(Date value) {
        setAttributeInternal(DATECREATED, value);
    }

    /**Gets the attribute value for CreatedBy, using the alias name CreatedBy.
     */
    public String getCreatedBy() {
        return (String)getAttributeInternal(CREATEDBY);
    }

    /**Sets <code>value</code> as the attribute value for CreatedBy.
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**Gets the attribute value for DateModified, using the alias name DateModified.
     */
    public Date getDateModified() {
        return (Date)getAttributeInternal(DATEMODIFIED);
    }

    /**Sets <code>value</code> as the attribute value for DateModified.
     */
    public void setDateModified(Date value) {
        setAttributeInternal(DATEMODIFIED, value);
    }

    /**Gets the attribute value for ModifiedBy, using the alias name ModifiedBy.
     */
    public String getModifiedBy() {
        return (String)getAttributeInternal(MODIFIEDBY);
    }

    /**Sets <code>value</code> as the attribute value for ModifiedBy.
     */
    public void setModifiedBy(String value) {
        setAttributeInternal(MODIFIEDBY, value);
    }

    /**Gets the attribute value for CondrIdseq, using the alias name CondrIdseq.
     */
    public String getCondrIdseq() {
        return (String)getAttributeInternal(CONDRIDSEQ);
    }

    /**Sets <code>value</code> as the attribute value for CondrIdseq.
     */
    public void setCondrIdseq(String value) {
        setAttributeInternal(CONDRIDSEQ, value);
    }

    /**Gets the attribute value for PreferredName, using the alias name PreferredName.
     */
    public String getPreferredName() {
        return (String)getAttributeInternal(PREFERREDNAME);
    }

    /**Sets <code>value</code> as the attribute value for PreferredName.
     */
    public void setPreferredName(String value) {
        setAttributeInternal(PREFERREDNAME, value);
    }

    /**Gets the attribute value for LongName, using the alias name LongName.
     */
    public String getLongName() {
        return (String)getAttributeInternal(LONGNAME);
    }

    /**Sets <code>value</code> as the attribute value for LongName.
     */
    public void setLongName(String value) {
        setAttributeInternal(LONGNAME, value);
    }

    /**Gets the attribute value for PreferredDefinition, using the alias name PreferredDefinition.
     */
    public String getPreferredDefinition() {
        return (String)getAttributeInternal(PREFERREDDEFINITION);
    }

    /**Sets <code>value</code> as the attribute value for PreferredDefinition.
     */
    public void setPreferredDefinition(String value) {
        setAttributeInternal(PREFERREDDEFINITION, value);
    }

    /**Gets the attribute value for ConteIdseq, using the alias name ConteIdseq.
     */
    public String getConteIdseq() {
        return (String)getAttributeInternal(CONTEIDSEQ);
    }

    /**Sets <code>value</code> as the attribute value for ConteIdseq.
     */
    public void setConteIdseq(String value) {
        setAttributeInternal(CONTEIDSEQ, value);
    }

    /**Gets the attribute value for AslName, using the alias name AslName.
     */
    public String getAslName() {
        return (String)getAttributeInternal(ASLNAME);
    }

    /**Sets <code>value</code> as the attribute value for AslName.
     */
    public void setAslName(String value) {
        setAttributeInternal(ASLNAME, value);
    }

    /**Gets the attribute value for DeletedInd, using the alias name DeletedInd.
     */
    public String getDeletedInd() {
        return (String)getAttributeInternal(DELETEDIND);
    }

    /**Sets <code>value</code> as the attribute value for DeletedInd.
     */
    public void setDeletedInd(String value) {
        setAttributeInternal(DELETEDIND, value);
    }

    /**Gets the attribute value for LatestVersionInd, using the alias name LatestVersionInd.
     */
    public String getLatestVersionInd() {
        return (String)getAttributeInternal(LATESTVERSIONIND);
    }

    /**Sets <code>value</code> as the attribute value for LatestVersionInd.
     */
    public void setLatestVersionInd(String value) {
        setAttributeInternal(LATESTVERSIONIND, value);
    }

    /**Gets the attribute value for CsiId, using the alias name CsiId.
     */
    public Number getCsiId() {
        return (Number)getAttributeInternal(CSIID);
    }

    /**Sets <code>value</code> as the attribute value for CsiId.
     */
    public void setCsiId(Number value) {
        setAttributeInternal(CSIID, value);
    }

    /**Gets the attribute value for Version, using the alias name Version.
     */
    public Number getVersion() {
        return (Number)getAttributeInternal(VERSION);
    }

    /**Sets <code>value</code> as the attribute value for Version.
     */
    public void setVersion(Number value) {
        setAttributeInternal(VERSION, value);
    }

    /**Gets the attribute value for Origin, using the alias name Origin.
     */
    public String getOrigin() {
        return (String)getAttributeInternal(ORIGIN);
    }

    /**Sets <code>value</code> as the attribute value for Origin.
     */
    public void setOrigin(String value) {
        setAttributeInternal(ORIGIN, value);
    }

    /**Gets the attribute value for BeginDate, using the alias name BeginDate.
     */
    public Date getBeginDate() {
        return (Date)getAttributeInternal(BEGINDATE);
    }

    /**Sets <code>value</code> as the attribute value for BeginDate.
     */
    public void setBeginDate(Date value) {
        setAttributeInternal(BEGINDATE, value);
    }

    /**Gets the attribute value for EndDate, using the alias name EndDate.
     */
    public Date getEndDate() {
        return (Date)getAttributeInternal(ENDDATE);
    }

    /**Sets <code>value</code> as the attribute value for EndDate.
     */
    public void setEndDate(Date value) {
        setAttributeInternal(ENDDATE, value);
    }

    /**Gets the attribute value for ChangeNote, using the alias name ChangeNote.
     */
    public String getChangeNote() {
        return (String)getAttributeInternal(CHANGENOTE);
    }

    /**Sets <code>value</code> as the attribute value for ChangeNote.
     */
    public void setChangeNote(String value) {
        setAttributeInternal(CHANGENOTE, value);
    }

    /**getAttrInvokeAccessor: generated method. Do not modify.
     */
    protected Object getAttrInvokeAccessor(int index, 
                                           AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case CSIIDSEQ:
            return getCsiIdseq();
        case CSITLNAME:
            return getCsitlName();
        case COMMENTS:
            return getComments();
        case DATECREATED:
            return getDateCreated();
        case CREATEDBY:
            return getCreatedBy();
        case DATEMODIFIED:
            return getDateModified();
        case MODIFIEDBY:
            return getModifiedBy();
        case CONDRIDSEQ:
            return getCondrIdseq();
        case PREFERREDNAME:
            return getPreferredName();
        case LONGNAME:
            return getLongName();
        case PREFERREDDEFINITION:
            return getPreferredDefinition();
        case CONTEIDSEQ:
            return getConteIdseq();
        case ASLNAME:
            return getAslName();
        case DELETEDIND:
            return getDeletedInd();
        case LATESTVERSIONIND:
            return getLatestVersionInd();
        case CSIID:
            return getCsiId();
        case VERSION:
            return getVersion();
        case ORIGIN:
            return getOrigin();
        case BEGINDATE:
            return getBeginDate();
        case ENDDATE:
            return getEndDate();
        case CHANGENOTE:
            return getChangeNote();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

    /**setAttrInvokeAccessor: generated method. Do not modify.
     */
    protected void setAttrInvokeAccessor(int index, Object value, 
                                         AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case CSIIDSEQ:
            setCsiIdseq((String)value);
            return;
        case CSITLNAME:
            setCsitlName((String)value);
            return;
        case COMMENTS:
            setComments((String)value);
            return;
        case DATECREATED:
            setDateCreated((Date)value);
            return;
        case CREATEDBY:
            setCreatedBy((String)value);
            return;
        case DATEMODIFIED:
            setDateModified((Date)value);
            return;
        case MODIFIEDBY:
            setModifiedBy((String)value);
            return;
        case CONDRIDSEQ:
            setCondrIdseq((String)value);
            return;
        case PREFERREDNAME:
            setPreferredName((String)value);
            return;
        case LONGNAME:
            setLongName((String)value);
            return;
        case PREFERREDDEFINITION:
            setPreferredDefinition((String)value);
            return;
        case CONTEIDSEQ:
            setConteIdseq((String)value);
            return;
        case ASLNAME:
            setAslName((String)value);
            return;
        case DELETEDIND:
            setDeletedInd((String)value);
            return;
        case LATESTVERSIONIND:
            setLatestVersionInd((String)value);
            return;
        case CSIID:
            setCsiId((Number)value);
            return;
        case VERSION:
            setVersion((Number)value);
            return;
        case ORIGIN:
            setOrigin((String)value);
            return;
        case BEGINDATE:
            setBeginDate((Date)value);
            return;
        case ENDDATE:
            setEndDate((Date)value);
            return;
        case CHANGENOTE:
            setChangeNote((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Creates a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String csiIdseq) {
        return new Key(new Object[]{csiIdseq});
    }
}
