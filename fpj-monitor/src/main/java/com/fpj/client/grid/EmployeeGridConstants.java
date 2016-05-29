package com.fpj.client.grid;

import com.google.gwt.i18n.client.Constants;

public interface EmployeeGridConstants extends Constants {
	
	@DefaultStringValue("Imię")
	String firstName();
	
	@DefaultStringValue("Nazwisko")
	String lastName();
	
	@DefaultStringValue("Nazwa firmy")
	String companyName();
	
	@DefaultStringValue("Data wygaśnięcia wizy/karty pobytu")
	String visaExpirtyDate();
	
	@DefaultStringValue("Data wygaśnięcia pozwolenia na pracę")
	String workPermissionExpiryDate();
	
	@DefaultStringValue("Data wygaśnięcia oświadczenia")
	String statementExpiryDate();
}
