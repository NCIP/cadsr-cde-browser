package gov.nih.nci.ncicb.cadsr.dto.jdbc;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ValueDomainTransferObject;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCQuestionTransferObject extends QuestionTransferObject 
{
  public JDBCQuestionTransferObject(ResultSet rs) throws SQLException
  {
    setQuesIdseq(rs.getString(1));  //QUES_IDSEQ
    setLongName(rs.getString(9));   // LONG_NAME
    setDisplayOrder(rs.getInt(13)); // DISPLAY_ORDER
    setAslName(rs.getString(5));//Workflow
    String deIdSeq = rs.getString(8);
    
    if(deIdSeq!=null)
    {
      DataElementTransferObject dataElementTransferObject =
        new DataElementTransferObject();       
      dataElementTransferObject.setDeIdseq(deIdSeq); // DE_IDSEQ
      dataElementTransferObject.setLongCDEName(rs.getString(15)); // DOC_TEXT 
      dataElementTransferObject.setVersion(new Float(rs.getFloat(16))); // VERSION
      dataElementTransferObject.setLongName(rs.getString(17)); // DE_LONG_NAME
      dataElementTransferObject.setCDEId(Integer.toString(rs.getInt(18)));
      setDataElement(dataElementTransferObject); 
      
      ValueDomainTransferObject valueDomainTransferObject = 
                                         new ValueDomainTransferObject();
      valueDomainTransferObject.setVdIdseq(rs.getString(19)); // VD_IDSEQ
      dataElementTransferObject.setValueDomain(valueDomainTransferObject);
    }
  }
}
