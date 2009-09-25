package gov.nih.nci.ncicb.cadsr.common.downloads;

import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;

import java.sql.Connection;

/**
 * @author Sumana Hegde
 */
public interface GetXMLDownload {
	public void generateXMLForCDECart(CDECart cart, String src, String _jndiName) throws Exception;
	
	public void generateXMLForDESearch(String sWhere, String src, String _jndiName) throws Exception;
	
	public String getFileName(String prefix);
	
	public void setFileName(String sfile);

}
