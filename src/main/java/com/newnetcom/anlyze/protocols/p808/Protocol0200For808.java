package com.newnetcom.anlyze.protocols.p808;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newnetcom.anlyze.protocols.p808.alarm.Protocol0200AlarmStatus;
import com.newnetcom.anlyze.protocols.p808.extra.Protocol0200Extra;
import com.newnetcom.anlyze.protocols.p808.status.Protocol0200Status;
import com.newnetcom.anlyze.utils.BCDUtils;
import com.newnetcom.anlyze.utils.ByteUtils;

/**   
*    
* 项目名称：com.newnetcom.anlyze   
* 类名称：Protocol0200For808   
* 类描述：   位置信息汇报
* 创建人：FH   
* 创建时间：2017年11月5日 下午9:50:52   
* @version        
*/
public class Protocol0200For808 {

	private Protocol0200AlarmStatus alarm ;
	private Protocol0200Status status;
	public Protocol0200Extra getExtra() {
		return extra;
	}
	public void setExtra(Protocol0200Extra extra) {
		this.extra = extra;
	}
	private Protocol0200Extra extra;
	private byte[] content;
	
	public Protocol0200For808(byte[] incontents)
	{
		this.content=incontents;
		//System.out.println(ByteUtils.bytesToHexString(this.content));
		alarm =new Protocol0200AlarmStatus(ByteUtils.getSubBytes(this.content, 0, 4));
		status=new Protocol0200Status(ByteUtils.getSubBytes(this.content, 4, 4));
		this.lat=ByteUtils.getIntForLarge(this.content, 8)*1.0/1000000;
		this.lng=ByteUtils.getIntForLarge(this.content, 12)*1.0/1000000;
		this.height=ByteUtils.getShortForLarge(this.content, 16);
		this.speed=ByteUtils.getShortForLarge(this.content, 18)*0.1;
		this.direction=ByteUtils.getShortForLarge(this.content, 21);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddhhmmss");  
		try {
			this.dateTime=
				(Date) sdf1.parse(BCDUtils.bcd2Str(ByteUtils.getSubBytes(this.content, 22,6)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 extra=new Protocol0200Extra(ByteUtils.getSubBytes(this.content, 28, this.content.length-29));
	
	}
	/** 
	* @Fields lat : 维度
	*/ 
	private double lat;
	/** 
	* @Fields lng : 经度 
	*/ 
	private double lng;
	/** 
	* @Fields height : 高度
	*/ 
	private int height;
	/** 
	* @Fields speed : 速度
	*/ 
	private double speed;
	/** 
	* @Fields direction : 方向 
	*/ 
	private int direction;
	/** 
	* @Fields dateTime : 时间 
	*/ 
	private Date dateTime;
	public Protocol0200AlarmStatus getAlarm() {
		return alarm;
	}
	public void setAlarm(Protocol0200AlarmStatus alarm) {
		this.alarm = alarm;
	}
	public Protocol0200Status getStatus() {
		return status;
	}
	public void setStatus(Protocol0200Status status) {
		this.status = status;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

}
