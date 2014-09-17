package com.cmn397.restexplorer;

public class RESTResponseRecord {
	String data = "";
	String contentType = "";

	Exception e = null;

	public String getData() {
		return this.data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public String getContentType() {
		return this.contentType;
	}
	public void setContentType(String cType) {
		this.contentType = cType;
	}

	public String getStringResult() {
		if (null != e)
			return e.getMessage();
		else
			return data;
	}

	public Exception getE() {
		return this.e;
	}

	public void setE(Exception e) {
		this.e = e;
	}
}

