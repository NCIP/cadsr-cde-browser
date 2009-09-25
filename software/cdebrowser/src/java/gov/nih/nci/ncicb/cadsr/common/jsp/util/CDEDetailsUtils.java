package gov.nih.nci.ncicb.cadsr.common.jsp.util;
import gov.nih.nci.ncicb.cadsr.common.resource.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.common.resource.Concept;
import gov.nih.nci.ncicb.cadsr.common.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.common.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CDEDetailsUtils 
{
  public CDEDetailsUtils()
  {
  }
  public static String getConceptCodesUrl(ConceptDerivationRule rule, CDEBrowserParams params, String anchorClass, String separator)
  {
    String codes = "";
    String hrefBegin1 = "";
    String hrefBegin2 = "";
    String hrefClose = "</a>";
    if(rule!=null)
    {
      List comps = rule.getComponentConcepts();
      int compsSize = 0 ; 
      if(comps==null)
       {
       return codes;
       }
      compsSize = comps.size();
      Iterator it = comps.iterator();
      while(it.hasNext())
      {
        ComponentConcept comp = (ComponentConcept)it.next();
        Concept con = comp.getConcept();
        String codeSuffix = "";
        if(comp.getIsPrimary()&&compsSize>1)
          codeSuffix = "(Primary)";
        if(con!=null)
        {
           String code = con.getPreferredName();
           String evsStr = getEvsUrlForConcept(con,params);
           String str = "";
           if(evsStr!=null)
             {
              hrefBegin1  = "<a class=\""+anchorClass+"\" TARGET=\"_blank\"  href=\""+evsStr;
              hrefBegin2 = "\">";
              hrefClose = "</a>";
              str = hrefBegin1+code+hrefBegin2+code+codeSuffix+hrefClose; 
             }
           else
           {
             hrefBegin1 = "";
             hrefBegin2 = "";
             hrefClose = "";
             str = code;
           }
                   
          if(codes.equals(""))
          {
            codes=codes+str;          
          }
          else
          {
            codes=codes+":"+str;  
          }
        }
      }
    }
    return codes;
  }
  
  public static String getConceptCodeUrl(Concept concept, CDEBrowserParams params, String anchorClass, String separator)
  {
    String codeUrl = "";
    String hrefBegin1 = "";
    String hrefBegin2 = "";
    String hrefClose = "";

        if(concept!=null)
        {
           String code = concept.getPreferredName();
           String evsStr = getEvsUrlForConcept(concept,params);
           if(evsStr!=null)
             {
              hrefBegin1  = "<a class=\""+anchorClass+"\" TARGET=\"_blank\"  href=\""+evsStr;
              hrefBegin2 = "\">";
              hrefClose = "</a>";
              codeUrl = hrefBegin1+code+hrefBegin2+code+hrefClose; 
             }
           else
           {
             hrefBegin1 = "";
             hrefBegin2 = "";
             hrefClose = "";
             codeUrl = code;
           }
                   
        }

    return codeUrl;
  }  
  public static String getEvsUrlForConcept(Concept concept, CDEBrowserParams params)
  {
    String evsSource = concept.getEvsSource();
    Map urlMap = params.getEvsUrlMap();
    return (String)urlMap.get(evsSource);
  }
}