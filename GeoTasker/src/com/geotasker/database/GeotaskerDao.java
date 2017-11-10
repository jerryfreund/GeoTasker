package com.geotasker.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class GeotaskerDao {
	
	
	private SQLiteDatabase db;

	public GeotaskerDao(SQLiteDatabase db) {
		this.db = db;
	}

	public long save(Task item) {

		ContentValues values = new ContentValues();
//		values.put(GeotaskerTable.TASKS_ID, item.getId());
		values.put(GeotaskerTable.Tasks_LAT, item.getLat());
		values.put(GeotaskerTable.Tasks_LON, item.getLon());
		values.put(GeotaskerTable.Tasks_TITLE, item.getTitle());
		values.put(GeotaskerTable.Tasks_ITEM, item.getItem());
		return db.insert(GeotaskerTable.TABLE_NAME, null, values);
		
	}
	
	public boolean deleteItem(String item) {
		
		Log.d("demo", item);
		return db.delete(GeotaskerTable.TABLE_NAME, GeotaskerTable.TASKS_ID + "=" + item, null) > 0;
		
	}

	public boolean delete() {
		
		return db.delete(GeotaskerTable.TABLE_NAME, null, null) > 0;
				//NewsTable.TABLE_NAME, NewsTable.NEWS_URL+"="+item.getUrl_link(), null)>0;
		
	}

	public ArrayList<Task> getAll() {
		
		ArrayList<Task> list = new ArrayList<Task>();
		Cursor c = db.query(GeotaskerTable.TABLE_NAME, 
				new String[]{GeotaskerTable.TASKS_ID, GeotaskerTable.Tasks_LAT, GeotaskerTable.Tasks_LON, 
							GeotaskerTable.Tasks_TITLE, GeotaskerTable.Tasks_ITEM}, 
				null, null, null, null, null); // Added extra 'null' when deleted third entry
		if(c != null && c.moveToFirst()){				
			do{
				Task task = this.buildNoteFromCursor(c);
				if(task != null){
					list.add(task);
				}				
			} while(c.moveToNext());
			
			if(!c.isClosed()){
				c.close();
			}
		}
		return list;
		
	}

	private Task buildNoteFromCursor(Cursor c) {
		
		Task item = null;		
		if(c != null){
			item = new Task();
			item.setId(c.getString(0));
			item.setLat(c.getDouble(1));
			item.setLon(c.getDouble(2));
			item.setTitle(c.getString(3));
			item.setItem(c.getString(4));
		}
		
		return item;
		
	}
	
	
}
