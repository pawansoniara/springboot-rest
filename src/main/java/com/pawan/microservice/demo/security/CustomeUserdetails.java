package com.pawan.microservice.demo.security;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class CustomeUserdetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	String password;
	Collection<? extends GrantedAuthority> authorities;
	String username;
	boolean accountNonExpired=true;
	boolean enabled;
	boolean accountNonLocked;
	boolean credentialsNonExpired;
	
	
	public CustomeUserdetails(String username,String password, Collection<? extends GrantedAuthority> authorities,
			boolean enabled,boolean accountNonExpired,boolean accountNonLocked,boolean credentialsNonExpired) {
		super();
		this.password = password;
		this.authorities = authorities;
		this.username = username;
		this.enabled = enabled;
		this.accountNonExpired =accountNonExpired;
		this.accountNonLocked=accountNonLocked;
		this.credentialsNonExpired=credentialsNonExpired;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

}
