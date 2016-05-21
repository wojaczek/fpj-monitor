package com.fpj.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Monitor implements EntryPoint {


	EmployeeEditingGrid empGrid;
	private final ContentPanel contentPanel = new ContentPanel();
	private CompanyEditingGrid companyGrid;
	private MonitorConstants constants = GWT.create(MonitorConstants.class);
	
	public void onModuleLoad() {
		RequestBuilder loginRequestBuilder = new RequestBuilder(RequestBuilder.GET, "spring/login/test");
		try {
			loginRequestBuilder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode()==200){
						buildInterface();
					}else {
						buildLoginWindow();
					}
					
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					AlertMessageBox mbox = new AlertMessageBox(constants.generalConnectivityErrorTitle(), constants.generalConnectivityErrorText()){
						protected void onButtonPressed(TextButton textButton) {
							onModuleLoad();
						};
					};
					mbox.show();
				}
			});
		} catch (RequestException e) {
		}
		
	}
	
	private void buildLoginWindow() {
		CenterLayoutContainer  clc = new CenterLayoutContainer();
		clc.setStyleName("loginPanel");
		VerticalLayoutContainer verticalLayout = new VerticalLayoutContainer();
		verticalLayout.setStyleName("loginPanel");		
		Label username = new Label(constants.username());
		final TextField userNameField = new TextField();
		Label password = new Label(constants.password());
		final PasswordField passwordField = new PasswordField();
		TextButton loginButton = new TextButton(constants.login());
		loginButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				RequestBuilder loginRequestBuilder = new RequestBuilder(RequestBuilder.POST, "spring/login");
				loginRequestBuilder.setHeader("Content-Type", "application/x-www-form-urlencoded");
				StringBuilder sb = new StringBuilder();
				sb.append("username=");
				sb.append(userNameField.getText());
				sb.append("&password=");
				sb.append(passwordField.getText());
				sb.append("&submit=Login");
				try {
					loginRequestBuilder.sendRequest(sb.toString(), new RequestCallback() {
						
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode()==200){
								buildInterface();
							}else {
								AlertMessageBox mbox = new AlertMessageBox(constants.generalConnectivityErrorTitle(), Integer.toString(response.getStatusCode()));
								mbox.show();
							}
						}
						
						@Override
						public void onError(Request request, Throwable exception) {
							AlertMessageBox mbox = new AlertMessageBox(constants.generalConnectivityErrorTitle(), constants.generalConnectivityErrorText());
							mbox.show();
						}
					});
				} catch (RequestException e) {
					
				}
			}
		});
		
		
		verticalLayout.add(username);
		verticalLayout.add(userNameField);
		verticalLayout.add(password);
		verticalLayout.add(passwordField);
		verticalLayout.add(loginButton);
		
		clc.add(verticalLayout);
		RootPanel.get("root").add(clc);
	}
	
	
	private void buildInterface(){
		Container mainContainer = new HorizontalLayoutContainer();
		mainContainer.add(createMenuPanel());
		contentPanel.setWidth(700);
		mainContainer.add(contentPanel);
		contentPanel.setHeaderVisible(false);
		RootPanel.get("root").clear();
		RootPanel.get("root").add(mainContainer);
	}
	
	public Container createMenuPanel(){
		Container menuPanel = new VerticalLayoutContainer();
		ToggleGroup menuGroup = new ToggleGroup();
		final ToggleButton buttonEmp = new ToggleButton("Employees");
		buttonEmp.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()){
					contentPanel.clear();
					contentPanel.add(getEmpGrid());	
				}
			}
		});
		final ToggleButton buttonNone = new ToggleButton("Companies");
		buttonNone.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()){
					contentPanel.clear();
					contentPanel.add(getCompanyGrid());
				}
			}

			
		});
		
		menuPanel.add(buttonEmp);
		menuPanel.add(buttonNone);
		
		menuGroup.add(buttonEmp);
		menuGroup.add(buttonNone);
		return menuPanel;
	}

	public EmployeeEditingGrid getEmpGrid() {
		if (empGrid == null){
			empGrid = new EmployeeEditingGrid("spring/employee");
		}
		return empGrid;
	}
	private CompanyEditingGrid getCompanyGrid() {
		if (companyGrid==null){
			companyGrid = new CompanyEditingGrid("spring/company");
		}
		return companyGrid;
	}

}
