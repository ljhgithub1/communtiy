function commentQuestion() {

    // 获取 问题id
    var questionId = $("#questionId").val();
    var content = $("#content").val();

    $.ajax({
        url:"/comment",
        type:"post",
        contentType:"application/json",
        data:JSON.stringify({"parentId":questionId,"content":content,"type":1}), // 将string转换成json
        dataType:"json",
        success:function(resultMap){
            // 回复成功
            if(resultMap.code == 2000){
                $("#commentArea").hide();  // 评论成功，那么就隐藏评论框
            }else if(resultMap.code == 1998){
                // 未登录状态
              var conf = confirm(resultMap.message);  //带有确认的提示框
              if(conf){
                  // 打开一个新页面
                  window.open("https://github.com/login/oauth/authorize?client_id=77b3bd8c926cc4119711&redirect_uri=http://localhost:8080/callback&scope=user");
                  // 多个页面可以共享数据
                  window.localStorage.setItem("closeable","true");
              }
            }else{
                alert(resultMap.message);
            }
        },
        error:function(){

        }


    });
}
// 展开二级评论
function show_se_comment(e) {
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
    var id = e.getAttribute("data-id");
    var status = e.getAttribute("data-status");
    if(status == 0){
        e.classList.add("comment-active");
        e.setAttribute("data-status",1);
    }else{
        e.classList.remove("comment-active");
        e.setAttribute("data-status",0);
    }
    $('#comment-'+id).collapse('toggle');

}