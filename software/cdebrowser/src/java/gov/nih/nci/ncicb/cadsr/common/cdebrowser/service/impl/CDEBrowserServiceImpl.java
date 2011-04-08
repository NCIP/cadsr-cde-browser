package gov.nih.nci.ncicb.cadsr.common.cdebrowser.service.impl;

import gov.nih.nci.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.cadsr.domain.ClassificationScheme;
import gov.nih.nci.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.cadsr.domain.Context;
import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.DataElementConcept;
import gov.nih.nci.cadsr.domain.Definition;
import gov.nih.nci.cadsr.domain.DefinitionClassSchemeItem;
import gov.nih.nci.cadsr.domain.DesignationClassSchemeItem;
import gov.nih.nci.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.service.CDEBrowserService;
import gov.nih.nci.ncicb.cadsr.common.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DefinitionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.UtilDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.domain.AbstractDomainObjectsDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.resource.Designation;
import gov.nih.nci.ncicb.cadsr.common.resource.impl.DefinitionImpl;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;


public class CDEBrowserServiceImpl implements CDEBrowserService
{
	private AbstractDAOFactory daoFactory;
	AbstractDomainObjectsDAOFactory domainObjectsDaoFactory = null;

	private ApplicationService appService = null;

	public CDEBrowserServiceImpl()
	{
	}

	public Properties getApplicationProperties(Locale locale, String toolName) {
		
		UtilDAO utilDAO = daoFactory.getUtilDAO();
		Properties browseProperty = getApplicationProperties(locale);
		Properties formProperty = utilDAO.getApplicationProperties(toolName, locale.getCountry());
		if (formProperty != null)
			browseProperty.putAll(formProperty);
		return browseProperty;
	}

	public Properties getApplicationProperties(Locale locale)
	{
		UtilDAO utilDAO = daoFactory.getUtilDAO();
		Properties browseProperty = utilDAO.getApplicationProperties(CaDSRConstants.CDEBROWSER,locale.getCountry());
		Properties urlProperty = utilDAO.getApplicationURLProperties(locale.getCountry());
		Properties cadsrProperty = utilDAO.getApplicationProperties(CaDSRConstants.cadsrToolName,locale.getCountry()); 
		if (urlProperty != null)
			browseProperty.putAll(urlProperty);   //"FormBuilder_URL", formProperty.get("URL"));
		if (cadsrProperty != null)
			browseProperty.putAll(cadsrProperty);   //"context_test", formProperty.get("EXCLUDE.CONTEXT.00.NAME"));

		return browseProperty;
	}

	public Properties reloadApplicationProperties(Locale locale, String username)
	{
		// Add code to valid user privileges
		return getApplicationProperties(locale);
	}

	public void setDaoFactory(AbstractDAOFactory daoFactory)
	{
		this.daoFactory = daoFactory;
	}


	public AbstractDAOFactory getDaoFactory()
	{
		return daoFactory;
	}
	
	/**
	 * instantiate the application service for cadsr api
	 *
	 */
	private void getCadsrService()
	{
		try {
			if (appService == null)
			{
				CDEBrowserParams params = CDEBrowserParams.getInstance();
				String url = params.getCadsrAPIUrl();
				if (!url.equals(""))
					appService = ApplicationServiceProvider.getApplicationServiceFromUrl(url, "CaDsrServiceInfo");
				else
					appService = ApplicationServiceProvider.getApplicationService("CaDsrServiceInfo");
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}

	/**
	 * This method populate the input params designation and definition
	 * release 3.1 TT 1628
	 * @param publicId
	 */
	public void populateDataElementAltNameDef (gov.nih.nci.ncicb.cadsr.common.resource.DataElement de)
	{

		DataElement deExample = new DataElement();      
		deExample.setPublicID(new Long(de.getCDEId()));

		List deList = null;

		try { 
			this.getCadsrService();
			deList = appService.search(deExample.getClass(), deExample);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		//Code for the GF 22283 
		DataElement dePop = new DataElement();
		for (Object deObj: deList){
			DataElement dePopObj = (DataElement)deObj;
			if("Yes".equalsIgnoreCase(dePopObj.getLatestVersionIndicator())){
				dePop = dePopObj;				
			}			
		}		
		//DataElement dePop = (DataElement)deList.get(0);
		//End of Code for GF 22283		
	
		for (gov.nih.nci.cadsr.domain.Designation altName : dePop.getDesignationCollection()) {
			//find the designation that match the altName
			for (int i=0; i<de.getDesignations().size(); i++) {
				Designation designation =(Designation) de.getDesignations().get(i);
				if (altName.getId().equalsIgnoreCase(designation.getDesigIDSeq())){

					Collection<DesignationClassSchemeItem> desCsis = altName.getDesignationClassSchemeItemCollection();
					List<ClassSchemeClassSchemeItem> cscsis = new ArrayList<ClassSchemeClassSchemeItem>();

					for(DesignationClassSchemeItem desCsi : desCsis) 
						cscsis.add(desCsi.getClassSchemeClassSchemeItem());

					//create a resource object from the bean
					for (int j=0; j<cscsis.size(); j++) {
						designation.addCscsi(createCsCsiFromBean(cscsis.get(j)));
					}
					break;
				}
			}
		}
		
		
		
		for(Definition def : dePop.getDefinitionCollection()) {
			gov.nih.nci.ncicb.cadsr.common.resource.Definition definition = new DefinitionImpl();
			definition.setContext(new ContextTransferObject());
			definition.getContext().setConteIdseq(def.getContext().getId());
			definition.getContext().setName(def.getContext().getName());
			definition.setDefinition(def.getText());
			definition.setType(def.getType());			
			//definition.setLanguage(defBean.get);

			Collection<DefinitionClassSchemeItem> defCsis = def.getDefinitionClassSchemeItemCollection();
			List<ClassSchemeClassSchemeItem> cscsis = new ArrayList<ClassSchemeClassSchemeItem>();

			for(DefinitionClassSchemeItem defCsi : defCsis) 
				cscsis.add(defCsi.getClassSchemeClassSchemeItem());

			for (int i=0; i<cscsis.size(); i++) {
				definition.addCsCsi(createCsCsiFromBean(cscsis.get(i)));
			}
			if (de.getDefinitions() == null)
				de.setDefinitions(new ArrayList());
			de.getDefinitions().add(definition);
		}
		
		DataElementConcept dec = dePop.getDataElementConcept();
		if (dec!=null && de.getDataElementConcept() != null) {
			Collection<Definition> decDefs = dec.getDefinitionCollection();
			if (decDefs != null) {
				Iterator<Definition> decDefsIter = decDefs.iterator();
				List<gov.nih.nci.ncicb.cadsr.common.resource.Definition> bc4jDefs = new ArrayList<gov.nih.nci.ncicb.cadsr.common.resource.Definition>();
				while (decDefsIter.hasNext()) {
					DefinitionTransferObject dto = new DefinitionTransferObject();
					Definition def = decDefsIter.next();
					dto.setDefinition(def.getText());
					dto.setType(def.getType());
					
					bc4jDefs.add(dto);
				}
				
				de.getDataElementConcept().setDefinitions(bc4jDefs);
			}
		}
		
		ValueDomain vd = dePop.getValueDomain();
		if (vd!=null && de.getValueDomain() != null) {
			Collection<Definition> vdDefs = vd.getDefinitionCollection();
			if (vdDefs != null) {
				Iterator<Definition> vdDefsIter = vdDefs.iterator();
				List<gov.nih.nci.ncicb.cadsr.common.resource.Definition> bc4jDefs = new ArrayList<gov.nih.nci.ncicb.cadsr.common.resource.Definition>();
				while (vdDefsIter.hasNext()) {
					DefinitionTransferObject dto = new DefinitionTransferObject();
					Definition def = vdDefsIter.next();
					dto.setDefinition(def.getText());
					dto.setType(def.getType());
					
					Context ctx = def.getContext();
					gov.nih.nci.ncicb.cadsr.common.resource.Context bc4jCtx = new gov.nih.nci.ncicb.cadsr.common.dto.bc4j.BC4JContextTransferObject();
					bc4jCtx.setName(ctx.getName());
					bc4jCtx.setDescription(ctx.getDescription());
					
					dto.setContext(bc4jCtx);
					
					List<gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem> bc4jCsis = new ArrayList<gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem>();
					Collection<DefinitionClassSchemeItem> defCsis = def.getDefinitionClassSchemeItemCollection();
					if (defCsis != null) {
						Iterator<DefinitionClassSchemeItem> defCsiIter = defCsis.iterator();
						while (defCsiIter.hasNext()) {
							DefinitionClassSchemeItem defCsi = defCsiIter.next();
							ClassificationSchemeItem csi = defCsi.getClassSchemeClassSchemeItem().getClassificationSchemeItem();
							gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem bc4jCsi = new gov.nih.nci.ncicb.cadsr.common.dto.CSITransferObject();
							bc4jCsi.setClassSchemeItemName(csi.getLongName());
							bc4jCsi.setClassSchemeItemType(csi.getType());
							bc4jCsi.setCsiDescription(csi.getPreferredDefinition());
							bc4jCsi.setCsiIdseq(csi.getId());
							bc4jCsi.setCsiId(csi.getPublicID().intValue());
							bc4jCsi.setCsiVersion(csi.getVersion());
							
							ClassificationScheme cs = defCsi.getClassSchemeClassSchemeItem().getClassificationScheme();
							bc4jCsi.setClassSchemeLongName(cs.getLongName());
							bc4jCsi.setClassSchemeType(cs.getType());
							bc4jCsi.setClassSchemeDefinition(cs.getPreferredDefinition());
							bc4jCsi.setClassSchemePrefName(cs.getPreferredName());
							bc4jCsi.setCsIdseq(cs.getId());
							bc4jCsi.setCsVersion(cs.getVersion());
						}
					}
					dto.setCsCsis(bc4jCsis);
					bc4jDefs.add(dto);
				}
				
				de.getValueDomain().setDefinitions(bc4jDefs);
			}
		}

	}

	public void setDomainObjectsDaoFactory(AbstractDomainObjectsDAOFactory domainObjectsDaoFactory)
	{
		this.domainObjectsDaoFactory = domainObjectsDaoFactory;
	}


	public AbstractDomainObjectsDAOFactory getDomainObjectsDaoFactory()
	{
		return domainObjectsDaoFactory;
	}

	private ClassSchemeItem createCsCsiFromBean (ClassSchemeClassSchemeItem cscsiIn){
		ClassSchemeItem cscsi = new CSITransferObject();
		cscsi.setCsCsiIdseq(cscsiIn.getId());
		cscsi.setClassSchemeLongName(cscsiIn.getClassificationScheme().getLongName());
		cscsi.setClassSchemePrefName(cscsiIn.getClassificationScheme().getPreferredName());   
		cscsi.setClassSchemeDefinition(cscsiIn.getClassificationScheme().getPreferredDefinition());
		cscsi.setClassSchemeItemName(cscsiIn.getClassificationSchemeItem().getLongName());
		cscsi.setClassSchemeItemType(cscsiIn.getClassificationSchemeItem().getType());
		return cscsi;
	}

	public List getReferenceDocuments(String acIdseq){
		ClassificationScheme cs = new ClassificationScheme();
		cs.setId(acIdseq);
		List csList;
		try {
			this.getCadsrService();
			csList = appService.search(cs.getClass(), cs);
		} catch (ApplicationException e) {
			throw new RuntimeException(e);
		}
		cs = (ClassificationScheme)csList.get(0);


		return new ArrayList(cs.getReferenceDocumentCollection());
	}

	 public List getReferenceDocumentsForCSI(String csiIdseq){
		ClassificationSchemeItem csi = new ClassificationSchemeItem();
		csi.setId(csiIdseq);
		List csiList;
		try {
			this.getCadsrService();
			csiList = appService.search(csi.getClass(), csi);
		} catch (ApplicationException e) {
			throw new RuntimeException(e);
		}
		csi = (ClassificationSchemeItem)csiList.get(0);

		return new ArrayList(csi.getReferenceDocumentCollection());
 }
}