package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j;
import java.util.*;
import gov.nih.nci.ncicb.cadsr.common.resource.CDEBrowserPageContext;
import gov.nih.nci.ncicb.cadsr.common.resource.TreeParameters;

public class PageContextValueObject implements CDEBrowserPageContext,java.io.Serializable{
  private String contextName = "";
  private String cdeTemplateName = "";
  private String classSchemeName = "";
  private String classSchemeItemName = "";
  private String paramType = "";
  private String cdeTemplateType = "";
  public String pageContextDisplayString = "caDSR Contexts >> ";
  private String conteIdseq = "";
  private String protocolName = "";
  private String crfName = "";

  public PageContextValueObject() {
  }
  public PageContextValueObject(Hashtable ht) {
    paramType = (String)ht.get("ParamType");
    if (paramType.equals("CONTEXT")){
      contextName = (String)ht.get("ContextName");
      conteIdseq = (String)ht.get("ConteIdseq");
      pageContextDisplayString = pageContextDisplayString + contextName;
    }
    else if (paramType.equals("CLASSIFICATION")){
      contextName = (String)ht.get("ContextName");
      classSchemeName = (String)ht.get("ClassSchemeName");
      conteIdseq = (String)ht.get("ConteIdseq");
      pageContextDisplayString = pageContextDisplayString + contextName
                      + " >> Classifications >> " + classSchemeName;
    }
    else if (paramType.equals("CSI")){
      contextName = (String)ht.get("ContextName");
      classSchemeName = (String)ht.get("ClassSchemeName");
      classSchemeItemName = (String)ht.get("ClassSchemeItemName");
      conteIdseq = (String)ht.get("ConteIdseq");
      pageContextDisplayString = pageContextDisplayString + contextName
                      + " >> Classifications >> " + classSchemeName
                      //+ " >> Classification Scheme Items >> "
                      + " >> "
                      +classSchemeItemName;
    }
    else if (paramType.equals("TEMPLATE")){
      contextName = (String)ht.get("ContextName");
      cdeTemplateName = (String)ht.get("CRFName");
      cdeTemplateType = (String)ht.get("CRFType");
      conteIdseq = (String)ht.get("ConteIdseq");
      pageContextDisplayString = pageContextDisplayString + contextName
                      + " >> Protocol Form Templates >> " + cdeTemplateType +
                      //" >> Templates >> " 
                       " >> "
                      +cdeTemplateName;
    }
    else if (paramType.equals("CRF")){
      contextName = (String)ht.get("ContextName");
      crfName = (String)ht.get("CRFName");
      protocolName = (String)ht.get("ProtocolName");
      conteIdseq = (String)ht.get("ConteIdseq");
      if(protocolName!=null)
      {
        pageContextDisplayString = pageContextDisplayString + contextName
                      + " >> Protocol Forms >> " + protocolName +
                      //" >> Templates >> " 
                       " >> "
                      +crfName;        
      }
      else
      {
        pageContextDisplayString = pageContextDisplayString + contextName 
                      +" >> Protocol Forms >> "
                      +crfName;              
      }
    }
    else if (paramType.equals("PROTOCOL")){
      contextName = (String)ht.get("ContextName");
      protocolName = (String)ht.get("ProtocolName");
      conteIdseq = (String)ht.get("ConteIdseq");
      pageContextDisplayString = pageContextDisplayString + contextName
                      + " >> Protocol Forms >> " + protocolName;
    }
    else if (paramType.equals("PUBLISHING_PROTOCOL")){
      contextName = (String)ht.get("ContextName");
      protocolName = (String)ht.get("ProtocolName");
      conteIdseq = (String)ht.get("ConteIdseq");
      pageContextDisplayString = pageContextDisplayString + contextName
                      + " >> Protocol Forms >> " + protocolName+"(Published)";
    }     
    else if (paramType.equals("CORE")){
      contextName = (String)ht.get("ContextName");
      classSchemeName = (String)ht.get("ClassSchemeName");
      classSchemeItemName = (String)ht.get("ClassSchemeItemName");
      conteIdseq = (String)ht.get("ConteIdseq");
      pageContextDisplayString = pageContextDisplayString + contextName
                      +" >> Diseases >> "+classSchemeItemName+" >> Group Name >> CORE";
    }
    else if (paramType.equals("NON-CORE")){
      contextName = (String)ht.get("ContextName");
      classSchemeName = (String)ht.get("ClassSchemeName");
      classSchemeItemName = (String)ht.get("ClassSchemeItemName");
      conteIdseq = (String)ht.get("ConteIdseq");
      pageContextDisplayString = pageContextDisplayString + contextName
                      +" >> Diseases >> "+classSchemeItemName+" >> Group Name >> NON-CORE";
    }
    else if (paramType.equals("P_PARAM_TYPE")){
      pageContextDisplayString = "caDSR Contexts";
    }
  }

  public PageContextValueObject(TreeParameters param) {
    paramType = param.getNodeType();
    if (paramType.equals("CORE")){
      contextName = "CTEP";
      classSchemeName = param.getClassSchemeName();
      classSchemeItemName = param.getClassSchemeItemName();
      conteIdseq = param.getConteIdseq();
      pageContextDisplayString = pageContextDisplayString + contextName
            + " >> Classifications >> " + classSchemeName + " >> "
            +classSchemeItemName + " >> Core Data Set" ;
    }
    else if (paramType.equals("NON-CORE")){
      contextName = "CTEP";
      classSchemeName = param.getClassSchemeName();
      classSchemeItemName = param.getClassSchemeItemName();
      conteIdseq = param.getConteIdseq();
      pageContextDisplayString = pageContextDisplayString + contextName
            + " >> Classifications >> " + classSchemeName + " >> "
            +classSchemeItemName + " >> Non-Core Data Set" ;
    }
    else if (paramType.equals("TEMPLATE")){
      contextName = param.getContextName();
      cdeTemplateName = param.getCDETemplateName();
      conteIdseq = param.getConteIdseq();
      if (contextName.equals("CTEP")) {
        //contextName = "CTEP";
        String str = "";
        if(param.getClassSchemeName()!=null)
            str = param.getClassSchemeName()+" >> ";
        if(param.getClassSchemeItemName()!=null)
            str = str+param.getClassSchemeItemName()+" >> ";
        if(param.getTemplateGrpName()!=null)
            str = str+param.getTemplateGrpName()+" >> ";
       /**     
        cdeTemplateType = param.getTemplateGrpName();
        classSchemeItemName = param.getClassSchemeItemName();
        classSchemeName = param.getClassSchemeName();
        **/
        
        pageContextDisplayString = pageContextDisplayString + contextName
            + " >> Protocol Form Templates >> "+ str + cdeTemplateName ;

      }
      else {
        pageContextDisplayString = pageContextDisplayString + contextName
            + " >> Protocol Form Templates >> "+ cdeTemplateName ;
      }
    }

  }
  public String getContextName() {
    return contextName;
  }
  public String getCDETemplateName(){
    return cdeTemplateName;
  }
  public String getClassSchemeName() {
    return classSchemeName;
  }
  public String getClassSchemeItemName() {
    return classSchemeItemName;
  }
  public String getPageContextDisplayText() {
    return pageContextDisplayString;
  }
  public String getConteIdseq() {
    return conteIdseq;
  }
}