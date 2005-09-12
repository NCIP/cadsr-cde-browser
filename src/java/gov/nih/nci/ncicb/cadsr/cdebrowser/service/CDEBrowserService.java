package gov.nih.nci.ncicb.cadsr.cdebrowser.service;
import java.util.Locale;
import java.util.Properties;

public interface CDEBrowserService 
{
  public Properties getApplicationProperties(Locale locale);
  public Properties reloadApplicationProperties(Locale locale, String username);
}