package com.fpj.spring.jackson.mixin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sencha.gxt.data.shared.SortInfoBean;

@JsonDeserialize(as = SortInfoBean.class)
public class SortInfoMixIn {

}