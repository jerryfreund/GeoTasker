package com.example.geotasker;

import com.geotasker.database.DataManager;
import com.geotasker.database.GeotaskerDao;
import com.geotasker.database.Task;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class AddPosition extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_position);
		
		
		final double lat;
		final double lon;
		
		lat = getIntent().getExtras().getDouble("lat");
		lon = getIntent().getExtras().getDouble("lon");
		
		Toast.makeText(this, lat + " " + lon, Toast.LENGTH_LONG).show();
		////////////////////////////////////////////////////////////////
		// Jerry
		//Setting the text views
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText("Adding GeoTask");
		
		TextView textView2 = (TextView) findViewById(R.id.textView2);
		textView2.setText("latitude " + lat);
		
		TextView textView3 = (TextView) findViewById(R.id.textView3);
		textView3.setText("longitude " + lon);

		
		// Get information and save it to database
		final Task task = new Task(); 
		final DataManager dm = new DataManager(this);
		
		
		final EditText tit = (EditText) findViewById(R.id.et_title);
		final EditText tas = (EditText) findViewById(R.id.et_task);
		
		Button save = (Button) findViewById(R.id.add_position_save);
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//added Jerry
				//Intent intent = new Intent(getBaseContext(), MainActivity.class);
				
				String title = tit.getText().toString();
				String item = tas.getText().toString();
				
				Toast.makeText(AddPosition.this, tit + "\n" + tas, Toast.LENGTH_LONG).show();
				
				// Creating Task object
				task.setLat(lat);
				task.setLon(lon);
				task.setTitle(title);
				task.setItem(item);
				
				// Saving Task object to database
				dm.saveTask(task);
				
				//Closing this activity and returning to the previous one
				finish();
				
			}
		});
		
		Button cancel = (Button) findViewById(R.id.add_position_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//added Jerry
				Toast.makeText(getBaseContext(), "Position Not Added !", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_position, menu);
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
/*	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_position,
					container, false);
			return rootView;
		}
	}*/

}
