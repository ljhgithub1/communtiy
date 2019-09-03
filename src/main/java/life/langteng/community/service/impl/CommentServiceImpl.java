package life.langteng.community.service.impl;

import life.langteng.community.bean.CommentType;
import life.langteng.community.bean.ResultMap;
import life.langteng.community.dto.CommentDTO;
import life.langteng.community.entity.Comment;
import life.langteng.community.entity.Question;
import life.langteng.community.entity.User;
import life.langteng.community.exception.CommentQuestionNotFoundException;
import life.langteng.community.exception.CommentResourceNotFoundException;
import life.langteng.community.exception.CommentTypeNotFountException;
import life.langteng.community.exception.QuestionResourceNotFoundException;
import life.langteng.community.mapper.CommentCustomizeMapper;
import life.langteng.community.mapper.CommentMapper;
import life.langteng.community.mapper.QuestionCustomizeMapper;
import life.langteng.community.mapper.QuestionMapper;
import life.langteng.community.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

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

    @Autowired
    private CommentCustomizeMapper commentCustomizeMapper;


    @Override
    @Transactional  // 添加事务支持 -- TransactionAspectSupport
    public ResultMap createComment(Comment comment) {

        ResultMap resultMap = new ResultMap(1999);

        // ------------------------------------------------------
        if (comment == null || comment.getContent() == null) {
            resultMap.setMessage("回复信息不能为空!");
            return resultMap;
        }

        if(comment.getParentId() == null){
            resultMap.setMessage("parentId 不能为空!");
            return resultMap;
        }

        if (comment.getType() == null){
            resultMap.setMessage("type 不能为空");
            return resultMap;
        }
        // ------------------------------------------------------

        // RequestAttributes 是一个接口，里面定义的属性是public static final的
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        User user = (User)requestAttributes.getAttribute("user", RequestAttributes.SCOPE_SESSION);

        if (user != null) {
            comment.setCommenter(user.getId());
        }else{
            resultMap.setMessage("您尚未登录,请先登录!");
            resultMap.setCode(1998);
            return resultMap;
        }

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
        // 添加评论 信息
        comment.setLikeCount(0);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());

        // 评论问题
        if(comment.getType() == 1){
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CommentQuestionNotFoundException(COMMENT_QUESTION_NOT_FOUND);
            }
            // 问题回复数加一
            questionCustomizeMapper.incCommentCount(question.getId(),1);


            commentMapper.insertSelective(comment);

        }else if(comment.getType() == 2){ // 评论回复

            Comment com = commentMapper.selectByPrimaryKey(comment.getParentId());

            if (com == null){
                throw new CommentResourceNotFoundException(COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }


        resultMap.setMessage("回复成功");
        resultMap.setCode(2000);
        return resultMap;
    }

    @Override
    public List<CommentDTO> queryAllQuestionComments(Integer questionId) {
        return commentCustomizeMapper.queryAllQuestionComments(questionId);
    }
}
