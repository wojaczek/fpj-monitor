package com.fpj.client.grid;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator.MinLengthMessages;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator.RegExMessages;

public interface UserGridConstants extends Messages, MinLengthMessages, RegExMessages {
	@DefaultMessage("Nazwa użytkownika")
	String username();

	@DefaultMessage("Adres email")
	String email();
	@DefaultMessage("Hasło")
	String password();
	
	@DefaultMessage("Powtórz hasło")
	String passwordRepeat();
	@DefaultMessage("Za krótko. minimalna długość: {0}")
	String minLengthText(int length);
	
	@DefaultMessage("Hasło i powtórzenie są różne")
	String passwordRepeatInvalid();
	
	@DefaultMessage("Zły format")
	String regExMessage();
	
	@DefaultMessage("Aktywny?")
	SafeHtml enabled();
	@DefaultMessage("Tak")
	String trueString();
	@DefaultMessage("Nie")
	String falseString();
	@DefaultMessage("Aktywne role")
	String roles();
	
}
