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


function validateMultiSelection(chkName,msg){
  dml=document.forms[0];
  len = dml.elements.length;
  var i=0;
  var numberselected=0;
  for( i=0 ; i<len ; i++) {
   if ((dml.elements[i].name==chkName) && (dml.elements[i].checked==1)) 
    numberselected ++;
   }
   if (numberselected <= 1)
     alert(msg);
   return numberselected;
}

function getNumberOfSelectedItems(chkName){
  dml=document.forms[0];
  len = dml.elements.length;
  var i=0;
  var numberselected=0;
  for( i=0 ; i<len ; i++) {
   if ((dml.elements[i].name==chkName) && (dml.elements[i].checked==1)) 
    numberselected ++;
   }

   return numberselected;
}

function getValueOfSelectedItem(chkName){
  dml=document.forms[0];
  len = dml.elements.length;
  var i=0;
  var value=0;
  for( i=0 ; i<len ; i++) {
   if ((dml.elements[i].name==chkName) && (dml.elements[i].checked==1)) 
    value = dml.elements[i].value;
   }

   return value;
}