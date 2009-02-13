package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.handler;

import gov.nih.nci.ncicb.cadsr.common.cdebrowser.userexception.*;
import gov.nih.nci.ncicb.cadsr.common.dto.bc4j.BC4JDataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementConceptTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ValueDomainTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.CDEBrowserBc4jModuleImpl;
import gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.DataElementsViewRowImpl;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomain;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.handler.DataElementHandler;
import gov.nih.nci.ncicb.cadsr.common.util.BC4JPageIterator;
import gov.nih.nci.ncicb.cadsr.common.util.PageIterator;

import oracle.cle.persistence.Handler;
import oracle.cle.persistence.HandlerFactory;

import oracle.cle.util.CLEUtil;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.ViewObject;

import oracle.jbo.domain.Number;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import oracle.jbo.server.ViewRowImpl;


public class DataElementHandlerImpl extends Handler
  implements DataElementHandler {
  public DataElementHandlerImpl() {
  }

  public Class getReferenceClass() {
    return DataElement.class;
  }

  public Object findObject(
    Object key,
    Object sessionId) throws Exception {
    DataElement dataElement = null;

    try {
      CDEBrowserBc4jModuleImpl module =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      dataElement = module.getDataElements(key);
      int publicId = new Integer(dataElement.getCDEId()).intValue();
      
      List deList= module.getAllCDEVersions(publicId);
      removeCurrentVersion(dataElement,deList);
      dataElement.setOtherVersions(deList);        
    }
    catch (Exception e) {
      System.out.println(
        "Exception caught in " +
        "DataElementHandlerImpl.findObject(String, String) " + e.getMessage());
      e.printStackTrace();
      throw e;
    }
    finally {
      releaseConnection(sessionId);
    }

    return dataElement;
  }

  public List findDataElementsBasedOnQuery(
    String sqlQuery,
    Object[] queryParams,
    Object sessionId,
    PageIterator deIterator) throws Exception {
    List deList = new ArrayList(1);
    ViewObject runtimeVO = null;

    try {
      CDEBrowserBc4jModuleImpl am =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      runtimeVO = am.createViewObjectFromQueryStmt(null, sqlQuery);

      deIterator.setScrollableObject(runtimeVO);

      Row[] deRows = (Row[]) deIterator.getRowsInRange();

      for (int i = 0; i < deRows.length; i++) {
        DataElement de = new BC4JDataElementTransferObject();
        de.setDeIdseq((String) deRows[i].getAttribute(0));
        de.setPreferredName((String) deRows[i].getAttribute(1));
        de.setLongName((String) deRows[i].getAttribute(2));

        de.setLongCDEName(checkForNull((String)deRows[i].getAttribute(3)));
        de.setContextName((String)deRows[i].getAttribute(4));
        de.setAslName((String)deRows[i].getAttribute(5));
        de.setCDEId(checkForNull((String)deRows[i].getAttribute(6)));
        de.setVersion(
          new Float(((Number)deRows[i].getAttribute(7)).floatValue()));
        de.setUsingContexts(checkForNull((String)deRows[i].getAttribute(8)));

        /*de.setContextName((String) deRows[i].getAttribute(3));
        de.setAslName((String) deRows[i].getAttribute(4));
        de.setCDEId(checkForNull((String) deRows[i].getAttribute(5)));
        de.setVersion(
          new Float(((Number) deRows[i].getAttribute(6)).floatValue()));
        de.setUsingContexts(checkForNull((String) deRows[i].getAttribute(7)));*/
        
        deList.add(de);
      }
    }
    catch (Exception ex) {
      System.out.println(
        "Exception caught in " +
        "DataElementHandlerImpl.findDataElementsBasedOnQuery(String , String) " +
        ex.getMessage());
      ex.printStackTrace();
      throw ex;
    }
    finally {
      if (runtimeVO != null) {
        runtimeVO.clearCache();
      }

      releaseConnection(sessionId);
    }

    return deList;
  }

  public List findDataElementsFromQueryClause(
    String sqlQuery,
    String orderByClause,
    Object sessionId,
    PageIterator deIterator) throws Exception {
    List deList = new ArrayList(1);
    ViewObject runtimeVO = null;
    long rowCount = 0;

    try {
      CDEBrowserBc4jModuleImpl am =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      runtimeVO = am.createViewObjectFromQueryStmt(null, sqlQuery);
      rowCount = runtimeVO.getEstimatedRowCount();
      runtimeVO.setOrderByClause(orderByClause);
      runtimeVO.executeQuery();

      deIterator.setScrollableObject(runtimeVO, rowCount);

      Row[] deRows = (Row[]) deIterator.getRowsInRange();

      for (int i = 0; i < deRows.length; i++) {
        DataElement de = new BC4JDataElementTransferObject();
        de.setDeIdseq(((String) deRows[i].getAttribute(0)).trim());
        de.setPreferredName((String) deRows[i].getAttribute(1));
        de.setLongName((String) deRows[i].getAttribute(2));

        de.setLongCDEName(checkForNull((String)deRows[i].getAttribute(3)));
        de.setContextName((String)deRows[i].getAttribute(4));
        de.setAslName((String)deRows[i].getAttribute(5));
        de.setCDEId(checkForNull((String)deRows[i].getAttribute(6)));
        de.setVersion(
          new Float(((Number)deRows[i].getAttribute(7)).floatValue()));
        de.setUsingContexts(checkForNull((String)deRows[i].getAttribute(8)));

        ValueDomain vd = new ValueDomainTransferObject();
        vd.setVdIdseq(((String)deRows[i].getAttribute(9)).trim());
        de.setValueDomain(vd);
        DataElementConcept dec = new DataElementConceptTransferObject();
        dec.setDecIdseq(((String)deRows[i].getAttribute(10)).trim());
        de.setDataElementConcept(dec);
        Context conte = new ContextTransferObject ();
        conte.setConteIdseq(((String)deRows[i].getAttribute(11)).trim());
        de.setContext(conte);
        de.setPreferredDefinition((String)deRows[i].getAttribute(12));
        //System.out.println("Registration Status "+);
        de.setRegistrationStatus((String)checkForNull(deRows[i].getAttribute(13)));

        /*de.setContextName((String) deRows[i].getAttribute(3));
        de.setAslName((String) deRows[i].getAttribute(4));
        de.setCDEId(checkForNull((String) deRows[i].getAttribute(5)));
        de.setVersion(
          new Float(((Number) deRows[i].getAttribute(6)).floatValue()));
        de.setUsingContexts(checkForNull((String) deRows[i].getAttribute(7)));*/

        deList.add(de);
      }
    }
    catch (Exception ex) {
      System.out.println(
        "Exception caught in " +
        "DataElementHandlerImpl.findDataElementsBasedOnQuery(String , String) " +
        ex.getMessage());
      ex.printStackTrace();
      throw ex;
    }
    finally {
      if (runtimeVO != null) {
        runtimeVO.clearCache();
      }

      releaseConnection(sessionId);
    }

    return deList;
  }

  public List findDataElementIdsFromQueryClause(
    String sqlQuery,
    String orderByClause,
    Object sessionId) throws Exception {
    List deList = new ArrayList(1);
    ViewObject runtimeVO = null;

    try {
      CDEBrowserBc4jModuleImpl am =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      runtimeVO = am.createViewObjectFromQueryStmt(null, sqlQuery);
      runtimeVO.setOrderByClause(orderByClause);
      runtimeVO.executeQuery();

      while (runtimeVO!=null&&runtimeVO.hasNext()) {
        ViewRowImpl vvImpl =  (ViewRowImpl)runtimeVO.next();
        String deId = (String)vvImpl.getAttribute(0);
        deList.add(deId);
      }
    }
    catch (Exception ex) {
      System.out.println(
        "Exception caught in " +
        "DataElementHandlerImpl.findDataElementsBasedOnQuery(String , String) " +
        ex.getMessage());
      ex.printStackTrace();
      throw ex;
    }
    finally {
      if (runtimeVO != null) {
        runtimeVO.clearCache();
      }

      releaseConnection(sessionId);
    }

    return deList;
  }
  


  public DataElement findDataElementsByPublicId(
    int cdeId,
    float version,
    Object sessionId) throws DataElementNotFoundException, Exception {
    DataElement de = null;

    try {
      CDEBrowserBc4jModuleImpl am =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      de = am.findDataElementByCdeId(cdeId, version);
    }
    catch (DataElementNotFoundException dex) {
      System.out.println(
        "DataElementNotFoundException caught in findDataElementsByPublicId (...)" +
        dex.getMessage());
      throw dex;
    }
    catch (Exception ex) {
      System.out.println(
        "Exception caught in findDataElementsByPublicId (...)" +
        ex.getMessage());
      ex.printStackTrace();
      throw ex;
    }
    finally {
      releaseConnection(sessionId);
    }

    return de;
  }

  public List getAllFormUsages(
    Object deIdseq,
    Object sessionId,
    PageIterator deIterator) throws Exception {
    List usages = null;

    try {
      CDEBrowserBc4jModuleImpl am =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      usages = am.getFormUsagesForADataElement(deIdseq, deIterator);
    }
    catch (Exception ex) {
      System.out.println("Exception caught in getAllFormUsages(...)");
      ex.printStackTrace();
      throw ex;
    }
    finally {
      releaseConnection(sessionId);
    }

    return usages;
  }
  public List getAllFormUsages(
    Object deIdseq,
    Object sessionId
    ) throws Exception {
    List usages = null;

    try {
      CDEBrowserBc4jModuleImpl am =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      usages = am.getFormUsagesForADataElement(deIdseq);
    }
    catch (Exception ex) {
      System.out.println("Exception caught in getAllFormUsages(...)");
      ex.printStackTrace();
      throw ex;
    }
    finally {
      releaseConnection(sessionId);
    }

    return usages;
  }  

  /*public static void main(String[] args) {
    Integer sessionId = new Integer(1);
    PageIterator pg = new BC4JPageIterator(40);

    try {
      DataElementHandler dh =
        (DataElementHandler) HandlerFactory.getHandler(DataElement.class);

      DataElement de =
         (DataElement) dh.findDataElementsByPublicId(62757, 2.31f, sessionId);
         System.out.println("Long Name is: " + de.getLongName());
      List l =
        dh.getAllFormUsages(
          "99BA9DC8-254B-4E69-E034-080020C9C0E0", sessionId, pg);
      System.out.println("List size is " + l.size());
    }
    catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }*/

  /**
   * Check for null value for the given Object. If the input Object is null, an
   * empty String is returned, else toString() of the given object is
   * returned.
   *
   * @param inputObj an <code>Object</code>.
   *
   * @return the <code>String</code> value for the given Object.
   */
  private String checkForNull(Object inputObj) {
    if (inputObj == null) {
      return new String("");
    }
    else {
      return inputObj.toString();
    }
  }
  private void removeCurrentVersion(DataElement currCDE, List cdes)
  {
      if(cdes==null) return;
      if(cdes.isEmpty() )return;
     int index = cdes.indexOf(currCDE);
     cdes.remove(index);
      
  }
}
