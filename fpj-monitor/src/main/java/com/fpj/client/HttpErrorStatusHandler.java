package com.fpj.client;

import com.google.gwt.core.shared.GWT;

public class HttpErrorStatusHandler {
	Integer statusCode;
	HttpErrorConstants constants = GWT.create(HttpErrorConstants.class);
	private String message = constants.unknown();
	public HttpErrorStatusHandler(int statusCode) {
		this.statusCode=statusCode;
		switch (statusCode){
		case 500: handle500();
		break;
		}
	}
	private void handle500() {
		this.message = constants.s500();
	}

	public String getMessage(){
		return message;
	}
}
