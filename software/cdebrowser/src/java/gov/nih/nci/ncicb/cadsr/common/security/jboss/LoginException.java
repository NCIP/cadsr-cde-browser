/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

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
