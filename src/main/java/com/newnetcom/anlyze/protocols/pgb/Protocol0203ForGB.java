package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class Protocol0203ForGB implements IProtocol02ForGB {

	@Override
	public List<PairResult> getResults() {
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(new PairResult("fuelVoltage","fuelVoltage","燃料电池电压",String.valueOf(this.fuelVoltage)));
		tempPairs.add(new PairResult("fuelElectricity","fuelElectricity","燃料电池电流",String.valueOf(this.fuelElectricity)));
		tempPairs.add(new PairResult("fuelConsumptionRate","fuelConsumptionRate","燃料消耗率",String.valueOf(this.fuelConsumptionRate)));
		tempPairs.add(new PairResult("fuelTotalProbes","fuelTotalProbes","燃料电池温度探针总数",String.valueOf(this.fuelTotalProbes)));
		tempPairs.add(new PairResult("fuelProbesValue","fuelProbesValue","探针温度",String.valueOf(this.fuelProbesValue)));
		tempPairs.add(new PairResult("hydrogenTem","hydrogenTem","氢系统中最高温度",String.valueOf(this.hydrogenTem)));
		tempPairs.add(new PairResult("hydrogenTemNo","hydrogenTemNo","氢系统中最高温度探针代号",String.valueOf(this.hydrogenTemNo)));
		tempPairs.add(new PairResult("hydrogenConcentration","hydrogenConcentration","氢气最高浓度",String.valueOf(this.hydrogenConcentration)));
		tempPairs.add(new PairResult("hydrogenConcentrationNo","hydrogenConcentrationNo","氢气最高浓度传感器代号",String.valueOf(this.hydrogenConcentrationNo)));
		tempPairs.add(new PairResult("hydrogenPressure","hydrogenPressure","氢气最高压力",String.valueOf(this.hydrogenPressure)));
		tempPairs.add(new PairResult("hydrogenPressureNo","hydrogenPressureNo","氢气最高压力传感器代号",String.valueOf(this.hydrogenPressureNo)));
		tempPairs.add(new PairResult("highDCDCStatus","highDCDCStatus","高DC/DC状态",String.valueOf(this.highDCDCStatus)));
		
		return tempPairs;
	}
     private byte[] content;
     public Protocol0203ForGB(byte[] inContent){
    	 this.content=inContent;
    	 if(this.content.length>0)
    	 {
    		 this.anlye();
    	 }
    	 
     }
     
     private void anlye(){
    	 this.fuelVoltage=ByteUtils.getShort(this.content, 0)/10.0;
    	 this.fuelElectricity=ByteUtils.getShort(this.content, 2)/10.0;
    	 this.fuelConsumptionRate=ByteUtils.getShort(this.content, 4)/100.0;
    	 this.fuelTotalProbes=ByteUtils.getShort(this.content, 6);
    	 for(int i=0;i<this.fuelTotalProbes;i++){
    		 this.fuelProbesValue.add(this.content[i+7]&0xff-40);
    	 }
    	 this.hydrogenTem=ByteUtils.getShort(this.content, this.fuelTotalProbes+7);
    	 this.hydrogenTemNo=this.content[this.fuelTotalProbes+9]&0xff;
    	 this.hydrogenConcentration=ByteUtils.getShort(this.content, this.fuelTotalProbes+10);
    	 this.hydrogenConcentrationNo=this.content[this.fuelTotalProbes+12];
    	 this.hydrogenPressure=ByteUtils.getShort(this.content, this.fuelTotalProbes+13);
    	 this.hydrogenPressureNo=this.content[this.fuelTotalProbes+15]&0xff;
    	 this.highDCDCStatus=this.content[this.fuelTotalProbes+16]&0xff;
    	 
     }
	private double fuelVoltage=0;
	private double fuelElectricity=0;
	private double fuelConsumptionRate=0;//燃料消耗率
	private int fuelTotalProbes=0 ;//燃料电池温度探针总数
	private List<Integer> fuelProbesValue=new ArrayList<>();
	private double hydrogenTem;//氢系统中最高温度
	private int hydrogenTemNo;//氢系统最高问题探针代号
	private int hydrogenConcentration;//氢气最高浓度
	private int hydrogenConcentrationNo;//氢气最高浓度传感器代号
	private double hydrogenPressure;//氢气最高压力
	private int hydrogenPressureNo;//氢气最高压力代号
	private int highDCDCStatus;//高压DC/DC状态
	@Override
	public int getDataLength() {
		// TODO Auto-generated method stub
		return this.fuelTotalProbes+17;
	}




}
