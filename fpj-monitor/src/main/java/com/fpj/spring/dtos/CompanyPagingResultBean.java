package com.fpj.spring.dtos;

import com.fpj.client.ICompanyDto;
import com.fpj.client.ICompanyPagingLoadResultBean;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@SuppressWarnings("serial")
public class CompanyPagingResultBean extends PagingLoadResultBean<ICompanyDto> implements ICompanyPagingLoadResultBean{

}
