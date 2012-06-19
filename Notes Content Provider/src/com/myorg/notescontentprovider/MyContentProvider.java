package com.myorg.notescontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
	private static final String TAG = MyContentProvider.class.getSimpleName();
	public static final Uri CONTENT_URI = Uri
			.parse("content://com.myorg.notescontentprovider.provider.notescp/notescp");
	private DBHelper dbOpenHelper;

	// create constant values to differenctiate different kinds of requests
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		// content://com.myorg.notescontentprovider.provider/notescp
		uriMatcher.addURI("com.myorg.notescontentprovider.provider.notescp",
				"notescp", ALLROWS);
		// content://com.myorg.notescontentprovider.provider/notescp/4
		uriMatcher.addURI("com.myorg.notescontentprovider.provider.notescp",
				"notescp/#", SINGLE_ROW);
	}

	@Override
	public boolean onCreate() {
		dbOpenHelper = new DBHelper(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db;

		try {
			db = dbOpenHelper.getWritableDatabase();
		} catch (SQLiteException e) {
			db = dbOpenHelper.getReadableDatabase();
		}

		String groupBy = null;
		String having = null;

		// creating a database query
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(DBHelper.NOTE_ID + "=" + rowID);

		default:
			break;
		}

		queryBuilder.setTables(DBHelper.TABLE_NAME);
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, groupBy, having, sortOrder);

		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String nullColumnHack = null;

		long id = db.insert(DBHelper.TABLE_NAME, nullColumnHack, values);
		// content://auth/base/id

		if (id > -1) {
			Log.d(TAG, "Data interted properly: id:" + id);
			Uri insertedUri = ContentUris.withAppendedId(uri, id);
			return insertedUri;
		} else {
			return null;
		}

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			String selectionStr = "";
			if (selection == null || TextUtils.isEmpty(selection)) {
				selectionStr = "";
			} else {
				selectionStr = " AND (" + selection + ")";
			}
			selection = DBHelper.NOTE_ID + "=" + rowID + selectionStr;

		default:
			break;
		}

		if (selection == null) {
			selection = "1";
		}

		int deleteCount = db.delete(DBHelper.TABLE_NAME, selection,
				selectionArgs);

		return deleteCount;

	}

}
