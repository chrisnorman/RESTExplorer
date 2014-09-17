package com.cmn397.restexplorer;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.HeaderIterator;
import org.apache.http.Header;

public class ResponseTypeHandler implements	ResponseHandler<RESTResponseRecord> {
	@Override
	public RESTResponseRecord handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException
	{
		RESTResponseRecord	rrr 		= new RESTResponseRecord();
		String 				strResult 	= null;

		if (response.getStatusLine().getStatusCode() == 200) {
			HeaderIterator headIt = response.headerIterator();
		    if (headIt != null) {
			    while (headIt.hasNext()) {
				    Header hd = headIt.nextHeader();
					if (hd.getName().equals("Content-Type")) {
						String ct = hd.getValue();
						rrr.setContentType(ct);
					}
				}
			}
		}
		else
			strResult = response.getStatusLine().getReasonPhrase();
		if (strResult == null)
			strResult = new BasicResponseHandler().handleResponse(response);
		rrr.setData(strResult);
		return rrr;
	}
}

