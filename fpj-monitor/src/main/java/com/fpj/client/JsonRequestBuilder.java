package com.fpj.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;

public abstract class JsonRequestBuilder extends RequestBuilder {
	private GenericGridConstants constants = GWT.create(GenericGridConstants.class);

	public JsonRequestBuilder(Method httpMethod, String url) {
		super(httpMethod, url);
		this.setHeader("Accept", "application/json");
		this.setHeader("Content-Type", "application/json");
		setCallback(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == 200) {
					successMethod(response);
				} else {
					HttpErrorStatusHandler handler = new HttpErrorStatusHandler(response.getStatusCode());
					AlertMessageBox mBox = new AlertMessageBox(constants.deleteErrorTitle(), handler
							.getMessage());
					mBox.show();
				}

			}

			@Override
			public void onError(Request request, Throwable exception) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	public Request sendRequest(String data){
	    try {
			return sendRequest(data, getCallback());
		} catch (RequestException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected abstract void successMethod(Response response);
}
