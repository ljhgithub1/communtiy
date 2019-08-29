package life.langteng.community.mapper;

import life.langteng.community.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * 创建 UserMapper 接口
 */
@Mapper
public interface UserMapper {

    @Select("select * from tb_user where id = #{id}")
    User getUserById(int id);


    /**
     * @Option(useGeneratedKeys = true) 表示使用自动增长的主键 ----》自动封装到 user对象中
     * keyProperty = "id"  java中的属性
     * keyColumn = "id"    数据库中的字段
     * @param user
     */
    @Insert("insert into tb_user (account,name,token,intro,gmt_create,gmt_modified,avatar_url) " +
            "values (#{account},#{name},#{token},#{intro},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void insertUser(User user);

    @Select("select * from tb_user where token = #{token}")
    User getUserByToken(@Param("token") String token);

    @Select("select * from tb_user where account = #{account}")
    User getUserByAccount(@Param("account") String account);

    @Update("update tb_user set (name= #{name},token= #{token},intro= #{intro},avatar_url= #{avatarUrl},gmt_modified= #{gmtModified}) where id = #{id}")
    void updateUserToken(User user);
}
