package com.fpj.spring.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(value = "test", method = RequestMethod.GET)
	@Secured(value="ROLE_USER")
	public @ResponseBody void test(){
		return;
	}
	
	@RequestMapping(value="getRoles")
	public @ResponseBody List<String> getRoles(){
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority grantedAuthority : authorities) {
			roles.add(grantedAuthority.getAuthority());
		}
		return roles;
	}
	
	
}
