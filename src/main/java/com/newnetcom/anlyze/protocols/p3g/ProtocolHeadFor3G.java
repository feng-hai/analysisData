package com.newnetcom.anlyze.protocols.p3g;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.utils.ByteUtils;

public class ProtocolHeadFor3G {
	private static final Logger logger = LoggerFactory.getLogger(ProtocolHeadFor3G.class);
	private byte[] contents;

	public ProtocolHeadFor3G(byte[] inContents) {
		this.contents = inContents;
		if(this.contents.length<20)
		{
			logger.error("无效字节，头部文件小于20个字节");
		}
		try {
			this.anlyze();
		} catch (Exception ex) {
			logger.error("解析头部文件出错", ex);
		}
	}

	private void anlyze() {
		startTag = "7E";
		terminalCommandId = ByteUtils.getShort(this.contents, 1);
		terminalCommandLength = ByteUtils.getShort(this.contents, 3);
		canId = ByteUtils.getShort(this.contents, 5);
		canLength = ByteUtils.getShort(this.contents, 7);
		serial = ByteUtils.getShort(this.contents, 9);
		terminalId = ByteUtils.bytesToAsciiString(this.contents, 11, 6);
		subCode = this.contents[17];
		supplierCode = this.contents[18];
		secretKey = this.contents[19];
	}

	private String startTag;// һ���ֽ�

	public String getStartTag() {
		return startTag;
	}

	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}

	public int getTerminalCommandId() {
		return terminalCommandId;
	}

	public void setTerminalCommandId(short terminalCommandId) {
		this.terminalCommandId = terminalCommandId;
	}

	public int getTerminalCommandLength() {
		return terminalCommandLength;
	}

	public void setTerminalCommandLength(short terminalCommandLength) {
		this.terminalCommandLength = terminalCommandLength;
	}

	public int getCanId() {
		return canId;
	}

	public void setCanId(int canId) {
		this.canId = canId;
	}

	public int getCanLength() {
		return canLength;
	}

	public void setCanLength(int canLength) {
		this.canLength = canLength;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public byte getSubCode() {
		return subCode;
	}

	public void setSubCode(byte subCode) {
		this.subCode = subCode;
	}

	public byte getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(byte supplierCode) {
		this.supplierCode = supplierCode;
	}

	public byte getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(byte secretKey) {
		this.secretKey = secretKey;
	}

	private int terminalCommandId;
	private int terminalCommandLength;
	private int canId;
	private int canLength;

	/** 
	* @Fields serial :消息流水号
	*/ 
	private int serial;
	/** 
	* @Fields terminalId :终端id 
	*/ 
	private String terminalId;// �����ֽ�
	/** 
	* @Fields subCode : 子code
	*/ 
	private byte subCode;
	
	/** 
	* @Fields supplierCode :供应商代码
	*/ 
	private byte supplierCode;

	/** 
	* @Fields secretKey : 加密
	*/ 
	private byte secretKey;// ��Կ �����Լ�

}
