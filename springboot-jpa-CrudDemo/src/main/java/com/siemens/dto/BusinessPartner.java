package com.siemens.dto;

public class BusinessPartner {

	private long BP_ID;
	private String BP_ID_AS_STRING;
	private String BP_NAME;
	private String BP_ALIAS;
	
	public BusinessPartner() {
		
	}
	
	public BusinessPartner(long bP_ID, String bP_ID_AS_STRING, String bP_NAME, String bP_ALIAS) {
		super();
		BP_ID = bP_ID;
		BP_ID_AS_STRING = bP_ID_AS_STRING;
		BP_NAME = bP_NAME;
		BP_ALIAS = bP_ALIAS;
	}

	
	public long getBP_ID() {
		return BP_ID;
	}	
	
	public void setBP_ID(long bP_ID) {
		BP_ID = bP_ID;
	}
	
	public String getBP_ID_AS_STRING() {
		return BP_ID_AS_STRING;
	}
	
	public void setBP_ID_AS_STRING(String bP_ID_AS_STRING) {
		BP_ID_AS_STRING = bP_ID_AS_STRING;
	}
	public String getBP_NAME() {
		return BP_NAME;
	}

	public void setBP_NAME(String bP_NAME) {
		BP_NAME = bP_NAME;
	}
	public String getBP_ALIAS() {
		return BP_ALIAS;
	}

	public void setBP_ALIAS(String bP_ALIAS) {
		BP_ALIAS = bP_ALIAS;
	}
	
	@Override
	public String toString() {
		return "BusinessPartner [BP_ID=" + BP_ID + ", BP_ID_AS_STRING=" + BP_ID_AS_STRING + ", BP_NAME=" + BP_NAME
				+ ", BP_ALIAS=" + BP_ALIAS + "]";
	}	
	
}
