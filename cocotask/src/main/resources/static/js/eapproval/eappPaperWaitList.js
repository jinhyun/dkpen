var goToViewPaper = function(paperUid) {
  var url, method, params;

  url = "/cocotask/eapp/paper/view";
  method = "post";
  params = {
    paperUid: paperUid,
    moduleName: "view",   // TODO: moduleName을 다른 방법으로 변경 (너무 복잡함)
    windowType: ""
  };

  post(url, params, method);
};

function post(path, params, method) {
  method = method || "post";

  var form = document.createElement("form");
  form.setAttribute("method", method);
  form.setAttribute("action", path);

  for(var key in params) {
    if(params.hasOwnProperty(key)) {
      var hiddenField = document.createElement("input");
      hiddenField.setAttribute("type", "hidden");
      hiddenField.setAttribute("name", key);
      hiddenField.setAttribute("value", params[key]);

      form.appendChild(hiddenField);
    }
  }

  document.body.appendChild(form);
  form.submit();
};