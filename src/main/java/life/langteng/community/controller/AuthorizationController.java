package life.langteng.community.controller;

import life.langteng.community.dto.GithutUser;
import life.langteng.community.entity.User;
import life.langteng.community.provider.GithubProvider;
import life.langteng.community.service.IUserService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * github 认证登录
 *
 * @author 宝哥
 * @date 2019/8/24
 */
@Controller
public class AuthorizationController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private IUserService userService;

    private final String token = "token";


    /**
     *
     * 在创建 GitHub的 OAuth application 的时候，指定该回调URL
     *
     * gitHub 回调我们的方法
     *
     * 该方法的目的是: 通过 code 去获取 token  ******
     *
     * 请求方式: post
     *
     * @param code
     */
    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code",required = true) String code,
                           @RequestParam(name = "state",required = false) String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        // 获取access_token
        String access_token = githubProvider.getAccess_token(code, state);
        // 通过access_token 获取 用户信息
        GithutUser gitHubUserInfo = githubProvider.getGitHubUserInfo(access_token);


        User user = userService.githubUser2User(gitHubUserInfo);

        if (user != null){
            // 设置用户信息到session中
            request.getSession().setAttribute("user",user);
            // 设置token到cookie中
            Cookie tokenCookie = new Cookie(token,user.getToken());
            tokenCookie.setMaxAge(2678400); // 1000 * 60 * 60 * 24 * 31 = 一个月的秒数
            response.addCookie(tokenCookie);
        }

        // redirect:映射路径
        return "redirect:/";
    }



    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){

        // 删除session中用户信息
        HttpSession session = request.getSession();
        session.removeAttribute("user");

        // 新建一个同名的cookie，然后设置其有效时间为0,相当于删除了cookie
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }




}
