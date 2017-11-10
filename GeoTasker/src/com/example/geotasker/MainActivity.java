package com.example.geotasker;
//import com.example.gmapsapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.geotasker.database.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.FragmentActivity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener{
	
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	GoogleMap mMap;
	
	private static final double CLT_LAT = 35.307517, CLT_LNG = -80.735727; //Coordinates for UNCC - maps starts at UNCC
	private static final float DEFAULTZOOM = 15;
	
	static LocationClient mLocationClient;
	Marker marker; //created to be able to remove markers
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		if(servicesOK()){
			
			Toast.makeText(this, "Ready to Map", Toast.LENGTH_SHORT).show();
			setContentView(R.layout.activity_map);
			
			
			
			if (initMap()) {
				Toast.makeText(this, "Map Ready", Toast.LENGTH_SHORT).show();
				
				//test
				//MarkerOptions markerPOI = new MarkerOptions();
				//markerPOI.position(new LatLng(CLT_LAT, CLT_LNG)).title("Hello CLT").snippet("Some Text Here");
				//mMap.addMarker(markerPOI);
				
				
				
				//gotoLocation(CLT_LAT, CLT_LNG, DEFAULTZOOM);
				//Enabling my location button
				mMap.setMyLocationEnabled(true);
				mLocationClient = new LocationClient(this, this, this);
				mLocationClient.connect();
				
				
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
				// H E R E 2014 05 04
				
				DataManager dm;
			    ArrayList<Task> tasks;
			    Task task;
			    
			    tasks = new ArrayList<Task>();
				task = new Task();
				dm = new DataManager(this);
				
				tasks = dm.getAllTasks();
			    
				
				//ArrayList<Task> tasks = new ArrayList<>();
				
				for (Task mTask : tasks){
					//
					MarkerOptions markerPOI1 = new MarkerOptions();
					markerPOI1.position(new LatLng(mTask.getLat(), mTask.getLon())).title(mTask.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mappad)).snippet(mTask.getItem());
					mMap.addMarker(markerPOI1);
					
					Log.d("db pull", "lat" + mTask.getLat());
//					Toast.makeText(getBaseContext(), "lat" + mTask.getTitle(), Toast.LENGTH_LONG).show();
				}
				
				
				
				
				
				//Adding the map on click listener
				mMap.setOnMapLongClickListener(new OnMapLongClickListener() {
					
					@Override
					public void onMapLongClick(LatLng ll) {
						//here can add code to save the location after the a long click
						//for testing purpose will add a marker.
						String markerText = null;
						
						MarkerOptions options = new MarkerOptions().title("New Task")
								.position(new LatLng(ll.latitude, ll.longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mappad))
								.snippet(ll.latitude + "\n" + ll.longitude); //more can be added here
						
						mMap.addMarker(options);
						//after adding a marker we add the location in the db
						addClickPostion(ll.latitude, ll.longitude);
							
					}
				});
				
				//Use this to get info about the marker at specific location
				//in this case we should go the activity that is being clicked on.
				mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
					
					@Override
					public boolean onMarkerClick(Marker marker) {
						String msgString = marker.getTitle();
						//double distTo = marker.
						Toast.makeText(MainActivity.this, msgString, Toast.LENGTH_SHORT).show();
						//Added to go to the view
						//works, find a way to link to a specific activity
						goToDo(getCurrentFocus());
						return false;
					}
				});
				
				
			}else{
				Toast.makeText(this, "Cannot use maps at this time!", Toast.LENGTH_SHORT).show();
			}

		}else{
			setContentView(R.layout.activity_main);
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Checking that the Android device is compatible
	//Does not alter the map and display
	public boolean servicesOK(){
		int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (isAvailable == ConnectionResult.SUCCESS){
			return true;
		}
		else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)){
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		}else{
			Toast.makeText(this, "No Google Play Services Available.", Toast.LENGTH_SHORT).show();
		}
		return false;
	}
	
	private boolean initMap(){
		if (mMap == null) {
			SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			mMap = mapFrag.getMap();
		}
		return (mMap != null);
	}
		
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//
		switch (item.getItemId()) {

		case R.id.mapTypeNormal:
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.mapTypeSatellite:
			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.gotoCurrentLocation:
			gotoCurrentLocation();
			break;
		case R.id.seeTraffic:
			
			if(mMap.isTrafficEnabled()){
				mMap.setTrafficEnabled(false);
			}else{
				mMap.setTrafficEnabled(true);
			}
			break;
		case R.id.exit:
			exitApp();
			break;

		default:
			break;
		}
		
		
		return super.onOptionsItemSelected(item);
	}
	
	protected void gotoCurrentLocation() {
		//to do
		Location currentLocation = mLocationClient.getLastLocation();
		//if location found
		if (currentLocation == null) {
			Toast.makeText(this, "location not available", Toast.LENGTH_SHORT).show();
		}else {
			LatLng ll = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, DEFAULTZOOM);
			mMap.animateCamera(update);
		}
	}
	
	public static float getCurrentLat() {
		Location currentLocation = mLocationClient.getLastLocation();
		float cLat = (float) currentLocation.getLatitude();
		return cLat;
	}
	
	public static float getCurrentLon() {
		Location currentLocation = mLocationClient.getLastLocation();
		float cLon = (float) currentLocation.getLongitude();
		return cLon;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		LocationRequest request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //this method may use a lot of battery
		request.setInterval(5000); //5 seconds google recomand 60000 (1 min)
		request.setFastestInterval(1000);
		mLocationClient.requestLocationUpdates(request, this);
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "Connected to location Services", Toast.LENGTH_SHORT).show();
		LocationRequest request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //this method may use a lot of battery
		request.setInterval(15000); //5 seconds google recomand 60000 (1 min)
		request.setFastestInterval(1000);
		mLocationClient.requestLocationUpdates(request, this);
		
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Currently disconnected", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLocationChanged(Location location) {
		
		//Test Block 
		LocationRequest request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY); //
		request.setInterval(60000); //google recommends 60000 (1 min)
		request.setFastestInterval(1000);
		mLocationClient.requestLocationUpdates(request, this);

		
		//passed in another method
		LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
		
		//added to get the distance
		double passLat = location.getLatitude();
		double passLon = location.getLongitude();
		
		getDistance(passLat, passLon);
		
		
		CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
		
		mMap.animateCamera(update);
		
		
	}
	
	//Method to go to the ToDo List
	public void goToDo(View v){
		Intent intent = new Intent(this, ToDoList.class);
		startActivity(intent);
	}
	
	//Method to go to the Add Position
	//the position must be passed.
	@SuppressWarnings("null")
	public void goToAddPos(View v){
			Intent intent = new Intent(this, AddPosition.class);
			Location cLocation = mLocationClient.getLastLocation();
			
			if(cLocation == null){
				Toast.makeText(this, "Position not avail at this time\nPlease try again", Toast.LENGTH_SHORT).show();
			}else{
				double lat = cLocation.getLatitude();
				double lon = cLocation.getLongitude();
				float acc = cLocation.getAccuracy();
				
				//Test display
				Toast.makeText(this, lat + " " + lon +"\nAccuracy: " + acc +" meters", Toast.LENGTH_LONG).show();
				
				intent.putExtra("lat", lat);
				intent.putExtra("lon", lon);
				
				startActivity(intent);
			}
			

		}
	
	//Added as a test method 
	public void addClickPostion(double lat, double lon){
		double clat, clon;
		
		clat = lat;
		clon = lon;
		//***************************************************
		//Add a marker here for the current position
		
		
		Intent intent = new Intent(this, AddPosition.class);
		intent.putExtra("lat", clat);
		intent.putExtra("lon", clon);
		startActivity(intent);
	}
	
	//Add a method to add markers/task on map load
	public void addTasksToMap(){
		//open an array list an load on map as markers
		//mMap.addMarker(null);
	}
	
	//Exiting the application
	public void exitApp(){
		//this is implemented for testing purposes. 
		//finish(); //soft exit
		
		int pid = android.os.Process.myPid();
		android.os.Process.killProcess(pid);
		
	}
	
	public float getDistance(double pLat, double pLon){
		
		//This method is supposed to be called on location changed
		//each time we get a new position we calculate the nearest item
		double currentLat;
		double currentLon;
		
		currentLat = pLat;
		currentLon = pLon;
		
		float nearestLoc = 0;
		DataManager dm;
		ArrayList<Task> tasks;
	    Task task;
	    tasks = new ArrayList<Task>();
		task = new Task();
		dm = new DataManager(this);
		tasks = dm.getAllTasks();
		
		//Hashmap to store String (item as keys)
		//Distance as values
		Map<String, Float> distance = new HashMap<String, Float>();
		
		for (Task mTask : tasks){
			
			String title = mTask.getTitle();
			String item = mTask.getItem();
			double savedLat = mTask.getLat();
			double savedLon = mTask.getLon();
			
			double lat1 = currentLat;
			double lng1 = currentLon;
			
		    double earthRadius = 3958.75;
		    double dLat = Math.toRadians(savedLat-lat1);
		    double dLng = Math.toRadians(savedLon-lng1);
		    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(savedLat)) *
		    Math.sin(dLng/2) * Math.sin(dLng/2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		    double dist = earthRadius * c;

		    int meterConversion = 1609;
		    
		    float totDist = new Float(dist * meterConversion / 1000).floatValue();
			
		    distance.put(title + "\n"+ item, totDist);
			
		}
		
		//Finding the nearest task
		String str = null;
		
		Float min =Float.valueOf(Float.POSITIVE_INFINITY );
		for(Map.Entry<String,Float> e:distance.entrySet()){
		    if(min.compareTo(e.getValue())>0){
		        str=e.getKey();
		        min=e.getValue();
		    }
		}
		
		//Toast.makeText(this, str + " " + min, Toast.LENGTH_LONG).show();
		
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText("Your nearset task: " + str + "\n" + min + " Km away");
		
		return nearestLoc;
		
	}
		
}





