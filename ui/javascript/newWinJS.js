function newWin(url,windowName,nwidth,nheight)
{
  if (nwidth == null) {
    nwidth = 440;
  }

  if (nheight == null) {
    nheight = 300;
  }

  screenx = (screen.availWidth - nwidth) / 2;
  screeny =  (screen.availHeight - nheight) / 2;
  var hWnd = window.open(url,windowName,"toolbar=no,width="+nwidth+",height="+nheight+",screenx="+screenx+",screeny="+screeny+",resizable=yes,scrollbars=yes,menubar=no,directories=no");
  hWnd.focus();
}

function newBrowserWin(url,windowName,nwidth,nheight)
{
  if (nwidth == null) {
    nwidth = 440;
  }

  if (nheight == null) {
    nheight = 300;
  }

  screenx = (screen.availWidth - nwidth) / 2;
  screeny =  (screen.availHeight - nheight) / 2;
  var hWnd = window.open(url,windowName,"toolbar=yes,width="+nwidth+",height="+nheight+",screenx="+screenx+",screeny="+screeny+",resizable=yes,scrollbars=yes,menubar=yes,directories=yes, location=yes");
  hWnd.focus();
}
function newDownloadWin(url,windowName,nwidth,nheight)
{
  if (nwidth == null) {
    nwidth = 440;
  }

  if (nheight == null) {
    nheight = 300;
  }

  screenx = (screen.availWidth - nwidth) / 2;
  screeny =  (screen.availHeight - nheight) / 2;
  var hWnd = window.open(url,windowName,"toolbar=no,width="+nwidth+",height="+nheight+",screenx=0"+",screeny=0"+",resizable=no,scrollbars=no,menubar=no,directories=no");
}
function fileDownloadWin(url,windowName,nwidth,nheight)
{
  if (nwidth == null) {
    nwidth = 440;
  }

  if (nheight == null) {
    nheight = 300;
  }

  screenx = (screen.availWidth - nwidth) / 2;
  screeny =  (screen.availHeight - nheight) / 2;
  var hWnd = window.open(url,windowName,"toolbar=yes,width="+nwidth+",height="+nheight+",screenx="+screenx+",screeny="+screeny+",resizable=no,scrollbars=no,menubar=yes,directories=no, location=no");
  hWnd.focus();
}

