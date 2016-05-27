package com.fpj.spring.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fpj.spring.security.RestAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
@ComponentScan(basePackages="com.fpj.spring.security")
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired 
	DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception{
		builder.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.anyRequest().authenticated()
			.and().formLogin().loginProcessingUrl("/spring/login").defaultSuccessUrl("/spring/login/getRoles", true).permitAll()
			.and().logout().logoutUrl("/spring/logout")
			.and().exceptionHandling().defaultAuthenticationEntryPointFor(authenticationEntryPoint, new AntPathRequestMatcher("**"))
			.and().csrf().disable();
		
	}
	
	@Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }
}
