package gov.nih.nci.ncicb.cadsr.dto.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;

import java.sql.ResultSet;
import java.sql.SQLException;


public class JDBCModuleTransferObject extends ModuleTransferObject {
  public JDBCModuleTransferObject(ResultSet rs) throws SQLException {
    setModuleIdseq(rs.getString(1));  // MOD_IDSEQ
    setLongName(rs.getString(8));     // LONG_NAME
    setDisplayOrder(rs.getInt(13));   // DISPLAY_ORDER
    //setForm(new ModuleTransferObject(rs.getString("CRF_IDSEQ")));
  }
}
