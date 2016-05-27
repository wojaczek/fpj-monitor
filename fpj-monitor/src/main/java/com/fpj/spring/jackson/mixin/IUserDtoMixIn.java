package com.fpj.spring.jackson.mixin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fpj.spring.dtos.UserDto;

@JsonDeserialize(as = UserDto.class)
public class IUserDtoMixIn {

}
