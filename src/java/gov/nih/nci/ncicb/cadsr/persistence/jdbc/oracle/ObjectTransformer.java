package gov.nih.nci.ncicb.cadsr.persistence.jdbc.oracle;
import gov.nih.nci.ncicb.cadsr.persistence.jdbc.oracle.OracleFormValidvalueList;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;

public class ObjectTransformer 
{
  public ObjectTransformer()
  {
  }
  
  /**
   * Converts a collection of Formvalidvalues to oracle specific valid value objects
   */
  public static OracleFormValidvalueList toOracleFormValidvalueList(List fvvlist,String parentidseq)throws Exception
  {
     ListIterator it = fvvlist.listIterator();
     FbValidvalue[] fvvarray = new FbValidvalue[fvvlist.size()];
     OracleFormValidvalueList orlist = new OracleFormValidvalueList(fvvarray);

     long  index =0;
     while(it.hasNext())
     {
       FormValidValue fvv = (FormValidValue)it.next();
       FbValidvalue fbvv = toFbValidvalue(fvv,parentidseq);
       //fvvarray[index] = fbvv;
       orlist.setElement(fbvv,index);
       index++;
     }
     return orlist;
  }

  public static FbValidvalue toFbValidvalue(FormValidValue fvv,String parentidseq) throws Exception
  {
  
    BigDecimal ver = new BigDecimal(fvv.getVersion().toString());
    String contextId = fvv.getContext().getConteIdseq();
    String prefName = null;
    String instruction = null;
    if(fvv.getInstruction()!=null)
      instruction = fvv.getInstruction().getLongName();
      FbValidvalue fbfvv = new FbValidvalue(parentidseq,fvv.getLongName(),prefName,fvv.getPreferredDefinition(),ver,contextId,fvv.getAslName(),fvv.getVpIdseq(),instruction);
    return fbfvv;
  }  
}