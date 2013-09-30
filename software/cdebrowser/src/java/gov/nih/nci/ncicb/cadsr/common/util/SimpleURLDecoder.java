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

// Copyright (c) 2000 Oracle Corporation
package gov.nih.nci.ncicb.cadsr.common.util;

import java.sql.*;
import java.lang.*;
import java.util.*;

/**
 * A Bean class.
 * <P>
 * @author Oracle Corporation
 */
public class SimpleURLDecoder extends Object {

  /**
   * Constructor
   */
  public SimpleURLDecoder() {
  }

  public static String decode(String inDateStr) {
//"08-DEC-2000+22%3A48%3A44"
    String tmp=inDateStr.substring(0,11)+' ';
    tmp = tmp+inDateStr.substring(12,14)+':';
    tmp = tmp+inDateStr.substring(17,19)+':';
    tmp = tmp +inDateStr.substring(22,24);
    return tmp;
 }
  public static String encode(String inDateStr) {
     String tmp=inDateStr.substring(0,11)+'+';
     tmp =tmp+inDateStr.substring(12,14)+"%3A";
     tmp =tmp+inDateStr.substring(15,17)+"%3A";
     tmp= tmp+inDateStr.substring(18,20);
     return tmp;
 }

}

