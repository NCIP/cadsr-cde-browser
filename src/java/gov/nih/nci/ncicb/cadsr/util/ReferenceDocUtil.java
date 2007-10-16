package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;

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
