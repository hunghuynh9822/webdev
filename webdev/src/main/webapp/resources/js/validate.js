console.log('Validate JavaScript');
function validateForm() {
	if (validateFullName() && validateUserName() && validateEmail()
			&& validatePassword() && validateComfirm() && validatePhone()) {
		return true;
	}
	return false;
}
function validateEmail() {
	var email = document.getElementById("email").value;
	if (!fomatEmail(email)) {
		document.getElementById("err-email").innerHTML = "Wrong fomat email";
		return false;
	} else {
		document.getElementById("err-email").innerHTML = "";
		return true;
	}
}
function validateFullName() {
	var fullName = document.getElementById("fullname").value;
	if (fullName == "") {
		document.getElementById("err-fullname").innerHTML = "Not allow to null";
		return false;
	} else {
		document.getElementById("err-fullname").innerHTML = "";
		return true;
	}
}
function validateUserName() {
	var userName = document.getElementById("username").value;
	if (userName == "") {
		document.getElementById("err-username").innerHTML = "Not allow to null";
		return false;
	} else {
		document.getElementById("err-username").innerHTML = "";
		return true;
	}
}

function fomatEmail(email) {
	var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(String(email).toLowerCase());
}
function fomatPhone(phone) {
	var re = /^\(?([0-9]{4})\)?[-. ]{0,1}?([0-9]{2,3})[-. ]{0,1}?([0-9]{3,4})$/;
	return re.test(String(phone));
}
function validatePassword() {
	var pass = document.getElementById("password").value;
	if (pass.length < 8) {
		document.getElementById("err-password").innerHTML = "Password must be least 8 or more characters";
		return false;
	} else {
		document.getElementById("err-password").innerHTML = "";
		return true;
	}
}
function validateComfirm() {
	var pass = document.getElementById("password").value;
	var passComfirm = document.getElementById("comfirmPassword").value;
	if (passComfirm != pass) {
		document.getElementById("err-comfirm").innerHTML = "Wrong comfirm password";
		return false;
	} else {
		document.getElementById("err-comfirm").innerHTML = "";
		return true;
	}
}
function validatePhone() {
	{
		var phone = document.getElementById("phone").value;
		if (!fomatPhone(phone)) {
			document.getElementById("err-phone").innerHTML = "Wrong fomat phone";
			return false;
		} else {
			document.getElementById("err-phone").innerHTML = "";
			return true;
		}
	}

}
