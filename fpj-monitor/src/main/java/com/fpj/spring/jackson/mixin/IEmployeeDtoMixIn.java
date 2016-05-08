package com.fpj.spring.jackson.mixin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fpj.spring.dtos.EmployeeDto;

@JsonDeserialize(as = EmployeeDto.class)
public class IEmployeeDtoMixIn {

}
