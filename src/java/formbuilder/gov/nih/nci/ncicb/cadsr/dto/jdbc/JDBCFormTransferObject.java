package gov.nih.nci.ncicb.cadsr.dto.jdbc;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCFormTransferObject extends FormTransferObject 
{
  public JDBCFormTransferObject(ResultSet rs) throws SQLException
  {
    setLongName(rs.getString("long_name"));
    setPreferredName(rs.getString("preferred_Name"));
    setContext(new ContextTransferObject(rs.getString("context_name")));
    setProtocol(new ProtocolTransferObject(rs.getString("PROTOCOL_LONG_NAME")));
    setFormType(rs.getString("type"));
    setAslName("workflow");
  }
}