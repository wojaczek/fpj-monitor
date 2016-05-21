package com.fpj.spring.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PrintOutJob {

	private int i=0;
	@Scheduled(cron="0 * * * * *")
	void print(){
		System.out.println("Run no: " + ++i );
	}
}
