package com.scheduler.demo;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class SpringbootSchedulerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSchedulerDemoApplication.class, args);
	}
	
	/*
	@Scheduled(fixedRate = 2000L)
	public void someJob() {
		System.out.println("Now is: "+ new Date());
	}*/
	
	/*@Scheduled(fixedDelay = 2000L)
	public void someJob() throws InterruptedException {
		System.out.println("Now is: "+ new Date()+ "#");
		Thread.sleep(1000);
	}*/
	
	@Scheduled(cron = "0 */5 * * * *") //- every 5 minutes
	public void someJob() throws InterruptedException {
		System.out.println("Now is: "+ new Date()+ "#");
		Thread.sleep(1000);
	}

}
