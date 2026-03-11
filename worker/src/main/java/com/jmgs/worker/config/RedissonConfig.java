package com.jmgs.worker.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(
            @Value("${spring.data.redis.host}") String host,
            @Value("${spring.data.redis.port}") int port,
            @Value("${spring.data.redis.database}") int database,
            @Value("${spring.data.redis.password:}") String password
    ) {

        Config config = new Config();
        String address = "redis://" + host + ":" + port;
        var serverConfig = config.useSingleServer()
                .setAddress(address)
                .setDatabase(database);

        if (!password.isEmpty()) {
            serverConfig.setPassword(password);
        }

        return Redisson.create(config);
    }
}