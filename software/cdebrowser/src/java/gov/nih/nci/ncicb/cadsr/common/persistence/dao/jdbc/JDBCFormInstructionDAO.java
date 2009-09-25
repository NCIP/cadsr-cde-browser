package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.FormInstructionDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.SimpleServiceLocator;

import java.util.Collection;
import java.util.List;
import org.springframework.jdbc.object.MappingSqlQuery;

public class JDBCFormInstructionDAO extends JDBCInstructionDAO
  implements FormInstructionDAO {
  public JDBCFormInstructionDAO(ServiceLocator locator) {
    super(locator);
  }


  /**
   * Creates a new form instruction component (just the header info).
   *
   * @param <b>formInstr</b> FormInstruction object
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int createInstruction(Instruction formInstr, String parentId)
    throws DMLException {
   return  super.createInstruction(formInstr,parentId,"FORM_INSTR","FORM_INSTRUCTION");
  }

  public int createFooterInstruction(Instruction formInstr, String parentId)
    throws DMLException {
   return  super.createInstruction(formInstr,parentId,"FOOTER","FORM_INSTRUCTION");
  }
  
  public List getInstructions(String formID)
    throws DMLException {
    return super.getInstructions(formID,"FORM_INSTR");
  }
  
  public List getFooterInstructions(String formID)
    throws DMLException {
    return super.getInstructions(formID,"FOOTER");
  }

  /**
   * Test application
   */
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    JDBCFormInstructionDAO test = new JDBCFormInstructionDAO(locator);
     
    // test createFormInstructionComponent method.
    // for each test, change long name(preferred name generated from long name)
    try {
      JDBCFormDAO formDao = new JDBCFormDAO(locator);
      String formId = "A019BB52-B911-3D6B-E034-080020C9C0E0";
      Form aForm = formDao.findFormByPrimaryKey(formId);

      Instruction newFormInstr = new InstructionTransferObject();
      newFormInstr.setVersion(new Float(2.31));
      newFormInstr.setIdseq("EBB3B308-4595-522D-E034-0003BA0B1A09");
      newFormInstr.setLongName("Testsh121-updated");
      newFormInstr.setPreferredDefinition("Test Form instr pref def");
      newFormInstr.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
      newFormInstr.setAslName("DRAFT NEW");
      newFormInstr.setCreatedBy("Hyun Kim");
      newFormInstr.setDisplayOrder(1);
     // int res = test.createInstruction(newFormInstr,formId,"FORM_INSTR","FORM_INSTRUCTION");
      //System.out.println("\n*****Create Form Instruction: " + res);
      
      //System.out.println("\n*****Query Form Instruction: " +test.getInstructions(formId));
      //System.out.println("\n*****Update Form Instruction: " +test.updateInstruction(newFormInstr));
      
      System.out.println("\n*****Deleted Form Instruction: " +test.deleteInstruction("EBB3B308-4595-522D-E034-0003BA0B1A09"));
      
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
    
  }


}
