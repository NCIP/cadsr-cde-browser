package gov.nih.nci.ncicb.cadsr.contexttree.service.impl;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.contexttree.CsCSICatetegoryHolder;
import gov.nih.nci.ncicb.cadsr.contexttree.CsCsiCategorytHolder;
import gov.nih.nci.ncicb.cadsr.contexttree.TreeConstants;
import gov.nih.nci.ncicb.cadsr.contexttree.TreeFunctions;
import gov.nih.nci.ncicb.cadsr.contexttree.TreeIdGenerator;
import gov.nih.nci.ncicb.cadsr.contexttree.service.CDEBrowserTreeService;
import gov.nih.nci.ncicb.cadsr.common.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextHolderTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ClassificationSchemeDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.ContextHolder;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.webtree.CSIRegStatusNode;
import gov.nih.nci.ncicb.webtree.ClassSchemeContainerNode;
import gov.nih.nci.ncicb.webtree.ClassSchemeItemNode;
import gov.nih.nci.ncicb.webtree.ClassSchemeNode;
import gov.nih.nci.ncicb.webtree.ClassSchemeRegStatusNode;
import gov.nih.nci.ncicb.webtree.LazyActionTreeNode;
import gov.nih.nci.ncicb.webtree.ProtocolFormNode;
import gov.nih.nci.ncicb.webtree.WebNode;

import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringEscapeUtils;

public class CDEBrowserTreeServiceImpl
implements CDEBrowserTreeService {
	private ServiceLocator locator;
	private AbstractDAOFactory daoFactory;

	/**
	 * Currently the locator classname is hard code need to be refactored
	 */
	public CDEBrowserTreeServiceImpl() { }

	/**
	 * @returns a Context holder that contains a contect object and context we node
	 */
	public List getContextNodeHolders(TreeFunctions treeFunctions, TreeIdGenerator idGen,
			String excludeList) throws Exception {
		ContextDAO dao = daoFactory.getContextDAO();

		List contexts = dao.getAllContexts(excludeList);
		ListIterator it = contexts.listIterator();
		List holders = new ArrayList();

		while (it.hasNext()) {
			Context context = (Context)it.next();

			ContextHolder holder = new ContextHolderTransferObject();
			holder.setContext(context);
			holder.setNode(getContextNode(idGen.getNewId(), context, treeFunctions));
			holders.add(holder);
		}

		return holders;
	}
	/**
	 * @returns two maps with contextid as key and value a holder object containing web node
	 * one containg the forms with no protocol and other with protocols
	 */
	public List getAllContextProtocolNodes(TreeFunctions treeFunctions, TreeIdGenerator idGen) throws Exception {
		Map protoCSMap = this.getFormClassificationNodes(treeFunctions, idGen, CaDSRConstants.FORM_CS_TYPE,
				CaDSRConstants.FORM_CSI_TYPE);

		Map treeNodeMap = new HashMap();

		Map allFormsWithProtocol = new HashMap();

		Map allFormsWithNoProtocol = new HashMap();
		FormDAO dao = daoFactory.getFormDAO();
		List forms = dao.getAllFormsOrderByContextProtocol();
		Map protocolHolder = new HashMap();
		Iterator iter = forms.iterator();

		while (iter.hasNext()) {
			Form currForm = (Form)iter.next();

			String currContextId = null;

			// When the form and the Protocol belongs to different context, the Protocol
			// context takes priority
			if (currForm.getProtocols() != null && currForm.getProtocols().size()>0)
				currContextId = currForm.getProtocols().get(0).getConteIdseq();

			if (currContextId == null)
				currContextId = currForm.getContext().getConteIdseq();

			Map currCSMap = (Map)protoCSMap.get(currContextId);

			String currProtoIdSeq = null;

			if (currForm.getProtocols() != null && currForm.getProtocols().size()>0)
				currProtoIdSeq = currForm.getProtocols().get(0).getProtoIdseq();
			DefaultMutableTreeNode formNode = getFormNode(idGen.getNewId(), currForm, treeFunctions, false);

			// add form node to protocol node
			if (currProtoIdSeq != null && !currProtoIdSeq.equals("")) {
				List protocolList = (List)allFormsWithProtocol.get(currContextId);

				if (protocolList == null) {
					protocolList = new ArrayList();

					allFormsWithProtocol.put(currContextId, protocolList);
				}

				DefaultMutableTreeNode protoNode = (DefaultMutableTreeNode)protocolHolder.get(currProtoIdSeq);

				if (protoNode == null) {
					protoNode = getProtocolNode(idGen.getNewId(), currForm.getProtocols().get(0), currContextId, treeFunctions);

					protocolHolder.put(currProtoIdSeq, protoNode);
					protocolList.add(protoNode);
					treeNodeMap.clear();
				}

				// check and see if form need to be added to cs tree
				if (currForm.getClassifications() == null || currForm.getClassifications().size() == 0) {
					protoNode.add(formNode);
				} else {
					//add formNode to csTree
					this.copyCSTree(currForm, currCSMap, treeNodeMap, formNode, protoNode, idGen);
				}
			} else {
				/** for release 3.0.1, forms without protocol is not displayed, uncomment this
				 * code to display them
      //forms do not have protocol
       DefaultMutableTreeNode noProtocolNode = (DefaultMutableTreeNode)allFormsWithNoProtocol.get(currContextId);

       if (noProtocolNode == null) {
        noProtocolNode=getWebNode("No Protocol", idGen.getNewId());
        allFormsWithNoProtocol.put(currContextId, noProtocolNode);
        treeNodeMap.clear();
       }
       if (currForm.getClassifications() == null ||
           currForm.getClassifications().size() == 0) {
         noProtocolNode.add(formNode);
       } else
       {
       this.copyCSTree(currForm, currCSMap, treeNodeMap, formNode, noProtocolNode, idGen);

       }
				 */
			}
		}

		List resultList = new ArrayList();
		resultList.add(0, allFormsWithNoProtocol);
		resultList.add(1, allFormsWithProtocol);
		return resultList;
	}
	/**
	 * @returns a map with contextid as key and value a list of template nodes
	 */
	public Map getAllContextTemplateNodes(TreeFunctions treeFunctions, TreeIdGenerator idGen) throws Exception {
		Map allTemplatesByContext = new HashMap();

		Map protoCSMap = this.getFormClassificationNodes(treeFunctions, idGen, CaDSRConstants.TEMPLATE_CS_TYPE,
				CaDSRConstants.TEMPLATE_CSI_TYPE);
		Map treeNodeMap = new HashMap();

		FormDAO dao = daoFactory.getFormDAO();
		List templates = dao.getAllTemplatesOrderByContext();

		Iterator iter = templates.iterator();

		while (iter.hasNext()) {
			Form currTemplate = (Form)iter.next();

			String currContextId = currTemplate.getContext().getConteIdseq();
			Map currCSMap = (Map)protoCSMap.get(currContextId);
			DefaultMutableTreeNode tmpNode = getTemplateNode(idGen.getNewId(), currTemplate, treeFunctions);

			DefaultMutableTreeNode tmpLabelNode = (DefaultMutableTreeNode)allTemplatesByContext.get(currContextId);

			if (tmpLabelNode == null) {
				tmpLabelNode = getWebNode("Protocol Form Templates", idGen.getNewId());

				treeNodeMap.clear();
			}

			allTemplatesByContext.put(currContextId, tmpLabelNode);

			if (currTemplate.getClassifications() == null || currTemplate.getClassifications().size() == 0) {
				tmpLabelNode.add(tmpNode);
			} else {
				//template nodes need to be added to the cs tree
				copyCSTree(currTemplate, currCSMap, treeNodeMap, tmpNode, tmpLabelNode, idGen);
			}
		}

		return allTemplatesByContext;
	}

	/**
	 * @returns a map with contextid as key and value a list of template nodes
	 * CTEP nodes are further categorized by classification 'CRF_DISEASE' and 'Phase'
	 */
	public List getAllTemplateNodesForCTEP(TreeFunctions treeFunctions, TreeIdGenerator idGen,
			Context currContext) throws Exception {
		List allTemplatesForCtep = new ArrayList();

		Map disCscsiHolder = new HashMap();
		Map phaseCscsiHolder = new HashMap();

		DefaultMutableTreeNode phaseNode = getWebNode("Phase", idGen.getNewId());
		DefaultMutableTreeNode diseaseNode = getWebNode("Disease", idGen.getNewId());
		allTemplatesForCtep.add(phaseNode);
		allTemplatesForCtep.add(diseaseNode);

		FormDAO dao = daoFactory.getFormDAO();
		List templateTypes = dao.getAllTemplateTypes(currContext.getConteIdseq());
		Collection csiList = dao.getCSIForContextId(currContext.getConteIdseq());

		Map cscsiMap = new HashMap();
		List phaseCsCsiList = new ArrayList();
		List diseaseCsCsiList = new ArrayList();

		Iterator iter = csiList.iterator();

		while (iter.hasNext()) {
			CSITransferObject csiTO = (CSITransferObject)iter.next();

			cscsiMap.put(csiTO.getCsCsiIdseq(), csiTO);

			if (csiTO.getClassSchemeLongName().equals("CRF_DISEASE")) {
				diseaseCsCsiList.add(csiTO.getCsCsiIdseq());
			}

			if (csiTO.getClassSchemeLongName().equals("Phase")) {
				phaseCsCsiList.add(csiTO.getCsCsiIdseq());
			}
		}

		Collection templates = dao.getAllTemplatesForContextId(currContext.getConteIdseq());
		String currContextId = currContext.getConteIdseq();
		//Add all the csi nodes
		addAllcscsiNodes(phaseCsCsiList, cscsiMap, currContextId, phaseNode, templateTypes, phaseCscsiHolder, idGen);
		addAllcscsiNodes(diseaseCsCsiList, cscsiMap, currContextId, diseaseNode, templateTypes, disCscsiHolder, idGen);
		iter = templates.iterator();

		while (iter.hasNext()) {
			Form currTemplate = (Form)iter.next();

			Collection csColl = currTemplate.getClassifications();
			String currCsCsiIdseq = null;
			Iterator csIter = csColl.iterator();

			if (csIter.hasNext()) {
				ClassSchemeItem currCsi = (ClassSchemeItem)csIter.next();

				currCsCsiIdseq = currCsi.getCsCsiIdseq();
			}

			String currCategory = currTemplate.getFormCategory();

			//
			if (currCategory != null && !currCategory.equals("") && currCsCsiIdseq != null) {
				ClassSchemeItem currcscsi = (ClassSchemeItem)cscsiMap.get(currCsCsiIdseq);

				if (currcscsi == null)
					continue;

				if (phaseCsCsiList.contains(currCsCsiIdseq)) {
					CsCsiCategorytHolder cscsiCategoryHolder = (CsCsiCategorytHolder)phaseCscsiHolder.get(currCsCsiIdseq);

					Map categoryHolder = cscsiCategoryHolder.getCategoryHolder();
					DefaultMutableTreeNode categoryNode = (DefaultMutableTreeNode)categoryHolder.get(currCategory);
					DefaultMutableTreeNode
					templateNode = getTemplateNode(idGen.getNewId(), currTemplate, currcscsi, currContext, treeFunctions);
					categoryNode.add(templateNode);
				} else if (diseaseCsCsiList.contains(currCsCsiIdseq)) {
					CsCsiCategorytHolder cscsiCategoryHolder = (CsCsiCategorytHolder)disCscsiHolder.get(currCsCsiIdseq);

					Map categoryHolder = cscsiCategoryHolder.getCategoryHolder();
					DefaultMutableTreeNode categoryNode = (DefaultMutableTreeNode)categoryHolder.get(currCategory);
					DefaultMutableTreeNode
					templateNode = getTemplateNode(idGen.getNewId(), currTemplate, currcscsi, currContext, treeFunctions);
					categoryNode.add(templateNode);
				}
			}
		}

		return allTemplatesForCtep;
	}

	//Get Publishing Node info
	public Map getAllPublishingNode(TreeFunctions treeFunctions, TreeIdGenerator idGen,
			boolean showFormsAlphebetically) throws Exception {
		CSITransferObject publishFormCSI = null, publishTemplateCSI = null;

		DefaultMutableTreeNode publishNode = null;
		Map formPublishCSCSIMap = new HashMap();
		Map templatePublishCSCSIMap = new HashMap();
		Map publishNodeByContextMap = new HashMap();

		Map formCSMap = getFormClassificationNodes(treeFunctions, idGen, CaDSRConstants.FORM_CS_TYPE,
				CaDSRConstants.FORM_CSI_TYPE);
		Map templateCSMap = getFormClassificationNodes(treeFunctions, idGen, CaDSRConstants.TEMPLATE_CS_TYPE,
				CaDSRConstants.TEMPLATE_CSI_TYPE);


		FormDAO dao = daoFactory.getFormDAO();
		List formCSIs = dao.getAllPublishingCSCSIsForForm();

		Iterator formIter = formCSIs.iterator();

		while (formIter.hasNext()) {
			publishFormCSI = (CSITransferObject)formIter.next();

			formPublishCSCSIMap.put(publishFormCSI.getCsConteIdseq(), publishFormCSI);
		}

		List templateCSIs = dao.getAllPublishingCSCSIsForTemplate();

		Iterator templateIter = templateCSIs.iterator();

		while (templateIter.hasNext()) {
			publishTemplateCSI = (CSITransferObject)templateIter.next();

			templatePublishCSCSIMap.put(publishTemplateCSI.getCsConteIdseq(), publishTemplateCSI);
		}

		// get all form publishing context
		Collection formPublishingContexts = formPublishCSCSIMap.keySet();
		Iterator contextIter = formPublishingContexts.iterator();
		Map treeNodeMap = new HashMap();

		while (contextIter.hasNext()) {
			String currContextId = (String)contextIter.next();
			treeNodeMap.clear();

			publishFormCSI = (CSITransferObject)formPublishCSCSIMap.get(currContextId);
			publishNode = new DefaultMutableTreeNode(new WebNode(idGen.getNewId(), publishFormCSI.getClassSchemeLongName()));
			publishNodeByContextMap.put(currContextId, publishNode);

			List publishedProtocols = null;
			List publishedForms = null;

			publishedProtocols = dao.getAllProtocolsForPublishedForms(currContextId);

			publishedForms = new ArrayList();

			if (showFormsAlphebetically)
				publishedForms = dao.getAllPublishedForms(currContextId);

			if (!publishedProtocols.isEmpty() || !publishedForms.isEmpty()) {
				DefaultMutableTreeNode
				publishFormNode = new DefaultMutableTreeNode(
						new WebNode(idGen.getNewId(), publishFormCSI.getClassSchemeItemName()));

				publishNode.add(publishFormNode);

				if (!publishedForms.isEmpty() && showFormsAlphebetically) {
					DefaultMutableTreeNode
					listedAlphabetically = new DefaultMutableTreeNode(new WebNode(idGen.getNewId(), "Listed Alphabetically"));

					Iterator formsIt = publishedForms.iterator();

					while (formsIt.hasNext()) {
						Form currForm = (Form)formsIt.next();

						listedAlphabetically.add(getFormNode(idGen.getNewId(), currForm, treeFunctions, true));
					}

					publishFormNode.add(listedAlphabetically);
				}

				//starting here
				if (!publishedProtocols.isEmpty()) {
					DefaultMutableTreeNode
					listedByProtocol = new DefaultMutableTreeNode(new WebNode(idGen.getNewId(), "Listed by Protocol"));

					Iterator protocolIt = publishedProtocols.iterator();

					while (protocolIt.hasNext()) {
						Protocol currProto = (Protocol)protocolIt.next();

						// first create protocol node for each protocol
						DefaultMutableTreeNode
						currProtoNode = getProtocolNode(idGen.getNewId(), currProto, currContextId, treeFunctions);

						// then add all form nodes
						List formsForProtocol = dao.getAllPublishedFormsForProtocol(currProto.getIdseq());

						Iterator protocolFormsIt = formsForProtocol.iterator();

						while (protocolFormsIt.hasNext()) {
							Form currProtocolForm = (Form)protocolFormsIt.next();

							//TODO - tree for multiple form/protocols
							//currProtocolForm.setProtocol(currProto);

							Collection formCSes = currProtocolForm.getClassifications();

							if (formCSes == null || formCSes.size() == 0) {
								currProtoNode.add(this.getFormNode(idGen.getNewId(), currProtocolForm, treeFunctions, true));
							} else {
								//add formNode to csTree
								Iterator csIter = formCSes.iterator();
								while (csIter.hasNext()) 
								{
									ClassSchemeItem currCSI = (ClassSchemeItem) csIter.next();

									Map currCSMap = (Map)formCSMap.get(currCSI.getCsConteIdseq());
									this.copyCSTree(currProtocolForm, currCSMap, treeNodeMap, 
											getFormNode(idGen.getNewId(), currProtocolForm, treeFunctions, true),
											currProtoNode, idGen);
								}
							}

							listedByProtocol.add(currProtoNode);
						}

						publishFormNode.add(listedByProtocol);
					}
				}
			}
		}
		// get all publishing template context
		Collection templatePublishingContexts = templatePublishCSCSIMap.keySet();
		contextIter = templatePublishingContexts.iterator();

		while (contextIter.hasNext()) {
			List publishedTemplates = null;
			treeNodeMap.clear();

			String currContextId = (String)contextIter.next();
			publishTemplateCSI = (CSITransferObject)templatePublishCSCSIMap.get(currContextId);
			publishNode = (DefaultMutableTreeNode)publishNodeByContextMap.get(currContextId);

			if (publishNode == null) {
				publishNode = new DefaultMutableTreeNode(
						new WebNode(idGen.getNewId(), publishTemplateCSI.getClassSchemeLongName()));

				publishNodeByContextMap.put(currContextId, publishNode);
			}

			publishedTemplates = dao.getAllPublishedTemplates(currContextId);

			if (publishedTemplates != null && !publishedTemplates.isEmpty()) {
				DefaultMutableTreeNode
				publishTemplateNode = new DefaultMutableTreeNode(
						new WebNode(idGen.getNewId(), publishTemplateCSI.getClassSchemeItemName()));

				Iterator templateIt = publishedTemplates.iterator();

				while (templateIt.hasNext()) {
					Form currTemplate = (Form)templateIt.next();

					if (currTemplate.getClassifications() == null 
							|| currTemplate.getClassifications().size() == 0) {
						publishTemplateNode.add(getTemplateNode(idGen.getNewId(), 
								currTemplate, treeFunctions));
					} else {
						//add template node to csTree(s)
						Iterator csIter = currTemplate.getClassifications().iterator();
						while (csIter.hasNext()) 
						{
							ClassSchemeItem currCSI = (ClassSchemeItem) csIter.next();

							Map currCSMap = (Map)templateCSMap.get(currCSI.getCsConteIdseq());
							this.copyCSTree(currTemplate, currCSMap, treeNodeMap, 
									getTemplateNode(idGen.getNewId(), currTemplate, treeFunctions),
									publishTemplateNode, idGen);
						}

					}

				}

				publishNode.add(publishTemplateNode);
			}
		}


		return publishNodeByContextMap;
	}

	/**
	 * @returns a map with contextid as key and value a list of Classification nodes
	 */
	public Map getAllClassificationNodes(TreeFunctions treeFunctions, TreeIdGenerator idGen) throws Exception {
		Map csNodeByContextMap = new HashMap();

		FormDAO dao = daoFactory.getFormDAO();
		List allCscsi = dao.getCSCSIHierarchy();
		CDEBrowserParams params = CDEBrowserParams.getInstance();
		String[] regStatusArr = params.getCsTypeRegStatus().split(",");

		Map csMap = new HashMap(); //this map stores the webnode for cs given cs_idseq
		Map csiMap = new HashMap();
		Map <String, Map> regStatusMapByCsId = new HashMap();
		Map <String, Map> csiMapByRegStatus = new HashMap();
		for (int i=0; i<regStatusArr.length; i++)
			csiMapByRegStatus.put(regStatusArr[i], new HashMap());

		Iterator iter = allCscsi.iterator();

		while (iter.hasNext()) {
			ClassSchemeItem cscsi = (ClassSchemeItem)iter.next();

			String csContextId = cscsi.getCsConteIdseq();

			DefaultMutableTreeNode classifcationNode = (DefaultMutableTreeNode)csNodeByContextMap.get(csContextId);

			if (classifcationNode == null) {
				//create a classification node for this context
				classifcationNode = getWebNode("Classifications", idGen.getNewId());

				csNodeByContextMap.put(csContextId, classifcationNode);
				csiMap.clear();
				csMap.clear();
			}

			String csId = cscsi.getCsIdseq();
			// create classification scheme node if necessary
			DefaultMutableTreeNode csNode = (DefaultMutableTreeNode)csMap.get(csId);

			if (csNode == null) {
				csNode = getClassificationSchemeNode(idGen.getNewId(), cscsi, treeFunctions);

				classifcationNode.add(csNode);
				csMap.put(csId, csNode);

				if (cscsi.getClassSchemeType().equalsIgnoreCase(params.getRegStatusCsTree())){
					Map<String, DefaultMutableTreeNode>  regStatusMap = new HashMap();
					for (int i=0; i<regStatusArr.length; i++){
						DefaultMutableTreeNode regNode = getRegStatusNode(idGen.getNewId(), regStatusArr[i], 
								csContextId, csId, treeFunctions);
						csNode.add(regNode);
						regStatusMap.put(regStatusArr[i], regNode);
					}
					regStatusMapByCsId.put(csId, regStatusMap);
				}
			}

			// add csi node

			String parentId = cscsi.getParentCscsiId();
			DefaultMutableTreeNode parentNode = null;
			DefaultMutableTreeNode csiNode =null;

			if (!cscsi.getClassSchemeType().equalsIgnoreCase(params.getRegStatusCsTree())) {
				//this is a regular cs tree stucture
				csiNode = getClassificationSchemeItemNode(idGen.getNewId(), cscsi, treeFunctions);
				if (parentId != null) 
					parentNode = (DefaultMutableTreeNode)csiMap.get(parentId);
				else
					parentNode = csNode;

				parentNode.add(csiNode);
				csiMap.put(cscsi.getCsCsiIdseq(), csiNode);

			} else {//this is the CS tree with registration status
				if (parentId == null) {
					//this is the first level csi link to reg status         
					Map<String, DefaultMutableTreeNode> regStatusNodesMap = regStatusMapByCsId.get(csId);
					for (int i=0; i<regStatusArr.length; i++) {
						if (dao.hasRegisteredAC(cscsi.getCsCsiIdseq(), regStatusArr[i])) {
							csiNode = this.getRegStatusCSINode(idGen.getNewId(), 
									cscsi, regStatusArr[i], treeFunctions);
							regStatusNodesMap.get(regStatusArr[i]).add(csiNode);
							csiMapByRegStatus.get(regStatusArr[i]).put(cscsi.getCsCsiIdseq(), csiNode);
						}
					}
				} else {
					for (int i=0; i<regStatusArr.length; i++) {
						if (dao.hasRegisteredAC(cscsi.getCsCsiIdseq(), regStatusArr[i])) {
							csiNode = this.getRegStatusCSINode(idGen.getNewId(), 
									cscsi, regStatusArr[i], treeFunctions);
							((DefaultMutableTreeNode) csiMapByRegStatus.get(regStatusArr[i]).get(parentId)).add(csiNode);
							csiMapByRegStatus.get(regStatusArr[i]).put(cscsi.getCsCsiIdseq(), csiNode);
						}
					}
				}
			}


			// for CTEP disease, add core, none core sub node
			if (treeFunctions.getTreeType().equals(TreeConstants.DE_SEARCH_TREE)) {
				if (cscsi.getClassSchemeItemType().equals("DISEASE_TYPE")) {
					if (cscsi.getClassSchemePrefName().equals("DISEASE")) {
						csiNode.add(this.getDiseaseSubNode(idGen.getNewId(), cscsi, treeFunctions, "Core Data Set"));

						csiNode.add(this.getDiseaseSubNode(idGen.getNewId(), cscsi, treeFunctions, "Non-Core Data Set"));
					}
				}
			}
		}

		return csNodeByContextMap;
	}

	private DefaultMutableTreeNode getClassificationSchemeNode(String nodeId, ClassSchemeItem csi,
			TreeFunctions treeFunctions) throws Exception {
		return new DefaultMutableTreeNode(
				new WebNode(nodeId,  csi.getClassSchemeLongName(),
						"javascript:" + treeFunctions.getJsFunctionName() 
						+ "('P_PARAM_TYPE=CLASSIFICATION&P_IDSEQ="
						+ csi.getCsIdseq() + "&P_CONTE_IDSEQ=" + csi.getCsConteIdseq()
						+ treeFunctions.getExtraURLParameters() + "')",
						csi.getClassSchemeDefinition()));
	}

	private DefaultMutableTreeNode getClassificationSchemeItemNode(String nodeId, ClassSchemeItem csi,
			TreeFunctions treeFunctions) throws Exception {
		return new DefaultMutableTreeNode(
				new WebNode(nodeId,
						csi.getClassSchemeItemName(),
						"javascript:" + treeFunctions.getJsFunctionName() + "('P_PARAM_TYPE=CSI&P_IDSEQ="
						+ csi.getCsCsiIdseq() + "&P_CONTE_IDSEQ=" + csi.getCsConteIdseq()
						+ treeFunctions.getExtraURLParameters() + "')",
						csi.getCsiDescription()));
	}
	private DefaultMutableTreeNode getRegStatusCSINode(String nodeId, ClassSchemeItem csi,
			String regStatus,  TreeFunctions treeFunctions) throws Exception {
		return new DefaultMutableTreeNode(
				new WebNode(nodeId,
						csi.getClassSchemeItemName(),
						"javascript:" + treeFunctions.getJsFunctionName() + "('P_PARAM_TYPE=REGCSI&P_IDSEQ="
						+ csi.getCsCsiIdseq() + "&P_CONTE_IDSEQ=" + csi.getCsConteIdseq()
						+ "&P_REGSTATUS=" + regStatus
						+ treeFunctions.getExtraURLParameters() + "')",
						csi.getCsiDescription()));
	}

	private DefaultMutableTreeNode getContextNode(String nodeId, Context context,
			TreeFunctions treeFunctions) throws Exception {
		String currContextId = context.getConteIdseq();

		String name = context.getName();
		String desc = context.getDescription();

		DefaultMutableTreeNode
		contextNode = new DefaultMutableTreeNode(
				new WebNode(nodeId,
						name + " (" + desc + ")",
						"javascript:" + treeFunctions.getJsFunctionName()
						+ "('P_PARAM_TYPE=CONTEXT&P_IDSEQ=" + currContextId + "&P_CONTE_IDSEQ="
						+ currContextId + treeFunctions.getExtraURLParameters() + "')",
						desc + " (" + name + ")"));
		return contextNode;
	}

	private DefaultMutableTreeNode getDiseaseSubNode(String nodeId, ClassSchemeItem csi, TreeFunctions treeFunctions,
			String nodeName) throws Exception {
		int firstSpace = nodeName.indexOf(" ");

		String nodeType = nodeName.substring(0, firstSpace).toUpperCase();
		return new DefaultMutableTreeNode(
				new WebNode(nodeId,
						nodeName,
						"javascript:" + treeFunctions.getJsFunctionName() + "('P_PARAM_TYPE=" + nodeType + "&P_IDSEQ="
						+ csi.getCsiIdseq() + "&P_CONTE_IDSEQ=" + csi.getCsConteIdseq() + "&P_CS_CSI_IDSEQ="
						+ csi.getCsCsiIdseq() + "&diseaseName="
						+ URLEncoder.encode(
								csi.getClassSchemeItemName()) + "&csName=" + URLEncoder.encode(
										csi.getClassSchemeLongName())
										+ treeFunctions.getExtraURLParameters() + "')",
										nodeName));
	}

	private DefaultMutableTreeNode getFormNode(String nodeId, Form form, TreeFunctions treeFunctions,
			boolean showContextName) throws Exception {
		String formIdseq = form.getFormIdseq();

		String displayName = form.getLongName();
		String preferred_definition = form.getPreferredDefinition();
		String currContextId = form.getConteIdseq();
		String contextName = "";
		String formLongName = "";

		if (form.getLongName() != null)
			formLongName = URLEncoder.encode(form.getLongName());

		if (form.getContext() != null)
			contextName = form.getContext().getName();

		if (contextName != null)
			contextName = URLEncoder.encode(contextName);

		if (showContextName)
			displayName = displayName + " (" + contextName + ")";

		String protocolId = "";

		//TODO - tree for multiple form/protocols
		//if (form.getProtoIdseq() != null)
		// protocolId = form.getProtoIdseq();

		DefaultMutableTreeNode
		formNode = new DefaultMutableTreeNode(
				new WebNode(nodeId,
						displayName,
						"javascript:" + treeFunctions.getFormJsFunctionName() + "('P_PARAM_TYPE=CRF&P_IDSEQ="
						+ formIdseq + "&P_CONTE_IDSEQ=" + currContextId + "&P_PROTO_IDSEQ=" + protocolId
						//        + "&templateName=" + formLongName
						//        + "&contextName=" + contextName
						+ treeFunctions.getExtraURLParameters() + "')", preferred_definition));
		return formNode;
	}

	private DefaultMutableTreeNode getTemplateNode(String nodeId, Form template,
			TreeFunctions treeFunctions) throws Exception {
		String templateIdseq = template.getFormIdseq();

		String longName = template.getLongName();
		String preferred_definition = template.getPreferredDefinition();
		String contextName = template.getContext().getName();
		String currContextId = template.getContext().getConteIdseq();
		DefaultMutableTreeNode
		tmpNode = new DefaultMutableTreeNode(
				new WebNode(nodeId,
						longName,
						"javascript:" + treeFunctions.getFormJsFunctionName()
						+ "('P_PARAM_TYPE=TEMPLATE&P_IDSEQ=" + templateIdseq + "&P_CONTE_IDSEQ="
						+ currContextId                                      //context idseq
						+ "&templateName=" + URLEncoder.encode(longName)     //longname
						+ "&contextName=" + URLEncoder.encode(contextName) + // context name
						treeFunctions.getExtraURLParameters() + "')",
						preferred_definition));                                 //preffered definition
		return tmpNode;
	}

	private DefaultMutableTreeNode getRegStatusNode(String nodeId, String regStatus,
			String contextIdseq, String csIdseq, TreeFunctions treeFunctions) throws Exception {

		DefaultMutableTreeNode
		regStatusNode = new DefaultMutableTreeNode(
				new WebNode(nodeId,
						regStatus,
						"javascript:" + treeFunctions.getFormJsFunctionName()
						+ "('P_PARAM_TYPE=REGCS&P_IDSEQ=" + csIdseq + "&P_CONTE_IDSEQ="
						+ contextIdseq                                      //context idseq
						+ "&P_REGSTATUS=" + regStatus     //classification idseq
						+ treeFunctions.getExtraURLParameters() + "')",
						regStatus));                                 //registration status
		return regStatusNode;
	}

	private DefaultMutableTreeNode getTemplateNode(String nodeId, Form template, ClassSchemeItem csi, Context currContext,
			TreeFunctions treeFunctions) throws Exception {
		String templateIdseq = template.getFormIdseq();

		String longName = template.getLongName();
		String prefferedDefinition = template.getPreferredDefinition();

		DefaultMutableTreeNode
		tmpNode = new DefaultMutableTreeNode(
				new WebNode(nodeId,
						longName,
						"javascript:" + treeFunctions.getFormJsFunctionName()
						+ "('P_PARAM_TYPE=TEMPLATE&P_IDSEQ=" + templateIdseq + "&P_CONTE_IDSEQ="
						+ currContext.getConteIdseq() + "&csName="
						+ URLEncoder.encode(
								csi.getClassSchemeLongName()) + "&diseaseName=" + URLEncoder.encode(
										csi.getClassSchemeItemName())
										+ "&templateType="
										+ URLEncoder.encode(
												template.getFormCategory()) + "&templateName=" + URLEncoder.encode(
														longName) + "&contextName="
														+ URLEncoder.encode(
																currContext.getName()) + treeFunctions.getExtraURLParameters() + "')",
																prefferedDefinition));

		return tmpNode;
	}

	private DefaultMutableTreeNode getProtocolNode(String nodeId, Protocol protocol, String currContextId,
			TreeFunctions treeFunctions) throws Exception {
		String protoIdseq = protocol.getProtoIdseq();

		String longName = protocol.getLongName();
		String preferred_definition = protocol.getPreferredDefinition();

		DefaultMutableTreeNode
		protocolNode = new DefaultMutableTreeNode(
				new WebNode(nodeId,
						longName,
						"javascript:" + treeFunctions.getJsFunctionName()
						+ "('P_PARAM_TYPE=PROTOCOL&P_IDSEQ=" + protoIdseq + "&P_CONTE_IDSEQ="
						+ currContextId + "&protocolLongName=" + longName
						+ treeFunctions.getExtraURLParameters() + "')",
						preferred_definition));
		return protocolNode;
	}

	private DefaultMutableTreeNode getWebNode(String name, String id) {
		return new DefaultMutableTreeNode(new WebNode(id, name));
	}

	private Map addInitialCategoryNodes(DefaultMutableTreeNode cscsiNode, String uniqueIdPrefix, List templateTypes) {
		if (templateTypes == null)
			return new HashMap();

		Map holderMap = new HashMap(); // Map holding catagory to  catagory Node
		ListIterator it = templateTypes.listIterator();

		while (it.hasNext()) {
			String type = (String)it.next();

			DefaultMutableTreeNode node = getWebNode(type, uniqueIdPrefix + type);
			cscsiNode.add(node);
			holderMap.put(type, node);
		}

		return holderMap;
	}

	private void addAllcscsiNodes(List cscsiList,     Map cscsiMap,       String contextId, DefaultMutableTreeNode csNode,
			List templateTypes, Map cscsiholderMap, TreeIdGenerator idGen) {
		if (cscsiList == null || cscsiMap == null || csNode == null || cscsiholderMap == null)
			return;

		ListIterator it = cscsiList.listIterator();

		while (it.hasNext()) {
			String cscsiId = (String)it.next();

			ClassSchemeItem cscsi = (ClassSchemeItem)cscsiMap.get(cscsiId);
			String aUniquesId = contextId + cscsi.getCsCsiIdseq() + System.currentTimeMillis();
			DefaultMutableTreeNode node = this.getWebNode(cscsi.getClassSchemeItemName(), aUniquesId);
			csNode.add(node);
			aUniquesId = idGen.getNewId();
			Map categoryMap = addInitialCategoryNodes(node, aUniquesId, templateTypes);
			CsCsiCategorytHolder cscsiCatHolder = new CsCsiCategorytHolder();
			cscsiCatHolder.setNode(node);
			cscsiCatHolder.setCategoryHolder(categoryMap);
			cscsiholderMap.put(cscsiId, cscsiCatHolder);
		}
	}

	private Map getFormClassificationNodes(TreeFunctions treeFunctions, TreeIdGenerator idGen, String csType,
			String csiType) throws Exception {
		Map csiByContextMap = new HashMap();

		FormDAO dao = daoFactory.getFormDAO();
		List allCscsi = dao.getCSCSIHierarchyByType(csType, csiType);
		Map csMap = new HashMap(); //this map stores the webnode for cs given cs_idseq
		Map csiMap = new HashMap();

		Iterator iter = allCscsi.iterator();

		while (iter.hasNext()) {
			ClassSchemeItem cscsi = (ClassSchemeItem)iter.next();

			String csContextId = cscsi.getCsConteIdseq();
			Map currentCsiMap = (Map)csiByContextMap.get(csContextId);

			if (currentCsiMap == null) {
				//create a classification node for this context
				csiMap = new HashMap();

				csiByContextMap.put(csContextId, csiMap);
				csMap.clear();
			}

			String csId = cscsi.getCsIdseq();
			// create classification scheme node if necessary
			DefaultMutableTreeNode csNode = (DefaultMutableTreeNode)csMap.get(csId);

			if (csNode == null) {
				csNode = getClassificationSchemeNode(idGen.getNewId(), cscsi, treeFunctions);

				csMap.put(csId, csNode);
			}

			// add csi node
			DefaultMutableTreeNode csiNode = getClassificationSchemeItemNode(idGen.getNewId(), cscsi, treeFunctions);

			String parentId = cscsi.getParentCscsiId();
			DefaultMutableTreeNode parentNode = null;

			if (parentId != null)
				parentNode = (DefaultMutableTreeNode)csiMap.get(parentId);
			else
				parentNode = csNode;

			if (parentNode != null)
				parentNode.add(csiNode);

			csiMap.put(cscsi.getCsCsiIdseq(), csiNode);
		}

		return csiByContextMap;
	}
	/**
	 * This method iterate all the classifications of a form and add
	 * the corresponding branch of the cs tree to the root node
	 *
	 * @param currForm the Form that needs to be attached to the root node
	 * @param currCSMap a Map to find a tree node given a cscsi id, this tree
	 *          node is used as a master copy for each protocol to create its own
	 *          cs tree
	 * @param treeNodeMap a Map used to avoid copy the orginal node more than one
	 *        time.  For each given Webnode id, this Map returns the corresponding
	 *        copy of that node that exist in the copy
	 * @param newNode the form node to be added to the cs tree
	 * @param rootNode the tree node for the branch to attach to
	 * @param idGen the id genator used to get unique id when copy a node from
	 * original tree
	 */
	private void copyCSTree(Form currForm, Map currCSMap, Map treeNodeMap,
			DefaultMutableTreeNode newNode, DefaultMutableTreeNode rootNode, TreeIdGenerator idGen) {

		//if the cs map does not exist for any reason, simplely add the new to the root
		if (currCSMap == null)
			rootNode.add(newNode);
		else {  
			Iterator csIter = currForm.getClassifications().iterator();

			while (csIter.hasNext()) {
				String cscsiId = ((ClassSchemeItem)csIter.next()).getCsCsiIdseq();

				DefaultMutableTreeNode origTreeNode = (DefaultMutableTreeNode)currCSMap.get(cscsiId);
				WebNode origWebNode = (WebNode)origTreeNode.getUserObject();

				DefaultMutableTreeNode treeNodeCopy = (DefaultMutableTreeNode)treeNodeMap.get(origWebNode.getId());

				if (treeNodeCopy == null) {
					treeNodeCopy = new DefaultMutableTreeNode(origWebNode.copy(idGen.getNewId()));

					treeNodeMap.put(origWebNode.getId(), treeNodeCopy);
				}

				treeNodeCopy.add(newNode);
				DefaultMutableTreeNode pTreeNode = origTreeNode;
				DefaultMutableTreeNode cTreeNode = treeNodeCopy;

				//copy this branch of the cs tree all the way until one parent node is
				//found in the new tree
				while (pTreeNode.getParent() != null) {
					DefaultMutableTreeNode parentTreeNode = (DefaultMutableTreeNode)pTreeNode.getParent();

					WebNode pWebNode = (WebNode)parentTreeNode.getUserObject();
					DefaultMutableTreeNode pNodeCopy = (DefaultMutableTreeNode)treeNodeMap.get(pWebNode.getId());

					if (pNodeCopy == null) {
						pNodeCopy = new DefaultMutableTreeNode(pWebNode.copy(idGen.getNewId()));

						treeNodeMap.put(pWebNode.getId(), pNodeCopy);
						pNodeCopy.add(cTreeNode);
						pTreeNode = parentTreeNode;
						cTreeNode = pNodeCopy;
					} else {
						// when one parent node is found in the new tree, attach the copy
						pNodeCopy.add(cTreeNode);

						return ;
					}
				}

				rootNode.add(cTreeNode);
			}
		}
	}

	public void setLocator(ServiceLocator locator) {
		this.locator = locator;
	}

	public ServiceLocator getLocator() {
		return locator;
	}

	public void setDaoFactory(AbstractDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public AbstractDAOFactory getDaoFactory() {
		return daoFactory;
	}

	/**
	 * @returns a list of template nodes that
	 *  are further categorized by classification 'CRF_DISEASE' and 'Phase'
	 */
	public List<LazyActionTreeNode> getAllTemplateNodesForCTEP(String currContextIdseq) throws Exception {
		List allTemplatesForCtep = new ArrayList();

		Map<String, CsCSICatetegoryHolder> disCscsiHolder = new HashMap();
		Map<String, CsCSICatetegoryHolder> phaseCscsiHolder = new HashMap();

		LazyActionTreeNode phaseNode = new LazyActionTreeNode("Folder", "Phase", false);
		LazyActionTreeNode diseaseNode = new LazyActionTreeNode("Folder", "Disease", false);
		allTemplatesForCtep.add(phaseNode);
		allTemplatesForCtep.add(diseaseNode);

		FormDAO dao = daoFactory.getFormDAO();
		List templateTypes = dao.getAllTemplateTypes(currContextIdseq);
		Collection csiList = dao.getCSIForContextId(currContextIdseq);

		Map<String, CSITransferObject> cscsiMap = new HashMap();
		List phaseCsCsiList = new ArrayList();
		List diseaseCsCsiList = new ArrayList();
		Iterator iter = csiList.iterator();

		while (iter.hasNext()) {
			CSITransferObject csiTO = (CSITransferObject)iter.next();

			cscsiMap.put(csiTO.getCsCsiIdseq(), csiTO);

			if (csiTO.getClassSchemeLongName().equals("CRF_DISEASE")) {
				diseaseCsCsiList.add(csiTO.getCsCsiIdseq());
			}

			if (csiTO.getClassSchemeLongName().equals("Phase")) {
				phaseCsCsiList.add(csiTO.getCsCsiIdseq());
			}
		}

		Collection templates = dao.getAllTemplatesForContextId(currContextIdseq);
		//Add all the csi nodes
		addAllcscsiNodes(phaseCsCsiList, cscsiMap, currContextIdseq, phaseNode, templateTypes, phaseCscsiHolder);
		addAllcscsiNodes(diseaseCsCsiList, cscsiMap, currContextIdseq, diseaseNode, templateTypes, disCscsiHolder);
		iter = templates.iterator();

		while (iter.hasNext()) {
			Form currTemplate = (Form)iter.next();

			Collection csColl = currTemplate.getClassifications();
			String currCsCsiIdseq = null;
			Iterator csIter = csColl.iterator();

			if (csIter.hasNext()) {
				ClassSchemeItem currCsi = (ClassSchemeItem)csIter.next();

				currCsCsiIdseq = currCsi.getCsCsiIdseq();
			}

			String currCategory = currTemplate.getFormCategory();

			//
			if (currCategory != null && !currCategory.equals("") && currCsCsiIdseq != null) {
				ClassSchemeItem currcscsi = cscsiMap.get(currCsCsiIdseq);

				if (currcscsi == null)
					continue;

				if (phaseCsCsiList.contains(currCsCsiIdseq)) {
					CsCSICatetegoryHolder cscsiCategoryHolder = phaseCscsiHolder.get(currCsCsiIdseq);

					Map categoryHolder = cscsiCategoryHolder.getCategoryHolder();
					LazyActionTreeNode categoryNode = (LazyActionTreeNode)categoryHolder.get(currCategory);
					LazyActionTreeNode
					templateNode = getTemplateNode(currTemplate, currcscsi, currContextIdseq, "CTEP");
					categoryNode.addLeaf(templateNode);
				} else if (diseaseCsCsiList.contains(currCsCsiIdseq)) {
					CsCSICatetegoryHolder cscsiCategoryHolder = disCscsiHolder.get(currCsCsiIdseq);

					Map categoryHolder = cscsiCategoryHolder.getCategoryHolder();
					LazyActionTreeNode categoryNode = (LazyActionTreeNode)categoryHolder.get(currCategory);
					LazyActionTreeNode
					templateNode = getTemplateNode(currTemplate, currcscsi, currContextIdseq, "CTEP" );
					categoryNode.addLeaf(templateNode);
				}
			}
		}

		return allTemplatesForCtep;
	}

	private void addAllcscsiNodes(List cscsiList,     Map cscsiMap,       String contextId, LazyActionTreeNode csNode,
			List templateTypes, Map cscsiholderMap) {
		if (cscsiList == null || cscsiMap == null || csNode == null || cscsiholderMap == null)
			return;

		ListIterator it = cscsiList.listIterator();

		while (it.hasNext()) {
			String cscsiId = (String)it.next();

			ClassSchemeItem cscsi = (ClassSchemeItem)cscsiMap.get(cscsiId);
			LazyActionTreeNode node = new LazyActionTreeNode("Folder", 
					cscsi.getClassSchemeItemName(), false);
			csNode.addLeaf(node);
			Map categoryMap = addInitialCategoryNodes(node, templateTypes);
			CsCSICatetegoryHolder cscsiCatHolder = new CsCSICatetegoryHolder();
			cscsiCatHolder.setNode(node);
			cscsiCatHolder.setCategoryHolder(categoryMap);
			cscsiholderMap.put(cscsiId, cscsiCatHolder);
		}
	}

	private Map addInitialCategoryNodes(LazyActionTreeNode cscsiNode, List templateTypes) {
		if (templateTypes == null)
			return new HashMap();

		Map holderMap = new HashMap(); // Map holding catagory to  catagory Node
		ListIterator it = templateTypes.listIterator();

		while (it.hasNext()) {
			String type = (String)it.next();

			LazyActionTreeNode node = new LazyActionTreeNode("Folder", type, false);
			cscsiNode.addLeaf(node);
			holderMap.put(type, node);
		}

		return holderMap;
	}  

	private LazyActionTreeNode getTemplateNode( Form template, ClassSchemeItem csi,
			String contextIdseq, String contextName) throws Exception {
		String templateIdseq = template.getFormIdseq();

		String longName = template.getLongName();
		//   String prefferedDefinition = template.getPreferredDefinition();

		LazyActionTreeNode tmpNode = new LazyActionTreeNode(
				"Template", longName,
				"javascript:performFormAction('P_PARAM_TYPE=TEMPLATE&P_IDSEQ=" 
				+ templateIdseq + "&P_CONTE_IDSEQ="
				+ contextIdseq + "&csName="
				+ URLEncoder.encode(
						csi.getClassSchemeLongName()) + "&diseaseName=" + URLEncoder.encode(
								csi.getClassSchemeItemName())
								+ "&templateType="
								+ URLEncoder.encode(
										template.getFormCategory()) + "&templateName=" + URLEncoder.encode(
												longName) + "&contextName="
												+ URLEncoder.encode(contextName) 
												+ StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes&") +
												csi.getClassSchemeItemName() + ">>" + longName                                
												+    "')",
												templateIdseq, true);

		return tmpNode;
	}   
	public void addClassificationNode(LazyActionTreeNode pNode, String contextId) throws Exception {

		CDEBrowserParams params = CDEBrowserParams.getInstance();
		ClassificationSchemeDAO csDao = daoFactory.getClassificationSchemeDAO();
		Collection<ClassificationScheme> rootCS = csDao.getRootClassificationSchemes(contextId);
		for (Iterator csIter=rootCS.iterator(); csIter.hasNext();){
			ClassificationScheme cs = (ClassificationScheme) csIter.next();
			if (cs.getClassSchemeType().equalsIgnoreCase(params.getRegStatusCsTree())){
				ClassSchemeNode csNode = getClassificationSchemeNode(cs);
				pNode.addChild(csNode);
				//add registration status nodes for registration_status cs type
				String[] regStatusArr = params.getCsTypeRegStatus().split(",");
				for (int i=0; i<regStatusArr.length; i++){
					ClassSchemeRegStatusNode regNode = getRegStatusNode(regStatusArr[i], 
							contextId, cs.getCsIdseq());
//					csNode.addLeaf(regNode);
					csNode.addChild(regNode);
					// Don't expose subnodes yet.
					regNode.setLoaded(false);
					regNode.setExpanded(false);
				}
//				csNode.markChildrenLoaded();
				// Don't expose subnodes yet.
				csNode.setLoaded(false);
				csNode.setExpanded(false);
			} else if (cs.getClassSchemeType().equalsIgnoreCase(params.getCsTypeContainer()))
				pNode.addChild(this.getClassificationSchemeContainerNode(cs));
			else    
				pNode.addChild(getClassificationSchemeNode(cs));

		}
	}   

	public void loadCSContainerNodes(ClassSchemeContainerNode pNode, String csId) throws Exception {

		CDEBrowserParams params = CDEBrowserParams.getInstance();
		// first Add Has_A classification scheme from cs_recs 
		ClassificationSchemeDAO csDao = daoFactory.getClassificationSchemeDAO();
		Collection<ClassificationScheme> childrenCS 
		= csDao.getChildrenClassificationSchemes(csId);
		for (Iterator cIter=childrenCS.iterator(); cIter.hasNext(); ){
			ClassificationScheme cs = (ClassificationScheme) cIter.next();
			if (cs.getClassSchemeType().equalsIgnoreCase(params.getCsTypeContainer()))
//				pNode.addLeaf(this.getClassificationSchemeContainerNode(cs));
				pNode.addChild(this.getClassificationSchemeContainerNode(cs));
			else
//				pNode.addLeaf(getClassificationSchemeNode(cs));
				pNode.addChild(getClassificationSchemeNode(cs));
		}
	}  



	public void loadCSNodes(ClassSchemeNode pNode, String csId) throws Exception {

		// first Add Has_A classification scheme from cs_recs 
		ClassificationSchemeDAO csDao = daoFactory.getClassificationSchemeDAO();
		List allCscsi = csDao.getFirstLevelCSIByCS(csId);
		Iterator iter = allCscsi.iterator();
		while (iter.hasNext()) {
			ClassSchemeItem cscsi = (ClassSchemeItem)iter.next();
			ClassSchemeItemNode csiNode = getClassificationSchemeItemNode(cscsi);
//			pNode.addLeaf(csiNode);
			pNode.addChild(csiNode);
			if (cscsi.getClassSchemeItemType().equals("DISEASE_TYPE")) {
				if (cscsi.getClassSchemePrefName().equals("DISEASE")) {
//					csiNode.addLeaf(this.getDiseaseSubNode(cscsi, "Core Data Set"));

//					csiNode.addLeaf(this.getDiseaseSubNode(cscsi, "Non-Core Data Set"));
					csiNode.addChild(this.getDiseaseSubNode(cscsi, "Core Data Set"));

					csiNode.addChild(this.getDiseaseSubNode(cscsi, "Non-Core Data Set"));
					// Don't expose sub-nodes yet.
					csiNode.setLoaded(false);
					csiNode.setExpanded(false);
				}
			}
		}        
	}

	public void loadRegStatusCSNodes(LazyActionTreeNode pNode) throws Exception {
		String csId = pNode.getIdentifier();
		ClassificationSchemeDAO csDao = daoFactory.getClassificationSchemeDAO();
		List allCscsi = csDao.getFirstLevelCSIByCS(csId);
		Iterator iter = allCscsi.iterator();
		while (iter.hasNext()) {
			ClassSchemeItem cscsi = (ClassSchemeItem)iter.next();
			if (csDao.hasRegisteredAC(cscsi.getCsCsiIdseq(), pNode.getDescription())) {
				CSIRegStatusNode csiNode = getRegStatusCSINode(cscsi, pNode.getDescription());
//				pNode.addLeaf(csiNode);
				pNode.addChild(csiNode);
			}
		}        

	}

	public void loadCSINodes(ClassSchemeItemNode pNode) throws Exception {
		String csiId = pNode.getIdentifier();
		ClassificationSchemeDAO csDao = daoFactory.getClassificationSchemeDAO();
		List allCscsi = csDao.getChildrenCSI(csiId);
		Iterator iter = allCscsi.iterator();
		while (iter.hasNext()) {
			ClassSchemeItem cscsi = (ClassSchemeItem)iter.next();
			ClassSchemeItemNode csiNode = getClassificationSchemeItemNode(cscsi);
//			pNode.addLeaf(csiNode);
			pNode.addChild(csiNode);

		}        

	}

	public void loadCSIRegStatusNodes(CSIRegStatusNode pNode) throws Exception {
		String csiId = pNode.getIdentifier();
		ClassificationSchemeDAO csDao = daoFactory.getClassificationSchemeDAO();
		List allCscsi = csDao.getChildrenCSI(csiId);
		Iterator iter = allCscsi.iterator();
		while (iter.hasNext()) {
			ClassSchemeItem cscsi = (ClassSchemeItem)iter.next();
			CSIRegStatusNode csiNode = getRegStatusCSINode(cscsi, pNode.getToolTip());
//			pNode.addLeaf(csiNode);
			pNode.addChild(csiNode);

		}        

	}

	private LazyActionTreeNode getClassificationSchemeNode(ClassSchemeItem csi)
	throws Exception {
		String      extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");

		LazyActionTreeNode csNode = new LazyActionTreeNode("Classifications", 
				csi.getClassSchemeLongName(),  
				"javascript:performAction"  
				+ "('P_PARAM_TYPE=CLASSIFICATION&P_IDSEQ="
				+ csi.getCsIdseq() + "&P_CONTE_IDSEQ=" + csi.getCsConteIdseq()
				+  extraURLParameters + "')",
				csi.getCsiIdseq(), false);
		csNode.setToolTip(csi.getClassSchemeDefinition());
		return csNode;

	}
	private ClassSchemeNode getClassificationSchemeNode(ClassificationScheme cs)
	throws Exception {
		String      extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");

		ClassSchemeNode csNode = new ClassSchemeNode("Classifications", 
				cs.getLongName(),  
				"javascript:performAction"  
				+ "('P_PARAM_TYPE=CLASSIFICATION&P_IDSEQ="
				+ cs.getCsIdseq() + "&P_CONTE_IDSEQ=" + cs.getConteIdseq()
				+  extraURLParameters + "')",
				cs.getCsIdseq(), false);
		csNode.setToolTip(cs.getPreferredDefinition());
		return csNode;

	}

	private ClassSchemeContainerNode getClassificationSchemeContainerNode(ClassificationScheme cs)
	throws Exception {
		String      extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");

		ClassSchemeContainerNode csNode = new ClassSchemeContainerNode("Container", 
				cs.getLongName(),  
				"javascript:performAction"  
				+ "('P_PARAM_TYPE=CSCONTAINER&P_IDSEQ="
				+ cs.getCsIdseq() + "&P_CONTE_IDSEQ=" + cs.getConteIdseq()
				+  extraURLParameters + "')",
				cs.getCsIdseq(), false);
		csNode.setToolTip(cs.getPreferredDefinition());
		return csNode;

	}
	private ClassSchemeRegStatusNode getRegStatusNode(String regStatus,
			String contextIdseq, String csIdseq) throws Exception {
		String      extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");

		ClassSchemeRegStatusNode regStatusNode = new ClassSchemeRegStatusNode(
				"Registration Status", regStatus,
				"javascript:performAction" 
				+ "('P_PARAM_TYPE=REGCS&P_IDSEQ=" + csIdseq + "&P_CONTE_IDSEQ="
				+ contextIdseq                    //context idseq
				+ "&P_REGSTATUS=" + regStatus     //classification idseq
				+ extraURLParameters + "')", csIdseq,
				false);                  //registration status
		return regStatusNode;
	}

	private ClassSchemeItemNode getClassificationSchemeItemNode( 
			ClassSchemeItem csi) throws Exception {
		String      extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");
		ClassSchemeItemNode csiNode =new ClassSchemeItemNode(
				"Classification Scheme Item", csi.getClassSchemeItemName(),
				"javascript:performAction" + "('P_PARAM_TYPE=CSI&P_IDSEQ="
				+ csi.getCsCsiIdseq() + "&P_CONTE_IDSEQ=" + csi.getCsConteIdseq()
				+ extraURLParameters + "')", csi.getCsCsiIdseq(),
				false);
		csiNode.setToolTip(csi.getCsiDescription());
		return csiNode;
	}
	private CSIRegStatusNode getRegStatusCSINode(ClassSchemeItem csi,
			String regStatus) throws Exception {
		String  extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");
		CSIRegStatusNode csiRSNode = new CSIRegStatusNode(  "Classification Scheme Item",
				csi.getClassSchemeItemName(),
				"javascript:performAction('P_PARAM_TYPE=REGCSI&P_IDSEQ="
				+ csi.getCsCsiIdseq() + "&P_CONTE_IDSEQ=" + csi.getCsConteIdseq()
				+ "&P_REGSTATUS=" + regStatus
				+ extraURLParameters + "')", csi.getCsCsiIdseq(),
				false);
		csiRSNode.setToolTip(regStatus);
		return csiRSNode;

	}

	private LazyActionTreeNode getDiseaseSubNode(ClassSchemeItem csi, 
			String nodeName) throws Exception {
		int firstSpace = nodeName.indexOf(" ");
		String  extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");

		String nodeType = nodeName.substring(0, firstSpace).toUpperCase();
		return new LazyActionTreeNode(
				"Classification Scheme Item",
				nodeName,
				"javascript:performAction('P_PARAM_TYPE=" + nodeType + "&P_IDSEQ="
				+ csi.getCsiIdseq() + "&P_CONTE_IDSEQ=" + csi.getCsConteIdseq() + "&P_CS_CSI_IDSEQ="
				+ csi.getCsCsiIdseq() + "&diseaseName="
				+ URLEncoder.encode(
						csi.getClassSchemeItemName()) + "&csName=" + URLEncoder.encode(
								csi.getClassSchemeLongName())
								+ extraURLParameters + "')",
								false);
	}

	/**
	 * @returns two maps with contextid as key and value a holder object containing web node
	 * one containg the forms with no protocol and other with protocols
	 */
	public void addProtocolNodes(LazyActionTreeNode pNode, String contextIdseq) throws Exception {

		Map cSMap = null;

		Map treeNodeMap = new HashMap();

		FormDAO dao = daoFactory.getFormDAO();
		List forms = dao.getFormsOrderByProtocol(contextIdseq);
		if (forms == null || forms.size() == 0)
			return;

		Map protocolHolder = new HashMap();
		Iterator iter = forms.iterator();

		while (iter.hasNext()) {
			Form currForm = (Form)iter.next();       
			String currProtoIdSeq = null;

			if (currForm.getProtocols() != null && currForm.getProtocols().size()>0)
				currProtoIdSeq = currForm.getProtocols().get(0).getProtoIdseq();
			LazyActionTreeNode formNode = getFormNode(currForm, false);

			// add form node to protocol node
			if (currProtoIdSeq != null && !currProtoIdSeq.equals("")) {

				LazyActionTreeNode protoNode = (LazyActionTreeNode)protocolHolder.get(currProtoIdSeq);

				if (protoNode == null) {
					protoNode = getProtocolNode( currForm.getProtocols().get(0), contextIdseq);
					//pNode.addLeaf(protoNode);
					pNode.addChild(protoNode);
					protocolHolder.put(currProtoIdSeq, protoNode);
					treeNodeMap.clear();
				}

				// check and see if form need to be added to cs tree
				if (currForm.getClassifications() == null || currForm.getClassifications().size() == 0) {
					protoNode.addLeaf(formNode);
					protoNode.addChild(formNode);
					// Don't expose subnodes yet.
					protoNode.setLoaded(false);
					protoNode.setExpanded(false);
				} else {
					//add formNode to csTree
					if (cSMap == null)
						cSMap = this.getFormClassificationNodesByContext(CaDSRConstants.FORM_CS_TYPE,
								CaDSRConstants.FORM_CSI_TYPE, contextIdseq);

//					this.copyCSTree(currForm, cSMap, treeNodeMap, formNode, protoNode, idGen);
				}
			} else {
			/** for release 3.0.1, forms without protocol is not displayed, uncomment this
			 * code to display them
	        //forms do not have protocol
	        DefaultMutableTreeNode noProtocolNode = (DefaultMutableTreeNode)allFormsWithNoProtocol.get(currContextId);

	        if (noProtocolNode == null) {
	         noProtocolNode=getWebNode("No Protocol", idGen.getNewId());
	         allFormsWithNoProtocol.put(currContextId, noProtocolNode);
	         treeNodeMap.clear();
	        }
	        if (currForm.getClassifications() == null ||
	            currForm.getClassifications().size() == 0) {
	          noProtocolNode.add(formNode);
	        } else
	        {
	        this.copyCSTree(currForm, currCSMap, treeNodeMap, formNode, noProtocolNode, idGen);

	        }*/
			}
		}
	}

	private Map getFormClassificationNodesByContext(String csType, String csiType, 
			String contextId) throws Exception {

		FormDAO dao = daoFactory.getFormDAO();
		List allCscsi = dao.getCSCSIHierarchyByTypeAndContext(csType, csiType, contextId);
		Map csMap = new HashMap(); //this map stores the webnode for cs given cs_idseq
		Map csiMap = new HashMap();

		Iterator iter = allCscsi.iterator();

		while (iter.hasNext()) {
			ClassSchemeItem cscsi = (ClassSchemeItem)iter.next();

			String csId = cscsi.getCsIdseq();
			// create classification scheme node if necessary
			LazyActionTreeNode csNode = (LazyActionTreeNode)csMap.get(csId);

			if (csNode == null) {
				csNode = getClassificationSchemeNode( cscsi);

				csMap.put(csId, csNode);
			}

			// add csi node
			LazyActionTreeNode csiNode = getClassificationSchemeItemNode(cscsi);

			String parentId = cscsi.getParentCscsiId();
			LazyActionTreeNode parentNode = null;

			if (parentId != null)
				parentNode = (LazyActionTreeNode)csiMap.get(parentId);
			else
				parentNode = csNode;

			if (parentNode != null)
			{
//				parentNode.addLeaf(csiNode);
				parentNode.addChild(csiNode);
				// Don't expose subnodes yet.
				parentNode.setLoaded(false);
				parentNode.setExpanded(false);
			}

			csiMap.put(cscsi.getCsCsiIdseq(), csiNode);
		}

		return csiMap;
	}     

	private LazyActionTreeNode getFormNode(Form form, boolean showContextName) throws Exception {
		String formIdseq = form.getFormIdseq();
		String  extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");

		String displayName = form.getLongName();
		String preferred_definition = form.getPreferredDefinition();
		String currContextId = form.getConteIdseq();
		String contextName = "";
		String formLongName = "";

		if (form.getLongName() != null)
			formLongName = URLEncoder.encode(form.getLongName());

		if (form.getContext() != null)
			contextName = form.getContext().getName();

		if (contextName != null)
			contextName = URLEncoder.encode(contextName);

		if (showContextName)
			displayName = displayName + " (" + contextName + ")";

		String protocolId = "";

		//TODO - tree for multiple form/protocols
		//if (form.getProtoIdseq() != null)
		// protocolId = form.getProtoIdseq();

		LazyActionTreeNode
		formNode = new LazyActionTreeNode(
				"Form",  displayName,
				"javascript:performFormAction('P_PARAM_TYPE=CRF&P_IDSEQ="
				+ formIdseq + "&P_CONTE_IDSEQ=" + currContextId + "&P_PROTO_IDSEQ=" + protocolId
				+ extraURLParameters + "')", true);
		return formNode;
	}

	private LazyActionTreeNode getProtocolNode(Protocol protocol,
			String contextId ) throws Exception {
		String protoIdseq = protocol.getProtoIdseq();
		String  extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");

		String longName = protocol.getLongName();

		LazyActionTreeNode protocolNode = new LazyActionTreeNode(
				"Protocol", longName,
				"javascript:performAction('P_PARAM_TYPE=PROTOCOL&P_IDSEQ=" + protoIdseq + "&P_CONTE_IDSEQ="
				+ contextId + "&protocolLongName=" + longName
				+ extraURLParameters + "')",
				protoIdseq, false);
		return protocolNode;
	}   

	private ProtocolFormNode getLazyProtocolNode(Protocol protocol,
			String contextId ) throws Exception {
		String protoIdseq = protocol.getProtoIdseq();
		String  extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");

		String longName = protocol.getLongName();

		ProtocolFormNode protocolNode = new ProtocolFormNode(
				"Protocol", longName,
				"javascript:performAction('P_PARAM_TYPE=PROTOCOL&P_IDSEQ=" + protoIdseq + "&P_CONTE_IDSEQ="
				+ contextId + "&protocolLongName=" + longName
				+ extraURLParameters + "')",
				false);
		protocolNode.setIdentifier(protoIdseq);
		return protocolNode;
	}   

	public void addPublishedFormbyAlphaNode(LazyActionTreeNode pNode, String contextId) throws Exception {
		FormDAO dao = daoFactory.getFormDAO();
		List publishedForms = dao.getAllPublishedForms(contextId);
		Iterator formsIt = publishedForms.iterator();

		while (formsIt.hasNext()) {
			Form currForm = (Form)formsIt.next();

			pNode.addLeaf(getFormNode(currForm,true));
		}
	}   

	public void addPublishedFormbyProtocolNode(LazyActionTreeNode pNode, String contextId) throws Exception {
		FormDAO dao = daoFactory.getFormDAO();
		List publishedProtocols = null;

		publishedProtocols = dao.getAllProtocolsForPublishedForms(contextId);

		Iterator protocolIt = publishedProtocols.iterator();

		while (protocolIt.hasNext()) {
			Protocol currProto = (Protocol)protocolIt.next();
			pNode.addLeaf(this.getLazyProtocolNode(currProto,contextId));
		}
	}   
	public void addPublishedFormNodesByProtocol(LazyActionTreeNode pNode, String protocolId) throws Exception {
		FormDAO dao = daoFactory.getFormDAO();

		List formsForProtocol = dao.getAllPublishedFormsForProtocol(protocolId);

		Iterator formIt = formsForProtocol.iterator();

		while (formIt.hasNext()) {
			Form currForm = (Form)formIt.next();
			pNode.addLeaf(this.getFormNode(currForm,true));
		}
	}   
	public void addPublishedTemplates(LazyActionTreeNode pNode, String contextId) throws Exception {
		FormDAO dao = daoFactory.getFormDAO();

		List publishedTemplates = dao.getAllPublishedTemplates(contextId);

		Iterator templateIt = publishedTemplates.iterator();

		while (templateIt.hasNext()) {
			Form currTemplate = (Form)templateIt.next();
			pNode.addLeaf(this.getTemplateNode(currTemplate,contextId));
		}
	}   
	private LazyActionTreeNode getTemplateNode( Form template, 
			String contextIdseq) throws Exception {
		String templateIdseq = template.getFormIdseq();
		String  extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");

		String currContextId = template.getConteIdseq();
		String contextName = "";
		String templateLongName = "";

		if (template.getLongName() != null)
			templateLongName = URLEncoder.encode(template.getLongName());

		if (template.getContext() != null)
			contextName = template.getContext().getName();

		if (contextName != null)
			contextName = URLEncoder.encode(contextName);

		String displayName = templateLongName + " (" + contextName + ")";

		LazyActionTreeNode tmpNode = new LazyActionTreeNode(
				"Template", template.getLongName(),
				"javascript:performFormAction('P_PARAM_TYPE=TEMPLATE&P_IDSEQ=" 
				+ templateIdseq + "&P_CONTE_IDSEQ="
				+ template.getConteIdseq() + 
				"&templateName=" + templateLongName
				+"&contextName=" + contextName
				+ extraURLParameters + "')",         
				templateIdseq, true);

		return tmpNode;

	}


}
