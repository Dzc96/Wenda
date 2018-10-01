package com.dzc.Wenda.service;

import com.dzc.Wenda.dao.FeedDao;
import com.dzc.Wenda.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {

    @Autowired
    private FeedDao feedDao;

    public boolean addFeed(Feed feed) {
        return feedDao.addFeed(feed) > 0;
    }


    public Feed getById(String id) {
        return feedDao.getFeedById(Integer.parseInt(id));
    }


    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDao.selectUserFeeds(maxId, userIds, count);
    }



}
