package com.cmn397.restexplorer;

import android.net.Uri;

public class RESTRequestRecord {
	private String  mURL,
					mResource,
					mParams,
					mDescription;

	public String getURL() {
		return this.mURL;
	}
	public void setURL(String uRL) {
		this.mURL = uRL;
	}

	public String getResource() {
		return this.mResource;
	}
	public void setResource(String resource) {
		this.mResource = resource;
	}

	public String getParams() {
		return this.mParams;
	}
	public void setParams(String params) {
		this.mParams = params;
	}

	public void setDescription(String desc) {
		this.mDescription = desc;
	}
	public String getDescription() {
		return this.mDescription;
	}

	public String buildRequestURI() {
		String raw = getURL();
		String resource = getResource();
		if (!resource.equals(""))
			raw = raw + "/" + resource;
		String params = getParams();
		if (params != "")
			raw = raw + "?" + getParams();

		Uri.Builder b = Uri.parse(raw).buildUpon();
		String res = b.build().toString();
		return res;
	}
}
