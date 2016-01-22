var doApprove = function(approveStatus) {
  switch (approveStatus) {
    case 'done':
      var form = document.paperForm;
      $("#approveStatus").val(approveStatus);

      form.action = "/cocotask/eapp/paper/approve";
      form.method = "post";
      form.submit();

      break;

    case 'hold':
      break;

    default :
      break;
  }
};