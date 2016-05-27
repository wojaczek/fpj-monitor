package com.fpj.client.dtos.properties;


import java.util.Date;

import com.fpj.client.dtos.ICompanyDto;
import com.fpj.client.dtos.IEmployeeDto;
import com.fpj.client.dtos.IdPropertyAccess;
import com.sencha.gxt.core.client.ValueProvider;

public interface IEmployeeDtoProperties extends IdPropertyAccess<IEmployeeDto> {

	
	ValueProvider<IEmployeeDto, String> firstName();
	
	ValueProvider<IEmployeeDto, String> lastName();

	ValueProvider<IEmployeeDto, ICompanyDto> company();

	ValueProvider<IEmployeeDto, Date> visaExpiredDate();

}
