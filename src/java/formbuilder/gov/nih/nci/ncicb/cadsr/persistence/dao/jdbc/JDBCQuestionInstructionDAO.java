package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionInstructionDAO;
import gov.nih.nci.ncicb.cadsr.resource.QuestionInstruction;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;

import java.util.Collection;


public class JDBCQuestionInstructionDAO extends JDBCInstructionDAO
  implements QuestionInstructionDAO {
  public JDBCQuestionInstructionDAO(ServiceLocator locator) {
    super(locator);
  }

  public int updateDisplayOrder(
    String instructionId,
    int newDisplayOrder) throws DMLException {
    return 0;
  }

  public int createQuestionInstructionComponent(QuestionInstruction quesInstr)
    throws DMLException {
    return 0;
  }

  public Collection getQuestionInstructions(String questionId)
    throws DMLException {
    return null;
  }

  public static void main(String[] args) {
  }
}
