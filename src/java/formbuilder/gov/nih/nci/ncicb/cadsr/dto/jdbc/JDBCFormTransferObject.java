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
    setLongName(rs.getString(9));       //LONG_NAME
    setPreferredName(rs.getString(7));  // PREFERRED_NAME
    
    //setContext(new ContextTransferObject(rs.getString("context_name")));
    ContextTransferObject contextTransferObject = new ContextTransferObject();
    contextTransferObject.setName(rs.getString(12));  // CONTEXT_NAME
    setContext(contextTransferObject);
    
    setProtocol(new ProtocolTransferObject(rs.getString(11))); //PROTOCOL_LONG_NAME
    setFormType(rs.getString(3)); // TYPE
    setAslName(rs.getString(6)); // WORKFLOW
  }
}