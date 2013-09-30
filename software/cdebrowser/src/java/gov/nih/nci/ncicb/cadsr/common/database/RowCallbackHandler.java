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

import java.sql.ResultSet;
import java.sql.SQLException;
/** 
* Callback interface used by the queries.
* Implementations of this interface
* perform the actual work of extracting results,
* but don't need to worry about exception handling.
*/

public interface RowCallbackHandler  {
  /** 
	* Process one row of data. This method should not call
	* next() on the ResultSet, but extract the current values.
	* Exactly what the implementation chooses to do is up to it;
	* a trivial implementation might simply count rows, while another
	* implementation might build an XML document.
	* @param ResultSet
	* @throw SQLException if a SQLException is encountered getting
	* column values (that is, there's no need to catch SQLException)
	*/
	void processRow(ResultSet rs) throws SQLException; // app exception

}