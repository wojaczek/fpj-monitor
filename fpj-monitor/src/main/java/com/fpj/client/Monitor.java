package com.fpj.client;

import java.util.ArrayList;
import java.util.List;

import com.fpj.client.buttons.MonitorButton;
import com.fpj.client.dtos.ELoginStatus;
import com.fpj.client.dtos.EntUserRole;
import com.fpj.client.dtos.ILoginDto;
import com.fpj.client.dtos.factories.ILoginFactory;
import com.fpj.client.grid.CompanyEditingGrid;
import com.fpj.client.grid.EmployeeEditingGrid;
import com.fpj.client.grid.UserEditingGrid;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.loader.JsonReader;
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
	private UserEditingGrid userGrid;
	private List<EntUserRole> roles = new ArrayList<EntUserRole>();
	private Container menuPanel = new VerticalLayoutContainer();
	private ToggleGroup menuGroup = new ToggleGroup();

	public void onModuleLoad() {
		RequestBuilder loginRequestBuilder = new RequestBuilder(RequestBuilder.GET, "spring/login/getRoles");
		try {
			loginRequestBuilder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						AutoBeanFactory loginFactory = GWT.create(ILoginFactory.class);
						JsonReader<ILoginDto, ILoginDto> reader = new JsonReader<ILoginDto, ILoginDto>(loginFactory,
								ILoginDto.class);
						ILoginDto result = reader.read(null, response.getText());
						if (result.getStatus() == ELoginStatus.SUCCESS) {
							roles = result.getRoles();
							buildInterface();
						} else {

						}
					}else {
						buildLoginWindow();
					}

				}

				@Override
				public void onError(Request request, Throwable exception) {
					AlertMessageBox mbox = new AlertMessageBox(constants.generalConnectivityErrorTitle(), constants
							.generalConnectivityErrorText()) {
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
		RootPanel.get("root").clear();
		CenterLayoutContainer clc = new CenterLayoutContainer();
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
							if (response.getStatusCode() == 200) {
								AutoBeanFactory loginFactory = GWT.create(ILoginFactory.class);
								JsonReader<ILoginDto, ILoginDto> reader = new JsonReader<ILoginDto, ILoginDto>(loginFactory,
										ILoginDto.class);
								ILoginDto result = reader.read(null, response.getText());
								if (result.getStatus() == ELoginStatus.SUCCESS) {
									roles = result.getRoles();
									buildInterface();
								} else {
									AlertMessageBox mbox = new AlertMessageBox(constants.loginFailedTitle(), constants
											.loginFailedText());
									mbox.show();
								}
							} else {
								AlertMessageBox mbox = new AlertMessageBox(constants.generalConnectivityErrorTitle(), Integer
										.toString(response.getStatusCode()));
								mbox.show();
							}
						}

						@Override
						public void onError(Request request, Throwable exception) {
							AlertMessageBox mbox = new AlertMessageBox(constants.generalConnectivityErrorTitle(), constants
									.generalConnectivityErrorText());
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

	private void buildInterface() {
		Container mainContainer = new HorizontalLayoutContainer();
		mainContainer.add(createMenuPanel());
		contentPanel.setWidth(1000);
		mainContainer.add(contentPanel);
		contentPanel.setHeaderVisible(false);
		RootPanel.get("root").clear();
		RootPanel.get("root").add(mainContainer);
	}

	public Container createMenuPanel() {
		menuPanel.clear();
		menuGroup.clear();
		for (EntUserRole role : roles) {
			GWT.log(role.toString());
		}
		final MonitorButton buttonEmp = new MonitorButton(constants.employeesButtonText(), EntUserRole.ROLE_USER) {
			@Override
			protected void selectHandler() {
				contentPanel.clear();
				contentPanel.add(getEmpGrid());
			}
		};

		final MonitorButton buttonCompanies = new MonitorButton(constants.companiesButtonText(), EntUserRole.ROLE_USER) {
			@Override
			protected void selectHandler() {
				contentPanel.clear();
				contentPanel.add(getCompanyGrid());
			}
		};

		final MonitorButton buttonUsers = new MonitorButton(constants.usersButtonText(), EntUserRole.ROLE_ADMIN) {
			@Override
			protected void selectHandler() {
				contentPanel.clear();
				contentPanel.add(getUsersGrid());
			}
		};

		final MonitorButton buttonLogout = new MonitorButton(constants.logoutButtonText(), EntUserRole.ROLE_ADMIN, EntUserRole.ROLE_USER) {
			@Override
			protected void selectHandler() {
				RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "spring/logout");
				try {
					builder.setCallback(new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode()==200){
								buildLoginWindow();
							} else {
								AlertMessageBox mbox = new AlertMessageBox(constants.generalConnectivityErrorTitle(), constants
										.generalConnectivityErrorText());
								mbox.show();
							}
						}
						@Override
						public void onError(Request request, Throwable exception) {
							AlertMessageBox mbox = new AlertMessageBox(constants.generalConnectivityErrorTitle(), constants
									.generalConnectivityErrorText());
							mbox.show();
						}
					});
					builder.send();
				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		};
		
		addButton(buttonEmp);
		addButton(buttonCompanies);
		addButton(buttonUsers);
		addButton(buttonLogout);
		return menuPanel;
	}

	private void addButton(MonitorButton button) {
		for (EntUserRole role : button.getRolesAllowed()) {
			if (roles.contains(role)) {
				menuPanel.add(button);
				menuGroup.add(button);
				break;
			}
		}
	}

	private EmployeeEditingGrid getEmpGrid() {
		if (empGrid == null) {
			empGrid = new EmployeeEditingGrid("spring/employee");
		}
		return empGrid;
	}

	private CompanyEditingGrid getCompanyGrid() {
		if (companyGrid == null) {
			companyGrid = new CompanyEditingGrid("spring/company");
		}
		return companyGrid;
	}

	private UserEditingGrid getUsersGrid() {
		if (userGrid == null) {
			userGrid = new UserEditingGrid("spring/user");
		}
		return userGrid;
	}
}
