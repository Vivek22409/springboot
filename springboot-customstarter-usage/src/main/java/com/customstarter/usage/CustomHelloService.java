package com.customstarter.usage;

import com.customstarter.service.HelloService;

public class CustomHelloService implements HelloService {

	@Override
	public void sayHello() {
		System.out.println("Hello to custom world");
	}

}
