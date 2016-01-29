var loginInit = function() {
  setDevLogin();
};

var setDevLogin = function() {
  $("#user_anna").click(function() {
    setDevLoginData("anna@dkpen.com", "1234");
  });

  $("#user_elsa").click(function() {
    setDevLoginData("elsa@dkpen.com", "1234");
  });

  $("#user_olaf").click(function() {
    setDevLoginData("olaf@dkpen.com", "1234");
  });

  $("#user_kristoff").click(function() {
    setDevLoginData("kristoff@dkpen.com", "1234");
  });
};

var setDevLoginData = function(username, password) {
  $("#username").val(username);
  $("#password").val(password);

  document.loginForm.submit();
};
