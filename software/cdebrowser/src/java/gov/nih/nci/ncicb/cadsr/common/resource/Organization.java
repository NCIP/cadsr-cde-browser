package gov.nih.nci.ncicb.cadsr.common.resource;

import java.util.Collection;

public interface Organization {
   public void setId(String id);

   public String getId();

   public void setName(String name) ;

   public String getName() ;

   public void setAddresses(Collection<Address> addresses);

   public Collection<Address> getAddresses();

   public void setPerson(Collection<Person> person) ;

   public Collection<Person> getPerson();

   public void setContacts(Collection<Contact> contacts) ;

   public Collection<Contact> getContacts() ;
   
   public void setContactCommunications(Collection<ContactCommunication> contactCommunications);

   public Collection<ContactCommunication> getContactCommunications() ;
}
