package com.mypomodoro.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mypomodoro.data.Task;

public class PomodoroDatabaseHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "tasks";
	public static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "( "
			+ Task._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Task.NAME + " TEXT NOT NULL, "
			+ Task.TYPE + " VARCHAR(10) NOT NULL, "
			+ Task.DEADLINE + " INTEGER, "
			+ "date_created INTEGER NOT NULL, "
			+ Task.ESTIMATED_POMODOROS + " INTEGER " + ");";
	public static final String DATABASE_NAME = "mypomodoro";

	public PomodoroDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}
}
