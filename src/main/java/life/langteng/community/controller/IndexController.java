package life.langteng.community.controller;

import life.langteng.community.dto.PageHelperDTO;
import life.langteng.community.dto.QuestionDTO;
import life.langteng.community.entity.User;
import life.langteng.community.service.IQuestionService;
import life.langteng.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IQuestionService questionService;

    /**
     * 默认到index页面
     * @return
     */
    @RequestMapping("/")
    public String index(HttpServletRequest request,
                        @CookieValue(name = "token",required = false)Cookie cookie,
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

        /**
         * new  user @CookieValue
         *
         * 通过 @CookieValue 可以 直接获取我们制定名称的cookie
         *
         *
         * 如果 用户判断放到 获取数据的前面，在cookie为null的时候，index.html就是解析失败，因为pageHelper为null
         */
        if(cookie == null){
            return "index";
        }
        String value = cookie.getValue();
        // 去数据库中查找是否存在用户的token和该token相同，如果有就直接将用户信息保存到session中，否则就跳过
        User user = userService.getUserByToken(value);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
        }


        /**
         * old
         */
        /*// 如果用户的浏览器中存在该用户登录过的信息，就直接登录
        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length <= 0) {
            return "index";
        }

        for (Cookie cookie : cookies) {
            if (token.equals(cookie.getName())) {
                String value = cookie.getValue();
                // 去数据库中查找是否存在用户的token和该token相同，如果有就直接将用户信息保存到session中，否则就跳过
                User user = userService.getUserByToken(value);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user",user);
                }
                break;
            }
        }*/
        return "index";
    }

}
