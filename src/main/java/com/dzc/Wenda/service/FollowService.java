package com.dzc.Wenda.service;

import com.dzc.Wenda.utils.JedisAdapter;
import com.dzc.Wenda.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {
    @Autowired
    private JedisAdapter jedisAdapter;


    /**
     * 用户关注某个实体，可以关注问题或用户
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean follow(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Date date = new Date();
        // 实体的粉丝增加当前用户
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
        // 当前用户对这类实体关注+1
        tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
      //  return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
        return ret.size() == 2;
    }


    public boolean unfollow(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction transaction = jedisAdapter.multi(jedis);
        transaction.zrem(followerKey, String.valueOf(userId));
        transaction.zrem(followeeKey, String.valueOf(userId));
        List<Object> ret = jedisAdapter.exec(transaction, jedis);
       // return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
        return ret.size() == 2;
    }


    public List<Integer> getFollowers(int entityType, int entityId, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, 0, count));
    }


    public List<Integer> getFollowers(int entityType, int entityId, int offset, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, offset, offset+count));
    }


    public List<Integer> getFollowees(int userId, int entityType, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, 0, count));
    }

    public List<Integer> getFollowees(int userId, int entityType, int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, offset, offset+count));
    }


    //得到某实体的粉丝数量
    public long getFollowerCount(int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zcard(followerKey);
    }

    //得到某用户关注的实体的数量
    public long getFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return jedisAdapter.zcard(followeeKey);
    }


    //判断用户是否关注了某个实体
    public boolean isFollower(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zscore(followerKey, String.valueOf(userId)) != null;
    }







    private List<Integer> getIdsFromSet(Set<String> idset) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (String str : idset) {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }





}
