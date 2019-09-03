package life.langteng.community.mapper;

import life.langteng.community.dto.CommentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentCustomizeMapper {

    List<CommentDTO> queryAllQuestionComments(@Param("questionId") Integer questionId);
}
