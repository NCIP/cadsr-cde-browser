package gov.nih.nci.ncicb.cadsr.cdebrowser.service;

import java.util.List;

import gov.nih.nci.ncicb.cadsr.resource.DataElement;

import java.util.Locale;
import java.util.Properties;

public interface CDEBrowserService 
{
  public Properties getApplicationProperties(Locale locale);
  public Properties reloadApplicationProperties(Locale locale, String username);
  public void populateDataElementAltNameDef (DataElement de);
  public List getReferenceDocuments(String acIdseq);
  public List getReferenceDocumentsForCSI(String cscsiIdseq);
}