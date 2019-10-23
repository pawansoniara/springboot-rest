package com.pawan.microservice.demo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ServiceOneRestController {

	private static final Logger log=LoggerFactory.getLogger(ServiceOneRestController.class);
	
	@RequestMapping(value = "/ping",method = RequestMethod.GET)
	public ResponseEntity<?> ping() throws InterruptedException{
		log.info("called ping");
//		Thread.sleep(2000);
		log.info("finish ping");
		return new ResponseEntity<>("OK",HttpStatus.OK);
	}
}
