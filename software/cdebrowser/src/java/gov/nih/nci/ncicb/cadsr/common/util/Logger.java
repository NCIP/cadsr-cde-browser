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

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Hashtable;

public class Logger{
  private static Logger instance = null;
  private String filename;

  private Logger(String filename){
    this.filename = filename;
  }

  public void message(String record){
    String dateString = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    try{
      FileOutputStream newFos = new FileOutputStream(filename, true);
      DataOutputStream newDos = new DataOutputStream(newFos);
      newDos.writeBytes("**" + dateString + "**:" + record + "\n");
      newDos.close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }

  public static synchronized Logger getInstance(String filename) {
    if (instance == null) {
      instance = new Logger(filename);
    }
    return instance;
  }

}