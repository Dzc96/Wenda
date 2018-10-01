package com.dzc.Wenda.async;

import com.alibaba.fastjson.JSONObject;
import com.dzc.Wenda.utils.JedisAdapter;

import com.dzc.Wenda.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {


    @Autowired
    private JedisAdapter jedisAdapter;


    //把事件模型对象序列化，推送到基于Redis的消息队列中
    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }




}





