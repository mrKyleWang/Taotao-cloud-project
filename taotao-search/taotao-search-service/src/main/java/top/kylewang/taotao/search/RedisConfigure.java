package top.kylewang.taotao.search;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import top.kylewang.taotao.search.jedis.JedisClient;
import top.kylewang.taotao.search.jedis.JedisClientPool;

/**
 * @author Kyle.Wang
 * 2018/1/27 0027 11:11
 */
@Configuration
public class RedisConfigure {

    @Bean
    public JedisPool getJedisPool(){
        return new JedisPool();
    }

    @Bean
    public JedisClient jedisClient(){
        return new JedisClientPool();
    }

}
