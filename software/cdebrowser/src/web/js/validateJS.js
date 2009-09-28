function JSLNotNull(pctl, pmsg){
   if (pctl.value == "") { alert(pmsg); pctl.focus(); return false; }
   return true;
}