package com.geotasker.database;

import java.util.ArrayList;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataManager {
	Context mContext;
	DatabaseHelper dbOpenHelper;
	SQLiteDatabase db;
	GeotaskerDao geotaskerDao;

	public DataManager(Context mContext) {
		this.mContext = mContext;
		dbOpenHelper = new DatabaseHelper(mContext);
		db = dbOpenHelper.getWritableDatabase();
		geotaskerDao = new GeotaskerDao(db);
	}

	public void close() {
		db.close();
	}

	public long saveTask(Task task) {
		return geotaskerDao.save(task);
	}
	
	public boolean deleteItem(String item) {
		return geotaskerDao.deleteItem(item);
	}
	
	// This actually deletes the whole table
	public boolean deleteTask() {
		return geotaskerDao.delete();
	}

	public ArrayList<Task> getAllTasks() {
		return geotaskerDao.getAll();
	}
}