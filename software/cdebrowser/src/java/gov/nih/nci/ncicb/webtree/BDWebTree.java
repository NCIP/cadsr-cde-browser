package gov.nih.nci.ncicb.webtree;

/*
 * BDWebTree.java
 * Created on 09/26/2002
 * @author  Marc Piparo SAIC
 * @version 1.0
 */
 
import java.io.*;
import java.util.*;
import javax.swing.tree.*;
import javax.servlet.http.*;

public class BDWebTree
{
 	private	DefaultMutableTreeNode _root = null; 
  private DefaultMutableTreeNode _displayRoot = null;
  private WebTree webTree = null;
  private Hashtable paramsHT = null;
  private Vector _synonymsVec = null;
    
  
  public BDWebTree(String treeClassName, String treeClassParams) throws Exception
  {
    // build the root tree using the class name specified
    try
    {
      webTree = (WebTree) (Class.forName(treeClassName).newInstance());      
      paramsHT = parseParameters(treeClassParams);  
      _root = webTree.getTree(paramsHT);                        
    }
    catch (ClassNotFoundException e)
    {
      System.err.println("Invalid Class Name: "+treeClassName+" - "+e.toString());    
      throw(new Exception("invalid class name: "+treeClassName));    
    }                  
    catch (Exception e)
    {
      System.err.println("Error getting instance of Class Name: "+treeClassName+" - "+e.toString());   
      throw(new Exception("Error getting instance of Class Name: "+treeClassName+"   Error Message: "+e.toString()));   
    }
    
  }
  
  private Hashtable parseParameters(String params) throws Exception
  {
    // parses a string of parameters defined with the following syntax:
    // name1:value1;name2:value2;
  
    Hashtable results = new Hashtable();
  
    if (params != null && !params.equals("null"))
    {
      StringTokenizer st = new StringTokenizer(params, ";");
      while (st.hasMoreTokens()) 
      {
        String pair = st.nextToken();
        StringTokenizer stPair = new StringTokenizer(pair, ":");
        if (stPair.countTokens() == 2)
        {
          String name = stPair.nextToken();
          String value = stPair.nextToken();
          results.put(name, value);  
        }
        else
        {
          System.err.println("invalid 'name=value' pair parameter");
          throw(new Exception("invalid 'name=value' pair parameter"));
        }      
      }         
    }  
    return results;      
  }
  
  public DefaultMutableTreeNode getDisplayTree(String treeAction, String targetId, HttpSession userSession, String treeDirective) throws Exception 
  {    
    //
    // Build Initial Display Tree
    //    
    if (treeAction == null)
    {
      // no tree action, assume new tree to build, put root node in display tree and expand on it      
                
      DefaultMutableTreeNode rootNode = _root;
      if (rootNode != null)
      {            
        if (rootNode.getChildCount() > 0)
        {        
          // find first non null node object
          while (rootNode.getUserObject() == null && rootNode.getNextNode() != null)
          {
            rootNode = rootNode.getNextNode();
          }
          if (rootNode.getUserObject() == null) throw (new Exception("root tree contains all null objects!"));
                    
          // construct display tree 
          WebNode myWebNode = (WebNode) rootNode.getUserObject();
          if (!rootNode.isLeaf()) myWebNode.setHasChildren(true);
          _displayRoot = new DefaultMutableTreeNode((WebNode)rootNode.getUserObject());

          // default behavior is for tree to expand its root node, however this behavior
          // can be over-riden through the declaration of a treeDirective.
          targetId = ((WebNode)rootNode.getUserObject()).getId();                       
          treeAction = "expand";

          // check for special tree directive
          if (treeDirective != null && !treeDirective.equals("null"))
          {
            // a tree directive exists, process
            if (processDirective(treeDirective))
            {      
              // tree directive successully processed, over-ride default tree action
              treeAction = "treeDirective Override";
            }
          }          
        }
        else
        {
          System.err.println("Error: Root Tree is Empty!");
          throw(new Exception("Root Tree is Empty"));
        }
      }
      else
      {
        System.err.println("Error: Root Tree is Null!");
        throw(new Exception("Root Tree is Null"));
      }      
    }
    
    //
    // Process Tree Actions
    //        
    if (treeAction.equals("expand"))
    {
      expandTree(targetId);
    }
    else if (treeAction.equals("collapse"))
    {
      collapseTree(targetId);
    }   
    else if (treeAction.equals("highlight"))
    {
      expandToNode(targetId);
    }                             
    
    return _displayRoot;    
  }

  private boolean processDirective(String directive)
  {
    boolean success = false;
    // parse directive instructions
    try 
    {
      Hashtable directivesHT = parseParameters(directive);
      
      if (directivesHT.containsKey("openToName"))
      {
        String value = (String) directivesHT.get("openToName");
        success = expandToNodeByName(value, true);
      }
    }
    catch (Exception e)
    {
      System.err.println("Error parsing tree directive string!");
    }      
    return success;
  }

  private void expandTree(String targetId)
  {
    // display childen of node specified by targetId

    // search root and root trees for targetId, 
    // and copy 1st level children from root to displayRoot.
    Enumeration rootTreeEnum = _root.preorderEnumeration();
    while (rootTreeEnum.hasMoreElements())
    {
      DefaultMutableTreeNode myRootNode = (DefaultMutableTreeNode) rootTreeEnum.nextElement();
      WebNode myRootObj = (WebNode) myRootNode.getUserObject();
                 
      if (myRootObj != null)
      {
        String myRootId = myRootObj.getId();
        
        // find in root tree
        if (myRootId.equals(targetId))
        {        
          // now find node in display tree
          Enumeration displayTreeEnum = _displayRoot.preorderEnumeration();
          while (displayTreeEnum.hasMoreElements())
          {
            DefaultMutableTreeNode myDisplayNode = (DefaultMutableTreeNode) displayTreeEnum.nextElement();
            WebNode myDisplayObj = (WebNode) myDisplayNode.getUserObject();
            if (myDisplayObj != null)
            {              
              String myDisplayId = myDisplayObj.getId();
              if (myDisplayId.equals(targetId))
              {
                // get children of this node from the root tree
                Enumeration rootChildrenEnum = myRootNode.children();
                while (rootChildrenEnum.hasMoreElements())
                {
                  DefaultMutableTreeNode myChildNode = (DefaultMutableTreeNode) rootChildrenEnum.nextElement();
                  WebNode myChildObj = (WebNode) myChildNode.getUserObject();
                  if (myChildObj != null)
                  { 
                    // add child node webNode in the to display tree
                    if (!myChildNode.isLeaf()) myChildObj.setHasChildren(true);                    
                    myDisplayNode.add ( new DefaultMutableTreeNode(myChildObj) );                    
                  }
                } // end children while loop                                                                    
              } // end match root targetId in display tree                       
            } // end null check
          } // end display tree while loop          
                 
        }  // end root targetId match in root tree
      } // end obj null check
    } // end root tree while loop                                     
  }

  private void collapseTree(String targetId)
  {
    // collpase children of node specified by targetId
  
    // search display tree for node id and remove all children      
    Enumeration displayTreeEnum = _displayRoot.preorderEnumeration();
    while (displayTreeEnum.hasMoreElements())
    {
      DefaultMutableTreeNode myDisplayNode = (DefaultMutableTreeNode) displayTreeEnum.nextElement();
      WebNode myDisplayObj = (WebNode) myDisplayNode.getUserObject();
      if (myDisplayObj != null)
      {              
        String myDisplayId = myDisplayObj.getId();
        if (myDisplayId.equals(targetId))
        {
          myDisplayNode.removeAllChildren();           
        }
      }
    }  
  }
      
  private void expandToNode(String targetId)
  {
    // (used for search hi-lighting)
    
    // expands tree down to node specified by targetId 
  
    // search root tree for node id
    Enumeration rootTreeEnum = _root.preorderEnumeration();
    while (rootTreeEnum.hasMoreElements())
    {
      DefaultMutableTreeNode myRootNode = (DefaultMutableTreeNode) rootTreeEnum.nextElement();
      WebNode myRootObj = (WebNode) myRootNode.getUserObject();
      if (myRootObj != null)
      {
        String myRootId = myRootObj.getId();
        if (myRootId.equals(targetId))
        {        
          // node found, get path to node, search displayRoot for each node in path and expand up to WebNode Id
          TreeNode myPath[] = myRootNode.getPath();
          for (int node=0; node<myPath.length; node++)
          {
            DefaultMutableTreeNode testNode = (DefaultMutableTreeNode) myPath[node];    
            WebNode testRootObj = (WebNode) testNode.getUserObject();
            if (testRootObj != null)
            {
              String testRootId = testRootObj.getId();
              // check if this node is already in the display tree        
              boolean foundNode = false;
              Enumeration displayTreeEnum = _displayRoot.preorderEnumeration();
              while (displayTreeEnum.hasMoreElements())
              {
                DefaultMutableTreeNode myDisplayNode = (DefaultMutableTreeNode) displayTreeEnum.nextElement();
                WebNode myDisplayObj = (WebNode) myDisplayNode.getUserObject();
                if (myDisplayObj != null)
                {              
                  String myDisplayId = myDisplayObj.getId();
                  if (myDisplayId.equals(testRootId))
                  {
                    foundNode = true;
                  }
                }
              }
              
              // check if node was not found
              if (!foundNode)
              {
                //node not found in display tree, expand its parent
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) testNode.getParent();
                if (parentNode != null)
                {
                  WebNode parentRootObj = (WebNode) parentNode.getUserObject();
                  if (parentRootObj != null)
                  {
                    expandTree(parentRootObj.getId());
                  }                  
                }
              }
              
            }
                    
          } // end for loop for parents
        }
      }
    } // end while loop for search root tree              
  }
  
  private boolean expandToNodeByName(String searchName, boolean includingNode)
  {
    // why do we need this strange directive? well its used to open to neoplasm level for evs diagnosis
    // trees, but maybe someone else will find it useful.
    
    // expands tree down to node with first occurence of searchName 
    // includingNode means to expand the target node as well
  
    // search root tree for searchName
    boolean success = false;
    Enumeration rootTreeEnum = _root.preorderEnumeration();
    boolean done = false;
    while (rootTreeEnum.hasMoreElements() && !done)
    {
      DefaultMutableTreeNode myNode = (DefaultMutableTreeNode) rootTreeEnum.nextElement();
      WebNode myWebNode = (WebNode) myNode.getUserObject();
      if (myWebNode != null)
      {
        // fragment search
        String myName = myWebNode.getName();
        String myId   = myWebNode.getId()
        ;
        if (myName.toLowerCase().indexOf(searchName.toLowerCase()) > -1)
        {        
          success = true;
          // term found, get path to node, search displayRoot for each node in path and expand up to WebNode id
          TreeNode myPath[] = myNode.getPath();
          for (int node=0; node<myPath.length; node++)
          {
            DefaultMutableTreeNode testNode = (DefaultMutableTreeNode) myPath[node];    
            WebNode testWebNode = (WebNode) testNode.getUserObject();
            if (testWebNode != null)
            {
              String testId = testWebNode.getId();
              // check if this node is already in the display tree        
              boolean foundNode = false;
              Enumeration displayTreeEnum = _displayRoot.preorderEnumeration();
              while (displayTreeEnum.hasMoreElements())
              {
                DefaultMutableTreeNode myDisplayNode = (DefaultMutableTreeNode) displayTreeEnum.nextElement();
                WebNode myDisplayWebNode = (WebNode) myDisplayNode.getUserObject();
                if (myDisplayWebNode != null)
                {              
                  String myDisplayId = myDisplayWebNode.getId();
                  if (myDisplayId.equals(testId))
                  {
                    foundNode = true;
                  }
                }
              }
              
              // check if node was not found
              if (!foundNode)
              {
                //node not found in display tree, expand its parent
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) testNode.getParent();
                WebNode parentWebNode = (WebNode) parentNode.getUserObject();
                if (parentWebNode != null)
                {
                  expandTree(parentWebNode.getId());                    
                }                  
              }
              
            }
                    
          } // end for loop for parents
          
          // expand node itself
          if (includingNode)
          {
            expandTree(myId);
            done = true;
          }                    
        }
      }
    } // end while loop for search root tree              
    return success;
  }
  
  
  public Vector searchTree(String searchTerm, boolean synonyms, boolean matchWholeWords)
  {
    Vector searchResults = null;  
      
    Vector searchTermsVec = new Vector();
    searchTermsVec.addElement(searchTerm);

    if (synonyms && webTree != null)
    {
      // get alternate synonym search terms
      try 
      {
        _synonymsVec = webTree.getSynonyms(searchTerm);

        // add synonyms to the searchTermsVec
        if (_synonymsVec != null)
        {
          Enumeration synonymsEnum = _synonymsVec.elements();
          while (synonymsEnum.hasMoreElements())
          {
            searchTermsVec.add((String) synonymsEnum.nextElement());        
          }      
        }
      }
      catch (Exception e)
      {
        System.err.println("Error in getSynonyms - "+e.toString());
      }
    }
    
    // search root tree for search term
    if (_root != null)
    {
      searchResults = new Vector();
      
      Enumeration rootTreeEnum = _root.preorderEnumeration();
      while (rootTreeEnum.hasMoreElements())
      {
        DefaultMutableTreeNode myRootNode = (DefaultMutableTreeNode) rootTreeEnum.nextElement();
        WebNode myWebNode = (WebNode) myRootNode.getUserObject();
        
        if (myWebNode != null)
        {
          String myName = myWebNode.getName().toLowerCase();
          String myId   = myWebNode.getId();
                            
          // loop through search terms vector
          Enumeration searchTermsEnum = searchTermsVec.elements();
          while (searchTermsEnum.hasMoreElements())
          {          
            String mySearchTerm = ((String) searchTermsEnum.nextElement()).toLowerCase();          
            if (matchWholeWords)
            {
              if ((myName.indexOf(" "+mySearchTerm+" ") > -1) ||
                  (myName.endsWith(" "+mySearchTerm)) ||
                  (myName.startsWith(mySearchTerm+" ")) ||
                  (myName.equals(mySearchTerm)))
              {
                // match found, add to results if not already in there
                if (!searchResults.contains(myWebNode))
                {                    
                  searchResults.addElement(myWebNode);                  
                }              
              }            
            }
            else // non-match whole words
            {
              if (myName.indexOf(mySearchTerm) > -1)
              {        
                // match found, add to results if not already in there
                if (!searchResults.contains(myWebNode))
                {                    
                  searchResults.addElement(myWebNode);                  
                }                   }
            }                                    
          }
        }
      }
    }
        
    return searchResults;              
  }

  public Vector getSynonyms()
  {
    return _synonymsVec;
  }
  
  public String getPostSearchMsg()
  {
    return webTree.getPostSearchMsg();
  }

}
