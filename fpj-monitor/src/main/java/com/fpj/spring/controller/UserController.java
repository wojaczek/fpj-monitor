package com.fpj.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpj.client.dtos.IUserDto;
import com.fpj.spring.dtos.UserPagingLoadResultBean;
import com.fpj.spring.exception.NotFoundException;
import com.fpj.spring.service.IUserService;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;

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
	@Secured("ROLE_ADMIN")
	public @ResponseBody IUserDto saveChanges(@RequestBody IUserDto dto) throws NotFoundException{
		return super.save(dto);
	}
	
	@Override
	@Secured("ROLE_ADMIN")
	public UserPagingLoadResultBean listAll() {
		return super.listAll();	
	}
	
	@Override
	@Secured("ROLE_ADMIN")
	public UserPagingLoadResultBean listAllFiltered(FilterPagingLoadConfigBean loadConfig) {
		return super.listAllFiltered(loadConfig);
	}
	
	@Override
	@Secured("ROLE_ADMIN")
	public IUserDto create() {
		return super.create();
	}
	
	@Override
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="delete/{id}", method= RequestMethod.GET,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable Integer id) {
		super.delete(id);
	}
	
}
