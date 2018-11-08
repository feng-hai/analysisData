package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.PointDouble;
import com.newnetcom.anlyze.utils.Wars2Wgs;

public class Protocol0205ForGB implements IProtocol02ForGB {

	@Override
	public List<PairResult> getResults() {
		// TODO Auto-generated method stub
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(new PairResult("localStatus", "localStatus", "定位状态", String.valueOf(this.localStatus)));
		tempPairs.add(new PairResult("latDi", "latDi", "维度南北", String.valueOf(this.latDi)));
		tempPairs.add(
				new PairResult("lngDi", "lngDi", "经度东西", String.valueOf(this.lngDi)));
		tempPairs.add(
				new PairResult("lat", "lat", "维度", String.valueOf(this.lat)));
		tempPairs.add(
				new PairResult("lng", "lng", "经度", String.valueOf(this.lng)));
		tempPairs.add(new PairResult("LON_D", "LON_D", "经度", String.valueOf(this.lng_D)));
		tempPairs.add(new PairResult("LAT_D", "LAT_D", "维度", String.valueOf(this.lat_D)));
		return tempPairs;
	}
	private int localStatus;
	private int latDi;
	private int lngDi;
	private double lat;
	private double lat_D;
	private double lng;
	private double lng_D;
	
	private byte[] content;
	public Protocol0205ForGB(byte[]inContent){
		this.content=inContent;
		if(this.content.length>0)
		{
			this.anlye();
		}
	}
	private void anlye(){
		this.localStatus=(this.content[0]&0x01)==1?1:0;
		this.latDi=(this.content[0]&0x02)==1?1:0;
		this.lngDi=(this.content[0]&0x04)==1?1:0;
		this.lng =ByteUtils.getIntForLarge(this.content, 1)/1000000.0;
		this.lat=ByteUtils.getIntForLarge(this.content, 5)/1000000.0;
		
		PointDouble pd = new PointDouble(this.lng, this.lat);
		if (pd != null) {
			PointDouble en = Wars2Wgs.s2c(pd);
			this.lat_D=en.y;
			this.lng_D=en.x;
			
		}
	}
	@Override
	public int getDataLength() {
		// TODO Auto-generated method stub
		return 9;
	}

}
