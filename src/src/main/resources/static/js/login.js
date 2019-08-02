function moveRegister() {
    location.href = "/register";
}

function moveLogin() {
    location.href = "/lgoin";
}

$('#username').keyup(function () {
    if ($(this).val().length < 4) {
        $("#login_id").text('아이디를 4자리 이상 입력하십시오.').css('color', 'red');
        $('#login_id').show();
    } else {
        $('#login_id').hide();
    }
});

$('#password').keyup(function () {
    if ($(this).val().length < 5) {
        $("#login_pw").text('비밀번호를 5자리 이상 입력하십시오.').css('color', 'red');
        $('#login_pw').show();
    } else {
        $('#login_pw').hide();
    }
});

var email_check = false;

function openCity(evt, cityName) {
    var i, x, tablinks;
    x = document.getElementsByClassName("city");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < x.length; i++) {
        tablinks[i].classList.remove("w3-light-grey");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.classList.add("w3-light-grey");
}

$('#forgot_id_email').keyup(function () {
    var input_email = $('#forgot_id_email').val();
    var re = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    if (re.test(input_email) == false) {
        $("#forgot_email").text('이메일 형식에 맞게 입력하십시오.').css('color', 'red');
        $('#forgot_email').show();
    } else if (input_email.length === 0) {
        $("#forgot_email").text('이메일을 반드시 입력해야 합니다.').css('color', 'red');
        $('#forgot_email').show();
    } else {
        $.ajax({
            url: "/login/emailcheck",
            type: "POST",
            data: $('#forgot_id_email').val(),
            contentType: "application/json",
            dataType: "text",
            success: function () {
                $('#forgot_email').text('존재하는 이메일 입니다. Send Email을 누르시길 바랍니다.').css('color', 'green');
                email_check = true;
            },
            error: function (parm) {
                $('#forgot_email').text(parm.responseText).css('color', 'red');
            }
        });
    }
});

$('#forgot_id_Btn').click(function () {
    if (email_check) {
        alert("메일을 전송중이니 잠시만 기다려주시길 바랍니다.");
        $.ajax({
            url: "/login/emailsendId",
            type: "POST",
            data: $('#forgot_id_email').val(),
            contentType: "application/json",
            dataType: "text",
            success: function () {
                alert("메일이 성공적으로 전송되었습니다.");
                location.reload();
            },
            error: function () {
                alert("메일 전송이 실패하였습니다.");
                location.reload();
            }
        });
    } else {
        alert("이메일을 다시 한 번 확인해주시길 바랍니다.");
    }
});

var id_pw_check = false;
var email_pw_check = false;

$('#forgot_pw_id').keyup(function () {
    var input_id = $('#forgot_pw_id').val();
    if (input_id.length > 4) {
        $.ajax({
            url: "/login/idcheck",
            type: "POST",
            data: $('#forgot_pw_id').val(),
            contentType: "application/json",
            dataType: "text",
            success: function () {
                $('#forgot_pw_id_vaild').text('존재하는 아이디 입니다.').css('color', 'green');
                id_pw_check = true;
            },
            error: function (parm) {
                $('#forgot_pw_id_vaild').text(parm.responseText).css('color', 'red');
            }
        });
    }
});

$('#forgot_pw_email').keyup(function () {
    var input_email = $('#forgot_pw_email').val();
    var re = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    if (re.test(input_email) == false) {
        $("#forgot_pw_email_vaild").text('이메일 형식에 맞게 입력하십시오.').css('color', 'red');
        $('#forgot_pw_email_vaild').show();
    } else if (input_email.length === 0) {
        $("#forgot_pw_email_vaild").text('이메일을 반드시 입력해야 합니다.').css('color', 'red');
        $('#forgot_pw_email_vaild').show();
    } else {
        $.ajax({
            url: "/login/emailcheck",
            type: "POST",
            data: $('#forgot_pw_email').val(),
            contentType: "application/json",
            dataType: "text",
            success: function () {
                $('#forgot_pw_email_vaild').text('존재하는 이메일 입니다. Send Email을 누르시길 바랍니다.').css('color', 'green');
                email_pw_check = true;
            },
            error: function (parm) {
                $('#forgot_pw_email_vaild').text(parm.responseText).css('color', 'red');
            }
        });
    }
});

$('#forgot_pw_email_Btn').click(function () {
    var jsonData = JSON.stringify({
        id: $('#forgot_pw_id').val(),
        email: $('#forgot_pw_email').val()
    });

    if (email_pw_check && id_pw_check) {
        alert("메일 전송을 위한, 아이디와 이메일 일치 여부를 확인을 위해 잠시만 기다려주시길 바랍니다.");
        $.ajax({
            url: "/login/forgot-password",
            type: "POST",
            data: jsonData,
            contentType: "application/json",
            dataType: "json",
            success: function () {
                alert("메일이 성공적으로 전송되었습니다.");
                location.reload();
            },
            error: function () {
                alert("메일 전송이 실패하였습니다.");
            }
        });
    } else {
        alert("이메일 또는 아이디가 존재하지 않습니다. 다시 한 번 확인해주시길 바랍니다.");
    }
});


var new_pw_vaild = false;
var new_pw_vaild_check = false;

$('#forgot_pw_new').keyup(function () {
    var regExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{5,22}$/;
    var password = $('#forgot_pw_new').val();

    if ($(this).val().length === 0) {
        $("#forgot_pw_new_vaild").text('비밀번호를 반드시 입력해야 합니다.').css('color', 'red');
        $('#forgot_pw_new_vaild').show();
    } else if (!regExp.test(password) || password.length > 22 || password.length < 5) {
        $('#forgot_pw_new_vaild').text('5~22의 영문 대소문자, 숫자, 특수문자로 사용해야합니다.').css('color', 'red');
        $('#forgot_pw_new_vaild').show();
    } else {
        $('#forgot_pw_new_vaild').text('사용가능한 비밀번호 입니다.').css('color', 'green');
        $('#forgot_pw_new_vaild').show();
        new_pw_vaild = true;
    }
});


$('#forgot_pw_new_check').keyup(function () {
    var new_pw = $('#forgot_pw_new').val();
    var new_pw_confirm = $('#forgot_pw_new_check').val();
    if ($(this).val().length === 0) {
        $("#forgot_pw_new_check_vaild").text('반드시 입력해야 합니다.').css('color', 'red');
        $('#forgot_pw_new_check_vaild').show();
    } else if (new_pw !== new_pw_confirm) {
        $('#forgot_pw_new_check_vaild').text('비밀번호가 일치하지 않습니다.').css('color', 'red');
        $('#forgot_pw_new_check_vaild').show();
    } else {
        $('#forgot_pw_new_check_vaild').text('비밀번호가 일치합니다.').css('color', 'green');
        $('#forgot_pw_new_check_vaild').show();
        new_pw_vaild_check = true;
    }
});

$('#forgot_pw_new_Btn').click(function () {
    var jsonData = JSON.stringify({
        password: $('#forgot_pw_new').val(),
        confirmPassword: $('#forgot_pw_new_check').val(),
        token: $('#token').val()
    });
    if (new_pw_vaild && new_pw_vaild_check) {
        $.ajax({
            url: "/login/reset-password",
            type: "POST",
            data: jsonData,
            contentType: "application/json",
            dataType: "json",
            success: function () {
                alert("비밀번호 재설정에 성공하였습니다.");
                location.href = '/login';
            },
            error: function (parm) {
                alert(parm.responseText);
            }
        });
    } else {
        alert("비밀번호 불일치 또는 사용불가능한 비밀번호이니, 다시 한 번 확인바랍니다. ")
    }
});