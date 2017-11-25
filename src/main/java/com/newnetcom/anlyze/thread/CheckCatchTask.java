package com.newnetcom.anlyze.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.anlyze.db.factory.DatabaseFactory;
import com.newnetcom.anlyze.anlyze.db.interfaces.IDatabase;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.config.PropertyResource;

public class CheckCatchTask extends TimerTask {

	private static long lastTime;
	private static final Logger logger = LoggerFactory.getLogger(CheckCatchTask.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void run() {
		lastTime++;
		if (publicStaticMap.logStatus) {
			System.out.println("待处理的原始数据：" + publicStaticMap.getRawDataQueue().size());
			System.out.println("待入hbase-解析数据：" + publicStaticMap.getCmdQueue().size());
			System.out.println("待更新redis数据：" + publicStaticMap.getRedisQueue().size());
			System.out.println("待插入kafka的数据：" + publicStaticMap.getSendDataQueue().size());
		}

		if (publicStaticMap.reloadData || lastTime > 6 * 60) {
			try {
				lastTime = 0;
				publicStaticMap.reloadData = false;
				System.out.println("重新加载规则开始" + sdf.format(new Date()));
				Map<String, String> config = PropertyResource.getInstance().getProperties();
				DatabaseFactory.getDB(Integer.parseInt(config.get("databaseType")), "A2L");// 1获取配置文件的分析类
				DatabaseFactory.getDB(Integer.parseInt(config.get("databaseType")), "CAN");
				System.out.println("重新加载规则结束" + sdf.format(new Date()));
			} catch (Exception ex) {
				logger.error("加载数据库错误", ex);
			}
		}

	}
}
