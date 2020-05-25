package com.test.demo;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import com.test.demo.controller.Controller;
import org.springframework.util.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	
	@Autowired
	private Controller con;

	@Test
	@DirtiesContext
	public void testControllerBean() {
		Assert.notNull(con, "Object not null");
	}
	
	@Test
	public void testControolerBeanMethod() {
		Assert.notNull(con.greeting("TEST_NAME", Mockito.mock(Model.class)),"Object not null");
	}

}
