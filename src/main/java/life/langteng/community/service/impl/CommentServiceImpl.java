package life.langteng.community.service.impl;

import life.langteng.community.bean.CommentType;
import life.langteng.community.bean.NotificationType;
import life.langteng.community.bean.ResultMap;
import life.langteng.community.dto.CommentDTO;
import life.langteng.community.entity.Comment;
import life.langteng.community.entity.Notification;
import life.langteng.community.entity.Question;
import life.langteng.community.entity.User;
import life.langteng.community.exception.CommentQuestionNotFoundException;
import life.langteng.community.exception.CommentResourceNotFoundException;
import life.langteng.community.exception.CommentTypeNotFountException;
import life.langteng.community.mapper.*;
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

    @Autowired
    private NotificationMapper  notificationMapper;


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
        comment.setCommentCount(0);

        // 评论问题
        if(comment.getType() == 1){
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CommentQuestionNotFoundException(COMMENT_QUESTION_NOT_FOUND);
            }
            // 问题回复数加一
            questionCustomizeMapper.incCommentCount(question.getId(),1);

            commentMapper.insertSelective(comment);

            // 记录问题提示记录
            notification(comment.getParentId(),question.getCreator(),comment.getCommenter(),NotificationType.COMMENT_QUESTION,question.getTitle());


        }else if(comment.getType() == 2){ // 评论回复

            Comment com = commentMapper.selectByPrimaryKey(comment.getParentId());

            if (com == null){
                throw new CommentResourceNotFoundException(COMMENT_NOT_FOUND);
            }
            // 给其父类添加评论数
            incCommentCount(comment.getParentId(),1);
            commentMapper.insertSelective(comment);
            // 记录问题提示记录
            notification(comment.getParentId(),com.getCommenter(),comment.getCommenter(),NotificationType.COMMENT_REPLY,com.getContent());
        }


        resultMap.setMessage("回复成功");
        resultMap.setCode(2000);
        return resultMap;
    }

    /**
     *
     * @param outer       问题或者回复的id
     * @param receiver     接收者
     * @param replyer      回复者
     * @param type         类型
     */
    private void notification(Integer outer,Integer receiver,Integer replyer,NotificationType type,String title){
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setOuter(outer);
        notification.setReceiver(receiver);
        notification.setReplyer(replyer);
        notification.setType(type.getCode());
        notification.setStatus(0);
        notification.setOuterTitle(title);
        notificationMapper.insertSelective(notification);
    }


    private void incCommentCount(Integer commentId,Integer count){
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setCommentCount(count);
        incCommentCount(comment);
    }

    private void incCommentCount(Comment comment){
        commentCustomizeMapper.incCommentCount(comment);
    }



    @Override
    public List<CommentDTO> queryAllQuestionComments(Integer questionId) {
        return commentCustomizeMapper.queryAllCommentsByParentId(questionId,1);
    }

    @Override
    public List<CommentDTO> queryAllCommentComments(Integer commentId) {
        return commentCustomizeMapper.queryAllCommentsByParentId(commentId,2);
    }

}
