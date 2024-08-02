package com.bareuni.coredomain;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan("com.bareuni.coredomain")
@EnableJpaRepositories("com.bareuni.coredomain")
@EnableJpaAuditing
public class CoreDomainConfig {
}
