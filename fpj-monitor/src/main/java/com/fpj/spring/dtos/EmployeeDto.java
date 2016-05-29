package com.fpj.spring.dtos;

import java.util.Date;

import com.fpj.client.dtos.ICompanyDto;
import com.fpj.client.dtos.IEmployeeDto;

public class EmployeeDto extends IdentifiableDto implements IEmployeeDto{
	private String firstName;
	private String lastName;
	private ICompanyDto company;
	private Date visaExpiredDate;
	private Date workPermissionExpiryDate;
	private Date statementExpiryDate;
	
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
	public Date getVisaExpiredDate() {
		return visaExpiredDate;
	}
	public void setVisaExpiredDate(Date visaExpiredDate) {
		this.visaExpiredDate = visaExpiredDate;
	}
	@Override
	public ICompanyDto getCompany() {
		return company;
	}
	@Override
	public void setCompany(ICompanyDto company) {
		this.company=company;
	}
	@Override
	public Date getWorkPermissionExpiryDate() {
		return workPermissionExpiryDate;
	}
	@Override
	public void setWorkPermissionExpiryDate(Date workPermissionExpiryDate) {
		this.workPermissionExpiryDate=workPermissionExpiryDate;
	}
	@Override
	public Date getStatementExpiryDate() {
		return statementExpiryDate;
	}
	@Override
	public void setStatementExpiryDate(Date statementExpiryDate) {
		this.statementExpiryDate = statementExpiryDate;
	}
	
}
