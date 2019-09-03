package life.langteng.community.controller;

import life.langteng.community.bean.ResultMap;
import life.langteng.community.entity.Comment;
import life.langteng.community.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {

    @Autowired
    private ICommentService commentService;

    /**
     *  评论
     *    --可以评论问题
     *    --可以评论回复
     *
     *  以json的形式传递到页面
     *
     * RequestBody    将json对象转换成java对象
     * @ResponseBody  将java对象转换成json对象
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/comment")
    public ResultMap comment(@RequestBody Comment comment){

        ResultMap resultMap = commentService.createComment(comment);

        return resultMap;
    }



}
