/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.util;

import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;

import java.util.ArrayList;
import java.util.List;

public class ReferenceDocUtil {
    public ReferenceDocUtil() {
    }
    
    public static List<ReferenceDocument> getReferenceDocsByType(List docs, String type)
    {
        List<ReferenceDocument>  newList = new ArrayList<ReferenceDocument>();
        for(ReferenceDocument doc: (List<ReferenceDocument>)docs) 
        {
            if(((ReferenceDocument)doc).getDocType().equalsIgnoreCase(type))
                newList.add(doc);
        }
        return newList;
    }
}
