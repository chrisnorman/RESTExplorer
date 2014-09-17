package com.cmn397.restexplorer;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmn397.androidsharedservices.SingleFragment;

public class ResultFragment extends SingleFragment {
	RESTRequestRecord mRSR = null;
	RESTAsyncClient mASRC = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.result_display_view, parent, false);
		mASRC = new RESTAsyncClient();
		mASRC.setFragment(this);
		mASRC.execute(mRSR);
		return v;
	}
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	public void setRESTServerRecord(RESTRequestRecord rsr) { mRSR = rsr; };
}
