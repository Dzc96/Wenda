package com.dzc.Wenda.dao;

import com.dzc.Wenda.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageDao {
    //更新信息是否已读的状态
    void updateMessagesReadStatus(String conversationId);

    //发送一条信息
    int addMessage(Message message);


    //得到某个会话中，所有的聊天信息
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);


    //得到用户某个会话的未读消息数
    int getConversationUnreadCount(@Param("userId") int userId,
                                   @Param("conversationId") String conversationId);

    //得到用户的所有私信列表，并按照日期排列
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

}
