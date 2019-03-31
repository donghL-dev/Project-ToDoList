
var content;
var days;
var id;
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$('.edit').click(function () {
    var name = $('.edit').attr('name');
    id = $(this).val();
    content = $(this).data("content");
    days = $(this).data("days");
    if(name === "edit") {
        $("."+id).replaceWith('<br><br><div id="new-li"><li style=""><input type="checkbox" class="checkbox1"><textarea class="mdl-textfield__input" id="new-content" onfocus="this.value = this.value;" onkeyup="this.style.height=\'26px\'; this.style.height = this.scrollHeight + \'px\';" style="width: 550px"></textarea><h5 id="days" style="margin-left: 40px"></h5>' +
            '<br><span style="float: left"><button onclick="move()"><i class="material-icons" style="margin-left: 50px">keyboard_backspacet</i></button><button class="edit" name="edit2" onclick="update()"><i class="material-icons" style="margin-left: 30px">edit</i></span></li></div><br>');
        $("#new-content").val(content);
        document.getElementById("days").innerHTML = days;
        $('.edit').attr("name","edit2");
    }
});

$('#insert').click(function () {
    var jsonData = JSON.stringify({
        description: $('#new-task').val(),
        status: false
    });
    $(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });
    $.ajax({
        url: "/todolist",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = "/todolist";
        },
        error: function (request) {
            alert(request.responseText);
        }
    });
});

$('.delete').click(function () {
    $(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });
    $.ajax({
        url: "/todolist/" + $(this).val(),
        type: "DELETE",
        success: function () {
            location.href = '/todolist';
        },
        error: function () {
            alert('삭제 실패!');
        }
    });
});

$('.checkbox1').change(function () {
    if($('.checkbox1').is(":checked")) {
        console.log("1111111111");
        var jsonData = JSON.stringify({
            status: true
        });
        $(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });
        $.ajax({
            url: "/todolist/status/" + $(this).val(),
            type: "PUT",
            data: jsonData,
            contentType: "application/json",
            dataType: "json",
            success: function () {
                location.href = '/todolist';
            },
            error: function () {
                alert('완료 실패!');
            }
        });
    }
});

$('.checkbox2').change(function () {
    var jsonData2 = JSON.stringify({
        status: false
    });
    $(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });
    $.ajax({
        url: "/todolist/status/" + $(this).val(),
        type: "PUT",
        data: jsonData2,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = '/todolist';
        },
        error: function () {
            alert('완료 실패!');
        }
    });
});

$('#register').click(function () {
    var jsonData = JSON.stringify({
        id: $('#username').val(),
        email: $('#email').val(),
        password: $('#password').val()
    });
    $(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });
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
});

function update() {
    $('.edit').attr("name","edit");
    var jsonData = JSON.stringify({
        description: $('#new-content').val()
    });
    $(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });
    $.ajax({
        url: "/todolist/" + id,
        type: "PUT",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = '/todolist';
        },
        error: function () {
            alert('수정 실패!');
        }
    });
}

function move() {
    location.href = "/todolist";
}

function resize(obj) {
    console.log("AAAAAA");
    obj.style.height = "1px";
    obj.style.height = (12+obj.scrollHeight)+"px";
}

$(".mdl-textfield__input").on('keydown keyup', function () {
    $(this).height(1).height( $(this).prop('scrollHeight')+12 );
});