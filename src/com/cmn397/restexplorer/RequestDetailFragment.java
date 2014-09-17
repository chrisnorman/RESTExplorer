package com.cmn397.restexplorer;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.cmn397.androidsharedservices.SingleFragment;
import com.cmn397.restexplorer.ServerDBOpenHelper;

/*
 *  TODO: enable save request button based on server url content
 *  TODO: provide feedback when a request is saved
 *
 */
public class RequestDetailFragment extends SingleFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.activity_request, parent, false);

		EditText DescriptionET		= (EditText) v.findViewById(R.id.description_et);
		EditText ServerURLET		= (EditText) v.findViewById(R.id.serverurl_et);
		EditText TargetResourceET	= (EditText) v.findViewById(R.id.targetresource_et);
		EditText ParamET			= (EditText) v.findViewById(R.id.params_et);

		Bundle bundle = getArguments();
		if (bundle == null) {	// use default preset target
			DescriptionET.setText("UniProt Database");
			ServerURLET.setText("http://www.uniprot.org/uniprot");
			TargetResourceET.setText("P12345");
			ParamET.setText("format=txt&limit=3");			
		}
		else {
			int dbid = bundle.getInt("_id");

			ServerDBOpenHelper dbHelper = new ServerDBOpenHelper(parent.getContext());
			RESTRequestRecord rrr = dbHelper.getRecordFromID(dbid);
			dbHelper.close();

			Uri uri = Uri.parse(rrr.getURL());

			DescriptionET.setText(rrr.getDescription());
			ServerURLET.setText(uri.getScheme() + "://" + uri.getHost() + uri.getPath());
			ParamET.setText(uri.getQuery());	
		}

		Button DoRequestButton = (Button) v.findViewById(R.id.dorequestid);
		DoRequestButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				ResultFragment rdf = new ResultFragment();
				rdf.setRESTServerRecord(createRequestRecord());
				FragmentManager fm = getFragmentManager();
				fm.beginTransaction()
					.replace(mFragmentID, rdf, "Main Fragment")
					.addToBackStack(null)
					.commit(); 
			}
		});

		Button SaveRequestButton = (Button) v.findViewById(R.id.saverequestid);
		SaveRequestButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				ServerDBOpenHelper dbHelper = new ServerDBOpenHelper(arg0.getContext());
				RESTRequestRecord rsr = createRequestRecord();
				dbHelper.insertRecord(rsr);
				dbHelper.close();
			}
		});

		return v;
	}
	
	private RESTRequestRecord createRequestRecord() {
		RESTRequestRecord rsr = new RESTRequestRecord();

		EditText DescriptionET = (EditText) getView().findViewById(R.id.description_et);
		EditText ServerURLET = (EditText) getView().findViewById(R.id.serverurl_et);
		EditText TargetResourceET = (EditText) getView().findViewById(R.id.targetresource_et);
		EditText ParamET = (EditText) getView().findViewById(R.id.params_et);

		String desc =DescriptionET.getText().toString();
		if (desc == "")
			rsr.setDescription("No description available");
		else
			rsr.setDescription(desc);

		rsr.setURL(ServerURLET.getText().toString());
		rsr.setResource(TargetResourceET.getText().toString());
		rsr.setParams(ParamET.getText().toString());

		return rsr;
	}
}
