/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.base.process.*;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.DerivedDataElementDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.*;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.common.util.*;

import javax.servlet.http.*;

import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;


/**
 * @author Oracle Corporation
 */
public class GetDataElementDerivation extends BasePersistingProcess {
  private HttpServletRequest myRequest = null;

  public GetDataElementDerivation() {
    this(null);

    DEBUG = false;
  }

  public GetDataElementDerivation(Service aService) {
    super(aService);

    DEBUG = false;
  }

  /**
   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
   * this process during construction.
   */
  public void registerInfo() {
    try {
      registerResultObject("tib");
      registerParameterObject("derivedDe");
      registerResultObject("derivedDe");
      
    }
    catch (ProcessInfoException pie) {
      reportException(pie, true);
    }

  }


  /**
   * persist: called by start to do all persisting work for this process.  If
   * this method throws an exception, then the condition returned by the
   * <code>getPersistFailureCondition()</code> is set.  Otherwise, the
   * condition returned by <code>getPersistSuccessCondition()</code> is set.
   */
  public void persist() throws Exception {
    TabInfoBean tib = null;
    DerivedDataElement dde = null;

    try {
      tib = new TabInfoBean("cdebrowser_details_tabs");
      myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);

      int tabNum = tib.getMainTabNum();

      if (tabNum != 5) {
        tib.setMainTabNum(5);
      }
      
      // TT #257 added support for derived data element
      DataElement de = (DataElement) getInfoObject("de");
      dde = (DerivedDataElement)getInfoObject ("derivedDe");
      if (dde == null) {
         ServiceLocator locator = 
         ServiceLocatorFactory.getLocator(CaDSRConstants.CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
         AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
         DerivedDataElementDAO ddeDAO = daoFactory.getDerivedDataElementDAO();
         dde = ddeDAO.findDerivedDataElement(de.getIdseq());
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    setResult("tib", tib);
    setResult("derivedDe", dde);
    setCondition(SUCCESS);
  }

  protected TransitionCondition getPersistSuccessCondition() {
    return getCondition(SUCCESS);
  }

  protected TransitionCondition getPersistFailureCondition() {
    return getCondition(FAILURE);
  }
}
