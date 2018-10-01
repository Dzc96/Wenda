package com.dzc.Wenda.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class JedisAdapter implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    //Redis连接池
    private JedisPool jedisPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化一下连接池
        jedisPool = new JedisPool("redis://localhost:6379/10");
    }


    /**********************下面是点赞和异步消息队列的Redis相关方法*************************/


    //先从连接池中获取一个Jedis对象
    //往集合中添加一个键值对
    public long sadd(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }

    //删除集合中的一个键值对
    public long srem(String key, String value) {
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.srem(key,value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }


    //返回集合中元素的数量
    public long scard(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }



    /**
     * 判断是否在set里面，1则表示在,0=则表示不存在
     */
    public boolean sismember(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return false;
    }


    //brpop命令移出并获取列表最后一个元素，若没有元素就阻塞，只要有元素弹出为止
    public List<String> brpop(int timeout, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.brpop(timeout, key);

        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;
    }


    //往list的列表头部插入一个值
    public long lpush(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }


    public List<String> lrange(String key, int start, int end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;

    }


    /**********************下面是操作SNS关注列表的Redis相关方法*************************/


    public long zadd(String key, double score, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zadd(key, score, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }


    public Jedis getJedis() {
        return jedisPool.getResource();
    }


    public Transaction multi(Jedis jedis) {
        try {
            return jedis.multi();
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;
    }


    /**
     * 执行事物块里面的命令
     * @param tx
     * @param jedis
     * @return
     */
    public List<Object> exec(Transaction tx, Jedis jedis) {
        try {
            return tx.exec();
        } catch (Exception e) {
            logger.error("发生异常", e.getMessage());
            tx.discard();
        } finally {
            if (tx != null) {
                try {
                    tx.close();
                } catch (IOException e) {
                    logger.error("关闭redis事物发生异常" + e.getMessage());
                }
            }
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }


    public Set<String> zrange(String key, int start, int end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;
    }


    //根据分数值从大到小排序这个集合，并且限制了取出的位数
    public Set<String> zrevrange(String key, int start, int end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;
    }


    public long zcard(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zcard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }


    //zscore命令本身是获取某个集合中对应元素的分数，如果没有，返回null
    //所以也可以根据是否返回null，判断集合中是否有该元素
    public Double zscore(String key, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zscore(key, member);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;
    }

    public long zrem(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrem(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }


















}
