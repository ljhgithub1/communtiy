package life.langteng.community.mapper;

import java.util.List;

import life.langteng.community.dto.QuestionDTO;
import life.langteng.community.entity.Question;
import life.langteng.community.entity.QuestionExample;
import org.apache.ibatis.annotations.Param;

public interface QuestionMapper {
    long countByExample(QuestionExample example);

    int deleteByExample(QuestionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    List<Question> selectByExampleWithBLOBs(QuestionExample example);

    List<Question> selectByExample(QuestionExample example);

    Question selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByExampleWithBLOBs(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByExample(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);

//-----------------------------------------------------------
    List<QuestionDTO>  queryAllQuestions();

    List<QuestionDTO> queryQuestionByPage(@Param("position") Integer position, @Param("pageSize") Integer pageSize);

    QuestionDTO getQuestionDTOById(@Param("questionId") Integer questionId);

    List<QuestionDTO> getQuestionsDTOByUserId(@Param("userId") Integer userId,@Param("position") Integer position,@Param("pageSize") Integer pageSize);
}