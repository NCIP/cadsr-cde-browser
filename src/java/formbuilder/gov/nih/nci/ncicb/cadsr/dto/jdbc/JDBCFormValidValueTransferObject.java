package gov.nih.nci.ncicb.cadsr.dto.jdbc;
import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCFormValidValueTransferObject extends FormValidValueTransferObject 
{
  public JDBCFormValidValueTransferObject(ResultSet rs) throws SQLException
  {
    setValueIdseq(rs.getString(1));     // VV_IDSEQ
    setLongName(rs.getString(9));       // LONG_NAME
    setDisplayOrder(rs.getInt(14));     // DISPLAY_ORDER
  }

}
