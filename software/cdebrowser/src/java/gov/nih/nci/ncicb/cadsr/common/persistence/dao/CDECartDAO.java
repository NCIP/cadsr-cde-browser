/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.common.persistence.dao;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;

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