package com.fpj.client;

import com.google.gwt.i18n.client.Messages;

public interface MonitorConstants extends Messages{

	@DefaultMessage("Logowanie nieudane")
	String loginFailedTitle();

	@DefaultMessage("Błędny login lub hasło")
	String loginFailedText();
	
	@DefaultMessage("Błąd łączności z aplikacją")
	String generalConnectivityErrorText();
	@DefaultMessage("Błąd łączności")
	String generalConnectivityErrorTitle();
	@DefaultMessage("Nazwa użytkownika")
	String username();
	@DefaultMessage("Hasło")
	String password();
	@DefaultMessage("Zaloguj")
	String login();

}
