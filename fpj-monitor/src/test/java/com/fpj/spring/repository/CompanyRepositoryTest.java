package com.fpj.spring.repository;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.fpj.spring.config.DatabaseConfig;
import com.fpj.spring.config.PropertyConfiguration;
import com.fpj.spring.entities.EntCompany;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={DatabaseConfig.class, PropertyConfiguration.class})
public class CompanyRepositoryTest {
	@Autowired
	CompanyRepository companyRepository;
	
	@Test
	public void testSave(){
		EntCompany company = new EntCompany();
		company.setCompanyName("FPJ");
		companyRepository.save(company);
		Assert.assertEquals("entity not saved/retrieved", 1, companyRepository.findAll().size());
	}
}
