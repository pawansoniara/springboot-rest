package com.pawan.microservice.demo.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pawan.microservice.demo.rest.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenProvider {
	private static Logger log=Logger.getLogger(TokenProvider.class.getName());
	
	 private final String TOKEN_SECRET="h4of9eh48vmg02nfu30v27yen295hf5AA";
	 
	 public boolean validateToken(String authToken) {
	        try {
	            Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(authToken);
	            return true;
	        }  catch (MalformedJwtException e) {
	            log.info("Invalid JWT token.");
	        } catch (ExpiredJwtException e) {
	            log.info("Expired JWT token.");
	        } catch (UnsupportedJwtException e) {
	            log.info("Unsupported JWT token.");
	        } catch (IllegalArgumentException e) {
	            log.info("JWT token compact of handler are invalid.");
	        }catch (Exception e) {
	            log.info("JWT token compact of handler are invalid.");
	        }
	        return false;
	}   
	 
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        String jwt = request.getParameter("access_token");
        if (StringUtils.hasText(jwt)) {
            return jwt;
        }
        return null;
    }
    
    public Authentication  getAuthenticattion(String token) {

    	Claims claims = Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
    	
    	Set<String> aa=new HashSet<>();
    	aa.add("ROLE_USER");
    	Collection<? extends GrantedAuthority> authorities=aa.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		//UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(claims.getSubject(), "",authorities);
    	CustomeUserdetails user=new CustomeUserdetails(claims.getSubject(), token, authorities, true,true,true,true);
    	return new UsernamePasswordAuthenticationToken(user, token,authorities);
	}
    
    public String createToken(UserDTO userDto)  {
        
        return Jwts.builder()
                .setSubject(userDto.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong("3600000")))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET )
                .compact();
   
    }  
}
