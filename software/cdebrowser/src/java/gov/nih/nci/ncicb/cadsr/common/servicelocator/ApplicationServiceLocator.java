/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.common.servicelocator;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.service.CDEBrowserService;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.service.OCBrowserService;
import gov.nih.nci.ncicb.cadsr.contexttree.service.CDEBrowserTreeService;

public interface ApplicationServiceLocator
{
  public static final String APPLICATION_SERVICE_LOCATOR_CLASS_KEY = "ApplicationServiceLocatorClassName";

  public OCBrowserService findOCBrowserService() throws ServiceLocatorException;

  public CDEBrowserTreeService findTreeService() throws ServiceLocatorException;

  public CDEBrowserService findCDEBrowserService() throws ServiceLocatorException;
  
  //public LockingService findLockingService() throws ServiceLocatorException;
}