package gov.nih.nci.ncicb.cadsr.util;

import java.util.ResourceBundle;

public class CDEBrowserParams
{
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
            System.out.println("*** Resource File Name ***: " + propFilename);
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
            System.out.println("Error getting init parameters, missing resource values");
            System.out.println("Property missing index: " + index);
            System.out.println(mre.getMessage());
            System.exit(-1);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
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