package com.fpj.spring.dtos;

import java.util.Date;

import com.fpj.client.IEmployeeDto;

public class EmployeeDto implements IEmployeeDto{
	private Integer id;
	private String firstName;
	private String lastName;
	private String companyName;

	private Date visaExpiredDate;
	
	@Override
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Override
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public Date getVisaExpiredDate() {
		return visaExpiredDate;
	}
	public void setVisaExpiredDate(Date visaExpiredDate) {
		this.visaExpiredDate = visaExpiredDate;
	}
	
}
