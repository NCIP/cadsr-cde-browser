package gov.nih.nci.ncicb.cadsr.security.oc4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.security.Permission;
import com.evermind.security.Group;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.Set;
import com.evermind.security.User;

public class OC4JUserObject implements User
{
  BaseUserManager userManager =null;
  String name = null;
  Collection groupNameList ;
  public OC4JUserObject()
  {
  } 
  
  public OC4JUserObject(String username)
  {
    name=username;
  }   
  public OC4JUserObject(BaseUserManager newUserManager)
  {
    userManager=newUserManager;
  }

  public void setName(String newName)
  {
    this.name=newName;
  }
  
  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return null;
  }

  public void setDescription(String p0)
  {
  }

  public boolean authenticate(String password)
  {
      if(password == null) {
        return false;
      } else {
          return userManager.checkPassword(this.getName(), password);
      }
  }

  public Locale getLocale()
  {
    return null;
  }

  public void setLocale(Locale p0) throws UnsupportedOperationException
  {
  }

  public boolean hasPermission(Permission p0)
  {
    return false;
  }

  public boolean isMemberOf(Group group)
  {
    if(groupNameList==null)
      groupNameList= new ArrayList();
    if(groupNameList.contains(group.getName()))
      return true;
    if(userManager.inGroup(this.getName(),group.getName()))
      {
        groupNameList.add(group.getName());
        return true;
      }
    else
    {
      return false;
    }
  }

  public void setPassword(String p0)
  {
  }

  public String getPassword()
  {
    return null;
  }

  public BigInteger getCertificateSerial()
  {
    return null;
  }

  public String getCertificateIssuerDN()
  {
    return null;
  }

  public void setCertificate(String p0, BigInteger p1) throws UnsupportedOperationException
  {
  }

  public void setCertificate(X509Certificate p0)
  {
  }

  public void addToGroup(Group p0) throws UnsupportedOperationException
  {
  }

  public void removeFromGroup(Group p0) throws UnsupportedOperationException
  {
  }

  public Set getGroups() throws UnsupportedOperationException
  {
    return null;
  }

  public BaseUserManager getUserManager()
  {
    return userManager;
  }

  public void setUserManager(BaseUserManager newUserManager)
  {
    userManager = newUserManager;
  }
  public static void main(String args[])
  {
    OC4JUserObject user = new OC4JUserObject();
  }
}