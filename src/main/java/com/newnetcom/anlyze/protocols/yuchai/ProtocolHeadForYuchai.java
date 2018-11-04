package com.newnetcom.anlyze.protocols.yuchai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.utils.ByteUtils;

public class ProtocolHeadForYuchai {
	private static final Logger logger = LoggerFactory.getLogger(ProtocolHeadForYuchai.class);
	private byte[] contents;

	public ProtocolHeadForYuchai(byte[] inContents) {
		this.contents = inContents;
		if(this.contents.length<9)
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
		startTag = "7b";
		terminalCommandId = this.contents[1];// ByteUtils.getShort(this.msg, 1);//
		terminalCommandLength = ByteUtils.getShortForLarge(this.contents, 5);
	}

	private String startTag;// һ���ֽ�

	private int terminalCommandId;
	private int terminalCommandLength;

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


}
