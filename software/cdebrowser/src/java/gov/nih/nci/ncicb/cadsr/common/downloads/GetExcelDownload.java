package gov.nih.nci.ncicb.cadsr.common.downloads;

import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;

import java.sql.Connection;

public interface GetExcelDownload {
	public void generateExcelForCDECart(CDECart cart, String src, String _jndiName) throws Exception;
	
	public void generateExcelForDESearch(String sWhere, String src, String _jndiName) throws Exception;
	
	public String getFileName();
	
	public void setFileName(String sfile);
}
