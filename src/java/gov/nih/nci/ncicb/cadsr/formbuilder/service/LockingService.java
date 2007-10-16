package gov.nih.nci.ncicb.cadsr.formbuilder.service;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormElementLocker;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface LockingService {
    public  boolean isFormLocked(String formIdSeq,
                                       String userId) throws ServiceLocatorException;
    public boolean lockForm(String formIdSeq,NCIUser nciUser,String sessionId) throws ServiceLocatorException;
    
    public void unlockFormByUser(String userId) throws ServiceLocatorException;
    
    public void unlockFormBySession(String sessionId) throws ServiceLocatorException;

    public void unlockForm(String formIdSeq,String userId) throws ServiceLocatorException;
                                  
    public FormElementLocker getFormLocker(String formIdSeq) throws ServiceLocatorException;

}
