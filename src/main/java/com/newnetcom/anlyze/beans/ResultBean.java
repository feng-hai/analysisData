package com.newnetcom.anlyze.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.newnetcom.anlyze.utils.JsonUtils;

public class ResultBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * @Fields key : 车辆唯一标识可以是终端唯一标识也可以是车辆vin
	 */
	private String key;
	/**
	 * @Fields datetime : 终端上传数据的时间
	 */
	private Date datetime;
	/**
	 * @Fields systemDate : 服务器收到数据的时间
	 */
	private Date systemDate;

	/**
	 * @Fields lng : 经度
	 */
	private Double lng;
	/**
	 * @Fields lat : 维度
	 */
	private Double lat;

	/**
	 * @Fields distance : 里程4个字节
	 */
	private float distance;

	/**
	 * @Fields direction : 方向角 2个字节 度
	 */
	private int direction;

	private String fiber_unid;
	public String getFiber_unid() {
		return fiber_unid;
	}

	public void setFiber_unid(String fiber_unid) {
		this.fiber_unid = fiber_unid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private String content;

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	private String vehicleUnid;

	public String getVehicleUnid() {
		return vehicleUnid;
	}

	public void setVehicleUnid(String vehicleUnid) {
		this.vehicleUnid = vehicleUnid;
	}

	/**
	 * @Fields hVesion : 硬件版本2个字节
	 */
	private int hVersion;
	/**
	 * @Fields sVesion : 软件版本2个字节
	 */
	private String sVersion;

	/**
	 * @Fields acc :ACC状态 B0
	 */
	private byte acc;
	/**
	 * @Fields locationState : 定位状态B1
	 */
	private byte locationState;
	/**
	 * @Fields starNum :卫星个数B5-B2 4位
	 */
	private byte starNum;
	/**
	 * @Fields gpsStatus : GPS模块正常， =1 模块异常 B6
	 */
	private byte gpsStatus;
	/**
	 * @Fields electricityStatus : B7 供电状态 0=外电供电；1=电池供电；
	 */
	private byte electricityStatus;

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public int gethVersion() {
		return hVersion;
	}

	public void sethVersion(int hVersion) {
		this.hVersion = hVersion;
	}

	public String getsVersion() {
		return sVersion;
	}

	public void setsVersion(String sVersion) {
		this.sVersion = sVersion;
	}

	public byte getAcc() {
		return acc;
	}

	public void setAcc(byte acc) {
		this.acc = acc;
	}

	public byte getLocationState() {
		return locationState;
	}

	public void setLocationState(byte locationState) {
		this.locationState = locationState;
	}

	public byte getStarNum() {
		return starNum;
	}

	public void setStarNum(byte starNum) {
		this.starNum = starNum;
	}

	public byte getGpsStatus() {
		return gpsStatus;
	}

	public void setGpsStatus(byte gpsStatus) {
		this.gpsStatus = gpsStatus;
	}

	public byte getElectricityStatus() {
		return electricityStatus;
	}

	public void setElectricityStatus(byte electricityStatus) {
		this.electricityStatus = electricityStatus;
	}

	/**
	 * @Fields pairs : Cans内容解析
	 */
	private List<PairResult> pairs;

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	/**
	 * @Title: getKey @Description: 车辆唯一标识可以是终端唯一标识也可以是车辆vin @param @return
	 * 设定文件 @return String 返回类型 @throws
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @Title: setKey @Description: 车辆唯一标识可以是终端唯一标识也可以是车辆vin @param @param key
	 * 设定文件 @return void 返回类型 @throws
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @Title: getDatetime @Description: 终端上传数据的时间 @param @return 设定文件 @return
	 * Date 返回类型 @throws
	 */
	public Date getDatetime() {
		return datetime;
	}

	/**
	 * @Title: setDatetime @Description: 终端上传数据的时间 @param @param datetime
	 * 设定文件 @return void 返回类型 @throws
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	/**
	 * @Title: getSystemDate @Description: 服务器收到数据的时间 @param @return
	 * 设定文件 @return Date 返回类型 @throws
	 */
	public Date getSystemDate() {
		return systemDate;
	}

	/**
	 * @Title: setSystemDate @Description: 服务器收到数据的时间 @param @param systemDate
	 * 设定文件 @return void 返回类型 @throws
	 */
	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	/**
	 * @Title: getPairs @Description: Cans内容解析 @param @return 设定文件 @return
	 * List<RuleBean> 返回类型 @throws
	 */
	public List<PairResult> getPairs() {
		return pairs;
	}

	/**
	 * @Title: setPairs @Description:Cans内容解析 @param @param pairs 设定文件 @return
	 * void 返回类型 @throws
	 */
	public void setPairs(List<PairResult> pairs) {
		this.pairs = pairs;
	}
	
	/* (非 Javadoc) 
	* <p>Title: toString</p> 
	* <p>Description: 序列化json对象</p> 
	* @return 
	* @see java.lang.Object#toString() 
	*/
	public String toString()
	{
	  return 	JsonUtils.serialize(this);
	}

}
