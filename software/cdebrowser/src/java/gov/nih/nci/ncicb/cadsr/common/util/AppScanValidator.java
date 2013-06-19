package gov.nih.nci.ncicb.cadsr.common.util;

import java.util.regex.Pattern;

public class AppScanValidator {
 	private static Pattern cdeIdSequencePattern = Pattern.compile("^[A-Za-z0-9_-]{36}$");
	public static boolean validateElementIdSequence(String idToCheck)
	{
 
	        return  validatePatternAndValue(cdeIdSequencePattern, idToCheck);        
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
