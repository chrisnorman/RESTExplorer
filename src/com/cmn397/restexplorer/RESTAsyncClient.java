package com.cmn397.restexplorer;


import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.cmn397.restexplorer.RESTRequestRecord;


public class RESTAsyncClient extends AsyncTask<RESTRequestRecord, Void, RESTResponseRecord> {
	String mTAG							= "RestExplorer";
	AndroidHttpClient mClient 			= AndroidHttpClient.newInstance("");
	ResultFragment mDisplayFragment 	= null;

	@Override
	protected RESTResponseRecord doInBackground(RESTRequestRecord... params) {
		HttpGet request						= new HttpGet(params[0].buildRequestURI());
		ResponseTypeHandler responseHandler = new ResponseTypeHandler();
		RESTResponseRecord rrr				= new RESTResponseRecord();

		try {
			return mClient.execute(request, responseHandler);
		} catch (ClientProtocolException e) {
			Log.e(mTAG, "ClientProtocolException");
			rrr.setE(e);
		} catch (IOException e) {
			Log.e(mTAG, "IOException");
			rrr.setE(e);
		}

		return rrr;
	}

	@Override
	protected void onPostExecute(RESTResponseRecord result) {
		if (result != null) {
			String res = result.getStringResult();
			android.view.View view = mDisplayFragment.getView();
			if (view == null) {
				// TODO: need to properly bind to the UI
			    /*
				FragmentManager fm = getFragmentManager();
				Fragment fragment = fm.findFragmentById(R.id.single_fragment_container);
				if (null == fragment) {
					fragment = createFragment(R.id.single_fragment_container);
					fm.beginTransaction()
						.add(R.id.single_fragment_container, fragment)
						.commit();
				}
			    */
				throw new IllegalStateException();
			}
			android.widget.EditText tet = (android.widget.EditText) mDisplayFragment.getView().findViewById(R.id.result_tv);
			android.widget.EditText set = (android.widget.EditText) mDisplayFragment.getView().findViewById(R.id.resultsize);
			android.widget.EditText ctet = (android.widget.EditText) mDisplayFragment.getView().findViewById(R.id.resultcontentype_et);
			set.setText(Integer.toString(res.length()));
			ctet.setText(result.getContentType());
			tet.setText(res);
		}
		if (null != mClient)
			mClient.close();
	}

	public void setFragment(ResultFragment frag) { mDisplayFragment = frag; }
}

