package gov.nih.nci.ncicb.cadsr.dto.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;

import java.sql.ResultSet;
import java.sql.SQLException;


public class JDBCFormTransferObject extends FormTransferObject {
  public JDBCFormTransferObject(ResultSet rs) throws SQLException {
    setFormIdseq(rs.getString(1)); // QC_IDSEQ
    setIdseq(rs.getString(1));
    setLongName(rs.getString(9)); //LONG_NAME
    setPreferredName(rs.getString(7)); // PREFERRED_NAME
    //Publish Change Order
    setPreferredDefinition(rs.getString(8)); // PreferredDefinition

    //setContext(new ContextTransferObject(rs.getString("context_name")));
    ContextTransferObject contextTransferObject = new ContextTransferObject();
    contextTransferObject.setConteIdseq(rs.getString(4)); //CONTE_IDSEQ
    contextTransferObject.setName(rs.getString(12)); // CONTEXT_NAME
    setContext(contextTransferObject);
    this.setDateModified(rs.getTimestamp(15));

    setFormType(rs.getString(3)); // TYPE
    setAslName(rs.getString(6)); // WORKFLOW
    setVersion(new Float(rs.getString(2))); // VERSION
    setPreferredDefinition(rs.getString(8)); // PREFERRED_DEFINITION
    setCreatedBy(rs.getString(13)); // CREATED_BY
    this.setFormCategory(rs.getString(5));
    
  }
}
