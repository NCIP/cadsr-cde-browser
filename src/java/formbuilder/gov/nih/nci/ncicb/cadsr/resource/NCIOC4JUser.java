package gov.nih.nci.ncicb.cadsr.resource;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import com.evermind.security.User;
import gov.nih.nci.ncicb.cadsr.security.oc4j.BaseUserManager;
public interface NCIOC4JUser extends NCIUser,User
{
  public BaseUserManager getUserManager();
  public void setUserManager(BaseUserManager newUserManager);
}