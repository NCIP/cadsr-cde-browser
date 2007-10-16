package gov.nih.nci.ncicb.cadsr.util;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class TimeUtils 
{
  public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EST"));
  public static Map startMap= new HashMap();
  public static Map endMap= new HashMap();
  
  public TimeUtils()
  {
  }
  public static void recordStartTime(String key)
  {
    startMap.put(key,Calendar.getInstance(TimeZone.getTimeZone("EST")));
  }
  public static void  recordEndTime(String key)
  {
    endMap.put(key,Calendar.getInstance(TimeZone.getTimeZone("EST")));
  }  
  
  public static String  getLapsedTime(String key)
  {
    Calendar start = (Calendar)startMap.get(key);
    Calendar end = (Calendar)endMap.get(key);
    
    long l1 = start.getTime().getTime();
    long l2 = end.getTime().getTime();
    long difference = l2 - l1;
    return Long.toString(difference);
  }    
  
  public static String  getLapsedTimeInSec(String key)
  {
    Calendar start = (Calendar)startMap.get(key);
    Calendar end = (Calendar)endMap.get(key);
    
    long l1 = start.getTime().getTime();
    long l2 = end.getTime().getTime();
    long difference = (l2 - l1)/1000;
    return Long.toString(difference);
  }   
  
  public static String  getLapsedTimeInMinutes(String key)
  {
    Calendar start = (Calendar)startMap.get(key);
    Calendar end = (Calendar)endMap.get(key);
    
    long l1 = start.getTime().getTime();
    long l2 = end.getTime().getTime();
    long difference = ((l2 - l1)/1000)/60;
    return Long.toString(difference);
  }     
  
  public static String  getEasternTime() {
        /* 
        ** on some JDK, the default TimeZone is wrong
        ** we must set the TimeZone manually!!!
        **   Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EST"));
        */
        
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EST"));
        
        java.text.SimpleDateFormat sdf = 
              new java.text.SimpleDateFormat(DATE_FORMAT);
        /*
        ** on some JDK, the default TimeZone is wrong
        ** we must set the TimeZone manually!!!
        **     sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        */
        sdf.setTimeZone(TimeZone.getDefault());          
        return sdf.format(cal.getTime());  
    }
    
    public static void main(String[] args) throws Exception
    {
      TimeUtils.recordStartTime("s");
      Thread.sleep(20);
      TimeUtils.recordEndTime("s");
      System.out.println(TimeUtils.getLapsedTime("s"));
    }
 
}