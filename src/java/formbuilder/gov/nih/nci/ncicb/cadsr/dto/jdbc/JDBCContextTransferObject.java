package gov.nih.nci.ncicb.cadsr.dto.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCContextTransferObject extends ContextTransferObject 
{
  public JDBCContextTransferObject(ResultSet rs) throws SQLException
  {
    setConteIdseq(rs.getString(1)); //CONTE_IDSEQ
    setName(rs.getString(2));  // NAME
  }
}