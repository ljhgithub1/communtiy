package life.langteng.community.provider;

import com.alibaba.fastjson.JSON;
import life.langteng.community.dto.GithubAccessTokenDTO;
import life.langteng.community.entity.GithutUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 提供Github的第三方登录
 *
 *
 * httpClient
 *
 * okHttp   是一个server内部发起http请求，并获得响应的工具
 *
 * @author 宝哥
 * @date 2019/8/24
 */
@Component
public class GithubProvider {

    private final String url = "https://github.com/login/oauth/access_token";

    private final String client_id = "77b3bd8c926cc4119711";

    private final String client_secret = "38afe716285836696e29ed490b64079c5200daa0";

    private final String redirect_uri = "http://localhost:8080/callback";

    private final String media_type = "application/json; charset=utf-8";



    /**
     *
     * @param code
     * @param state
     * @return
     */
    public String getAccess_token(String code,String state){

        GithubAccessTokenDTO tokenDTO = new GithubAccessTokenDTO();

        tokenDTO.setClient_id(client_id);

        tokenDTO.setClient_secret(client_secret);

        tokenDTO.setCode(code);

        tokenDTO.setState(state);

        // 注意这里的 Redirect_uri 是我们注册 OAuth application 时，指定的url
        tokenDTO.setRedirect_uri(redirect_uri);

        MediaType mediaType = MediaType.get(media_type);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(tokenDTO));

        Request request = new Request.Builder().url(url).post(body).build();

        try{
            Response response = client.newCall(request).execute();

            String message = response.body().string();

            return  message.split("&")[0].split("=")[1];
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;

    }


    /**
     *
     *
     *
     *
     *
     * @param access_token
     * @return
     */
    public GithutUser getGitHubUserInfo(String access_token){

        String  url = "https://api.github.com/user?access_token="+access_token;

        Request request = new Request.Builder().url(url).build();

        OkHttpClient client = new OkHttpClient();

        try{
            Response response = client.newCall(request).execute();
            return JSON.parseObject(response.body().string(),GithutUser.class);
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }



}
