package com.fpj.client.dtos.properties;

import com.fpj.client.dtos.ICompanyDto;
import com.fpj.client.dtos.IdPropertyAccess;
import com.sencha.gxt.core.client.ValueProvider;

public interface ICompanyDtoProperties extends IdPropertyAccess<ICompanyDto>{
	
	ValueProvider<ICompanyDto, String> companyName();


}
