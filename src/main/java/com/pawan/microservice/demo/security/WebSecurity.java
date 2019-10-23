package com.pawan.microservice.demo.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final CustomeUserDetailsService usersService;
    
    private final TokenProvider tokenProvider;
   
    private final BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
    
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
   
    @Autowired
    public WebSecurity(CustomeUserDetailsService usersService,TokenProvider tokenProvider,AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.usersService = usersService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        
     //   System.out.println(bCryptPasswordEncoder.encode("test"));
    }
    
    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                .userDetailsService(usersService)
                    .passwordEncoder(bCryptPasswordEncoder);
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }
    
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
    	DefaultHttpFirewall  firewall = new DefaultHttpFirewall ();
        firewall.setAllowUrlEncodedSlash(true);    
        return firewall;
    }
    
    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
    	 web
         .ignoring()
         .antMatchers("/")
         .antMatchers("/**.html")
         .antMatchers("/**.css")
         .antMatchers("/**.map")
         .antMatchers("/**.js")
         .antMatchers("/**.js.map")
         .antMatchers("/**.ico","/**.png","/**.jpeg","/**.gif");
    	 
    	 web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
      
        http.authorizeRequests()
        		.antMatchers(HttpMethod.POST,"/api/login").permitAll()
        		.antMatchers(HttpMethod.GET,"/api/ping").permitAll()
                .anyRequest().authenticated();
        
        http.apply(new JWTConfigurer(tokenProvider));
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
    }
    
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
      return authenticationManager();
    }
    
   
    
    
}