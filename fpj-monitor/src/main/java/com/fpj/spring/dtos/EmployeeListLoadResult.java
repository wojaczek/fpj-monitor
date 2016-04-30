package com.fpj.spring.dtos;

import java.util.List;

import com.fpj.client.IEmployeeDto;
import com.fpj.client.IEmployeeListLoadResult;

@SuppressWarnings("serial")
public class EmployeeListLoadResult implements IEmployeeListLoadResult {

	private int totalLength;
	private int offset;
	private List<IEmployeeDto> data;

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public int getTotalLength() {
		return totalLength;
	}

	@Override
	public void setOffset(int offset) {
		this.offset=offset;
	}

	@Override
	public void setTotalLength(int totalLength) {
		this.totalLength=totalLength;
	}

	@Override
	public List<IEmployeeDto> getData() {
		return data;
	}

	public void setData(List<IEmployeeDto> data){
		this.data=data;
	}
}
