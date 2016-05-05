package com.fpj.client;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;

public interface LoadConfigFactory extends AutoBeanFactory {
	AutoBean<FilterPagingLoadConfig> loadConfig();


}
