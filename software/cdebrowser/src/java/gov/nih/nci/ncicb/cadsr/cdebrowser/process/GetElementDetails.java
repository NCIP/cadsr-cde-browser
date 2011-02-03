package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.common.base.process.BasePersistingProcess;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;

public class GetElementDetails extends BasePersistingProcess {

	public GetElementDetails() {
		this(null);
		
		DEBUG = false;
	}

	public GetElementDetails(Service aService) {
		super(aService);

		DEBUG = false;
	}
	
	public void registerInfo() {
	    try {
	      registerStringResult("queryDE");
	      registerStringParameter("publicId");
	      registerStringParameter("publicid");
	      registerStringParameter("public_id");
	      registerStringResult("cdeId");
	      registerStringParameter("version");
	      registerStringResult("version");
	    }
	    catch (ProcessInfoException pie) {
	      reportException(pie, true);
	    }
	  }
	
	public void persist() throws Exception {
		if (getInfo("desb") == null) {
			setCondition(FAILURE);
			return;
		}
		
		String publicId = getStringInfo("publicId");
		if (publicId == null || publicId.trim().equals("")) {
			publicId = getStringInfo("publicid");
			if (publicId == null || publicId.trim().equals("")) {
				publicId = getStringInfo("public_id");
			}
		}
		String version = getStringInfo("version");
		
		if (publicId == null || publicId.trim().equals("") || version == null || version.trim().equals("")) {
			throw new Exception("public id AND version not specified");
		}
		
		setResult("cdeId", publicId);
		setResult("version", version);
		setResult("queryDE", "yes");
		
		setCondition(SUCCESS);
	}
}