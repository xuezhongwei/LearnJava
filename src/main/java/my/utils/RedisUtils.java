package my.utils;

import java.util.Collections;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

public final class RedisUtils {
	private static JedisPool pool = null;
	private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(10);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(5000);
		// testOnBorrow：在提取一个jedis实例时，是否提前进行验证操作；如果为true，则得到的jedis实例均是可用的
		// 如果为false，则得到的jedis实例不一定可用
		config.setTestOnBorrow(true);
		String host = "localhost";
		int port = 6379;
		pool = new JedisPool(config, host, port);
	}
	
	/**
	 * 获得分布式琐
	 */
	public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String value, int expireTime) {
		SetParams setParams = new SetParams();
		// if not exist 设置了此参数，则相当于setnx
		setParams.nx();
		// 设置key的失效时间
		setParams.px(expireTime);
        String result = jedis.set(lockKey, value, setParams);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
	/**
	 * 释放分布式琐
	 */
	public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
		// 使用lua脚本，为了保证操作的原子性
		// 释放锁，不能简单通过key来释放锁，还要校验value
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
}
