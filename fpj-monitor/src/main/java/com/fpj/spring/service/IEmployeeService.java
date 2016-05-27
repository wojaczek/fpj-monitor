package com.fpj.spring.service;

import com.fpj.client.dtos.IEmployeeDto;
import com.fpj.spring.dtos.EmployeePagingLoadResultBean;
import com.fpj.spring.entities.EntEmployee;

public interface IEmployeeService extends IGenericService<IEmployeeDto, EmployeePagingLoadResultBean, EntEmployee> {

}
