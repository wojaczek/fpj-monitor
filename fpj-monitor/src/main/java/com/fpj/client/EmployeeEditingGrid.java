package com.fpj.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;

public class EmployeeEditingGrid extends GenericEditingGrid<IEmployeeDto, IEmployeeDtoProperties, IEmployeeListLoadResult> {
	
	private EmployeeGridConstants constants;
	
	@Override
	protected LoadConfigFactory createFactory() {
		return GWT.create(IEmployeeAutoBeanFactory.class);
	}

	@Override
	protected IEmployeeDtoProperties createModelProperties() {
		return GWT.create(IEmployeeDtoProperties.class);
	}

	@Override
	protected ColumnModel<IEmployeeDto> createColumnModel() {
		List<ColumnConfig<IEmployeeDto, ?>> columnConfig = new ArrayList<ColumnConfig<IEmployeeDto, ?>>();
		
		ColumnConfig<IEmployeeDto, String> firstNameColumn = new ColumnConfig<>(properties.firstName(),	150, getConstants().firstName());
		configurers.add(new EditorConfigurer<IEmployeeDto, String>(firstNameColumn, new TextField()));
		columnConfig.add(firstNameColumn);

		ColumnConfig<IEmployeeDto, String> lastNameColumn = new ColumnConfig<>(properties.lastName(), 150, getConstants().lastName());
		configurers.add(new EditorConfigurer<IEmployeeDto, String>(lastNameColumn, new TextField()));
		columnConfig.add(lastNameColumn);

		ColumnConfig<IEmployeeDto, String> companyNameColumn = new ColumnConfig<>(properties.companyName(), 150, getConstants().companyName());
		configurers.add(new EditorConfigurer<IEmployeeDto, String>(companyNameColumn, new TextField()));
		columnConfig.add(companyNameColumn);

		ColumnConfig<IEmployeeDto, Date> visaExpiryDateColumn = new ColumnConfig<>(properties.visaExpiredDate(), 150,
				getConstants().visaExpirtyDate());
		visaExpiryDateColumn.setCell(new DateCell( DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
		
		
		DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
		DateField field = new DateField(new DateTimePropertyEditor(dateFormat));
		configurers.add(new EditorConfigurer<IEmployeeDto, Date>(visaExpiryDateColumn, field));
		columnConfig.add(visaExpiryDateColumn);

		ColumnModel<IEmployeeDto> cm = new ColumnModel<IEmployeeDto>(columnConfig);
		return cm;
	}

	@Override
	protected Class<IEmployeeListLoadResult> getListResultClass() {
		return IEmployeeListLoadResult.class;
	}

	public EmployeeGridConstants getConstants() {
		if (constants == null ){
			constants = GWT.create(EmployeeGridConstants.class);
		}
		return constants;
	}

}
