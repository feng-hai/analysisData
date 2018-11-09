package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class ChargeVoltageInfo {
	private byte[] content;

	public ChargeVoltageInfo(byte[] inContent, List<Double> inSingleBatteryVoltages) {
		if (inSingleBatteryVoltages != null) {
			this.singleBatteryVoltages = inSingleBatteryVoltages;
		}
		this.content = inContent;
		if (this.content.length > 0) {
			this.anlye();
		}
	}

	private void anlye() {
		this.chargeServerNo = this.content[0] & 0xff;
		this.chargeVoltage = ByteUtils.getShortForLarge(this.content, 1) / 10.0;
		this.chargeElectricity = ByteUtils.getShortForLarge(this.content, 3) / 10.0 - 1000;
		this.chargeSingleNum = ByteUtils.getShortForLarge(this.content, 5);
		this.chargeStartIndex = ByteUtils.getShortForLarge(this.content, 7);
		this.currentBatteryNum = this.content[9]&0xff;
		for (int i = 0; i < this.currentBatteryNum*2; i=i+2) {
			this.singleBatteryVoltages.add(ByteUtils.getShortForLarge(this.content, i + 10) / 1000.0);
		}
	}
	
	/** 
	* @Title: isResult 
	* @Description: 判断数据是否传输完整 
	* @param @return    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	public Boolean isResult()
	{
		return this.chargeSingleNum<this.chargeStartIndex+this.currentBatteryNum;
	}

	public List<PairResult> getResults(int i) {
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(new PairResult("chargeServerNo", "chargeServerNo" + i, "可充电储能子系统号",
				String.valueOf(this.chargeServerNo)));
		tempPairs.add(
				new PairResult("chargeVoltage", "chargeVoltage" + i, "可充电储能装置电压", String.valueOf(this.chargeVoltage)));
		tempPairs.add(new PairResult("chargeElectricity", "chargeElectricity" + i, "可充电储能电流",
				String.valueOf(this.chargeElectricity)));
		tempPairs.add(new PairResult("chargeSingleNum", "chargeSingleNum" + i, "单体电池总数",
				String.valueOf(this.chargeSingleNum)));
		tempPairs.add(new PairResult("chargeStartIndex", "chargeStartIndex" + i, "本帧起始电池序号",
				String.valueOf(this.chargeStartIndex)));
		tempPairs.add(new PairResult("currentBatteryNum", "currentBatteryNum" + i, "本帧单体电池总数",
				String.valueOf(this.currentBatteryNum)));
		tempPairs.add(new PairResult("singleBatteryVoltages", "singleBatteryVoltages" + i, " 单体电池电压", this.toString()));

		return tempPairs;
	}

	private int chargeServerNo;// 可充电储能子系统号
	private double chargeVoltage;// 可充电储能装置电压
	private double chargeElectricity;// 可充电储能电流
	private int chargeSingleNum;// 单体电池总数
	private int chargeStartIndex;// 本帧起始电池序号
	private int currentBatteryNum;// 本帧单体电池总数
	private List<Double> singleBatteryVoltages = new ArrayList<>();// 单体电池电压

	/**
	 * @return the singleBatteryVoltages
	 */
	public List<Double> getSingleBatteryVoltages() {
		return singleBatteryVoltages;
	}

	/**
	 * @param singleBatteryVoltages the singleBatteryVoltages to set
	 */
	public void setSingleBatteryVoltages(List<Double> singleBatteryVoltages) {
		this.singleBatteryVoltages = singleBatteryVoltages;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (double voltage : singleBatteryVoltages) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append(',');
			}
			stringBuilder.append(String.valueOf(voltage));
		}
		return stringBuilder.toString();
	}

	public int getLength() {
		return 10 + this.currentBatteryNum * 2;
	}

}
