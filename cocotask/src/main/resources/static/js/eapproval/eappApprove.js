var doApprove = function(approveStatus) {
  var form = document.paperForm;
  $("#approveStatus").val(approveStatus);

  form.action = "/cocotask/eapp/paper/approve";
  form.method = "post";
  form.submit();
};