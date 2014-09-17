package com.cmn397.restexplorer;

import com.cmn397.androidsharedservices.SingleFragmentActivity;

import android.app.Fragment;
import android.view.Menu;


public class MainActivity extends SingleFragmentActivity {
	
	protected Fragment createFragment(int fragmentID) {
		return new ChooseRequestFragment();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
