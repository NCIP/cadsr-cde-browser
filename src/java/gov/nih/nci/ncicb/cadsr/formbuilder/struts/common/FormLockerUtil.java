package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormElementLocker;

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormAction;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

import java.util.Collections;
import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FormLockerUtil {
    protected static Log log = LogFactory.getLog(FormLockerUtil.class.getName());

    public FormLockerUtil() {
    }

    public static boolean isFormLocked(String formIdSeq,
                                       HttpServletRequest request) {
        Object formOwnerObject =
            request.getSession(true).getServletContext().getAttribute("testcontext");
        Map formOwnerMap = null;
        if (formOwnerObject == null) {
            return false;
        } else {
            formOwnerMap = (Map)formOwnerObject;
        }
        
        FormElementLocker locker = (FormElementLocker)formOwnerMap.get(formIdSeq);
        if (locker==null){
            return false;
        }
        
        if (request.getSession(true).getId().equals(locker.getSessionId())) {
            System.out
            .println("--------------------this form is owned by you. You can  edit it");
            return false;
        } else {
            return true;
        }
    }

    public static boolean lockForm(String formIdSeq,
                                   HttpServletRequest request) {
        ServletContext servletContext =
            request.getSession(true).getServletContext();
        Object formOwnerObject = servletContext.getAttribute("testcontext");
        Map formOwnerMap = null;
        if (formOwnerObject == null) {
            formOwnerMap = Collections.synchronizedMap(new HashMap());
        } else {
            formOwnerMap = (Map)formOwnerObject;
        }

        if (formOwnerMap.containsKey(formIdSeq)) {
            return false;
        }

        FormElementLocker locker =
            new FormElementLocker(formIdSeq, request.getRemoteUser());
        locker.setSessionId(request.getSession(true).getId());
        formOwnerMap.put(formIdSeq, locker);
        servletContext.setAttribute("testcontext", formOwnerMap);
        System.out
        .println("-------------Form idseq=" + formIdSeq + " is owned by you");
        return true;
    }
    
    
    public static boolean unlockFormBySession(HttpSession session) {
        ServletContext servletContext = session.getServletContext();
        Object formOwnerObject = servletContext.getAttribute("testcontext");
        Map formOwnerMap = null;
        if (formOwnerObject == null) {
            formOwnerMap = Collections.synchronizedMap(new HashMap());
        } else {
            formOwnerMap = (Map)formOwnerObject;
        }

        Iterator it = formOwnerMap.values().iterator();
        while (it.hasNext()){
            FormElementLocker locker = (FormElementLocker)it.next();
            if (session.getId().equals(locker.getSessionId())){
                formOwnerMap.remove(locker.getAcIdSeq());
                log.debug("Form idseq=" + locker.getAcIdSeq() + " is unlocked");
                System.out.println("Form idseq=" + locker.getAcIdSeq() + " is unlocked");
            }
        }
        return true;
    }
    

    public static void unlockForm(String formIdSeq,
                                  HttpServletRequest request) {
        ServletContext servletContext =
            request.getSession(true).getServletContext();
        Object formOwnerObject = servletContext.getAttribute("testcontext");
        Map formOwnerMap = null;
        if (formOwnerObject == null) {
           log.warn("Trying to unlock a form while there is no map in the servletContext"); 
           return;
        } else {
            formOwnerMap = (Map)formOwnerObject;
        }

        formOwnerMap.remove(formIdSeq);
        servletContext.setAttribute("testcontext", formOwnerMap);
        System.out
        .println("-------------Form idseq=" + formIdSeq + " unlocked.");
    }
}
