package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import oracle.cle.process.GenericProcess;
import gov.nih.nci.ncicb.cadsr.common.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.common.base.process.BaseGenericProcess;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.common.util.DBUtil;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.Service;

// Framework imports
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

import java.io.*;

import java.util.*;

/**
 * @author Ram Chilukuri
 */
public class ResetParameters extends BaseGenericProcess {
  public ResetParameters() {
    super();
    DEBUG = false;
  }

 /**
   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
   * this process during construction.
   */
  protected void registerInfo() {
    try {
      registerStringParameter("P_PARAM_TYPE");
      registerStringParameter("P_IDSEQ");
      registerStringResult("P_PARAM_TYPE");
      registerStringResult("P_IDSEQ");
      registerParameterObject(ProcessConstants.PAGE_CONTEXT);
      registerParameterObject(ProcessConstants.ALL_DATA_ELEMENTS);
      registerResultObject(ProcessConstants.PAGE_CONTEXT);
      registerResultObject(ProcessConstants.ALL_DATA_ELEMENTS);
      registerParameterObject("SEARCH");
      registerResultObject("SEARCH");
      registerResultObject("desb");
      registerParameterObject("dbUtil");
      registerStringParameter("SBR_DSN");
    }
    catch (ProcessInfoException pie) {
      reportException(pie, DEBUG);
    }
  }

  /**
   * Start:  Everything that this process does at runtime is done within the
   * scope of this method and anything that it invokes.
   */
  public void start() {
    try {
      setCondition(SUCCESS);
    }
    catch (Exception ex) {
      try {
        setCondition(FAILURE);
      }
      catch (TransitionConditionException tce) {
        reportException(tce, DEBUG);
      }

      reportException(ex, DEBUG);
    }
  }
}
