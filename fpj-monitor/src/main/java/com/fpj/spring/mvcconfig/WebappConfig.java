package com.fpj.spring.mvcconfig;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpj.client.dtos.ICompanyDto;
import com.fpj.client.dtos.IEmployeeDto;
import com.fpj.client.dtos.IUserDto;
import com.fpj.spring.jackson.mixin.FilterConfigMixIn;
import com.fpj.spring.jackson.mixin.ICompanyDtoMixIn;
import com.fpj.spring.jackson.mixin.IEmployeeDtoMixIn;
import com.fpj.spring.jackson.mixin.IUserDtoMixIn;
import com.fpj.spring.jackson.mixin.SortInfoMixIn;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterConfig;

@Configuration
@EnableWebMvc
@ComponentScan("com.fpj.spring.controller")
@EnableGlobalMethodSecurity(securedEnabled=true)
public class WebappConfig extends WebMvcConfigurerAdapter {

	@Bean
	public InternalResourceViewResolver setupViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");

		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("/resources/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/spring/login/error");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Bean
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.addMixIn(SortInfo.class, SortInfoMixIn.class);
		objectMapper.addMixIn(FilterConfig.class, FilterConfigMixIn.class);
		objectMapper.addMixIn(ICompanyDto.class, ICompanyDtoMixIn.class);
		objectMapper.addMixIn(IEmployeeDto.class, IEmployeeDtoMixIn.class);
		objectMapper.addMixIn(IUserDto.class, IUserDtoMixIn.class);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(customJackson2HttpMessageConverter());
	}
}
