package life.langteng.community.service.impl;

import life.langteng.community.bean.CommentType;
import life.langteng.community.entity.Comment;
import life.langteng.community.entity.Question;
import life.langteng.community.exception.CommentResourceNotFoundException;
import life.langteng.community.exception.CommentTypeNotFountException;
import life.langteng.community.exception.QuestionResourceNotFoundException;
import life.langteng.community.mapper.CommentMapper;
import life.langteng.community.mapper.QuestionCustomizeMapper;
import life.langteng.community.mapper.QuestionMapper;
import life.langteng.community.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static life.langteng.community.bean.ErrorMessage.COMMENT_NOT_FOUND;
import static life.langteng.community.bean.ErrorMessage.COMMENT_QUESTION_NOT_FOUND;
import static life.langteng.community.bean.ErrorMessage.COMMENT_TYPE_NOT_FOUND;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionCustomizeMapper questionCustomizeMapper;


    @Override
    public void createComment(Comment comment) {

        // user id 没有  存放

        /**
         * parent_id commenter content type
         *
         * parent_id  1(问题)  2(回复)
         *
         */
        // 检查参数的有效性 comment == null ? comment.type == null ? comment.parent_id == null ? comment.commenter==null? comment.content==null?

        // 检查 type 是否是   CommentType中存在的
        boolean exist = CommentType.isExist(comment.getType());
        if(!exist){
            throw new CommentTypeNotFountException(COMMENT_TYPE_NOT_FOUND);
        }

        // 评论问题
        if(comment.getType() == 1){
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new QuestionResourceNotFoundException(COMMENT_QUESTION_NOT_FOUND);
            }
            // 问题回复数加一
            questionCustomizeMapper.incCommentCount(question.getId(),1);
            // 添加评论
            comment.setLikeCount(0);
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(comment.getGmtCreate());

            commentMapper.insertSelective(comment);

        }else if(comment.getType() == 2){ // 评论回复

            Comment com = commentMapper.selectByPrimaryKey(comment.getParentId());

            if (com == null){
                throw new CommentResourceNotFoundException(COMMENT_NOT_FOUND);
            }
            // ======================================
            // 添加评论
            comment.setLikeCount(0);
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(comment.getGmtCreate());

            commentMapper.insertSelective(comment);
        }



    }
}
