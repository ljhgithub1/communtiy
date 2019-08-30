package life.langteng.community.mapper;

import life.langteng.community.dto.QuestionDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 针对于 question的多表查询
 */
public interface QuestionCustomizeMapper {

    List<QuestionDTO> queryAllQuestions();

    List<QuestionDTO> queryQuestionByPage(@Param("position") Integer position, @Param("pageSize") Integer pageSize);

    QuestionDTO getQuestionDTOById(@Param("questionId") Integer questionId);

    List<QuestionDTO> getQuestionsDTOByUserId(@Param("userId") Integer userId,@Param("position") Integer position,@Param("pageSize") Integer pageSize);

    int incViewCount(@Param("questionId") Integer questionId);

    int incCommentCount(@Param("questionId") Integer questionId,@Param("commentCount")Integer commentCount);
}
