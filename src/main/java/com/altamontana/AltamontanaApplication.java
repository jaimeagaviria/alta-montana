package com.altamontana;

import com.altamontana.repository.impl.AbstractRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.altamontana.*")
@EnableJpaRepositories(repositoryBaseClass = AbstractRepositoryImpl.class)
@EnableCaching
@EnableScheduling
@EnableAsync
public class AltamontanaApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(AltamontanaApplication.class, args);
	}

}
