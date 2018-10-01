package com.dzc.Wenda.service;

import com.dzc.Wenda.dao.MessageDao;
import com.dzc.Wenda.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private SensitiveService sensitiveService;



    public void updateMessageReadStatus(String conversationId) {
        messageDao.updateMessagesReadStatus(conversationId);
    }


    public int addMessage(Message message) {
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDao.addMessage(message);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDao.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDao.getConversationList(userId, offset, limit);
    }

    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageDao.getConversationUnreadCount(userId, conversationId);
    }

}
