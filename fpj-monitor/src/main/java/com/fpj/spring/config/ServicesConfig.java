package com.fpj.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages="com.fpj.spring.service")
@EnableAsync
@EnableScheduling
public class ServicesConfig {

}
