package life.langteng.community.provider;

import com.alibaba.fastjson.JSON;
import life.langteng.community.dto.GithubAccessTokenDTO;
import life.langteng.community.dto.GitHubUserDTO;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 提供Github的第三方登录
 *
 * httpClient --- 了解
 *
 * okHttp   是一个server内部发起http请求，并获得响应的工具 --- 了解
 *
 * @author 宝哥
 * @date 2019/8/24
 */
@Component
public class GithubProvider {

    private final Logger logger = LoggerFactory.getLogger(GithubProvider.class);

    @Value("${github.url}")
    private  String url;
    @Value("${github.client.id}")
    private  String client_id;
    @Value("${github.client.secret}")
    private  String client_secret;
    @Value("${github.redirect.uri}")
    private  String redirect_uri;



    /**
     * 通过 github 回传给我们的code 获取 access_token
     * @param code   必须的
     * @param state  可选的
     * @return
     */
    private String getAccess_token(String code,String state){

        GithubAccessTokenDTO tokenDTO = new GithubAccessTokenDTO();

        tokenDTO.setClient_id(client_id);

        tokenDTO.setClient_secret(client_secret);

        tokenDTO.setCode(code);

        tokenDTO.setState(state);

        // 注意这里的 Redirect_uri 是我们注册 OAuth application 时，指定的回调的url
        tokenDTO.setRedirect_uri(redirect_uri);
        // 请求类型
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        // 获取 OkHttpClient 对象，并通过该对象发起http请求
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(tokenDTO));

        Request request = new Request.Builder().url(url).post(body).build();

        try{
            Response response = client.newCall(request).execute();

            String message = response.body().string();

            return  message.split("&")[0].split("=")[1];
        }catch (IOException e){
            logger.info("OKHttp 发送请求,获取 access token 失败",e);
        }

        return null;

    }


    /**
     *  通过 access_token 获取用户信息
     * @param access_token
     * @return
     */
    private GitHubUserDTO getGitHubUserInfo(String access_token){

        String  url = "https://api.github.com/user?access_token="+access_token;

        Request request = new Request.Builder().url(url).build();

        OkHttpClient client = new OkHttpClient();

        try{
            Response response = client.newCall(request).execute();
            return JSON.parseObject(response.body().string(),GitHubUserDTO.class);
        }catch (IOException e){
            logger.info("获取用户信息请求失败",e);
        }

        return null;
    }

    /**
     * github 授权
     * @param code
     * @param state
     * @return
     */
    public GitHubUserDTO gitHubAuthorization(String code, String state){
        String accessToken = getAccess_token(code, state);
        GitHubUserDTO gitHubUserInfo = getGitHubUserInfo(accessToken);
        return gitHubUserInfo;
    }


}
