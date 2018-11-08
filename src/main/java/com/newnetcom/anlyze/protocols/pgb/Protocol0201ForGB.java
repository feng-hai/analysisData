package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class Protocol0201ForGB implements IProtocol02ForGB{
	
	private byte[] content;
	/** 
	* <p>Title:整车数据 </p> 
	* <p>Description: </p> 
	* @param inContent 
	*/
	public Protocol0201ForGB(byte[] inContent)
	{
		this.content=inContent;
		if(this.content.length>18)
		{
			this.anlyze();
		}
	}
	/**
	 * @return the vehicleStatus
	 */
	public int getVehicleStatus() {
		return vehicleStatus;
	}
	/**
	 * @param vehicleStatus the vehicleStatus to set
	 */
	public void setVehicleStatus(int vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}
	/**
	 * @return the chargerStatus
	 */
	public int getChargerStatus() {
		return chargerStatus;
	}
	/**
	 * @param chargerStatus the chargerStatus to set
	 */
	public void setChargerStatus(int chargerStatus) {
		this.chargerStatus = chargerStatus;
	}
	/**
	 * @return the runType
	 */
	public int getRunType() {
		return runType;
	}
	/**
	 * @param runType the runType to set
	 */
	public void setRunType(int runType) {
		this.runType = runType;
	}
	
	
	
	
	/**
	 * @return the soc
	 */
	public int getSoc() {
		return soc;
	}
	/**
	 * @param soc the soc to set
	 */
	public void setSoc(int soc) {
		this.soc = soc;
	}
	/**
	 * @return the dcdcStatus
	 */
	public int getDcdcStatus() {
		return dcdcStatus;
	}
	/**
	 * @param dcdcStatus the dcdcStatus to set
	 */
	public void setDcdcStatus(int dcdcStatus) {
		this.dcdcStatus = dcdcStatus;
	}
	/**
	 * @return the gear
	 */
	public int getGear() {
		return gear;
	}
	/**
	 * @param gear the gear to set
	 */
	public void setGear(int gear) {
		this.gear = gear;
	}
	/**
	 * @return the insulationResistance
	 */
	public int getInsulationResistance() {
		return insulationResistance;
	}
	/**
	 * @param insulationResistance the insulationResistance to set
	 */
	public void setInsulationResistance(int insulationResistance) {
		this.insulationResistance = insulationResistance;
	}
	/**
	 * @return the lastNUm
	 */
	public int getLastNUm() {
		return lastNUm;
	}
	/**
	 * @param lastNUm the lastNUm to set
	 */
	public void setLastNUm(int lastNUm) {
		this.lastNUm = lastNUm;
	}
	private int vehicleStatus=2;//1个字节
	private int chargerStatus=3;//1个字节
	private int runType=1;//1个字节
	private double speed=0;//车辆速度2个字节
	private double mileage=0;//里程4个字节
	private double allVoltage=0;//总电压2个字节
	private double allElectricity=0;//总电流2个字节
	private int soc=0;//1个字节
	private int dcdcStatus=0;//1个字节
	private int gear=0;//1个字节
	private int insulationResistance=0;//绝缘电阻2个字节
	private int lastNUm=0;//预留2个字节
	@Override
	public List<PairResult> getResults() {
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(new PairResult("vehicleStatus","vehicleStatus","车辆状态",String.valueOf(this.vehicleStatus)));
		tempPairs.add(new PairResult("chargerStatus","chargerStatus","充电状态",String.valueOf(this.getChargerStatus())));
		tempPairs.add(new PairResult("runType","runType","运行模式",String.valueOf(this.runType)));
		tempPairs.add(new PairResult("speed","speed","车辆速度",String.valueOf(this.speed)));
		tempPairs.add(new PairResult("mileage","mileage","累计里程",String.valueOf(this.mileage)));
		tempPairs.add(new PairResult("allVoltage","allVoltage","总电压",String.valueOf(this.allVoltage)));
		tempPairs.add(new PairResult("allElectricity","allElectricity","总电流",String.valueOf(this.allElectricity)));
		tempPairs.add(new PairResult("soc","soc","SOC",String.valueOf(this.soc)));
		tempPairs.add(new PairResult("dcdcStatus","dcdcStatus","DCDC状态",String.valueOf(this.dcdcStatus)));
		tempPairs.add(new PairResult("gear","gear","档位",String.valueOf(this.gear)));
		tempPairs.add(new PairResult("insulationResistance","insulationResistance","绝缘电阻",String.valueOf(this.insulationResistance)));
		tempPairs.add(new PairResult("lastNUm","lastNUm","预留",String.valueOf(this.lastNUm)));
		
		return tempPairs;
	}
	
	private void anlyze() {
		this.vehicleStatus=this.content[0]&0xff;
		this.chargerStatus=this.content[1]&0xff;
		this.runType=this.content[2]&0xff;
		this.speed=ByteUtils.getShortForLarge(this.content, 3)/10.0;
		this.mileage=ByteUtils.getIntForLarge(this.content, 5)/10.0;
		this.allVoltage=ByteUtils.getShortForLarge(this.content, 9)/10.0;
		this.allElectricity=(ByteUtils.getShortForLarge(this.content, 11))/10.0-1000;
		this.soc=this.content[13]&0xff;
		this.dcdcStatus=this.content[14]&0xff;
		this.gear=this.content[15]&0xff;
		this.insulationResistance=ByteUtils.getShortForLarge(this.content, 16);
		this.lastNUm=ByteUtils.getShortForLarge(this.content, 18);
	}
	@Override
	public int getDataLength() {
		// TODO Auto-generated method stub
		return 20;
	}
	
}
