package com.fpj.spring.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpj.client.dtos.ELoginStatus;
import com.fpj.client.dtos.EntUserRole;
import com.fpj.spring.dtos.LoginDto;

@Controller("login")
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(value = "logout", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void test(){
		return;
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value="getRoles")
	public @ResponseBody LoginDto getRoles(){
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		List<EntUserRole> roles = new ArrayList<EntUserRole>();
		for (GrantedAuthority grantedAuthority : authorities) {
			roles.add(EntUserRole.valueOf(grantedAuthority.getAuthority()));
		}
		LoginDto result = new LoginDto();
		result.setStatus(ELoginStatus.SUCCESS);
		result.setRoles(roles);
		return result;
	}
	
	@RequestMapping(value="error", method=RequestMethod.POST)
	public @ResponseBody LoginDto loginError(HttpServletRequest request, HttpServletResponse response){
	//	String message = ((Exception)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).getMessage();
		LoginDto result = new LoginDto();
		result.setStatus(ELoginStatus.INVALID_LOGIN);
		//result.setMessage(message);
		return result;
	}
	
	
}
