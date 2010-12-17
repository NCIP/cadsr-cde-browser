package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.base.process.BasePersistingProcess;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AdminComponentDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Classification;
import gov.nih.nci.ncicb.cadsr.common.resource.Contact;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.common.util.TabInfoBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;

public class GetAdminInfo extends BasePersistingProcess {

	public GetAdminInfo() {
	    this(null);

	    DEBUG = false;
	  }

	  public GetAdminInfo(Service aService) {
	    super(aService);

	    DEBUG = false;
	  }
	  
	  /**
	   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
	   * this process during construction.
	   */
	  public void registerInfo() {
	    try {
	    	registerResultObject("tib");
	      registerResultObject("deContacts");
	      registerResultObject("decContacts");
	      registerResultObject("ocContacts");
	      registerResultObject("propContacts");
	      registerResultObject("csContacts");
	      registerResultObject("ctxContacts");
	      registerResultObject("vdContacts");
	      registerResultObject("repTermContacts");
	    }
	    catch (ProcessInfoException pie) {
	      reportException(pie, true);
	    }

	  }
	  
	  /**
	   * persist: called by start to do all persisting work for this process.  If
	   * this method throws an exception, then the condition returned by the
	   * <code>getPersistFailureCondition()</code> is set.  Otherwise, the
	   * condition returned by <code>getPersistSuccessCondition()</code> is set.
	   */
	  public void persist() throws Exception {
		  
		  TabInfoBean tib = null;
		  if (getInfoObject("tib") == null) {
			  tib = new TabInfoBean("cdebrowser_details_tabs");
		  }
		  else {
			  tib = (TabInfoBean)getInfoObject("tib");
		  }

	      if (tib.getMainTabNum() != 6) {
	        tib.setMainTabNum(6);
	      }
	      
	      DataElement de = (DataElement) getInfoObject("de");
		  List<Contact> deContacts = null;
		  List<Contact> decContacts = null;
		  List<Contact> ocContacts = null;
		  List<Contact> propContacts = null;
		  Map<Classification, List<Contact>> csContacts = null;
		  List<Contact> ctxContacts = null;
		  List<Contact> vdContacts = null;
		  List<Contact> repTermContacts = null;
		  
		  if (de != null) {
			ServiceLocator locator = ServiceLocatorFactory.getLocator(CaDSRConstants.CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
			AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
			AdminComponentDAO adminDAO = daoFactory.getAdminComponentDAO();
			deContacts = adminDAO.getContacts(de.getIdseq());
			
			if (de.getDataElementConcept() != null) {
				decContacts = adminDAO.getContacts(de.getDataElementConcept().getIdseq());
				if (de.getDataElementConcept().getObjectClass() != null) {
					ocContacts = adminDAO.getContacts(de.getDataElementConcept().getObjectClass().getIdseq());
				}
				if (de.getDataElementConcept().getProperty() != null) {
					propContacts = adminDAO.getContacts(de.getDataElementConcept().getProperty().getIdseq());
				}
			}
			
			if (de.getClassifications() != null ) {
				csContacts = new HashMap<Classification, List<Contact>>();
				for (Classification cs: (List<Classification>)de.getClassifications()) {
					csContacts.put(cs, adminDAO.getContacts(cs.getCsIdseq()));
				}
			}
			
			if (de.getConteIdseq() != null) {
				ctxContacts = adminDAO.getContacts(de.getConteIdseq());
			}
			
			if (de.getVdIdseq() != null) {
				vdContacts = adminDAO.getContacts(de.getVdIdseq());
			}
			
			if (de.getValueDomain().getRepresentation()!=null) {
				repTermContacts = adminDAO.getContacts(de.getValueDomain().getRepresentation().getIdseq());
			}
		  }
		  
		setResult("deContacts", deContacts);
		setResult("decContacts", decContacts);
		setResult("ocContacts", ocContacts);
		setResult("propContacts", propContacts);
		setResult("csContacts", csContacts);
		setResult("ctxContacts", ctxContacts);
		setResult("vdContacts", vdContacts);
		setResult("repTermContacts", repTermContacts);
		setResult("tib", tib);
		setCondition(SUCCESS);
	  }
	  
	  protected TransitionCondition getPersistSuccessCondition() {
	    return getCondition(SUCCESS);
	  }

	  protected TransitionCondition getPersistFailureCondition() {
	    return getCondition(FAILURE);
	  }
}
