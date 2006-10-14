package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.dto.BaseTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Address;
import gov.nih.nci.ncicb.cadsr.resource.Contact;
import gov.nih.nci.ncicb.cadsr.resource.ContactCommunication;
import gov.nih.nci.ncicb.cadsr.resource.Organization;

import gov.nih.nci.ncicb.cadsr.resource.Person;

import java.util.Collection;
import java.util.Iterator;

public class OrganizationTransferObject extends BaseTransferObject
  implements Organization{
   public String id;
    public String name;
    private Collection<Address> addresses;
    private Collection<Person> person;
    private Collection<Contact> contacts;
   private Collection<ContactCommunication> contactCommunications; //phone, email, fax, etc
   public OrganizationTransferObject() {
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getId() {
      return id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setAddresses(Collection<Address> addresses) {
      this.addresses = addresses;
   }

   public Collection<Address> getAddresses() {
      return addresses;
   }

   public void setPerson(Collection<Person> person) {
      this.person = person;
   }

   public Collection<Person> getPerson() {
      return person;
   }

   public void setContacts(Collection<Contact> contacts) {
      this.contacts = contacts;
   }

   public Collection<Contact> getContacts() {
      return contacts;
   }
   
   public void setContactCommunications(Collection<ContactCommunication> contactCommunications) {
      this.contactCommunications = contactCommunications;
   }

   public Collection<ContactCommunication> getContactCommunications() {
      return contactCommunications;
   }
    public String toString() {
        String person = "Name: " + this.getName() + "<br>";
        for (Iterator iter=this.getContactCommunications().iterator(); iter.hasNext();){
            ContactCommunication cc= (ContactCommunication) iter.next();
            person += cc.getType() + ": " + cc.getValue() + "<br>";
        }
        return person;
    }
   
}
