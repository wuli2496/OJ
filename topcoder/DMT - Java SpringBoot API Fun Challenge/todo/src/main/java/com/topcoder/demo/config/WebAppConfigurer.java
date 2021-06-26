package com.topcoder.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.topcoder.demo.interceptor.TokenInterceptor;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TokenInterceptor());
	}
	
}
