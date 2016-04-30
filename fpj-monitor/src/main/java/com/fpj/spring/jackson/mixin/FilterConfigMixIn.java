package com.fpj.spring.jackson.mixin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sencha.gxt.data.shared.loader.FilterConfigBean;

@JsonDeserialize(as = FilterConfigBean.class)
public class FilterConfigMixIn {

}