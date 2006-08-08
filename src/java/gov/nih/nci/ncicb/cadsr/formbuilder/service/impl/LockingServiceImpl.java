package gov.nih.nci.ncicb.cadsr.formbuilder.service.impl;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormElementLocker;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.LockingService;

import gov.nih.nci.ncicb.cadsr.formbuilder.service.LockingService;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;

import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LockingServiceImpl implements LockingService{
    
    public LockingServiceImpl() {
    }
    
    private Map<String, FormElementLocker> lockerMap;
    protected Log log = LogFactory.getLog(LockingServiceImpl.class.getName());
    
    public boolean isFormLocked(String acId, String userId) throws ServiceLocatorException{
        //System.out.println(" this is in LockFormServiceImpl isFormLocked");
        Map<String, FormElementLocker> lockerMap = getLockerMap();
        if (lockerMap==null){
            log.error("lockerMap is null");
            //System.out.println("lockerMap is null");
            throw new ServiceLocatorException("lockerMap is null at LockingServiceImpl");
        }
        
        FormElementLocker locker = lockerMap.get(acId);
        if (locker==null){
            //System.out.println("not locked");
            if (log.isDebugEnabled()){
                log.debug("this form is not locked");
            }
            return false;
        }
        
        if (locker.getNciUser().getUsername().equalsIgnoreCase(userId)){//by the same user
             if (log.isDebugEnabled()){
                log.debug("Form " + acId + " is locked by the same user " + userId);
             }
             return false;
        }    
        return true;
    }

    public boolean lockForm(String formIdSeq,NCIUser nciUser,String sessionId) throws ServiceLocatorException{
        FormElementLocker locker = new FormElementLocker(formIdSeq, nciUser, sessionId);
        
        if (getLockerMap() == null){
            log.error("lockermap is null");
            throw new ServiceLocatorException("lockermap is null");
        }
        
        FormElementLocker existinglocker = getLockerMap().get(formIdSeq);
        if (existinglocker!=null){
            String existingUserName = existinglocker.getNciUser().getUsername();
            if (existingUserName.equalsIgnoreCase(nciUser.getUsername())){
                if (log.isDebugEnabled()){
                    log.debug("This form is already locked by you");
                }    
                return true; //already locked by you.
            }else{
                log.warn("Can not lock this form. This form is locked by another user: " + existingUserName);
                return false;
            }            
        }
        
        getLockerMap().put(formIdSeq, locker);
        return true;
    }
    
    
    public void unlockForm(String formIdSeq,String userId) throws ServiceLocatorException{
        Map formOwnerMap = getLockerMap();
        if (formOwnerMap == null) {
           log.warn("Trying to unlock a form while there is no formOwnerMap"); 
           throw new ServiceLocatorException("Trying to unlock a form while there is no formOwnerMap"); 
        } 
                
        if (!formOwnerMap.containsKey(formIdSeq)){
            if (log.isDebugEnabled()) {
                log.debug("This form " + formIdSeq + " is not locked at all.");
            }
            //System.out.println("Form " + formIdSeq + " is not locked at all ");
            return;
        }
        
        //check if it is locked by the same user
        FormElementLocker locker = (FormElementLocker)formOwnerMap.get(formIdSeq);
        if (!(locker.getNciUser().getUsername().equalsIgnoreCase(userId))){
            if (log.isDebugEnabled()){
                log.debug("Form " + formIdSeq + " is not locked by user " + userId + ". You can not unlock it.");
            }
            //System.out.println("Form " + formIdSeq + " is not locked by user " + userId + ". You can not unlock it.");
            return;
        }else{
            formOwnerMap.remove(formIdSeq);
            if (log.isDebugEnabled()){
                log.debug("Form " + formIdSeq + " is unlocked.");
            }
            //System.out.println("Form " + formIdSeq + " is unlocked by user " + userId);
        }        
    }
    
    public void unlockFormByUser(String userId) throws ServiceLocatorException{
        Map formOwnerMap = getLockerMap();
        if (formOwnerMap == null) {
            log.error("lockMap is null");
            throw new ServiceLocatorException("lockermap is null");
        } 

        synchronized(formOwnerMap){            
            Iterator it = formOwnerMap.values().iterator();
            while (it.hasNext()){
                FormElementLocker locker = (FormElementLocker)it.next();
                if (locker.getNciUser().getUsername().equalsIgnoreCase(userId)){ //locked by this user
                    formOwnerMap.remove(locker.getAcIdSeq());
                    //if (log.isDebugEnabled()){
                    //    log.debug("Form idseq=" + locker.getAcIdSeq() + " is unlocked by user " + userId);
                    //}
                    //System.out.println("Form idseq=" + locker.getAcIdSeq() + " is unlocked by user " + userId);
                }
            }
        }    
        return;
    }
    
    public void unlockFormBySession(String sessionId) throws ServiceLocatorException{
        Map formOwnerMap = getLockerMap();
        if (formOwnerMap == null) {
            log.error("lockermap is null");
            throw new ServiceLocatorException(" lockermap is null");
        } 
        
        synchronized(formOwnerMap){
            Iterator it = formOwnerMap.values().iterator();
            while (it.hasNext()){
                FormElementLocker locker = (FormElementLocker)it.next();
                if (locker.getSessionId().equals(sessionId)){
                    formOwnerMap.remove(locker.getAcIdSeq());
                    //if (log.isDebugEnabled()){
                    //    log.debug("Form idseq=" + locker.getAcIdSeq() + " is unlocked by session " + sessionId);
                    //}
                    //System.out.println("Form idseq=" + locker.getAcIdSeq() + " is unlocked in session " + sessionId);
                }
            }
        } //end of synchronized   
        return;        
    }

    
    public FormElementLocker getFormLocker(String formIdSeq) throws ServiceLocatorException{
        Map<String, FormElementLocker> formOwnerMap = getLockerMap();
        if (formOwnerMap == null) {
            log.error("lockermap is null");
            throw new ServiceLocatorException("locker map is null");
        }           
        return formOwnerMap.get(formIdSeq);
    }



    public void setLockerMap(Map<String, FormElementLocker> lockerMap) {
        this.lockerMap = Collections.synchronizedMap(lockerMap);
    }

    public Map<String, FormElementLocker> getLockerMap() {
        return lockerMap;
    }
}
