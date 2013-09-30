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
