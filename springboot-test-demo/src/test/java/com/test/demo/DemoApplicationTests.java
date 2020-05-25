package com.test.demo;




import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.test.demo.controller.Controller;
import org.springframework.util.Assert;


@SpringBootTest
public class DemoApplicationTests {
	
	@Autowired
	private Controller con;

	@Test
	public void testControllerBean() {
		Assert.notNull(con, "Object not null");
	}

}
