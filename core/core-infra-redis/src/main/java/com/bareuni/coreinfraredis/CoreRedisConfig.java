package com.bareuni.coreinfraredis;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories("com.bareuni.coreinfraredis")
public class CoreRedisConfig {
}
