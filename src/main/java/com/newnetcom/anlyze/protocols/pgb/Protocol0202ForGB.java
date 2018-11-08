package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class Protocol0202ForGB implements IProtocol02ForGB {
	
	private byte[] content;
	public Protocol0202ForGB(byte[] inContent){
		this.content=inContent;
		if(this.content.length>0)
		{
			this.anlyze();
		}
	}
	private List<PairResult> tempResult=new ArrayList<>();
    private void anlyze(){
       this.motorNumbe=this.content[0];
       for(int i=0;i<this.motorNumbe;i++){
    	   byte[] tempContent=ByteUtils.getSubBytes(this.content, i+1, 12);
    	   DrivingMotor  dm=new DrivingMotor(tempContent);
    	   tempResult.addAll(dm.getResults(i));
       }
    }
	@Override
	public List<PairResult> getResults() {
		// TODO Auto-generated method stub
		return tempResult;
	}
	private int motorNumbe;
	@Override
	public int getDataLength() {
		// TODO Auto-generated method stub
		return this.motorNumbe*12+1;
	}
	

}
