package gov.nih.nci.ncicb.cadsr.security.oc4j;

import com.evermind.security.*;
import java.math.BigInteger;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.Set;

public abstract class SimpleUserManager extends AbstractUserManager
{
    class UserWrapper
        implements User
    {

        private String name;
        private String stPassword;

        public String getName()
        {
            return name;
        }

        public boolean authenticate(String password)
        {
            if(password == null)
                return false;
            if(checkPassword(name, password))
            {
                setPassword(password);
                return true;
            }
            else
            {
                return false;
            }
        }

        public boolean isMemberOf(Group group)
        {
            return true;
           // if(group == null)
             //   return false;
            //else
              //  return inGroup(name, group.getName());
        }

        public Set getGroups()
        {
            return null;
        }

        public String getDescription()
        {
            return null;
        }

        public void setDescription(String s)
        {
        }

        public Locale getLocale()
        {
            return null;
        }

        public void setLocale(Locale locale)
        {
        }

        public boolean hasPermission(Permission permission)
        {
            return false;
        }

        public void setPassword(String password)
        {
            stPassword = password;
        }

        public String getPassword()
        {
            return stPassword;
        }

        public BigInteger getCertificateSerial()
        {
            return null;
        }

        public String getCertificateIssuerDN()
        {
            return null;
        }

        public void setCertificate(String s, BigInteger biginteger)
            throws UnsupportedOperationException
        {
        }

        public void setCertificate(X509Certificate x509certificate)
        {
        }

        public void addToGroup(Group group1)
            throws UnsupportedOperationException
        {
        }

        public void removeFromGroup(Group group1)
            throws UnsupportedOperationException
        {
        }

        UserWrapper(String name)
        {
            this.name = name;
        }
    }


    public SimpleUserManager()
    {
    }

    protected abstract boolean userExists(String s);

    protected abstract boolean checkPassword(String s, String s1);

    protected abstract boolean inGroup(String s, String s1);

    public User getUser(String username)
    {
        if(username != null && userExists(username))
            return new UserWrapper(username);
        else
            return getParent().getUser(username);
    }
    

}
