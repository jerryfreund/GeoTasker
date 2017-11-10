package com.geotasker.database;


public class Task {
	
	private String id;
	private Double lon;
	private Double lat;
	private String title;
	private String item;
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public Double getLon() {
		return lon;
	}
	
	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	
	public Double getLat() {
		return lat;
	}
	
	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getItem() {
		return item;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", lon=" + lon + ", lat=" + lat + ", title="
				+ title + ", item=" + item + "]";
	}
	
	
}
