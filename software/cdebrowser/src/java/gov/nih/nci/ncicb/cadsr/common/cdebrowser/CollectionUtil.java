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

package gov.nih.nci.ncicb.cadsr.common.cdebrowser;
import gov.nih.nci.ncicb.cadsr.common.dto.bc4j.BC4JDataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.util.DTOTransformer;
import java.util.Iterator;
import java.util.List;

/**
 * Collection and cdebrowser related utilities
 */
public class CollectionUtil 
{
  public CollectionUtil()
  {
  }
  public static DataElement locateDataElement(
    List results,
    String deId) {
    Iterator it = results.iterator();
    DataElement de = null;

    while (it.hasNext()) {
      de = (DataElement) it.next();

      if (de.getDeIdseq().equals(deId)) {
        return DTOTransformer.toDataElement((BC4JDataElementTransferObject) de);
      }
    }

    return de;
  }  
}