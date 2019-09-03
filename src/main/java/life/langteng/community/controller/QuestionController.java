package life.langteng.community.controller;

import life.langteng.community.dto.CommentDTO;
import life.langteng.community.dto.PageHelperDTO;
import life.langteng.community.dto.QuestionDTO;
import life.langteng.community.entity.Question;
import life.langteng.community.entity.User;
import life.langteng.community.service.ICommentService;
import life.langteng.community.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {


    @Autowired
    private IQuestionService questionService;

    @Autowired
    private ICommentService commentService;

    /**
     * 用来展示用户提出的所有问题
     * @return
     */
    @GetMapping("/profile/questions")
    public String userQuestions(HttpServletRequest request,
                                @RequestParam(name = "currentPage",defaultValue = "1")Integer currentPage,
                                @RequestParam(name = "pageSize",defaultValue ="5")Integer pageSize,
                                Model model
                                ){
        /**
         * 容错  最小值
         */
        if (currentPage < 1) {
            currentPage = 1;
        }

        User user = (User)request.getSession().getAttribute("user");

        int total = (int) questionService.queryCountByUserId(user.getId());
        // 总页数
        int count = ((total % pageSize == 0) ? (total / pageSize) : (total / pageSize + 1));

        /**
         * 容错 最大值
         */
        if (currentPage > count) {
            currentPage = count;
        }


        List<QuestionDTO> questions = questionService.getQuestionsByUserId(user.getId(),currentPage,pageSize);



        PageHelperDTO pageHelper = null;

        if(total != 0){
            pageHelper = new PageHelperDTO(questions,currentPage,pageSize,total);
        }
        model.addAttribute("pageHelper",pageHelper);

        return "questions";
    }




    @GetMapping("/profile/replies/{id}")
    public String userReplies(@PathVariable(name = "id") Integer questionId,
                              HttpServletRequest request){

        // 修改 当前问题的浏览数  我们需要考虑并发问题，我们应该在数据库的基础上去自增1，
        // 而不是，将值取出来，加一后再存入数据库

        questionService.incViewCount(questionId);


        List<CommentDTO> commentDTOS = commentService.queryAllQuestionComments(questionId);

        request.setAttribute("comments",commentDTOS);


        QuestionDTO question = questionService.getQuestionById(questionId);

        request.setAttribute("question",question);

        return "replies";
    }





}
