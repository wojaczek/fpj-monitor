package com.fpj.client;

import com.google.web.bindery.autobean.shared.AutoBean;

public interface IEmployeeAutoBeanFactory extends LoadConfigFactory<IEmployeeDto> {
	AutoBean<IEmployeePagingoadResult> result();
}
