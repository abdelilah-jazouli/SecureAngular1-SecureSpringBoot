package com.azeka.bakendSecure.config;

/**
 * Application constants.
 */
public final class Constants {
	    
    /*****************
     * Spring security 
     ****************/
    //Form authentification
    public static final String AUTH_FORM_USERNAME_PARAMETER="j_username";				//form login
    public static final String AUTH_FORM_PASSEWORD_PARAMETER="j_password";				//form password. In production use SSL
    
    //Roles & Authority
    public static final String ROLE_ADMIN="ROLE_ADMIN" ;
    public static final String ROLE_USER="ROLE_USER" ;
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /************************
     * API GETEWAY
     *************************/
    //Api URL
    public static final String URL_API_BASE ="/api" ;
    public static final String URL_ALL_API = URL_API_BASE + "/**" ;
    public static final String URL_AUTHENTICATION =URL_API_BASE +"/authentication" ;  	//Send request to login
    public static final String URL_AUTHENTICATE = URL_API_BASE +"/authenticate" ;     	//Send request to check is current user is authenticated
    public static final String URL_LOGOUT =URL_API_BASE +"/logout" ;                  	//logout and clean the current session
    public static final String URL_RENEW_CSRF_TOKEN = URL_API_BASE +"/renwewCSRFtoken" ;//request to renew CSRF token
    public static final String URL_PUBLIC = URL_API_BASE +"/public/**" ;				//No authorization needed, but protected against CSRF attack. Only GET method allowed 
	public static final String URL_ACCOUNT = URL_API_BASE +"/account";					//Retrieve user login and authorities
    
    private Constants() {
    } 
    
}
