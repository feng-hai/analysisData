package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class AlarmInfo {
	private byte[] content;

	public AlarmInfo(byte[] inContent) {
		this.content = inContent;
		this.anlye();
	}

	private void anlye() {
		long temp = ByteUtils.getIntForLarge(this.content, 0);
		this.temperatureDifferenceAlarm = (temp & 0x01) > 0 ? 1 : 0;
		this.batteryHighTemperatureAlarm = (temp & 0x02) > 0 ? 1 : 0;
		this.overvoltageAlarm = (temp & 0x04) > 0 ? 1 : 0;
		this.undervoltageAlarm = (temp & 0x08) > 0 ? 1 : 0;
		this.lowSocAlarm = (temp & 0x10) > 0 ? 1 : 0;
		this.singleOvervoltageAlarm = (temp & 0x20) > 0 ? 1 : 0;
		this.singleUndervoltageAlarm = (temp & 0x40) > 0 ? 1 : 0;
		this.socExorbitantAlarm = (temp & 0x80) > 0 ? 1 : 0;
		this.socJumpAlarm = (temp & 0x100) > 0 ? 1 : 0;
		this.serverMismatch = (temp & 0x200) > 0 ? 1 : 0;
		this.singleUniformity = (temp & 0x400) > 0 ? 1 : 0;
		this.insulationAlarm = (temp & 0x800) > 0 ? 1 : 0;
		this.dCDCTemAlarm = (temp & 0x1000) > 0 ? 1 : 0;
		this.brakingAlarm = (temp & 0x2000) > 0 ? 1 : 0;
		this.dCDCStatusAlarm = (temp & 0x4000) > 0 ? 1 : 0;
		this.controlTemAlarm = (temp & 0x8000) > 0 ? 1 : 0;
		this.highVoltageInterlocking = (temp & 0x10000) > 0 ? 1 : 0;
		this.engineTemAlarm = (temp & 0x20000) > 0 ? 1 : 0;
		this.overchargeAlarm = (temp & 0x40000) > 0 ? 1 : 0;
	}

	private int temperatureDifferenceAlarm;// 温度差异报警
	private int batteryHighTemperatureAlarm;// 电池高温报警
	private int overvoltageAlarm;// 过压报警
	private int undervoltageAlarm;// 欠压报警
	private int lowSocAlarm;// soc低报警
	private int singleOvervoltageAlarm;// 单体电池过压报警
	private int singleUndervoltageAlarm;// 单体电池欠压报警
	private int socExorbitantAlarm;// soc过高报警
	private int socJumpAlarm;// soc跳变报警
	private int serverMismatch;// 可充电储能系统不匹配报警
	private int singleUniformity;// 电池单体一致性差报警
	private int insulationAlarm;// 绝缘报警
	private int dCDCTemAlarm;// DCDC温度报警
	private int brakingAlarm;// 制动报警
	private int dCDCStatusAlarm;// DCDC状态报警
	private int controlTemAlarm;// 驱动电机控制器温度报警
	private int highVoltageInterlocking;// 高压互锁状态报警
	private int engineTemAlarm;// 驱动电机温度报警
	private int overchargeAlarm;// 车载储能装置类型过充报警
	
	public List<PairResult> getResults() {
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(new PairResult("temperatureDifferenceAlarm","temperatureDifferenceAlarm","温度差异报警",String.valueOf(this.temperatureDifferenceAlarm)));
		tempPairs.add(new PairResult("batteryHighTemperatureAlarm","batteryHighTemperatureAlarm","电池高温报警",String.valueOf(this.batteryHighTemperatureAlarm)));
		tempPairs.add(new PairResult("overvoltageAlarm","overvoltageAlarm","过压报警",String.valueOf(this.overvoltageAlarm)));
		tempPairs.add(new PairResult("undervoltageAlarm","undervoltageAlarm","欠压报警",String.valueOf(this.undervoltageAlarm)));
		tempPairs.add(new PairResult("lowSocAlarm","lowSocAlarm"," soc低报警",String.valueOf(this.lowSocAlarm)));
		tempPairs.add(new PairResult("singleOvervoltageAlarm","singleOvervoltageAlarm","单体电池过压报警",String.valueOf(this.singleOvervoltageAlarm)));
		tempPairs.add(new PairResult("singleUndervoltageAlarm","singleUndervoltageAlarm","单体电池欠压报警",String.valueOf(this.singleUndervoltageAlarm)));
		tempPairs.add(new PairResult("socExorbitantAlarm","socExorbitantAlarm","soc过高报警",String.valueOf(this.socExorbitantAlarm)));
		tempPairs.add(new PairResult("socJumpAlarm","socJumpAlarm","soc跳变报警",String.valueOf(this.socJumpAlarm)));
		tempPairs.add(new PairResult("serverMismatch","serverMismatch","可充电储能系统不匹配报警",String.valueOf(this.serverMismatch)));
		tempPairs.add(new PairResult("singleUniformity","singleUniformity","电池单体一致性差报警",String.valueOf(this.singleUniformity)));
		tempPairs.add(new PairResult("insulationAlarm","insulationAlarm","绝缘报警",String.valueOf(this.insulationAlarm)));
		
		tempPairs.add(new PairResult("dCDCTemAlarm","dCDCTemAlarm","DCDC温度报警",String.valueOf(this.dCDCTemAlarm)));
		
		tempPairs.add(new PairResult("brakingAlarm","brakingAlarm","制动报警",String.valueOf(this.brakingAlarm)));
		tempPairs.add(new PairResult("dCDCStatusAlarm","dCDCStatusAlarm","DCDC状态报警",String.valueOf(this.dCDCStatusAlarm)));
		tempPairs.add(new PairResult("controlTemAlarm","controlTemAlarm","驱动电机控制器温度报警",String.valueOf(this.controlTemAlarm)));
		tempPairs.add(new PairResult("highVoltageInterlocking","highVoltageInterlocking","高压互锁状态报警",String.valueOf(this.highVoltageInterlocking)));
		tempPairs.add(new PairResult("engineTemAlarm","engineTemAlarm","驱动电机温度报警",String.valueOf(this.engineTemAlarm)));
		tempPairs.add(new PairResult("overchargeAlarm","overchargeAlarm","车载储能装置类型过充报警",String.valueOf(this.overchargeAlarm)));
		
		return tempPairs;
	}
}
