package com.fpj.spring.repository;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.fpj.client.dtos.EntUserRole;
import com.fpj.spring.config.DatabaseConfig;
import com.fpj.spring.config.PropertyConfiguration;
import com.fpj.spring.entities.EntUser;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={DatabaseConfig.class, PropertyConfiguration.class})
public class UserRepositoryTest {
	@Autowired
	UserRepository userRepository;
	
	@Test
	@Transactional
	public void testSave(){
		EntUser user = new EntUser();
		user.setUsername("FPJ");
		user.getRoles().add(EntUserRole.ROLE_ADMIN);
		userRepository.save(user);
		Assert.assertEquals("entity not saved/retrieved", 1, userRepository.findAll().size());
		Assert.assertTrue("entity not saved/retrieved", userRepository.findAll().get(0).getRoles().contains(EntUserRole.ROLE_ADMIN));
		Assert.assertFalse("entity not saved/retrieved", userRepository.findAll().get(0).getRoles().contains(EntUserRole.ROLE_USER));
	}
}
