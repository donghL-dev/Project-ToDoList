var email_check = false;
var id_check = false;

$('#username').blur(function () {
    var username = $('#username').val();
    var regExp = /^[a-z0-9]{4,12}$/g;

    if (username.length === 0) {
        $('#user_id').text('아이디를 반드시 입력해야 합니다.').css('color', 'red');
    } else if (!regExp.test(username) || username.length < 4 || username.length > 12) {
        $('#user_id').text('4~12의 영문 소문자, 숫자로 사용 해야합니다.').css('color', 'red');
    } else {
        $.ajax({
            url: "/register/idcheck",
            type: "POST",
            data: $('#username').val(),
            contentType: "application/json",
            dataType: "text",
            success: function (parm) {
                $('#user_id').text(parm).css('color', 'green');
                id_check = true;
            },
            error: function (parm) {
                $('#user_id').text(parm.responseText).css('color', 'red');
                id_check = false;
            }
        });
    }
});

$('#password').blur(function () {

    var regExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{5,22}$/;
    var password = $('#password').val();

    if ($(this).val().length === 0) {
        $("#user_pw").text('비밀번호를 반드시 입력해야 합니다.').css('color', 'red');
        $('#user_pw').show();
    } else if (!regExp.test(password) || password.length > 22 || password.length < 5) {
        $('#user_pw').text('5~22의 영문 대소문자, 숫자, 특수문자로 사용해야합니다.').css('color', 'red');
        $('#user_pw').show();
    } else {
        $('#user_pw').hide();
    }
});

$('#email').keyup(function () {
    var input_email = $('#email').val();
    var re = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    if (re.test(input_email) == false) {
        $("#user_email").text('이메일 형식에 맞게 입력하십시오.').css('color', 'red');
        $('#user_email').show();
    } else if (input_email.length === 0) {
        $("#user_email").text('이메일을 반드시 입력해야 합니다.').css('color', 'red');
        $('#user_email').show();
    } else {
        $.ajax({
            url: "/register/emailcheck",
            type: "POST",
            data: $('#email').val(),
            contentType: "application/json",
            dataType: "text",
            success: function (parm) {
                $('#user_email').text(parm).css('color', 'green');
                email_check = true;
            },
            error: function (parm) {
                $('#user_email').text(parm.responseText).css('color', 'red');
                email_check = false;
            }
        });
    }
});


$('#register').click(function () {
    var jsonData = JSON.stringify({
        id: $('#username').val(),
        email: $('#email').val(),
        password: $('#password').val()
    });

    if (id_check && email_check) {
        $.ajax({
            url: "/register",
            type: "POST",
            data: jsonData,
            contentType: "application/json",
            dataType: "json",
            success: function () {
                alert('가입 성공');
                location.href = '/login';
            },
            error: function (request) {
                alert(request.responseText);
            }
        });
    } else {
        alert("양식을 입력해 주세요.");
    }
});