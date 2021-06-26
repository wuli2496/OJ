package com.topcoder.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration(proxyBeanMethods=false)
@Getter
public class TokenConfig {
	@Value("${todo.app.token}")
	private  String token;

}
