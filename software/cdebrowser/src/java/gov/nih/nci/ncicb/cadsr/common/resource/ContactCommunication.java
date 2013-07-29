/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.resource;

public interface ContactCommunication {
   public void setType(String type);

   public String getType();

   public void setValue(String value);

   public String getValue() ;

   public void setId(String id) ;

   public String getId();
   
   public void setRankOrder(int rankOrder);

   public int getRankOrder() ;
   
}
