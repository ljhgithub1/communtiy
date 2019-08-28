package life.langteng.community.service;

import life.langteng.community.dto.QuestionDTO;
import life.langteng.community.entity.Question;

import java.util.List;

public interface IQuestionService {
    /**
     * 向数据库中插入一条数据
     * @param question
     * @return
     */
    boolean createQuestion(Question question);

    /**
     * 获取所有的问题，并且包含发布人的信息
     * @return
     */
    List<QuestionDTO> getAllQuestions();

    /**
     * 查询分页数据
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<QuestionDTO> queryQuestionByPage(Integer currentPage, Integer pageSize);

    /**
     * 查询中的记录数
     * @return
     */
    long queryCount();
}
