package com.newnetcom.anlyze.beans;

public class VehicleIndex {
	
	public VehicleIndex(String invhicleUnid,String time)
	{
		this.vehicleUnid=invhicleUnid;
		this.time=time;
	}
	
	private String vehicleUnid;
	private String time;
	public String getVehicleUnid() {
		return vehicleUnid;
	}
	public void setVehicleUnid(String vehicleUnid) {
		this.vehicleUnid = vehicleUnid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
