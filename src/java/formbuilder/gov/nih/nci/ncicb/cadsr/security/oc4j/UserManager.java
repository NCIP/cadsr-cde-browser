package gov.nih.nci.ncicb.cadsr.security.oc4j;

import java.io.PrintStream;
import java.sql.*;
import java.util.Properties;

// Referenced classes of package gov.nih.nci.webadmin.oc4j:
//            SimpleUserManager

public class UserManager extends SimpleUserManager
{

    private String stNCIRealmServerUrl;
    private String stNCIRealmJDBCDriver;
    private String stDBPoolEnable;
    private String stDBPoolContext;
    private String stDBPoolServerUrl;
    private String stDBPoolLoginUser;
    private String stDBPoolLoginPassword;
    private int iDBPoolMinConnections;
    private int iDBPoolMaxConnections;
    private boolean bDebug;

    public UserManager()
    {
        stDBPoolEnable = "true";
        stDBPoolContext = "";
    }

    public void init(Properties properties)
    {
        stNCIRealmServerUrl = properties.getProperty("NCIRealmServerUrl");
        stNCIRealmJDBCDriver = properties.getProperty("NCIRealmJDBCDriver");
        bDebug = properties.getProperty("debug").equalsIgnoreCase("true");
        if(bDebug)
            System.out.println("in init: [" + stNCIRealmServerUrl + "][" + stNCIRealmJDBCDriver + "][" + bDebug + "]");
    }

    protected boolean userExists(String stUsername)
    {
        if(bDebug)
            System.out.println("in userExists: " + stUsername);
        return true;
    }

    protected boolean checkPassword(String stUsername, String stPassword)
    {
        if(bDebug){
            System.out.println("in checkPassword: ");
            return true;
        }
        try
        {
            DriverManager.registerDriver((Driver)Class.forName(stNCIRealmJDBCDriver).newInstance());
        }
        catch(Exception e)
        {
            System.out.println("Could not register JDBC driver [" + stNCIRealmJDBCDriver + "].  Check the NCICBOracle8iRealm:JDBCDriver entry in the server.xml.");
        }
        try
        {
            if(bDebug)
                System.out.println("before getConnection(): --");
            Connection con = null;
            con = DriverManager.getConnection(stNCIRealmServerUrl, stUsername, stPassword);
            con.close();
            if(bDebug)
                System.out.println("after getConnection(): --");
            return true;
        }
        catch(SQLException e)
        {
            System.out.println("Failed to connect as user [" + stUsername + "]");
            if(bDebug)
                e.printStackTrace();
            return false;
        }
    }

    protected boolean inGroup(String stUsername, String stGroupname)
    {
        if(bDebug)
            System.out.println("in inGroup: " + stUsername + " : " + stGroupname);
        return true;
    }
}
