package gov.nih.nci.ncicb.cadsr.persistence.bc4j.handler;

import gov.nih.nci.ncicb.cadsr.persistence.bc4j.CDEBrowserBc4jModuleImpl;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.ValidValuesValueObject;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.ValidValuesViewRowImpl;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.resource.handler.ValidValueHandler;
import gov.nih.nci.ncicb.cadsr.util.PageIterator;

import oracle.cle.persistence.Handler;
import oracle.cle.persistence.HandlerFactory;

import oracle.cle.util.CLEUtil;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.ViewObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class ValidValueHandlerImpl extends Handler implements ValidValueHandler {
  public ValidValueHandlerImpl() {
    super();
  }

  public Class getReferenceClass() {
    return ValidValue.class;
  }

  public Vector getValidValues(Object aVdIdseq, Object sessionId)
    throws Exception {
    Vector validValues = new Vector();

    try {
      CDEBrowserBc4jModuleImpl module =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      validValues = module.getValidValues(aVdIdseq);
    } catch (Exception e) {
      throw e;
    } finally {
      releaseConnection(sessionId);
    }

    return validValues;
  }

  public List getMyValidValues(Object aVdIdseq, Object sessionId,
    PageIterator vvIterator) throws Exception {
    ViewObject vw;
    Row[] vvRows = null;
    List vvList = new ArrayList(11);

    try {
      CDEBrowserBc4jModuleImpl module =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      vw = module.getMyValidValuesView(aVdIdseq);

      //vw.setMaxFetchSize(20);
      vvIterator.setScrollableObject(vw);

      vvRows = (Row[]) vvIterator.getRowsInRange();

      for (int i = 0; i < vvRows.length; i++) {
        vvList.add(new ValidValuesValueObject(
            (ValidValuesViewRowImpl) vvRows[i]));
      }
    } catch (Exception e) {
      throw e;
    } finally {
      releaseConnection(sessionId);
    }

    return vvList;
  }

  public static void main(String[] args) {
    Integer sessionId = new Integer(1);
    String testVdIdseq = new String("99BA9DC8-213F-4E69-E034-080020C9C0E0");

    try {
      ValidValueHandler vvh =
        (ValidValueHandler) HandlerFactory.getHandler(ValidValue.class);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}
