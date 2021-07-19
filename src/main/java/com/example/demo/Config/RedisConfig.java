package com.example.demo.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis缓存配置类
 * @author szekinwin
 *
 */
@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig extends CachingConfigurerSupport{
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.pool.max-active}")
	private int maxActive;
	@Value("${spring.redis.pool.max-wait}")
	private int maxWait;
	@Value("${spring.redis.pool.max-idle}")
	private int maxIdle;
	@Value("${spring.redis.pool.min-idle}")
	private int minIdle;
	@Value("${spring.redis.timeout}")
	private int timeOut;

	private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);
	/**
	 * JedisPool注入
	 * @return
	 */
	@Bean
	public JedisPool redisPoolFactory(){
		JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWait);
		jedisPoolConfig.setMinIdle(minIdle);

		JedisPool jedisPool=new JedisPool(jedisPoolConfig,host,port,timeOut);
		log.info("Jedis注入成功");
		return jedisPool;
	}
	
	/*	@Value("${spring.redis.host}")
	    private String host;
	    @Value("${spring.redis.port}")
	    private int port;
	    @Value("${spring.redis.timeout}")
	    private int timeout;*//*
	    
	     
	  
	    
	    @Bean
	    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
	        StringRedisTemplate template = new StringRedisTemplate(factory);
	        setSerializer(template);//设置序列化工具
	        template.afterPropertiesSet();
	        return template;
	    }
	     private void setSerializer(StringRedisTemplate template){
	            @SuppressWarnings({ "rawtypes", "unchecked" })
	            Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
	            ObjectMapper om = new ObjectMapper();
	            om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	            om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	            jackson2JsonRedisSerializer.setObjectMapper(om);
	            template.setValueSerializer(jackson2JsonRedisSerializer);
	     }
	     
	     //缓存管理器
	     @Bean
	     public CacheManager cacheManager(RedisConnectionFactory factory) {
	         RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();  // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
	         config = config.entryTtl(Duration.ofMinutes(1))     // 设置缓存的默认过期时间，也是使用Duration设置
	                 .disableCachingNullValues();     // 不缓存空值

	         // 设置一个初始化的缓存空间set集合
	         Set<String> cacheNames =  new HashSet<>();
	         cacheNames.add("my-redis-cache1");
	         cacheNames.add("my-redis-cache2");

	         // 对每个缓存空间应用不同的配置
	         Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
	         configMap.put("my-redis-cache1", config);
	         configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));

	         RedisCacheManager cacheManager = RedisCacheManager.builder(factory)     // 使用自定义的缓存配置初始化一个cacheManager
	                 .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
	                 .withInitialCacheConfigurations(configMap)
	                 .build();
	         return cacheManager;
	     }*/
	    
}