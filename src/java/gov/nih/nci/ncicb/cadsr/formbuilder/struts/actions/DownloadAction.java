package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util.CDEDetailsUtils;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;
import gov.nih.nci.ncicb.cadsr.util.ApplicationParameters;
import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.util.ContentTypeHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class DownloadAction
 extends FormBuilderSecureBaseDispatchAction {
 public ActionForward downloadFormInExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws IOException, ServletException {
  DynaActionForm hrefCRFForm = (DynaActionForm)form;

  String formIdSeq = (String)hrefCRFForm.get(FORM_ID_SEQ);
  
  FormBuilderServiceDelegate service = getFormBuilderService();
  Form crf = null;

  try {
   crf = service.getFormDetails(formIdSeq);
  } catch (FormBuilderException exp) {
   if (log.isErrorEnabled()) {
    log.error("Exception getting CRF", exp);
   }

   saveError(ERROR_FORM_RETRIEVE, request);
   saveError(ERROR_FORM_DOES_NOT_EXIST, request);
   return mapping.findForward(FAILURE);
  }

  // create a new excel workbook
  HSSFWorkbook wb = new HSSFWorkbook();
  HSSFSheet sheet = wb.createSheet();
  short rowNumber = 0;

  //create bold cell style
  HSSFCellStyle boldCellStyle = wb.createCellStyle();
  HSSFFont font = wb.createFont();
  font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
  boldCellStyle.setFont(font);
  boldCellStyle.setAlignment(HSSFCellStyle.ALIGN_GENERAL);

  // Create a row and put some cells in it. Rows are 0 based.
  HSSFRow row = sheet.createRow(rowNumber++);
  HSSFCell cell = row.createCell((short)0);
  cell.setCellValue("Long Name");
  cell.setCellStyle(boldCellStyle);
  row.createCell((short)1).setCellValue(crf.getLongName());

  row = sheet.createRow(rowNumber++);
  cell = row.createCell((short)0);
  cell.setCellValue("Definition");
  cell.setCellStyle(boldCellStyle);
  row.createCell((short)1).setCellValue(crf.getPreferredDefinition());

  row = sheet.createRow(rowNumber++);
  cell = row.createCell((short)0);
  cell.setCellValue("Context");
  cell.setCellStyle(boldCellStyle);
  row.createCell((short)1).setCellValue(crf.getContext().getName());
  
  //for multiple protocols.
  List protocols = crf.getProtocols();
  if (protocols!=null && !protocols.isEmpty()){
    Iterator it = protocols.iterator();
    while (it.hasNext()){
        Protocol  p = (Protocol)it.next();
        row = sheet.createRow(rowNumber++);
        cell = row.createCell((short)0);
        cell.setCellValue("Protocol Long Name");
        cell.setCellStyle(boldCellStyle);
        row.createCell((short)1).setCellValue(p.getLongName());
    }
  }
  
  row = sheet.createRow(rowNumber++);
  cell = row.createCell((short)0);
  cell.setCellValue("Workflow");
  cell.setCellStyle(boldCellStyle);
  row.createCell((short)1).setCellValue(crf.getAslName());

  row = sheet.createRow(rowNumber++);
  cell = row.createCell((short)0);
  cell.setCellValue("Type");
  cell.setCellStyle(boldCellStyle);
  row.createCell((short)1).setCellValue(crf.getFormType());

  row = sheet.createRow(rowNumber++);
  cell = row.createCell((short)0);
  cell.setCellValue("Public ID");
  cell.setCellStyle(boldCellStyle);
  row.createCell((short)1).setCellValue(crf.getPublicId());
  
  row = sheet.createRow(rowNumber++);
  cell = row.createCell((short)0);
  cell.setCellValue("Version");
  cell.setCellStyle(boldCellStyle);
  row.createCell((short)1).setCellValue(crf.getVersion().toString());

  if (crf.getInstruction() != null) {
   row = sheet.createRow(rowNumber++);

   cell = row.createCell((short)0);
   cell.setCellValue("Header Instruction");
   cell.setCellStyle(boldCellStyle);
   row.createCell((short)1).setCellValue(crf.getInstruction().getLongName());
  }

  if (crf.getFooterInstruction() != null) {
   row = sheet.createRow(rowNumber++);

   cell = row.createCell((short)0);
   cell.setCellValue("Footer Instruction");
   cell.setCellStyle(boldCellStyle);
   row.createCell((short)1).setCellValue(crf.getFooterInstruction().getLongName());
  }

  //export module related info
  List modules = crf.getModules();

  if (modules.size() > 0) {
   //add a blank line
   short colNumber = 0;

   row = sheet.createRow(rowNumber++);
   row = sheet.createRow(rowNumber++);
   cell = row.createCell(colNumber++);
   cell.setCellValue("Module");
   cell.setCellStyle(boldCellStyle);
   cell = row.createCell(colNumber++);
   cell.setCellValue("Module Instructions");
   cell.setCellStyle(boldCellStyle);
   
   cell = row.createCell(colNumber++);
   cell.setCellValue("Number of Repetitions");
   cell.setCellStyle(boldCellStyle);
   
   cell = row.createCell(colNumber++);
   cell.setCellValue("Question");
   cell.setCellStyle(boldCellStyle);
   cell = row.createCell(colNumber++);
   cell.setCellValue("CDE");
   cell.setCellStyle(boldCellStyle);
   cell = row.createCell(colNumber++);
   cell.setCellValue("CDE Public ID");
   cell.setCellStyle(boldCellStyle);
   cell = row.createCell(colNumber++);
   cell.setCellValue("CDE Version");
   cell.setCellStyle(boldCellStyle);
   cell = row.createCell(colNumber++);
   cell.setCellValue("Question Instructions");
   cell.setCellStyle(boldCellStyle);

//question mandatory   
   cell = row.createCell(colNumber++);
   cell.setCellValue("Answer is Mandatory");
   cell.setCellStyle(boldCellStyle);

//question default value
     cell = row.createCell(colNumber++);
     cell.setCellValue("Question Default Value");
     cell.setCellStyle(boldCellStyle);

//value domain details
 cell = row.createCell(colNumber++);
 cell.setCellValue("Value Domain Long Name");
 cell.setCellStyle(boldCellStyle);
 cell = row.createCell(colNumber++);
 cell.setCellValue("Value Domain Data Type");
 cell.setCellStyle(boldCellStyle);
 cell = row.createCell(colNumber++);
 cell.setCellValue("Value Domain Unit of Measure");
 cell.setCellStyle(boldCellStyle);
 cell = row.createCell(colNumber++);
 cell.setCellValue("Display Format");
 cell.setCellStyle(boldCellStyle);
 cell = row.createCell(colNumber++);
 cell.setCellValue("Concepts");
 cell.setCellStyle(boldCellStyle);

   cell = row.createCell(colNumber++);
   cell.setCellValue("Valid Value");
   cell.setCellStyle(boldCellStyle);
   cell = row.createCell(colNumber++);
   cell.setCellValue("Form Value Meaning Text");
   cell.setCellStyle(boldCellStyle);
   cell = row.createCell(colNumber++);
   cell.setCellValue("Form Value Meaning Desc.");
   cell.setCellStyle(boldCellStyle);
   cell = row.createCell(colNumber++);
   cell.setCellValue("Valid Value Instructions");
   cell.setCellStyle(boldCellStyle);

   for (int i = 0; i < modules.size(); i++) {
    Module module = (Module)modules.get(i);

    row = sheet.createRow(rowNumber++);
    row.createCell((short)0).setCellValue(module.getLongName());

    if (module.getInstruction() != null)
     row.createCell((short)1).setCellValue(module.getInstruction().getLongName());     

    row.createCell((short)2).setCellValue(""+module.getNumberOfRepeats());

    //export question related info
    List questions = module.getQuestions();

    for (int iQues = 0; iQues < questions.size(); iQues++) {
     row = sheet.createRow(rowNumber++);

     Question question = (Question)questions.get(iQues);
     DataElement cde = question.getDataElement();

     colNumber = 3;
     row.createCell(colNumber++).setCellValue(question.getLongName());

     if (cde != null) {
      row.createCell(colNumber++).setCellValue(cde.getLongName());
      row.createCell(colNumber++).setCellValue(cde.getCDEId());
      row.createCell(colNumber++).setCellValue(cde.getVersion().toString());
     } else
      colNumber += 3;

     if (question.getInstruction() != null)
      row.createCell(colNumber++).setCellValue(question.getInstruction().getLongName());
     else
      colNumber++;

     if (question.isMandatory())
       row.createCell(colNumber++).setCellValue("Yes");
     else
       row.createCell(colNumber++).setCellValue("No");

     //question default value
     String questionDefaultValue = question.getDefaultValue();
     if (questionDefaultValue==null || questionDefaultValue.length()==0){
         FormValidValue fvv = question.getDefaultValidValue();
         if (fvv!=null && fvv.getLongName()!=null){
             questionDefaultValue = fvv.getLongName();
         }
     }
     
     row.createCell(colNumber++).setCellValue(questionDefaultValue);     
     
     String vdLongName = "";
     String vdDataType = "";
     String vdUnitOfMeasure="";
     String vdDisplayFormat = "";
     String vdConcepts = "";
     
     DataElement de = question.getDataElement();
     if (de!=null){
         ValueDomain vd = de.getValueDomain();
         if (vd!=null){
             vdLongName = vd.getLongName();
             vdDataType = vd.getDatatype();
             vdDisplayFormat = vd.getDisplayFormat();
             vdUnitOfMeasure = vd.getUnitOfMeasure();
             vdConcepts = 
                CDEDetailsUtils.getConceptCodesUrl(
                    vd.getConceptDerivationRule(),
                    CDEBrowserParams.getInstance(),"link",",");
         }
     }
     
    row.createCell(colNumber++).setCellValue(vdLongName);     
    row.createCell(colNumber++).setCellValue(vdDataType);            
    row.createCell(colNumber++).setCellValue(vdUnitOfMeasure);     
    row.createCell(colNumber++).setCellValue(vdDisplayFormat);     
    row.createCell(colNumber++).setCellValue(vdConcepts);     

     //export valid value related info  
     List validValues = question.getValidValues();

     if (validValues.size() > 0) {
      short vvColNum = colNumber;

      for (int iVVs = 0; iVVs < validValues.size(); iVVs++) {
       FormValidValue validValue = (FormValidValue)validValues.get(iVVs);

       row = sheet.createRow(rowNumber++);
       colNumber = vvColNum;
       row.createCell(colNumber++).setCellValue(validValue.getLongName());
       row.createCell(colNumber++).setCellValue(validValue.getFormValueMeaningText());
       row.createCell(colNumber++).setCellValue(validValue.getFormValueMeaningDesc());

       if (validValue.getInstruction() != null)
        row.createCell(colNumber++).setCellValue(validValue.getInstruction().getLongName());
      }
     }
    }
   }
  }

  CDEBrowserParams params = CDEBrowserParams.getInstance();
  String excelFilename ="Form"  + crf.getPublicId() + "_v" + crf.getVersion();
  excelFilename = excelFilename.replace('/', '_').replace('.', '_');
  excelFilename = params.getXMLDownloadDir() + excelFilename+ ".xls";
  
  FileOutputStream fileOut = new FileOutputStream(excelFilename);
  wb.write(fileOut);
  fileOut.close();

  File f = new File(excelFilename);
  String ctype = ContentTypeHelper.getContentType(f.getName());

  response.setContentType(ctype);
  response.setContentLength((int)f.length());
  response.addHeader("Content-Disposition", "attachment; filename=" + f.getName());
  response.addHeader("Pragma", "No-cache");
  response.addHeader("Cache-Control", "no-cache");
  response.addHeader("Expires", "0");

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
}
