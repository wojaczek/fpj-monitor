package com.fpj.client;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface LoadConfigFactory<MODEL_TYPE, RESULT_TYPE extends PagingLoadResult<MODEL_TYPE>> extends AutoBeanFactory {
	AutoBean<FilterPagingLoadConfig> loadConfig();
	AutoBean<MODEL_TYPE> modelType();
	AutoBean<RESULT_TYPE> result();
	AutoBean<SortInfo> getSortInfo();

}
