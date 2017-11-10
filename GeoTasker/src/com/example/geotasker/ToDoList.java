package com.example.geotasker;

import java.util.ArrayList;

import com.geotasker.adapter.TaskAdapter;
import com.geotasker.database.DataManager;
import com.geotasker.database.Task;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class ToDoList extends ListActivity {
	
	private ActionBarActivity abarAct;
	
	// Needed stuff for getting Tasks list and creating ListView
    DataManager dm;
    ArrayList<Task> tasks;
    Task task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_list); //activity_to_do_list
		abarAct = new ActionBarActivity();

//		if (savedInstanceState == null) {
//			abarAct.getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
		
		tasks = new ArrayList<Task>();
		task = new Task();
		dm = new DataManager(this);
		
//		TextView tv = (TextView) findViewById(R.id.textView1);
//		tv.setText(tasks.get(0).getTitle());
		
		new GetGeoTasks().execute();
		
		ImageView iv_delete = (ImageView) findViewById(R.id.task_delete_image);
		/*iv_delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tag_del = (String) v.getTag();
				dm.deleteItem(tag_del);
				finish();
				startActivity(getIntent());
			}
		});*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_to_do_list,
					container, false);
			return rootView;
		}
	}
	
	
	// If any errors occur, then make a method to first check the db for tasks, then execute AsyncTask
    
	// AsyncTask to get GeoTasks
	  private class GetGeoTasks extends AsyncTask<Void, Void, Void> {
	      
	      @Override
	      protected Void doInBackground(Void... params) {
	          
	          tasks = dm.getAllTasks();
	          
	          return null;
	      }
	      
	      @Override
	      protected void onPostExecute(Void result) {
	          super.onPostExecute(result);
	          
	          TaskAdapter adapter = new TaskAdapter(ToDoList.this, R.layout.item_task, tasks);
	          setListAdapter(adapter);
	          adapter.notifyDataSetChanged();
	           
	      }
	
	      @Override
	      protected void onPreExecute() {
	          super.onPreExecute();
	      }
	
	      
	  } // END GetGeoTasks


}
