package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class DrivingMotor {
	private byte[] content;
	public DrivingMotor(byte[] inContent)
	{
		this.content=inContent;
		if(this.content.length==12)
		{
			this.anlyze();
		}
		
	}
	
	private void anlyze(){
		this.motorNo=this.content[0]&0xff;
		this.motorStatus=this.content[1]&0xff;
		this.motorControlTemperature=(this.content[2]&0xff)-40;
		this.motorSpeed=ByteUtils.getShortForLarge(this.content, 3)-20000;
		this.motorTorque=(ByteUtils.getShortForLarge(this.content, 5)-20000)/10.0;
		this.motorEngineTemperature=(this.content[7]&0xff)-40;
		this.motorControlVoltage=ByteUtils.getShortForLarge(this.content, 8)/10.0;
		this.motorControlElectricity=(ByteUtils.getShortForLarge(this.content,10))/10.0-1000;
			
	}
	
	public List<PairResult> getResults(int i) {
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(new PairResult("motorNo","motorNo"+i,"驱动电机号",String.valueOf(this.motorNo)));
		tempPairs.add(new PairResult("motorStatus","motorStatus"+i,"驱动电机状态",String.valueOf(this.motorStatus)));
		tempPairs.add(new PairResult("motorControlTemperature","motorControlTemperature"+i,"驱动电机控制器温度",String.valueOf(this.motorControlTemperature)));
		tempPairs.add(new PairResult("motorSpeed","motorSpeed"+i,"驱动电机转速",String.valueOf(this.motorSpeed)));
		tempPairs.add(new PairResult("motorTorque","motorTorque"+i,"驱动电机扭矩",String.valueOf(this.motorTorque)));
		tempPairs.add(new PairResult("motorEngineTemperature","motorEngineTemperature"+i,"驱动电机温度",String.valueOf(this.motorEngineTemperature)));
		tempPairs.add(new PairResult("motorControlVoltage","motorControlVoltage"+i,"电机控制器输入电压",String.valueOf(this.motorControlVoltage)));
		tempPairs.add(new PairResult("motorControlElectricity","motorControlElectricity"+i,"电机控制器直流母线电流",String.valueOf(this.motorControlElectricity)));
		return tempPairs;
	}
	private int motorNo=0;
	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * @return the motorNo
	 */
	public int getMotorNo() {
		return motorNo;
	}

	/**
	 * @param motorNo the motorNo to set
	 */
	public void setMotorNo(int motorNo) {
		this.motorNo = motorNo;
	}

	/**
	 * @return the motorStatus
	 */
	public int getMotorStatus() {
		return motorStatus;
	}

	/**
	 * @param motorStatus the motorStatus to set
	 */
	public void setMotorStatus(int motorStatus) {
		this.motorStatus = motorStatus;
	}

	/**
	 * @return the motorControlTemperature
	 */
	public int getMotorControlTemperature() {
		return motorControlTemperature;
	}

	/**
	 * @param motorControlTemperature the motorControlTemperature to set
	 */
	public void setMotorControlTemperature(int motorControlTemperature) {
		this.motorControlTemperature = motorControlTemperature;
	}

	/**
	 * @return the motorSpeed
	 */
	public int getMotorSpeed() {
		return motorSpeed;
	}

	/**
	 * @param motorSpeed the motorSpeed to set
	 */
	public void setMotorSpeed(int motorSpeed) {
		this.motorSpeed = motorSpeed;
	}



	/**
	 * @return the motorEngineTemperature
	 */
	public int getMotorEngineTemperature() {
		return motorEngineTemperature;
	}

	/**
	 * @param motorEngineTemperature the motorEngineTemperature to set
	 */
	public void setMotorEngineTemperature(int motorEngineTemperature) {
		this.motorEngineTemperature = motorEngineTemperature;
	}

	private int motorStatus=0;
	private int motorControlTemperature=0;
	private int motorSpeed=0;
	private double motorTorque=0;
	private int motorEngineTemperature=0;
	private double motorControlVoltage=0;
	private double motorControlElectricity=0;

}
