package com.fpj.spring.dtos;

import com.fpj.client.dtos.IUserDto;
import com.fpj.client.dtos.loadResults.IUserPagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@SuppressWarnings("serial")
public class UserPagingLoadResultBean extends PagingLoadResultBean<IUserDto> implements IUserPagingLoadResult {

}
