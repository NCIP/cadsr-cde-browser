package gov.nih.nci.ncicb.cadsr.util;

/**
 * This utility class supports a conversion between String and boolean data
 * types.
 *
 * @author Alan M. Levine, Oracle Corporation
 *
 */
public class StringUtilities 
{
  public static boolean toBoolean(String inString) throws Exception {
    if (inString.toUpperCase().equals("Y") || inString.toUpperCase().equals("TRUE"))
    {
      return true;
    }
    else if (inString.toUpperCase().equals("N") || inString.toUpperCase().equals("FALSE"))
    {
      return false;
    }
    else
    {
      throw new Exception();
    }
  }

  public static String booleanToStr(boolean bool) {
    if (bool)
      return "Y";
    else
      return "N";
  }

  public static String [] tokenizeCSVList(String values)
  {
    String [] retVal = null;
    try {
      java.util.StringTokenizer st = new java.util.StringTokenizer(values,",");
      int numberOfTokens = st.countTokens();
      retVal = new String[numberOfTokens];
      for(int i = 0; i < numberOfTokens; i++) {
        retVal[i] = st.nextToken();
      }
    }
    catch (Exception e)
    {
      return new String[0];
    }

    return retVal;
  }

  public static String getValidJSString(String sourceStr) {
    String newStr = StringReplace.strReplace(sourceStr,"'","\\'");
    newStr = StringReplace.strReplace(newStr,"\"","&quot;");
    return newStr;
  }

  public static String ReplaceNull(Object obj) {
    if (obj == null) return "";
    else return obj.toString();
  }
}