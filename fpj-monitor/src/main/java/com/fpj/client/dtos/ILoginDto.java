package com.fpj.client.dtos;

import java.util.List;

public interface ILoginDto {
	ELoginStatus getStatus();
	void setStatus(ELoginStatus status);
	List<EntUserRole> getRoles();
	void setRoles(List<EntUserRole> roles);
	String getMessage();
	void setMessage(String message);

}
