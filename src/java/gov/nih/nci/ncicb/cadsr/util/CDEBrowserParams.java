package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
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
    String evsSources = "";
    String showFormsAlphebetical ="no";    
    String excludeTestContext = "no";
    String excludeTrainingContext="no";
    String excludeWorkFlowStatuses = "";
    String excludeRegistrationStatuses = "";
    Map evsUrlMap = new HashMap();
    
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
            evsSources = b.getString("EVS_URL_SOURCES");
            index++;
            setEvsUrlMap(b,evsSources);
            showFormsAlphebetical = b.getString("SHOW_FORMS_ALPHEBETICAL");
            index++;            
            excludeTestContext = b.getString("EXCLUDE_TEST_CONTEXT_BY_DEFAULT");
            index++;
            excludeTrainingContext = b.getString("EXCLUDE_TRAINING_CONTEXT_BY_DEFAULT");
            index++;            
            excludeWorkFlowStatuses = b.getString("EXCLUDE_WORKFLOW_BY_DEFAULT");
            index++;
            excludeRegistrationStatuses = b.getString("EXCLUDE_REGISTRATION_BY_DEFAULT");
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
  
  public void setEvsUrlMap(Map evsUrlMap)
  {
   this.evsUrlMap = evsUrlMap;
  }

  public void setEvsUrlMap(ResourceBundle bundle,String evsSourcesArr)
  {
        try
        {
            String[] urls = StringUtils.tokenizeCSVList(evsSourcesArr);
            for(int i=0; i<urls.length;i++)
            {
              String key  = urls[i];
              String value = bundle.getString(key);
              if(evsUrlMap==null)
                evsUrlMap = new HashMap();
              evsUrlMap.put(key,value);
            }
        }
        catch (java.util.MissingResourceException mre) 
        {
            log.error("Error getting init parameters, missing resource values");
            log.error("EVS Url not mapped correctly");
            log.error(mre.getMessage(), mre);
            System.exit(-1);
        }
        catch (Exception e)
        {
            log.error("Exception occurred", e);
            System.exit(-1);
        }
  }
  public Map getEvsUrlMap()
  {
    return evsUrlMap;
  }
  
  public void setShowFormsAlphebetical(String showFormsAlphebetical)
  {
    this.showFormsAlphebetical = showFormsAlphebetical;
  }


  public String getShowFormsAlphebetical()
  {
    return showFormsAlphebetical;
  }
  
  
  public void setExcludeTestContext(String excludeTestContext)
  {
    this.excludeTestContext = excludeTestContext;
  }


  public String getExcludeTestContext()
  {
    return excludeTestContext;
  }
  
  public void setExcludeTrainingContext(String excludeTrainingContext)
  {
    this.excludeTrainingContext = excludeTrainingContext;
  }


  public String getExcludeTrainingContext()
  {
    return excludeTrainingContext;
  }  

  public String getExcludeWorkFlowStatuses()
  {
    return excludeWorkFlowStatuses;
  }

  public void setExcludeWorkFlowStatuses(String excludeWorkFlowStatuses)
  {
    this.excludeWorkFlowStatuses = excludeWorkFlowStatuses;
  }

  public String getExcludeRegistrationStatuses()
  {
    return excludeRegistrationStatuses;
  }

  public void setExcludeRegistrationStatuses(String excludeRegistrationStatuses)
  {
    this.excludeRegistrationStatuses = excludeRegistrationStatuses;
  }
           
}