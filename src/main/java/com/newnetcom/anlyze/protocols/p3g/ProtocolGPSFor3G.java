package com.newnetcom.anlyze.protocols.p3g;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.utils.ByteUtils;

public class ProtocolGPSFor3G {
	private static final Logger logger = LoggerFactory.getLogger(ProtocolGPSFor3G.class);
	private byte[] contents;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param inContent
	 */
	public ProtocolGPSFor3G(byte[] inContents) {
		this.contents = inContents;
		try {
			anlyze();
		} catch (Exception ex) {
			logger.error("解析GPS数据出错", ex);
		}
	}

	/**
	 * @Fields reason : 上报原因1个字节
	 */
	private byte reason;
	/**
	 * @Fields lng : 经度4个字节
	 */
	private double lng;
	/**
	 * @Fields lat : 维度 4个字节
	 */
	private double lat;
	/**
	 * @Fields speed : 速度2个字节
	 */
	private float speed;
	/**
	 * @Fields height :高度2个字节
	 */
	private int height;
	/**
	 * @Fields direction : 方向角 2个字节 度
	 */
	private int direction;
	/**
	 * @Fields distance : 里程4个字节
	 */
	private float distance;

	/**
	 * @Fields hVesion : 硬件版本2个字节
	 */
	private int hVersion;
	/**
	 * @Fields sVesion : 软件版本2个字节
	 */
	private String sVersion;
	/**
	 * @Fields date : 日期 6个字节
	 */
	private Date date;

	/**
	 * @Fields acc :ACC状态 B0
	 */
	private byte acc;
	/**
	 * @Fields locationState : 定位状态B1
	 */
	private byte locationState;
	/**
	 * @Fields starNum :卫星个数B5-B2 4位
	 */
	private byte starNum;
	/**
	 * @Fields gpsStatus : GPS模块正常， =1 模块异常 B6
	 */
	private byte gpsStatus;
	/**
	 * @Fields electricityStatus : B7 供电状态 0=外电供电；1=电池供电；
	 */
	private byte electricityStatus;

	public byte getReason() {
		return reason;
	}

	public void setReason(byte reason) {
		this.reason = reason;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(short direction) {
		this.direction = direction;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public int gethVersion() {
		return hVersion;
	}

	public void sethVersion(int hVesion) {
		this.hVersion = hVesion;
	}

	public String getsVersion() {
		return sVersion;
	}

	public void setsVersion(String sVersion) {
		this.sVersion = sVersion;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public byte getAcc() {
		return acc;
	}

	public void setAcc(byte acc) {
		this.acc = acc;
	}

	public byte getLocationState() {
		return locationState;
	}

	public void setLocationState(byte locationState) {
		this.locationState = locationState;
	}

	public byte getStarNum() {
		return starNum;
	}

	public void setStarNum(byte starNum) {
		this.starNum = starNum;
	}

	public byte getGpsStatus() {
		return gpsStatus;
	}

	public void setGpsStatus(byte gpsStatus) {
		this.gpsStatus = gpsStatus;
	}

	public byte getElectricityStatus() {
		return electricityStatus;
	}

	public void setElectricityStatus(byte electricityStatus) {
		this.electricityStatus = electricityStatus;
	}

	private void anlyze() {
		reason = this.contents[0];
		lng = ByteUtils.getInt(this.contents, 1) * 1.0 / 1000000;
		lat = ByteUtils.getInt(this.contents, 5) * 1.0 / 1000000;
		speed = (float) (ByteUtils.getShort(this.contents, 9) * 0.5);
		height = ByteUtils.getShort(this.contents, 11);
		direction = ByteUtils.getShort(this.contents, 13);
		distance = (float) (ByteUtils.getInt(this.contents, 15) / 500.0);
		hVersion = ByteUtils.getShort(this.contents, 19);
		sVersion = this.contents[21]+"."+this.contents[22]+"."+this.contents[23]+"."+this.contents[24];
		date = ByteUtils.BytesTodateForSecond(this.contents, 25);
		Byte tByte = this.contents[31];
		String binaryStr = ByteUtils.byte2bits(tByte); // Integer.toBinaryString(tByte												// & 0xFF);
		acc = ByteUtils.bit2byte(binaryStr.substring(binaryStr.length() - 2, binaryStr.length() - 1));
		locationState = ByteUtils.bit2byte(binaryStr.substring(binaryStr.length() - 3, binaryStr.length() - 2));
		starNum = ByteUtils.bit2byte(binaryStr.substring(binaryStr.length() - 6, binaryStr.length() - 2));
		gpsStatus = ByteUtils.bit2byte(binaryStr.substring(binaryStr.length() - 7, binaryStr.length() - 6));
		electricityStatus = ByteUtils.bit2byte(binaryStr.substring(binaryStr.length() - 8, binaryStr.length() - 6));
		// acc=byte

	}
}
