package com.dzc.Wenda.service;

import com.dzc.Wenda.dao.QuestionDao;
import com.dzc.Wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private SensitiveService sensitiveService;

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }


    public int addQuestion(Question question) {
        //html标签转义
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        //敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDao.addQuestion(question) > 0 ? question.getId() : 0;

    }


    public int updateCommentCount(int id, int count) {
        return questionDao.updateCommentCount(id, count);
    }

    public Question getById(int id) {
        return questionDao.getById(id);
    }






}
