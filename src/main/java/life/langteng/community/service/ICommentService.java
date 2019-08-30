package life.langteng.community.service;

import life.langteng.community.entity.Comment;

public interface ICommentService {

    /**
     * 创建评论
     * @param comment
     */
    void createComment(Comment comment);

}
