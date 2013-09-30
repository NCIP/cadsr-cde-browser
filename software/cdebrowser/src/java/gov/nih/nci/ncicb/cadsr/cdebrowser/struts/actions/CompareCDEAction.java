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

/**
 * CompareCDEAction
 *
 * This class is the Action class for comparing CDEs side by side
  *
 * @release 3.0
 * @author: <a href=mailto:jane.jiang@oracle.com>Jane Jiang</a>
 * @date: 8/16/2005
 * @version: $Id: CompareCDEAction.java,v 1.19 2008-07-30 14:21:46 davet Exp $
 */

package gov.nih.nci.ncicb.cadsr.cdebrowser.struts.actions;

import gov.nih.nci.ncicb.cadsr.cdebrowser.CDECompareList;
import gov.nih.nci.ncicb.cadsr.common.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ConceptDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.DerivedDataElementDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Classification;
import gov.nih.nci.ncicb.cadsr.common.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.DerivedDataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.ObjectClass;
import gov.nih.nci.ncicb.cadsr.common.resource.Property;
import gov.nih.nci.ncicb.cadsr.common.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.common.resource.handler.ClassificationHandler;
import gov.nih.nci.ncicb.cadsr.common.resource.handler.DataElementHandler;
import gov.nih.nci.ncicb.cadsr.common.resource.handler.ValidValueHandler;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.common.util.ContentTypeHelper;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.cle.persistence.HandlerFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class CompareCDEAction
 extends BrowserBaseDispatchAction {
 private static final int COLUMN_PER_CDE = 4;

 /**
  * Displays CDE Cart.
  *
  * @param mapping The ActionMapping used to select this instance.
  * @param form The optional ActionForm bean for this request.
  * @param request The HTTP Request we are processing.
  * @param response The HTTP Response we are processing.
  *
  * @return
  *
  * @throws IOException
  * @throws ServletException
  */
 public ActionForward compareCDEs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws IOException, ServletException {
  //CDECart cart = new CDECartTransferObject();
  CDECart cart = null;

  DynaActionForm compareForm = (DynaActionForm)form;

  String [] cdeIdseqArr = (String [])compareForm.get(ProcessConstants.SELECT_DE);
  CDECompareList cdeList = (CDECompareList)this.getSessionObject(request, CDE_COMPARE_LIST);

  if (cdeList == null) {
   cdeList = new CDECompareList();

   setSessionObject(request, CDE_COMPARE_LIST, cdeList, true);
  }

  if (cdeIdseqArr != null) {
   cdeList.add(cdeIdseqArr);
  }

  if (cdeList.getCdeList().size() == 0) {
	  saveMessage("cadsr.cdebrowser.cdecompare.list.empty", request);

   return mapping.findForward("search");
  }

  if (cdeList.getCdeList().size() < 2) {
	  saveMessage("cadsr.cdebrowser.cdecompare.list.oneelement", request);

   return mapping.findForward("search");
  }

  ListIterator it = cdeList.listIterator();
  List cdeDetailList = new ArrayList();

  try {
   while (it.hasNext()) {
    DataElement de = (DataElement)it.next();

    cdeDetailList.add(getDataElementDetails(de, request.getSession().getId()));
   }

   cdeList.setDetailCDEList(cdeDetailList);
  } catch (Exception e) {
   Exception ex = e;

   saveMessage("cadsr.cdebrowser.cdecompare.compare.failed", request);
   return mapping.findForward(FAILURE);
  }

  return mapping.findForward(SUCCESS);
 }
 /**
 *
 *
 * @param mapping The ActionMapping used to select this instance.
 * @param form The optional ActionForm bean for this request.
 * @param request The HTTP Request we are processing.
 * @param response The HTTP Response we are processing.
 *
 * @return
 *
 * @throws IOException
 * @throws ServletException
 */
 public ActionForward addToCDECompareList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws IOException, ServletException {
  DynaActionForm compareForm = (DynaActionForm)form;

  String [] cdeIdseqArr = (String [])compareForm.get(ProcessConstants.SELECT_DE);
  CDECompareList cdeList = (CDECompareList)this.getSessionObject(request, CDE_COMPARE_LIST);

  if (cdeList == null) {
   cdeList = new CDECompareList();

   setSessionObject(request, CDE_COMPARE_LIST, cdeList, true);
  }

  if (cdeIdseqArr != null && cdeList != null) {
   if (cdeList.add(cdeIdseqArr)) {
    saveMessage("cadsr.cdebrowser.cdecompare.list.add.success", request);
   } else {
    saveMessage("cadsr.cdebrowser.cdecompare.list.add.duplicate", request);
   }
  } else {
	  saveMessage("cadsr.cdebrowser.cdecompare.list.empty", request);
  }

  return mapping.findForward(SUCCESS);
 }

 /**
  *
  *
  * @param mapping The ActionMapping used to select this instance.
  * @param form The optional ActionForm bean for this request.
  * @param request The HTTP Request we are processing.
  * @param response The HTTP Response we are processing.
  *
  * @return
  *
  * @throws IOException
  * @throws ServletException
  */
 public ActionForward removeFromCDECompareList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                               HttpServletResponse response) throws IOException, ServletException {
  DynaActionForm compareForm = (DynaActionForm)form;

  String [] cdeIndexs = (String [])compareForm.get(CDE_TO_REMOVE);
  CDECompareList cdeList = (CDECompareList)this.getSessionObject(request, CDE_COMPARE_LIST);

  if (cdeIndexs != null && cdeList != null) {
   if (cdeList.remove(cdeIndexs)) {
    if (!cdeList.isEmpty() && cdeList.getCdeList().size() > 1)
     saveMessage("cadsr.cdebrowser.cdecompare.list.remove.success", request);
   } else {
	   saveMessage("cadsr.cdebrowser.cdecompare.list.remove.failed", request);
   }
  } else {
   //go back to homepage
	  saveMessage("cadsr.cdebrowser.cdecompare.list.empty", request);

   return mapping.findForward(FAILURE);
  }

  if (cdeList != null && cdeList.isEmpty()) {
   //go back to homepage
   saveMessage("cadsr.cdebrowser.cdecompare.list.empty", request);

   return mapping.findForward("lessThanTwo");
  }

  if (cdeList.getCdeList().size() < 2) {
   //go back to homepage
   saveMessage("cadsr.cdebrowser.cdecompare.list.oneelement", request);

   return mapping.findForward("lessThanTwo");
  }

  return mapping.findForward(SUCCESS);
 }

 /**
*
*
* @param mapping The ActionMapping used to select this instance.
* @param form The optional ActionForm bean for this request.
* @param request The HTTP Request we are processing.
* @param response The HTTP Response we are processing.
*
* @return
*
* @throws IOException
* @throws ServletException
*/
 public ActionForward doneCDECompare(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException, ServletException {
  return mapping.findForward(SUCCESS);
 }

 /**
  *
  *
  * @param mapping The ActionMapping used to select this instance.
  * @param form The optional ActionForm bean for this request.
  * @param request The HTTP Request we are processing.
  * @param response The HTTP Response we are processing.
  *
  * @return
  *
  * @throws IOException
  * @throws ServletException
  */
 public ActionForward changeCompareOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response) throws IOException, ServletException {
  DynaActionForm compareForm = (DynaActionForm)form;

  String [] newDisplayOrderArr = (String [])compareForm.get(CDE_COMPARE_DISPAY_ORDER);
  CDECompareList cdeList = (CDECompareList)this.getSessionObject(request, CDE_COMPARE_LIST);

  if (newDisplayOrderArr != null && cdeList != null) {
   cdeList.setItemOrder(newDisplayOrderArr);
  }

  return mapping.findForward(SUCCESS);
 }

 public ActionForward downloadToExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) throws IOException, ServletException {
  DynaActionForm hrefCRFForm = (DynaActionForm)form;

  CDECompareList cdeList = (CDECompareList)this.getSessionObject(request, CDE_COMPARE_LIST);

  // create a new excel workbook
  HSSFWorkbook wb = new HSSFWorkbook();
  HSSFSheet sheet = wb.createSheet();
  short rowNumber = 0;
  short colNumber = 0;

  //create bold cell style
  HSSFCellStyle boldCellStyle = wb.createCellStyle();
  HSSFFont font = wb.createFont();
  font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
  boldCellStyle.setFont(font);
  boldCellStyle.setAlignment(HSSFCellStyle.ALIGN_GENERAL);

  // Create a row and put some cells in it. Rows are 0 based.
  HSSFRow row = sheet.createRow(rowNumber++);
  HSSFCell cell = row.createCell((short)0);
  cell.setCellValue("Data Element");
  cell.setCellStyle(boldCellStyle);

  List cdeColl = cdeList.getCdeList();

  addNewRow(sheet, rowNumber++, "Public ID", boldCellStyle, cdeColl, "CDEId");
  addNewRow(sheet, rowNumber++, "Long Name", boldCellStyle, cdeColl, "longName");
  addNewRow(sheet, rowNumber++, "Document Text", boldCellStyle, cdeColl, "longCDEName");
  addNewRow(sheet, rowNumber++, "Definition", boldCellStyle, cdeColl, "preferredDefinition");
  addNewRow(sheet, rowNumber++, "Owned by Context", boldCellStyle, cdeColl, "contextName");
  addNewRow(sheet, rowNumber++, "Used by Context", boldCellStyle, cdeColl, "usingContexts");
  addNewRow(sheet, rowNumber++, "Origin", boldCellStyle, cdeColl, "origin");
  addNewRow(sheet, rowNumber++, "Workflow Status", boldCellStyle, cdeColl, "aslName");
  addNewRow(sheet, rowNumber++, "Registration Status", boldCellStyle, cdeColl, "registrationStatus");
  addNewRow(sheet, rowNumber++, "Short Name", boldCellStyle, cdeColl, "preferredName");
  addNewRow(sheet, rowNumber++, "Version", boldCellStyle, cdeColl, "version");

  row = sheet.createRow(rowNumber++);
  row = sheet.createRow(rowNumber++);
  cell = row.createCell((short)0);
  cell.setCellValue("Data Element Concept");
  cell.setCellStyle(boldCellStyle);

  List cdeConceptList = new ArrayList();

  for (int i = 0; i < cdeColl.size(); i++)
   cdeConceptList.add(i, ((DataElement)cdeColl.get(i)).getDataElementConcept());

  addNewRow(sheet, rowNumber++, "Public ID", boldCellStyle, cdeConceptList, "publicId");
  addNewRow(sheet, rowNumber++, "Long Name", boldCellStyle, cdeConceptList, "longName");
  addNewRow(sheet, rowNumber++, "Short Name", boldCellStyle, cdeConceptList, "preferredName");
  addNewRow(sheet, rowNumber++, "Definition", boldCellStyle, cdeConceptList, "preferredDefinition");
  addNewRow(sheet, rowNumber++, "Context", boldCellStyle, cdeConceptList, "contextName");
  addNewRow(sheet, rowNumber++, "Conceptual Domain Short Name", boldCellStyle, cdeConceptList, "CDPrefName");
  addNewRow(sheet, rowNumber++, "Object Class Short Name", boldCellStyle, cdeConceptList,
            "objectClass.preferredName");
  addNewRow(sheet, rowNumber++, "Property Short Name", boldCellStyle, cdeConceptList, "property.preferredName");
  addNewRow(sheet, rowNumber++, "Origin", boldCellStyle, cdeConceptList, "origin");
  addNewRow(sheet, rowNumber++, "Workflow Status", boldCellStyle, cdeConceptList, "aslName");

  row = sheet.createRow(rowNumber++);
  row = sheet.createRow(rowNumber++);
  cell = row.createCell((short)0);
  cell.setCellValue("Value Domain");
  cell.setCellStyle(boldCellStyle);

  addNewRow(sheet, rowNumber++, "Public ID", boldCellStyle, cdeColl, "valueDomain.publicId");
  addNewRow(sheet, rowNumber++, "Long Name", boldCellStyle, cdeColl, "valueDomain.longName");
  addNewRow(sheet, rowNumber++, "Short Name", boldCellStyle, cdeColl, "valueDomain.preferredName");
  addNewRow(sheet, rowNumber++, "Definition", boldCellStyle, cdeColl, "valueDomain.preferredDefinition");
  addNewRow(sheet, rowNumber++, "Data Type", boldCellStyle, cdeColl, "valueDomain.datatype");
  addNewRow(sheet, rowNumber++, "Unit of Measure", boldCellStyle, cdeColl, "valueDomain.unitOfMeasure");
  addNewRow(sheet, rowNumber++, "Display Format", boldCellStyle, cdeColl, "valueDomain.displayFormat");
  addNewRow(sheet, rowNumber++, "Maximum Length", boldCellStyle, cdeColl, "valueDomain.maxLength");
  addNewRow(sheet, rowNumber++, "Minimum Length", boldCellStyle, cdeColl, "valueDomain.minLength");
  addNewRow(sheet, rowNumber++, "Decimal Place", boldCellStyle, cdeColl, "valueDomain.decimalPlace");
  addNewRow(sheet, rowNumber++, "High Value", boldCellStyle, cdeColl, "valueDomain.highValue");
  addNewRow(sheet, rowNumber++, "Low Value", boldCellStyle, cdeColl, "valueDomain.lowValue");
  addNewRow(sheet, rowNumber++, "Value Domain Type", boldCellStyle, cdeColl, "valueDomain.VDType");
  addNewRow(sheet, rowNumber++, "Conceptual Domain Short Name", boldCellStyle, cdeColl, "valueDomain.CDPrefName");
  addNewRow(sheet, rowNumber++, "Representation", boldCellStyle, cdeColl, "valueDomain.representation.longName");
  addNewRow(sheet, rowNumber++, "Origin", boldCellStyle, cdeColl, "valueDomain.origin");
  addNewRow(sheet, rowNumber++, "Workflow Status", boldCellStyle, cdeColl, "valueDomain.aslName");
  addNewRow(sheet, rowNumber++, "Version", boldCellStyle, cdeColl, "valueDomain.version");


  List pvTitles = new ArrayList();
  pvTitles.add(0, "Value");
  pvTitles.add(1, "Value Meaning");
  pvTitles.add(2, "Description");
  List pvProperties = new ArrayList();
  pvProperties.add(0, "shortMeaningValue");
  pvProperties.add(1, "shortMeaning");
  pvProperties.add(2, "shortMeaningDescription");

  rowNumber += this.exportObjects(sheet, rowNumber, "Permissible Values",
  "valueDomain.validValues", boldCellStyle,
             cdeColl, pvProperties, pvTitles);


  List refDocPropertyTitles = new ArrayList();
  refDocPropertyTitles.add(0, "Document Name");
  refDocPropertyTitles.add(1, "Document Type");
  refDocPropertyTitles.add(2, "Document Text");
  List refDocProperties = new ArrayList();
  refDocProperties.add(0, "docName");
  refDocProperties.add(1, "docType");
  refDocProperties.add(2, "docText");

  rowNumber += this.exportObjects(sheet,   rowNumber,        "Reference Document", "refereceDocs", boldCellStyle,
                                  cdeColl, refDocProperties, refDocPropertyTitles);

  List csPropertyTitles = new ArrayList();
  csPropertyTitles.add(0, "CS* Short Name");
  csPropertyTitles.add(1, "CS* Definition");
  csPropertyTitles.add(2, "CSI* Name");
  csPropertyTitles.add(3, "CSI* Type");
  List csProperties = new ArrayList();
  csProperties.add(0, "classSchemeName");
  csProperties.add(1, "classSchemeDefinition");
  csProperties.add(2, "classSchemeItemName");
  csProperties.add(3, "classSchemeItemType");

  rowNumber += this.exportObjects(sheet, rowNumber, "Classifications", "classifications", boldCellStyle,
                                  cdeColl, csProperties, csPropertyTitles);

  row = sheet.createRow(rowNumber++);
  row = sheet.createRow(rowNumber++);
  cell = row.createCell((short)0);
  cell.setCellValue("Data Element Derivation");
  cell.setCellStyle(boldCellStyle);

  addNewRow(sheet, rowNumber++, "Derivation Type", boldCellStyle, cdeColl, "derivedDataElement.type.name");
  addNewRow(sheet, rowNumber++, "Rule", boldCellStyle, cdeColl, "derivedDataElement.rule");
  addNewRow(sheet, rowNumber++, "Method", boldCellStyle, cdeColl, "derivedDataElement.methods");
  addNewRow(sheet, rowNumber++, "Concatenation Character", boldCellStyle, cdeColl, "derivedDataElement.concatenationCharacter");

  List dedPropertyTitles = new ArrayList();
  dedPropertyTitles.add(0, "Long Name");
  dedPropertyTitles.add(1, "Context");
  dedPropertyTitles.add(2, "Public ID");
  dedPropertyTitles.add(3, "Version");
  List dedProperties = new ArrayList();
  dedProperties.add(0, "longName");
  dedProperties.add(1, "contextName");
  dedProperties.add(2, "CDEId");
  dedProperties.add(3, "version");

  rowNumber += this.exportObjects(sheet, rowNumber, "Component Data Elements",
  "derivedDataElement.dataElementDerivation", boldCellStyle,
  cdeColl, dedProperties, dedPropertyTitles);


  CDEBrowserParams params = CDEBrowserParams.getInstance();
  String excelFilename = params.getXMLDownloadDir() + "compareCDEs" + ".xls";
  FileOutputStream fileOut = new FileOutputStream(excelFilename);
  wb.write(fileOut);
  fileOut.close();

  File f = new File(excelFilename);
  String ctype = ContentTypeHelper.getContentType(f.getName());

  response.setContentType(ctype);
  response.setContentLength((int)f.length());  
  response.setHeader("Content-Disposition", "attachment;filename=\"" + f.getName() + "\"");
  response.setHeader("Pragma", "public");
  response.setHeader("Expires", "0");
  response.setHeader("Cache-Control", "max-age=0"); 
 
  try {
   // create buffer
   byte [] buffer = new byte[1024];

   int r = 0;
   // write out file
   FileInputStream fin = new FileInputStream(f);
   OutputStream out = response.getOutputStream();

   while ((r = fin.read(buffer, 0, buffer.length)) != -1) {
    out.write(buffer, 0, r);
   }

   try {
    fin.close();

    out.flush();
    out.close();
   } catch (Exception e) { }

   out = null;
   fin = null;
   buffer = null;
  } catch (Exception ex) {
   String msg = ex.getMessage();

   response.setContentType("text/html");
   response.setContentLength(msg.length());
   PrintWriter out = response.getWriter();
   out.println("Unexpected error");
   out.flush();
   out.close();
  }

  return null;
 }

 private DataElement getDataElementDetails(DataElement de, String sessionId) throws Exception {
  DataElementHandler dh = (DataElementHandler)HandlerFactory.getHandler(DataElement.class);

  de = (DataElement)dh.findObject(de.getDeIdseq(), sessionId);

  ServiceLocator locator = ServiceLocatorFactory.getLocator(CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
  AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
  ConceptDAO conDAO = daoFactory.getConceptDAO();

  if (de != null) {
   Property prop = de.getDataElementConcept().getProperty();

   if (prop != null) {
    ConceptDerivationRule
       propRule = conDAO.getPropertyConceptDerivationRuleForDEC(de.getDataElementConcept().getDecIdseq());

    de.getDataElementConcept().getProperty().setConceptDerivationRule(propRule);
   }

   ObjectClass objClass = de.getDataElementConcept().getObjectClass();

   if (objClass != null) {
    ConceptDerivationRule
       classRule = conDAO.getObjectClassConceptDerivationRuleForDEC(de.getDataElementConcept().getDecIdseq());

    de.getDataElementConcept().getObjectClass().setConceptDerivationRule(classRule);
   }

   ValidValueHandler validValueHandler = (ValidValueHandler)HandlerFactory.getHandler(ValidValue.class);
   List vvList = validValueHandler.getValidValues(de.getVdIdseq(), sessionId);

  if (vvList != null && !vvList.isEmpty()) {
    vvList = conDAO.populateConceptsForValidValues(vvList);
   }

   de.getValueDomain().setValidValues(vvList);
  }

  ClassificationHandler classificationHandler = (ClassificationHandler)HandlerFactory.getHandler(Classification.class);
  List classificationSchemes = classificationHandler.getClassificationSchemes(de.getDeIdseq(), sessionId);
  de.setClassifications(classificationSchemes);

  DerivedDataElementDAO ddeDAO = daoFactory.getDerivedDataElementDAO();
  DerivedDataElement dde = ddeDAO.findDerivedDataElement(de.getDeIdseq());
  de.setDerivedDataElement(dde);
  return de;
 }

 private void addNewRow(HSSFSheet sheet, short rowNumber, String title, HSSFCellStyle titleStyle, List cdeColl,
                        String propertyName) {
  HSSFRow row = sheet.createRow(rowNumber++);

  short colNumber = 0;
  HSSFCell cell = row.createCell(colNumber++);
  cell.setCellValue(title);
  cell.setCellStyle(titleStyle);

  for (int i = 0; i < cdeColl.size(); i++) {
   cell = row.createCell(colNumber);

   try {
    cell.setCellValue(PropertyUtils.getProperty(cdeColl.get(i), propertyName).toString());
   } catch (Exception ie) {
    cell.setCellValue("");
   }

   colNumber += COLUMN_PER_CDE;
  }
 }

 private int exportObjects(HSSFSheet sheet,          short rowNumber, String title,      String propertyName,
                           HSSFCellStyle titleStyle, List cdeColl,    List propertyList, List titleList) {
  //this row contains the number of valid values for each data element value domain
  HSSFRow row = sheet.createRow(rowNumber++);

  short colNumber = 1;
  int maxValueNumber = 0;
  HSSFCell cell;

  row = sheet.createRow(rowNumber++);
  row = sheet.createRow(rowNumber++);
  cell = row.createCell((short)0);
  cell.setCellValue(title);
  cell.setCellStyle(titleStyle);

  for (int i = 0; i < cdeColl.size(); i++) {
   cell = row.createCell(colNumber);

   try {
    int validValueSize = ((List)PropertyUtils.getProperty(cdeColl.get(i), propertyName)).size();

    if (validValueSize > maxValueNumber)
     maxValueNumber = validValueSize;

    cell.setCellValue(validValueSize + " " + title);
   } catch (Exception e) {
   //cell.setCellValue("");
      }

   colNumber += COLUMN_PER_CDE;
  }

  if (maxValueNumber > 0) {
   //this row contains valid value properties
   row = sheet.createRow(rowNumber++);

   colNumber = 1;

   for (int i = 0; i < cdeColl.size(); i++) {
    colNumber = (short)(i * COLUMN_PER_CDE + 1);

    for (int titleIdx = 0; titleIdx < titleList.size(); titleIdx++) {
     cell = row.createCell(colNumber++);

     cell.setCellValue(titleList.get(titleIdx).toString());
     cell.setCellStyle(titleStyle);
    }
   }

   for (int i = 0; i < maxValueNumber; i++) {
    colNumber = 1;

    row = sheet.createRow(rowNumber++);

    for (int j = 0; j < cdeColl.size(); j++) {
     List valueList = null;

     try {
      valueList = (List)PropertyUtils.getProperty(cdeColl.get(j), propertyName);
     } catch (Exception e) { }

     if (valueList != null && valueList.size() > i) {
      colNumber = (short)(j * COLUMN_PER_CDE + 1);

      for (int pIdx = 0; pIdx < propertyList.size(); pIdx++) {
       cell = row.createCell(colNumber++);

       try {
        cell.setCellValue(PropertyUtils.getProperty(valueList.get(i), (String)propertyList.get(pIdx)).toString());
       } catch (Exception e) {
        cell.setCellValue("");
       }
      } //end of writing one object
     }  //end if object not null
    }   //end of one row
   }    //if there is any row
  }     // end of all rows

  return (4 + maxValueNumber);
 }
}
