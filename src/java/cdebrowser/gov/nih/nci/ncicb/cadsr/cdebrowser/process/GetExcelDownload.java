package gov.nih.nci.ncicb.cadsr.cdebrowser.process;
// java imports
import java.util.*;
import java.io.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import oracle.sql.*;
import oracle.jdbc.*;

// Framework imports
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.PersistingProcess;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.Service;
//import oracle.cle.process.ProcessConstants;
import oracle.cle.persistence.HandlerFactory;

//CDE Browser Application Imports

import gov.nih.nci.ncicb.cadsr.util.*;
import gov.nih.nci.ncicb.cadsr.database.*;
import gov.nih.nci.ncicb.cadsr.base.process.*;
import gov.nih.nci.ncicb.cadsr.resource.*;
import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.cdebrowser.DESearchQueryBuilder;
import gov.nih.nci.ncicb.cadsr.CaDSRConstants;

/**
 *
 * @author Ram Chilukuri 
 */
public class GetExcelDownload extends BasePersistingProcess{
	private static final String EMPTY_STRING = "";
  private static final int NUMBER_OF_DE_COLUMNS = 32;
    
	public GetExcelDownload(){
		this(null);

		DEBUG = false;
	} 

	public GetExcelDownload(Service aService){
		super(aService);

		DEBUG = false;
	}

	/**
	* Registers all the parameters and results 
	* (<code>ProcessInfo</code>) for this process
	* during construction.
	*
	* @author Ram Chilukuri
	*/
	public void registerInfo(){
		try{
			registerParameterObject(ProcessConstants.ALL_DATA_ELEMENTS);
      registerStringParameter("SBREXT_DSN");
      registerStringParameter("src");
      registerStringParameter("XML_DOWNLOAD_DIR");
      registerStringResult("EXCEL_FILE_NAME");
      registerParameterObject("dbUtil");
      registerStringParameter("SBR_DSN");
      registerParameterObject("desb");
      registerParameterObject(ProcessConstants.DE_SEARCH_QUERY_BUILDER);
      
		} 
		catch(ProcessInfoException pie){
			reportException(pie,true);
		}  

    catch(Exception e) {
    }
	}

	/**
	* persist: called by start to do all persisting
	*   work for this process.  If this method throws
	*   an exception, then the condition returned by
	*   the <code>getPersistFailureCondition()</code>
	*   is set.  Otherwise, the condition returned by
	*   <code>getPersistSuccessCondition()</code> is
	*   set.
	*
	* @author Ram Chilukuri
	*/
	public void persist() throws Exception{
    DBUtil dbUtil = null;
    String excelFileSuffix = "";
    String excelFilename = "";
    
		try{
      dbUtil = (DBUtil)getInfoObject("dbUtil");
      String dsName = getStringInfo("SBREXT_DSN");
      dbUtil.getConnectionFromContainer(dsName);

      excelFileSuffix = dbUtil.getUniqueId("SBREXT.XML_FILE_SEQ.NEXTVAL");
      excelFilename = getStringInfo("XML_DOWNLOAD_DIR")+"DataElements"+"_"
                          +excelFileSuffix+".csv";
      //writeToExcelFile(resultsVector,excelFilename);
      generateExcelFile(excelFilename,dbUtil);
      setResult("EXCEL_FILE_NAME",excelFilename);

			setCondition(SUCCESS);
		}
		catch(Exception ex){
      try{
				setCondition(FAILURE);
			}
			catch(TransitionConditionException tce){
				reportException(tce,DEBUG);
			}
		reportException(ex,DEBUG);
		}
    finally {
      try{
        dbUtil.returnConnection();
			}
      catch (Exception e){
        e.printStackTrace();
      }
    }
	}

	protected TransitionCondition getPersistSuccessCondition(){
		return getCondition(SUCCESS);
	}

	protected TransitionCondition getPersistFailureCondition(){
		return getCondition(FAILURE);
	}

  private void writeToExcelFile(Vector resultsVector,String fn) throws Exception {
    try {
      PrintWriter out
          = new PrintWriter(new BufferedWriter(new FileWriter(fn)));
      out.println("Preferred Name,Long Name,Doc Text,Context,Workflow Status,CDE ID,Version");
      Vector deVec = new Vector();
      String excelRow = new String();
      for (int i = 0; i < resultsVector.size(); i++)  {
        excelRow = "";
        deVec = (Vector)resultsVector.elementAt(i);
        excelRow = "\""+checkForNull((String)deVec.elementAt(0))+"\"" +"," +
                   "\""+checkForNull((String)deVec.elementAt(1))+"\"" +"," +
                   "\""+checkForNull((String)deVec.elementAt(2))+"\"" +"," +
                   "\""+checkForNull((String)deVec.elementAt(3))+"\"" +"," +
                   "\""+checkForNull((String)deVec.elementAt(4))+"\"" +"," +
                   "\""+checkForNull((String)deVec.elementAt(5))+"\"" +"," +
                   "\""+checkForNull((String)deVec.elementAt(6))+"\"";
        out.println(excelRow);
      }
      excelRow = null;
      out.close();
    } 
    catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } 
    finally {
    }
    
  }

  private String checkForNull(String s){
    if (s==null) return "";
    else return s;
  }

  private String convertToString(CHAR s){
    if (s==null) return "";
    else return s.toString();
  }
  private String convertToString(java.sql.Date s){
    if (s==null) return "";
    else return s.toString();
  }
  private String convertToString(NUMBER s){
    if (s==null) return "";
    else return String.valueOf(s.floatValue());
  }
  public void generateExcelFile(String filename, DBUtil dbUtil) throws Exception{
    Connection cn = null;
    Statement st = null;
    ResultSet rs = null;
    PrintWriter pw = null;
    String where = "";
    DataElementSearchBean desb = null;
    DESearchQueryBuilder deSearch = null;
    String source = null;

    source = getStringInfo("src");
    
    try {
  
      String dataSource = getStringInfo("SBREXT_DSN");
      cn = dbUtil.getConnection();
      st = cn.createStatement();
      if ("deSearch".equals(source)) {
        desb = (DataElementSearchBean)getInfoObject("desb");
        deSearch = (DESearchQueryBuilder)getInfoObject(
                                ProcessConstants.DE_SEARCH_QUERY_BUILDER);
        where = deSearch.getXMLQueryStmt();
      }
      else if ("cdeCart".equals(source)) { 
        HttpServletRequest myRequest = 
          (HttpServletRequest) getInfoObject("HTTPRequest");
        HttpSession userSession = myRequest.getSession(false);
        CDECart cart = (CDECart)userSession.getAttribute(CaDSRConstants.CDE_CART);
        Collection items = cart.getDataElements();
        CDECartItem item = null;
        boolean firstOne = true;
        StringBuffer whereBuffer = new StringBuffer("");
        Iterator itemsIt = items.iterator();
        while (itemsIt.hasNext()) {
          item = (CDECartItem)itemsIt.next();
          if (firstOne) {
            whereBuffer.append("'" +item.getId()+"'");
            firstOne = false;
          }
          else 
            whereBuffer.append(",'" +item.getId()+"'");
        }
        where = whereBuffer.toString();
      }
      else {
        throw new Exception("No result set to download");
      }
    
      String sqlStmt = "SELECT * FROM DE_EXCEL_GENERATOR_VIEW "
                      +"WHERE DE_IDSEQ IN "
                      +" ( "+where+" )  ";
                      //+" ORDER BY PREFERRED_NAME ";
      
      rs = st.executeQuery(sqlStmt);
      pw = getPrintWriter(filename);
      
      printHeader(pw);
      
      String deStr = null;
      List validValueStrList =null;
      List clasStrList = null;
      List desStrList = null;
      List refStrList = null;
      
      while (rs.next()){
        List deList = new ArrayList(18);
        deList.add(rs.getString("PREFERRED_NAME"));
        deList.add(rs.getString("LONG_NAME"));
        deList.add(rs.getString("DOC_TEXT"));
        deList.add(rs.getString("PREFERRED_DEFINITION"));
        deList.add(rs.getString("VERSION"));
        deList.add(rs.getString("DE_CONTE_NAME"));
        deList.add(rs.getString("DE_CONTE_VERSION"));
        deList.add(rs.getString("VD_PREFERRED_NAME"));  
        deList.add(rs.getString("VD_VERSION"));
        deList.add(rs.getString("VD_CONTE_NAME")); 
        deList.add(rs.getString("VD_CONTE_VERSION")); 
        deList.add(rs.getString("DEC_PREFERRED_NAME"));
        deList.add(rs.getString("DEC_VERSION")); 
        deList.add(rs.getString("DEC_CONTE_NAME")); 
        deList.add(rs.getString("DEC_CONTE_VERSION")); 
        deList.add(rs.getString("CDE_ID"));
        deList.add(convertToString(rs.getDate("BEGIN_DATE")));
        deList.add(rs.getString("ORIGIN"));
        //Added in 2.0.1
        deList.add(Integer.toString(rs.getInt("DEC_ID"))); //18
        deList.add(Integer.toString(rs.getInt("VD_ID"))); //19
        deList.add(rs.getString("VD_TYPE")); //20
        deList.add(rs.getString("DTL_NAME")); //21
        deList.add(Integer.toString(rs.getInt("MIN_LENGTH_NUM"))); //22
        deList.add(Integer.toString(rs.getInt("MAX_LENGTH_NUM"))); //23
        deList.add(rs.getString("LOW_VALUE_NUM")); //24
        deList.add(rs.getString("HIGH_VALUE_NUM")); //25
        deList.add(Integer.toString(rs.getInt("DECIMAL_PLACE"))); //26
        deList.add(rs.getString("FORML_NAME")); //27
        deList.add(rs.getString("VD_LONG_NAME")); //28
        deList.add(rs.getString("DEC_LONG_NAME")); //29
        deList.add(rs.getString("DE_WK_FLOW_STATUS")); //30
        deList.add(rs.getString("REGISTRATION_STATUS")); //31
        
        deStr= getDataElementStr(deList);

        ARRAY array = ((OracleResultSet)rs).getARRAY ("VALID_VALUES");
        ResultSet nestedRs = array.getResultSet();
        List vvList = new ArrayList(9);
        while (nestedRs.next()){
          List vvAtrr = new ArrayList(2);
          STRUCT vvStruct = (STRUCT)nestedRs.getObject(2);
          Datum vv[] = vvStruct.getOracleAttributes();
          CHAR value = (CHAR)vv[0];
          CHAR vm =  (CHAR)vv[1];
          vvAtrr.add(value.toString());
          vvAtrr.add(vm.toString());
          //vvList.add(value.toString());
          vvList.add(vvAtrr);         
        }
        validValueStrList=this.getValidValuesStr(vvList);
       // Print Classification

         List clasList = new ArrayList(11);
         
        ARRAY clasArray = ((OracleResultSet)rs).getARRAY ("CLASSIFICATIONS");
        ResultSet nestedClassRs = clasArray.getResultSet();
        
        while (nestedClassRs.next()){
         List arrList = new ArrayList(9);
          //Object obj = nestedClassRs.getObject(2);
          //String str = obj.getClass().getName();
          STRUCT clasStruct = (STRUCT)nestedClassRs.getObject(2);
          Datum clas[] = clasStruct.getOracleAttributes();
                            
          STRUCT adminClas = (STRUCT)clas[0];
          Datum admin[] = adminClas.getOracleAttributes();
          NUMBER csId = (NUMBER)admin[0];
          CHAR csContNAme = (CHAR)admin[1];
          NUMBER csVersion = (NUMBER)admin[2];
          CHAR csPreName = (CHAR)admin[3];         
          NUMBER csContVersion = (NUMBER)admin[4];
          
          CHAR csiName = (CHAR)clas[1];
          CHAR csTypeName = (CHAR)clas[2];
          
          arrList.add(convertToString(csPreName));
          arrList.add(convertToString(csVersion));
          arrList.add(convertToString(csContNAme));
          arrList.add(convertToString(csContVersion));
          arrList.add(convertToString(csiName));
          arrList.add(convertToString(csTypeName));
          clasList.add(arrList);
        }
        
        clasStrList=this.getClassificationsStr(clasList);

        // print Designation
         List desList = new ArrayList(11);

        ARRAY desArray = ((OracleResultSet)rs).getARRAY ("designations");
        ResultSet nestedDesRs = desArray.getResultSet();
        
        while (nestedDesRs.next()){
          List arrList = new ArrayList(9);
          STRUCT desStruct = (STRUCT)nestedDesRs.getObject(2);
          Datum ref[] = desStruct.getOracleAttributes();
                            
          CHAR desContName = (CHAR)ref[0];
          NUMBER desContVer = (NUMBER)ref[1];
          CHAR desName = (CHAR)ref[2];         
          CHAR desType = (CHAR)ref[3];
          CHAR desLae = (CHAR)ref[4];
          

          arrList.add(convertToString(desContName));
          arrList.add(convertToString(desContVer));
          arrList.add(convertToString(desName));
          arrList.add(convertToString(desType)); 
          desList.add(arrList);
        }        
        desStrList=getDesignationStr(desList); 

        
        // print reference docs
         List refList = new ArrayList(11);

        ARRAY refArray = ((OracleResultSet)rs).getARRAY ("reference_docs");
        ResultSet nestedRefRs = refArray.getResultSet();
        
        while (nestedRefRs.next()){
          List arrList = new ArrayList(9);
          STRUCT refStruct = (STRUCT)nestedRefRs.getObject(2);
          Datum ref[] = refStruct.getOracleAttributes();
                            
          CHAR rdName = (CHAR)ref[0];
          CHAR orgName = (CHAR)ref[1];
          CHAR rdType = (CHAR)ref[2];         
          CHAR docText = (CHAR)ref[3];
          CHAR rdUrl = (CHAR)ref[4];
          CHAR laeName = (CHAR)ref[5];
          CHAR displayOrder = (CHAR)ref[6];
          
          arrList.add(convertToString(docText));
          arrList.add(convertToString(rdName));
          arrList.add(convertToString(rdType));  
          refList.add(arrList);
        }
        
        refStrList=this.getReferenceDocsStr(refList); 

        pw.println(deStr);
        printMultipleValus(validValueStrList,clasStrList,desStrList,refStrList,pw);
        
        
        vvList = null;
        clasList = null;
        refList=null;
        desList=null;
        deList = null;

      }
      
    } 
    catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } 
    finally {
      try {
        if (rs != null) rs.close();
        if (st != null) st.close();
        //if (cn != null) cn.close();
        if (pw != null) pw.close();
      }
      
      catch (Exception e){
        System.out.println
         ("Unable to perform clean up due to the following error "+e.getMessage());
      }
      
    }
  }
  private PrintWriter getPrintWriter(String newFilename) throws Exception{
    PrintWriter pw = null;
    try {
      pw = new PrintWriter(new BufferedWriter(new FileWriter(newFilename)));
    }
    catch (Exception ex) {
      throw ex;
    }
    return pw;
  }
  private void printHeader(PrintWriter pw) {
    pw.println("Preferred Name,"
                  +"Long Name,"
                  +"Document Text,"
                  +"Preferred Definition,"
                  +"Version,"
                  +"Context Name,"
                  +"Context Version,"
                  +"Value Domain Public ID,"
                  +"Value Domain Preferred Name,"
                  +"Value Domain Long Name,"
                  +"Value Domain Version,"
                  +"Value Domain Context Name,"
                  +"Value Domain Context Version,"
                  +"Value Domain Type,"
                  +"Value Domain Datatype,"
                  +"Value Domain Min Length,"
                  +"Value Domain Max Length,"
                  +"Value Domain Min Value,"
                  +"Value Domain Max Value,"
                  +"Value Domain Decimal Place,"
                  +"Value Domain Format,"
                  +"Data Element Concept Public ID,"
                  +"Data Element Concept Preferred Name,"
                  +"Data Element Concept Long Name,"
                  +"Data Element Concept Version,"
                  +"Data Element Concept Context Name,"
                  +"Data Element Concept Context Version,"
                  +"Public ID,"
                  +"Workflow Status,"
                  +"Registration Status,"
                  +"Begin Date,"
                  +"Source,"
                  +"Valid Values,"
                  +"Value Meaning,"
                  
                  +"Classification Scheme Preferred Name,"
                  +"Classification Scheme Context Version,"
                  +"Classification Scheme Context Name,"
                  +"Classification Scheme Version,"
                  +"Classification Scheme Item Name,"
                  +"Classification Scheme Item Type Name,"
                  
                  +"Designation Context Name,"
                  +"Designation Context Version,"
                  +"Name,"
                  +"Designation Type Name,"

                  +"Document,"
                  +"Document Name,"
                  +"Document Type,"
                  );
  }

  private String getDataElementStr(List l) {
    String excelRow = "";
    
    excelRow = "\""+checkForNull((String)l.get(0))+"\"" +"," +
               "\""+checkForNull((String)l.get(1))+"\"" +"," +
               "\""+checkForNull((String)l.get(2))+"\"" +"," +
               "\""+checkForNull((String)l.get(3))+"\"" +"," +
               "\""+checkForNull((String)l.get(4))+"\"" +"," +
               "\""+checkForNull((String)l.get(5))+"\"" +"," +
               "\""+checkForNull((String)l.get(6))+"\"" +"," +
               "\""+checkForNull((String)l.get(19))+"\"" +"," +
               "\""+checkForNull((String)l.get(7))+"\"" +"," +
               "\""+checkForNull((String)l.get(28))+"\"" +"," +
               "\""+checkForNull((String)l.get(8))+"\"" +"," +
               "\""+checkForNull((String)l.get(9))+"\"" +"," +
               "\""+checkForNull((String)l.get(10))+"\"" +"," +
               "\""+checkForNull((String)l.get(20))+"\"" +"," +
               "\""+checkForNull((String)l.get(21))+"\"" +"," +
               "\""+checkForNull((String)l.get(22))+"\"" +"," +
               "\""+checkForNull((String)l.get(23))+"\"" +"," +
               "\""+checkForNull((String)l.get(24))+"\"" +"," +
               "\""+checkForNull((String)l.get(25))+"\"" +"," +
               "\""+checkForNull((String)l.get(26))+"\"" +"," +
               "\""+checkForNull((String)l.get(27))+"\"" +"," +
               "\""+checkForNull((String)l.get(18))+"\"" +"," +
               "\""+checkForNull((String)l.get(11))+"\"" +"," +
               "\""+checkForNull((String)l.get(29))+"\"" +"," +
               "\""+checkForNull((String)l.get(12))+"\"" +"," +
               "\""+checkForNull((String)l.get(13))+"\"" +"," +
               "\""+checkForNull((String)l.get(14))+"\"" +"," +
               "\""+checkForNull((String)l.get(15))+"\"" +"," +
               "\""+checkForNull((String)l.get(30))+"\"" +"," +   //Workflow Status
               "\""+checkForNull((String)l.get(31))+"\"" +"," +   //Registration Status
               "\""+checkForNull((String)l.get(16))+"\"" +"," +   //Begin Date
               "\""+checkForNull((String)l.get(17))+"\"" +"," +   //Source            
               "\""+EMPTY_STRING+"\"";
    return excelRow;
  }

  private List getValidValuesStr(List l) {
    List arrList = new ArrayList(9);
    for(int i=0; i<l.size(); i++){
      String excelRow = "";
      List vvAttr = (ArrayList)l.get(i);
      excelRow =  "\""+(String)vvAttr.get(0)+"\""+"," +
                 "\""+(String)vvAttr.get(1)+"\""+"," ;   
      arrList.add(excelRow);
    }
    return arrList;
  }
  private List getClassificationsStr(List l) {
    List arrList = new ArrayList(9);
    for(int i=0; i<l.size(); i++){
      String excelRow = "";
      List vvAttr = (ArrayList)l.get(i);     
      excelRow =  "\""+(String)vvAttr.get(0)+"\""+"," +
                 "\""+(String)vvAttr.get(1)+"\""+"," +
                 "\""+(String)vvAttr.get(2)+"\""+"," +
                 "\""+(String)vvAttr.get(3)+"\""+"," +
                 "\""+(String)vvAttr.get(4)+"\""+"," +
                 "\""+(String)vvAttr.get(5)+"\""+"," ;    
      arrList.add(excelRow);
    }
    return arrList;
  }

  private List getDesignationStr(List l) {
        List arrList = new ArrayList(9);
    for(int i=0; i<l.size(); i++){
      String excelRow = "";
      List vvAttr = (ArrayList)l.get(i);  
      excelRow = "\""+(String)vvAttr.get(0)+"\""+"," +
                 "\""+(String)vvAttr.get(1)+"\""+"," +
                 "\""+(String)vvAttr.get(2)+"\""+"," +
                 "\""+(String)vvAttr.get(3)+"\""+"," ;
      arrList.add(excelRow);
    }
    return arrList;
  }
  private List getReferenceDocsStr(List l) {
        List arrList = new ArrayList(9);
    for(int i=0; i<l.size(); i++){
      String excelRow = "";
      List vvAttr = (ArrayList)l.get(i);  
      excelRow = "\""+(String)vvAttr.get(0)+"\""+"," +
                 "\""+(String)vvAttr.get(1)+"\""+"," +
                 "\""+(String)vvAttr.get(2)+"\"";    
    arrList.add(excelRow);
    }
    return arrList;
  }  
    private String getEmptyStrings(int n)
    {
      String str = "";
      for(int i=0;i<n;++i)
      {
        str=str+"\""+EMPTY_STRING+"\"" +",";
      }
      return str;
    }

   public void  printMultipleValus(List vvList,List clList,List desList,List refList,PrintWriter pw)
    {
      int temp1 = Math.max(vvList.size(),clList.size());
      int temp2 = Math.max(desList.size(),refList.size());
      int maxSize = Math.max(temp1,temp2);
      
      for (int i=0;i<maxSize;++i)
      {
        String str = getEmptyStrings(NUMBER_OF_DE_COLUMNS);
        if(vvList.size()>i)
          {
            str=str+vvList.get(i);
          }
        else
        {
            str=str+getEmptyStrings(2);
        }
        if(clList.size()>i)
          {
            str=str+clList.get(i);
          }
        else
        {
           str=str+getEmptyStrings(6);
        }
        if(desList.size()>i)
          {
            str=str+desList.get(i);
          }
        else
        {
            str=str+getEmptyStrings(4);
        }
        if(refList.size()>i)
          {
            str=str+refList.get(i);
          }  
        pw.println(str);
      }
    }
  }

    