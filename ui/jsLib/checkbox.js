function validateSelection(chkName,msg){
  dml=document.forms[0];
  len = dml.elements.length;
  var i=0;
  for( i=0 ; i<len ; i++) {
   if ((dml.elements[i].name==chkName) && (dml.elements[i].checked==1)) 
    return true
   }
   alert(msg)
   return false;
}

function setChecked(val,chkName) {
  dml=document.forms[0];
  len = dml.elements.length; 
  var i=0;
  for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name==chkName) {
    dml.elements[i].checked=val;
   }
  }
}

