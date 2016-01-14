package com.azeka.bakendSecure.config;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * 
 * @author abdelilah
 
 * configuration
 */
@Configuration
public class CorsConfig {
	/**
	 * Global CORS configuration is defined by can be defined by registering a 
	 * WebMvcConfigurer bean with a customized addCorsMappings(CorsRegistry)
	 * By default all origins and GET, HEAD and POST methods are allowed.
	 * Origin = scheme + domain + port
	 * Use @CrossOrigin annotation in controller or method controller to fine-grained CORS
	 */	
//	 Work Only for URL API ( RestController ) 
//	@Bean
//	  public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//	      @Override
//	      public void addCorsMappings(CorsRegistry registry) {
//	          registry.addMapping("/api/**") ;	          
//	      }
//	  };
//	}	  
		/**
		 * 
		 * CORS Negotation :
		The browser tries to negotiate with our resource server to find out if it is allowed
		to access it according to the Common Origin Resource Sharing protocol. 
		It’s not an Angular JS responsibility, so just like the cookie contract it will work like this 
		with all JavaScript in the browser. 
		The two servers do not declare that they have a common origin, so the browser declines to send 
		the request and the UI is broken.
		To fix that we need to support the CORS protocol which involves a “pre-flight” OPTIONS request 
		and some headers to list the allowed behaviour of the caller. 
		Spring 4.2 might have some nice fine-grained CORS support.
		@see https://dzone.com/articles/resource-server-angular-js-and
	  * Work for all URL : API ( RestController ) and Spring specific authentication URL ( login and logout )
	  * @since Spring 4.2
	  * @return FilterRegistrationBean
	  */
	  @Bean
	  public FilterRegistrationBean corsFilter() {
	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      CorsConfiguration config = new CorsConfiguration();
	      config.setAllowCredentials(true); //Access-Control-Allow-Credentials : indicates whether the actual request can contain credentials or not
	      config.addAllowedOrigin("*"); //Access-Control-Allow-Origin : specifies the origin URL from which requests will be accepted.
	      								//Our API is public, all origin are allowed 
	      config.addAllowedHeader("*");
	      config.addAllowedMethod("OPTIONS"); //Is needed, for CORS negotiation : that’s the preflight  
	      config.addAllowedMethod("HEAD");
	      config.addAllowedMethod("GET");
	      config.addAllowedMethod("PUT");
	      config.addAllowedMethod("POST");
	      config.addAllowedMethod("DELETE");
	      config.addAllowedMethod("PATCH");
	      config.setMaxAge(3600L); //1h : defines how long the preflight will be cached, that is, how much time the browser has between what he announced it would request and the actual request
	      source.registerCorsConfiguration("/api/**", config);	      
	      final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
	      bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	      return bean;
	  }
}
