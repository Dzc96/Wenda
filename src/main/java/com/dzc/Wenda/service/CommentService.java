package com.dzc.Wenda.service;

import com.dzc.Wenda.dao.CommentDao;

import com.dzc.Wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nowcoder on 2016/7/9.
 */
@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDao.selectByEntity(entityId, entityType);
    }

    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDao.getCommentCount(entityId, entityType);
    }

    public Comment getCommentById(int commentId) {
        return commentDao.getCommentById(commentId);
    }

    public void deleteComment(int entityId, int entityType) {
        commentDao.updateStatus(entityId, entityType, 1);
    }


    public int getUserCommentCount(int userId) {
        return commentDao.getUserCommentCount(userId);
    }
}
