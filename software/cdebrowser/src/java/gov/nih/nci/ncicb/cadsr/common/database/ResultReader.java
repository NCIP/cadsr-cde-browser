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

package gov.nih.nci.ncicb.cadsr.common.database;

import java.util.List;


/**
 * Extension of RowCallbackHandler that saves the
 * accumulated results as a Collection.
 */
public interface ResultReader extends RowCallbackHandler {
	 
	/**
	 * Return all results, disconnected from the JDBC ResultSet.
	 * @return all results, disconnected from the JDBC ResultSet.
	 * Never returns null; returns the empty collection if there
	 * were no results.
	 */
	List getResults();

}
