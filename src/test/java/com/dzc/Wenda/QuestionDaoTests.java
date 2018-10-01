package com.dzc.Wenda;


import com.dzc.Wenda.dao.QuestionDao;
import com.dzc.Wenda.model.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionDaoTests {

    @Autowired
    private QuestionDao questionDao;

    /**
     *  int addQuestion(Question question);
     *
     *     List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);
     *
     *
     *     Question getById(int id);
     *
     *     int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
     */

    @Test
    public void testAddQuestion() {
        Question question = new Question();
        question.setTitle("这是一个问题355555");
        question.setContent("这是内容3");
        question.setCreatedDate(new Date());
        question.setCommentCount(200);
        question.setUserId(10000);

        int count = questionDao.addQuestion(question);
        System.out.println("影响行数：" + count);
    }


    @Test
    public void testSelectLatestQuestions() {
        List<Question> questions = questionDao.selectLatestQuestions(10000, 0, 5);
        for (Question q : questions) {
            System.out.println(q.getTitle());
        }
    }



    @Test
    public void testGetById() {
        Question q = questionDao.getById(11);
        System.out.println(q);
    }





    @Test
    public void testUpdateCommentCount() {
        questionDao.updateCommentCount(11, 888);

    }





}
