package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.base.process.*;

import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.resource.*;
import gov.nih.nci.ncicb.cadsr.util.*;

import oracle.cle.persistence.HandlerFactory;

import oracle.cle.process.PersistingProcess;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.Service;

import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

import java.io.*;

import java.util.*;

import javax.servlet.http.*;


/**
 * @author Oracle Corporation
 */
public class GetDataElementConceptDetails extends BasePersistingProcess {
  private HttpServletRequest myRequest = null;

  public GetDataElementConceptDetails() {
    this(null);

    DEBUG = false;
  }
  
  public GetDataElementConceptDetails(Service aService) {
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

    try {
      tib = new TabInfoBean("cdebrowser_details_tabs");
      myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
      tib.processRequest(myRequest);

      int tabNum = tib.getMainTabNum();

      if (tabNum != 1) {
        tib.setMainTabNum(1);
      }
     
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    setResult("tib", tib);
    setCondition(SUCCESS);
  }
  
  protected TransitionCondition getPersistSuccessCondition() {
    return getCondition(SUCCESS);
  }

  protected TransitionCondition getPersistFailureCondition() {
    return getCondition(FAILURE);
  }
}
