package gov.nih.nci.ncicb.cadsr.persistence.dao;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import java.util.Locale;
import java.util.Properties;

public interface UtilDAO 
{
    /**
   * Utility method to get the Application properties
   *
   * @param <b>Application Name<b> corresponds to the target record whose 
   *        display order is to be updated
   * @param <b>Locale<b> of the user
   *
   * @return <b>properties</b> containing application properties
   *
   * @throws <b>DMLException</b>
   */
    public Properties getApplicationProperties(String ApplicationName, String locale) throws DMLException;


}