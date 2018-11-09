package com.newnetcom.anlyze.protocols.pgb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.utils.ByteUtils;

public class Protocol02ForGB {
	private byte[] contents;
     private String vehicleId;
	public Protocol02ForGB(byte[] incontents, String inVehicleId) {
		this.contents = incontents;
		this.vehicleId=inVehicleId;
		this.anlyze();
	}
	private Date dataTime;
//	private int serial;
//	private String ICCID;
//	private Byte subSystemNum;
//	private byte systemCodeLength;
//	private String chargeSystemNum;
	private List<PairResult> tempPairs = new ArrayList<PairResult>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private void anlyze() {
		dataTime = ByteUtils.BytesTodateForSecondForNotUTC(this.contents, 0);
		int dataIndex = 6;

		while (dataIndex < this.contents.length) {
			int type = this.contents[dataIndex++] & 0xff;
			byte[] temp = ByteUtils.getSubBytes(this.contents, dataIndex, this.contents.length-dataIndex);
			IProtocol02ForGB protocol02 = null;
			switch (type) {
				case 0x01: {
	
					protocol02 = new Protocol0201ForGB(temp);
	
					break;
				}
				case 0x02: {
					protocol02 = new Protocol0202ForGB(temp);
	
					break;
				}
				case 0x03: {
					protocol02 = new Protocol0203ForGB(temp);
	
					break;
				}
				case 0x04: {
					protocol02 = new Protocol0204ForGB(temp);
	
					break;
				}
				case 0x05: {
					protocol02 = new Protocol0205ForGB(temp);
	
					break;
				}
				case 0x06: {
					protocol02 = new Protocol0206ForGB(temp);
	
					break;
				}
				case 0x07: {
					protocol02 = new Protocol0207ForGB(temp);
					break;
				}
				case 0x08: {
					protocol02 = new Protocol0208ForGB(temp,this.vehicleId);
					break;
				}
				case 0x09: {
					protocol02 = new Protocol0209ForGB(temp);
					break;
				}
			}
			if (protocol02 != null) {
				tempPairs.addAll(protocol02.getResults());
				dataIndex = dataIndex + protocol02.getDataLength();
			}
		}
		tempPairs.add(new PairResult("dataTime","dataTime","时间",sdf.format(dataTime)) );
	}
	
	/**
	 * @return the dataTime
	 */
	public Date getDataTime() {
		return dataTime;
	}

	/**
	 * @param dataTime the dataTime to set
	 */
	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	public List<PairResult> getResult(){
		return tempPairs;
	}

	

}
