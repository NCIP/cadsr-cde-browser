package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.util.DebugStringBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class FormValidValueTransferObject extends FormElementTransferObject
  implements FormValidValue {
  
  private String valueIdseq;
  private Question term;
  private String vpIdseq;
  private int dispOrder;
  private String shortMeaning;
  private List instructions;
  
  public FormValidValueTransferObject() {
  }

  public String getValueIdseq() 
  {
    return valueIdseq;
  }
  
  public void setValueIdseq(String idseq) 
  {
    this.valueIdseq = idseq;
  }

  public Question getQuestion() 
  {
    return term;
  }
  
  public void setQuestion(Question term) 
  {
    this.term = term;
  }

  public String getVpIdseq() 
  {
    return vpIdseq;
  }
  
  public void setVpIdseq(String vpIdseq) 
  {
    this.vpIdseq = vpIdseq;
  }

  public int getDisplayOrder() {
    return dispOrder;
  }

  public void setDisplayOrder(int dispOrder) {
    this.dispOrder = dispOrder;
  }
  
  public String getShortMeaning(){
    return shortMeaning;
  }
  public void setShortMeaning(String newShortMeaning){
    shortMeaning=newShortMeaning;
  }


  public Instruction getInstruction()
  {
    if(instructions!=null&&!instructions.isEmpty())
      return (Instruction)instructions.get(0);
    else
      return null;
  }
  public void setInstruction(Instruction newInstruction)
  {
    if(newInstruction!=null)
    {
    instructions= new ArrayList();
    instructions.add(newInstruction);      
    }
    else
    {
      instructions=null;
    }
  }
  
  public List getInstructions()
  {
    return instructions;
  }
  public void setInstructions(List newInstructions)
  {
    instructions=newInstructions;
  }
  
  /**
   * Clones the object
   * Makes a deep copy of the Valivalues
   * sets Question to null;
   * @return 
   */
  public Object clone() throws CloneNotSupportedException {
     FormValidValue copy = null;
      copy = (FormValidValue)super.clone();
      copy.setQuestion(null);
      
     if(getInstructions()!=null)
     {
       List instructionsCopy = new ArrayList();
       ListIterator it = getInstructions().listIterator();
       while(it.hasNext())
       {
         Instruction instr = (Instruction)it.next();
         Instruction instrClone = (Instruction)instr.clone();
         instructionsCopy.add(instrClone);
       }
       copy.setInstructions(instructionsCopy);
     } 
      
      return copy;
  }  
 
   /**
   * This equals method only compares the Idseq to define equals
   * @param obj
   * @return 
   */ 
 public boolean equals(Object obj)
 {
   if(obj == null)
    return false;
   if(!(obj instanceof FormValidValue))
    return false;
   FormValidValue vv = (FormValidValue)obj;

   if(getLongName().equalsIgnoreCase(vv.getLongName()))
      return true;
    else
      return false;
 
 }  
  public String toString()
  {
    DebugStringBuffer sb = new DebugStringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR+"valueIdseq="+getValueIdseq(),getValueIdseq()); 
    sb.append(ATTR_SEPARATOR+"displayOrder="+getDisplayOrder()); 
    
    if(instructions!=null)
      sb.append(ATTR_SEPARATOR+"Instructions="+instructions);
    else
      sb.append(ATTR_SEPARATOR+"instructions=null");
      
    sb.append(OBJ_SEPARATOR_END);  
    return sb.toString();  
  }
}
