package com.advenspirit.model;

public class Response {

	private long code;
	private String message;
	private String description;
	private Object object = new String("empty");

	public Response() {
	}

	public Response(long code, String message, String description) {
		super();
		this.code = code;
		this.message = message;
		this.description = description;

	}

	public Response(long code, String message, String description, Object object) {
		super();
		this.code = code;
		this.message = message;
		this.description = description;
		this.object = object;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "Response [code=" + code + ", message=" + message + ", description=" + description + "]";
	}

}
