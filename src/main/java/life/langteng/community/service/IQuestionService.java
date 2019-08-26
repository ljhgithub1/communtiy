package life.langteng.community.service;

import life.langteng.community.entity.Question;

public interface IQuestionService {
    /**
     * 向数据库中插入一条数据
     * @param question
     * @return
     */
    boolean createQuestion(Question question);
}
