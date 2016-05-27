package com.fpj.client.generics;

import java.util.ArrayList;
import java.util.List;

import com.fpj.client.dtos.IIdentifiableDto;
import com.fpj.client.dtos.IdPropertyAccess;
import com.fpj.client.dtos.factories.LoadConfigFactory;
import com.fpj.client.utils.JsonRequestBuilder;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.data.client.loader.HttpProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.JsonReader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.data.shared.writer.JsonWriter;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.AdapterField;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public abstract class GenericEditingGrid<MODEL_TYPE extends IIdentifiableDto, MODEL_TYPE_PROPERTIES extends IdPropertyAccess<MODEL_TYPE>, RESULT_LIST extends PagingLoadResult<MODEL_TYPE>, FACTORY extends LoadConfigFactory<MODEL_TYPE, RESULT_LIST>>
		extends ContentPanel implements IsWidget {

	protected final List<EditorConfigurer<MODEL_TYPE, ?>> configurers = new ArrayList<EditorConfigurer<MODEL_TYPE, ?>>();
	final Grid<MODEL_TYPE> basicGrid;
	private final GridRowEditing<MODEL_TYPE> editingGrid;
	final PagingLoader<FilterPagingLoadConfig, RESULT_LIST> pagingLoader;
	protected MODEL_TYPE_PROPERTIES properties = createModelProperties();
	protected FACTORY factory = createFactory();
	private final ListStore<MODEL_TYPE> listStore;
	private GenericGridConstants constants;
	private final String urlPrefix;
	private ColumnModel<MODEL_TYPE> columnModel;
	final CellSelectionModel<MODEL_TYPE> selectionModel = new CellSelectionModel<>();
	private List<ColumnConfig<MODEL_TYPE, ?>> columnConfigs;

	@SuppressWarnings("unchecked")
	public GenericEditingGrid(final String urlPrefix) {
		super();
		this.urlPrefix = urlPrefix;
		this.listStore = createListStore();
		columnConfigs = createColumnConfigs();
		columnConfigs.add(createDeleteButton());
		columnModel = new ColumnModel<MODEL_TYPE>(columnConfigs);
		basicGrid = new Grid<MODEL_TYPE>(getListStore(), columnModel);
		basicGrid.setHeight(300);
		editingGrid = new GridRowEditing<MODEL_TYPE>(basicGrid);
		
		editingGrid.addEditor((ColumnConfig<MODEL_TYPE, Integer>)columnConfigs.get(columnConfigs.size()-1), new AdapterField<Integer>(new Hidden()){
			Integer value;
			@Override
			public void setValue(Integer value) {
				this.value = value;
			}

			@Override
			public Integer getValue() {
				return value;
			}
			
		});	
		getEditingGrid().addCompleteEditHandler(new CompleteEditHandler<MODEL_TYPE>() {

			@Override
			public void onCompleteEdit(CompleteEditEvent<MODEL_TYPE> event) {
				for (final Store<MODEL_TYPE>.Record modifiedRecord : getListStore().getModifiedRecords()) {
					MODEL_TYPE storeElem = factory.create(GenericEditingGrid.this.getModelTypeClass()).as();
					fillModelFromChange(modifiedRecord, storeElem);
					JsonRequestBuilder builder = new JsonRequestBuilder(RequestBuilder.PUT, urlPrefix + "/update") {
						@Override
						protected void successMethod(Response response) {
							getListStore().commitChanges();
						}

					};
					JsonWriter<MODEL_TYPE> writer = new JsonWriter<MODEL_TYPE>(factory, getModelTypeClass());
					builder.sendRequest(writer.write(storeElem));
				}
			}
		});
		applyEditors();
		pagingLoader = createListLoader(urlPrefix);
		basicGrid.setLoader(pagingLoader);
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		ToolBar bar = new ToolBar();
		bar.add(this.createAddButton());

		PagingToolBar ptb = new PagingToolBar(10);
		ptb.bind(pagingLoader);

		vlc.add(bar, new VerticalLayoutData(1, -1));
		vlc.add(ptb, new VerticalLayoutData(1, -1));
		vlc.add(basicGrid, new VerticalLayoutData(1, -1));
		this.add(vlc);
		pagingLoader.load();
		this.setHeight(500);
		this.setHeaderVisible(false);
		finalizeConfiguration();
		System.out.println("Grid initialized");
	}

	protected void finalizeConfiguration() {
		
	}

	private ColumnConfig<MODEL_TYPE, Integer> createDeleteButton() {
		ColumnConfig<MODEL_TYPE, Integer> deleteColumn = new ColumnConfig<MODEL_TYPE, Integer>(properties.id());
		ButtonCell<Integer> cell = new ButtonCell<Integer>() {
			@Override
			public boolean handlesSelection() {
				return true;
			}
		};

		cell.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(final SelectEvent event) {
				final Integer deletedId = getListStore().get(event.getContext().getIndex()).getId();
				JsonRequestBuilder builder = new JsonRequestBuilder(RequestBuilder.GET, urlPrefix + "/delete/" + deletedId) {
					@Override
					protected void successMethod(Response response) {
						getListStore().remove (event.getContext().getIndex());
						getListStore().commitChanges();
					}

				};
				builder.sendRequest(null);

			}
		});
		cell.setText(getConstants().delete());
		deleteColumn.setCell(cell);
		deleteColumn.setFixed(true);
		deleteColumn.setHideable(false);
		deleteColumn.setMenuDisabled(true);
		deleteColumn.setResizable(false);
		deleteColumn.setSortable(false);
		return deleteColumn;
	}

	private IsWidget createAddButton() {
		TextButton addButton = new TextButton(getConstants().add());
		addButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				JsonRequestBuilder builder = new JsonRequestBuilder(RequestBuilder.PUT, urlPrefix + "/create"){
					@Override
					protected void successMethod(Response response) {
						JsonReader<MODEL_TYPE, MODEL_TYPE> reader = new JsonReader<MODEL_TYPE, MODEL_TYPE>(factory,
								getModelTypeClass());
						MODEL_TYPE respondedModel = reader.read(null, response.getText());
						getListStore().add(0, respondedModel);
						int row = getListStore().indexOf(respondedModel);
						getListStore().commitChanges();
						getEditingGrid().startEditing(new GridCell(row, 0));
					}
				};
				builder.sendRequest(null);
			}
		});
		return addButton;
	}

	protected abstract MODEL_TYPE createModelType();

	protected abstract void fillModelFromChange(Store<MODEL_TYPE>.Record modifiedRecord, MODEL_TYPE storeElem);

	protected abstract Class<MODEL_TYPE> getModelTypeClass();

	protected abstract FACTORY createFactory();

	protected abstract MODEL_TYPE_PROPERTIES createModelProperties();

	private void applyEditors() {
		for (EditorConfigurer<MODEL_TYPE, ?> configurer : configurers) {
			configurer.addGridEditor(getEditingGrid());
		}
	}

	private PagingLoader<FilterPagingLoadConfig, RESULT_LIST> createListLoader(String urlPrefix) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, urlPrefix + "/list");
		builder.setHeader("Accept", "application/json");
		builder.setHeader("Content-Type", "application/json");

		HttpProxy<FilterPagingLoadConfig> httpProxy = new HttpProxy<FilterPagingLoadConfig>(builder);
		JsonWriter<FilterPagingLoadConfig> dataWriter = new JsonWriter<FilterPagingLoadConfig>(factory,
				FilterPagingLoadConfig.class);
		httpProxy.setWriter(dataWriter);

		JsonReader<RESULT_LIST, RESULT_LIST> jsonReader = new JsonReader<>(factory, getListResultClass());
		PagingLoader<FilterPagingLoadConfig, RESULT_LIST> listLoader = new PagingLoader<FilterPagingLoadConfig, RESULT_LIST>(
				httpProxy, jsonReader) {
			@Override
			public void addSortInfo(SortInfo sortInfo) {
				final SortInfo as = factory.getSortInfo().as();
				as.setSortField(sortInfo.getSortField());
				as.setSortDir(sortInfo.getSortDir());
				super.addSortInfo(as);
			}

		};
		listLoader.useLoadConfig(factory.loadConfig().as());
		listLoader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, MODEL_TYPE, RESULT_LIST>(getListStore()));
		listLoader.setRemoteSort(true);
		return listLoader;
	}

	protected abstract Class<RESULT_LIST> getListResultClass();

	private ListStore<MODEL_TYPE> createListStore() {
		ListStore<MODEL_TYPE> ls = new ListStore<MODEL_TYPE>(properties.key());
		return ls;
	}

	protected abstract List<ColumnConfig<MODEL_TYPE, ?>> createColumnConfigs();

	private GenericGridConstants getConstants() {
		if (constants == null) {
			constants = GWT.create(GenericGridConstants.class);
		}
		return constants;
	}

	protected GridRowEditing<MODEL_TYPE> getEditingGrid() {
		return editingGrid;
	}

	public ListStore<MODEL_TYPE> getListStore() {
		return listStore;
	}
	
}
