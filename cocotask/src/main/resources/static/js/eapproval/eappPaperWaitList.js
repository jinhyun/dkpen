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

var goPageNumber = function(pageNumber) {
  var url, method, params;

  url = "/cocotask/eapp/paper/waitList";
  method = "get";
  params = {
    pageNumber: pageNumber
  };

  post(url, params, method);
};

var createPagination = function() {
  var selectedPageNumber, totalPages, startPages, showPages;

  showPages = 5;
  selectedPageNumber = $("#selectedPageNumber").val();
  totalPages = $("#totalPages").val();
  startPages = (selectedPageNumber - (selectedPageNumber % 5)) + 1;

  if (showPages > totalPages) {
    showPages = totalPages;
  }

  for (var i = 0; i < showPages; i++) {
    var liNode = document.createElement("li");
    liNode.className = "page-item";

    var aNode = document.createElement("a");
    var aTextNode = document.createTextNode(startPages);
    aNode.className = "page-link";
    aNode.href = "javascript:void(0);";
    aNode.onclick = function(test) {
      goPageNumber(this.innerText);
    };
    aNode.appendChild(aTextNode);

    liNode.appendChild(aNode);

    $(liNode).insertBefore($("#pageItemLast"));
    startPages++;
  }
};