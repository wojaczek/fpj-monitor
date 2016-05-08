package com.fpj.spring.dtos;

import com.fpj.client.ICompanyDto;
import com.fpj.client.ICompanyPagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@SuppressWarnings("serial")
public class CompanyPagingLoadResultBean extends PagingLoadResultBean<ICompanyDto> implements ICompanyPagingLoadResult{

}
