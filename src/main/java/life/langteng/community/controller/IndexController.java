package life.langteng.community.controller;

import life.langteng.community.dto.PageHelperDTO;
import life.langteng.community.dto.QuestionDTO;
import life.langteng.community.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IQuestionService questionService;

    /**
     * 默认到index页面
     * @return
     */
    @RequestMapping("/")
    public String index(HttpServletRequest request,
                        @RequestParam(name = "currentPage",defaultValue = "1") Integer currentPage,
                        @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){

        /**
         * 容错 最小值
         */
        if (currentPage < 1) {
            currentPage = 1;
        }
        int total = (int) questionService.queryCount();

        int totalPages = ((total % pageSize == 0) ? (total / pageSize) : (total / pageSize + 1));

        /**
         * 容错 最大值
         */
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        /**
         * 无论是否登录，都需要回显所有的问题数据
         *
         * ------ > 这里以后考虑使用缓存
         */
        List<QuestionDTO> questions = questionService.queryQuestionByPage(currentPage,pageSize);

        PageHelperDTO  pageHelperDTO = null;

        if (total != 0) {
            pageHelperDTO = new PageHelperDTO(questions, currentPage, pageSize, total);
        }

        request.setAttribute("pageHelper",pageHelperDTO);

        return "index";
    }

}
