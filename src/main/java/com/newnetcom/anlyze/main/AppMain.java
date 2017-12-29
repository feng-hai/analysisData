package com.newnetcom.anlyze.main;

import java.util.Date;
import java.util.Map;
import java.util.Timer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.anlyze.db.factory.DatabaseFactory;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.config.PropertyResource;
import com.newnetcom.anlyze.thread.AnlyzeDataTask;
import com.newnetcom.anlyze.thread.AnlyzeDataTaskRun;
import com.newnetcom.anlyze.thread.CheckCatchTask;
import com.newnetcom.anlyze.thread.DataToKafKaTask;
import com.newnetcom.anlyze.thread.MyTask;
import com.newnetcom.anlyze.thread.RawDataMyTaskRun;
import com.newnetcom.anlyze.thread.ReadInputMessage;
import com.newnetcom.anlyze.thread.UpdateHbaseMyTask;
import com.newnetcom.anlyze.thread.UpdateRedisTask;

public class AppMain {

	
	private static  Logger logger=LoggerFactory.getLogger(AppMain.class);
	public static void main(String[] args) {
		
		
		Map<String,String> config=	PropertyResource.getInstance().getProperties();
		DatabaseFactory.getDB(Integer.parseInt(config.get("databaseType")),"A2L");//1获取配置文件的分析类
		DatabaseFactory.getDB(Integer.parseInt(config.get("databaseType")),"CAN");
		
	   while(true)
	   {
		   if(publicStaticMap.getCans().size()>0&&publicStaticMap.getA2LValues().size()>0)
		   {
			   break;
		   }
		   try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
		
		
		Timer timer2 = new Timer();
		timer2.schedule(new MyTask(), new Date(), 60000*60);
		// TODO Auto-generated method stub
	   
	   
		
		logger.info("开启分析线程");
		AnlyzeDataTask sendDataA = new AnlyzeDataTask();
		sendDataA.setDaemon(true);
		sendDataA.start();
		
//		AnlyzeDataTaskRun an=new AnlyzeDataTaskRun();
//		an.setDaemon(true);
//		an.start();
		logger.info("开启kafka线程");
		
		RawDataMyTaskRun sendData2 = new RawDataMyTaskRun();
		sendData2.setDaemon(true);
		sendData2.start();
		
		
		logger.info("开启更新hbase线程");
		UpdateHbaseMyTask sendData = new UpdateHbaseMyTask();
		sendData.setDaemon(true);
		sendData.start();
	
		
		logger.info("开启更新redis线程");
		UpdateRedisTask sendDataRedis = new UpdateRedisTask();
		sendDataRedis.setDaemon(true);
		sendDataRedis.start(); 
		logger.info("开启写kafka数据");
		DataToKafKaTask sendDataTask = new DataToKafKaTask();
		sendDataTask.setDaemon(true);
		
		sendDataTask.start();
		
		
		
		Timer timer = new Timer();
		timer.schedule(new CheckCatchTask(), new Date(), 10000);
	
		
		ReadInputMessage reload = new ReadInputMessage();
		reload.setDaemon(true);
		reload.start();
		
		

	}

}
