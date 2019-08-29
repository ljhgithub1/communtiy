package life.langteng.community.controller;

import life.langteng.community.entity.Question;
import life.langteng.community.entity.User;
import life.langteng.community.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 发布的控制器
 */
@Controller
public class PublishController {

    @Autowired
    private IQuestionService questionService;

    /**
     * 来到发布页面
     * @return
     */
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "tag") String tag,
            HttpServletRequest request,
            Model model
            ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        /**
         * 对参数进行校验
         */
        if(StringUtils.isEmpty(title)){
            request.setAttribute("titleError","问题不能为空");
            return "publish";
        }
        if(StringUtils.isEmpty(description)){
            request.setAttribute("descriptionError","问题描述不能为空");
            return "publish";
        }
        if(StringUtils.isEmpty(tag)){
            request.setAttribute("tagError","标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");


        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        question.setCommentCount(0);
        question.setViewCount(0);
        question.setLikeCount(0);

        questionService.createQuestion(question);

        return "redirect:/";
    }

}
