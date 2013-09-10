/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.util;

import java.util.regex.Pattern;

public class AppScanValidator {
 	private static Pattern cdeIdSequencePattern = Pattern.compile("^[A-Za-z0-9_-]{36}$");
 	private static Pattern cdePublicIdPattern=Pattern.compile("^[0-9]+$");
 	private static Pattern searchParameterTypePatter=Pattern.compile("^[A-Za-z]+$");
 	
 	/**
 	 * Validate the ID sequence of a CDEElement
 	 * @param idSequenceToCheck
 	 * @return
 	 */
	public static boolean validateElementIdSequence(String idSequenceToCheck)
	{
 
	        return  validatePatternAndValue(cdeIdSequencePattern, idSequenceToCheck);        
	}
	
 	/**
 	 * Validate the search parameter type
 	 * @param parameterTypeToCheck
 	 * @return
 	 */
	public static boolean validateSearchParameterType(String parameterTypeToCheck)
	{
 
	        return  validatePatternAndValue(searchParameterTypePatter, parameterTypeToCheck);        
	}
	
	/**
	 * Validate the Public Id of a CDE Element
	 * @param publicIdToCheck
	 * @return
	 */
	public static boolean validateElementPublicId(String publicIdToCheck)
	{
		return  validatePatternAndValue(cdePublicIdPattern, publicIdToCheck);   
	}
	
	private static boolean validatePatternAndValue(Pattern checkPattern, String valueToCheck)
	{
	    try {
	        return  checkPattern.matcher(valueToCheck).matches();        
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return false;
	}
}
