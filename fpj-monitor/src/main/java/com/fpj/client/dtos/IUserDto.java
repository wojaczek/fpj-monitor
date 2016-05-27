package com.fpj.client.dtos;

import java.util.List;

public interface IUserDto extends IIdentifiableDto {
	String getUsername();
	void setUsername(String username);
	
	String getEmail();
	void setEmail(String email);
	
	String getPassword();
	void setPassword(String password);
	
	String getPasswordRepeat();
	void setPasswordRepeat(String passwordRepeat);
	
	List<String> getRoles();
	void setRoles(List<String> roles);
	
	Boolean getEnabled();
	void setEnabled(Boolean enabled);
}
