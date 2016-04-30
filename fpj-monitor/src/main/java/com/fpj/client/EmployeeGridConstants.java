package com.fpj.client;

import com.google.gwt.i18n.client.Constants;

public interface EmployeeGridConstants extends Constants {
	
	@DefaultStringValue("Imię")
	String firstName();
	
	@DefaultStringValue("Nazwisko")
	String lastName();
	
	@DefaultStringValue("Nazwa firmy")
	String companyName();
	
	@DefaultStringValue("Data wygaśnięcia wizy")
	String visaExpirtyDate();
}
