var goToViewPaper = function(selectedPaperDTO) {
  var paperUid = selectedPaperDTO.children.paperUid.innerHTML;
  $("#selectedPaperUid").val(paperUid);

  var form = document.paperWaitListForm;
  form.action = "/cocotask/eapp/paper/view";
  form.method = "post";
  form.submit();
};