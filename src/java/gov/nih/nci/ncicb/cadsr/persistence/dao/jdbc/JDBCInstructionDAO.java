package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.InstructionDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import javax.sql.DataSource;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;


public abstract class JDBCInstructionDAO extends JDBCAdminComponentDAO
  implements InstructionDAO {
  public JDBCInstructionDAO(ServiceLocator locator) {
    super(locator);
  }

  public int deleteInstruction(String instructionId) throws DMLException {
    return 0;
  }

  public int updateInstruction(
    String instructionId,
    String newLongName) throws DMLException {
    return 0;
  }

  public abstract int updateDisplayOrder(
    String instructionId,
    int newDisplayOrder) throws DMLException;

  public Collection getInstructions(
    String componentId) 
    {
      return null;
    }
  /**
   * Inner class that accesses database to get all the questions that belong to
   * the specified module
   */
  class InstructionsForAnElementQuery extends MappingSqlQuery {
    InstructionsForAnElementQuery(DataSource ds) {
      super();
      setDataSource(ds);
      setSql();
      compile();
    }

    public void setSql() {
      super.setSql(
        "SELECT * FROM SBREXT.FB_VALID_VALUES_VIEW where QUES_IDSEQ = ? ");
      declareParameter(new SqlParameter("QUESTION_IDSEQ", Types.VARCHAR));
    }
   /**
    * 3.0 Refactoring- Removed JDBCTransferObject
    */    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
          //FormValidValue fvv = new FormValidValueTransferObject();
          //fvv.setValueIdseq(rs.getString(1));     // VV_IDSEQ
          //fvv.setVpIdseq(rs.getString(8));        // VP_IDSEQ
          //fvv.setLongName(rs.getString(9));       // LONG_NAME
          //fvv.setDisplayOrder(rs.getInt(14));     // DISPLAY_ORDER
          //fvv.setShortMeaning(rs.getString(15));    // Meaning      
         return null;
    }
  }
    
  public static void main(String[] args) {
  }
}
