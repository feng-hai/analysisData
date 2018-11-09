package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class Protocol0204ForGB implements IProtocol02ForGB {

	@Override
	public List<PairResult> getResults() {
		// TODO Auto-generated method stub
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(new PairResult("engineStatus", "engineStatus", "发动机状态", String.valueOf(this.engineStatus)));
		tempPairs.add(new PairResult("engineSpeed", "engineSpeed", "发动机曲轴转速", String.valueOf(this.engineSpeed)));
		tempPairs.add(
				new PairResult("enginefuelRate", "enginefuelRate", "发动机燃料消耗率", String.valueOf(this.enginefuelRate)));
		return tempPairs;
	}

	private int engineStatus;
	private int engineSpeed;
	private double enginefuelRate;
	private byte[] content;

	public Protocol0204ForGB(byte[] inContent) {
		this.content = inContent;
		if (this.content.length == 5) {
			this.anlye();
		}
	}

	private void anlye() {
		this.engineStatus = this.content[0] & 0xff;
		this.engineSpeed = ByteUtils.getShortForLarge(this.content, 1);
		this.enginefuelRate = ByteUtils.getShortForLarge(this.content, 3) / 100.0;

	}

	@Override
	public int getDataLength() {
		// TODO Auto-generated method stub
		return 5;
	}

}
