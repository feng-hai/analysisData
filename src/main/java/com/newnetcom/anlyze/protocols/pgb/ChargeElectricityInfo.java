package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class ChargeElectricityInfo {
	private byte[] content;

	public ChargeElectricityInfo(byte[] inContent) {

		this.content = inContent;
		if (this.content.length > 0) {
			this.anlye();
		}
	}

	private void anlye() {
		this.chargeServerNo = this.content[0] & 0xff;
		this.chargeTemNo = ByteUtils.getShortForLarge(this.content, 1);

		for (int i = 0; i < this.chargeTemNo; i++) {
			this.singleBatteryElectricity.add((this.content[ i + 3]&0xff) - 40);
		}
	}

	public List<PairResult> getResults(int i) {
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(
				new PairResult("chargeServerNo", "chargeServerNo"+i, "可充电储能子系统号", String.valueOf(this.chargeServerNo)));
		tempPairs.add(new PairResult("chargeTemNo", "chargeTemNo"+i, "可充电储能装置温度探针个数", String.valueOf(this.chargeTemNo)));
		tempPairs.add(new PairResult("singleBatteryElectricity", "singleBatteryElectricity"+i, "可充电储能子系统个温度探针检查到温度值",this.toString()));
		return tempPairs;
	}

	private int chargeServerNo;// 可充电储能子系统号
	private int chargeTemNo=0;// 可充电储能装置温度探针个数

	private List<Integer> singleBatteryElectricity = new ArrayList<>();// 单体电池电压
	
	public int getLength(){
		return 3+this.chargeTemNo;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (double voltage : singleBatteryElectricity) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append(',');
			}
			stringBuilder.append(String.valueOf(voltage));
		}
		return stringBuilder.toString();
	}

}
