/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.contexttree;

public interface TreeConstants  {
  public static final String DE_SEARCH_TREE = "deTree";
  public static final String FORM_SEARCH_TREE = "formTree";
  public static final String TREE_TYPE_URL_PARAM = "treeType";
  public static final String FUNCTION_NAME_URL_PARAM = "functionName";
  public static final String EXTRA_URL_PARAMS = "extraURLParameters";
  public final static String IDSEQ_GENERATOR = "admincomponent_crud.cmr_guid";
  public static final String DE_SEARCH_FUNCTION = "performAction";
  public static final String FORM_SEARCH_FUNCTION = "formSearchAction";
  public static final String FORM_DETAILS_FUNCTION = "formDetailsAction";
  public static final String CTEP_USER_FLAG = "ctepUser";
  
  public static final String TREE_BREADCRUMBS="treeBreadCrumbs";
  
  //This has to be replaced to the actual value once the tree is build
  //Done in webTree.jsp
  public static final String TREE_BREADCRUMBS_HOLDER="%%crumbs%%";
  
  public static final String BR_CONTEXT_EXCLUDE_LIST_STR = "brContextExcludeListStr";
}