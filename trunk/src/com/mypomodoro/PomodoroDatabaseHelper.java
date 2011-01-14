package com.mypomodoro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PomodoroDatabaseHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 2;
	public static final String TABLE_NAME = "activities";
	public static final String NAME = "pomodoro_name";
	public static final String ESTIMATED = "estimated_poms";
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + NAME + " TEXT, " + ESTIMATED + " TEXT);";
	public static final String DATABASE_NAME = "mypomodoro";

	PomodoroDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
