package com.newnetcom.anlyze.protocols.yuchai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.utils.ByteUtils;

public class ProtocolA5ForYuchai {
	private static final Logger logger = LoggerFactory.getLogger(ProtocolHeadForYuchai.class);
	private byte[] contents;
	public ProtocolA5ForYuchai(byte[] inContents) {
		this.contents = inContents;
		try {
			anlyze();
		} catch (Exception ex) {
			logger.error("解析GPS数据出错", ex);
		}
	}	
	
	private ProtocolLocal  local;
	/**
	 * @return the local
	 */
	public ProtocolLocal getLocal() {
		return local;
	}
	/**
	 * @param local the local to set
	 */
	public void setLocal(ProtocolLocal local) {
		this.local = local;
	}
	/**
	 * @return the vehicle
	 */
	public VehicleStatus getVehicle() {
		return vehicle;
	}
	/**
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(VehicleStatus vehicle) {
		this.vehicle = vehicle;
	}

	private VehicleStatus vehicle;
	private void anlyze(){
		
		local=new ProtocolLocal(ByteUtils.getSubBytes(this.contents, 7, 22));
		
	    vehicle=new VehicleStatus(ByteUtils.getSubBytes(this.contents, 29, 4));
	
	}
	
}
