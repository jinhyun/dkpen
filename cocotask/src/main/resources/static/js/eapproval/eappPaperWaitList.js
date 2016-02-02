var goToViewPaper = function(selectedPaperDTO) {
  var paperUid, parameter, moduleName;
  paperUid = selectedPaperDTO.children.paperUid.innerHTML;
  moduleName = $("#moduleName").val();

  $("#selectedPaperUid").val(paperUid);

  // TODO: moduleName을 다른 방법으로 변경 (너무 복잡함)
  if (typeof moduleName == "undefined") {
    parameter = "?moduleName=view";
  } else {
    parameter = "?moduleName=" + moduleName;
  }

  var form = document.paperWaitListForm;
  form.action = "/cocotask/eapp/paper/view" + parameter;
  form.method = "post";
  form.submit();
};