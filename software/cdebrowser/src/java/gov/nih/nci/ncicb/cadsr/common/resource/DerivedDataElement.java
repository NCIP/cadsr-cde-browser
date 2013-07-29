/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.resource;
import java.util.Collection;

public interface DerivedDataElement extends Audit
{
   public String getConcatenationCharacter();
   public void setConcatenationCharacter(String concatChar);

   public String getMethods();
   public void setMethods(String methods);

   public String getRule();
   public void setRule(String rule);

   public DataElementDerivationType getType();
   public void setType(DataElementDerivationType typeName);

   public Collection getDataElementDerivation();
   public void setDataElementDerivation(Collection deDerivations);

}