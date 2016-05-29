package com.fpj.spring.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fpj.client.dtos.ELoginStatus;
import com.fpj.client.dtos.EntUserRole;
import com.fpj.client.dtos.ILoginDto;


public class LoginDto implements ILoginDto {

	private ELoginStatus status;
	private List<EntUserRole> roles = new ArrayList<EntUserRole>();
	private String message;

	@Override
	public ELoginStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(ELoginStatus status) {
		this.status=status;
	}

	@Override
	public List<EntUserRole> getRoles() {
		return roles ;
	}

	@Override
	public void setRoles(List<EntUserRole> roles) {
		this.roles=roles;
	}

	@Override
	public void setMessage(String message) {
		this.message=message;
	}

	@Override
	public String getMessage(){
		return message;
	}
}
