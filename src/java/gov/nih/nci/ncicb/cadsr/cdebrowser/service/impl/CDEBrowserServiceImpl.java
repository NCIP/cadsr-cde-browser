package gov.nih.nci.ncicb.cadsr.cdebrowser.service.impl;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.cdebrowser.service.CDEBrowserService;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UtilDAO;

import java.util.Locale;
import java.util.Properties;

public class CDEBrowserServiceImpl implements CDEBrowserService
{
  private AbstractDAOFactory daoFactory;
  
  public CDEBrowserServiceImpl()
  {
  }
  
  public Properties getApplicationProperties(Locale locale)
  {
    UtilDAO utilDAO = daoFactory.getUtilDAO();
    return utilDAO.getApplicationProperties(CaDSRConstants.CDEBROWSER,locale.getCountry());
  }

  public Properties reloadApplicationProperties(Locale locale, String username)
  {
    // Add code to valiad user preveleges
    return getApplicationProperties(locale);
  }

  public void setDaoFactory(AbstractDAOFactory daoFactory)
  {
    this.daoFactory = daoFactory;
  }


  public AbstractDAOFactory getDaoFactory()
  {
    return daoFactory;
  }
  
}