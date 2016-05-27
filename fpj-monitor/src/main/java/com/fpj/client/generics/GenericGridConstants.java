package com.fpj.client.generics;

import com.google.gwt.i18n.client.Messages;

public interface GenericGridConstants extends Messages {

	@DefaultMessage("Zapis nieudany, kod błędu: {0}")
	String saveError(String errorCode);
	@DefaultMessage("Błąd zapisu")
	String saveErrorTitle();
	@DefaultMessage("Dodaj")
	String add();
	@DefaultMessage("Błąd usuwania")
	String deleteErrorTitle();
	@DefaultMessage("Błąd komunikacji")
	String communicationErrorTitle();
	@DefaultMessage("Usuń")
	String delete();
}
