package com.fpj.spring.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.fpj.client.dtos.IUserDto;
import com.fpj.spring.dtos.UserPagingLoadResultBean;
import com.fpj.spring.entities.EntUser;

public interface IUserService extends IGenericService<IUserDto, UserPagingLoadResultBean, EntUser>, UserDetailsService {

	void insertAdmin();

}
