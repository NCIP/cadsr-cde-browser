package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.resource.Question;
import java.util.*;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;

import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;

public class DTOTransformer {

    
    /**
     * Transformer method to convert ValidValue to FormValidValue
     *
     * @param validValue a <code>ValidValue</code> value
     * @return a <code>FormValidValue</code> value
     */
    public static FormValidValue toFormValidValue(ValidValue validValue,Question question) {
      FormValidValue fvv = new FormValidValueTransferObject();
      fvv.setVpIdseq(validValue.getVpIdseq());
      fvv.setLongName(validValue.getShortMeaningValue());
      fvv.setPreferredDefinition(validValue.getShortMeaning());
      fvv.setContext(question.getModule().getForm().getContext());
      fvv.setAslName(question.getModule().getForm().getAslName());
      fvv.setVpIdseq(validValue.getVpIdseq());
      fvv.setVersion(question.getModule().getForm().getVersion());
      fvv.setQuestion(question);
      return fvv;
    }

    
    /**
     * Transform a list of validValues to a list of FormValidValues.
     * <br/> Will throw a ClassCastException is list is not of ValidValues
     *
     * @param validValues a <code>List&lt;ValidValue&gt;</code> value
     * @return a <code>List</code> value
     */
    public static List toFormValidValueList(List validValues,Question question) {
	List newValidValues = new ArrayList();
	for(Iterator it = validValues.iterator(); it.hasNext();) {
	    ValidValue vv = (ValidValue)it.next();
	    FormValidValue fvv = toFormValidValue(vv,question);
	    newValidValues.add(fvv);
	}

	return newValidValues;
    }

}