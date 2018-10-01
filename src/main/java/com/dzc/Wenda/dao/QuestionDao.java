package com.dzc.Wenda.dao;

import com.dzc.Wenda.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface QuestionDao {

    int addQuestion(Question question);

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);


    Question getById(int id);

    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}
