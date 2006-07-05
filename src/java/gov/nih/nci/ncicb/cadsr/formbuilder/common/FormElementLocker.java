package gov.nih.nci.ncicb.cadsr.formbuilder.common;

/**This class is used to keep the information of the user/session who owned this form element.
 * 
 */
public class FormElementLocker {
    private String acIdSeq;
    private String sessionId;
    private String userName;
    
    public FormElementLocker(String acIdSeq, String userName) {
        setAcIdSeq(acIdSeq);
        setUserName(userName);
    }


    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setAcIdSeq(String acIdSeq) {
        this.acIdSeq = acIdSeq;
    }

    public String getAcIdSeq() {
        return acIdSeq;
    }
}
