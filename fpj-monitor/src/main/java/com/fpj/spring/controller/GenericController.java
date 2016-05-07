package com.fpj.spring.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fpj.client.IdentifiableDto;
import com.fpj.spring.exception.NotFoundException;
import com.fpj.spring.service.GenericService;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

public abstract class GenericController<DTO extends IdentifiableDto, LIST_RESULT extends PagingLoadResultBean<DTO>> {

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
	@ExceptionHandler(NotFoundException.class)
	public void entityNotFound() {
		// Nothing to do
	}

	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LIST_RESULT listAll(@RequestBody FilterPagingLoadConfigBean loadConfig) {
		return getService().list(loadConfig);
	}

	@RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void saveChange(@RequestBody DTO dto) throws NotFoundException {
		getService().update(dto);
	}

	protected abstract GenericService<DTO, LIST_RESULT, ?> getService();
}
