package com.fpj.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpj.client.ICompanyDto;
import com.fpj.spring.dtos.CompanyPagingLoadResultBean;
import com.fpj.spring.exception.NotFoundException;
import com.fpj.spring.service.CompanyService;
import com.fpj.spring.service.GenericService;
@Controller
@RequestMapping("company")
public class CompanyController extends GenericController<ICompanyDto, CompanyPagingLoadResultBean>{

	@Autowired
	CompanyService service;
	
	@Override
	protected GenericService<ICompanyDto, CompanyPagingLoadResultBean, ?> getService() {
		return service;
	}

	@RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ICompanyDto saveChanges(@RequestBody ICompanyDto dto) throws NotFoundException{
		return super.save(dto);
	}
	
}
