package com.fpj.client;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface IdPropertyAccess<T> extends PropertyAccess<T> {
	@Path("id")
	ModelKeyProvider<T> key();

}
