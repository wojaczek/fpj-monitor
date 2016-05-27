package com.fpj.spring.dtos;

import com.fpj.client.dtos.ICompanyDto;

public class CompanyDto extends IdentifiableDto implements ICompanyDto {

	private String companyName;

	@Override
	public String getCompanyName() {
		return companyName;
	}

	@Override
	public void setCompanyName(String companyName) {
		this.companyName=companyName;
	}

}
