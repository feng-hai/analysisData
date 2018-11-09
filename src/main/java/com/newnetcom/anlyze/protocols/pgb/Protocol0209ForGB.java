package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class Protocol0209ForGB implements IProtocol02ForGB {
	private byte[] content;
	public Protocol0209ForGB(byte[] inContent){
		this.content=inContent;
		if(this.content.length>0)
		{
			this.anlye();
		}
	}
	private int length=0;
	private void anlye(){
		this.chargeServerNo=this.content[0]&0xff;
		length++;
		for(int i=0;i<this.chargeServerNo;)
		{
		//tempPairs.addAll(this.getResults());
			ChargeElectricityInfo cei=new ChargeElectricityInfo(ByteUtils.getSubBytes(this.content,i+1, this.content.length-1-i));
			i=i+cei.getLength();
			length=length+cei.getLength();
			ceiList.add(cei);
		}
	}

	@Override
	public List<PairResult> getResults() {
		// TODO Auto-generated method stub
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(new PairResult("chargeServerNo","chargeServerNo","可充电储能子系统个数",String.valueOf(this.chargeServerNo)));
		
		int i=0;
		for(ChargeElectricityInfo cei:ceiList)
		{
			i++;
			tempPairs.addAll(cei.getResults(i));	
		}
		return tempPairs;
	}

	@Override
	public int getDataLength() {
		return length;
	}
	private int chargeServerNo;//可充电储能子系统个数
	private List<ChargeElectricityInfo> ceiList=new ArrayList<>();
	
	

}
