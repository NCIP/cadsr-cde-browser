package gov.nih.nci.ncicb.cadsr.html;

import gov.nih.nci.ncicb.cadsr.exception.PopListBuilderException;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.util.GenericPopListBean;


public class DropDownListHelper {
  public static String getReadContextsList(
    String selectedId, String listName, DBUtil dbUtil)
    throws PopListBuilderException {
    String popList = null;

    try {
      String where = " 1=1 ";
      StringBuffer contextList =
        GenericPopListBean.buildList(
          "SBR.CONTEXTS","NAME", "CONTE_IDSEQ", selectedId, listName, dbUtil,
          where, false, 0, false, false, false, false, "LOVField");
      popList = contextList.toString();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
    }

    return popList;
  }

  public static String getACStatusesList(
    String selectedId, String acType, String listName, DBUtil dbUtil)
    throws PopListBuilderException {
    String popList = null;

    try {
      String where = " ACTL_NAME = '" + acType + "'";
      StringBuffer workflowList =
        GenericPopListBean.buildList(
          "sbrext.ASL_ACTL_EXT", "ASL_NAME", "ASL_NAME", selectedId, listName,
          dbUtil, where, false, 0, false, false, false, false, "LOVField");
      popList = workflowList.toString();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new PopListBuilderException(
        "Exception caught while building statuses pop list ", ex);
    } finally {
    }

    return popList;
  }

  public static String getFormTypesList(
    String selectedId, String listName, DBUtil dbUtil)
    throws PopListBuilderException {
    String popList = null;

    try {
      String where = " 1=1 ";
      StringBuffer typesList =
        GenericPopListBean.buildList(
          "SBREXT.QC_DISPLAY_LOV_EXT", "QCDL_NAME", "QCDL_NAME", selectedId,
          listName, dbUtil, where, false, 0, false, false, false, false,
          "LOVField");
      popList = typesList.toString();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new PopListBuilderException(
        "Exception caught while building form types pop list ", ex);
    } finally {
    }

    return popList;
  }
}
