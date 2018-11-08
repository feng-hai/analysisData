package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class Protocol0206ForGB  implements IProtocol02ForGB{

	@Override
	public List<PairResult> getResults() {
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(new PairResult("hightVoltageCNo","hightVoltageCNo","最高电压电池子系统号",String.valueOf(this.hightVoltageCNo)));
		tempPairs.add(new PairResult("hightVoltageSNo","hightVoltageSNo","最高电压电池单体代号",String.valueOf(this.hightVoltageSNo)));
		tempPairs.add(new PairResult("hightVoltageValue","hightVoltageValue","电池单体电压最高值",String.valueOf(this.hightVoltageValue)));
		tempPairs.add(new PairResult("lowVoltageCNo","lowVoltageCNo","最低电压电池子系统号",String.valueOf(this.lowVoltageCNo)));
		tempPairs.add(new PairResult("lowVoltageSNo","lowVoltageSNo","最低电压电池单体代号",String.valueOf(this.lowVoltageSNo)));
		tempPairs.add(new PairResult("lowVoltageValue","lowVoltageValue","电池单体电压最低值",String.valueOf(this.lowVoltageValue)));
		tempPairs.add(new PairResult("hightTemCNo","hightTemCNo","最高温度系统号",String.valueOf(this.hightTemCNo)));
		tempPairs.add(new PairResult("hightTemNo","hightTemNo","最高温度探针单体代号",String.valueOf(this.hightTemNo)));
		tempPairs.add(new PairResult("hightTemValue","hightTemValue","最高温度值",String.valueOf(this.hightTemValue)));
		tempPairs.add(new PairResult("lowTemCNo","lowTemCNo","最低温度系统号",String.valueOf(this.lowTemCNo)));
		tempPairs.add(new PairResult("lowTemNo","lowTemNo","最低温度探针单体代号",String.valueOf(this.lowTemNo)));
		tempPairs.add(new PairResult("lowTemValue","lowTemValue","最低温度值",String.valueOf(this.lowTemValue)));
		
		return tempPairs;
	}
	private byte[] content;
	public Protocol0206ForGB(byte[]inContent){
		this.content=inContent;
		if(this.content.length>0)
		{
			this.anlye();
		}
	}
	private void anlye(){
		this.hightVoltageCNo=this.content[0]&0xff;
		this.hightVoltageSNo=this.content[1]&0xff;
		this.hightVoltageValue=ByteUtils.getShortForLarge(this.content, 2)/1000.0;
		this.lowVoltageCNo=this.content[4]&0xff;
		this.lowVoltageSNo=this.content[5]&0xff;
		this.lowVoltageValue=ByteUtils.getShortForLarge(this.content, 6)/1000.0;
		this.hightTemCNo=this.content[8]&0xff;
		this.hightTemNo=this.content[9]&0xff;
		this.hightTemValue=(this.content[10]&0xff)-40;
		this.lowTemCNo=this.content[11]&0xff;
		this.lowTemNo=this.content[12]&0xff;
		this.lowTemValue=(this.content[13]&0xff)-40;	
	}
	@Override
	public int getDataLength() {
		// TODO Auto-generated method stub
		return 14;
	}
	private int hightVoltageCNo;//最高电压电池子系统号
	private int hightVoltageSNo;//最高电压电池单体代号
	private double hightVoltageValue;//电池单体电压最高值
	private int lowVoltageCNo;//最低电压电池子系统号
	private int lowVoltageSNo;//最低电压电池单体代号
	private double lowVoltageValue;//电池单体电压最低值
	private int hightTemCNo;//最高温度系统号
	private int hightTemNo;//最高温度探针单体代号
	private int hightTemValue;//最高温度值
	private int lowTemCNo;//最低温度系统号
	private int lowTemNo;//最低温度探针单体代号
	private int lowTemValue;//最低温度值

}
