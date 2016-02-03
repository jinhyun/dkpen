var eappPaperCreateInit = function() {
  $("#registerPaper").click(function() {
    var form = document.paperForm;
    form.action = "/cocotask/eapp/paper/create";
    form.submit();
  });
};

/*var setEditorContents = function() {
  var data = CKEDITOR.instances.editor1.getData();
};*/

var searchEappLiner = function() {
  var url = "/cocotask/eapp/line/userList";
  $("#resultUserList").load(url);

  $("#userListTable").rowSorter({handler:"td.sorter"});
};

var addEappLine = function(chooseLiner) {
  var eappLineData = chooseLiner.cloneNode(true);
  eappLineData.removeAttribute("onclick");

  $("#eappLineTbody").append(eappLineData);
  $("#eappLineTable").rowSorter({handler:"td.sorter"});
};

var setEappLine = function() {
  var eappLineTbody = $("#eappLineTbody");

  var dataSize = eappLineTbody.children().size();
  for (var i=0; i<dataSize; i++){
    var userUid = eappLineTbody.children()[i].children.userUid.innerText;
    var userName = eappLineTbody.children()[i].children.userName.innerText;

    $("#eappLine_" + i).val(userUid);
    $("#eappLineUserName_" + i).val(userName);

    var lineType = eappLineType(i, dataSize);
    $("#eappLineType_" + i).text(lineType);
  }

  $("#eappLineModal").modal('hide');
};

// Temp Function
var eappLineType = function(userOrder, userLastOrder) {
  var lineType;

  switch (userOrder / (userLastOrder-1)) {
    case 0:
      lineType = "기안";
      break;
    case 1:
      lineType = "결재";
      break;
    default :
      lineType = "검토";
      break;
  }

  return lineType;
};

var setEappLineType = function() {
  var lineList = $('[name="lineList"]');
  var dataSize = lineList.length;

  for (var i = 0; i < dataSize; i++) {
    var lineOrder = $("#lineOrder_" + i).val();
    var lineType = eappLineType(lineOrder, dataSize);
    $("#eappLineType_" + i).text(lineType);
  }
};

var fnPaperFormInit = function() {
  setEappLineType();
  setIfArchiveModule();

  // editor
  editorInit();
  editorReadOnly();
};

var setIfArchiveModule = function() {
  var moduleName = $("#moduleName").val();

  if (moduleName == "archive") {
    $("#eappApproveModalBtn").remove();
  }
};