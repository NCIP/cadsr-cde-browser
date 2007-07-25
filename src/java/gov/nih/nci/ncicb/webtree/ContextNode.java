package gov.nih.nci.ncicb.webtree;

import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;

import gov.nih.nci.ncicb.cadsr.resource.Context;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContextNode extends LazyActionTreeNode {
   protected Log log = LogFactory.getLog(ContextNode.class.getName());
   public ContextNode() {
   }
   public ContextNode(String type, String description, String actionURL, boolean leaf) {
           super(type, description, leaf);
           setAction(actionURL);
   }
   public ContextNode(String type, String description, String actionURL, String id, boolean leaf) {
           super(type, description, actionURL, id, leaf);
   }
   public void loadChildren() {
         try {
            ClassificationFolderNode csNode = new ClassificationFolderNode("Folder",
            "Classifications", getIdentifier(), false);
            super.addLeaf(csNode);
            
            ProtocolNode protocolFormNode = new ProtocolNode("Folder",
            "Protocol Forms", getIdentifier(), false);
            super.addLeaf(protocolFormNode);
         
         String contextName = getDescription().substring(0, 
         getDescription().indexOf("(")-1);
         
         if (Context.CTEP.equals(contextName)) {
            LazyActionTreeNode ctepPFTNode = 
            new ProtocolFormTemplateNode("Folder", "Protocol Form Templates",
            getIdentifier(), false);
            super.addLeaf(ctepPFTNode);
               
            }
         
         if (Context.CONTEXT_CABIG.equals(contextName)) {
            LazyActionTreeNode publishNode = new LazyActionTreeNode("Folder",
            "Catalogue of Published Forms", getIdentifier(), false);
            super.addLeaf(publishNode);
            
            LazyActionTreeNode protoForms = new LazyActionTreeNode("Folder", 
            "Protocol Forms", getIdentifier(), false);
            publishNode.addLeaf(protoForms);
            
            LazyActionTreeNode protoFormTemplate = 
            new PublishedProtocolFormTemplateNode("Folder", "Protocol Form Templates",
            getIdentifier(), false);
            publishNode.addLeaf(protoFormTemplate);
               
            LazyActionTreeNode formListedAlpha = 
            new PublishedFormNodeAlpha("Folder", "Listed Alphabetically",
            getIdentifier(), false);
            protoForms.addLeaf(formListedAlpha);
            LazyActionTreeNode formListedByProtocol = 
            new PublishedFormNodeByProtocol("Folder", "Listed by Protocol",
            getIdentifier(), false);
            protoForms.addLeaf(formListedByProtocol);
            }
        
         } catch (Exception e) {
         log.error("Unable to retrieve classification nodes for context " + getDescription(), e);   
      }

       isChildrenLoaded = true;
   }
   
  @Override
  public int getChildCount()  {
    if (!isChildrenLoaded) loadChildren();
    List childs = super.getChildren();
    if (childs == null) return 0;    
    else return childs.size(); 
  }
}
