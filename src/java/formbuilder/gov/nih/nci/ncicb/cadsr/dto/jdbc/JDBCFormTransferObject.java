package gov.nih.nci.ncicb.cadsr.dto.jdbc;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCFormTransferObject extends FormTransferObject 
{
  public JDBCFormTransferObject(ResultSet rs) throws SQLException
  {
    setPreferredName(rs.getString("preferredName"));
  }
}