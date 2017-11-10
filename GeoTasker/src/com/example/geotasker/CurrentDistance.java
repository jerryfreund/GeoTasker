package com.example.geotasker;

import java.text.NumberFormat;

import android.annotation.SuppressLint;

@SuppressLint("UseValueOf")
public class CurrentDistance {
	
	/**
	 * 
	 * @param lat1 Latitude of the First Location
	 * @param lng1 Logitude of the First Location
	 * @param lat2 Latitude of the Second Location
	 * @param lng2 Longitude of the Second Location
	 * @return distance between two lat-lon in float format
	 */

	public String distFrom (double lat2, double lng2 ) 
	{
		float lat1 = MainActivity.getCurrentLat();
		float lng1 = MainActivity.getCurrentLon();
		
	    double earthRadius = 3958.75;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	    Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    int meterConversion = 1609;
	    
	    float totDist = new Float(dist * meterConversion / 1000).floatValue();
	    
	    // 1 decimal place
	    NumberFormat formatter = NumberFormat.getNumberInstance();
	    formatter.setMinimumFractionDigits(1);
	    formatter.setMaximumFractionDigits(1);

	    return formatter.format(totDist);
	}
	
}
