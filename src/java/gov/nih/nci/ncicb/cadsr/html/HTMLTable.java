package gov.nih.nci.ncicb.cadsr.html;

import java.lang.IllegalArgumentException;

import javax.swing.table.TableModel;

import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

public class HTMLTable  {
  private static Log log = LogFactory.getLog(HTMLTable.class.getName());
  protected int[] displayedColumns = null;
  protected String [] columnSizes = null;
  protected String [] columnHeaders = null;
  protected String html = "";
  private StringBuffer tableBuffer;
  private TableModel table;
  private boolean multipleSelect = false;
  private boolean singleSelect = false;
  private int selectColumnValueIndex;
  private String selectColumnName = "select";
  private String selectColumnHeader = "Select";
  private String tableSize = "100%";
  private String tableAlign = "";
  private String emptyTableMessage = "No matching records found for the "+
                                     "specified search criteria";
  private int numberOfColumns = 0;
  
  public HTMLTable(TableModel table) {
    this.table = table;
    log.trace("Row count in html table is "+table.getRowCount());
  }

  public void setDisplayedColumns(int[] dispColumns){
    displayedColumns = dispColumns;
  }

  public void setColumnsSize(String[] widths){
    columnSizes = widths;
  }

  public void setColumHeaders(String[] headers) {
    columnHeaders = headers;
    if (columnHeaders == null) 
      throw new IllegalArgumentException("Please specify valid column header info");
    else 
      numberOfColumns = columnHeaders.length;
    
  }
  public String getHTMLTable() {
    return tableBuffer.toString();
  }

  public void generateHTML() {
    tableBuffer = new StringBuffer();
    if (!"".equals(tableAlign)) {
      tableBuffer.append(
      "<table border=\"0\" cellspacing=\"1\" cellpadding=\"1\" width=\""+tableSize
        +"\" class=\"OraBGAccentVeryDark\" " +" align = \""+tableAlign + "\">\n");
    }
    else {
      tableBuffer.append(
      "<table border=\"0\" cellspacing=\"1\" cellpadding=\"1\" width=\""+tableSize
        +"\" class=\"OraBGAccentVeryDark\">\n");
    }
    tableBuffer.append("<tr>");
    if (singleSelect || multipleSelect ){
      tableBuffer.append("<th scope=\"COL\" class=\"OraTableColumnHeader\" "+
      "align=\"LEFT\" valign=\"BOTTOM\">" + selectColumnHeader + "</TH>");
      numberOfColumns++; 
    }
    for (int i=0; i < columnHeaders.length; i++) {
      tableBuffer.append("<th scope=\"COL\" class=\"OraTableColumnHeader\" "+
      "align=\"LEFT\" valign=\"BOTTOM\">" + columnHeaders[i] + "</TH>");
    }
    tableBuffer.append("</tr>\n");
    if (table.getRowCount() > 0) {
      for(int i=0; i < table.getRowCount(); i++) {
        tableBuffer.append("<tr>");
        if (multipleSelect) {
          tableBuffer.append("<TD CLASS=\"OraTableCellSelect\">");
          tableBuffer.append("<input type=\"checkbox\" name=\""+selectColumnName+
             "\" value=\""+table.getValueAt(i,selectColumnValueIndex)+"\">");
          tableBuffer.append("</TD>");
        }
        for (int j=0; j < displayedColumns.length; j++) {
          if (columnSizes == null)
            tableBuffer.append("<TD CLASS=\"OraTableCellText\">");
          else 
            tableBuffer.append("<TD NOWRAP WIDTH=\"" + columnSizes[j] + "\" CLASS=\"OraTableCellText\">");
          tableBuffer.append(ReplaceNull(table.getValueAt(i,displayedColumns[j])));
          tableBuffer.append("</TD>");
        }
        tableBuffer.append("</tr>\n");
      }
    }
    else {
      tableBuffer.append("<tr>");
      tableBuffer.append("<td class=\"OraTableCellText\" colspan=\""+
                numberOfColumns+"\">");
      tableBuffer.append(emptyTableMessage);
      tableBuffer.append("</td>");
      tableBuffer.append("</tr>\n");
    }
    tableBuffer.append("</table>\n");
  }

  private String ReplaceNull(Object obj) {
    if (obj == null) return "";
    else return obj.toString();
  }
  public void setSingleSelect(boolean option){
    singleSelect = option;
  }
  public void setMultipleSelect(boolean option){
    multipleSelect = option;
  }
  public void setSelectColumnName(String columnName){
    selectColumnName = columnName;
  }

  public void setSelectColumnHeader(String columnHeader){
    selectColumnHeader = columnHeader;
  }
  
  public void setSelectColumnValueIndex(int columnIndex) {
    selectColumnValueIndex = columnIndex;
  }

  public void setTableSize(String size) {
    tableSize = size ;
  }

  public void setTableAlign(String align) {
    tableAlign = align;
  }

  public void setEmptyTableMessage(String msg) {
    emptyTableMessage = msg;
  }
}