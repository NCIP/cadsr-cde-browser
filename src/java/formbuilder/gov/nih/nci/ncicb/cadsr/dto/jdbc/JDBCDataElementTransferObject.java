package gov.nih.nci.ncicb.cadsr.dto.jdbc;
import gov.nih.nci.ncicb.cadsr.dto.DataElementTransferObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.Float;

public class JDBCDataElementTransferObject extends DataElementTransferObject
{
  public JDBCDataElementTransferObject(ResultSet rs) throws SQLException
  {
    setDeIdseq(rs.getString(8));       // DE_IDSEQ
    setVersion(new Float(rs.getFloat(16))); // VERSION
    setCDEId(rs.getString(18));        // CDE_ID (public_id)
    setLongName(rs.getString(17));     // LONG_NAME
    setLongCDEName(rs.getString(15));  // Doc Text from Reference_Documents
    setAslName(rs.getString("DE_WORKFLOW"));
  }
}
