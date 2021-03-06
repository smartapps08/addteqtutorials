package com.myorg.notescontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "notesdb";
	public static final String TABLE_NAME = "notes";
	public static final String NOTE_ID = "_id";
	public static final String NOTE_TITLE = "title";
	public static final String NOTE_BODY = "body";
	
	private static String CREATE_TABLE_SQL = "Create table "
			+ DBHelper.TABLE_NAME + " (" + DBHelper.NOTE_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + DBHelper.NOTE_TITLE
			+ " TEXT, " + DBHelper.NOTE_BODY + " TEXT);";

	public DBHelper(Context context) {
		super(context, DBHelper.DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// create the tables here
		database.execSQL(DBHelper.CREATE_TABLE_SQL);
		Log.d("DBHelper", "Table Created: "+DBHelper.CREATE_TABLE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// Logic for database version

	}

}
