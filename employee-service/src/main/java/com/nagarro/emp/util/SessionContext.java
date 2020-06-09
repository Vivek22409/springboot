package com.nagarro.emp.util;

import java.util.Map;

import com.nagarro.emp.model.EmployeeDto;

public class SessionContext {
	
	private static String sessionToken;
	private static EmployeeDto logedInEmpDetails;
	private static Map<String,String> emailIdTokenMap;

	public static String getSessionToken() {
		return sessionToken;
	}

	public static void setSessionToken(String sessionToken) {
		SessionContext.sessionToken = sessionToken;
	}

	public static EmployeeDto getLogedInEmpDetails() {
		return logedInEmpDetails;
	}

	public static void setLogedInEmpDetails(EmployeeDto logedInEmpDetails) {
		SessionContext.logedInEmpDetails = logedInEmpDetails;
	}

	public static Map<String, String> getEmailIdTokenMap() {
		return emailIdTokenMap;
	}

	public static void setEmailIdTokenMap(Map<String, String> emailIdTokenMap) {
		SessionContext.emailIdTokenMap = emailIdTokenMap;
	}
	
	public static void clear() {
		SessionContext.emailIdTokenMap = null;
		SessionContext.logedInEmpDetails=null;
		SessionContext.sessionToken=null;
	}
	
	
	
}
