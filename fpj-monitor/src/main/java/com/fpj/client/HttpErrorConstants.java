package com.fpj.client;

import com.google.gwt.i18n.client.Messages;

public interface HttpErrorConstants extends Messages {
	@DefaultMessage("Błąd wewnętrzny")
	String s500();
	@DefaultMessage("Nieznany błąd")
	String unknown();
	
}
