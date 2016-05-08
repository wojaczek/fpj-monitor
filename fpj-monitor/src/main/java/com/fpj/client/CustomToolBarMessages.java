package com.fpj.client;

import com.google.gwt.i18n.client.Messages.DefaultMessage;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar.PagingToolBarMessages;

public interface CustomToolBarMessages extends PagingToolBarMessages {
	@Override
	@DefaultMessage("Strona {0} z {1}")
	public String displayMessage(int start, int end, int total);
	@Override
	@DefaultMessage("Skocz do pierwszej strony")
	public String firstText();
	
	@Override
	@DefaultMessage("Skocz do ostatniej strony")
	public String lastText();
	
	@Override
	@DefaultMessage("Odśwież")
	public String refreshText();

}
