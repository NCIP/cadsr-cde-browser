package gov.nih.nci.ncicb.cadsr.common.security.jboss;

public class LoginException extends javax.security.auth.login.LoginException {

	public LoginException() {
		System.out.println("what is the error");
	}

	public LoginException(String sMsg)
	{
		System.out.println("what is the error with msg " + sMsg);
	}
}
