package com.newnetcom.anlyze.main;

import java.util.Date;
import java.util.Timer;
import com.newnetcom.anlyze.thread.AnlyzeDataTask;
import com.newnetcom.anlyze.thread.CheckCatchTask;
import com.newnetcom.anlyze.thread.DataToKafKaTask;
import com.newnetcom.anlyze.thread.MyTask;
import com.newnetcom.anlyze.thread.RawDataMyTaskRun;
import com.newnetcom.anlyze.thread.ReadInputMessage;
import com.newnetcom.anlyze.thread.UpdateHbaseMyTask;
import com.newnetcom.anlyze.thread.UpdateRedisTask;

public class AppMain {

	public static void main(String[] args) {
		
		Timer timer2 = new Timer();
		timer2.schedule(new MyTask(), new Date(), 60000*60);
		
		// TODO Auto-generated method stub
		RawDataMyTaskRun sendData2 = new RawDataMyTaskRun();
		sendData2.setDaemon(true);
		sendData2.start();
		UpdateHbaseMyTask sendData = new UpdateHbaseMyTask();
		sendData.setDaemon(true);
		sendData.start();
		AnlyzeDataTask sendData1 = new AnlyzeDataTask();
		sendData1.setDaemon(true);
		sendData1.start();     
		UpdateRedisTask sendDataRedis = new UpdateRedisTask();
		sendDataRedis.setDaemon(true);
		sendDataRedis.start(); 
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
