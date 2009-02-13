package gov.nih.nci.ncicb.cadsr.common.exception;

public interface HasRootCause {
	
	/** 
	 * Return the root cause of this exception
	 * @return the root cause of this exception,
	 * or null if there was no root cause
	 */
	Throwable getRootCause();

}