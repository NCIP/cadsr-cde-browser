package gov.nih.nci.ncicb.cadsr.common.resource;

import java.util.Collection;

public interface Person {
   public void setFirstName(String firstName);

   public String getFirstName() ;

   public void setId(String id);

   public String getId();

   public void setLastName(String lastName);

   public String getLastName();

   public void setMiddleInitial(String middleInitial) ;

   public String getMiddleInitial() ;

   public void setPosition(String position);

   public String getPosition() ;

   public void setRank(Integer rank) ;

   public Integer getRank() ;

   public void setAddresses(Collection addresses);

   public Collection getAddresses() ;

   public void setContacts(Collection<Contact> contacts) ;

   public Collection<Contact> getContacts() ;

   public void setOrganization(Organization organization) ;

   public Organization getOrganization() ;
   
   public void setContactCommunications(Collection<ContactCommunication> contactCommunications);

   public Collection<ContactCommunication> getContactCommunications() ;
   
}
