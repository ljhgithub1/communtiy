package life.langteng.community.service.impl;

import life.langteng.community.dto.QuestionDTO;
import life.langteng.community.entity.Question;
import life.langteng.community.mapper.QuestionMapper;
import life.langteng.community.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<QuestionDTO> QuestionDTOs = questionMapper.getAllQuestions();
        return QuestionDTOs;
    }

    @Override
    public List<QuestionDTO> queryQuestionByPage(Integer currentPage, Integer pageSize) {
        /**
         *   假设:  pageSize  为   5
         *
         *       limit position , pageSize
         *
         *      limit 0,5   1 2 3 4 5         第1页
         *
         *      limit 5,5   6 7 8 9 10        第2页
         *
         *      limit 10,5  11 12 13 14 15    第3页
         *
         *---------------------------------
         *
         *  规律:
         *
         *       position = (current - 1) * pageSize
         *
         */

        int position = (currentPage - 1) * pageSize;

        return questionMapper.queryQuestionByPage(position,pageSize);
    }

    @Override
    public long queryCount() {
        return questionMapper.queryCount();
    }

    @Override
    public List<QuestionDTO> getQuestionsByUserId(Integer userId, Integer currentPage, Integer pageSize) {

        int postion = (currentPage -1) * pageSize;

        List<QuestionDTO> questions = questionMapper.getQuestionsByUserId(userId,postion,pageSize);

        return questions;
    }

    @Override
    public long queryCountByUserId(Integer userId) {
        long count = questionMapper.queryCountByUserId(userId);
        return count;
    }

    @Override
    public QuestionDTO getQuestionById(Integer questionId) {

        QuestionDTO question = questionMapper.getQuestionById(questionId);

        return question;
    }


}
