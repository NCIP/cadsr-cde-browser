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
