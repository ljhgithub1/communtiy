package life.langteng.community.controller;

import life.langteng.community.entity.Question;
import life.langteng.community.entity.User;
import life.langteng.community.service.IQuestionService;
import life.langteng.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 发布的控制器
 */
@Controller
public class PublishController {

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IUserService userService;

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
            @CookieValue(name = "token",required = false)Cookie cookie,
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


        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);


        /**
         * 先去session中取，如果还没有
         *
         * 再去判断本地是否存在用户登录的token，如果通过token能找到对应的用户，那就将信息存放到session中
         *
         */
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){

            if (cookie != null) {
                user = userService.getUserByToken(cookie.getValue());
                if (user != null){
                    request.getSession().setAttribute("user",user);
                }else {
                    request.setAttribute("question",question);
                    request.setAttribute("msg","请先登录!");
                    return "publish";
                }
            }else{
                request.setAttribute("question",question);
                request.setAttribute("msg","请先登录!");
                return "publish";
            }

        }
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
