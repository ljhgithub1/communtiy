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
 * 登录
 * @author 宝哥
 * @date 2019/8/24
 */
@Controller
public class LoginController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private IUserService userService;

    private final String token = "token";


    /**
     *
     * 用户使用github第三方登录   ----------- 通过这样的形式是，请求不到github的，必须通过 index.html 中的 <a></a> 标签 的href 属性指定
     *
     * 执行后，github会调用我们的回调方法，并返回一个code参数
     *
     * 请求方式: get
     *
     */
    @RequestMapping("/login")
    @org.springframework.web.bind.annotation.ResponseBody
    public String login(){

        String url = "https://github.com/login/oauth/authorize" +       // url
                "?client_id=77b3bd8c926cc4119711" +                     // client_id
                "&redirect_uri=http://localhost:8080/callback" +        // github回调我们的方法的路径(url)
                "&scope=user";                                          // 获取用户信息
        // state : type is String   如果我们这里添加了状态，那么GitHub回调我们方法的时候，会将该状态传入过来，我们可以通过前后状态码做一些判断


        // 获取 okHttpClient(http请求的客户端)
        OkHttpClient client = new OkHttpClient();

        // 通过url地址获取一个 okHttp 中的request对象
        Request request = new Request.Builder().url(url).build();

        try {
            // 执行url请求
            client.newCall(request).execute();
        } catch(IOException e){
            // 这里可以输出日志
        }
        return null;
    }


    /**
     * 默认到index页面
     * @return
     */
    @RequestMapping("/")
    public String index(HttpServletRequest request,@CookieValue(name = "token",required = false)Cookie cookie){

        /**
         * new  user @CookieValue
         *
         * 通过 @CookieValue 可以 直接获取我们制定名称的cookie
         *
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






}
