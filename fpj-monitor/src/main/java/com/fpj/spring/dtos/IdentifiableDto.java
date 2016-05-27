package com.fpj.spring.dtos;

import com.fpj.client.dtos.IIdentifiableDto;

public class IdentifiableDto implements IIdentifiableDto{

	private Integer id;
	
	@Override
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
