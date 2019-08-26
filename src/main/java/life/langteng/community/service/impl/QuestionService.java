package life.langteng.community.service.impl;

import life.langteng.community.entity.Question;
import life.langteng.community.mapper.QuestionMapper;
import life.langteng.community.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;


    @Override
    public boolean createQuestion(Question question) {
        boolean bool = questionMapper.createQuertion(question);
        System.out.println("insert 中返回的 boolean 类型"+ bool);
        return bool;
    }
}
