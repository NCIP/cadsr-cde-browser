/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common;

public interface CommonNavigationConstants 
{
  //struts forward names
  public static final String SUCCESS = "success";
  public static final String FAILURE = "failure";
  public static final String CANCEL = "cancel";
  public static final String LOGIN = "login";

  //Method names

  public static final String METHOD_PARAM="method";
  //Used to define the forward to use in the target action
  //When Chaining actions
  
  public static final String SEND_HOME_METHOD="sendHome";
  public static final String DEFAULT_METHOD=SEND_HOME_METHOD;  
}