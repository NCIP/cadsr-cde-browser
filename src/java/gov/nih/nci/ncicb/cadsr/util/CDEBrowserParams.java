package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;
import java.util.ResourceBundle;

public class CDEBrowserParams
{
    private static Log log = LogFactory.getLog(CDEBrowserParams.class.getName());
    String sbrextDSN = "";
    String sbrDSN = "";
    String xmlDownloadDir = "";
    String xmlPaginationFlag = "no";
    String xmlFileMaxRecord;
    String treeURL = "";
    static CDEBrowserParams instance;
    // constructor
    private CDEBrowserParams()
    {
    }

   /**
   *  Read the resource bundle file
   *  propFilename - the specified resource file (fn.properties) without the extension
   *  (e.g., medsurv)
   */

    private CDEBrowserParams(String propFilename)
    {

        // read the init parameters from the resource bundle
        int index = 0;
        try
        {
            log.info("*** Resource File Name ***: " + propFilename);
            ResourceBundle b = ResourceBundle.getBundle(propFilename, java.util.Locale.getDefault());
            sbrextDSN = b.getString("SBREXT_DATA_SOURCE_NAME");
            index++;
            sbrDSN = b.getString("SBR_DATA_SOURCE_NAME");
            index++;
            xmlDownloadDir = b.getString("XML_DOWNLOAD_DIR");
            index++;
            xmlPaginationFlag = b.getString("XML_PAGINATION_FLAG");
            index++;
            xmlFileMaxRecord = b.getString("XML_FILE_MAX_RECORDS");
            index++;
            treeURL = b.getString("TREE_URL");
            index++;
                        
        } 
        catch (java.util.MissingResourceException mre) 
        {
            log.error("Error getting init parameters, missing resource values");
            log.error("Property missing index: " + index);
            log.error(mre.getMessage(), mre);
            System.exit(-1);
        }
        catch (Exception e)
        {
            log.error("Exception occurred", e);
            System.exit(-1);
        }
    }
    public String getSbrextDSN(){
      return sbrextDSN;
    }
    public String getSbrDSN(){
      return sbrDSN;
    }
    public String getXMLDownloadDir(){
      return xmlDownloadDir;
    }
    public static CDEBrowserParams getInstance(String propFilename){
      if (instance == null) {
        instance = new CDEBrowserParams(propFilename);
      }
    return instance;
    }
    public String getTreeURL() {
      return treeURL;
    }
    public String getXMLPaginationFlag(){
      return xmlPaginationFlag;
    }
    public String getXMLFileMaxRecords() {
      return xmlFileMaxRecord;
    }
           
}