package com.newnetcom.anlyze.protocols.p808.alarm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.config.Read808Config;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.StrFormat;

/**
 * 
 * 项目名称：com.newnetcom.anlyze 类名称：Protocol0200AlarmStatus 类描述： 创建人：FH
 * 创建时间：2017年11月8日 下午9:09:44
 * 
 * @version
 */
public class Protocol0200AlarmStatus {

	private byte[] content;
	private char[] charContents;

	private static Logger logger = LoggerFactory.getLogger(Protocol0200AlarmStatus.class);

	/**
	 * @Fields alarmPairs : TODO(用一句话描述这个变量表示什么)
	 */
	private List<Pair> alarmPairs = new ArrayList<>();

	/**
	 * @Title: getAlarmPairs @Description: TODO(这里用一句话描述这个方法的作用) @param @return
	 * 设定文件 @return List<Pair> 返回类型 @throws
	 */
	public List<Pair> getAlarmPairs() {
		return alarmPairs;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param incontent
	 */
	public Protocol0200AlarmStatus(byte[] incontent) {
		this.content = incontent;
		this.charContents = StrFormat.addZeroForNum(ByteUtils.binary(this.content, 2), 32).toCharArray();
		this.alarmPairs = Read808Config.getAlarmStatus();
		this.anlysis();
	}

	/**
	 * @Title: anlysis @Description: TODO(这里用一句话描述这个方法的作用) @param 设定文件 @return
	 * void 返回类型 @throws
	 */
	private void anlysis() {
		for (Pair pair : this.alarmPairs) {
			try {
				pair.setValue(String.valueOf(this.charContents[pair.getStart()]));
			} catch (Exception ex) {
				logger.error("解析808-0200报警状态错误", ex);
			}
			
			//System.out.println(pair.getTitle()+":"+pair.getValue());
		}
	}

	//
	// /**
	// * @Fields emergencyAlarm : 紧急报警
	// */
	// private byte emergencyAlarm;
	// /**
	// * @Fields speedAlarm : 超速报警
	// */
	// private byte speedAlarm;
	//
	// /**
	// * @Fields fatigueDriving : 疲劳驾驶
	// */
	// private byte fatigueDriving;
	//
	// /**
	// * @Fields riskWarning : 危险预警
	// */
	// private byte riskWarning;
	//
	// /**
	// * @Fields GNSSAlarm : GNSS 模块发生故障
	// */
	// private byte GNSSModelAlarm;
	//
	// /**
	// * @Fields GNSSNotAntenna : GNSS 天线未接或被剪断
	// */
	// private byte GNSSNotAntenna ;
	//
	// /**
	// * @Fields GNSSAntennaShortCircuit : GNSS 天线短路
	// */
	// private byte GNSSAntennaShortCircuit;
	//
	// /**
	// * @Fields UndervoltageOfTerminal : 终端主电源欠压
	// */
	// private byte undervoltageOfTerminal;
	//
	// /**
	// * @Fields powerLossOfTerminal : 终端主电源掉电
	// */
	// private byte powerLossOfTerminal;
	//
	// /**
	// * @Fields terminalLCDOrDisplayFault : 终端 LCD 或显示器故障
	// */
	// private byte terminalLCDOrDisplayFault;
	//
	// public byte getEmergencyAlarm() {
	// return emergencyAlarm;
	// }
	//
	// public void setEmergencyAlarm(byte emergencyAlarm) {
	// this.emergencyAlarm = emergencyAlarm;
	// }
	//
	// public byte getSpeedAlarm() {
	// return speedAlarm;
	// }
	//
	// public void setSpeedAlarm(byte speedAlarm) {
	// this.speedAlarm = speedAlarm;
	// }
	//
	// public byte getFatigueDriving() {
	// return fatigueDriving;
	// }
	//
	// public void setFatigueDriving(byte fatigueDriving) {
	// this.fatigueDriving = fatigueDriving;
	// }
	//
	// public byte getRiskWarning() {
	// return riskWarning;
	// }
	//
	// public void setRiskWarning(byte riskWarning) {
	// this.riskWarning = riskWarning;
	// }
	//
	// public byte getGNSSModelAlarm() {
	// return GNSSModelAlarm;
	// }
	//
	// public void setGNSSModelAlarm(byte gNSSModelAlarm) {
	// GNSSModelAlarm = gNSSModelAlarm;
	// }
	//
	// public byte getGNSSNotAntenna() {
	// return GNSSNotAntenna;
	// }
	//
	// public void setGNSSNotAntenna(byte gNSSNotAntenna) {
	// GNSSNotAntenna = gNSSNotAntenna;
	// }
	//
	// public byte getGNSSAntennaShortCircuit() {
	// return GNSSAntennaShortCircuit;
	// }
	//
	// public void setGNSSAntennaShortCircuit(byte gNSSAntennaShortCircuit) {
	// GNSSAntennaShortCircuit = gNSSAntennaShortCircuit;
	// }
	//
	// public byte getUndervoltageOfTerminal() {
	// return undervoltageOfTerminal;
	// }
	//
	// public void setUndervoltageOfTerminal(byte undervoltageOfTerminal) {
	// this.undervoltageOfTerminal = undervoltageOfTerminal;
	// }
	//
	// public byte getPowerLossOfTerminal() {
	// return powerLossOfTerminal;
	// }
	//
	// public void setPowerLossOfTerminal(byte powerLossOfTerminal) {
	// this.powerLossOfTerminal = powerLossOfTerminal;
	// }
	//
	// public byte getTerminalLCDOrDisplayFault() {
	// return terminalLCDOrDisplayFault;
	// }
	//
	// public void setTerminalLCDOrDisplayFault(byte terminalLCDOrDisplayFault)
	// {
	// this.terminalLCDOrDisplayFault = terminalLCDOrDisplayFault;
	// }
}
