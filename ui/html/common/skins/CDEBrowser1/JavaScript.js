function findFrameByName(strName) {
    return findFrame(top, strName);
  }

function findFrame(doc, strName) {
   if (doc.frames.length == 0) return;

   for (var i = 0; i != doc.frames.length; i++)
     if (doc.frames[i].name == strName)
       return doc.frames[i];
     else {
       var frm = findFrame(doc.frames[i].window, strName);

       if ( frm  )
         return frm;
     }

   return top;
}
function performAction_not_used(urlParams){
  var frm = findFrameByName('body');
  document.body.style.cursor = "wait";
  frm.document.body.style.cursor = "wait";
  frm.document.location = "search?"+urlParams + "<%= callerParams %>";
}
function formSearchAction(urlParams){
    var frm = findFrameByName('body');
    document.body.style.cursor = "wait";
    frm.document.body.style.cursor = "wait";
    frm.document.location = "/CDEBrowser/formSearchAction.do?method=getAllFormsForTreeNode&"+urlParams;
}

function formDetailsAction(urlParams){
    top.document.location = "/CDEBrowser/formDetailsAction.do?method=getFormDetails&"+urlParams;
}

