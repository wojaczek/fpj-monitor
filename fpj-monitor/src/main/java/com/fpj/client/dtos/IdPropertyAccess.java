package com.fpj.client.dtos;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface IdPropertyAccess<T> extends PropertyAccess<T> {
	@Path("id")
	ModelKeyProvider<T> key();
	ValueProvider<T, Integer> id();

}
