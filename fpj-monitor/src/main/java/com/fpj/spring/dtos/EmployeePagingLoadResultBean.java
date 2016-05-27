package com.fpj.spring.dtos;

import com.fpj.client.dtos.IEmployeeDto;
import com.fpj.client.dtos.loadResults.IEmployeePagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@SuppressWarnings("serial")
public class EmployeePagingLoadResultBean extends PagingLoadResultBean<IEmployeeDto> implements IEmployeePagingLoadResult {
	
}
