package gov.nih.nci.ncicb.cadsr.crfbuilder.process;

import gov.nih.nci.ncicb.cadsr.base.process.BasePersistingProcess;
import gov.nih.nci.ncicb.cadsr.dto.bc4j.BC4JFormTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.handler.FormHandler;

import oracle.cle.persistence.HandlerFactory;

import oracle.cle.process.PersistingProcess;
import oracle.cle.process.ProcessConstants;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.Service;

// Framework imports
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

import java.io.*;

// java imports
import java.util.*;


/**
 * @author Ram Chilukuri
 */
public class CopyTemplate extends BasePersistingProcess {
  public CopyTemplate() {
    this(null);
    DEBUG = false;
  }

  public CopyTemplate(Service aService) {
    super(aService);
    DEBUG = false;
  }

  /**
   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
   * this process during construction.
   */
  public void registerInfo() {
    try {
      registerStringParameter(CRFBuilderConstants.COPY_FORM_IDSEQ);
      registerStringParameter(CRFBuilderConstants.COPY_FORM_ASL_NAME);
      registerStringParameter(CRFBuilderConstants.COPY_FORM_CONTE_IDSEQ);
      registerStringParameter(CRFBuilderConstants.COPY_FORM_DEFINITION);
      registerStringParameter(CRFBuilderConstants.COPY_FORM_LONG_NAME);
      registerStringParameter(CRFBuilderConstants.COPY_FORM_PREF_NAME);
      registerStringParameter(CRFBuilderConstants.COPY_FORM_VERSION);
      registerStringParameter(CRFBuilderConstants.COPY_FORM_TYPE);
      registerStringParameter(CRFBuilderConstants.COPY_FORM_PROTO_IDSEQ);
      registerStringResult(CRFBuilderConstants.TARGET_FORM_ID);
    } catch (ProcessInfoException pie) {
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
    try {
      String qcIdseq = getStringInfo(CRFBuilderConstants.COPY_FORM_IDSEQ);
      String prefName = getStringInfo(CRFBuilderConstants.COPY_FORM_PREF_NAME);
      String longName = getStringInfo(CRFBuilderConstants.COPY_FORM_LONG_NAME);
      String version = getStringInfo(CRFBuilderConstants.COPY_FORM_VERSION);
      String def = getStringInfo(CRFBuilderConstants.COPY_FORM_DEFINITION);
      String status = getStringInfo(CRFBuilderConstants.COPY_FORM_ASL_NAME);
      String conte = getStringInfo(CRFBuilderConstants.COPY_FORM_CONTE_IDSEQ);
      String formType = getStringInfo(CRFBuilderConstants.COPY_FORM_TYPE);
      String protoIdseq =
        getStringInfo(CRFBuilderConstants.COPY_FORM_PROTO_IDSEQ);
      Form srcFrm = new BC4JFormTransferObject();
      srcFrm.setFormIdseq(qcIdseq);

      Form tarFrm = new BC4JFormTransferObject();
      tarFrm.setLongName(longName);
      tarFrm.setPreferredName(prefName);
      tarFrm.setPreferredDefinition(def);
      tarFrm.setAslName(status);
      tarFrm.setVersion(new Float(Float.parseFloat(version)));
      tarFrm.setConteIdseq(conte);
      tarFrm.setFormType(formType);
      tarFrm.setProtoIdseq(protoIdseq);

      FormHandler formHandler =
        (FormHandler) HandlerFactory.getHandler(Form.class);
      String newFrmIdseq = formHandler.copyForm(srcFrm, tarFrm, getSessionId());
      setResult(CRFBuilderConstants.TARGET_FORM_ID, newFrmIdseq);
      setCondition(SUCCESS);
    } catch (Exception ex) {
      try {
        setCondition(FAILURE);
      } catch (TransitionConditionException tce) {
        reportException(tce, DEBUG);
      }

      reportException(ex, DEBUG);
    }
  }

  protected TransitionCondition getPersistSuccessCondition() {
    return getCondition(SUCCESS);
  }

  protected TransitionCondition getPersistFailureCondition() {
    return getCondition(FAILURE);
  }
}
