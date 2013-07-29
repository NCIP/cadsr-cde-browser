/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.resource;
//import oracle.sql.*;
import oracle.jbo.domain.BlobDomain;

public interface ReferenceBlob  {
  public String getMimeType();
  public void setMimeType(String mimetype);

  public BlobDomain getBlobContent();
  public void setBlobContent(BlobDomain theBlob);

  public String getName();
  public void setName(String docName);

  public int getDocSize();
  
}