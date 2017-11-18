package com.newnetcom.anlyze.protocols.p808.extra;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.config.Read808Config;
import com.newnetcom.anlyze.protocols.p808.alarm.Protocol0200AlarmStatus;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.StrFormat;

public class Protocol0200ExtraStatus {

	private byte[] content;
	public Protocol0200ExtraStatus(byte[] incontent)
	{
		this.content=incontent;
		this.content = incontent;
		this.charContents = StrFormat.addZeroForNum(ByteUtils.binary(this.content, 2), 32).toCharArray();
		this.statusPairs = Read808Config.getVehicleStatus();
		this.anlysis();
	}
	

	private char[] charContents;

	private static Logger logger = LoggerFactory.getLogger(Protocol0200AlarmStatus.class);

	/**
	 * @Fields alarmPairs : TODO(用一句话描述这个变量表示什么)
	 */
	private List<Pair> statusPairs = new ArrayList<>();

	/**
	 * @Title: getAlarmPairs @Description: TODO(这里用一句话描述这个方法的作用) @param @return
	 * 设定文件 @return List<Pair> 返回类型 @throws
	 */
	public List<Pair> getStatus() {
		return statusPairs;
	}

	
	

	/**
	 * @Title: anlysis @Description: TODO(这里用一句话描述这个方法的作用) @param 设定文件 @return
	 * void 返回类型 @throws
	 */
	private void anlysis() {
		for (Pair pair : this.statusPairs) {
			try {
				pair.setValue(String.valueOf(this.charContents[pair.getStart()]));
			} catch (Exception ex) {
				logger.error("解析808-0200车辆状态错误", ex);
			}
			//System.out.println(pair.getTitle()+":"+pair.getValue());
		}
	}

}
