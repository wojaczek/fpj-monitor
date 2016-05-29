package com.fpj.client.grid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fpj.client.dtos.ICompanyDto;
import com.fpj.client.dtos.IEmployeeDto;
import com.fpj.client.dtos.factories.ICompanyAutoBeanFactory;
import com.fpj.client.dtos.factories.IEmployeeAutoBeanFactory;
import com.fpj.client.dtos.loadResults.ICompanyPagingLoadResult;
import com.fpj.client.dtos.loadResults.IEmployeePagingLoadResult;
import com.fpj.client.dtos.properties.IEmployeeDtoProperties;
import com.fpj.client.generics.EditorConfigurer;
import com.fpj.client.generics.GenericEditingGrid;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.loader.JsonReader;
import com.sencha.gxt.widget.core.client.event.BeforeShowEvent;
import com.sencha.gxt.widget.core.client.event.BeforeShowEvent.BeforeShowHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

public class EmployeeEditingGrid extends
		GenericEditingGrid<IEmployeeDto, IEmployeeDtoProperties, IEmployeePagingLoadResult, IEmployeeAutoBeanFactory> {

	interface ICompanyDtoLabelProvider extends PropertyAccess<ICompanyDto> {
		LabelProvider<ICompanyDto> companyName();

		ModelKeyProvider<ICompanyDto> id();
	}

	public EmployeeEditingGrid(String urlPrefix) {
		super(urlPrefix);
	}

	private EmployeeGridConstants constants;
	private ICompanyAutoBeanFactory companyFactory = GWT.create(ICompanyAutoBeanFactory.class);

	@Override
	protected IEmployeeAutoBeanFactory createFactory() {
		return GWT.create(IEmployeeAutoBeanFactory.class);
	}

	@Override
	protected IEmployeeDtoProperties createModelProperties() {
		return GWT.create(IEmployeeDtoProperties.class);
	}

	@Override
	protected List<ColumnConfig<IEmployeeDto, ?>> createColumnConfigs() {
		List<ColumnConfig<IEmployeeDto, ?>> columnConfig = new ArrayList<ColumnConfig<IEmployeeDto, ?>>();

		ColumnConfig<IEmployeeDto, String> firstNameColumn = new ColumnConfig<>(properties.firstName(), 150, getConstants()
				.firstName());
		configurers.add(new EditorConfigurer<IEmployeeDto, String>(firstNameColumn, new TextField()));
		columnConfig.add(firstNameColumn);

		ColumnConfig<IEmployeeDto, String> lastNameColumn = new ColumnConfig<>(properties.lastName(), 150, getConstants()
				.lastName());
		configurers.add(new EditorConfigurer<IEmployeeDto, String>(lastNameColumn, new TextField()));
		columnConfig.add(lastNameColumn);

		ICompanyDtoLabelProvider companyProperties = GWT.create(ICompanyDtoLabelProvider.class);
		ColumnConfig<IEmployeeDto, ICompanyDto> companyNameColumn = new ColumnConfig<>(properties.company(), 150, getConstants()
				.companyName());
		Cell<ICompanyDto> cell = new AbstractCell<ICompanyDto>(new String[] {}) {

			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context, ICompanyDto value, SafeHtmlBuilder sb) {
				if (value != null) {
					sb.appendEscaped(value.getCompanyName());
				}
			}

		};
		companyNameColumn.setCell(cell);
		final ListStore<ICompanyDto> store = new ListStore<ICompanyDto>(companyProperties.id());
		ComboBox<ICompanyDto> companyComboBox = new ComboBox<ICompanyDto>(store, companyProperties.companyName());
		companyComboBox.setAllowBlank(true);
		companyComboBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "spring/company/list");
				builder.setHeader("Accept", "application/json");
				builder.setHeader("Content-Type", "application/json");
				try {
					builder.sendRequest(null, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								JsonReader<ICompanyPagingLoadResult, ICompanyPagingLoadResult> reader = new JsonReader<ICompanyPagingLoadResult, ICompanyPagingLoadResult>(
										companyFactory, ICompanyPagingLoadResult.class);
								ICompanyPagingLoadResult result = reader.read(null, response.getText());
								store.clear();
								store.addAll(result.getData());
							} else {
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
		companyComboBox.addBeforeShowHandler(new BeforeShowHandler() {

			@Override
			public void onBeforeShow(BeforeShowEvent event) {

			}
		});
		configurers.add(new EditorConfigurer<IEmployeeDto, ICompanyDto>(companyNameColumn, companyComboBox));
		columnConfig.add(companyNameColumn);
		
		DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
		
		ColumnConfig<IEmployeeDto, Date> visaExpiryDateColumn = new ColumnConfig<>(properties.visaExpiredDate(), 150,
				getConstants().visaExpirtyDate());
		visaExpiryDateColumn.setCell(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
		DateField visaExpField = new DateField(new DateTimePropertyEditor(dateFormat));
		configurers.add(new EditorConfigurer<IEmployeeDto, Date>(visaExpiryDateColumn, visaExpField));
		columnConfig.add(visaExpiryDateColumn);

		ColumnConfig<IEmployeeDto, Date> workPermissionExpiryColumn = new ColumnConfig<>(properties.workPermissionExpiryDate(),
				150, getConstants().workPermissionExpiryDate());
		workPermissionExpiryColumn.setCell(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
		DateField workPermField = new DateField(new DateTimePropertyEditor(dateFormat));
		configurers.add(new EditorConfigurer<IEmployeeDto, Date>(workPermissionExpiryColumn, workPermField));
		columnConfig.add(workPermissionExpiryColumn);

		ColumnConfig<IEmployeeDto, Date> statementExpiryColumn = new ColumnConfig<>(properties.statementExpiryDate(), 150,
				getConstants().statementExpiryDate());
		statementExpiryColumn.setCell(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
		DateField statementField = new DateField(new DateTimePropertyEditor(dateFormat));
		configurers.add(new EditorConfigurer<IEmployeeDto, Date>(statementExpiryColumn, statementField));
		columnConfig.add(statementExpiryColumn);
		return columnConfig;
	}

	@Override
	protected Class<IEmployeePagingLoadResult> getListResultClass() {
		return IEmployeePagingLoadResult.class;
	}

	private EmployeeGridConstants getConstants() {
		if (constants == null) {
			constants = GWT.create(EmployeeGridConstants.class);
		}
		return constants;
	}

	@Override
	protected Class<IEmployeeDto> getModelTypeClass() {
		return IEmployeeDto.class;
	}

	@Override
	protected void fillModelFromChange(Store<IEmployeeDto>.Record modifiedRecord, IEmployeeDto storeElem) {
		IEmployeeDtoProperties properties = GWT.create(IEmployeeDtoProperties.class);
		storeElem.setId(modifiedRecord.getValue(properties.id()));
		storeElem.setFirstName(modifiedRecord.getValue(properties.firstName()));
		storeElem.setLastName(modifiedRecord.getValue(properties.lastName()));
		storeElem.setCompany(modifiedRecord.getValue(properties.company()));
		storeElem.setVisaExpiredDate(modifiedRecord.getValue(properties.visaExpiredDate()));
		storeElem.setWorkPermissionExpiryDate(modifiedRecord.getValue(properties.workPermissionExpiryDate()));
		storeElem.setStatementExpiryDate(modifiedRecord.getValue(properties.statementExpiryDate()));
	}

	@Override
	protected IEmployeeDto createModelType() {
		return factory.create(IEmployeeDto.class).as();
	}

}
