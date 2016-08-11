function validateLogin() {
    var login = document.getElementById("login").value;
    var loginMessage = document.getElementById("loginMessage");
    var isValid = true;

    if (login.length < 5) {
        loginMessage.innerHTML = "Should contain at least 5 characters";
        isValid = false;
    }
    return isValid;
}
