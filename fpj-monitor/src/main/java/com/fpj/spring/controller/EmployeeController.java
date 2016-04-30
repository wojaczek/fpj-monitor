package com.fpj.spring.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpj.client.IEmployeeDto;
import com.fpj.client.IEmployeeListLoadResult;
import com.fpj.spring.dtos.EmployeeDto;
import com.fpj.spring.dtos.EmployeeListLoadResult;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;

@Controller
@RequestMapping("/employee/")
public class EmployeeController {

	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody IEmployeeListLoadResult listAll(@RequestBody FilterPagingLoadConfigBean loadConfig) {
		EmployeeListLoadResult result = new EmployeeListLoadResult();
		List<IEmployeeDto> resultData = new ArrayList<IEmployeeDto>();
		EmployeeDto dto = new EmployeeDto();
		dto.setCompanyName("company");
		dto.setFirstName("Jan");
		dto.setLastName("Nowak");
		dto.setId(1);
		dto.setVisaExpiredDate(new Date());
		resultData.add(dto);
		result.setData(resultData);
		result.setTotalLength(1);
		result.setOffset(0);
		return result;

	}

}
