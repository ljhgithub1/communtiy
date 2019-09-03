// 展开二级评论
function show_second_comment(e) {
    /**
     *  从前端传来的 doc 获取信息
     *
     *  可以通过 e.getAttribute("name") 获取其值
     *
     *  e.classList.remove("comment-active");  // 删除class
     *
     *  e.classList.add("comment-active");     // 添加class
     *
     */
    var id = e.getAttribute("data-id");  //评论的id
    var status = e.getAttribute("data-status");
    if(status == 0){
        e.classList.add("comment-active");
        e.setAttribute("data-status",1);
        // 通过ajax去获取服务端的数据
        $.ajax({
            url:"/subComment",
            type:"get",
            data:{"commentId":id},
            success:function (data) {
                console.log(data);

                // 通过jquery将数据动态的写到子评论下
                var commentElement = $('#comment-'+id);
                // 写之前先清空
               commentElement.empty();


                // 循环展示评论
                $.each(data.data.comments,function(index,comment){
                    var subComment = $("<div/>",{
                                        "class":"col-sm-12 col-xs-12 col-sm-12 col-md-12 item-style",
                                    }).append(
                                        $("<img/>",{
                                            "class":"avatar img-rounded",
                                            "src":comment.user.avatarUrl
                                        })
                                    ).append(
                                        $("<h5/>",{
                                            "style":"display: inline",
                                            "text":comment.user.name
                                        })
                                    );
                    var commentContent = $("<div/>",{
                        "class":"comment-content"

                    }).append(
                        $("<p/>",{
                            "text":comment.content
                        })
                    ).append(
                        $("<div/>",{
                            "class":"reply-hint"
                        }).append(
                            // 点赞
                            $("<span/>",{
                                "class":"glyphicon glyphicon-thumbs-up icon"
                            })
                        ).append(
                            // 评论
                            $("<span/>",{
                                "class":"glyphicon glyphicon-comment icon",
                                "data-id":comment.id,
                                "data-status":"0",
                            })
                        ).append(
                            $("<span/>",{
                                "class":"pull-right",
                                "text":moment(comment.gmtCreate).format("YYYY-MM-DD")
                            })
                        )
                    ).append(
                        $("<div/>",{
                            "class":"panel-collapse collapse",
                            "id":"subcomment-"+comment.id
                        })
                    );

                    subComment.append(commentContent);
                    subComment.append(
                        $("<hr/>",{
                            "class":"hr-style"
                        })
                    );
                    commentElement.append(subComment);

                });


                // 最后的评论框
                var inputElement =  $("<div/>",{
                    "class":"input-style"
                }).append(
                    $("<input/>",{
                        "class":"form-control",
                        "type":"text",
                        "placeholder":"评论一下...",
                        "id":"inputContent"
                    })
                ).append(
                    $("<button/>",{
                        "class":"btn btn-success pull-right sub-comment-btn",
                        "type":"button",
                        "text":"评论",
                        "onclick":"subComment("+id+")"
                    })
                );

                commentElement.append(inputElement);
            }
        });


    }else{
        e.classList.remove("comment-active");
        e.setAttribute("data-status",0);
    }
    $('#comment-'+id).collapse('toggle');

}

// 添加问题的评论
function commentQuestion() {
    // 获取 问题id
    var questionId = $("#questionId").val();
    var content = $("#content").val();
    if (checkContent(content)){
        comment(questionId,content,1);
    }

}

// 评论的公共方法
function comment(parentId,content,type){
    $.ajax({
        url: "/comment",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify({"parentId": parentId, "content": content, "type": type}), // 将string转换成json
        dataType: "json",
        success: function (resultMap) {
            // 回复成功
            if (resultMap.code == 2000) {
                window.location.reload(); // 从新刷新页面
            } else if (resultMap.code == 1998) {
                // 未登录状态
                var conf = confirm(resultMap.message);  //带有确认的提示框
                if (conf) {
                    // 打开一个新页面
                    window.open("https://github.com/login/oauth/authorize?client_id=77b3bd8c926cc4119711&redirect_uri=http://localhost:8080/callback&scope=user");
                    // 多个页面可以共享数据
                    window.localStorage.setItem("closeable", "true");
                }
            } else {
                alert(resultMap.message);
            }
        },
        error: function () {

        }
    });

}


// 添加评论的评论
function subComment(id){
    var subContent = $("#inputContent");
    var content = subContent.val();
    if(checkContent(content)){
        comment(id,content,2);
    }
}

function checkContent(content){
    if(!content){
        alert("评论内容不能为空!!");
        return false;
    }
    return true;
}
