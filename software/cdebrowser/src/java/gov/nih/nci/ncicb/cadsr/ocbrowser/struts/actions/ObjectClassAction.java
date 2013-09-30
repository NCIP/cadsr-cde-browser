/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.ocbrowser.struts.actions;

import gov.nih.nci.cadsr.domain.AdministeredComponentClassSchemeItem;
import gov.nih.nci.cadsr.domain.Definition;
import gov.nih.nci.cadsr.domain.DefinitionClassSchemeItem;
import gov.nih.nci.cadsr.domain.Designation;
import gov.nih.nci.cadsr.domain.DesignationClassSchemeItem;
import gov.nih.nci.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.dto.AttachmentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.service.OCBrowserService;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.struts.common.OCBrowserFormConstants;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.struts.common.OCBrowserNavigationConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.Attachment;
import gov.nih.nci.ncicb.cadsr.common.util.DBUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;


public class ObjectClassAction extends OCBrowserBaseDispatchAction
implements OCBrowserFormConstants,OCBrowserNavigationConstants
{
	protected static Log log = LogFactory.getLog(ObjectClassAction.class.getName());
	public ObjectClassAction()
	{
	}

	/**
	 *
	 * @param mapping The ActionMapping used to select this instance.
	 * @param form The optional ActionForm bean for this request.
	 * @param request The HTTP Request we are processing.
	 * @param response The HTTP Response we are processing.
	 *
	 * @return
	 *
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward getObjectClass(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		DynaActionForm dynaForm = (DynaActionForm) form;
		String obIdSeq = (String) dynaForm.get(OC_IDSEQ);
		String resetCrumbs = (String) dynaForm.get(this.RESET_CRUMBS);

		String cscsiIdSeq = (String)request.getSession().getAttribute("cscsiId");

		//System.out.println("---cscsiIdSeq from Session is : "+cscsiIdSeq);
		/*if (log.isDebugEnabled()) {
    log.info("ocr for With object class " + obIdSeq);
    }*/

		try {

			OCBrowserService service = this.getApplicationServiceLocator().findOCBrowserService();
			ObjectClass objClass = service.getObjectClass(obIdSeq);
			if(!(cscsiIdSeq == null || ("").equals(cscsiIdSeq.trim()))){
				//--Filtering Classification 
				Collection acCSICollection = new ArrayList();
				//System.out.println("---Size of objClass.getAdministeredComponentClassSchemeItemCollection() : "+objClass.getAdministeredComponentClassSchemeItemCollection().size());
				for (Object o: objClass.getAdministeredComponentClassSchemeItemCollection()){
					AdministeredComponentClassSchemeItem accsi = (AdministeredComponentClassSchemeItem)o;
					if(cscsiIdSeq.equalsIgnoreCase(accsi.getClassSchemeClassSchemeItem().getId())){
						acCSICollection.add(accsi);
					}
				}
				//System.out.println("----Size of acCSICollection: "+acCSICollection.size());      
				objClass.setAdministeredComponentClassSchemeItemCollection(acCSICollection);
				//-- End of Classification Filter
				//-- Filtering Alternate Names
				Collection designationCollection = new ArrayList();    	  
				//System.out.println("---Size of objectClass.getDesignationCollection(): "+objClass.getDesignationCollection().size());
				for(Object ob: objClass.getDesignationCollection()){
					Designation desg = (Designation)ob;
					for(Object ob1: desg.getDesignationClassSchemeItemCollection()){
						DesignationClassSchemeItem desCSI = (DesignationClassSchemeItem)ob1;
						if(cscsiIdSeq.equalsIgnoreCase(desCSI.getClassSchemeClassSchemeItem().getId())){
							designationCollection.add(desg);
						}
					}
				}
				//System.out.println("----Size of designationCollection"+designationCollection.size()); 
				objClass.setDesignationCollection(designationCollection);
				//-- End of Alternate Names Filter
				//-- Filtering Alternate Definitions
				Collection definitionCollection = new ArrayList();
				//System.out.println("--Size of objectClass.getDefinitionCollection(): "+objClass.getDefinitionCollection().size());
				for (Object objDef: objClass.getDefinitionCollection()){
					Definition def = (Definition)objDef ;    		  
					for(Object obj1: def.getDefinitionClassSchemeItemCollection()){
						DefinitionClassSchemeItem defCSI = (DefinitionClassSchemeItem)obj1;
						if(cscsiIdSeq.equalsIgnoreCase(defCSI.getClassSchemeClassSchemeItem().getId())){
							definitionCollection.add(def);
						}
					}    		  
				}
				//System.out.println("--- Size of definitionCollection: "+definitionCollection.size());
				objClass.setDefinitionCollection(definitionCollection);
				//-- End Alternate Definitions
			}
			setSessionObject(request,OBJECT_CLASS,objClass,true);

			List<ObjectClass> superClasses = service.getInheritenceRelationships(objClass);
			//
			/*if(!(cscsiIdSeq == null || ("").equals(cscsiIdSeq.trim()))){
				//System.out.println("---Size of superClasses List: "+superClasses.size());
				List<ObjectClass> superClassRemoveList = new ArrayList<ObjectClass>();
				int count = 0;
				for(Object superClassesObj: superClasses){
					ObjectClass sObj = (ObjectClass)superClassesObj;
					//
					sObj = service.getObjectClass(sObj.getId());
					//					
					Collection sacCSICollection = new ArrayList();					
					if(sObj.getAdministeredComponentClassSchemeItemCollection() != null && !sObj.getAdministeredComponentClassSchemeItemCollection().isEmpty()){
						for (Object o: sObj.getAdministeredComponentClassSchemeItemCollection()){
							AdministeredComponentClassSchemeItem accsiSObj = (AdministeredComponentClassSchemeItem)o;
							if(!(cscsiIdSeq.equalsIgnoreCase(accsiSObj.getClassSchemeClassSchemeItem().getId()))){
								superClassRemoveList.add(sObj);								
							}
						}
					}    		     	  
				}
				if(superClassRemoveList != null && !superClassRemoveList.isEmpty()){					
					for (Object sCObj: superClassRemoveList){
						ObjectClass removeSCobj = (ObjectClass)sCObj;
						int scsize = superClasses.size();
						for (int i = 0; i< scsize; i++){
							if(superClasses.iterator().hasNext()){
								ObjectClass soc = (ObjectClass)superClasses.iterator().next();							
								if(soc.getId().equalsIgnoreCase(removeSCobj.getId())){
									superClasses.remove(soc);									
								}
							}
						}					
					}
				}				
				//System.out.println("----Modified Size of superClasses List: "+superClasses.size());
			}*///Filter taken off GF#18627 Object Class Browser does not display correct inheritance
			//
			setSessionObject(request,SUPER_OBJECT_CLASSES,superClasses,true);

			//Reset OCR Navigation bread crumbs if resetCrumbs is not null
			if(resetCrumbs!=null&&!resetCrumbs.equals(""))
				setSessionObject(request,OCR_NAVIGATION_BEAN,null);
		}catch (Exception e){
			e.printStackTrace();
			return mapping.findForward(FAILURE);
		}/*catch (ServiceLocatorException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on getObjectClass obid= "+obIdSeq );
      }
      return mapping.findForward(FAILURE);
    }*/    
		return mapping.findForward(SUCCESS);
	}

	/**
	 *
	 *
	 * @param mapping The ActionMapping used to select this instance.
	 * @param form The optional ActionForm bean for this request.
	 * @param request The HTTP Request we are processing.
	 * @param response The HTTP Response we are processing.
	 *
	 * @return
	 *
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward viewReferenceDocAttchment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		OutputStream out = null;

		InputStream is = null;
		out = response.getOutputStream();
		String attachmentName = request.getParameter(CaDSRConstants.REFERENCE_DOC_ATTACHMENT_NAME);
		response.addHeader("Content-Disposition", "inline;filename=" + attachmentName);
		response.addHeader("Pragma", "cache");
		response.addHeader("Cache-Control", "private");
		response.addHeader("Expires", "0");

		// first find out if the attachment is new and saved in the session

		Map attMap = (Map)getSessionObject(request, CaDSRConstants.REFDOC_ATTACHMENT_MAP);
		Attachment attachment = getAttachmentFromSession(attMap, attachmentName);

		if (attachment != null) {
			FormFile attFile = (FormFile)attMap.get(attachment);

			is = attFile.getInputStream();
			response.setContentType(attachment.getMimeType());
		} else {
			Blob theBlob = null;

			Connection conn = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			try {
				DBUtil dbUtil = new DBUtil();

				//String dsName = CDEBrowserParams.getInstance("cdebrowser").getSbrDSN();
				dbUtil.getOracleConnectionFromContainer();

				String sqlStmt = "SELECT blob_content, mime_type, doc_size from reference_blobs where name = ?";
				log.info(sqlStmt);
				conn = dbUtil.getConnection();
				ps = conn.prepareStatement(sqlStmt);
				ps.setString(1, attachmentName);
				rs = ps.executeQuery();
				boolean exists = false;

				if (rs.next()) {
					exists = true;

					String mimeType = rs.getString(2);
					//    (mimeType);

					response.setContentType(mimeType);
					//theBlob = ((OracleResultSet)rs).getBLOB(1);
					theBlob = rs.getBlob(1);
					is = theBlob.getBinaryStream();
					response.setContentLength(rs.getInt(3));
					response.setBufferSize(4*1024);

					//Writing to the OutputStream
					if (is != null) {
						byte [] buf = new byte[4 * 1024]; // 4K buffer

						int bytesRead;

						while ((bytesRead = is.read(buf)) != -1) {
							out.write(buf, 0, bytesRead);
						}
					}
					response.setStatus(HttpServletResponse.SC_OK);
				}
			} catch (Exception ex) {
				log.error("Exception Caught in ObjectClassAction.viewReferenceDocAttchment:", ex);
			} finally {
				try {
					if (is != null)
						is.close();

					if (out != null) 
						out.close();

					try{
						if(ps!=null)
							ps.close();
					}catch (Exception e){}
					try{
						if(rs!=null)
							rs.close();
					}catch (Exception e){}
					try{
						if (conn != null)
							conn.close();
					}catch(Exception e){}
					//if (db != null) db.closeDB();
				} catch (Exception ex) {
					log.error("Exception Caught in ObjectClassAction during cleaning up :", ex);
				}
			}
		}
		return null;
	}  

	private Attachment getAttachmentFromSession(Map attMap, String fileName) {
		if (attMap == null)
			return null;

		Iterator iter = attMap.keySet().iterator();
		while (iter.hasNext()) {
			Attachment attachment = (AttachmentTransferObject)iter.next();
			if (attachment.getName().equals(fileName))
				return attachment;
		}

		return null;
	}

}