package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class Protocol0207ForGB implements IProtocol02ForGB{

	@Override
	public List<PairResult> getResults() {
		// TODO Auto-generated method stub
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(new PairResult("lastHightAlarm","lastHightAlarm","最高报警等级",String.valueOf(this.lastHightAlarm)));
		tempPairs.addAll(this.getResults());
		return tempPairs;
	}
	private byte[] content;
	public Protocol0207ForGB(byte[]inContent){
		this.content=inContent;
		if(this.content.length>0)
		{
			this.anlye();
		}
	}
	private void anlye(){
		this.lastHightAlarm=this.content[0]&0xff;
		alarm=new AlarmInfo(ByteUtils.getSubBytes(this.content, 1, 4));
		this.chargeAlarmNum=this.content[5]&0xff;
		this.motorAlarmNum=this.content[6]&0xff;
		this.engineAlarmNum=this.content[7]&0xff;
		this.otherAlarm=this.content[8]&0xff;	
	}
	@Override
	public int getDataLength() {
		// TODO Auto-generated method stub
		return 9;
	}
	private int lastHightAlarm;//最高报警等级1
	private AlarmInfo alarm;//通用报警 4
	private int chargeAlarmNum;
	private int motorAlarmNum;
	private int engineAlarmNum;
	private int otherAlarm;
	

}
