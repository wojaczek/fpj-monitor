package com.fpj.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.IsWidget;
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
import com.sencha.gxt.theme.gray.client.tools.GrayTools.GrayToolResources;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent.BeforeStartEditHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
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
	final GridRowEditing<MODEL_TYPE> editingGrid;
	final PagingLoader<FilterPagingLoadConfig, RESULT_LIST> pagingLoader;
	protected MODEL_TYPE_PROPERTIES properties = createModelProperties();
	protected FACTORY factory = createFactory();
	private final ListStore<MODEL_TYPE> listStore;
	private GenericGridConstants constants;
	private final  String urlPrefix;
	private ColumnModel<MODEL_TYPE> columnModel;
	private List<ColumnConfig<MODEL_TYPE, ?>> columnConfigs;
	
	public GenericEditingGrid(final String urlPrefix) {
		super();
		this.urlPrefix = urlPrefix;
		this.listStore = createListStore();
		columnConfigs = createColumnConfigs();
		columnConfigs.add(createDeleteButton());
		columnModel = new ColumnModel<MODEL_TYPE>(columnConfigs);
		basicGrid = new Grid<MODEL_TYPE>(listStore, columnModel);
		editingGrid = new GridRowEditing<MODEL_TYPE>(basicGrid);
		editingGrid.addCompleteEditHandler(new CompleteEditHandler<MODEL_TYPE>() {

			@Override
			public void onCompleteEdit(CompleteEditEvent<MODEL_TYPE> event) {
				for (final Store<MODEL_TYPE>.Record modifiedRecord : listStore.getModifiedRecords()) {
					MODEL_TYPE storeElem = factory.create(GenericEditingGrid.this.getModelTypeClass()).as();
					fillModelFromChange(modifiedRecord, storeElem);
					RequestBuilder builder = new RequestBuilder(RequestBuilder.PUT, urlPrefix + "/update");
					builder.setHeader("Accept", "application/json");
					builder.setHeader("Content-Type", "application/json");
					JsonWriter<MODEL_TYPE> writer = new JsonWriter<MODEL_TYPE>(factory, getModelTypeClass());
					try {
						builder.sendRequest(writer.write(storeElem), new RequestCallback() {
							@Override
							public void onResponseReceived(Request request, Response response) {
								if (response.getStatusCode() == 200) {
									listStore.commitChanges();
								} else {
									AlertMessageBox mBox = new AlertMessageBox(getConstants().saveErrorTitle(), getConstants()
											.saveError(Integer.toString(response.getStatusCode())));
									mBox.show();
									listStore.rejectChanges();
								}
							}

							@Override
							public void onError(Request request, Throwable exception) {
								// TODO Auto-generated method stub
							}
						});
					} catch (RequestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
		vlc.add(basicGrid, new  VerticalLayoutData(1, -1));
		this.add(vlc);
		pagingLoader.load();
		this.setHeight(500);
		this.setHeaderVisible(false);
		System.out.println("Grid initialized");
	}


	private ColumnConfig<MODEL_TYPE, ?> createDeleteButton() {
		ColumnConfig<MODEL_TYPE, Integer> deleteColumn = new ColumnConfig<MODEL_TYPE, Integer>(properties.id());
		GrayToolResources resources = GWT.create(GrayToolResources.class);
		ActionCell<Integer> cell = new ActionCell<Integer>("Delete", new Delegate<Integer>() {
			@Override
			public void execute(final Integer object) {
				RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, urlPrefix+"/delete/"+object);
				builder.setHeader("Accept", "application/json");
				builder.setHeader("Content-Type", "application/json");
				try {
					builder.sendRequest(null, new RequestCallback() {
						
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode()==200){
								MODEL_TYPE deletedObject=null;
								for (MODEL_TYPE elem : listStore.getAll()) {
									if (elem.getId()==object){
										deletedObject = elem;
										break;
									}
								}
								listStore.remove(deletedObject);
								listStore.commitChanges();
							}
							editingGrid.cancelEditing();
						}
						
						@Override
						public void onError(Request request, Throwable exception) {
							// TODO Auto-generated method stub
							
						}
					});
				} catch (RequestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		deleteColumn.setCell(cell);
		return deleteColumn;
	}


	private IsWidget createAddButton() {
		TextButton addButton = new TextButton(getConstants().add());
		addButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				RequestBuilder builder = new RequestBuilder(RequestBuilder.PUT, urlPrefix + "/create");
				builder.setHeader("Accept", "application/json");
				builder.setHeader("Content-Type", "application/json");
				try {
					builder.sendRequest(null, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								JsonReader<MODEL_TYPE, MODEL_TYPE> reader = new JsonReader<MODEL_TYPE, MODEL_TYPE>(factory, getModelTypeClass());
								MODEL_TYPE respondedModel = reader.read(null, response.getText());
								listStore.add(0, respondedModel);
								int row = listStore.indexOf(respondedModel);
								listStore.commitChanges();
								editingGrid.startEditing(new GridCell(row, 0));
							} else {
								AlertMessageBox mBox = new AlertMessageBox(getConstants().saveErrorTitle(), getConstants()
										.saveError(Integer.toString(response.getStatusCode())));
								mBox.show();
							}
						}

						@Override
						public void onError(Request request, Throwable exception) {
							// TODO Auto-generated method stub
						}
					});
				} catch (RequestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			configurer.addGridEditor(editingGrid);
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
		PagingLoader<FilterPagingLoadConfig, RESULT_LIST> listLoader = new PagingLoader<FilterPagingLoadConfig, RESULT_LIST>(httpProxy, jsonReader){
			@Override
			public void addSortInfo(SortInfo sortInfo)
			{
				final SortInfo as = factory.getSortInfo().as();
				as.setSortField(sortInfo.getSortField());
				as.setSortDir(sortInfo.getSortDir());
				super.addSortInfo(as);
			}

		};
		listLoader.useLoadConfig(factory.loadConfig().as());
		listLoader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, MODEL_TYPE, RESULT_LIST>(listStore));
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
}
