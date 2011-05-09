package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelDownloadUtil {

	private HSSFWorkbook wb;
	private HSSFSheet currentSheet;
	private HSSFRow currentRow;
	private HSSFCell currentCol;
	
	HSSFCellStyle boldCellStyle;
	HSSFFont font;
	
	volatile int rowNumber;
	volatile short col;
	int rowOffset = 0;
	
	public ExcelDownloadUtil() {
		wb = new HSSFWorkbook();
	}
	
	public void init(List<String> colHeaders) {
		rowNumber = 0;
		col = 0;
		
		initStyles();
		currentSheet = wb.createSheet();
		newRecord(0);
		writeHeader(colHeaders);
	}
	
	private void initStyles() {
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		boldCellStyle = wb.createCellStyle();
		boldCellStyle.setFont(font);
		boldCellStyle.setAlignment(HSSFCellStyle.ALIGN_GENERAL);
	}
	
	private void writeHeader(List<String> colHeaders) {
		for (String colHeader: colHeaders) {
			HSSFCell newCol = nextCol();
			newCol.setCellValue(colHeader);
			newCol.setCellStyle(boldCellStyle);
		}
		newRecord(0);
	}
	
	public synchronized void newRecord(int offset) {
		rowNumber += offset + rowOffset;
		currentRow = currentSheet.createRow(rowNumber++);
		col = 0;
		rowOffset = 0;
	}
	
	private synchronized HSSFCell nextCol() {
		currentCol = currentRow.createCell(col++);
		return currentCol;
	}
	
	public synchronized void write(Object data) {
		write(nextCol(), data);
	}
	
	private synchronized void write(HSSFCell cell, Object data) {
		if (data instanceof String) {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue((String)data);
		} else if (data instanceof Float) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Float)data);
		}else if (data instanceof Double) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Double)data);
		}
	}
		
	public synchronized void write(List<Object[]> nestedRecords) {
		int rowAndColsOffset[] = write(nestedRecords, currentRow.getRowNum(), col);
		
		if (rowOffset < rowAndColsOffset[0]) rowOffset = rowAndColsOffset[0];
		col += rowAndColsOffset[1];
	}
	
	private synchronized int[] write(List<Object[]> nestedRecords, int rowNum, short colNum) {
		int[] rowAndColsOffset = new int[2];
		int[] nestedRecsOffset = new int[2];
		
		rowAndColsOffset[0] = nestedRecords.size()-1; // rows offset is atleast equal to no. of nested records (1 rec / row)
		
		for (int i=0;i<nestedRecords.size();i++) {
			HSSFRow row = currentSheet.createRow(rowNum+i+nestedRecsOffset[0]);
			Object[] nestedRow = nestedRecords.get(i);
			if (rowAndColsOffset[1] < nestedRow.length) rowAndColsOffset[1] = nestedRow.length;
			for (int j=0;j<nestedRow.length;j++) {
				Object nestedCol = nestedRow[j];
				short currColNum = (short)(colNum+j);
				if (nestedCol instanceof List) {
					 int offsetRecs[] = write((List<Object[]>)nestedCol, row.getRowNum(), currColNum);
					 nestedRecsOffset[0] += offsetRecs[0];
					 if (offsetRecs[1] > nestedRecsOffset[1]) nestedRecsOffset[1] = offsetRecs[1]-1;
				} else {
					write(row.createCell(currColNum), nestedCol);
				}
			}
		}
		rowAndColsOffset[0] += nestedRecsOffset[0];
		rowAndColsOffset[1] += nestedRecsOffset[1];
		return rowAndColsOffset;
	}
	
	public void print(OutputStream out) throws IOException{
		wb.write(out);
	}
}
