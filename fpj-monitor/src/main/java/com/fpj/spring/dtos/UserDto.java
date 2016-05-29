package com.fpj.spring.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fpj.client.dtos.EntUserRole;
import com.fpj.client.dtos.IUserDto;

public class UserDto extends IdentifiableDto implements IUserDto {

	private String username;
	private String email;
	private String password;
	private String passwordRepeat;
	private List<EntUserRole> roles = new ArrayList<EntUserRole>();
	private Boolean enabled;

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username=username;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email=email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password=password;
	}

	@Override
	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	@Override
	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	@Override
	public List<EntUserRole> getRoles() {
		return roles;
	}

	@Override
	public void setRoles(List<EntUserRole> roles) {
		this.roles=roles;
	}

	@Override
	public Boolean getEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(Boolean enabled) {
		this.enabled=enabled;
	}

}
