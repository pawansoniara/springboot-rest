package com.pawan.microservice.demo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

	private static final Logger log=LoggerFactory.getLogger(HealthCheckController.class);
	
	@Autowired
	private CircuitBreakerService circuitBreakerService;
	
	@RequestMapping(value = "/ping",method = RequestMethod.GET)
	public ResponseEntity<?> ping() throws InterruptedException{
		log.info("called ping");
		return new ResponseEntity<>("OK",HttpStatus.OK);
	}
	
	
	 @RequestMapping("/ping/circuit-breaker")
	 public String toRead() {
	    return circuitBreakerService.ping();
	 }
}
