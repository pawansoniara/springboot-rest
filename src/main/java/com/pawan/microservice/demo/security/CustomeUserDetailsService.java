package com.pawan.microservice.demo.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCursor;
import com.pawan.microservice.demo.rest.dto.UserDTO;
import com.pawan.microservice.demo.utils.AppUtils;

@Service
public class CustomeUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MongoOperations mongo;
	
	
	public UserDTO getUser(String userName) {
		
		Document query=new Document("_id", userName);
		MongoCursor<Document> cusor = this.mongo.getCollection(AppUtils.USERS_COLLECTION_NAME).find(query).sort(new Document("_id", -1)).limit(1).iterator();
		String password=null;
		if(cusor.hasNext()){
			Document obj = cusor.next();
			password=(String) obj.get("password");
		}
		return new UserDTO(userName,password);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDTO userEntity = getUser(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        
        Set<String> aa=new HashSet<>();
    	aa.add("ROLE_USER");
    	Collection<? extends GrantedAuthority> authorities=aa.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    	
        CustomeUserdetails user=new CustomeUserdetails(userEntity.getUsername(), userEntity.getPassword(), authorities, true,true,true,true);
        
        return user;
	}

}
