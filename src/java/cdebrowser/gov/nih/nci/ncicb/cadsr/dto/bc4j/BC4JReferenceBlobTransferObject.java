package gov.nih.nci.ncicb.cadsr.dto.bc4j;
import gov.nih.nci.ncicb.cadsr.resource.ReferenceBlob;
import oracle.jbo.domain.BlobDomain;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.ReferenceBlobsViewRowImpl;

public class BC4JReferenceBlobTransferObject implements ReferenceBlob {
	String mimeType = "";
	String docName = "";
	BlobDomain blobContent = null;
	int docSize;

	public BC4JReferenceBlobTransferObject() {

	}

	public BC4JReferenceBlobTransferObject(ReferenceBlobsViewRowImpl rbViewRowImpl) {
		mimeType = rbViewRowImpl.getMimeType();
		docName = rbViewRowImpl.getName();
		blobContent = rbViewRowImpl.getBlobContent();
		docSize = rbViewRowImpl.getDocSize().intValue();
	}

	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimetype) {
		this.mimeType = mimetype;
	}

	public BlobDomain getBlobContent() {
		return blobContent;
	}
	public void setBlobContent(BlobDomain theBlob) {
		blobContent = theBlob;
	}

	public String getName() {
		return docName;
	}
	public void setName(String docName) {
		this.docName = docName;
	}

	public int getDocSize() {
		return docSize;
	}
}