package com.azeka.bakendSecure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import com.azeka.bakendSecure.security.CsrfCookieGeneratorFilter;
import com.azeka.bakendSecure.security.CustomAccessDeniedHandler;
import com.azeka.bakendSecure.security.RestAuthenticationFailureHandler;
import com.azeka.bakendSecure.security.RestAuthenticationSuccessHandler;
import com.azeka.bakendSecure.security.RestLogoutSuccessHandler;
import com.azeka.bakendSecure.security.RestUnauthorizedEntryPoint;



/**
 * 
 * @author abdelilah
 *In a normal web application, whenever a secured resource is accessed Spring Security check the security context for the current user and will decide either to forward him to login page (if the user is not authenticated), or to forward him to the resource not authorised page (if he doesn’t have the required permissions).

In our scenario this is different, because we don’t have pages to forward to, we need to adapt and override Spring Security to communicate using HTTP protocols status only, below I liste the things to do to make Spring Security works best :

The authentication is going to be managed by the normal form login, the only difference is that the response will be on JSON along with an HTTP status which can either code 200 (if the autentication passed) or code 401 (if the authentication failed) ;
Override the AuthenticationFailureHandler to return the code 401 UNAUTHORIZED ;
Override the AuthenticationSuccessHandler to return the code 20 OK, the body of the HTTP response contain the JSON data of the current authenticated user ;
Override the AuthenticationEntryPoint to always return the code 401 UNAUTHORIZED. This will override the default behavior of Spring Security which is forwarding the user to the login page if he don’t meet the security requirements, because on REST we don’t have any login page ;
Override the LogoutSuccessHandler to return the code 20 OK ;
Like a normal web application secured by Spring Security, before accessing a protected service, it is mandatory to first authenticate by submitting the password and username to the Login URL.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private RestUnauthorizedEntryPoint authenticationEntryPoint;
	@Autowired
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
	@Autowired
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
	@Autowired
	private RestLogoutSuccessHandler restLogoutSuccessHandler ;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
				http				
					.csrf() //CSRF protection
						.requireCsrfProtectionMatcher(new AndRequestMatcher(
								// Apply CSRF protection to all paths that do NOT match the ones below
								// We disable CSRF at login/logout, but only for OPTIONS methods
								//We start by disabling CSRF on OPTIONS requests… Why? Because those are “safe” 
								//requests that do not modify anything server-side, and because we need OPTIONS 
								//for CORS to work!!! Indeed, remember that in some cases CORS uses OPTIONS 
								//requests to obtain the server’s agreement on sharing its resources… that’s the preflight
								//See : http://www.codesandnotes.be/2015/07/24/angularjs-web-apps-for-spring-based-rest-services-security-the-server-side-part-2-csrf/
								  new NegatedRequestMatcher(new AntPathRequestMatcher(Constants.URL_AUTHENTICATION, HttpMethod.OPTIONS.toString())),								  
								  new NegatedRequestMatcher(new AntPathRequestMatcher(Constants.URL_RENEW_CSRF_TOKEN, HttpMethod.GET.toString())),
								  new NegatedRequestMatcher(new AntPathRequestMatcher(Constants.URL_LOGOUT, HttpMethod.OPTIONS.toString()))))
						
				.and()  
					 
					 .addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class) //CSRF protection
					.exceptionHandling()
					 	.accessDeniedHandler(new CustomAccessDeniedHandler()) ////CSRF protection
					 	.authenticationEntryPoint(authenticationEntryPoint) //In order to provide a behavior that is more REST-friendly
				.and()
					.formLogin()
						.loginProcessingUrl(Constants.URL_AUTHENTICATION)
						.usernameParameter(Constants.AUTH_FORM_USERNAME_PARAMETER)
						.passwordParameter(Constants.AUTH_FORM_PASSEWORD_PARAMETER)
						.successHandler(restAuthenticationSuccessHandler) //In order to provide a behavior that is more REST-friendly 
						.failureHandler(restAuthenticationFailureHandler) //in order to provide a behavior that is more REST-friendly 
						.permitAll()
				.and()
					.logout()
						.logoutUrl(Constants.URL_LOGOUT)
						.logoutSuccessHandler(restLogoutSuccessHandler)
						.deleteCookies("JSESSIONID")
						.permitAll()				
				.and()
					.authorizeRequests() 
					.antMatchers(HttpMethod.OPTIONS,"/*/**").permitAll() //Requests of the OPTIONS type are always permitted (otherwise CORS preflights cannot be done).
					.antMatchers("/api/admin/**").hasAuthority(Constants.ROLE_ADMIN)
					.antMatchers("/api/contact/**").hasAnyAuthority(Constants.ROLE_ADMIN,Constants.ROLE_USER)
					.antMatchers("/api/public/**").permitAll()
					.antMatchers(Constants.URL_RENEW_CSRF_TOKEN).permitAll()
					.anyRequest().authenticated()
		 ;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication().withUser("abdou").password("abdou1").authorities(Constants.ROLE_USER);
		auth.inMemoryAuthentication().withUser("admin").password("admin1").authorities(Constants.ROLE_ADMIN);
	}

	
}
