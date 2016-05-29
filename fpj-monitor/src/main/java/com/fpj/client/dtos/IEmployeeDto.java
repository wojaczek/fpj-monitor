package com.fpj.client.dtos;

import java.util.Date;

public interface IEmployeeDto extends IIdentifiableDto {

	Date getVisaExpiredDate();
	void setVisaExpiredDate(Date visaExpiredDate);
	
	ICompanyDto getCompany();
	void setCompany(ICompanyDto company);

	String getLastName();
	void setLastName(String lastName);

	String getFirstName();
	void setFirstName(String firstName);
	
	Date getWorkPermissionExpiryDate();
	void setWorkPermissionExpiryDate(Date workPermissionExpiryDate);
	
	Date getStatementExpiryDate();
	void setStatementExpiryDate(Date statementExpiryDate);
}
