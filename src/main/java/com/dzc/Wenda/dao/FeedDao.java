package com.dzc.Wenda.dao;

import com.dzc.Wenda.model.Feed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeedDao {
    int addFeed(Feed feed);


    Feed getFeedById(int id);


    //这个maxId是干嘛的
    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);



}
