package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.cdebrowser.cdecart.*;
import gov.nih.nci.ncicb.cadsr.cdebrowser.cdecart.CDECart;
import gov.nih.nci.ncicb.cadsr.cdebrowser.cdecart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.dto.CDECartItemTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.CDECartTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.dao.CDECartDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import java.util.List;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;


public class JDBCCDECartDAO extends JDBCBaseDAO implements CDECartDAO {
  private DataElementsInCartQuery deCartQuery;
  private FormsInCartQuery frmCartQuery;
  
  public JDBCCDECartDAO(ServiceLocator locator) {
    super(locator);
    deCartQuery = new DataElementsInCartQuery();
    frmCartQuery = new FormsInCartQuery();
  }

  public CDECart findCDECart(String username) throws DMLException  {
    CDECart cart = new CDECartTransferObject();
    List deList = deCartQuery.execute(username);
    cart.setDataElements(deList);
    List formList = frmCartQuery.execute(username);
    //cart.setForms(formList);
    return cart;
  }

  public int insertCartItem(CDECartItem item) throws DMLException {   // check if the user has the privilege to create module
     InsertCartItem  cartItem  = 
      new InsertCartItem (this.getDataSource());
    String ccmIdseq = generateGUID(); 
    int res = cartItem.createItem(item, ccmIdseq);
    if (res != 1) {
      throw new DMLException("Did not succeed creating item in the " + 
        " cde_cart_items table.");
    }
    return 1;
  }

  public int deleteCartItem(String itemId) throws DMLException {
  DeleteCartItem  cartItem  = new DeleteCartItem (this.getDataSource());
    int res = cartItem.deleteItem(itemId);
    if (res != 1) {
      throw new DMLException("Did not succeed in deleting  the " + 
        " cde_cart_items table.");
    }
    return 1;
}
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    JDBCCDECartDAO cTest = new JDBCCDECartDAO(locator);

    
    try {
      CDECartItem newItem = new CDECartItemTransferObject();

      int res = cTest.deleteCartItem("D5378537-60EA-2B2C-E034-0003BA0B1A09");
      System.out.println("\n*****Create Item Result 1: " + res);
    }
    catch (DMLException de) {
      System.out.println("******Printing DMLException*******");
      de.printStackTrace();
      System.out.println("******Finishing printing DMLException*******");
    }
    
    
    /*
    try {
      int res = formTest.deleteForm("D4700045-2FD0-0DAA-E034-0003BA0B1A09");
      System.out.println("\n*****Delete Form Result 1: " + res);
    }
    catch (DMLException de) {
      System.out.println("******Printing DMLException*******");
      de.printStackTrace();
      System.out.println("******Finishing printing DMLException*******");
    }
    */
  }

  /**
   * Inner class
   */
  class DataElementsInCartQuery extends MappingSqlQuery {
    DataElementsInCartQuery() {
      super();
    }

    public void setSql() {
      super.setSql("SELECT * FROM FB_DE_CART_VIEW where UA_NAME = ? ");
      declareParameter(new SqlParameter("ua_name", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      CDECartItem item = new CDECartItemTransferObject();
      return item;
    }
  }

  /**
   * Inner class
   */
  class FormsInCartQuery extends MappingSqlQuery {
    FormsInCartQuery() {
      super();
    }

    public void setSql() {
      super.setSql("SELECT * FROM FB_FORM_CART_VIEW where UA_NAME = ? ");
      declareParameter(new SqlParameter("ua_name", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      CDECartItem item = new CDECartItemTransferObject();
      return item;
    }
  }

    /**
   * Inner class that accesses database to create a cart item
   */
 private class InsertCartItem extends SqlUpdate {
    public InsertCartItem(DataSource ds) {
      String itemInsertSql = 
      " INSERT INTO cde_cart_items " + 
      " (ccm_idseq, ac_idseq, ua_name, actl_name,created_by,date_created) " +
      " VALUES " +
      " (?, ?, ?, ?, ?, ?) ";

      this.setDataSource(ds);
      this.setSql(itemInsertSql);
      declareParameter(new SqlParameter("p_ccm_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_ac_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_ua_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_actl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      declareParameter(new SqlParameter("p_date_created", Types.TIMESTAMP));
      compile();
    }
    protected int createItem (CDECartItem sm, String ccmIdseq) 
    {
      Object [] obj = 
        new Object[]
          {ccmIdseq, 
           sm.getId(),
           sm.getCreatedBy(),
           sm.getType(),
           sm.getCreatedBy(),
           sm.getCreatedDate()
          };
      
	    int res = update(obj);
      return res;
    }
  }

  /**
   * Inner class that accesses database to delete an item.
   */
 private class DeleteCartItem extends SqlUpdate {
    public DeleteCartItem(DataSource ds) {
      String itemDeleteSql = 
      " DELETE FROM cde_cart_items " +
      " WHERE ccm_idseq =  " +
      " (?) ";

      this.setDataSource(ds);
      this.setSql(itemDeleteSql);
      declareParameter(new SqlParameter("p_ccm_idseq", Types.VARCHAR));
      compile();
    }
    protected int deleteItem (String ccmIdseq) 
    {
      Object [] obj = 
        new Object[]
          {ccmIdseq
          };
      
	    int res = update(obj);
      return res;
    }
  }
}
