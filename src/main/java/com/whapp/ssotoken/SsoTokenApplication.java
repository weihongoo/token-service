package com.whapp.ssotoken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SsoTokenApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(SsoTokenApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SsoTokenApplication.class, args);
	}
}
