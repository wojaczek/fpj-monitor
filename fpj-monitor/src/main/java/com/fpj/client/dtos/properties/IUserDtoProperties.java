package com.fpj.client.dtos.properties;

import java.util.List;

import com.fpj.client.dtos.IUserDto;
import com.fpj.client.dtos.IdPropertyAccess;
import com.sencha.gxt.core.client.ValueProvider;

public interface IUserDtoProperties extends IdPropertyAccess<IUserDto> {
	ValueProvider<IUserDto, String> username();
	ValueProvider<IUserDto, String> email();
	ValueProvider<IUserDto, String> password();
	ValueProvider<IUserDto, String> passwordRepeat();
	ValueProvider<IUserDto, List<String>> roles();
	ValueProvider<IUserDto, Boolean> enabled();
}
