package com.fpj.spring.dtos;

import com.fpj.client.ICompanyDto;

public class CompanyDto implements ICompanyDto {

	private Integer id;
	private String companyName;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id=id;
	}

	@Override
	public String getCompanyName() {
		return companyName;
	}

	@Override
	public void setCompanyName(String companyName) {
		this.companyName=companyName;
	}

}
