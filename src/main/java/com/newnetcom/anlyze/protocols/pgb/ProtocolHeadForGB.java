package com.newnetcom.anlyze.protocols.pgb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.protocols.p3g.ProtocolHeadFor3G;
import com.newnetcom.anlyze.utils.ByteUtils;

public class ProtocolHeadForGB {

	private static final Logger logger = LoggerFactory.getLogger(ProtocolHeadFor3G.class);
	private byte[] contents;

	public ProtocolHeadForGB(byte[] inContents) {
		this.contents = inContents;
		if(this.contents.length<24)
		{
			logger.error("无效字节，头部文件小于24个字节");
		}
		try {
			this.anlyze();
		} catch (Exception ex) {
			logger.error("解析头部文件出错", ex);
		}
	}

	private void anlyze() {
		startTag = "2323";
		commandTag=this.contents[2];
		answerTag=this.contents[3];
		vin=ByteUtils.bytesToAsciiString(this.contents, 4, 17);
		encrypt=this.contents[21];
		dataLength=ByteUtils.getShort(this.contents, 22);
//		terminalCommandId = ByteUtils.getShort(this.contents, 1);
//		terminalCommandLength = ByteUtils.getShort(this.contents, 3);
//		canId = ByteUtils.getShort(this.contents, 5);
//		canLength = ByteUtils.getShort(this.contents, 7);
//		serial = ByteUtils.getShort(this.contents, 9);
//		terminalId = ByteUtils.bytesToAsciiString(this.contents, 11, 6);
//		subCode = this.contents[17];
//		supplierCode = this.contents[18];
//		secretKey = this.contents[19];
	}
	/** 
	* @Fields startTag : 开始标识
	*/ 
	private String startTag;
	
	private byte commandTag;
	private byte answerTag;
	private String vin;
	private byte  encrypt;
	private int dataLength;
	public String getStartTag() {
		return startTag;
	}

	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}

	public byte getCommandTag() {
		return commandTag;
	}

	public void setCommandTag(byte commandTag) {
		this.commandTag = commandTag;
	}

	public byte getAnswerTag() {
		return answerTag;
	}

	public void setAnswerTag(byte answerTag) {
		this.answerTag = answerTag;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public byte getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(byte encrypt) {
		this.encrypt = encrypt;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}



	
}
