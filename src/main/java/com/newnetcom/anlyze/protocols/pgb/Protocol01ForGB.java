package com.newnetcom.anlyze.protocols.pgb;

import java.util.Date;

import com.newnetcom.anlyze.utils.ByteUtils;

public class Protocol01ForGB {
	private byte[] contents;
	public Protocol01ForGB(byte[] incontents)
	{
		this.contents=incontents;
		this.anlyze();
	}
	private Date  dataTime;
	private int serial;
	private String ICCID;
	private Byte subSystemNum;
	private byte systemCodeLength;
	private String chargeSystemNum;
	
	private void anlyze() {
		dataTime=ByteUtils.BytesTodateForSecond(this.contents, 0);
		serial=ByteUtils.getShort(this.contents, 6);
		ICCID=ByteUtils.bytes2Str(this.contents, 8, 20);
		subSystemNum=this.contents[28];
		systemCodeLength=this.contents[29];
		chargeSystemNum=ByteUtils.bytes2Str(this.contents, 30, subSystemNum*systemCodeLength);	
	}
	public Date getDataTime() {
		return dataTime;
	}
	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public Byte getSubSystemNum() {
		return subSystemNum;
	}
	public void setSubSystemNum(Byte subSystemNum) {
		this.subSystemNum = subSystemNum;
	}
	public byte getSystemCodeLength() {
		return systemCodeLength;
	}
	public void setSystemCodeLength(byte systemCodeLength) {
		this.systemCodeLength = systemCodeLength;
	}
	public String getChargeSystemNum() {
		return chargeSystemNum;
	}
	public void setChargeSystemNum(String chargeSystemNum) {
		this.chargeSystemNum = chargeSystemNum;
	}

//	public void toString()
//	{
//		
//	}

}
