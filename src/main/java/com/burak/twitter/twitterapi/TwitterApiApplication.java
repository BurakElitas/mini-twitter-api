package com.burak.twitter.twitterapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TwitterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterApiApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper(){
		return  new ModelMapper();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
