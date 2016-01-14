package com.azeka.bakendSecure.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.azeka.bakendSecure.config.Constants;

@RestController
@RequestMapping(Constants.URL_API_BASE)
public class BackendSecureController {

	@RequestMapping(value="/contact",method=RequestMethod.GET,produces=MediaType.TEXT_PLAIN_VALUE)
	public String contact(){
		return "Hello contact page from REST SECURE backend ! (Only authenticated user)";		
	}
	
	@RequestMapping(value="/admin",method=RequestMethod.GET,produces=MediaType.TEXT_PLAIN_VALUE)
	public String admin(){
		return "Hello admin page from REST SECURE backend ! ( Only admin user) "  ;		
	}
	
	@RequestMapping(value="/postme",method=RequestMethod.POST,produces=MediaType.TEXT_PLAIN_VALUE)
	public String postme(){
		return "Hello poste me from REST SECURE backend ";		
	}
	
	@RequestMapping(value="/public/hellopublic",method=RequestMethod.GET,produces=MediaType.TEXT_PLAIN_VALUE)
	public String hellopublic(){
		return "Hello public page from REST SECURE backend! (No AUTHORIZATION needed, but it protected against CSRF)" ;		
	}
	 
}
