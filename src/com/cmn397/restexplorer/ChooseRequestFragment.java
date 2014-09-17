package com.cmn397.restexplorer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;

import com.cmn397.androidsharedservices.SingleFragment;

public class ChooseRequestFragment extends SingleFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main, parent, false);
		// create list view footer
		ListView lv = (ListView) v.findViewById(R.id.servers_lv);
		lv.setHeaderDividersEnabled(true);
		TextView headerView = (TextView) inflater.inflate(R.layout.header_view, null);		
		lv.addHeaderView(headerView);

		// load up known/saved requests/servers...
		ServerDBOpenHelper dbHelper = new ServerDBOpenHelper(parent.getContext());
		Cursor c = getRequestCursor(dbHelper);
		SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(
			parent.getContext(),
			R.layout.chooserequest_view,
			c,
			ServerDBOpenHelper.displayColumns,
			new int[] { R.id.serverlistid_tv, R.id.serverlistdesc_tv},
			0
		);
		lv.setAdapter(scAdapter);
		dbHelper.close();

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
				try {
					Bundle reqBundle	= new Bundle();
					Cursor cursor		= (Cursor) parent.getItemAtPosition(position);
					reqBundle.putInt("_id", cursor.getInt(cursor.getColumnIndex("_id")));

					Fragment reqFrag	= new RequestDetailFragment();
					reqFrag.setArguments(reqBundle);

					FragmentManager fm = getFragmentManager();
							fm.beginTransaction()
								.replace(mFragmentID, reqFrag, "RequestFragmentTag")
								.addToBackStack(null)
								.commit(); 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		});

		headerView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)  {
				try {
					Fragment reqFrag = new RequestDetailFragment();
					FragmentManager fm = getFragmentManager();
							fm.beginTransaction()
								.replace(mFragmentID, reqFrag, "RequestFragmentTag")
								.addToBackStack(null)
								.commit(); 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		return v;
	}
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	private Cursor getRequestCursor(ServerDBOpenHelper dbHelper) {
		return dbHelper.getReadableDatabase().query(
				ServerDBOpenHelper.TABLE_NAME,
				ServerDBOpenHelper.displayColumns,
				null,
				new String[]{},
				null,
				null,
				null);
	}
}
