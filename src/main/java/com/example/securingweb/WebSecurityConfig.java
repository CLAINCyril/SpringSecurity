package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity //to enable Spring Security’s web security support and provide the Spring MVC integration. It also extends 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	Environment environment;

	private final String USER = "username2";
	private final String PASSWORD = "password";



	/**method sets up an in-memory user store with a single user.
	 That user is given a user name of user, a password of password, and a role of USER.
	 **/
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username(environment.getRequiredProperty(USER))
				.password(environment.getRequiredProperty(PASSWORD))
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
	/**
	 method defines which URL paths should be secured and which should not.
	 Specifically, the / and /home paths are configured to not require any authentication.
	 All other paths must be authenticated.
	 
	  **/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/home").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}

}