package com.tom.autobetter;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.tom.autobetter")
@EnableDynamoDBRepositories(basePackages = "com.tom.autobetter.repository.dynamodb")
@EnableJpaRepositories(basePackages = "com.tom.autobetter.repository.jpa")
@EntityScan(basePackages = "com.tom.autobetter.entity")
public class AutobetterApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutobetterApplication.class, args);
	}

}
