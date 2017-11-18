package com.newnetcom.anlyze.protocols.p808;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.utils.BCDUtils;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.StrFormat;

public class ProtocolHeadFor808 {
	private static Logger logger=LoggerFactory.getLogger(ProtocolHeadFor808.class);
	private byte[] contents;
	public ProtocolHeadFor808(byte[] incontents)
	{
		this.contents=incontents;
		try{
			this.anlyze();
		}
		catch(Exception ex)
		{
			logger.error("808头部解析错误",ex);
		}
	}

	private byte startTag = (byte) 0x7E;
	private short commondId;
	/**
	 * @Fields hold : 2位保留
	 */
	private byte hold;
	/**
	 * @Fields subpackage : 分包 第13位
	 */
	private byte subpackage;

	/**
	 * 
	 * 12-10
	 * @Fields secretKey : 加密方式—— bit10-bit12为数据加密标识位； ——当此三位都为0，表示消息体不加密；
	 *         ——当第10位为1，表示消息体经过RSA算法加密； ——其他保留。
	 *
	 */
	private byte secretKey;

	/**
	 * @Fields dataLength :数据部分长度
	 */
	private int dataLength;

	/**
	 * @Fields phone : 根据安装后终端自身的手机号转换。手机号不足12位，则在前补充数字，大陆手机
	 *         号补充数字0港澳台则根据其区号进行位数补充。
	 * BCD[6]  
	 * 6个字节
	 */
	private String phone;
	/** 
	* @Fields serial : 消息流水号 
	*/ 
	private short serial;
	
	/** 
	* @Fields sumPackage :消息总包数 
	*/ 
	private short sumPackage;
	/** 
	* @Fields packageNo : 包序号
	*/ 
	private short packageNo;
	
	/** 
	* @Fields headLength : 包头长度
	*/ 
	private short headLength;
	
	public short getHeadLength() {
		return headLength;
	}

	public void setHeadLength(short headLength) {
		this.headLength = headLength;
	}

	private void anlyze() {
		startTag=this.contents[0];
		commondId=ByteUtils.getShortForLarge(this.contents, 1);
		String binary=ByteUtils.binary((ByteUtils.getSubBytes(this.contents, 3, 2)), 2);
		binary=StrFormat.addZeroForLeftNum(binary, 16);
		//hold=ByteUtils.bit2byte(binary.substring(0, 1));
		subpackage=ByteUtils.bit2byte(binary.substring(2, 2));
		secretKey=ByteUtils.bit2byte(binary.substring(3, 5));
		dataLength= ByteUtils.getShortForLarge(this.contents, 3)&1023;// Integer.parseInt(StrFormat.addZeroForLeftNum(binary.substring(6, 15), 16),2);
		phone=BCDUtils.bcd2Str(ByteUtils.getSubBytes(this.contents,5, 6));
		serial=ByteUtils.getShortForLarge(this.contents, 11);
		
		if(subpackage==1)
		{
			sumPackage=ByteUtils.getShortForLarge(this.contents, 13);
			packageNo=ByteUtils.getShortForLarge(this.contents, 15);
			headLength=17;
		}else
		{
			headLength=13;
		}
	}
	
	public byte getStartTag() {
		return startTag;
	}
	public void setStartTag(byte startTag) {
		this.startTag = startTag;
	}
	public short getCommondId() {
		return commondId;
	}
	public void setCommondId(short commondId) {
		this.commondId = commondId;
	}
	public byte getHold() {
		return hold;
	}
	public void setHold(byte hold) {
		this.hold = hold;
	}
	public byte getSubpackage() {
		return subpackage;
	}
	public void setSubpackage(byte subpackage) {
		this.subpackage = subpackage;
	}
	public byte getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(byte secretKey) {
		this.secretKey = secretKey;
	}
	public int getDataLength() {
		return dataLength;
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public short getSerial() {
		return serial;
	}
	public void setSerial(short serial) {
		this.serial = serial;
	}
	public short getSumPackage() {
		return sumPackage;
	}
	public void setSumPackage(short sumPackage) {
		this.sumPackage = sumPackage;
	}
	public short getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(short packageNo) {
		this.packageNo = packageNo;
	}
	

}
