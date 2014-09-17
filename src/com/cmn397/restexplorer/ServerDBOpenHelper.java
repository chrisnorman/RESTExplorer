package com.cmn397.restexplorer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 *  "RESTServers" SQLite database schema:
 *  	Table: restservers
 *  		column: _id:integer
 *  		column: url:text
 *  		column: description:text
 */

public class ServerDBOpenHelper extends SQLiteOpenHelper {

	// all public and accessed from fragment ui code
	final static String DB_NAME 			= "RESTServers";
	final static String TABLE_NAME 			= "servers";
	final static String CREATE_COMMAND 		= "CREATE TABLE servers (_id INTEGER PRIMARY KEY AUTOINCREMENT, url TEXT NOT NULL, description TEXT NOT NULL)";
	final static int	DB_VERSION			= 1;
	final static String	URLColumn			= "url";
	final static String	DescriptionColumn 	= "description";
	final static String[] displayColumns 	= { "_id", DescriptionColumn };

	public ServerDBOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_COMMAND);
		primeDB(db);
	}

	/* 
	 * Initially populate the DB with a couple of sample REST endpoints.
	 * 
	 */
	private void primeDB(SQLiteDatabase db) { 
		ContentValues values = new ContentValues();

		// Uniprot Protein Database
		values.put(ServerDBOpenHelper.URLColumn, "http://www.uniprot.org/uniprot/P12345?format=txt&limit=3");
		values.put(ServerDBOpenHelper.DescriptionColumn, "Uniprot Protein Database");
		db.insert(ServerDBOpenHelper.TABLE_NAME, null, values);

		// Yahoo stock quotes
		values.put(ServerDBOpenHelper.URLColumn, "http://download.finance.yahoo.com/d/quotes.json?s=GOOG+MSFT&f=sb2b3jk");
		values.put(ServerDBOpenHelper.DescriptionColumn, "Stock Quotes");
		db.insert(ServerDBOpenHelper.TABLE_NAME, null, values);
	}
	
	/*
	 * Return the URL column of a request record given it's database id.
	 */
	RESTRequestRecord getRecordFromID(int id) {
		String s = Integer.toString(id);
		Cursor c = getReadableDatabase().query(
				TABLE_NAME,
				new String[]{URLColumn, DescriptionColumn}, 
				"_id == ?",
				new String[]{s}, 
				null,
				null,
				null
		);
		assert(c.getCount() == 1);
		c.moveToFirst();
		RESTRequestRecord rrr = new RESTRequestRecord();
		int uc = c.getColumnIndex(URLColumn);
		int dc = c.getColumnIndex(DescriptionColumn);
		String desc = c.getString(dc);
		String url = c.getString(uc);
		rrr.setURL(url);
		rrr.setDescription(desc);
		return rrr;
	}
	
	void insertRecord(RESTRequestRecord rsr) {
		ContentValues values = new ContentValues();
		values.put(ServerDBOpenHelper.URLColumn, rsr.buildRequestURI());
		values.put(ServerDBOpenHelper.DescriptionColumn, rsr.getDescription());
		this.getWritableDatabase().insert(ServerDBOpenHelper.TABLE_NAME, null, values);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		throw new IllegalStateException();
	}
}
