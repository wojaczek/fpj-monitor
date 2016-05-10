package com.fpj.client;


import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;

public interface IEmployeeDtoProperties extends IdPropertyAccess<IEmployeeDto> {

	
	ValueProvider<IEmployeeDto, String> firstName();
	
	ValueProvider<IEmployeeDto, String> lastName();

	ValueProvider<IEmployeeDto, ICompanyDto> company();

	ValueProvider<IEmployeeDto, Date> visaExpiredDate();

}
