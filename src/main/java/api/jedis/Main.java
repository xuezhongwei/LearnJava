package api.jedis;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

public class Main {
	private static JedisPool pool = null;
	private static Jedis jedis = getJedis();
	
	private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		int i = 0;
		try {
			long start = System.currentTimeMillis();
			while (true) {
				long end = System.currentTimeMillis();
				if (end - start >= 1000) {
					break;
				}
				i++;
				jedis.set("test" + i, i + "");
			}
		} finally {
			jedis.close();
		}
		System.out.println("redis per second operate " + i + " times");
	}
	
	private static void init() {
		if (pool == null) {
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
	}
	
	private static Jedis getJedis() {
		return pool.getResource();
	}
	
	public static void close(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
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
	
	/**
	 * 获得key的类型
	 */
	public static String type(String key) {
		return jedis.type(key);
	}
	
	/**
	 * 获取指定key的值,如果key不存在返回null，如果该Key存储的不是字符串，会抛出一个错误
	 * get只能获得字符串类型值
     */
	public static String get(String key) {
		String value = null;
		value = jedis.get(key);
		return value;
	}
	
	/**
	 * 设置key的值为value
	 */
	public static String set(String key, String value) {
		return jedis.set(key, value);
	}
	
	/**
	 * 删除指定的key
	 */
	public static Long del(String... keys) {
		return jedis.del(keys);
	}
	
	/**
	 * 通过key向指定的value值追加值
	 * 必须是字符串类型
	 */
	public static Long append(String key, String str) {
		return jedis.append(key, str);
	}
	
	/**
	 * 判断key是否存在
	 */
	public static boolean exists(String key) {
		return jedis.exists(key);
	}
	
	/**
	 * 如果key不存在则设置值，否则不设置
	 */
	public static long setnx(String key, String value) {
		return jedis.setnx(key, value);
	}
	
	/**
	 * 为指定的key设置到期时间
	 */
	public static void expire(String key, int seconds) {
		jedis.expire(key, seconds);
	}
	
	/**
	 * 从指定位置开始将原先value替换
	 */
	public static long setrange(String key, int offset, String str) {
		return jedis.setrange(key, offset, str);
	}
	
	/**
	 * 通过批量的key获得批量的字符串value
	 */
	public static List<String> mget(String... keys) {
		return jedis.mget(keys);
	}
	
	/**
	 * 批量设置key:value
	 */
	public static String mset(String... keysValues) {
		return jedis.mset(keysValues);
	}
	
	/**
	 * 通过key对value进行值+1操作，当value不是int类型时返回错误，当key不存在时则value为1
	 */
	public static long incr(String key) {
		return jedis.incr(key);
	}
	
	/**
	 * 通过key给指定的value加值，如果key不存在，则这时value为该值
	 */
	public static long incrBy(String key, long num) {
		return jedis.incrBy(key, num);
	}
	
	/**
	 * 通过key给指定的value进行值-1操作，当value不是int类型是返回错误，当key不存在时value为-1
	 */
	public static long decr(String key) {
		return jedis.decr(key);
	}
	
	/**
	 * 通过key给指定的value进行减值，如果key不存在，则这时value为负的该值
	 */
	public static long decrBy(String key, long num) {
		return jedis.decrBy(key, num);
	}
	
	/**
	 * 通过key获得字符串类型值的长度
	 */
	public static long strLen(String key) {
		return jedis.strlen(key);
	}
	
	/**
	 * 给hash类型设置值
	 */
	public static void hset(String key, String field, String value) {
		jedis.hset(key, field, value);
	}
	
	public static void hset(String key, Map<String, String> value) {
		jedis.hset(key, value);
	}
	/**
	 * 获得map的指定field值
	 */
	public static String hget(String key, String field) {
		return jedis.hget(key, field);
	}
	/**
	 * 获得map中所有内容
	 */
	public static Map<String, String> hgetAll(String key) {
		return jedis.hgetAll(key);
	}
	/**
	 * 判断map中是否存在指定field
	 */
	public static boolean hexists(String key, String field) {
		return jedis.hexists(key, field);
	}
	/**
	 * 获得map中field的数量
	 */
	public static long hlen(String key) {
		return jedis.hlen(key);
	}
	/**
	 * 删除map中指定的fields
	 */
	public static long hdel(String key, String... fields) {
		return jedis.hdel(key, fields);
	}
	/**
	 * 获得map中包含的所有fields
	 */
	public static Set<String> hkeys(String key) {
		return jedis.hkeys(key);
	}
	/**
	 * 获得map中包含的所有values
	 */
	public static List<String> hvals(String key) {
		return jedis.hvals(key);
	}
	
	/**
	 * 从list左边添加元素
	 */
	public static long lpush(String key, String... values) {
		return jedis.lpush(key, values);
	}
	/**
	 * 从list左边删除一个元素
	 */
	public static String lpop(String key) {
		return jedis.lpop(key);
	}
	/**
	 * 从list右边添加元素
	 */
	public static long rpush(String key, String... values) {
		return jedis.rpush(key, values);
	}
	/**
	 * 从list右边删除一个元素
	 */
	public static String rpop(String key) {
		return jedis.rpop(key);
	}
	/**
	 * 获得list长度
	 */
	public static long llen(String key) {
		return jedis.llen(key);
	}
	/**
	 * 获得list中指定index的元素
	 */
	public static String lindex(String key, long index) {
		return jedis.lindex(key, index);
	}
	/**
	 * 设置list中指定index元素的值
	 */
	public static String lset(String key, long index, String value) {
		return jedis.lset(key, index, value);
	}
	
	/**
	 * 删除list中指定index范围的元素
	 */
	public static String ldel(String key, long start, long stop) {
		return jedis.ltrim(key, start, stop);
	}
	
	/**
	 * 获得list中指定index范围内的元素
	 * 如果start 为 0 end 为 -1 则返回全部的list中的value
	 */
	public static List<String> lrange(String key, long start, long end) {
		return jedis.lrange(key, start, end);
	}
	
	/**
	 * 向set中添加元素
	 */
	public static long sadd(String key, String... members) {
		return jedis.sadd(key, members);
	}
	
	/**
	 * 删除set中指定值的元素
	 */
	public static long srem(String key, String... members) {
		return jedis.srem(key, members);
	}
	
	/**
	 * 从set中随机删除一个元素
	 */
	public static String spop(String key) {
		return jedis.spop(key);
	}
	/**
	 * 以第一个set为基准，获得set间的差集
	 */
	public static Set<String> sdiff(String... keys) {
		return jedis.sdiff(keys);
	}
	/**
	 * 获得set中元素的个数
	 */
	public static long scard(String key) {
		return jedis.scard(key);
	}
	/**
	 * 判断set中是否存在某元素
	 */
	public static boolean sismember(String key, String member) {
		return jedis.sismember(key, member);
	}
	/**
	 * 随机获得set中一个元素
	 */
	public static String srandmember(String key) {
		return jedis.srandmember(key);
	}
	/**
	 * 获得set中所有元素
	 */
	public static Set<String> smembers(String key) {
		return jedis.smembers(key);
	}
	/**
	 * 向zset中添加元素
	 */
	public static long zadd(String key, double score, String member) {
		return jedis.zadd(key, score, member);
	}
	/**
	 * 向zset中批量添加元素
	 */
	public static long zadd(String key, Map<String, Double> scoreMembers) {
		return jedis.zadd(key, scoreMembers);
	}
	/**
	 * 删除zset中指定值的元素
	 */
	public static long zrem(String key, String... members) {
		return jedis.zrem(key, members);
	}
	/**
	 * 获得zset中元素个数
	 */
	public static long zcard(String key) {
		return jedis.zcard(key);
	}
	
}
