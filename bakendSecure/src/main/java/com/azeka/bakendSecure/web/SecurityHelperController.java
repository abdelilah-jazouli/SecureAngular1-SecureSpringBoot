package com.azeka.bakendSecure.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.azeka.bakendSecure.config.Constants;
import com.azeka.bakendSecure.dto.UserDTO;
import com.azeka.bakendSecure.security.SecurityUtils;


@RestController
public class SecurityHelperController {

	static final Logger logger =  LoggerFactory.getLogger(SecurityHelperController.class) ;
	
	@RequestMapping(value=Constants.URL_RENEW_CSRF_TOKEN,
					method=RequestMethod.GET,
					produces=MediaType.TEXT_PLAIN_VALUE)
	public String renwewCSRFtoken(){
		
		return "Your CSRF token has been renewed, look at CSRF-TOKEN cookie ! ";
		
	}
	
	/**
     * Check if the user is authenticated and return its login.
     */
    @RequestMapping(value = Constants.URL_AUTHENTICATE,
    				method = RequestMethod.GET,
    				produces = MediaType.TEXT_PLAIN_VALUE)    
    public String isAuthenticated(HttpServletRequest request) {
    	logger.info("REST request to check if the current user is authenticated");
    	return SecurityUtils.isAuthenticated() ?  request.getRemoteUser() : null;        
    }
    
    /**
     * Get the current user with login and authorities.
     */
    @RequestMapping(value = Constants.URL_ACCOUNT,
    				method = RequestMethod.GET,
    				produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getAccount() {
    	UserDTO user = SecurityUtils.getUserWithAuthorities() ;
    	return ((user != null) ? new ResponseEntity<>(SecurityUtils.getUserWithAuthorities(), HttpStatus.OK): new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)) ;
    	        
    }

	
	
}
