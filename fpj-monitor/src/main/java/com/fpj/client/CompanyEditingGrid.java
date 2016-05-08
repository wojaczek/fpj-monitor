package com.fpj.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

public class CompanyEditingGrid extends GenericEditingGrid<ICompanyDto, ICompanyDtoProperties, ICompanyPagingLoadResult, ICompanyAutoBeanFactory> {

	private CompanyGridConstants constants;

	public CompanyEditingGrid(String urlPrefix) {
		super(urlPrefix);
	}

	@Override
	protected void fillModelFromChange(Store<ICompanyDto>.Record modifiedRecord, ICompanyDto storeElem) {
		ICompanyDtoProperties properties = createModelProperties();
		storeElem.setId(modifiedRecord.getValue(properties.id()));
		storeElem.setCompanyName(modifiedRecord.getValue(properties.companyName()));
	}

	@Override
	protected Class<ICompanyDto> getModelTypeClass() {
		return ICompanyDto.class;
	}

	@Override
	protected ICompanyAutoBeanFactory createFactory() {
		return GWT.create(ICompanyAutoBeanFactory.class);
	}

	@Override
	protected ICompanyDtoProperties createModelProperties() {
		return GWT.create(ICompanyDtoProperties.class);
	}

	@Override
	protected Class<ICompanyPagingLoadResult> getListResultClass() {
		return ICompanyPagingLoadResult.class;
	}

	@Override
	protected List<ColumnConfig<ICompanyDto,?>> createColumnConfigs() {
		List<ColumnConfig<ICompanyDto, ?>> columnConfigs = new ArrayList<ColumnConfig<ICompanyDto, ?>>();

		ColumnConfig<ICompanyDto, String> firstNameColumn = new ColumnConfig<>(properties.companyName(), 150, getConstants().columnName());
		configurers.add(new EditorConfigurer<ICompanyDto, String>(firstNameColumn, new TextField()));
		columnConfigs.add(firstNameColumn);
		
		
		return columnConfigs;
	}

	private CompanyGridConstants getConstants() {
		if (constants == null ){
			constants = GWT.create(CompanyGridConstants.class);
		}
		return constants;
	}

	@Override
	protected ICompanyDto createModelType() {
		return factory.create(ICompanyDto.class).as();
	}
	
}
