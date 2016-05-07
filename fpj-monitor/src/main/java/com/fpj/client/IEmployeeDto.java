package com.fpj.client;

import java.util.Date;

public interface IEmployeeDto extends IdentifiableDto {

	Date getVisaExpiredDate();
	void setVisaExpiredDate(Date visaExpiredDate);
	
	String getCompanyName();
	void setCompanyName(String companyName);

	String getLastName();
	void setLastName(String lastName);

	String getFirstName();
	void setFirstName(String firstName);

}
