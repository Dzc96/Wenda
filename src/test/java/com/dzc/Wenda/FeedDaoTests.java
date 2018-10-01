package com.dzc.Wenda;

import com.alibaba.fastjson.JSONObject;
import com.dzc.Wenda.dao.FeedDao;
import com.dzc.Wenda.model.EntityType;
import com.dzc.Wenda.model.Feed;
import com.dzc.Wenda.model.Question;
import com.dzc.Wenda.model.User;
import com.dzc.Wenda.service.QuestionService;
import com.dzc.Wenda.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedDaoTests {
    /**
     *     int addFeed(Feed feed);
     *
     *     Feed getFeedById(int id);
     *
     *     //这个maxId是干嘛的
     *     List<Feed> selectUserFeeds(@Param("maxId") int maxId,
     *                                @Param("userIds") List<Integer> userIds,
     *                                @Param("count") int count);
     */
    @Autowired
    private FeedDao feedDao;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    //模拟10010用户关注问题16的数据
    //模拟10011用户关注问题17的数据
    @Test
    public void testAddFeed() {
        Feed feed = new Feed();
        feed.setCreateDate(new Date());
        feed.setType(EntityType.ENTITY_QUESTION);
        feed.setUserId(10011);

        User user = userService.getUser(10011);
        Map<String, String> map = new HashMap<String ,String>();

        map.put("userId", String.valueOf(user.getId()));
        map.put("userHead", user.getHeadUrl());
        map.put("userName", user.getName());
        Question question = questionService.getById(16);
        map.put("questionId", "17");
        map.put("questionTitle",question.getTitle());
        String data = JSONObject.toJSONString(map);


        //下面要传一个JSON字符串
        feed.setData(data);
        int count = feedDao.addFeed(feed);
        System.out.println("影响行数:" + count);

    }



    @Test
    public void testGetFeedById() {
        Feed feed = feedDao.getFeedById(1);
        System.out.println(feed);
    }



    @Test
    public void testSelectUserFeeds() {
        List<Integer> userIds = new ArrayList<>();
        userIds.add(10010);
        userIds.add(10011);
        List<Feed> feeds = feedDao.selectUserFeeds(10, userIds, 10);
        System.out.println("size:" + feeds.size());
        for (Feed feed : feeds) {
            System.out.println(feed);
        }
    }




}
