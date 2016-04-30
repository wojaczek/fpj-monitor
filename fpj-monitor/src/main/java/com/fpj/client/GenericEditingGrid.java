package com.fpj.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.data.client.loader.HttpProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.JsonReader;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.data.shared.writer.JsonWriter;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;

public abstract class GenericEditingGrid<MODEL_TYPE, MODEL_TYPE_PROPERTIES extends IdPropertyAccess<MODEL_TYPE>, RESULT_LIST extends PagingLoadResult<MODEL_TYPE>> extends ContentPanel implements IsWidget {

	protected final List <EditorConfigurer<MODEL_TYPE, ?>> configurers = new ArrayList<EditorConfigurer<MODEL_TYPE, ?>>();
	final Grid<MODEL_TYPE> basicGrid;
	final GridRowEditing<MODEL_TYPE> editingGrid;	
	final ListLoader<FilterPagingLoadConfig, RESULT_LIST> listLoader;
	MODEL_TYPE_PROPERTIES properties = createModelProperties();
	LoadConfigFactory factory = createFactory();
	private final ListStore<MODEL_TYPE> listStore;

	public GenericEditingGrid() {
		super();
		this.listStore = createListStore();
		basicGrid = new Grid<MODEL_TYPE>(listStore, createColumnModel());
		editingGrid = new GridRowEditing<MODEL_TYPE>(basicGrid);
		editingGrid.addCompleteEditHandler(new CompleteEditHandler<MODEL_TYPE>() {

			@Override
			public void onCompleteEdit(CompleteEditEvent<MODEL_TYPE> event) {
				listStore.commitChanges();
			}
		});
		applyEditors();
		listLoader = createListLoader();
		basicGrid.setLoader(listLoader);
		this.add(basicGrid);
		listLoader.load();
		this.setHeight(500);
		System.out.println("Grid initialized");
	}

	protected abstract LoadConfigFactory createFactory();

	protected abstract MODEL_TYPE_PROPERTIES createModelProperties();

	private void applyEditors() {
		for (EditorConfigurer<MODEL_TYPE, ?> configurer : configurers) {
			configurer.addGridEditor(editingGrid);
		}
	}

	private ListLoader<FilterPagingLoadConfig, RESULT_LIST> createListLoader() {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "spring/employee/list");
		builder.setHeader("Accept", "application/json");
		builder.setHeader("Content-Type", "application/json");
		
		HttpProxy<FilterPagingLoadConfig> httpProxy = new HttpProxy<FilterPagingLoadConfig>(builder);
		JsonWriter<FilterPagingLoadConfig> dataWriter = new JsonWriter<FilterPagingLoadConfig>(factory, FilterPagingLoadConfig.class);
		httpProxy.setWriter(dataWriter);

		JsonReader<RESULT_LIST, RESULT_LIST> jsonReader = new JsonReader<>(factory, getListResultClass());
		PagingLoader<FilterPagingLoadConfig, RESULT_LIST> listLoader = new PagingLoader<>(httpProxy, jsonReader );
		listLoader.useLoadConfig(factory.loadConfig().as());
		listLoader.addLoadHandler( new LoadResultListStoreBinding<FilterPagingLoadConfig, MODEL_TYPE, RESULT_LIST>(listStore));
		return listLoader;
	}

	protected abstract Class<RESULT_LIST> getListResultClass() ;

	private ListStore<MODEL_TYPE> createListStore() {
		ListStore<MODEL_TYPE> ls = new ListStore<MODEL_TYPE>(properties.key());
		return ls;
	}

	protected abstract ColumnModel<MODEL_TYPE> createColumnModel();
}
