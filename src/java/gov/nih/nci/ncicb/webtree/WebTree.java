package gov.nih.nci.ncicb.webtree;

import java.util.*;
import javax.swing.tree.*;

/**
 * WebTree is the abstact base class for implementation of a web based GUI tree navigator.  This class
 * encapsulates all functionality necessary to build web trees that are processed by the WebTreeMain.jsp. 
 * <p>
 * How to implement:<p>
 * Create a subclass extending this class implementing the getTree(Hashtable params) method.  This is
 * the only REQUIRED method to implement, others are optional to gain further functionality. You must
 * create a tree structure using the DefaultMutableTreeNode swing component consisting of WebNode objects.
 * <p>
 * Optionally you can implement the getSynonyms(String term) method to add the functionality of 
 * a synonym search executed during the tree search.  Give the search term entered by the user,
 * a Vector of additional search terms to search the tree for should be returned.
 * <p>
 * Optionally you can implement the getPostSearchMsg() method.  This method is called after a
 * search is performed, and should return a string containing information you may feel is useful
 * to communicate to the user. It is displayed at the end of the search results list.
 * <p>
 * How to execute:<p>
 * The WebTreeMain.jsp is the primary jsp that will process the tree for GUI display.  It requires one 
 * parameters at a minimum.  The 'treeClass' parameter which should point to your derived
 * class.  The jsp will then invoke the getTree(Hashtable params) you have implemented to retrieve the 
 * tree structure.  Using the 'treeParams' parameter in the jsp allows you to send your getTree method
 * parameters for your internal use.  The 'treeParams' parameter should contain a string of 
 * parameters in the format of "key1:value1;key2:value2;key3:value3" etc.. These parameters 
 * will be represented in the Hashtable that is passed into the getTree(Hashtable params) method 
 * you implement. 
 * <p>
 * Created on 09/26/2002
 * @author  Marc Piparo SAIC
 * @version 1.0
 */ 
public abstract class WebTree
{

 /**
  * Builds and returns a tree structure of defaultMutableTreeNodes using WebNode objects.
  * <p>
  * @param params    the parameters supplied to the WebTreeMain.jsp 'treeParams' parameter
  *                  using the "key1:value1;key2:value2;key3:value3" syntax.
  * @return The DefaultMutableTreeNode representing the root node of the tree.
  */     
  public abstract DefaultMutableTreeNode getTree(Hashtable params) throws Exception;


 /**
  * If implemented should return a Vector of similar terms or synonyms given the term parameter supplied. 
  * Used during the search tree process initiated in the GUI.
  * <p>
  * @param term    the search term to retrieve synonyms for.
  * @return A Vector of terms representing synonyms for the search term supplied.
  */
  public Vector getSynonyms(String term) throws Exception
  {
    return null;
  }
  
 /**
  * If implemented should build a string message which may contain HTML displayed after a search has 
  * been performed.  It is displayed at the end of the search results listing.
  * <p>
  * @return A string containing a message which may contain HTML tags.
  */
  public String getPostSearchMsg()
  {
    return null;
  }
  
}
