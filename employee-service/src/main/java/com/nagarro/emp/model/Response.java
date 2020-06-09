package com.nagarro.emp.model;

public class Response {

	private String code;
	private String status;
	private String msg;
	private Object data;
	
	
	public Response() {
		super();
	}

	public Response(String code, String status, String msg, Object data) {
		super();
		this.code = code;
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
