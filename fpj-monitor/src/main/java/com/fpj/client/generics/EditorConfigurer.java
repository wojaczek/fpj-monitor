package com.fpj.client.generics;

import com.sencha.gxt.widget.core.client.form.IsField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;

public class EditorConfigurer<LIST_TYPE, FIELD_TYPE>{
	private ColumnConfig<LIST_TYPE, FIELD_TYPE> columnConfig;
	private IsField<FIELD_TYPE> field;
	
	public EditorConfigurer(ColumnConfig<LIST_TYPE, FIELD_TYPE> columnConfig, IsField<FIELD_TYPE> field){
		this.columnConfig = columnConfig;
		this.field = field;
	}
	
	public void addGridEditor(GridRowEditing<LIST_TYPE> grid){
		grid.addEditor(columnConfig, field);
	}
}