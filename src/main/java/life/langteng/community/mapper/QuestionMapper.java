package life.langteng.community.mapper;

import life.langteng.community.dto.QuestionDTO;
import life.langteng.community.entity.Question;
import life.langteng.community.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface QuestionMapper {

    /**
     * 向数据库中插入数据
     * @param question
     * @return
     */
    @Insert("insert into tb_question (title,description,tag,creator,gmt_create,gmt_modified,comment_count,view_count,like_count)" +
            " values (#{title},#{description},#{tag},#{creator},#{gmtCreate},#{gmtModified},#{commentCount},#{viewCount},#{likeCount})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    boolean createQuertion(Question question);

    /**
     * 获取所有的问题，包含其发布者的信息
     *
     *
     *
     *
     *
     * @return
     */
    @Results(id = "questionDTO",value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "title",column = "title"),
            @Result(property = "description",column = "description"),
            @Result(property = "tag",column = "tag"),
            @Result(property = "gmtCreate",column = "gmt_create"),
            @Result(property = "gmtModified",column = "gmt_modified"),
            @Result(property = "creator",column = "creator"),
            @Result(property = "commentCount",column = "comment_count"),
            @Result(property = "viewCount",column = "view_count"),
            @Result(property = "likeCount",column = "like_count"),

            @Result(property = "user.id",column = "uid"),
            @Result(property = "user.account",column = "account"),
            @Result(property = "user.name",column = "name"),
            @Result(property = "user.token",column = "token"),
            @Result(property = "user.intro",column = "intro"),
            @Result(property = "user.gmtCreate",column = "u_gmt_create"),
            @Result(property = "user.gmtModified",column = "u_gmt_modified"),
            @Result(property = "user.avatarUrl",column = "avatar_url")
    })
    @Select("select q.id,q.title,q.description,q.tag,q.gmt_create,q.gmt_modified,q.creator,q.comment_count,q.view_count,q.like_count," +
            "u.id uid,u.account,u.name,u.token,u.intro,u.gmt_create u_gmt_create,u.gmt_modified u_gmt_modified,u.avatar_url from tb_question q,tb_user u where q.creator = u.id")
    List<QuestionDTO> getAllQuestions();

    /**
     *  count(*)  最慢    为什么是最慢的呢?
     *
     *  count(1)  一般    ？
     *
     *  count(主键) 最快  ？
     *
     *  mysql 中  count(1)  可以使用 long 和 int 来接收
     *  Returns a count of the number of non-NULL values of expr in the rows
     *        retrieved by a SELECT statement. The result is a BIGINT value (官方说是long)
     * @return
     */
    @Select("select count(id) from tb_question")
    long queryCount();

    /**
     * 无论是 mybatis 的注解开发，还是 xml 开发
     *
     *  当 Mapper 接口的方法中的参数超过一个时，就需要通过 org.apache.ibatis.annotations.@Param 注解 来表明
     *
     * @param position
     * @param pageSize
     * @return
     */
    @ResultMap("questionDTO")
    @Select("select q.id,q.title,q.description,q.tag,q.gmt_create,q.gmt_modified,q.creator,q.comment_count,q.view_count,q.like_count," +
            "u.id uid,u.account,u.name,u.token,u.intro,u.gmt_create u_gmt_create,u.gmt_modified u_gmt_modified,u.avatar_url from" +
            " tb_question q,tb_user u where q.creator = u.id  limit #{position},#{pageSize}")
    List<QuestionDTO> queryQuestionByPage(@Param("position") Integer position,@Param("pageSize") Integer pageSize);

    /**
     * 查询指定用户发布的所有的问题
     * @param userId
     * @param position
     * @param pageSize
     * @return
     */
    @ResultMap("questionDTO")
    @Select("select q.id,q.title,q.description,q.tag,q.gmt_create,q.gmt_modified,q.creator,q.comment_count,q.view_count,q.like_count," +
            "u.id uid,u.account,u.name,u.token,u.intro,u.gmt_create u_gmt_create,u.gmt_modified u_gmt_modified,u.avatar_url from" +
            " tb_question q,tb_user u where q.creator = u.id and u.id = #{userId} limit #{position},#{pageSize}")
    List<QuestionDTO> getQuestionsByUserId(@Param("userId") Integer userId,@Param("position") Integer position,@Param("pageSize") Integer pageSize);

    @Select("select count(id) from tb_question where creator = #{userId}")
    long queryCountByUserId(@Param("userId") Integer userId);

    @ResultMap("questionDTO")
    @Select("select q.id,q.title,q.description,q.tag,q.gmt_create,q.gmt_modified,q.creator,q.comment_count,q.view_count,q.like_count," +
            "u.id uid,u.account,u.name,u.token,u.intro,u.gmt_create u_gmt_create,u.gmt_modified u_gmt_modified,u.avatar_url from" +
            " tb_question q,tb_user u where q.creator = u.id and q.id = #{questionId}")
    QuestionDTO getQuestionById(@Param("questionId") Integer questionId);
}
