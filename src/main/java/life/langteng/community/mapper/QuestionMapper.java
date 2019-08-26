package life.langteng.community.mapper;

import life.langteng.community.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

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


}
