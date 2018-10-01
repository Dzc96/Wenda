package com.dzc.Wenda.async.handler;

import com.dzc.Wenda.async.EventHandler;
import com.dzc.Wenda.async.EventModel;
import com.dzc.Wenda.async.EventType;
import com.dzc.Wenda.model.Message;
import com.dzc.Wenda.model.User;
import com.dzc.Wenda.service.MessageService;
import com.dzc.Wenda.service.UserService;
import com.dzc.Wenda.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {


    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;


    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName() + "赞了你的评论，http://127.0.0.1:8080/question/" + model.getExt("questionId"));
        //这个通知短信是由系统发送给某个用户的
        message.setConversationId(WendaUtil.SYSTEM_USERID + "_" + model.getEntityOwnerId());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
