package com.fpj.spring.dtos;

import com.fpj.client.IIdentifiableDto;

public class IDentifiableDto implements IIdentifiableDto{

	private Integer id;
	
	@Override
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
