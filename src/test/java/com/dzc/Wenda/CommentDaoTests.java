package com.dzc.Wenda;

import com.dzc.Wenda.dao.CommentDao;
import com.dzc.Wenda.model.Comment;
import com.dzc.Wenda.model.EntityType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentDaoTests {
    /**
     *     int addComment(Comment comment);
     *
     *     void updateStatus(@Param("entityId") int entityId, @Param("entityType") int entityType, @Param("status") int status);
     *
     *     List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);
     *
     *     int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);
     *
     *     Comment getCommentById(int id);
     *
     *     int getUserCommentCount(int userId);
     */
    @Autowired
    private CommentDao commentDao;


    @Test
    public void testAddComment() {
        Comment comment = new Comment();
        comment.setUserId(10000);
        comment.setContent("这是一条评论!");
        comment.setCreatedDate(new Date());
        comment.setEntityType(EntityType.ENTITY_QUESTION);
        comment.setEntityId(1);
        comment.setStatus(0);
       int count =  commentDao.addComment(comment);
        System.out.println("count:" + count);
    }


    @Test
    public void testUpdateStatus() {
        commentDao.updateStatus(1,EntityType.ENTITY_QUESTION,1);
    }


    @Test
    public void testSelectByEntity() {
        List<Comment> comments = commentDao.selectByEntity(1, EntityType.ENTITY_QUESTION);
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }


    //getCommentCount
    @Test
    public void testGetCommentCount() {
        int commentCount = commentDao.getCommentCount(1, EntityType.ENTITY_QUESTION);
        System.out.println("count:" + commentCount);
    }


    @Test
    public void testGetCommentById() {
        Comment commentById = commentDao.getCommentById(1);
        System.out.println(commentById);
    }

    @Test
    public void testGetUserCommentCount(){
        int userCommentCount = commentDao.getUserCommentCount(10000);
        System.out.println("count:" + userCommentCount);
    }

}
