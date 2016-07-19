function validateLogin() {
    var login = document.getElementById("login").value;
    //var loginMessage = document.getElementById("emptyLogin");
    var isValid = true;

    if (login.length < 5) {
        alert("Should contain at least 5 characters");
        isValid = false;
    }
    return isValid;
}
