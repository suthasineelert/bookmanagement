package com.bookmanagement.bookmanagement.configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.bookmanagement.bookmanagement.repository")
public class JpaConfig {
}
