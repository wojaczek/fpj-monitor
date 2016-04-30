package com.fpj.client;

import java.util.Date;

public interface IEmployeeDto {

	Date getVisaExpiredDate();
	void setVisaExpiredDate(Date visaExpiredDate);
	
	String getCompanyName();
	void setCompanyName(String companyName);

	String getLastName();

	String getFirstName();

	Integer getId();

}
