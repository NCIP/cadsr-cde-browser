package gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class ReferenceDocFormBean extends ActionForm
{
   private String formIdSeq;
   private String contextIdSeq;
   private String url;
   private String description;
   private String name;
   private String selectedRefDocId;
   private FormFile uploadedFile;
  
  public ReferenceDocFormBean()
  {
  }

  public String getContextIdSeq()
  {
    return contextIdSeq;
  }

  public void setContextIdSeq(String contextIdSeq)
  {
    this.contextIdSeq = contextIdSeq;
  }

  public String getFormIdSeq()
  {
    return formIdSeq;
  }

  public void setFormIdSeq(String formIdSeq)
  {
    this.formIdSeq = formIdSeq;
  }

  public FormFile getUploadedFile()
  {
    return uploadedFile;
  }

  public void setUploadedFile(FormFile uploadedFile)
  {
    this.uploadedFile = uploadedFile;
  }

  public String getSelectedRefDocId()
  {
    return selectedRefDocId;
  }

  public void setSelectedRefDocId(String selectedRefDocId)
  {
    this.selectedRefDocId = selectedRefDocId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }



}