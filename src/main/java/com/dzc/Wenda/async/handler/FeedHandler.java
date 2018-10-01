package com.dzc.Wenda.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.dzc.Wenda.async.EventHandler;
import com.dzc.Wenda.async.EventModel;
import com.dzc.Wenda.async.EventType;
import com.dzc.Wenda.model.EntityType;
import com.dzc.Wenda.model.Feed;
import com.dzc.Wenda.model.Question;
import com.dzc.Wenda.model.User;
import com.dzc.Wenda.service.FeedService;
import com.dzc.Wenda.service.FollowService;
import com.dzc.Wenda.service.QuestionService;
import com.dzc.Wenda.service.UserService;
import com.dzc.Wenda.utils.JedisAdapter;
import com.dzc.Wenda.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FeedHandler implements EventHandler {
    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @Autowired
    FeedService feedService;

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    QuestionService questionService;

    @Override
    public void doHandle(EventModel model) {
        //构造一个最新动态
        Feed feed = new Feed();
        feed.setCreateDate(new Date());
        feed.setType(model.getType().getValue());
        feed.setUserId(model.getActorId());
        feed.setData(buildFeedDate(model));
        if (feed.getData() == null) {
            return;
        }


        feedService.addFeed(feed);

        //获得该动态的实体（问题或用户）对应的所有的粉丝
        //返回的List中，是所有粉丝的用户ID
        List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER, model.getActorId(), Integer.MAX_VALUE);
        //followers.add(0);

        //最新动态放到Redis的list中保存
        for (int follower : followers) {
            String timelineKey = RedisKeyUtil.getTimelineKey(follower);
            jedisAdapter.lpush(timelineKey, String.valueOf(feed.getId()));
        }


    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT, EventType.FOLLOW});
    }


    private String buildFeedDate(EventModel model) {
        Map<String, String> map = new HashMap<>();
        User actor = userService.getUser(model.getActorId());
        if (actor == null) {
            return null;
        }

        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());


        //判断这个事件相关的是评论还是关注某个问题
        if (model.getType() == EventType.COMMENT ||
                (model.getType() == EventType.FOLLOW && model.getEntityType() == EntityType.ENTITY_QUESTION)) {

            Question question = questionService.getById(model.getEntityId());
            if (question == null) {
                return null;
            }

            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }


}
