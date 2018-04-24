package com.altamontana;

import com.altamontana.repository.impl.AbstractRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

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

	/***
	 * Este bean se implementa para poder atnder las peticiones Angular y evitar mostrar el mensaje de error 404
	 * @return
	 */
	@Bean
	ErrorViewResolver supportPathBasedLocationStrategyWithoutHashes() {
		return new ErrorViewResolver() {
			@Override
			public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
				return status == HttpStatus.NOT_FOUND
						? new ModelAndView("index.html", Collections.<String, Object>emptyMap(), HttpStatus.OK)
						: null;
			}
		};
	}
}
