package com.dzc.Wenda.controller;

import com.dzc.Wenda.model.EntityType;
import com.dzc.Wenda.model.Feed;
import com.dzc.Wenda.model.HostHolder;
import com.dzc.Wenda.service.FeedService;
import com.dzc.Wenda.service.FollowService;
import com.dzc.Wenda.utils.JedisAdapter;
import com.dzc.Wenda.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    FeedService feedService;
    @Autowired
    FollowService followService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    JedisAdapter jedisAdapter;


    //推模式，从Redis中读取关注的东西的最新动态id
    @RequestMapping(path = "/pushfeeds", method = {RequestMethod.POST, RequestMethod.GET})
    public String getPushFeeds(Model model) {
        //判断当前用户是否登陆
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;


        //从Redis中读取到当前用户关注的实体的最新动态id (feedId)
        List<String> feedIds = jedisAdapter.lrange(RedisKeyUtil.getTimelineKey(localUserId), 0, 10);
        List<Feed> feeds = new ArrayList<>();

        //根据feedId来封装Feed对象，返回Feed对象的List到前台去解析显示
        for (String feedId : feedIds) {
            Feed feed = feedService.getById(feedId);
            if (feed != null) {
                feeds.add(feed);
            }
        }

        model.addAttribute("feeds", feeds);
        return "feeds";
    }


    @RequestMapping(path = "/pullfeeds", method = {RequestMethod.POST, RequestMethod.GET})
    public String getPullFeeds(Model model) {
        int localUserId = hostHolder.getUser() != null? hostHolder.getUser().getId() : 0;
        List<Integer> followees = new ArrayList<>();
        //找到当前用户关注的用户ID
        if (localUserId != 0) {
           followees =  followService.getFollowees(localUserId, EntityType.ENTITY_USER, Integer.MAX_VALUE);
        }

        List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE, followees, 10);
        model.addAttribute("feeds", feeds);
        return "feeds";

    }







}
