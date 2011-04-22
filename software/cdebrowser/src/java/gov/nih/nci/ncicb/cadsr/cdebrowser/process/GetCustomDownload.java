package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.common.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.common.base.process.BasePersistingProcess;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DESearchQueryBuilder;
import gov.nih.nci.ncicb.cadsr.common.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

public class GetCustomDownload extends BasePersistingProcess {
	private static Log log = LogFactory.getLog(GetCustomDownload.class.getName());

	public GetCustomDownload() {
		this(null);

		DEBUG = false;
	}

	public GetCustomDownload(Service aService) {
		super(aService);

		DEBUG = false;
	}
	
	public void registerInfo() {
		try {			
			registerStringResult("downloadIDs");
		}
		catch (ProcessInfoException pie) {
			reportException(pie, true);
		}
		catch (Exception e) {
		}
	}
	
	
	public void persist() throws Exception {
		DBUtil dbUtil = null;
		StringBuffer downloadIDsBuff = new StringBuffer();
		Statement st = null;
		ResultSet rs = null;
		
		try {
			dbUtil = (DBUtil) getInfoObject("dbUtil");
			dbUtil.getConnectionFromContainer();

			DESearchQueryBuilder deSearch = (DESearchQueryBuilder) getInfoObject(ProcessConstants.DE_SEARCH_QUERY_BUILDER);
			String query = deSearch.getXMLQueryStmt();

			dbUtil.getOracleConnectionFromContainer();
			Connection cn = dbUtil.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(query);
			
			while (rs.next()) {
				downloadIDsBuff.append(rs.getString(1)+",");
			}
			
			if (downloadIDsBuff.length() > 0) {
				downloadIDsBuff.deleteCharAt(downloadIDsBuff.length()-1);
			}
			setResult("downloadIDs", downloadIDsBuff.toString());
			
			setCondition(SUCCESS);
		}
		catch (Exception ex) {
			try {
				setCondition(FAILURE);
			}
			catch (TransitionConditionException tce) {
				reportException(tce, DEBUG);
			}
			log.error("Error generating excel file", ex);
			reportException(ex, DEBUG);
		}
		finally {
			try {
				dbUtil.returnConnection();
				if (rs != null) rs.close();
				if(st != null) st.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected TransitionCondition getPersistSuccessCondition() {
		return getCondition(SUCCESS);
	}

	protected TransitionCondition getPersistFailureCondition() {
		return getCondition(FAILURE);
	}
}
