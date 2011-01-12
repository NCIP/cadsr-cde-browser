package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.base.process.BasePersistingProcess;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AdminComponentDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Classification;
import gov.nih.nci.ncicb.cadsr.common.resource.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.common.resource.Concept;
import gov.nih.nci.ncicb.cadsr.common.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.common.resource.Contact;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.ObjectClass;
import gov.nih.nci.ncicb.cadsr.common.resource.Property;
import gov.nih.nci.ncicb.cadsr.common.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueMeaning;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.common.util.TabInfoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;

public class GetAdminInfo extends BasePersistingProcess {

	private static final String DE_CON_VAR = "deContacts";
	private static final String DEC_CON_VAR = "decContacts";
	private static final String OC_CON_VAR = "ocContacts";
	private static final String PROP_CON_VAR = "propContacts";
	private static final String VD_CON_VAR = "vdContacts";
	private static final String CS_CON_VAR = "csContacts";
	private static final String CTX_CON_VAR = "ctxContacts";
	private static final String REP_CON_VAR = "repTermContacts";
	private static final String CON_CON_VAR = "conContacts";
	private static final String VM_CON_VAR = "vmContacts";
	
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
	      registerResultObject(DE_CON_VAR);
	      registerResultObject(DEC_CON_VAR);
	      registerResultObject(OC_CON_VAR);
	      registerResultObject(PROP_CON_VAR);
	      registerResultObject(CS_CON_VAR);
	      registerResultObject(CTX_CON_VAR);
	      registerResultObject(VD_CON_VAR);
	      registerResultObject(REP_CON_VAR);
	      registerResultObject(CON_CON_VAR);
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
		  Map<Classification, List<Contact>> csContacts = null;
		  Map<Concept, List<Contact>> conContacts = null;
		  Map<ValueMeaning, List<Contact>> vmContacts = null;
		  
		  if (de != null) {			
			Map<String, Object> varMap = new HashMap<String, Object>();
			
			varMap.put(de.getIdseq(), DE_CON_VAR);
			
			if (de.getDataElementConcept() != null) {
				varMap.put(de.getDataElementConcept().getIdseq(), DEC_CON_VAR);
				ObjectClass oc = de.getDataElementConcept().getObjectClass();
				Property prop = de.getDataElementConcept().getProperty();
				if (oc != null) {
					varMap.put(oc.getIdseq(),OC_CON_VAR);
					addConceptsToMap(oc.getConceptDerivationRule(), varMap);
				}
				if (prop != null) {
					varMap.put(prop.getIdseq(), PROP_CON_VAR);
					addConceptsToMap(prop.getConceptDerivationRule(), varMap);
				}
			}
			
			if (de.getClassifications() != null ) {
				for (Classification cs: (List<Classification>)de.getClassifications()) {
					varMap.put(cs.getCsIdseq(), cs);
				}
			}
			
			if (de.getConteIdseq() != null) {
				varMap.put(de.getConteIdseq(), CTX_CON_VAR);
			}
			
			if (de.getVdIdseq() != null) {
				varMap.put(de.getVdIdseq(), VD_CON_VAR);
			}
			
			if (de.getValueDomain() != null) {
				List vvs = de.getValueDomain().getValidValues();
				if (vvs != null && !vvs.isEmpty()) {
					for (Object obj: vvs) {
						ValidValue vv = (ValidValue)obj;
						ValueMeaning vm = vv.getValueMeaning();
						if (vm != null) {
							varMap.put(vm.getIdseq(), vm);
						}
					}
				}
			}
			
			if (de.getValueDomain().getRepresentation()!=null) {
				varMap.put(de.getValueDomain().getRepresentation().getIdseq(), REP_CON_VAR);
				addConceptsToMap(de.getValueDomain().getRepresentation().getConceptDerivationRule(), varMap);
			}
			
			ServiceLocator locator = ServiceLocatorFactory.getLocator(CaDSRConstants.CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
			AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
			AdminComponentDAO adminDAO = daoFactory.getAdminComponentDAO();
			
			Map<String, List<Contact>> contactsMap = adminDAO.getContacts(new ArrayList<String>(varMap.keySet()));
			if (contactsMap != null) {
				Iterator<String> iter = contactsMap.keySet().iterator();
				while (iter.hasNext()) {
					String id = iter.next();
					Object varType = varMap.get(id);
					if (varType instanceof String) {
						setResult((String)varType, contactsMap.get(id));
					}
					else {
						if (varType instanceof Classification ) {
							if (csContacts == null) {
								csContacts = new HashMap<Classification, List<Contact>>();
								setResult(CS_CON_VAR, csContacts);
							}
							csContacts.put((Classification)varType, contactsMap.get(id));
						}
						else if (varType instanceof Concept) {
							if (conContacts == null) {
								conContacts = new HashMap<Concept, List<Contact>>();
								setResult(CON_CON_VAR, conContacts);
							}
							conContacts.put((Concept)varType, contactsMap.get(id));
						}
						else if (varType instanceof ValueMeaning) {
							if (vmContacts == null) {
								vmContacts = new HashMap<ValueMeaning, List<Contact>>();
								setResult(VM_CON_VAR, vmContacts);
							}
							vmContacts.put((ValueMeaning)varType, contactsMap.get(id));
						}
					}
				}
			}
		  }
		  	
		setResult("tib", tib);
		setCondition(SUCCESS);
	  }
	  
	  private void addConceptsToMap(ConceptDerivationRule conDR, Map<String, Object> varMap) {
		  if (conDR != null && conDR.getComponentConcepts() != null) {
				for (Object compCons: conDR.getComponentConcepts()) {
					ComponentConcept compCon = (ComponentConcept)compCons;
					Concept con = compCon.getConcept();
					if (con != null) varMap.put(con.getIdseq(), con);
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
