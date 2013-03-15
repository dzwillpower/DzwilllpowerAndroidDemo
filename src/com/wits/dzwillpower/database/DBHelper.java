package com.wits.dzwillpower.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASENAME = "diaryOpenHelper.db";
	public static final int DATABASEVERSION = 1;

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table diary " + 
				"(" + 
				"_id interger primary key autoincrement ," + 
				"topic varchar(100),"+ 
				"content varchar(1000)" + 
				")";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql ="drop table if exits diary";
		db.execSQL(sql);
		this.onCreate(db);
	}

}
