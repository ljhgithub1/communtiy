package life.langteng.community.entity;


import lombok.Data;

/**
 * 用户实体类
 */
@Data
public class User {

    private Integer id;      // 数据库id

    private String account;  // 用户账号

    private String token;    // 用户令牌

    private String name;     // 用户名称

    private String intro;    // 用户个人简介

    private Long gmtCreate;  // 创建的时间戳

    private Long gmtModified;// 修改的时间戳

    private String avatarUrl; // 头像地址


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
