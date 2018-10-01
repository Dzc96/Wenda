package com.dzc.Wenda.dao;


import com.dzc.Wenda.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao {
    int addComment(Comment comment);

    void updateStatus(@Param("status") int status,  @Param("entityId") int entityId, @Param("entityType") int entityType);

    List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    Comment getCommentById(int id);

    int getUserCommentCount(int userId);

}
