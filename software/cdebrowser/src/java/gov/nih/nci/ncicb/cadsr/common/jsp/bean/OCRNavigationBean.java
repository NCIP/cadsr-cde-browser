package gov.nih.nci.ncicb.cadsr.common.jsp.bean;
import gov.nih.nci.cadsr.domain.ObjectClass;
import gov.nih.nci.cadsr.domain.ObjectClassRelationship;

public class OCRNavigationBean 
{
	private ObjectClass objectClass =null;
	private ObjectClassRelationship ocr = null;
	private String direction = null;
	private boolean showDirection =false;

	public OCRNavigationBean()
	{
	}


	public void setObjectClass(ObjectClass objectClass)
	{
		this.objectClass = objectClass;
	}


	public ObjectClass getObjectClass()
	{
		return objectClass;
	}


	public void setOcr(ObjectClassRelationship ocr)
	{
		this.ocr = ocr;
	}


	public ObjectClassRelationship getOcr()
	{
		return ocr;
	}


	public void setShowDirection(boolean showDirection)
	{
		this.showDirection = showDirection;
	}


	public boolean isShowDirection()
	{
		return showDirection;
	}


	public void setDirection(String direction)
	{
		this.direction = direction;
	}


	public String getDirection()
	{
		return direction;
	}
}