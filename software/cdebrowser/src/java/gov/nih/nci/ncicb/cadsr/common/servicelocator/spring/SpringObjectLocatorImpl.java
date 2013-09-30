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

package gov.nih.nci.ncicb.cadsr.common.servicelocator.spring;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ObjectLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorException;

import org.springframework.context.ApplicationContext;


public class SpringObjectLocatorImpl implements ObjectLocator

{
	public static ApplicationContext applicationContext = null;
	public SpringObjectLocatorImpl()
	{
	}

	public Object findObject(String key)
	{
		System.out.println("findobject " + key);
		if(applicationContext==null)
			throw new ServiceLocatorException("applicationContext is null");
		return applicationContext.getBean(key);
	}

}