package com.fpj.spring.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.fpj.client.dtos.EntUserRole;
import com.fpj.spring.config.DatabaseConfig;
import com.fpj.spring.config.PropertyConfiguration;
import com.fpj.spring.dtos.UserPagingLoadResultBean;
import com.fpj.spring.entities.EntUser;
import com.fpj.spring.repository.UserRepository;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { DatabaseConfig.class,
		PropertyConfiguration.class, UserService.class})
public class UserServiceTest {
	private static final String USERNAME = "FPJ";
	@Autowired
	IUserService userService;
	@Autowired
	UserRepository userRepository;

	@Test
	public void testGetAll() {
		EntUser user = new EntUser();
		user.setUsername(USERNAME);
		userRepository.save(user);
		FilterPagingLoadConfigBean loadConfig = new FilterPagingLoadConfigBean();
		loadConfig.setLimit(20);
		loadConfig.setOffset(0);
		UserPagingLoadResultBean resultBean = userService.list(loadConfig);
		Assert.assertEquals("Not retrieved properly", 1, resultBean.getTotalLength());
		Assert.assertEquals("Not retrieved correctly", 1, resultBean.getData().size());
		Assert.assertEquals("Not retrieved correctly", USERNAME, resultBean.getData().get(0).getUsername());
	}

	@Test
	@Transactional
	public void testFindByUsername(){
		EntUser user = new EntUser();
		user.setUsername(USERNAME);
		user.getRoles().add(EntUserRole.ROLE_ADMIN);
		userRepository.save(user);
		UserDetails userDetails = userService.loadUserByUsername(USERNAME);
		Assert.assertEquals("User not retrieved correctly", USERNAME, userDetails.getUsername());
		Assert.assertEquals("User not retrieved correctly", 1, userDetails.getAuthorities().size());
		GrantedAuthority[] array = new GrantedAuthority[0];
		Assert.assertEquals("User not retrieved correctly", EntUserRole.ROLE_ADMIN.toString(), userDetails.getAuthorities().toArray(array)[0].getAuthority());
	}	
	
}
