package com.fpj.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpj.client.dtos.IEmployeeDto;
import com.fpj.client.dtos.IUserDto;
import com.fpj.spring.dtos.UserPagingLoadResultBean;
import com.fpj.spring.exception.NotFoundException;
import com.fpj.spring.service.IUserService;

@Controller
@RequestMapping("user")
public class UserController extends GenericController<IUserDto, UserPagingLoadResultBean> {

	@Autowired
	private IUserService service;

	@Override
	protected IUserService getService() {
		return service;
	}


	@RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody IUserDto saveChanges(@RequestBody IUserDto dto) throws NotFoundException{
		return super.save(dto);
	}
	
	
}
