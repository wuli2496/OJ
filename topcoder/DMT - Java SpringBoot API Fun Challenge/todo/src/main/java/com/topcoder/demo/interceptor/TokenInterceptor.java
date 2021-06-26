package com.topcoder.demo.interceptor;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.topcoder.demo.config.TokenConfig;
import com.topcoder.demo.error.InvalidTokenException;

import cn.hutool.extra.spring.SpringUtil;

public class TokenInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String authorization = request.getHeader("Authorization");
		if (StringUtils.isBlank(authorization)) {
			throw new InvalidTokenException("");
		}
		
		String[] splits = StringUtils.split(authorization, " ");
		if (splits.length != 2) {
			throw new InvalidTokenException("");
		}
		
		String token = ((TokenConfig)SpringUtil.getBean(TokenConfig.class)).getToken();
		if (!Objects.equals(splits[1], token)) {
			throw new InvalidTokenException("");
		}
		
		return true;
	}

}
