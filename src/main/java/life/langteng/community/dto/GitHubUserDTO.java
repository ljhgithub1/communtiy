package life.langteng.community.dto;


import lombok.Data;

/**
 * 通过github 登录获取的一些信息
 * @author 宝哥
 * @date 2019/8/24
 */
@Data
public class GitHubUserDTO {

    private String login;  // 登录用户名

    private long id;      // github 唯一 id

    private String name;  // 姓名

    private String bio;   // 个人简介

    private String avatar_url; // 头像

}
