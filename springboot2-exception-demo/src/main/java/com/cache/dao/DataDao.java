package com.cache.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DataDao {
	
	public List<String> getData() {
		
		List<String> list = new ArrayList<>();		
		list.add("abc");
		list.add("def");
		list.add("ghi");
		
		return list;
	}

}
