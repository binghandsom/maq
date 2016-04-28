$(function() {
	var regMethod = "phone";
	$("#whichWayReg button").click(function() {

		$("form#regForm input.form-control").val("");
		$("form#regForm label.mess").text("");

		var whichWay = $(this).html();

		if ("手机" == whichWay) {
			$("#inputEmail").css({
				"display" : "none"
			});
			$("#inputPhone").css({
				"display" : ""
			});
			regMethod = "phone";
		} else {
			$("#inputPhone").css({
				"display" : "none"
			});
			$("#inputEmail").css({
				"display" : ""
			});
			regMethod = "email";
		}
	});

	var messageAccount = $("label.messAccount");

	var regPhone = /^(1)\d{10}$/
	var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,9})$/;
	var phone = $("#inputPhone");
	var email = $("#inputEmail");
	var infoAccountOk = false;
	var infoPassOk = false;
	var infoRepassOk = false;
	$("input.regAccount").blur(function() {
		if (regMethod == "phone") {
			// 手机注册
			var phoneVal = phone.val().trim();
			if (regPhone.test(phoneVal)) {
				$(this).css({
					"background-color" : "#D6D6FF"
				});
				messageAccount.html("");
				infoAccountOk = true;
			} else {
				messageAccount.html("手机号码格式不正确");
				infoAccountOk = false;
			}
		} else {
			// 邮箱注册
			var emailVal = email.val();
			if (regEmail.test(emailVal)) {
				$(this).css({
					"background-color" : "#D6D6FF"
				});
				messageAccount.html("");
				infoAccountOk = true;
			} else {
				messageAccount.html("邮箱格式不正确");
				infoAccountOk = false;
			}
		}

	});
	var messagePass = $("label.messPass");
	var messRepass = $("label.messRepass");

	var regPass = new RegExp(
			"(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^0-9a-zA-Z]).{6,40}");
	var password = "";
	$("#inputPassword").keyup(function() {
		var tempPass = $(this).val();
		infoPassOk = false;
		if (tempPass.length < 6) {
			messagePass.html("密码长度必须大于6");
			messagePass.css({
				"color" : "red"
			});
			$(this).css({
				"background-color" : ""
			});
		} else if (tempPass.length > 40) {
			messagePass.css({
				"color" : "red"
			});
			$(this).css({
				"background-color" : ""
			});
			messagePass.html("密码长度过长");
		} else if (regPass.test(tempPass)) {
			messagePass.html("密码满足要求");
			password = $(this).val();
			$(this).css({
				"background-color" : "#D6D6FF"
			});
			messagePass.css({
				"color" : "yellow"
			});
			infoPassOk = true;
		} else {
			messagePass.css({
				"color" : "red"
			});
			$(this).css({
				"background-color" : ""
			});
			messagePass.html("密码必须包含数字，英文，特殊符号");
		}
	});
	$("#rePassword").keyup(function() {
		infoRepassOk = false;
		if ($(this).val() == password) {
			infoRepassOk = true;
			messRepass.html("");
		} else {
			messRepass.html("两次输入密码不一致");
		}

	});

	$("#loginForm").bind('keypress', function(event) {
		if (event.keyCode == "13") {
			login();
		}
	});
	// 登录方法
	$("#login").click(function() {
		login();
	});

	// 注册验证码，验证码发送至手机或邮箱
	$("#getValidateCode").click(function() {
		if (regMethod == "phone") {
			if (infoAccountOk) {
				var phoneNum = phone.val();
				alert(phoneNum);
				$.ajax({
					url : ctx+"/userAccount/sendRegValidateCode",
					data : "phoneNum=" + phoneNum,
					type : 'POST',
					async : false
				});
			}

		} else {

		}

	});

});
login = function() {
	// ?email=143%40qq.com&password=111111
	var data = "email=" + $("#loginForm #email").val() + "&password="
			+ $("#loginForm #loginPassword").val();
	$.ajax({
		type : 'POST',
		async : false,
		url : ctx + "/userAccount/loginCheck",
		data : data,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(result) {
			// alert(JSON.stringify(result));
			// alert(result.success);
			if (result.success) {
				window.location.href = "xxx";
			} else {
				$("#loginError").html("账号不存在或密码错误！");
			}

		}
	});
}
