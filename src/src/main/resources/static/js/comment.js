
function Comment (commentDTO) {
    this.idx = commentDTO['commentIdx'];
    this.content = commentDTO['content'];
    this.dateTime = commentDTO['dateTime'];
    this.modifyTime = commentDTO['modifyTime'];

    this.li_style_tag = function() {
        var li = document.createElement( 'li' );
        li.style.marginBottom = "1%";
        li.id = "comment_li" + this.idx;

        var h4 = document.createElement( 'h4' );
        h4.style.width = "500px";
        h4.style.padding = "10px 13px 10px";
        h4.id = "comment_content";
        h4.contentEditable = false;

        var comment_content = document.createTextNode(commentDTO['content']);
        h4.appendChild(comment_content);
        li.appendChild( h4 );

        var h5 = document.createElement('h5');
        h5.style.cssFloat = "right";
        h5.className = "comment_days";

        var comment_days = document.createTextNode(commentDTO['dateTime'].toString().substring(0, 10));
        h5.appendChild(comment_days);
        li.appendChild(h5);

        var div = document.createElement('div');
        div.style.cssFloat = "right";
        div.style.marginBottom ="4px";

        var button1 = document.createElement('button');
        button1.style.width = "35px";
        button1.className = "checkbox3";
        button1.value = JSON.stringify(commentDTO['commentIdx']);

        var i1 = document.createElement('i');
        i1.style.marginBottom = "20px";
        i1.className = "material-icons";
        i1.appendChild(document.createTextNode("check_box_outline_blank"));

        var button2 = document.createElement('button');
        button2.style.width ="35px";
        button2.className = "comment_delete";
        button2.value = JSON.stringify(commentDTO['commentIdx']);

        var i2 = document.createElement('i');
        i2.style.marginBottom = "20px";
        i2.className = "material-icons";
        i2.appendChild(document.createTextNode("delete"));

        var button3 = document.createElement('button');
        button3.style.width = "35px";
        button3.className = "comment_edit";
        button3.value = JSON.stringify(commentDTO['commentIdx']);

        var i3 = document.createElement('i');
        i3.style.marginBottom = "20px";
        i3.className = "material-icons";
        i3.appendChild(document.createTextNode("edit"));

        button1.appendChild(i1);
        div.appendChild(button1);

        button2.appendChild(i2);
        div.appendChild(button2);

        button3.appendChild(i3);
        div.appendChild(button3);

        li.appendChild(div);

        return li;
    };
}

$('.comment_insert').click(function () {
    var id = $(this).parent().find('.mdl-textfield__input');
    var comment_ul = $(this).parent().parent().parent().find('#comment_ul');
    var jsonData = JSON.stringify({
        content: $('#new-commnet' + $(this).val()).val(),
        todolistIdx: $(this).val(),
        status : false
    });
    $(id).val('');
    $.ajax({
        url: "/comment",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function (commentDTO) {
            var comment_object = new Comment(commentDTO);
            comment_ul.append(comment_object.li_style_tag());
        },
        error: function (request) {
            alert(request.responseText);
        }
    });
});

$(document).on("click",".comment_edit",function() {
    var comment_content = $(this).parent().parent().find('#comment_content');
    var comment_days = $(this).parent().parent().find('#comment_days');
    var comment_delete_btn = $(this).parent().find('.comment_delete');
    var comment_checkbox_btn = $(this).parent().find('.checkbox3');
    var date = new Date();

    comment_days.text(date.getFullYear() + "-" + ( date.getMonth() + 1 > 9 ? date.getMonth() + 1 : "0" + (date.getMonth() + 1)  ) + "-" + date.getDate());

    comment_content.attr('contenteditable', true);
    comment_content.trigger('focus');
    comment_delete_btn.css('visibility', 'hidden');
    comment_checkbox_btn.css('visibility', 'hidden');

    var editBtn = $(this);

    var button = document.createElement('button');
    button.style.width = "35px";
    button.className = "comment_edit2";
    button.value = $(this).val();

    var i = document.createElement('i');
    i.style.marginBottom = "20px";
    i.className = "material-icons";
    i.appendChild(document.createTextNode('done'));

    button.appendChild(i);

    editBtn.replaceWith(button);

    $(document).on("click", ".comment_edit2", function() {
        var editBtn = $(this);

        var button = document.createElement('button');
        button.style.width = "35px";
        button.className = "comment_edit";
        button.value = $(this).val();

        var i = document.createElement('i');
        i.style.marginBottom = "20px";
        i.className = "material-icons";
        i.appendChild(document.createTextNode("edit"));

        button.appendChild(i);

        editBtn.replaceWith(button);

        $.ajax({
            url: "/comment/" + $(this).val(),
            type: "PUT",
            data: comment_content.text(),
            contentType: "application/json",
            dataType: "text",
            success: function () {
                console.log('수정 완료');
            },
            error: function () {
                alert('수정 실패!');
            }
        });
        comment_delete_btn.css('visibility', 'visible');
        comment_checkbox_btn.css('visibility', 'visible');
        comment_content.attr('contenteditable', false);
    });
});

$(document).on("click",".comment_delete",function() {
    var comment_li = $(this).parent().parent().parent().find('#comment_li'+$(this).val());
    $.ajax({
        url: "/comment/" + $(this).val(),
        type: "DELETE",
        success: function () {
            comment_li.replaceWith('');
        },
        error: function () {
            alert('삭제 실패!');
        }
    });
});

$(document).on("click",".checkbox3",function() {
    var editbtn = $(this).parent().find('.comment_edit');
    var checkboxbtn = $(this);
    var comment_content = $(this).parent().parent().find('#comment_content');

    var button = document.createElement('button');
    button.style.width = "35px";
    button.className = "checkbox4";
    button.value = $(this).val();

    var i = document.createElement('i');
    i.style.marginBottom = "20px";
    i.className = "material-icons";
    i.appendChild(document.createTextNode("check_box"));

    button.appendChild(i);

    var comment_ul = $(this).parent().parent().parent().parent().find('#comment_ul');

    $.ajax({
        url: "/comment/status/" + $(this).val(),
        type: "PUT",
        contentType: "application/json",
        success: function () {
            editbtn.css('display', 'none');
            checkboxbtn.replaceWith(button);
            comment_content.css('text-decoration', 'line-through');
            comment_ul.load(comment_ul);
        },
        error: function () {
            alert('완료 실패!');
        }
    });

});

$(document).on("click",".checkbox4",function() {
    var editbtn = $(this).parent().find('.comment_edit');
    var checkboxbtn = $(this);
    var comment_content = $(this).parent().parent().find('#comment_content');

    var button = document.createElement('button');
    button.style.width = "35px";
    button.className = "checkbox3";
    button.value = $(this).val();

    var i = document.createElement('i');
    i.style.marginBottom = "20px";
    i.className = "material-icons";
    i.appendChild(document.createTextNode("check_box_outline_blank"));

    button.appendChild(i);

    var comment_ul = $(this).parent().parent().parent().parent().find('#comment_ul');

    $.ajax({
        url: "/comment/status/" + $(this).val(),
        type: "PUT",
        contentType: "application/json",
        success: function () {
            editbtn.css('display', 'inline');
            checkboxbtn.replaceWith(button);
            comment_content.css('text-decoration', 'none');
            comment_ul.load(comment_ul);
        },
        error: function () {
            alert('완료 실패!');
        }
    });
});