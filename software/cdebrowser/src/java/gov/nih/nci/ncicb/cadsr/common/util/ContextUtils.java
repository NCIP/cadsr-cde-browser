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

package gov.nih.nci.ncicb.cadsr.common.util;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import java.util.Collection;
import java.util.Iterator;

public class ContextUtils 
{
  public ContextUtils()
  {
  }
  
  public static Context getContextByName(Collection contexts, String contextName)
  {
    if(contexts==null||contexts.isEmpty()||contextName==null)
      return null;
    Iterator colIt = contexts.iterator();
    while(colIt.hasNext())
    {
      Context context = (Context)colIt.next();
     System.out.println("Context : " + context.getName());
      if(context.getName().equalsIgnoreCase(contextName))
        return context;
    }
    return null;
  }
}