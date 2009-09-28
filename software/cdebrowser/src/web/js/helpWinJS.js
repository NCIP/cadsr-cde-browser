<!--
function helpWin(url,nwidth,nheight)
{
  if (nwidth == null) {
    nwidth = 440;
  }

  if (nheight == null) {
    nheight = 300;
  }

  screenx = (screen.availWidth - nwidth) / 2;
  screeny =  (screen.availHeight - nheight) / 2;
  var hWnd = window.open(url,"winLOV","toolbar=no,width="+nwidth+",height="+nheight+",screenx="+screenx+",screeny="+screeny+",resizable=no,scrollbars=no,menubar=no,directories=no");
  hWnd.focus();
}
//-->
