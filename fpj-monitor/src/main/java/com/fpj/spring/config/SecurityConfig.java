package com.fpj.spring.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fpj.spring.security.RestAuthenticationEntryPoint;
import com.fpj.spring.service.IUserService;

@EnableWebSecurity
@Configuration
@ComponentScan(basePackages="com.fpj.spring.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired 
	DataSource dataSource;
	@Autowired
	IUserService userService;
	
	@Autowired
	public void configure(AuthenticationManagerBuilder builder) throws Exception{
		//builder.inMemoryAuthentication().withUser("user").password("password").roles("USER");
		builder.authenticationProvider(authProvider());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin().loginProcessingUrl("/spring/login")
				.defaultSuccessUrl("/spring/login/getRoles", true)
				.failureForwardUrl("/spring/login/error")
			.and().logout().logoutUrl("/spring/logout").logoutSuccessUrl("/spring/login/logout")
			.and().exceptionHandling().defaultAuthenticationEntryPointFor(authenticationEntryPoint, new AntPathRequestMatcher("**"))
			.and().csrf().disable();
		
	}
	
	@Bean
	AuthenticationProvider authProvider(){
		DaoAuthenticationProvider result = new DaoAuthenticationProvider();
		result.setSaltSource(new SaltSource() {
			
			@Override
			public Object getSalt(UserDetails user) {
				return "QX";
			}
		});
		result.setUserDetailsService(userService);
		result.setPasswordEncoder(new ShaPasswordEncoder());
		return result;
	}

	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
