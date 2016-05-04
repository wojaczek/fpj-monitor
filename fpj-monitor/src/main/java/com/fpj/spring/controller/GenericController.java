package com.fpj.spring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpj.spring.service.GenericService;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

public abstract class GenericController<DTO, LIST_RESULT extends PagingLoadResultBean<DTO>> {

	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LIST_RESULT listAll(@RequestBody FilterPagingLoadConfigBean loadConfig){
		return getService().list(loadConfig);
	}

	@RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void saveChange(@RequestBody DTO dto){
		
	}
	
	protected abstract  GenericService<DTO, LIST_RESULT, ?> getService(); 
}
