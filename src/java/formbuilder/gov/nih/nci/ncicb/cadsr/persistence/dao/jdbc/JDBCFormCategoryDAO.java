package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import java.util.Collection;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormCategoryDAO;

public class JDBCFormCategoryDAO extends JDBCBaseDAO implements FormCategoryDAO  {
  public JDBCFormCategoryDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all the form/template categories. This info is maintained in 
   * Table:QC_DISPLAY_LOV_EXT  Column:QCDL_NAME
   * 
   * @return <b>Collection</b> Collection of categories (Strings)
   */
  public Collection getAllCategories() {
    return null;
  }
}