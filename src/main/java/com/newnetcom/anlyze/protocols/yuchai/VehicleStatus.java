package com.newnetcom.anlyze.protocols.yuchai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VehicleStatus {

	private static final Logger logger = LoggerFactory.getLogger(ProtocolLocal.class);
	private byte[] contents;

	public VehicleStatus(byte[] inContents) {
		this.contents = inContents;
		try {
			anlyze();
		} catch (Exception ex) {
			logger.error("解析GPS数据出错", ex);
		}
	}

	private byte gpsStatus = 0;// ：为 1 时表示 GPS 天线异常（开路或短路），为 0 时表示天线正常。
	private byte openStatus = 0;// 为 1 时表示终端为开盖状态，为 0 时终端外壳为关闭状态
	private byte mainElectricState = 0;// 为 1 时表示主电为被切断状态，为 0 时表示主电未被切断。
	private byte mainElectricLow = 0;// 为 1 时表示主电欠压，为 0 时表示主电未欠压。
	private byte canStatus = 0;// 为 1 时表示 CAN 总线故障，为 0 时表示 CAN 总线工作正常
	private byte electricPower = 0;// ：为 1 时表示备电处于断电状态；为 0 时表示备电未处于断电状态。
	private byte electricPowerLow = 0;// 为 1 时表示备电处于欠压状态；为 0 时表示备电未处于欠压状态。
	private void anlyze() {
		this.gpsStatus=(byte)(this.contents[0]&0x02);
		this.openStatus=(byte)(this.contents[0]&0x04);
		this.mainElectricState=(byte)(this.contents[0]&0x08);
		this.mainElectricLow=(byte)(this.contents[0]&0x10);
		this.canStatus=(byte)(this.contents[0]&0x20);
		this.electricPower=(byte)(this.contents[1]&0x08);
		this.electricPowerLow=(byte)(this.contents[1]&0x10);
	}

	private byte acc = 0;// ：为 1 时表示车辆 ACC 为点火状态；为 0 时为熄火状态
	/**
	 * @return the contents
	 */
	public byte[] getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(byte[] contents) {
		this.contents = contents;
	}

	/**
	 * @return the acc
	 */
	public byte getAcc() {
		return acc;
	}

	/**
	 * @param acc the acc to set
	 */
	public void setAcc(byte acc) {
		this.acc = acc;
	}

	/**
	 * @return the gpsStatus
	 */
	public byte getGpsStatus() {
		return gpsStatus;
	}

	/**
	 * @param gpsStatus the gpsStatus to set
	 */
	public void setGpsStatus(byte gpsStatus) {
		this.gpsStatus = gpsStatus;
	}

	/**
	 * @return the openStatus
	 */
	public byte getOpenStatus() {
		return openStatus;
	}

	/**
	 * @param openStatus the openStatus to set
	 */
	public void setOpenStatus(byte openStatus) {
		this.openStatus = openStatus;
	}

	/**
	 * @return the mainElectricState
	 */
	public byte getMainElectricState() {
		return mainElectricState;
	}

	/**
	 * @param mainElectricState the mainElectricState to set
	 */
	public void setMainElectricState(byte mainElectricState) {
		this.mainElectricState = mainElectricState;
	}

	/**
	 * @return the voltage
	 */
	public byte getMainElectricLow() {
		return mainElectricLow;
	}

	/**
	 * @param voltage the voltage to set
	 */
	public void setMainElectricLow(byte voltage) {
		this.mainElectricLow = voltage;
	}

	/**
	 * @return the canStatus
	 */
	public byte getCanStatus() {
		return canStatus;
	}

	/**
	 * @param canStatus the canStatus to set
	 */
	public void setCanStatus(byte canStatus) {
		this.canStatus = canStatus;
	}

	/**
	 * @return the electricPower
	 */
	public byte getElectricPower() {
		return electricPower;
	}

	/**
	 * @param electricPower the electricPower to set
	 */
	public void setElectricPower(byte electricPower) {
		this.electricPower = electricPower;
	}

	/**
	 * @return the electricPowerLow
	 */
	public byte getElectricPowerLow() {
		return electricPowerLow;
	}

	/**
	 * @param electricPowerLow the electricPowerLow to set
	 */
	public void setElectricPowerLow(byte electricPowerLow) {
		this.electricPowerLow = electricPowerLow;
	}


}
