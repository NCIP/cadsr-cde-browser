/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.common.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceBlob;

import java.io.*;

import oracle.clex.process.*;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.Service;
import oracle.cle.util.statemachine.TransitionCondition;


public class DownloadTemplate extends CreateGenericBinaryPage {
  public DownloadTemplate() {
    super();
    DEBUG = false;
  }

  protected void registerInfo() {
    super.registerInfo();

    try {
      registerParameter(
        new ProcessParameter(
          ProcessConstants.REFERENCE_BLOB_VO, "ReferenceBlobValueObject",
          "ReferenceBlob Value Object", null));
    }
    catch (ProcessInfoException pie) {
      reportException(pie, DEBUG);
    }
  }

  protected String getContentType() {
    return getMimeTypeForBlob();
  }

  protected InputStream getBinaryInputStream() {
    return getBlobContentsAsInputStream();
  }

  protected int getLength() {
    return getBlobLength();
  }

  protected String getMimeTypeForBlob() {
    ReferenceBlob rb = null;
    ProcessInfo info =
      (ProcessInfo) getInfo(ProcessConstants.REFERENCE_BLOB_VO);

    if ((info != null) && info.isReady()) {
      rb = (ReferenceBlob) info.getValue();
    }

    String strContentType = rb.getMimeType();

    return strContentType;
  }

  protected BufferedInputStream getBlobContentsAsInputStream() {
    ReferenceBlob rb = null;
    ProcessInfo info =
      (ProcessInfo) getInfo(ProcessConstants.REFERENCE_BLOB_VO);

    if ((info != null) && info.isReady()) {
      rb = (ReferenceBlob) info.getValue();
    }

    InputStream inputStream = rb.getBlobContent().getBinaryStream();
    BufferedInputStream bis = new BufferedInputStream(inputStream);

    return bis;
  }

  protected int getBlobLength() {
    ReferenceBlob rb = null;
    ProcessInfo info =
      (ProcessInfo) getInfo(ProcessConstants.REFERENCE_BLOB_VO);

    if ((info != null) && info.isReady()) {
      rb = (ReferenceBlob) info.getValue();
    }

    int length = rb.getDocSize();

    return length;
  }
}
