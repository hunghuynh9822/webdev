console.log('Validate JavaScript');

	function validateForm() {
		if (validateFullname() && validateUsername() && validateEmail()
				&& validatePassword() && validateComfirm() && validatePhone() && searchViaAjax()) {
			return true;
		}
		return false;
	}
	function validateFullname() 
	{
		var fullName = document.getElementById("fullname").value;
		if (fullName == "") {
			document.getElementById("err-fullname").innerHTML = "Not allow to null";
			return false;
		} 
		else {
			document.getElementById("err-fullname").innerHTML = "";
			return true;
		}
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
	function validateUsername() {
		var userName = document.getElementById("username").value;
		if (userName == "") {
			document.getElementById("err-username").innerHTML = "Not allow to null";
			return false;
		} else {
			document.getElementById("err-username").innerHTML = "";
			return true;
		}
	}
	
	function searchViaAjax() {
		var token = $('#csrfToken').val();
		var header = $("meta[name='_csrf_header']").attr("content");
		
		var search = {}
		search["username"] = $("#username").val();
		search["email"] = $("#email").val();

		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/webdev/search/api/getSearchResult",
			data : JSON.stringify(search),
			dataType : 'json',
			timeout : 100000,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("accept", "application/json");
				xhr.setRequestHeader("contentType", "application/json");
				xhr.setRequestHeader(header, token);
			},
			
			success : function(data) {
				console.log("SUCCESS: ", data);
				if(data.result != null){
					displayMessage(data);
					return false;
				}
				return true;
				
			},
			error : function(e) {
				console.log("ERROR: ", e);
				return false;
			},
			done : function(e) {
				console.log("DONE");
			}
		});

	}
	function displayMessage(data) {
		var message;
		if(data.code == '200'){
			message = "User has existed";	
		}else{
			message = "";			
		}
		$('#err-username').html(message);
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
	function validatePhone()
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
	