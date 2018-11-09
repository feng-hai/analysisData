package com.newnetcom.anlyze.protocols.pgb;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class Protocol0208ForGB implements IProtocol02ForGB {

	private static Map<String, ChargeVoltageInfo> subCh = new ConcurrentHashMap<>();
	private byte[] content;
	private String vehicleID;

	public Protocol0208ForGB(byte[] inContent, String inVehicleID) {
		this.content = inContent;
		this.vehicleID = inVehicleID;
		if (this.content.length > 0) {
			this.anlye();
		}
	}
    private int length=0;
	private void anlye() {
		this.chargeServerNo = this.content[0] & 0xff;
		length++;
		for (int i = 0; i < this.chargeServerNo;) {
			byte[] temp = ByteUtils.getSubBytes(this.content, i + 1, this.content.length - i - 1);
			int no = temp[0] & 0xff;
			ChargeVoltageInfo tempcvi = null;
			if (subCh.containsKey(this.vehicleID + no)) {
				tempcvi = subCh.get(this.vehicleID + no);
			}
			ChargeVoltageInfo cvi = new ChargeVoltageInfo(temp,
					tempcvi == null ? null : tempcvi.getSingleBatteryVoltages());
			i = i + cvi.getLength();
			length=length+cvi.getLength();
			if (cvi.isResult()) {
				cviList.add(cvi);
				subCh.remove(this.vehicleID + no);
			} else {
				if(!subCh.containsKey(this.vehicleID + no))
				{
					cviList.add(cvi);	
				}
				subCh.put(this.vehicleID + no, cvi);
				
			}
		}
	}

	@Override
	public List<PairResult> getResults() {
		// TODO Auto-generated method stub
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		tempPairs.add(
				new PairResult("chargeServerNo", "chargeServerNo", "可充电储能子系统个数", String.valueOf(this.chargeServerNo)));

		int i = 0;
		for (ChargeVoltageInfo cvi : cviList) {
			i++;
			tempPairs.addAll(cvi.getResults(i));
		}
		return tempPairs;
	}

	@Override
	public int getDataLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int chargeServerNo;
	private List<ChargeVoltageInfo> cviList = new ArrayList<>();

}
