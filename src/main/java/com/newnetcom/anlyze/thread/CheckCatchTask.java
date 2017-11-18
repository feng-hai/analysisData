package com.newnetcom.anlyze.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimerTask;
import com.newnetcom.anlyze.anlyze.db.factory.DatabaseFactory;
import com.newnetcom.anlyze.anlyze.db.interfaces.IDatabase;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.config.PropertyResource;

public class CheckCatchTask extends TimerTask {
	
	private static long lastTime;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void run() {
		lastTime++;
		System.out.println("待处理的原始数据："+publicStaticMap.getRawDataQueue().size());
		System.out.println("待入hbase-解析数据："+publicStaticMap.getCmdQueue().size());
		System.out.println("待更新redis数据："+publicStaticMap.getRedisQueue().size());
		System.out.println("待插入kafka的数据："+publicStaticMap.getSendDataQueue().size());
		if(publicStaticMap.reloadData||lastTime>6*60)
		{
			lastTime=0;
			publicStaticMap.reloadData=false;
			System.out.println("重新加载规则开始"+sdf.format(new Date()));
			Map<String,String> config=	PropertyResource.getInstance().getProperties();
			IDatabase db=DatabaseFactory.getDB(Integer.parseInt(config.get("databaseType")));//1获取配置文件的分析类
			publicStaticMap.setCans(db.getRules());
			System.out.println("重新加载规则结束"+sdf.format(new Date()));
		}
		
	}
}
