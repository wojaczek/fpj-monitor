package com.fpj.spring.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.fpj.spring.config.DatabaseConfig;
import com.fpj.spring.config.PropertyConfiguration;
import com.fpj.spring.dtos.CompanyPagingResultBean;
import com.fpj.spring.entities.EntCompany;
import com.fpj.spring.repository.CompanyRepository;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={DatabaseConfig.class, PropertyConfiguration.class, CompanyService.class})
public class CompanyServiceTest {
@Autowired
CompanyService companyService;
@Autowired
CompanyRepository companyRepository;
	@Test
	public void testGetAll() {
		EntCompany company = new EntCompany();
		company.setCompanyName("FPJ");
		companyRepository.save(company);
		FilterPagingLoadConfigBean loadConfig = new FilterPagingLoadConfigBean();
		loadConfig.setLimit(20);
		loadConfig.setOffset(0);
		CompanyPagingResultBean resultBean = companyService.list(loadConfig);
		Assert.assertEquals("Not retrieved properly", 1, resultBean.getTotalLength());
		Assert.assertEquals("Not retrieved correctly", 1, resultBean.getData().size());
		Assert.assertEquals("Not retrieved correctly", "FPJ", resultBean.getData().get(0).getCompanyName());
	}

}
