package life.langteng.community.entity;

/**
 * 通过github 登录获取的一些信息
 * @author 宝哥
 * @date 2019/8/24
 */
public class GithutUser {

    private String login;  // 登录用户名

    private long id;      // github 唯一 id

    private String name;  // 姓名

    private String bio;   // 个人简介


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
