/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.resource;

public interface Contact extends AdminComponent{
   public void setContactRole(String contactRole);

   public String getContactRole();

   public void setRank(Integer rank) ;

   public Integer getRank() ;

   public void setPerson(Person person) ;

   public Person getPerson();

   public void setOrganization(Organization organization);

   public Organization getOrganization() ;
}
