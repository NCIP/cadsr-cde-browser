package gov.nih.nci.ncicb.cadsr.dto.jdbc;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCQuestionTransferObject extends QuestionTransferObject 
{
  public JDBCQuestionTransferObject(ResultSet rs) throws SQLException
  {
    setQuesIdseq(rs.getString(1));  //QUES_IDSEQ
    setLongName(rs.getString(9));   // LONG_NAME
    setDisplayOrder(rs.getInt(13)); // DISPLAY_ORDER
  }
}