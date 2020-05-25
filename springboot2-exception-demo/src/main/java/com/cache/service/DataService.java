package com.cache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cache.dao.DataDao;

@Service
public class DataService {
	
	@Autowired
	private DataDao dao;
	
	
	@Cacheable(value="data")//,key="#param1",condition="#param1 <25")
	public List<String> getData(){
		List<String> l = dao.getData();
		return l;		
	}

}
