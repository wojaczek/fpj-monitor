package com.fpj.client;

import com.google.gwt.i18n.client.Messages;

public interface GenericGridConstants extends Messages {

	@DefaultMessage("Zapis nieudany, kod błędu: {0}")
	String saveError(String errorCode);
	@DefaultMessage("Błąd zapisu")
	String saveErrorTitle();
}
