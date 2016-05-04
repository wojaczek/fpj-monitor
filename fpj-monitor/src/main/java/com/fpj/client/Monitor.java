package com.fpj.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Monitor implements EntryPoint {


	EmployeeEditingGrid empGrid;
	private final ContentPanel contentPanel = new ContentPanel();
	
	public void onModuleLoad() {
		Container mainContainer = new HorizontalLayoutContainer();
		mainContainer.add(createMenuPanel());
		mainContainer.add(contentPanel);
		contentPanel.setHeaderVisible(false);
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
					contentPanel.add(empGrid);	
				}
			}
		});
		final ToggleButton buttonNone = new ToggleButton("None");
		buttonNone.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()){
					contentPanel.clear();
					contentPanel.add(new ContentPanel());
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


}
