package gov.nih.nci.ncicb.webtree;
 
import java.io.*;
import java.util.*;
import javax.swing.tree.*;

/**
 * WebNode is the object class used to build user objects used for DefaultMutableTree objects constructed
 * by the WebTree class.  
 * <p>
 * It contains various attributes used by the WebTreeMain.jsp gui for displaying and processing
 * the behavior of each node in the GUI tree.
 * <p>
 * Every WebNode requires the an 'id' and 'name' attribute.  All others are optional. The 'id' attribute
 * must be a unique id which will be used to indentifty the node.  The 'name' attribute will be what
 * is displayed to the user in the tree, and what will be searched during searches.
 * <p>
 * Optionally an 'action' attribue may be defined which defines the action URL to be exectued when the
 * node name is clicked in the GUI.  This URL may contain any valid URL including javascipt calls using the 
 * "javascript:" syntax.  The target of the URL is the top frame of the tree GUI which contains the
 * code defined "JavaScript.js", for the skin currently defined.
 * <p>
 * In addition, an optional 'info' attribute is used for displaying information in a popup window
 * when the mouse hovers of the icon of the node, on compatible browsers.
 * <p>
 * All the attributes discussed thus far may be defined during object construction, or through
 * an accessor method.
 * <p>
 * An advanced attribute set using the 'setIcon' method is used if you wish your node to over-ride
 * the standard behavior of your node action, such as expanding or collapsing folder icon and action.
 * Beware however of placement of these icons, for they may block functionality needed for viewing the
 * tree. They are best used as the leaf nodes, or the tree root.
 * <p>
 * Created on 09/26/2002
 * @author  Marc Piparo SAIC
 * @version 1.0
 */ 
public class WebNode 
{
  private String    _id;          // * required - unique node id *
  private String    _name;        // * required - node name to display *
  
  private String    _action;      // * optional - url action for node *
  private String    _info;        // * optional - mouse hover info box content *
  
  private String    _iconAction;  // * optional - overide url action on icon *
  private String    _iconGraphic; // * optional - icon graphic filename (in current skin) *
  
  // internal use
  private boolean   _hasChildren; 

 /**
  * Constructs a WebNode given the supplied parameters.
  * <p>
  * @param id     unique Identifier of the node.
  * @param name   node name to be displayed in the tree, and used for searches.
  */
  public WebNode (String id, String name)
  {
    _id    = id;
    _name  = name;
    _action = "";
    _info = "";
    _iconAction = "";
    _iconGraphic = "";
    _hasChildren = false;
  }

  /**
   * Constructs a WebNode given the supplied parameters.
   * <p>
   * @param id     unique Identifier of the node.
   * @param name   node name to be displayed in the tree, and used for searches.
   * @param action URL action to be executed when the node name is selected.
   */
  public WebNode (String id, String name, String action)
  {
    this(id, name);
    _action = action;
  }

  /**
   * Constructs a WebNode given the supplied parameters.
   * <p>
   * @param id     unique Identifier of the node.
   * @param name   node name to be displayed in the tree, and used for searches.
   * @param action URL action to be executed when the node name is selected.
   * @param info   Message displayed on mouse hover on node icon.
   */
  public WebNode (String id, String name, String action, String info)
  {
    this(id, name, action);
    _info = info;
  }
  
  /**
   * Copy Constructor constructs a WebNode given another WebNode
   * <p>
   * @param srcWebNode     WebNode to copy.
   */
  public WebNode (WebNode srcWebNode)
  {
     _id         = srcWebNode.getId();
    _name        = srcWebNode.getName();
    _action      = srcWebNode.getAction();
    _info        = srcWebNode.getInfo();
    _iconAction  = srcWebNode.getIconAction();
    _iconGraphic = srcWebNode.getIconGraphic();
    _hasChildren = srcWebNode.hasChildren();
  }

 /**
  * Gets WebNode Identifier.
  * <p>
  * @return Id attribute of WebNode.
  */
  public String getId()
  {
    return _id;
  }

 /**
  * Gets WebNode Name.
  * <p>
  * @return Name attribute of WebNode.
  */
  public String getName()
  {
    return _name;
  }

 /**
  * Gets action attribute defined for this WebNode.
  * <p>
  * @return WebNode Action attribute.
  */
  public String getAction()
  {
    return _action;
  }
  
 /**
  * Gets WebNode Info message used for mouse hover popups.
  * <p>
  * @return Info attribute of WebNode.
  */
  public String getInfo()
  {
    return _info;
  }
  
 /**
  * Sets the info message attribute of the WebNode.
  * <p>
  * @param infoMessage  Message used for mouse hover popup window.
  */  
  public void setInfo(String infoMessage)
  {
    _info = infoMessage;
  }
  
 /**
  * Sets the action attribute of the WebNode.
  * <p>
  * @param action Nodes action URL executed on node name selection.
  */  
  public void setAction(String action)
  {
    _action = action;        
  }
  
 /**
  * Used to over-ride the inherit tree icon actions, such as expand or collapse folders.
  * Be sure to use with caution, changing the tree behavior can cause display issues.
  * <p>
  * @param iconGraphic   The filename of the graphic to use for the node icon. File should be
  *                      in the skin directory in use.
  * @param iconAction    The URL action to execute on the selection of the icon graphic.
  */  
  public void setIcon(String iconGraphic, String iconAction)
  {
    _iconGraphic = iconGraphic;
    _iconAction = iconAction;
  }
  
 /**
  * Gets the iconAction attribute.
  * <p>
  * @return iconAction attribute of WebNode.
  */
  public String getIconAction()
  {
    return _iconAction;
  }
  
 /**
  * Gets the iconGraphic attribute.
  * <p>
  * @return iconGraphic attribute of WebNode.
  */
  public String getIconGraphic()
  {
    return _iconGraphic;
  }
  
 /**
  * Sets if this node has children.
  * <p>
  * @param value  boolean indicating if this node has children. true=yes.
  */  
  public void setHasChildren(boolean value)
  {
    _hasChildren = value;
  }
  
 /**
  * Determines if this node has children.
  * <p>
  * @return hasChildren attribute of the WebNode.
  */  
  public boolean hasChildren()
  {
    return _hasChildren;
  }
  
  public WebNode copy(String newId) 
  {
    return new WebNode(newId, getName(), getAction(), getInfo());
  }
}

