package com.fpj.spring.jackson.mixin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fpj.spring.dtos.CompanyDto;

@JsonDeserialize(as = CompanyDto.class)
public class ICompanyDtoMixIn {

}
