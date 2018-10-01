package com.dzc.Wenda.async;

import com.alibaba.fastjson.JSON;
import com.dzc.Wenda.utils.JedisAdapter;
import com.dzc.Wenda.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    //EventType表示登陆，点赞，评论，关注，取消关注这些事件，每种事件后续有多种处理器
    //消息队列根据这些事件类型，对应不同的多种处理器挨个处理
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for (EventType type : eventTypes) {
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }


        //启动线程来执行每个事件模型对应的处理器(可能是多个，依次执行)
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);

                    for (String message : events) {
                        if (message.equals(key)) {
                            continue;
                        }
                        //反序列化事件模型对象
                        EventModel eventModel = JSON.parseObject(message, EventModel.class);
                        if (!config.containsKey(eventModel.getType())) {
                            logger.error("不能识别的事件!");
                            continue;
                        }

                        for (EventHandler handler : config.get(eventModel.getType())) {
                            handler.doHandle(eventModel);
                        }

                    }
                }
            }
        });

        thread.start();


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
