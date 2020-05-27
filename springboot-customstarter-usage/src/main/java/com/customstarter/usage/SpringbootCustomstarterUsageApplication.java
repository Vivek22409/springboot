package com.customstarter.usage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.customstarter.service.HelloService;


@SpringBootApplication
public class SpringbootCustomstarterUsageApplication implements CommandLineRunner {
	
	@Autowired
	private HelloService hserv;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCustomstarterUsageApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {		
		hserv.sayHello();
	}
	
	@Bean
	public HelloService getCustomHelloservice() {
		return new CustomHelloService();
	}

}
