package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.cdebrowser.cdecart.*;
import gov.nih.nci.ncicb.cadsr.cdebrowser.cdecart.CDECart;
import gov.nih.nci.ncicb.cadsr.cdebrowser.cdecart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.dto.CDECartItemTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.CDECartTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.dao.CDECartDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.List;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;


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

  public int insertCartItem(CDECartItem item) throws DMLException {
    return 0;
  }

  public int deleteCartItem(String itemId) throws DMLException {
    return 0;
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
}
