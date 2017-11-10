package com.geotasker.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class GeotaskerTable {
	
	static final String TABLE_NAME = "tasks";
	static final String TASKS_ID = "_id";
	static final String Tasks_LAT = "lat";
	static final String Tasks_LON = "lon";
	static final String Tasks_TITLE = "title";
	static final String Tasks_ITEM = "item";
	
	static public void onCreate(SQLiteDatabase db) {
		
		StringBuilder sb = new StringBuilder();		
		sb.append("CREATE TABLE " + GeotaskerTable.TABLE_NAME + " (");
		sb.append(GeotaskerTable.TASKS_ID + " integer primary key autoincrement, ");
		sb.append(GeotaskerTable.Tasks_LAT + " int not null, ");
		sb.append(GeotaskerTable.Tasks_LON + " int not null, ");
		sb.append(GeotaskerTable.Tasks_TITLE + " text not null, ");
		sb.append(GeotaskerTable.Tasks_ITEM + " text not null ");
		sb.append(");");		
		try{
			db.execSQL(sb.toString());
		} catch (SQLException e){				
			e.printStackTrace();
		}
		
	}

	static public void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		
		/*db.execSQL("DROP TABLE IF EXISTS " + TwitterTable.TABLE_NAME);
		TwitterTable.onCreate(db);*/
		
	}
}