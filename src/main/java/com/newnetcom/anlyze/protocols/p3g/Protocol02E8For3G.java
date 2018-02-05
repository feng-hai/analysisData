package com.newnetcom.anlyze.protocols.p3g;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.utils.ByteUtils;

/**   
*    
* 项目名称：com.newnetcom.anlyze   
* 类名称：Protocol02E8For3G   
* 类描述：   
* 创建人：FH   
* 创建时间：2017年10月29日 上午9:47:05   
* @version        
*/
public class Protocol02E8For3G {
	private static final Logger logger = LoggerFactory.getLogger(Protocol02E8For3G.class);
	private byte[] contents;

	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param contents 
	*/
	public Protocol02E8For3G(byte[] contents) {
		this.contents = contents;
		try {
			this.anlyze();
		} catch (Exception ex) {
			logger.error("解析02E8时出错", ex);
		}
	}
	/** 
	* @Fields reason : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private byte reason;// 上报原因
	/** 
	* @Fields packageIndex : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private long packageIndex;// 上报数据包信号
	/** 
	* @Fields lng : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private double lng;// 精确到百万分之一度
	/** 
	* @Fields lat : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private double lat;// 精确到百万分之一度
	/** 
	* @Fields speed : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private float speed;// 0.5km/h
	/** 
	* @Fields height : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private int height;// 高度 米
	/** 
	* @Fields direction : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private int direction;// 方向
	/** 
	* @Fields startTime :起始帧时间(UTC时间) 7个字节
	*/ 
	private Date startTime;// 起始帧时间(UTC时间) 7个字节
	/** 
	* @Fields pattern : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private byte pattern;// CAN数据格式
	/** 
	* @Fields canNum : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private byte canNum;// CAN数据包个数
	/** 
	* @Fields timeInterval : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private short timeInterval;// [和上包CAN数据时间间隔]1
	/** 
	* @Fields cans : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private Map<byte[], byte[]> cans = new HashMap<>();// can帧集合

	/** 
	* @Title: getCans 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return Map<byte[],byte[]>    返回类型 
	* @throws 
	*/
	public Map<byte[], byte[]> getCans() {
		return cans;
	}

	/** 
	* @Title: setCans 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param cans    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setCans(Map<byte[], byte[]> cans) {
		this.cans = cans;
	}

	/** 
	* @Title: anlyze 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void anlyze() {
		reason = this.contents[0];
		packageIndex = ByteUtils.getInt(this.contents, 1);
		lng = ByteUtils.getInt(this.contents, 5)*1.0/1000000;
		lat = ByteUtils.getInt(this.contents, 9)*1.0/1000000;
		speed = (float)(ByteUtils.getShort(this.contents, 13)*0.5);
		height = ByteUtils.getShort(this.contents, 15);
		direction = ByteUtils.getShort(this.contents, 17);
		startTime = ByteUtils.BytesTodate(this.contents, 19);
		pattern = this.contents[26];
		canNum = this.contents[27];
		//timeInterval = ByteUtils.getShort(this.contents, 28);
		//System.out.println(ByteUtils.bytesToHexString(this.contents));
		for (int i = 0; i < canNum; i++) {
			byte[] key = ByteUtils.getSubBytes(this.contents, i * 15+28+2, 4);
			byte[] value = ByteUtils.getSubBytes(this.contents,i * 15+28+2+4+1,8);
			
		//	System.out.println(ByteUtils.byte2HexStr(key)+":"+ByteUtils.byte2HexStr(value)+":"+i);
			cans.put(key, value);
		}
	}

	/** 
	* @Title: getStartTime 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return Date    返回类型 
	* @throws 
	*/
	public Date getStartTime() {
		return startTime;
	}

	/** 
	* @Title: setStartTime 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param startTime    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/** 
	* @Title: getPattern 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return byte    返回类型 
	* @throws 
	*/
	public byte getPattern() {
		return pattern;
	}

	/** 
	* @Title: setPattern 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param pattern    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setPattern(byte pattern) {
		this.pattern = pattern;
	}

	/** 
	* @Title: getCanNum 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return byte    返回类型 
	* @throws 
	*/
	public byte getCanNum() {
		return canNum;
	}

	/** 
	* @Title: setCanNum 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param canNum    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setCanNum(byte canNum) {
		this.canNum = canNum;
	}

	/** 
	* @Title: getTimeInterval 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return short    返回类型 
	* @throws 
	*/
	public short getTimeInterval() {
		return timeInterval;
	}

	/** 
	* @Title: setTimeInterval 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param timeInterval    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setTimeInterval(short timeInterval) {
		this.timeInterval = timeInterval;
	}

	/** 
	* @Title: getReason 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return byte    返回类型 
	* @throws 
	*/
	public byte getReason() {
		return reason;
	}

	/** 
	* @Title: setReason 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param reason    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setReason(byte reason) {
		this.reason = reason;
	}

	/** 
	* @Title: getPackageIndex 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public long getPackageIndex() {
		return packageIndex;
	}

	/** 
	* @Title: setPackageIndex 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param packageIndex    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setPackageIndex(int packageIndex) {
		this.packageIndex = packageIndex;
	}

	/** 
	* @Title: getLng 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return double    返回类型 
	* @throws 
	*/
	public double getLng() {
		return lng;
	}

	/** 
	* @Title: setLng 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lng    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setLng(double lng) {
		this.lng = lng;
	}

	/** 
	* @Title: getLat 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return double    返回类型 
	* @throws 
	*/
	public double getLat() {
		return lat;
	}

	/** 
	* @Title: setLat 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lat    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setLat(double lat) {
		this.lat = lat;
	}

	/** 
	* @Title: getSpeed 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return float    返回类型 
	* @throws 
	*/
	public float getSpeed() {
		return speed;
	}

	/** 
	* @Title: setSpeed 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param speed    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/** 
	* @Title: getHeight 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return short    返回类型 
	* @throws 
	*/
	public int getHeight() {
		return height;
	}

	/** 
	* @Title: setHeight 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param height    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setHeight(int height) {
		this.height = height;
	}

	/** 
	* @Title: getDirection 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return short    返回类型 
	* @throws 
	*/
	public int getDirection() {
		return direction;
	}

	/** 
	* @Title: setDirection 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param direction    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setDirection(int direction) {
		this.direction = direction;
	}

}
