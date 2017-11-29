package com.newnetcom.anlyze.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.anlyze.AnlyzeMain;
import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.beans.RowKeyBean;
import com.newnetcom.anlyze.beans.VehicleIndex;
import com.newnetcom.anlyze.beans.VehicleInfo;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.config.PropertyResource;
import com.newnetcom.anlyze.utils.JsonUtils;

import cn.ngsoc.hbase.HBase;
import cn.ngsoc.hbase.util.HBaseUtil;

public class AnlyzeDataTaskRun extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(AnlyzeDataTaskRun.class);
//	private int threadNum = Integer
//			.parseInt(PropertyResource.getInstance().getProperties().get("indexHistoryThreadNum"));
//
//	private ExecutorService executor;
//	private long lastTime = 0;
	private long rawHabaseNum = 0;

	public AnlyzeDataTaskRun() {
//		HBaseUtil.init(PropertyResource.getInstance().getProperties().get("zks"));
//		lastTime = System.currentTimeMillis();
//		executor = Executors.newFixedThreadPool(threadNum);
	}

//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private List<Put> puts = new ArrayList<>();
//	private List<VehicleIndex> vehicleIndexs = new ArrayList<>();

	



	@Override
	public void run() {

		while(true)
		{
			try {
				ProtocolBean protocol =publicStaticMap.getRawDataQueue().take();
				//System.out.println("测试"+rawHabaseNum++);
				new AnlyzeMain(protocol).run();
				//Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
