package life.langteng.community.dto;

import lombok.Data;

/**
 *
 *  获取github token 的 数据传输对象
 * @author 宝哥
 * @date 2019/8/24
 */
@Data
public class GithubAccessTokenDTO {

    private String client_id;

    private String client_secret;

    private String code;

    private String redirect_uri;

    private String state;

}
