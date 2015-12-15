var searchEappLiner = function() {
  var url = "/cocotask/eapp/line/userList";
  $("#resultUserList").load(url);
};

var addEappLine = function(chooseLiner) {
  var eappLineData = chooseLiner.cloneNode(true);
  eappLineData.removeAttribute("onclick");

  $("#eappLineTbody").append(eappLineData);
};

var setEappLine = function() {
  var eappLineTbody = $("#eappLineTbody");

  var dataSize = eappLineTbody.children().size();
  for (var i=0; i<dataSize; i++){
    var userUid = eappLineTbody.children()[i].children.userUid.innerText;

    $("#eappLine_" + i).val(userUid);
  }

  $("#eappLineModal").modal('hide');
};
