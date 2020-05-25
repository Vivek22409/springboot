package com.cache.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cache.service.DataService;

@RestController
public class Controller {

	@Autowired
	private DataService dataService;

	@GetMapping("/data")
	public List<String> getMessage() throws Exception {
		List<String> l = dataService.getData();
		return l;
	}

	@GetMapping("/clear")
	@CacheEvict(value = "data", allEntries = true)
	public void clearCache() {
	}

}
