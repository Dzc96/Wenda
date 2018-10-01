package com.dzc.Wenda.controller;

import com.dzc.Wenda.async.EventModel;
import com.dzc.Wenda.async.EventProducer;
import com.dzc.Wenda.async.EventType;
import com.dzc.Wenda.model.Comment;
import com.dzc.Wenda.model.EntityType;
import com.dzc.Wenda.model.HostHolder;
import com.dzc.Wenda.service.CommentService;
import com.dzc.Wenda.service.LikeService;
import com.dzc.Wenda.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private EventProducer eventProducer;



    /**
     * 点完赞后要以JSON格式返回该评论目前的点赞数给前台显示
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        //如果没登录，无法点赞
        if (hostHolder.getUser() == null)
            return WendaUtil.getJSONString(999);
        Comment comment = commentService.getCommentById(commentId);


        //点赞之后会给对应用户发送一个站内信，这是一个异步操作
        EventModel model = new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId())
                .setEntityType(EntityType.ENTITY_COMMENT)
                .setEntityId(commentId)
                .setEntityOwnerId(comment.getUserId())
                .setExt("questionId", String.valueOf(comment.getEntityId()));
        //往异步队列中发送一个事件模型
        eventProducer.fireEvent(model);



        long likecount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likecount));
    }


    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null)
            return WendaUtil.getJSONString(999);
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }



}
