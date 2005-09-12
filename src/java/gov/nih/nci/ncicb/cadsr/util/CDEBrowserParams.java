package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.spring.ApplicationServiceLocatorImpl;
import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class CDEBrowserParams
{
    private static Log log = LogFactory.getLog(CDEBrowserParams.class.getName());
    
    //TODO has to be moved to read from Spring Application Context
    private static ApplicationServiceLocator appServiceLocator= new ApplicationServiceLocatorImpl();
    //String sbrextDSN = "";
    //String sbrDSN = "";
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
    
    String curationToolUrl = "";
    String nciMetathesaurusUrl="";
    String nciTerminologyServerUrl="";
    String sentinelToolUrl="";
    String adminToolUrl="";
    
    Map evsUrlMap = new HashMap();
    
    static CDEBrowserParams instance;

   /**
   *  Read the resource bundle file
   *  propFilename - the specified resource file (fn.properties) without the extension
   *  (e.g., medsurv)
   */
   private CDEBrowserParams()
   {
   }

    
    
    

    public String getXMLDownloadDir(){
      return xmlDownloadDir;
    }
    public static CDEBrowserParams getInstance(){
      if (instance == null ) {
        Properties properties = appServiceLocator.findCDEBrowserService().getApplicationProperties(Locale.US);
        instance = new CDEBrowserParams();
        instance.initAttributesFromProperties(properties);
      }
      return instance;
    }
    
    public static void reloadInstance(String userName){
        Properties properties = appServiceLocator.findCDEBrowserService().reloadApplicationProperties(Locale.US,userName);
        instance = new CDEBrowserParams();
        instance.initAttributesFromProperties(properties);
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

  public void setEvsUrlMap(Properties bundle,String evsSourcesArr)
  {
        try
        {
            String[] urls = StringUtils.tokenizeCSVList(evsSourcesArr);
            for(int i=0; i<urls.length;i++)
            {
              String key  = urls[i];
              String value = bundle.getProperty(key);
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


  public void setCurationToolUrl(String curationToolUrl)
  {
    this.curationToolUrl = curationToolUrl;
  }


  public String getCurationToolUrl()
  {
    return curationToolUrl;
  }


  public void setNciMetathesaurusUrl(String nciMetathesaurusUrl)
  {
    this.nciMetathesaurusUrl = nciMetathesaurusUrl;
  }


  public String getNciMetathesaurusUrl()
  {
    return nciMetathesaurusUrl;
  }


  public void setNciTerminologyServerUrl(String nciTerminologyServerUrl)
  {
    this.nciTerminologyServerUrl = nciTerminologyServerUrl;
  }


  public String getNciTerminologyServerUrl()
  {
    return nciTerminologyServerUrl;
  }


  public void setSentinelToolUrl(String sentinelToolUrl)
  {
    this.sentinelToolUrl = sentinelToolUrl;
  }


  public String getSentinelToolUrl()
  {
    return sentinelToolUrl;
  }


  public void setAdminToolUrl(String adminToolUrl)
  {
    this.adminToolUrl = adminToolUrl;
  }


  public String getAdminToolUrl()
  {
    return adminToolUrl;
  }
  
  private void initAttributesFromProperties(Properties properties)
  {
        // read the init parameters from the resource bundle
        int index = 0;
        try
        {
                     
            xmlDownloadDir = properties.getProperty("XML_DOWNLOAD_DIR");
            index++;
            xmlPaginationFlag = properties.getProperty("XML_PAGINATION_FLAG");
            index++;
            xmlFileMaxRecord = properties.getProperty("XML_FILE_MAX_RECORDS");
            index++;
            treeURL = properties.getProperty("TREE_URL");
            index++;
            evsSources = properties.getProperty("EVS_URL_SOURCES");
            index++;
            setEvsUrlMap(properties,evsSources);
            showFormsAlphebetical = properties.getProperty("SHOW_FORMS_ALPHEBETICAL");
            index++;            
            excludeTestContext = properties.getProperty("EXCLUDE_TEST_CONTEXT_BY_DEFAULT");
            index++;
            excludeTrainingContext = properties.getProperty("EXCLUDE_TRAINING_CONTEXT_BY_DEFAULT");
            index++;            
            excludeWorkFlowStatuses = properties.getProperty("EXCLUDE_WORKFLOW_BY_DEFAULT");
            index++;
            excludeRegistrationStatuses = properties.getProperty("EXCLUDE_REGISTRATION_BY_DEFAULT");
            index++;      

            adminToolUrl = properties.getProperty("ADMIN_TOOL_URL");
            index++;      
            curationToolUrl = properties.getProperty("CURATION_TOOL_URL");
            index++;
            nciMetathesaurusUrl = properties.getProperty("NCI_METATHESAURUS_URL");
            index++;            
            nciTerminologyServerUrl = properties.getProperty("NCI_TERMINOLOGY_SERVER_URL");
            index++;
            sentinelToolUrl = properties.getProperty("SENTINEL_TOOL_URL");
            index++;             
            log.info("Loaded Properties"+properties);
                        
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
}