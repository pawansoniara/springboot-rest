package com.pawan.microservice.demo.rest;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class CircuitBreakerService {


	  private final RestTemplate restTemplate;

	  public CircuitBreakerService(RestTemplate rest) {
	    this.restTemplate = rest;
	  }

	  @HystrixCommand(fallbackMethod = "reliable")
	  public String ping() {
	    URI uri = URI.create("http://localhost:8090/ping");

	    return this.restTemplate.getForObject(uri, String.class);
	  }

	  public String reliable() {
	    return "Cloud Native Java (O'Reilly)";
	  }
	  
}
