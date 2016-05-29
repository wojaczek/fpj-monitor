package com.fpj.client.dtos.factories;

import com.fpj.client.dtos.ILoginDto;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface ILoginFactory extends AutoBeanFactory {
	AutoBean<ILoginDto> loginDto();
}
