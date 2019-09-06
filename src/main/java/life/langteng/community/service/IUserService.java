package life.langteng.community.service;

import life.langteng.community.dto.GitHubUserDTO;
import life.langteng.community.entity.User;

public interface IUserService {

    /**
     * 将githubUser信息封装到User中
     * @param githutUser
     * @return
     */
    User githubUser2User(GitHubUserDTO githutUser);

    /**
     * 添加用户信息
     *
     * --以后升级时：需要将 void -->boolean
     * @param user
     */
    void insertUser(User user);

    /**
     * 通过 token 获取用户信息
     * @param token
     * @return
     */
    User getUserByToken(String token);




}
