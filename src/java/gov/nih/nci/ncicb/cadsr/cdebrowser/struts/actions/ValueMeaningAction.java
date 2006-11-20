package gov.nih.nci.ncicb.cadsr.cdebrowser.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.cdebrowser.DataElementSearchBean;

import gov.nih.nci.ncicb.cadsr.dto.ValueMeaningTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ConceptDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ValueDomainDAO;
import gov.nih.nci.ncicb.cadsr.resource.ValueMeaning;

import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ValueMeaningAction extends BrowserBaseDispatchAction{
    public ActionForward showValueMeaningAlternates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws IOException, ServletException {
       DynaActionForm formBean = (DynaActionForm)form;
       
       Object obj = formBean.get("id");
       if (obj==null){
           log.error("shortMeaning is not passed in");
           return mapping.findForward(FAILURE);
       }
       String shortMeaning = (String)obj;
       
       ServiceLocator locator = 
        ServiceLocatorFactory.getLocator(CaDSRConstants.CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
       AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
       ValueDomainDAO dao = daoFactory.getValueDomainDAO();
       ValueMeaning vm = dao.getValueMeaning(shortMeaning);

       request.setAttribute(FormConstants.VALUE_MEANING_OBJ, vm);
       request.setAttribute("CDEBrowser", true);

       return mapping.findForward(SUCCESS);
    }

}
