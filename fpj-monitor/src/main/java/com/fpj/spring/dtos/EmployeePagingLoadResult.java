package com.fpj.spring.dtos;

import com.fpj.client.IEmployeeDto;
import com.fpj.client.IEmployeePagingoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@SuppressWarnings("serial")
public class EmployeePagingLoadResult extends PagingLoadResultBean<IEmployeeDto> implements IEmployeePagingoadResult {
	
}
