package com.fpj.client.buttons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fpj.client.dtos.EntUserRole;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.ToggleButton;

public abstract class MonitorButton extends ToggleButton {

	private List<EntUserRole> rolesAllowed = new ArrayList<EntUserRole>();

	private MonitorButton(String text){
		
	}
	
	public MonitorButton(String text, EntUserRole... roles) {
		super(text);
		Collections.addAll(getRolesAllowed(), roles);
		addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					selectHandler();
				}
			}
		});
	}

	protected abstract void selectHandler();

	public List<EntUserRole> getRolesAllowed() {
		return rolesAllowed;
	}

	public void setRolesAllowed(List<EntUserRole> rolesAllowed) {
		this.rolesAllowed = rolesAllowed;
	}

}
