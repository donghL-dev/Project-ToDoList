
$('.todocoet').click(function () {
    var status = $(this).val();
    if(status) {
        var completeComment = $(this).parent().parent().parent().find('#completeComment');
        completeComment.slideToggle('slow');
    }
    else {
        var todocomment = $(this).parent().parent().parent().find('#todoComment');
        todocomment.slideToggle('slow');
    }

});

$('.edit').click(function () {

    $('#todo_description' + $(this).val()).attr('contenteditable', true);
    $('#todo_description' + $(this).val()).trigger('focus');

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
            error: function () {
                alert('수정 실패!');
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
    var jsonData = JSON.stringify({
            status: true
    });
    $.ajax({
        url: "/todolist/status/" + $(this).val(),
        type: "PUT",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.reload();
        },
        error: function () {
            alert('완료 실패!');
        }
    });
});

$('.checkbox2').click(function () {
    var jsonData2 = JSON.stringify({
        status: false
    });
    $.ajax({
        url: "/todolist/status/" + $(this).val(),
        type: "PUT",
        data: jsonData2,
        contentType: "application/json",
        dataType: "json",
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
    obj.style.height = (12+obj.scrollHeight)+"px";
}

$(".mdl-textfield__input").on('keydown keyup', function () {
    $(this).height(1).height( $(this).prop('scrollHeight')+12 );
});