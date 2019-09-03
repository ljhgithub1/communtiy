package life.langteng.community.service;

import life.langteng.community.bean.ResultMap;
import life.langteng.community.dto.CommentDTO;
import life.langteng.community.entity.Comment;

import java.util.List;

public interface ICommentService {

    /**
     * 创建评论
     * @param comment
     */
    ResultMap createComment(Comment comment);

    List<CommentDTO> queryAllQuestionComments(Integer questionId);
}
