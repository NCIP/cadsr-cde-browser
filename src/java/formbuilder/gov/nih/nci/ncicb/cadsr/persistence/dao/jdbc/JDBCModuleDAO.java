package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCQuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleDAO;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class JDBCModuleDAO extends JDBCBaseDAO implements ModuleDAO {
  public JDBCModuleDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all the questions that belong to the specified module
   *
   * @param moduleId corresponds to the module idseq
   *
   * @return questions that belong to the specified module
   */
  public Collection getQuestionsInAModule(String moduleId) {
    QuestionsInAModuleQuery query = new QuestionsInAModuleQuery();
    query.setDataSource(getDataSource());
    query.setSql();

    return query.execute(moduleId);
  }

  public int createModuleComponent(Module sourceModule)
    throws DMLException {
    
    CreateModule createMod = new CreateModule(this.getDataSource());
    Map out = createMod.execute(sourceModule);

    String returnCode = (String)out.get("p_return_code");
    String returnDesc = (String)out.get("p_return_desc");
    if (returnCode.equals("OK")) 
    {
      return 1;
    }
    else // for p_return_code is not OK
    {
      throw new DMLException(returnDesc);
    }
  }

  public Module addQuestions(
    String moduleId,
    Collection questions) throws DMLException {
    return null;
  }

  public int deleteModule(String moduleId) throws DMLException {
    DeleteModule deleteMod = new DeleteModule(this.getDataSource());
    Map out = deleteMod.execute(moduleId);

    String returnCode = (String)out.get("p_return_code");
    String returnDesc = (String)out.get("p_return_desc");
    if (StringUtils.isInteger(returnCode))  
    // checks for null, and non positive integer value
    {
      return 1;
    }
    else // for p_return_code >= 0
    {
      throw new DMLException(returnDesc);
    }
  }

  /**
   * Inner class that accesses database to delete a module.
   */
  private class DeleteModule extends StoredProcedure {
    public DeleteModule(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.remove_module");
      declareParameter(new SqlParameter("p_mod_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }
              
    public Map execute(
      String modIdseq) {
      
      Map in = new HashMap();
      in.put("p_mod_idseq", modIdseq);

      Map out = execute(in);
      return out;
    }
  }

  /**
   * Inner class that accesses database to create a module.
   */
  private class CreateModule extends StoredProcedure {
    public CreateModule(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.ins_module");
      declareParameter(new SqlParameter("p_crf_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_version", Types.VARCHAR));
      declareParameter(new SqlParameter("p_preferred_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_long_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_preferred_definition", Types.VARCHAR));
      declareParameter(new SqlParameter("p_conte_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_proto_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_asl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      declareParameter(new SqlParameter("p_display_order", Types.INTEGER));
      declareParameter(new SqlOutParameter("p_mod_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_qr_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }
              
    public Map execute(
      Module sm) {
      
      Map in = new HashMap();
      in.put("p_crf_idseq", sm.getForm().getFormIdseq());
      in.put("p_version", sm.getVersion().toString());
      in.put("p_preferred_name", sm.getPreferredName());
      in.put("p_long_name", sm.getLongName());
      in.put("p_preferred_definition", sm.getPreferredDefinition());
      in.put("p_conte_idseq", sm.getConteIdseq());
      in.put("p_proto_idseq", sm.getForm().getProtoIdseq());
      in.put("p_asl_name", sm.getAslName());
      in.put("p_created_by", sm.getCreatedBy());
      in.put("p_display_order", new Integer(sm.getDisplayOrder()));

      Map out = execute(in);
      return out;
    }
  }

  public int updateDisplayOrder(
    String moduleId,
    int newDisplayOrder) throws DMLException {
    return 0;
  }

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    JDBCModuleDAO test = new JDBCModuleDAO(locator);

    //System.out.println(
    //  test.getQuestionsInAModule("99CD59C5-B13D-3FA4-E034-080020C9C0E0"));
    System.out.println(
      test.getQuestionsInAModule("99CD59C5-A9C3-3FA4-E034-080020C9C0E0"));


    int res;
    try {
      res = test.deleteModule("99CD59C5-B206-3FA4-E034-080020C9C0E0");
      System.out.println("\n*****Delete Module Result 1: " + res);
    }
    catch (DMLException de)
    {
      System.out.println("******Printing DMLException*******");
      de.printStackTrace();
      System.out.println("******Finishing printing DMLException*******");
    }




  }

  /**
   * Inner class that accesses database to get all the questions that belong to
   * the specified module
   */
  class QuestionsInAModuleQuery extends MappingSqlQuery {
    QuestionsInAModuleQuery() {
      super();
    }

    public void setSql() {
      super.setSql(
        "SELECT * FROM SBREXT.FB_QUESTIONS_VIEW where MODULE_IDSEQ = ? ");
      declareParameter(new SqlParameter("MODULE_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return new JDBCQuestionTransferObject(rs);
    }
  }
}
