package gov.nih.nci.ncicb.cadsr.common.persistence.base;

public class BaseValueObject  {
  public BaseValueObject() {
  }
  /**
   * Check for null value for the given Object. If the input Object is null, an
   * empty String is returned, else toString() of the given object is returned.
   *
   * @param   inputObj an <code>Object</code>.
   * @return  the <code>String</code> value for the given Object.
   */
  protected static String checkForNull(Object inputObj) {
    if (inputObj == null) {
      return new String("");
    }
    else {
      return inputObj.toString();
    }
  }
}