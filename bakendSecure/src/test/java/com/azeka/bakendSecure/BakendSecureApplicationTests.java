package com.azeka.bakendSecure;



import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeTest;

import com.azeka.bakendSecure.config.Constants;
import com.azeka.bakendSecure.dto.UserDTO;


/**
 * @author abdelilah JAZOULI
 * Azeka Technologies
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BakendSecureApplication.class)
@WebAppConfiguration
public class BakendSecureApplicationTests {
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}
	
	@BeforeTest
	public void mustLogout() throws Exception {
		//must logout before every unit test
		mvc.perform(get(Constants.URL_LOGOUT).with(csrf().asHeader())) ;
	}
	
	@Test
	@WithMockUser
	public void requestForbiden() throws Exception {
		mvc.perform(get("/api/sommeUrl"))
				// Ensure we got forbidden access, 
				.andExpect(status().isForbidden());		//403		
	}
	
	@Test
	public void requestUnauthorized() throws Exception {
		mvc.perform(get("/api/sommeUrl").with(csrf().asHeader()))
				// Ensure we got an authorized access ( without principal, @WithMockUser missed)
				.andExpect(status().isUnauthorized());	//401			
	}
	
	@Test
	public void authenticationFailed() throws Exception {
		mvc.perform(post(Constants.URL_AUTHENTICATION)
		         .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				 .with(csrf().asHeader())
				 .param(Constants.AUTH_FORM_USERNAME_PARAMETER, "abdou")
				 .param(Constants.AUTH_FORM_PASSEWORD_PARAMETER, "badPassword")
		   )
				.andExpect(status().isUnauthorized())	//401			
				.andExpect(unauthenticated());
	}
	@Test
	public void authenticationSuccess() throws Exception {
		mvc.perform(post(Constants.URL_AUTHENTICATION)
				         .contentType(MediaType.APPLICATION_FORM_URLENCODED)
						 .with(csrf().asHeader())
						 .param(Constants.AUTH_FORM_USERNAME_PARAMETER, "abdou")
						 .param(Constants.AUTH_FORM_PASSEWORD_PARAMETER, "abdou1")
				   )
				.andExpect(status().isOk())		//200		
				.andExpect(authenticated());
	}
	
	@Test
	@WithMockUser(username="goodTester",password="goodTester123", roles={"ADMIN"})
    public void logoutSuccess() throws Exception {
		mvc.perform(post("/api/logout")
                .with(csrf().asHeader()))
                .andExpect(status().isOk()); //200
    }
	
	@Test
	@WithMockUser 	//  defaults ==> username "user", password "password", and role "USER"
	public void requestNotFoundURL() throws Exception {
		mvc.perform(get("/sommeUrl").with(csrf().asHeader()))
				// Ensure we can not serve /sommeUrl pattern
				.andExpect(status().isNotFound());		//404		
	}
	
	@Test
	@WithMockUser(username="goodTester",password="goodTester123", roles={"USER","ADMIN"})
	public void requestContatUrlWithUser() throws Exception {
		mvc.perform(get("/api/contact")
					.with(csrf().asHeader())					
					)
				// Ensure we got past Security
				.andExpect(status().is(HttpServletResponse.SC_OK)) //200
				// Ensure it appears we are authenticated
				.andExpect(authenticated().withUsername("goodTester"))				
				.andExpect(authenticated().withRoles("USER","ADMIN"))
				;
	}
	
	@Test
	@WithMockUser(username="goodTester",password="goodTester123", roles={"USER"})
	public void requestOnlyAdminAccess() throws Exception {
		mvc.perform(get("/api/admin")
				.with(csrf().asHeader()  ))
				//Ensure it appears authenticated
				.andExpect(authenticated().withUsername("goodTester").withRoles("USER"))
				//Ensure it appears we can not access admin URL with USER role
				.andExpect(status().isForbidden() ) ; //403 
	}
	
	@Test
	@WithMockUser(username="goodTester",password="goodTester123", roles={"USER"})
	public void getAccount() throws Exception {
		Set<String> authorities = new HashSet<>() ;
		authorities.add("ROLE_USER") ;
		UserDTO usrDto = new UserDTO("goodTester", "PROTECTED", authorities) ;
		String jsonContent = UtilTestHelper.convertObjectToJson(usrDto); 
		
		mvc.perform(get(Constants.URL_ACCOUNT)
				.with(csrf().asHeader()  ))
				//Ensure it appears authenticated
				.andExpect(authenticated().withUsername("goodTester").withRoles("USER"))
				//Make sure the content type is application/json
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//Make sure we receive the excepted content
				.andExpect(content().string(jsonContent) ) ;
	}
	
	@Test
	@WithMockUser(username="goodTester",password="goodTester123", roles={"ADMIN"})
	public void isAuthenticated() throws Exception{
		mvc.perform(get(Constants.URL_AUTHENTICATE)
				.with(csrf().asHeader()  )
				)
				//Make sure  content type is text/plain
				.andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE))
				//Make sure we receive user login
				.andExpect(content().string("goodTester") ) ;
	}
	@Test
	public void hellopublic() throws Exception {
		mvc.perform(get("/api/public/hellopublic")
				.with(csrf().asHeader()  ))
				//Make sure  content type is text/plain
				.andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE))
				//Make sure the user is anonymous
				.andExpect(authenticated().withRoles("ANONYMOUS"))
				//Ensure it past security
				.andExpect(status().isOk()) ;
		
	}
	
	
	

}
