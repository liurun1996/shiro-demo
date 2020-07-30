package com.lr.shiro;

/**
 * @author zdjs 1992lcg@163.com
 * @version V1.0
 */

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 *
 */
public class RedisManager {


	private   String host = "127.0.0.1";


	private   int port = 6378;

	// 0 - never expire
	private int expire = 0;

	//timeout for jedis try to connect to redis server, not expire time! In milliseconds

	private int timeout = 5000;

	@Value("${spring.redis.password}")
	private String password = "";

	private static JedisPool jedisPool = null;

	public RedisManager() {

	}

	/**
	 * 初始化方法
	 */
	public void init() {
		if (jedisPool == null) {
			if (password != null && !"".equals(password)) {
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
			} else if (timeout != 0) {
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout);
			} else {
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port);
			}

		}
	}

	/**
	 * get value from redis
	 *
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key) {
		byte[] value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.get(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}

	/**
	 * set
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] set(byte[] key, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (this.expire != 0) {
				jedis.expire(key, this.expire);
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}

	/**
	 * 添加key value
	 *
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 添加key value 并且设置存活时间
	 *
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(String key, String value, int liveTime) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			jedis.expire(key, liveTime);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 添加key value
	 *
	 * @param key
	 * @param value
	 */
	public String get(String key) {
		String value;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.get(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}

	/**
	 * set
	 *
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key, byte[] value, int expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}

	/**
	 * del
	 *
	 * @param key
	 */
	public void del(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * del
	 *
	 * @param key
	 */
	public void del(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * flush
	 */
	public void flushDB() {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.flushDB();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * size
	 */
	public Long dbSize() {
		Long dbSize = 0L;
		Jedis jedis = jedisPool.getResource();
		try {
			dbSize = jedis.dbSize();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return dbSize;
	}

	public boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		Boolean exists = false;
		try {
			exists = jedis.exists(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return exists;
	}

	/**
	 * 添加key value 并且设置存活时间
	 *
	 * @param key
	 * @param value
	 */
	public String set(String key, String value, String nxxx, String expx, int time) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.set(key, value, nxxx, expx, time);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * keys
	 *
	 * @return
	 */
	public Set<byte[]> keys(String pattern) {
		Set<byte[]> keys = null;
		Jedis jedis = jedisPool.getResource();
		try {
			keys = jedis.keys(pattern.getBytes());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return keys;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * keys
	 * @return
	 */
	public Set<String> keysStr(String pattern) {
		Set<String> keys = null;
		Jedis jedis = jedisPool.getResource();
		try {
			keys = jedis.keys(pattern);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return keys;
	}
}
