package com.geotasker.adapter;

import java.util.List;

import com.example.geotasker.CurrentDistance;
import com.example.geotasker.R;
import com.geotasker.database.DataManager;
import com.geotasker.database.Task;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
	

/*
 * Used by: 
 * 
 * TaskAdapter adapter = new TaskAdapter(ToDoList.this, R.layout.item_task, tweetStuff);
 * setListAdapter(adapter);
 */

public class TaskAdapter extends ArrayAdapter<Task>{

	private Context context;
	private List<Task> objects;

	public TaskAdapter(Context context, int resource, List<Task> objects) {
		super(context, resource, objects);
		this.context = context;
		this.objects = objects;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final DataManager dm = new DataManager(getContext());
		Task task = objects.get(position);
		
		LayoutInflater inflater = 
				(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_task, null);
		
		// Get Text Views
		TextView tv_title = (TextView) view.findViewById(R.id.task_title);
		TextView tv_item = (TextView) view.findViewById(R.id.task_item);
		TextView tv_distance = (TextView) view.findViewById(R.id.task_distance);
		ImageView iv_delete = (ImageView) view.findViewById(R.id.task_delete_image);
		
		tv_title.setText(task.getTitle());
		tv_item.setText(task.getItem());
		iv_delete.setTag(task.getId());
		
		// Getting distance method
		double curLat1 = task.getLat();
		double curLng1 = task.getLon();
		CurrentDistance distance = new CurrentDistance();
		String curDist = distance.distFrom(curLat1, curLng1);
		Log.d("demo", curDist);
		
		tv_distance.setText(curDist + "m");
		
		iv_delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String tag_del = (String) v.getTag();
				dm.deleteItem(tag_del);
				objects.remove(position);
				notifyDataSetChanged();
			}
		});
		
		return view;
	}
	
	
	
}
