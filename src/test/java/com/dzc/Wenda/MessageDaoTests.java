package com.dzc.Wenda;

import com.dzc.Wenda.dao.MessageDao;
import com.dzc.Wenda.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageDaoTests {
    /**
     *     //更新信息是否已读的状态
     *     void updateMessagesReadStatus(String conversationId);
     *
     *     //发送一条信息
     *     int addMessage(Message message);
     *
     *
     *     //得到某个会话中，所有的聊天信息
     *     List<Message> getConversationDetail(@Param("conversationId") String conversationId,
     *                                         @Param("offset") int offset,
     *                                         @Param("limit") int limit);
     *
     *
     *     //得到用户某个会话的未读消息数
     *     int getConversationUnreadCount(@Param("userId") int userId,
     *                                    @Param("conversationId") String conversationId);
     *
     *     //得到用户的所有私信列表，并按照日期排列
     *     List<Message> getConversationList(@Param("userId") int userId,
     *                                       @Param("offset") int offset,
     *                                       @Param("limit") int limit);
     */

    @Autowired
    private MessageDao messageDao;

    @Test
    public void testAddMessage() {
        Message message = new Message();
        message.setFromId(10000);
        message.setToId(10001);
        message.setConversationId("2");
        message.setContent("这是一条测试评论");
        message.setCreatedDate(new Date());
        message.setHasRead(0);
        messageDao.addMessage(message);
    }


    @Test
    public void testUpdateMessagesReadStatus() {
        messageDao.updateMessagesReadStatus("2");

    }

    @Test
    public void testGetConversationDetail() {
        List<Message> conversationDetail = messageDao.getConversationDetail("1", 0, 5);
        for (Message m : conversationDetail) {
            System.out.println(m);
        }
    }

    @Test
    public void testGetConversationUnreadCount() {
        int conversationUnreadCount = messageDao.getConversationUnreadCount(100000, "1");
        System.out.println("未读消息数为:" + conversationUnreadCount);

    }

    //getConversationList

    @Test
    public void testGetConversationList() {
        List<Message> conversationList = messageDao.getConversationList(10000, 0, 5);
        for (Message m : conversationList) {
            System.out.println(m);
        }

    }




}
