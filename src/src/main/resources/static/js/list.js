$('.todocoet').click(function () {
    var status = $(this).val();
    if (status) {
        var completeComment = $(this).parent().parent().find('#completeComment');
        completeComment.slideToggle('slow');
    } else {
        var todocomment = $(this).parent().parent().find('#todoComment');
        todocomment.slideToggle('slow');
    }

});

function openNav() {
    document.getElementById("mySidenav").style.width = "15%";
    document.getElementById("main").style.marginLeft = "15%";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
}

$('.edit').click(function () {

    $('#todo_description' + $(this).val()).attr('contenteditable', true);
    $('#todo_description' + $(this).val()).trigger('focus');

    var delete_button = $(this).parent().find('.delete');
    var checkbox_btn = $(this).parent().find('.checkbox1');
    var comment_btn = $(this).parent().find('.todocoet');
    var edit_btn_icon = $(this).children('.material-icons');

    delete_button.css('visibility', 'hidden');
    checkbox_btn.css('visibility', 'hidden');
    comment_btn.css('visibility', 'hidden');
    edit_btn_icon.text('done');

    $('.edit').click(function () {

        var jsonData = JSON.stringify({
            description: $('#todo_description' + $(this).val()).text()
        });

        $.ajax({
            url: "/todolist/" + $(this).val(),
            type: "PUT",
            data: jsonData,
            contentType: "application/json",
            dataType: "json",
            success: function () {
                location.reload();
            },
            error: function (request) {
                alert(request.responseText);
                location.reload();
            }
        });
        $('#todo_description' + $(this).val()).attr('contenteditable', false);
    });
});


$('#insert').click(function () {
    var jsonData = JSON.stringify({
        description: $('#new-task').val(),
        status: false
    });
    $.ajax({
        url: "/todolist",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.reload();
        },
        error: function (request) {
            alert(request.responseText);
        }
    });
});

$('.delete').click(function () {
    $.ajax({
        url: "/todolist/" + $(this).val(),
        type: "DELETE",
        success: function () {
            location.reload();
        },
        error: function () {
            alert('삭제 실패!');
        }
    });
});

$('.checkbox1').click(function () {
    $.ajax({
        url: "/todolist/status/" + $(this).val(),
        type: "PUT",
        contentType: "application/json",
        success: function () {
            location.reload();
        },
        error: function () {
            alert('완료 실패!');
        }
    });
});

$('.checkbox2').click(function () {
    $.ajax({
        url: "/todolist/status/" + $(this).val(),
        type: "PUT",
        contentType: "application/json",
        success: function () {
            location.reload();
        },
        error: function () {
            alert('완료 실패!');
        }
    });
});

function resize(obj) {
    obj.style.height = "1px";
    obj.style.height = (12 + obj.scrollHeight) + "px";
}

$(".mdl-textfield__input").on('keydown keyup', function () {
    $(this).height(1).height($(this).prop('scrollHeight') + 12);
});