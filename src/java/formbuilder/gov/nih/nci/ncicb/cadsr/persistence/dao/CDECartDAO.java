package gov.nih.nci.ncicb.cadsr.persistence.dao;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;

public interface CDECartDAO  {
  /**
   * Retrieves cart for a specified user.
   * @param <b>username</b> Username
   * 
   * @return <b>CDECart</b> CDECart object representing the CDE Cart.
   *
   * @throws <b>DMLException</b>
   */
  public CDECart findCDECart(String username);

  /**
   * Inserts an item into cart.
   * @param <b>item</b> A CDECartItem object.
   * 
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int insertCartItem(CDECartItem item);

  /**
   * Deletes an item from cart.
   * @param <b>itemId</b> Idseq of cart item.
   * @param <b>username</b> username
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteCartItem(String itemId, String username);
  
}