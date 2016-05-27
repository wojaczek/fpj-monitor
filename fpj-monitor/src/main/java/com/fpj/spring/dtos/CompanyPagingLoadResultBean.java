package com.fpj.spring.dtos;

import com.fpj.client.dtos.ICompanyDto;
import com.fpj.client.dtos.loadResults.ICompanyPagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@SuppressWarnings("serial")
public class CompanyPagingLoadResultBean extends PagingLoadResultBean<ICompanyDto> implements ICompanyPagingLoadResult{

}
