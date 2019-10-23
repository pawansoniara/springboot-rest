package com.pawan.microservice.demo.security;


import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationFilter extends GenericFilterBean {
	
	private static Logger log=Logger.getLogger(AuthenticationFilter.class.getName());
	
    private final TokenProvider tokenProvider;
   
   
    
    public AuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider=tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpServletRequest = (HttpServletRequest) req;
      
    	String jwt = tokenProvider.resolveToken(httpServletRequest);
    	 if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
             Authentication authentication;
 			try {
 				authentication = tokenProvider.getAuthenticattion(jwt);
 				SecurityContextHolder.getContext().setAuthentication(authentication);
 			} catch (Exception e) {
 				log.info(e.getMessage());
 			}
         }
    	
    	 chain.doFilter(req, res);
    }
    
    
    
}