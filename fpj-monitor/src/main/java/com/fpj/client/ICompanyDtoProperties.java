package com.fpj.client;

import com.sencha.gxt.core.client.ValueProvider;

public interface ICompanyDtoProperties extends IdPropertyAccess<ICompanyDto>{
	
	ValueProvider<ICompanyDto, String> companyName();


}
