package gov.nih.nci.ncicb.cadsr.common.dto;

import gov.nih.nci.ncicb.cadsr.common.dto.AdminComponentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.Contact;
import gov.nih.nci.ncicb.cadsr.common.resource.Organization;
import gov.nih.nci.ncicb.cadsr.common.resource.Person;

public class ContactTransferObject extends AdminComponentTransferObject 
implements Contact{
   public String contactRole;
    public Integer rank;
    private Person person;
    private Organization organization;
   public ContactTransferObject() {
   }

   public void setContactRole(String contactRole) {
      this.contactRole = contactRole;
   }

   public String getContactRole() {
      return contactRole;
   }

   public void setRank(Integer rank) {
      this.rank = rank;
   }

   public Integer getRank() {
      return rank;
   }

   public void setPerson(Person person) {
      this.person = person;
   }

   public Person getPerson() {
      return person;
   }

   public void setOrganization(Organization organization) {
      this.organization = organization;
   }

   public Organization getOrganization() {
      return organization;
   }
   
   public String toString() {
   String contactInfo = null;
       if (this.getPerson() != null) {
           contactInfo = "Contact Person:<br>"
         + "Role: " + this.getContactRole() + "<br>"
         + person.toString();
       }
       if (this.getOrganization() != null) {
           contactInfo = "Contact Organization:<br>"
         + "Role: " + this.getContactRole() + "<br>"
         + this.getOrganization().toString();
       }
      if (contactInfo == null)      
        contactInfo = super.toString();
      return contactInfo;
   }
}
