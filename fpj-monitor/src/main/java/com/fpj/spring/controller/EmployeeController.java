package com.fpj.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpj.client.dtos.IEmployeeDto;
import com.fpj.spring.dtos.EmployeePagingLoadResultBean;
import com.fpj.spring.exception.NotFoundException;
import com.fpj.spring.service.IEmployeeService;
import com.fpj.spring.service.IGenericService;

@Controller
@RequestMapping("employee")
public class EmployeeController extends GenericController<IEmployeeDto, EmployeePagingLoadResultBean> {

	@Autowired
	IEmployeeService employeeService;
	@Override
	protected IGenericService<IEmployeeDto, EmployeePagingLoadResultBean, ?> getService() {
		return employeeService;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody IEmployeeDto saveChanges(@RequestBody IEmployeeDto dto) throws NotFoundException{
		return super.save(dto);
	}

}
