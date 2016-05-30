package com.fpj.spring.service;


//@Service
public class PrintOutJob {

	private int i=0;
	//@Scheduled(cron="0 * * * * *")
	void print(){
		System.out.println("Run no: " + ++i );
	}
}
