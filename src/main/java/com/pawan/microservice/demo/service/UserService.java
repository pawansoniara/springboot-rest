package com.pawan.microservice.demo.service;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pawan.microservice.demo.rest.dto.UserDTO;
import com.pawan.microservice.demo.utils.AppUtils;

@Service
public class UserService {

	@Autowired
	private MongoOperations mongo;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
	 
	public void updateUser(UserDTO dto) {
		Document query=new Document("_id", dto.getUsername());
		Document update=new Document();
		update.append("$set", new Document("password", bCryptPasswordEncoder.encode(dto.getPassword())));
		this.mongo.getCollection(AppUtils.USERS_COLLECTION_NAME).updateOne(query, update);
	}
	
}

