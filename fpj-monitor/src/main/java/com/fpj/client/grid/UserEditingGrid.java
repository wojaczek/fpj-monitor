package com.fpj.client.grid;

import java.util.ArrayList;
import java.util.List;

import com.fpj.client.dtos.IUserDto;
import com.fpj.client.dtos.factories.IUserAutoBeanFactory;
import com.fpj.client.dtos.loadResults.IUserPagingLoadResult;
import com.fpj.client.dtos.properties.IUserDtoProperties;
import com.fpj.client.generics.EditorConfigurer;
import com.fpj.client.generics.GenericEditingGrid;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.AdapterField;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

public class UserEditingGrid extends GenericEditingGrid<IUserDto, IUserDtoProperties, IUserPagingLoadResult, IUserAutoBeanFactory>{

	private UserGridConstants constants;

	public UserEditingGrid(String urlPrefix) {
		super(urlPrefix);
	}

	@Override
	protected IUserDto createModelType() {
		return factory.create(IUserDto.class).as();
	}

	@Override
	protected void fillModelFromChange(Store<IUserDto>.Record modifiedRecord, IUserDto storeElem) {
		IUserDtoProperties properties = createModelProperties();
		storeElem.setId(modifiedRecord.getValue(properties.id()));
		storeElem.setUsername(modifiedRecord.getValue(properties.username()));
		storeElem.setEmail(modifiedRecord.getValue(properties.email()));
		storeElem.setPassword(modifiedRecord.getValue(properties.password()));
		storeElem.setPasswordRepeat(modifiedRecord.getValue(properties.passwordRepeat()));
		storeElem.setEnabled(modifiedRecord.getValue(properties.enabled()));
	}

	@Override
	protected Class<IUserDto> getModelTypeClass() {
		return IUserDto.class;
	}

	@Override
	protected IUserAutoBeanFactory createFactory() {
		return GWT.create(IUserAutoBeanFactory.class);
	}

	@Override
	protected IUserDtoProperties createModelProperties() {
		return GWT.create(IUserDtoProperties.class);
	}

	@Override
	protected Class<IUserPagingLoadResult> getListResultClass() {
		return IUserPagingLoadResult.class;
	}

	@Override
	protected List<ColumnConfig<IUserDto, ?>> createColumnConfigs() {
		List<ColumnConfig<IUserDto, ?>> columnConfig = new ArrayList<ColumnConfig<IUserDto, ?>>();
		ColumnConfig<IUserDto, String> usernameColumn = new ColumnConfig<>(properties.username(),	150, getConstants().username());
		configurers.add(new EditorConfigurer<IUserDto, String>(usernameColumn, new TextField()));
		
		ColumnConfig<IUserDto, String> emailColumn = new ColumnConfig<>(properties.email(),	150, getConstants().email());
		final TextField emailTextField = new TextField();
		final String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		RegExValidator regExpValidator = new RegExValidator(EMAIL_PATTERN);
		regExpValidator.setMessages(getConstants());
		emailTextField.addValidator(regExpValidator);
		configurers.add(new EditorConfigurer<IUserDto, String>(emailColumn, emailTextField));
		
		ColumnConfig<IUserDto, String> passwordColumn = new ColumnConfig<IUserDto, String>(properties.password(), 150, getConstants().password());
		passwordColumn.setCell(new TextCell(new SafeHtmlRenderer<String>() {
			
			@Override
			public void render(String object, SafeHtmlBuilder builder) {
				builder.appendEscaped("***");
			}
			
			@Override
			public SafeHtml render(String object) {
				return new SafeHtmlBuilder().appendEscaped("***").toSafeHtml();
			}
		}));
		
		final PasswordField passwordField = new PasswordField();
		MinLengthValidator minLenValidator = new MinLengthValidator(6);
		minLenValidator.setMessages(getConstants());
		passwordField.addValidator(minLenValidator);
		configurers.add(new EditorConfigurer<IUserDto, String>(passwordColumn, passwordField));
		
		ColumnConfig<IUserDto, String> passwordRepeatColumn = new ColumnConfig<IUserDto, String>(properties.passwordRepeat(), 150, getConstants().passwordRepeat());
		passwordRepeatColumn.setCell(new TextCell(new SafeHtmlRenderer<String>() {
			
			@Override
			public void render(String object, SafeHtmlBuilder builder) {
				builder.appendEscaped("***");
			}
			
			@Override
			public SafeHtml render(String object) {
				return new SafeHtmlBuilder().appendEscaped("***").toSafeHtml();
			}
		}));
		
		final PasswordField passwordRepeatField = new PasswordField();
		configurers.add(new EditorConfigurer<IUserDto, String>(passwordRepeatColumn, passwordRepeatField));
		
		passwordRepeatField.addValidator(new Validator<String>() {
			@Override
			public List<EditorError> validate(Editor<String> editor, String value) {
				if ((value == null  || value.isEmpty()) && (passwordField.getValue()==null || passwordField.getValue().isEmpty())){
					return null;
				}
				if (value.equals(passwordField.getValue())){
					return null;
				} else {
					List<EditorError> result = new ArrayList<EditorError>();
					result.add(new DefaultEditorError(editor, getConstants().passwordRepeatInvalid(), value));
					return result;
				}
			}
		});
		
		passwordField.addValidator(new Validator<String>() {
			@Override
			public List<EditorError> validate(Editor<String> editor, String value) {
				if ((value == null  || value.isEmpty()) && (passwordRepeatField.getValue()==null || passwordRepeatField.getValue().isEmpty())){
					return null;
				}
				if (value.equals(passwordRepeatField.getValue())){
					return null;
				} else {
					List<EditorError> result = new ArrayList<EditorError>();
					result.add(new DefaultEditorError(editor, getConstants().passwordRepeatInvalid(), value));
					return result;
				}
			}
		});
		
		ColumnConfig<IUserDto, Boolean> enableColumn = new ColumnConfig<IUserDto, Boolean>(properties.enabled(), 100, getConstants().enabled());
		enableColumn.setCell(new AbstractCell<Boolean>(new String[0]){

			@Override
			public void render(Context context, Boolean value, SafeHtmlBuilder sb) {
				if (value==null || !value){
					sb.appendEscaped(getConstants().falseString());
				}else{
					sb.appendEscaped(getConstants().trueString());
				}
				
			}
			
		});
		configurers.add(new EditorConfigurer<IUserDto, Boolean>(enableColumn, new CheckBox()));
		
		final CheckBox user = new CheckBox();
		user.setBoxLabel("user");
		final CheckBox admin = new CheckBox();
		admin.setBoxLabel("admin");
		ColumnConfig<IUserDto, List<String>>rolesColumn = new ColumnConfig<IUserDto, List<String>>(properties.roles(), 200, getConstants().roles());
		ContentPanel contentPanel = new ContentPanel();
		contentPanel.setHeaderVisible(false);
		VerticalLayoutContainer verticalPanel = new VerticalLayoutContainer();
		contentPanel.add(verticalPanel);
		verticalPanel.add(user);
		verticalPanel.add(admin);
		AdapterField<List<String>> rolesField = new AdapterField<List<String>>(contentPanel) {
			List<String> roles;
			@Override
			public void setValue(List<String> value) {
				this.roles=value!=null?value:new ArrayList<String>();
				if (roles.contains("ROLE_USER")){
					user.setValue(true);
				}
				if (roles.contains("ROLE_ADMIN")){
					admin.setValue(true);
				}
			}

			@Override
			public List<String> getValue() {
				if (roles==null){
					roles = new ArrayList<String>();
				}
				roles.clear();
				if (user.getValue()){
					roles.add("ROLE_USER");
				}
				if (admin.getValue()){
					roles.add("ROLE_ADMIN");
				}
				return roles;
			}
		};
		configurers.add(new EditorConfigurer<IUserDto, List<String>>(rolesColumn, rolesField));
		
		
		columnConfig.add(usernameColumn);
		columnConfig.add(rolesColumn);
		columnConfig.add(emailColumn);
		columnConfig.add(passwordColumn);
		columnConfig.add(passwordRepeatColumn);
		columnConfig.add(enableColumn);
		return columnConfig;
	}

	private UserGridConstants getConstants(){
		if (constants == null){
			constants = GWT.create(UserGridConstants.class);
		}
		return constants;
	}
	
}
