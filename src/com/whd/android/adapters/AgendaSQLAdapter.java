package com.whd.android.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AgendaSQLAdapter {
	
	private static final String DBNAME 			= "Agenda.db";
	private static final String TABLE  			= "agenda";
	private static final int    VERSION 		= 1;
	
	private static final String KEY_ID 			= "_id";
	private static final String KEY_LOCATIE 	= "locatie";
	private static final String KEY_DATUM 		= "datum";
	private static final String KEY_TIJD 		= "tijd";
	private static final String KEY_AFSTAND 	= "afstand";
	
	private static String a = "TEXT";
	private static String b = "INTEGER";
	private static String c = "PRIMARY KEY";
	private static String d = "AUTOINCREMENT";
	private static String e = " ";
	private static String f = ",";
	
	private static final String SCRIPT_CREATE_DATABASE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE + " ( " +
			KEY_ID + e + b + e + c + e + d
			+ f + e +
			KEY_LOCATIE + e + a
			+ f + e +
			KEY_DATUM + e + a
			+ f + e +
			KEY_TIJD + e + a
			+ f + e +
			KEY_AFSTAND + e + a +
			");";
	  
	
	private Context context;
	private SQLiteDatabase sqLiteDatabase;
	private SQLiteOpenHelper sqLiteHelper;
	
	public AgendaSQLAdapter (Context c) {
		context = c;
	}
	
	public void close() {
		sqLiteHelper.close();
	}
	
	public int deleteAll() {
		return sqLiteDatabase.delete(TABLE, null, null);
	}
	
	public long insert(String locatie, String datum, String tijd, String afstand) {
	    ContentValues localContentValues = new ContentValues();
	    localContentValues.put(KEY_LOCATIE, locatie);
	    localContentValues.put(KEY_DATUM, datum);
	    localContentValues.put(KEY_TIJD, tijd);
	    localContentValues.put(KEY_AFSTAND, afstand);
	    return this.sqLiteDatabase.insert(TABLE, null, localContentValues);
	}

	public AgendaSQLAdapter openToRead() throws SQLException {
		sqLiteHelper = new SQLiteHelper(context, DBNAME, null);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	public AgendaSQLAdapter openToWrite() throws SQLException {
		sqLiteHelper = new SQLiteHelper(context, DBNAME, null);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public Cursor queueAll() {
		String[] arrayOfString = { KEY_ID, KEY_LOCATIE, KEY_DATUM, KEY_TIJD, KEY_AFSTAND };
		return sqLiteDatabase.query(TABLE, arrayOfString, null, null, null, null, null);
	}
	public class SQLiteHelper extends SQLiteOpenHelper {
	  	public SQLiteHelper(Context mContext, String name, SQLiteDatabase.CursorFactory factory) {
	   		super(context, name, factory, VERSION);
	   	}

	  	public void onCreate(SQLiteDatabase SQLDatabase) {
	  		SQLDatabase.execSQL(AgendaSQLAdapter.SCRIPT_CREATE_DATABASE);
	   	}

	   	public void onUpgrade(SQLiteDatabase SQLDatabase, int oldversion, int newversion){}
	}

}
