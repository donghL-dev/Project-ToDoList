var email_check = false;
var id_check = false;

$('#username').blur(function () {
    var username = $('#username').val();
    if (username.length < 4){
        $('#user_id').text('아이디는 4자리 이상 기입헤야 합니다.').css('color', 'red');
    } else{
        $.ajax({
            url: "/register/idcheck",
            type: "POST",
            data: $('#username').val(),
            contentType: "application/json",
            dataType: "text",
            success: function () {
                $('#user_id').text('사용가능한 아이디 입니다.').css('color', 'green');
                id_check = true;
            },
            error: function () {
                $('#user_id').text('이미 사용중인 아이디 입니다.').css('color', 'red');
                id_check  = false;
            }
        });
    }
});

$('#password').blur(function () {
    if($(this).val().length < 5) {
        $("#user_pw").text('비밀번호를 5자리 이상 입력하십시오.').css('color', 'red');
        $('#user_pw').show();
    }
    else {
        $('#user_pw').hide();
    }
});

$('#email').keyup(function () {
    var input_email = $('#email').val();
    var re = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    if(re.test(input_email) == false) {
        $("#user_email").text('이메일 형식에 맞게 입력하십시오.').css('color', 'red');
        $('#user_email').show();
    }
    else {
        $.ajax({
            url: "/register/emailcheck",
            type: "POST",
            data: $('#email').val(),
            contentType: "application/json",
            dataType: "text",
            success: function () {
                $('#user_email').text('멋진 이메일입니다.').css('color', 'green');
                email_check = true;
            },
            error: function () {
                $('#user_email').text('이미 사용중인 이메일입니다.').css('color', 'red');
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