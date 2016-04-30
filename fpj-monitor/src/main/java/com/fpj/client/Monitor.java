package com.fpj.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.container.NorthSouthContainer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Monitor implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */

	/*
	 * This is the entry point method.
	 */
	private NorthSouthContainer northSouthContainer;

	EmployeeEditingGrid empGrid = new EmployeeEditingGrid();
	static{
		System.out.println("Class created");
	}
	
	public void onModuleLoad() {
	//	RootPanel.get("monitor").clear();
		northSouthContainer = new NorthSouthContainer();
		northSouthContainer.setSouthWidget(empGrid);
		RootPanel.get("monitor").add(northSouthContainer);
	}



}
