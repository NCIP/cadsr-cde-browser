package gov.nih.nci.ncicb.webtree;

/*
 * BDWebTree.java
 * Created on 09/26/2002
 * @author  Marc Piparo SAIC
 * @version 1.0
 */
 
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.tree.DefaultMutableTreeNode;

public class WebTreeFileReader
{
   private static Log log = LogFactory.getLog(WebTreeFileReader.class.getName());
 	private	DefaultMutableTreeNode _root = null;   
  
  public static DefaultMutableTreeNode parseTabbedFile(String fileName, Class callingClass) throws Exception
  {
    // get file stream for file located in this calling classes current classpath
    try
    {
      InputStreamReader myReader = new InputStreamReader(callingClass.getResourceAsStream(fileName));
      return parseTabbedStream(myReader, fileName);
    }
    catch (Exception e)
    {
      throw (new Exception("Error reading file: "+fileName+"  - "+e.toString()));
    }              
  }
  
  public static DefaultMutableTreeNode parseTabbedFile(File dataFile) throws Exception
  {
    try
    {
      InputStreamReader myReader = new InputStreamReader(new FileInputStream(dataFile));
      return parseTabbedStream(myReader, dataFile.getPath());
    }
    catch (Exception e)
    {
      throw (e);
    }    
  
  }
      
  public static DefaultMutableTreeNode parseTabbedStream(InputStreamReader readStream, String fileName) throws Exception
  {  
  
    DefaultMutableTreeNode treeRoot = null;
  
    // Buffer the file for parsing using a BufferedReader
    BufferedReader br = new BufferedReader(readStream);

    log.info("Parsing file: "+fileName);

    try 
    {  
      int totalNodes = 0;
      int prevTabCount = 0;
      DefaultMutableTreeNode prevNode = null;
      String nodeString = null;                
      
      while ( (nodeString = br.readLine()) != null ) 
      {  
        totalNodes++;
        
        // count leading tabs to determine where to place node and get node name
        String nodeName = "undefined";
        int tabCount = 0;
        int ci = 0;
        boolean done = false;
        while ((ci < nodeString.length()) && !done)
        {
          if (nodeString.charAt(ci) == '\t')
          {
            tabCount++;
          }
          else
          {
            done = true;
            nodeName = nodeString.substring(ci);
          }
          ci++;
        }                    
        
        // build a new node
        DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode(new WebNode(String.valueOf(totalNodes), nodeName));

        // if zero tabs, this is a root - point root to this node
        if (tabCount == 0)
        {
          treeRoot = currentNode;
        }
        else if (tabCount == prevTabCount)
        { 
          // tab count has not changed, this is a sibling, add node to prev node's parent
          DefaultMutableTreeNode parent = (DefaultMutableTreeNode)prevNode.getParent();
          if (parent != null)
          {
            parent.add(currentNode);
          }
          else
          {
            throw (new Exception("Error parsing file: "+fileName+" - error line #"+totalNodes+"\n"+nodeString));
          }          
        }
        else if (tabCount > prevTabCount)
        {
          // tabs increased, this is a child of the prev node, add as child node            
          prevNode.add(currentNode);            
        }
        else if (tabCount < prevTabCount)
        {
          // tabs decreased, by how much will determine who is this nodes parent.
          int tabDiff = (prevTabCount - tabCount) + 1;
          
          // loop through parents of previous node
          DefaultMutableTreeNode parent = null;
          for (int i=0; i<tabDiff; i++)
          {
            parent = (DefaultMutableTreeNode) prevNode.getParent();
            prevNode = parent;
          }
          
          // add to parent determined
          if (parent != null)
          {
            parent.add(currentNode);
          }
          else
          {
            throw (new Exception("Error parsing file: "+fileName+" - error line #"+totalNodes+"\n"+nodeString));
          }                                                        
        }                            
      
        prevNode = currentNode;
        prevTabCount = tabCount;          
      }  

    } 
    catch (IOException e) 
    {  
     throw (new Exception("Error parsing file: "+fileName+" - "+e.toString()));       
    }     
  
    return treeRoot;  
  }
  
    
/*
  static DefaultMutableTreeNode parseXMLFile(File dataFile)
  {
  
  }
*/

  



}
