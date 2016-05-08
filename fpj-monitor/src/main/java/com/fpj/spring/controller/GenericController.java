package com.fpj.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fpj.client.IIdentifiableDto;
import com.fpj.spring.exception.NotFoundException;
import com.fpj.spring.service.GenericService;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

public abstract class GenericController<DTO extends IIdentifiableDto, LIST_RESULT extends PagingLoadResultBean<DTO>> {

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not found")
	@ExceptionHandler(NotFoundException.class)
	public void entityNotFound() {
		// Nothing to do
	}

	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LIST_RESULT listAllFiltered(@RequestBody FilterPagingLoadConfigBean loadConfig) {
		return getService().list(loadConfig);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LIST_RESULT listAll() {
		return getService().list();
	}

	@RequestMapping(value = "create", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DTO create() {
		return getService().createEmpty();
	}

	@RequestMapping(value="delete/{1}", method= RequestMethod.GET,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void delete(@PathVariable Integer id){
		getService().delete(id);
	}
	
	public DTO save(DTO dto) throws NotFoundException {
			getService().update(dto);
			return dto;
	}

	protected abstract GenericService<DTO, LIST_RESULT, ?> getService();
}
