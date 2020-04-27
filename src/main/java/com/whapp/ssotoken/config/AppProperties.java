package com.whapp.ssotoken.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@PropertySource("classpath:application-${spring.profiles.active}.properties")
@ConfigurationProperties("app")
@Getter @Setter
public class AppProperties {
	private String tokenSecret;
	private int tokenExpiration;
}
