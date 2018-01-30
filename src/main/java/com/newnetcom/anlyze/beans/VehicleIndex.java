package com.newnetcom.anlyze.beans;

public class VehicleIndex {
	
	public VehicleIndex(String invhicleUnid,long time)
	{
		this.vehicleUnid=invhicleUnid;
		this.time=time;
	}
	
	private String vehicleUnid;
	private long time;
	public String getVehicleUnid() {
		return vehicleUnid;
	}
	public void setVehicleUnid(String vehicleUnid) {
		this.vehicleUnid = vehicleUnid;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}

}
