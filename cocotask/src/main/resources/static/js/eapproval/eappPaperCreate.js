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
