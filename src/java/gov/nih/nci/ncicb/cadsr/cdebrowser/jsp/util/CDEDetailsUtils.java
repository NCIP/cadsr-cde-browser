package gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util;
import gov.nih.nci.ncicb.cadsr.resource.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.resource.Concept;
import gov.nih.nci.ncicb.cadsr.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import java.util.Iterator;
import java.util.List;

public class CDEDetailsUtils 
{
  public CDEDetailsUtils()
  {
  }
  public static String getConceptCodes(ConceptDerivationRule rule, String evsStr, String anchorClass, String separator)
  {
    String codes = "";
    String hrefBegin1 = "<a class=\""+anchorClass+"\" TARGET=\"_blank\"  href=\""+evsStr+"code=\"";
    String hrefBegin2 = ">";
    String hrefClose = "</a>";
    if(rule!=null)
    {
      List comps = rule.getComponentConcepts();
      if(comps==null)
       return codes;
      Iterator it = comps.iterator();
      while(it.hasNext())
      {
        ComponentConcept comp = (ComponentConcept)it.next();
        Concept con = comp.getConcept();
        if(con!=null)
        {
           String code = con.getPreferredName();
           String str = hrefBegin1+code+hrefBegin2+code+hrefClose;        
          if(codes.equals(""))
          {
            codes=codes+str;          
          }
          else
          {
            codes=codes+", "+str;  
          }
        }
      }
    }
    return codes;
  }
}