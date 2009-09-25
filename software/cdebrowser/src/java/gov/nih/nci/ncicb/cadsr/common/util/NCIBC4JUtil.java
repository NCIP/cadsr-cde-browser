package gov.nih.nci.ncicb.cadsr.common.util;


import gov.nih.nci.ncicb.cadsr.contexttree.CDEBrowserTree;
import java.util.Vector;
import oracle.cle.exception.CLEException;
import oracle.cle.util.CLEUtil;
import oracle.jbo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NCIBC4JUtil
{

   protected Log log =  LogFactory.getLog(NCIBC4JUtil.class.getName());
   
    public NCIBC4JUtil()
    {
    }

    public Row getRow(RowIterator rowIterator)
    {
        Row row = null;
        rowIterator.reset();
        if(rowIterator.hasNext())
            row = rowIterator.next();
        rowIterator.reset();
        return row;
    }

    public Vector getAllRows(RowIterator rowIterator)
    {
        Vector rows = new Vector(rowIterator.getRowCount());
        try
        {
            rowIterator.reset();
            for(; rowIterator.hasNext(); rows.addElement(rowIterator.next()));
            rowIterator.reset();
        }
        catch(Exception exception)
        {
            if(rowIterator != null)
                rowIterator.reset();
            rows = new Vector();
        }
        return rows;
    }

    public ViewObject cloneViewObject(ViewObject view)
        throws Exception
    {
        CLEUtil util = new CLEUtil();
        String tempName = "x" + CLEUtil.getRandomString(25);
        log.info("CLEUtil.getRandomString " + tempName);
        ViewObject clonedView = null;
        try
        {
            ApplicationModule module = view.getApplicationModule();
            String name = view.getDefFullName();
            clonedView = module.createViewObject(tempName, name);
        }
        catch(Exception e)
        {
            throw new CLEException("CLE-155", "Exception : " + e.getMessage() + " happened in BC4JHandler.cloneViewObject ", e);
        }
        return clonedView;
    }
}  
