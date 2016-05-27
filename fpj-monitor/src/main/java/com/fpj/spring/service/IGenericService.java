package com.fpj.spring.service;

import com.fpj.client.dtos.IIdentifiableDto;
import com.fpj.spring.entities.IdentifiableEntity;
import com.fpj.spring.exception.NotFoundException;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

public interface IGenericService<DTO_TYPE extends IIdentifiableDto, LIST_RESULT extends PagingLoadResultBean<DTO_TYPE>, ENTITY extends IdentifiableEntity> {

	public abstract LIST_RESULT list(FilterPagingLoadConfigBean loadConfig);

	public abstract void update(DTO_TYPE dto) throws NotFoundException;

	public abstract DTO_TYPE createEmpty();

	public abstract LIST_RESULT list();

	public abstract void delete(Integer id);

}